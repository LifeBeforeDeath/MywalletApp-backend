package com.FullSatckProject.WalletApp.repository;

import com.FullSatckProject.WalletApp.entity.Transaction;
import com.FullSatckProject.WalletApp.entity.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction,Long> {
    List<Transaction> findByWalletId(Long walletId);
}
