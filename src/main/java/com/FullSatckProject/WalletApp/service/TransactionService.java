package com.FullSatckProject.WalletApp.service;

import com.FullSatckProject.WalletApp.entity.Transaction;
import com.FullSatckProject.WalletApp.entity.User;
import com.FullSatckProject.WalletApp.entity.Wallet;
import com.FullSatckProject.WalletApp.exception.WalletException;
import com.FullSatckProject.WalletApp.repository.TransactionRepository;
import com.FullSatckProject.WalletApp.repository.UserRepository;
import com.FullSatckProject.WalletApp.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Transaction> getAll(int userId,Long walletId){
        User user = userRepository.findByUserId(userId).orElse(null);
        if(user != null){
            Wallet wallet = walletRepository.findById(walletId).orElse(null);
            if(wallet != null){
                return transactionRepository.findByWalletId(walletId);
            }
        }

        return null;
    }
    public Transaction getById(int userId,Long walletId,Long id){
        User user = userRepository.findByUserId(userId).orElse(null);
        if(user != null){
            Optional<Wallet> wallet = walletRepository.findById(walletId);
            if(wallet.isPresent()) {
                Optional<Transaction> transaction = transactionRepository.findById(id);
                if (transaction.isPresent()) {
                    return transaction.get();
                }
            }
        }

        throw new WalletException("Transaction with "+id+" does not exists!");
    }
    public Transaction createOrUpdate(int userId,Long walletId, Transaction transaction){
        User user = userRepository.findByUserId(userId).orElse(null);
        if(user != null){
            Wallet wallet = walletRepository.findById(walletId).orElse(null);
//        Optional<Wallet> wallet = walletRepository.findById(walletId);

            if(wallet != null){
                transaction.setWalletId(walletId);
                if(transaction.getType() == 1){
                    wallet.setCurrentBalance(wallet.getCurrentBalance() + transaction.getAmount());
                }
                if (transaction.getType() == 2){
                    wallet.setCurrentBalance(wallet.getCurrentBalance() - transaction.getAmount());
                    if(wallet.getCurrentBalance() < 0){
                        wallet.setCurrentBalance(0.0);
                    }
                }
                if(transaction.getType() == 3){
                    User newUser = userRepository.findByEmail(transaction.getUserEmail()).get();
                    System.out.println("newUser is "+newUser);
                    newUser.setBalance(newUser.getBalance() + transaction.getAmount());
                    wallet.setCurrentBalance(wallet.getCurrentBalance() - transaction.getAmount());
                    if(wallet.getCurrentBalance() < 0){
                        wallet.setCurrentBalance(0.0);
                    }
                    userRepository.save(newUser);
                }
                transactionRepository.save(transaction);
                walletRepository.save(wallet);

                return transaction;
            }
        }

        return null;
    }
    public boolean delete(int userId,Long wallet_id,Long id){
        Optional<Wallet> wallet = walletRepository.findById(wallet_id);
        if(wallet.isPresent()) {
            Optional<Transaction> transaction = transactionRepository.findById(id);
            if (transaction.isPresent()) {
                transactionRepository.delete(transaction.get());
                return true;
            }
        }
        throw new WalletException("Transaction with "+id+" does not exists!");
    }
}
