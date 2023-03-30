package com.FullSatckProject.WalletApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.FullSatckProject.WalletApp.entity.Wallet;
import com.FullSatckProject.WalletApp.service.ValidationErrorService;
import com.FullSatckProject.WalletApp.service.WalletService;

import javax.validation.Valid;


@RestController
@RequestMapping("/wallet")
@CrossOrigin
public class WalletController {
	
	@Autowired
	private WalletService walletService;
	
	@Autowired
	private ValidationErrorService validationService;
	
	@GetMapping("/{userId}")
	public ResponseEntity<?> getAll(@PathVariable int userId){
		return new ResponseEntity<>(walletService.getAll(userId),HttpStatus.OK);
	}

	@GetMapping("/{userId}/{id}")
	public ResponseEntity<?> getById(@PathVariable int userId,@PathVariable Long id){
		return new ResponseEntity<>(walletService.getById(userId,id),HttpStatus.OK);
	}
	
	@PostMapping("/{userId}")
	public ResponseEntity<?> create(@PathVariable int userId,@Valid @RequestBody Wallet wallet, BindingResult result ){
		ResponseEntity errors = validationService.validate(result);
		if(errors!=null) return errors;
		Wallet walletSaved =  walletService.createOrUpdate(userId ,wallet);
		return new ResponseEntity<Wallet>(walletSaved,HttpStatus.CREATED);
	}

	@PutMapping("/{userId}/{id}")
	public ResponseEntity<?> update(@PathVariable int userId,@PathVariable Long id,@Valid @RequestBody Wallet wallet, BindingResult result){
		ResponseEntity errors = validationService.validate(result);
		if(errors != null) return errors;
		wallet.setId(id);
		Wallet walletSaved = walletService.createOrUpdate(userId,wallet);
		return new ResponseEntity<Wallet>(walletSaved,HttpStatus.OK);
	}

	@DeleteMapping("/{userId}/{id}")
	public ResponseEntity<?> delete(@PathVariable int userId,@PathVariable Long id){
		walletService.delete(userId,id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
