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
    public ResponseEntity<User> getById(@PathVariable int id){
        System.out.println(id);
        return new ResponseEntity<>(userService.getById(id),HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user){
        User userSaved =  userService.createUser(user);
        return new ResponseEntity<User>(userSaved,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody User user){
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if(existingUser.isPresent() && existingUser.get().getPassword() == user.getPassword()){
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.badRequest().body("Invalid username/password");
        }

    }
}
