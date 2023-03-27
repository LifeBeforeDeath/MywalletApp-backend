package com.FullSatckProject.WalletApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.FullSatckProject.WalletApp.entity.Wallet;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletRepository extends MongoRepository<Wallet, Long> {
}
