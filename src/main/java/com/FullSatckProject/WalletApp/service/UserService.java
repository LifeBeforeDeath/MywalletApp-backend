package com.FullSatckProject.WalletApp.service;

import com.FullSatckProject.WalletApp.entity.Transaction;
import com.FullSatckProject.WalletApp.entity.User;
import com.FullSatckProject.WalletApp.entity.Wallet;
import com.FullSatckProject.WalletApp.exception.UserNotFound;
import com.FullSatckProject.WalletApp.exception.WalletException;
import com.FullSatckProject.WalletApp.repository.UserRepository;
import com.FullSatckProject.WalletApp.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;
    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User getById(int id){
        User user = userRepository.findByUserId(id).orElse(null);
        System.out.println(user);
        List<Wallet> walletList = walletRepository.findByUserId(id);
        if(user != null){

            user.setWallets(walletList);
            userRepository.save(user);
            return user;
        }
        throw new WalletException("User with "+id+" does not exists!");
    }

    public User createUser(User user){
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
//            throw new UserNotFound();
        }
        userRepository.save(user);
        user.setPassword(null);
        return user;
    }


}

