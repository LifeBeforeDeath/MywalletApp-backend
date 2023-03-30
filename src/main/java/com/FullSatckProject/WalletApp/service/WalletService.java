package com.FullSatckProject.WalletApp.service;

import com.FullSatckProject.WalletApp.entity.Transaction;
import com.FullSatckProject.WalletApp.entity.User;
import com.FullSatckProject.WalletApp.exception.WalletException;
import com.FullSatckProject.WalletApp.repository.TransactionRepository;
import com.FullSatckProject.WalletApp.repository.UserRepository;
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

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private UserRepository userRepository;

	public List<Wallet> getAll(int userId){
		Optional<User> user = userRepository.findByUserId(userId);
		if(user.isPresent()){
			return walletRepository.findByUserId(userId);
		}
		return null;
	}

	public Wallet getById(int userId,Long id){
		Optional<User> user = userRepository.findByUserId(userId);
		if(user.isPresent()){
			Optional<Wallet> wallet = walletRepository.findById(id);
			List<Transaction> transactionList = transactionRepository.findByWalletId(id);
			if(wallet.isPresent()){
				wallet.get().setTransactions(transactionList);
				return wallet.get();
			}
		}

		throw new WalletException("Wallet with "+id+" does not exists!");
	}
	
	public Wallet createOrUpdate(int userId,Wallet wallet) {
		User user = userRepository.findByUserId(userId).orElse(null);
		if(user!=null){
			wallet.setUserId(userId);
			user.setBalance(user.getBalance() + wallet.getCurrentBalance());
			if(wallet.getId()==null) {
				walletRepository.save(wallet);
			}else {
				List<Transaction> transactionList = transactionRepository.findByWalletId(wallet.getId());
				wallet.setTransactions(transactionList);
				walletRepository.save(wallet);

			}
			return wallet;
		}
		return null;
	}

	public boolean delete(int userId,Long id){
		User user = userRepository.findByUserId(userId).orElse(null);
		if(user != null){
			Optional<Wallet> wallet = walletRepository.findById((id));
			if(wallet.isPresent()){
				walletRepository.delete(wallet.get());
				return true;
			}
		}

		throw new WalletException("Wallet with "+id+" does not exists!");
	}
}
