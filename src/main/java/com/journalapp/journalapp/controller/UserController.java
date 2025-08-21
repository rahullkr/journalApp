package com.journalapp.journalapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.journalapp.journalapp.entity.UserEntry;
import com.journalapp.journalapp.service.UserEntryService;
@RestController
@RequestMapping("/user")
public class UserController {
@Autowired
private UserEntryService userEntryService;

@GetMapping

public List<UserEntry> getAllUsers(){
    return userEntryService.getAll();
}

@PostMapping
public void CreateUser( @RequestBody UserEntry user){
    System.out.println("Creating user: " + user.getUserName()); 
    userEntryService.saveEntry((user));
}

@PutMapping("/{userName}")
public ResponseEntity<?> updateUser(@RequestBody UserEntry user, @PathVariable String userName) {
    UserEntry existingUser = userEntryService.findByUserName(userName);
    if (existingUser != null) {
        existingUser.setUserName(user.getUserName());
        existingUser.setPassword(user.getPassword());
        userEntryService.saveEntry(existingUser);   
    }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}
}

