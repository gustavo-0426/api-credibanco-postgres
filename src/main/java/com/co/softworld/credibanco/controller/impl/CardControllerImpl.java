package com.co.softworld.credibanco.controller.impl;

import com.co.softworld.credibanco.controller.ICardController;
import com.co.softworld.credibanco.model.Card;
import com.co.softworld.credibanco.service.ICardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/${api.version}/card")
@Tag(name = "Card Controller")
@AllArgsConstructor
public class CardControllerImpl implements ICardController {

    private final ICardService cardService;

    @Override
    @PostMapping("/{productId}/number")
    @Operation(summary = "Genera la tarjeta. Recibe por parámetro el id del producto, valida que el producto exista y " +
            "genera la tarjeta con una fecha de expiración de 3 años.")
    public ResponseEntity<Card> generateCard(@PathVariable int productId) {
        return cardService.generateCard(productId);
    }

    @Override
    @PostMapping("/enroll")
    @Operation(summary = "Activa la tarjeta. Recibe en el cuerpo del mensaje el id de la tarjeta, valida que la " +
            "tarjeta exista, que no se encuentre activa y realiza la activación. ")
    public ResponseEntity<Card> activateCard(@RequestBody Card card) {
        return cardService.activateCard(card);
    }

    @Override
    @DeleteMapping("/{cardId}")
    @Operation(summary = "Bloquea la tarjeta. Recibe por parámetro el id de la tarjeta, valida que la tarjeta exista, " +
            "que no se encuentra bloqueada y realiza el bloqueo.")
    public ResponseEntity<Card> block(@PathVariable int cardId) {
        return cardService.block(cardId);
    }

    @Override
    @PostMapping("/balance")
    @Operation(summary = "Recarga el saldo de la tarjeta. Recibe en el cuerpo del mensaje el id de la tarjeta, valida " +
            "que la tarjeta exista, que no exté bloqueada y procesa la recarga de saldo.")
    public ResponseEntity<Card> addBalance(@RequestBody Card card) {
        return cardService.addBalance(card);
    }

    @Override
    @GetMapping("/balance/{cardId}")
    @Operation(summary = "Consulta el saldo de la tarjeta. Recibe por parámetro el id de la tarjeta, valida que la tarjeta exista y realiza la consulta.")
    public ResponseEntity<Map<String, Double>> getBalance(@PathVariable int cardId) {
        return cardService.getBalance(cardId);
    }

    @Override
    @GetMapping
    @Operation(summary = "Consulta todas las tarjetas creadas.")
    public ResponseEntity<List<Card>> findAll() {
        return cardService.findAll();
    }

}
