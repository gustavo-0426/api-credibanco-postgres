package com.co.softworld.credibanco.service.impl;

import com.co.softworld.credibanco.exception.InvalidCardException;
import com.co.softworld.credibanco.model.Card;
import com.co.softworld.credibanco.model.Product;
import com.co.softworld.credibanco.repository.ICardRepository;
import com.co.softworld.credibanco.repository.IProductRepository;
import com.co.softworld.credibanco.service.ICardService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.co.softworld.credibanco.util.IUtility.*;
import static java.lang.Math.random;
import static java.lang.String.format;
import static java.time.LocalDate.now;
import static org.springframework.http.HttpStatus.OK;

@Service
@AllArgsConstructor
public class CardServiceImpl implements ICardService {

    private final ICardRepository cardRepository;
    private final IProductRepository productRepository;

    @Override
    public ResponseEntity<Card> generateCard(int productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new InvalidCardException(PRODUCT_NOT_FOUND));
        Card card = new Card();
        card.setNumber(generateNumber(productId));
        card.setProduct(product);
        card.setExpiryDate(now().plusYears(3).format(FORMAT_DATE));
        return new ResponseEntity<>(cardRepository.save(card), OK);
    }

    @Override
    public String generateNumber(int productId) {
        long random = (long)(random() * 10000000000L);
        return format("%s%10d", productId, random);
    }

    @Override
    public ResponseEntity<Card> activateCard(Card card) {
        Card cardDatabase = cardRepository.findByCardIdInactive(card.getCardId())
                .orElseThrow(() -> new InvalidCardException(CARD_NOT_FOUND_OR_IS_ACTIVE));
        cardDatabase.setActive(1);
        return new ResponseEntity<>(cardRepository.save(cardDatabase), OK);
    }

    @Override
    public ResponseEntity<Card> block(int cardId) {
        Card cardDatabase = cardRepository.findByCardIdActive(cardId)
                .orElseThrow(() -> new InvalidCardException(CARD_NOT_FOUND_OR_IS_INACTIVE));
        cardDatabase.setActive(0);
        return new ResponseEntity<>(cardRepository.save(cardDatabase), OK);
    }

    @Override
    public ResponseEntity<Card> addBalance(Card card) {
        if (card.getBalance() <= 0)
            throw new InvalidCardException(CARD_INVALID_BALANCE);
        Card cardDatabase = cardRepository.findByCardIdActive(card.getCardId()).orElseThrow(() -> new InvalidCardException(CARD_NOT_FOUND_OR_IS_INACTIVE));
        cardDatabase.setBalance(cardDatabase.getBalance() + card.getBalance());
        return new ResponseEntity<>(cardRepository.save(cardDatabase), OK);
    }

    @Override
    public ResponseEntity<Map<String, Double>> getBalance(int cardId) {
        Card cardDatabase = cardRepository.findByCardIdActive(cardId)
                .orElseThrow(() -> new InvalidCardException(CARD_NOT_FOUND_OR_IS_INACTIVE));
        Map<String, Double> balance = new HashMap<>();
        balance.put("balance", cardDatabase.getBalance());
        return new ResponseEntity<>(balance, OK);
    }

    @Override
    public ResponseEntity<List<Card>> findAll() {
        return new ResponseEntity<>(cardRepository.findAll(), OK);
    }

}