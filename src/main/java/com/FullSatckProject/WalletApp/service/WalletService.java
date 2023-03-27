package com.FullSatckProject.WalletApp.service;

import com.FullSatckProject.WalletApp.exception.WalletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FullSatckProject.WalletApp.entity.Wallet;
import com.FullSatckProject.WalletApp.repository.WalletRepository;

import java.util.List;
import java.util.Optional;

//import jakarta.validation.constraints.AssertFalse.List;

@Service
public class WalletService {
	
	@Autowired
	private WalletRepository walletRepository;

	public List<Wallet> getAll(){
		return walletRepository.findAll();
	}

	public Wallet getById(Long id){
		Optional<Wallet> wallet = walletRepository.findById(id);
		if(wallet.isPresent()){
			return wallet.get();
		}
		throw new WalletException("Wallet with "+id+" does not exists!");
	}
	
	public Wallet createOrUpdate(Wallet wallet) {
		if(wallet.getId()==null) {
			walletRepository.save(wallet);
		}else {
			walletRepository.save(wallet);
		}
		return wallet;
	}

	public boolean delete(Long id){
		Optional<Wallet> wallet = walletRepository.findById((id));
		if(wallet.isPresent()){
			walletRepository.delete(wallet.get());
			return true;
		}
		throw new WalletException("Wallet with '+id+' does not exists!");
	}
}
