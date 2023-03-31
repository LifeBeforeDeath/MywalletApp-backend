package com.FullSatckProject.WalletApp.controller;

import com.FullSatckProject.WalletApp.entity.User;
import com.FullSatckProject.WalletApp.entity.Wallet;
import com.FullSatckProject.WalletApp.repository.UserRepository;
import com.FullSatckProject.WalletApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getById(@PathVariable int userId){
        System.out.println(userId);
        return new ResponseEntity<>(userService.getById(userId),HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user){
        User userSaved =  userService.createUser(user);
        return new ResponseEntity<User>(userSaved,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user){
        User existingUser = userRepository.findByEmail(user.getEmail()).orElse(null);
        System.out.println("existingUser"+existingUser);
        if(existingUser != null && existingUser.getPassword() == user.getPassword()){
            System.out.println("inside existingUser");
            existingUser.setPassword(null);
            return new ResponseEntity<User>(existingUser,HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().body("Invalid username/password");
        }

    }
}
