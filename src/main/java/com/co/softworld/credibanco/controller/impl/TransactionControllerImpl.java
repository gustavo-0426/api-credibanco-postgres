package com.co.softworld.credibanco.controller.impl;

import com.co.softworld.credibanco.controller.ITransactionController;
import com.co.softworld.credibanco.model.TransactionManager;
import com.co.softworld.credibanco.model.TransactionMapper;
import com.co.softworld.credibanco.service.ITransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@Api(tags = "Transaction Controller")
@AllArgsConstructor
public class TransactionControllerImpl implements ITransactionController {

    private final ITransactionService transactionService;

    @Override
    @PostMapping("/purchase")
    @ApiOperation(value = "Realiza una compra. Recibe en el cuerpo del mensaje el id de la tarjeta y el precio de la " +
            "compra, valida que la tarjeta exista, que se encuentre activa, que la fecha de expiración esté vigente, " +
            "que el precio de la compra sea mayor a 0 y tenga el saldo suficiente para procesar la transacción.")
    public ResponseEntity<TransactionManager> purchase(@RequestBody TransactionMapper mapper) {
        return transactionService.purchase(mapper);
    }

    @Override
    @GetMapping("/{transactionId}")
    @ApiOperation(value = "Consulta una transacción. Recibe por parámetro el id de la transacción, valida que la " +
            "transacción exista y genera la consulta.")
    public ResponseEntity<TransactionManager> getPurchase(@PathVariable int transactionId) {
        return transactionService.getPurchase(transactionId);
    }

    @Override
    @PostMapping("/anulation")
    @ApiOperation(value = "Anula una transacción. Recibe en el cuerpo del mensaje el id de la transacción, valida que " +
            "la transacción exista, que la fecha sea menor a 24 horas y procesa la anulación restando el precio de la " +
            "transacción a la tarjeta.")
    public ResponseEntity<TransactionManager> annulation(@RequestBody TransactionMapper mapper) {
        return transactionService.annulation(mapper);
    }

    @Override
    @GetMapping
    @ApiOperation(value = "Consulta todas las transacciones realizadas.")
    public ResponseEntity<List<TransactionManager>> findAll() {
        return transactionService.findAll();
    }
}
