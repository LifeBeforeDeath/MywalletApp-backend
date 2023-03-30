package com.FullSatckProject.WalletApp.controller;


import com.FullSatckProject.WalletApp.entity.Transaction;
import com.FullSatckProject.WalletApp.service.TransactionService;
import com.FullSatckProject.WalletApp.service.ValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/transaction")
@CrossOrigin
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ValidationErrorService validationService;
    @GetMapping("/{userId}/{wallet_id}")
    public ResponseEntity<?> getAll(@PathVariable int userId,@PathVariable Long wallet_id){
        return new ResponseEntity<>(transactionService.getAll(userId,wallet_id), HttpStatus.OK);
    }
    @GetMapping("/{userId}/{wallet_id}/{id}")
    public ResponseEntity<?> getById(@PathVariable int userId,@PathVariable Long wallet_id,@PathVariable Long id){
        return new ResponseEntity<>(transactionService.getById(userId,wallet_id,id),HttpStatus.OK);
    }
    @PostMapping("/{userId}/{wallet_id}")
    public ResponseEntity<?> create(@PathVariable int userId ,@PathVariable Long wallet_id, @Valid @RequestBody Transaction transaction, BindingResult result){
        ResponseEntity errors = validationService.validate(result);
        if(errors != null) return errors;
        Transaction transactionSaved = transactionService.createOrUpdate(userId,wallet_id,transaction);
        return new ResponseEntity<Transaction>(transaction,HttpStatus.CREATED);
    }
    @PutMapping("/{userId}/{wallet_id}/{id}")
    public ResponseEntity<?> update(@PathVariable int userId,@PathVariable Long wallet_id,@PathVariable Long id,@Valid @RequestBody Transaction transaction, BindingResult result){
        ResponseEntity errors = validationService.validate(result);
        if(errors != null) return errors;
        transaction.setId(id);
        Transaction transactionSaved = transactionService.createOrUpdate(userId,wallet_id,transaction);
        return new ResponseEntity<Transaction>(transactionSaved,HttpStatus.OK);
    }
    @DeleteMapping("/{userId}/{wallet_id}/{id}")
    public ResponseEntity<?> delete(@PathVariable int userId ,@PathVariable Long wallet_id,@PathVariable Long id){
        transactionService.delete(userId ,wallet_id,id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
