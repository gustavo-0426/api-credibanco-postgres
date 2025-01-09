package com.co.softworld.credibanco.service.impl;

import com.co.softworld.credibanco.exception.InvalidCardException;
import com.co.softworld.credibanco.exception.InvalidTransactionException;
import com.co.softworld.credibanco.model.Card;
import com.co.softworld.credibanco.model.TransactionManager;
import com.co.softworld.credibanco.model.TransactionMapper;
import com.co.softworld.credibanco.repository.ICardRepository;
import com.co.softworld.credibanco.repository.ITransactionRepository;
import com.co.softworld.credibanco.service.ITransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.co.softworld.credibanco.util.IUtility.*;
import static java.lang.String.format;
import static java.time.Duration.between;
import static java.time.LocalDate.now;
import static org.springframework.http.HttpStatus.OK;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements ITransactionService {

    private final ITransactionRepository transactionRepository;
    private final ICardRepository cardRepository;

    @Override
    public ResponseEntity<TransactionManager> purchase(TransactionMapper mapper) {
        Card card = cardRepository.findByCardIdActive(mapper.getCardId())
                .orElseThrow(() -> new InvalidCardException(format("%s %s", DECLINED_TRANSACTION, CARD_NOT_FOUND_OR_IS_INACTIVE)));
        double price = mapper.getPrice();
        String date = now().format(FORMAT_DATE);
        if (date.compareTo(card.getExpiryDate()) > 0)
            throw new InvalidTransactionException(TRANSACTION_INVALID_EXPIRY_CARD);
        if (price <= 0 || card.getBalance() == 0)
            throw new InvalidTransactionException(TRANSACTION_INVALID_BALANCE);
        if (card.getBalance() < price)
            throw new InvalidTransactionException(TRANSACTION_INVALID_INSUFFICIENT_BALANCE);
        card.setBalance(card.getBalance() - price);
        cardRepository.save(card);
        TransactionManager transaction = new TransactionManager();
        transaction.setDate(LocalDateTime.now().format(FORMAT_DATETIME));
        transaction.setCard(card);
        transaction.setPrice(price);
        transaction.setStatus(ACTIVE);
        return new ResponseEntity<>(transactionRepository.save(transaction), OK);
    }

    @Override
    public ResponseEntity<TransactionManager> getPurchase(int transactionId) {
        TransactionManager transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new InvalidTransactionException(TRANSACTION_NOT_FOUND));
        return new ResponseEntity<>(transaction, OK);
    }

    @Override
    public ResponseEntity<TransactionManager> annulation(TransactionMapper mapper) {
        TransactionManager transactionDatabase = transactionRepository.findById(mapper.getTransactionId())
                .orElseThrow(() -> new InvalidTransactionException(TRANSACTION_NOT_FOUND));
        LocalTime timeTransaction = LocalDateTime.parse(transactionDatabase.getDate(), FORMAT_DATETIME).toLocalTime();
        LocalTime timeNow = LocalTime.now();
        if (between(timeTransaction, timeNow).toSeconds() > 1440)
            throw new InvalidTransactionException(ANNULLED_INVALID);
        Card card = transactionDatabase.getCard();
        card.setBalance(card.getBalance() + transactionDatabase.getPrice());
        cardRepository.save(card);
        transactionDatabase.setCard(card);
        transactionDatabase.setStatus(ANNULLED);
        return new ResponseEntity<>(transactionRepository.save(transactionDatabase), OK);
    }

    @Override
    public ResponseEntity<List<TransactionManager>> findAll() {
        return new ResponseEntity<>(transactionRepository.findAll(), OK);
    }
}
