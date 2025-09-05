package com.HTTPHeaders.Practise.Controller;

import com.HTTPHeaders.Practise.Model.Transaction;
import com.HTTPHeaders.Practise.Service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/money")
@RequiredArgsConstructor
public class TransactionController {

    @Autowired
    private final TransactionService transactionService;


    @PostMapping("/transfer")
    public ResponseEntity<String> transferMoney(@RequestBody Transaction transaction){
        return ResponseEntity.ok(transactionService.transferMoney(transaction));
    }


}