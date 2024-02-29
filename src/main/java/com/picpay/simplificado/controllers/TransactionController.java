package com.picpay.simplificado.controllers;

import com.picpay.simplificado.dto.TransactionDTO;
import com.picpay.simplificado.servicies.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionDTO> saveTransaction(@RequestBody TransactionDTO dto) throws Exception {
        dto = transactionService.createTransaction(dto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
