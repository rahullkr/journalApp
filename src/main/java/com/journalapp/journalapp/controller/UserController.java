package com.journalapp.journalapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.journalapp.journalapp.entity.UserEntry;
import com.journalapp.journalapp.repository.UserRepo;
import com.journalapp.journalapp.service.UserEntryService;
@RestController
@RequestMapping("/user")
public class UserController {
@Autowired
private UserEntryService userEntryService;

@Autowired
private UserRepo userRepo;  

@GetMapping

public List<UserEntry> getAllUsers(){
    return userEntryService.getAll();
}


@PutMapping
public ResponseEntity<?> updateUser(@RequestBody UserEntry user) {

    //first get the user 
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userName = authentication.getName();
    UserEntry existingUser = userEntryService.findByUserName(userName);
    if (existingUser != null) {
        existingUser.setUserName(user.getUserName());
        existingUser.setPassword(user.getPassword());
        userEntryService.saveEntry(existingUser); 
    }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}
@DeleteMapping 
public ResponseEntity<?> deleteUserById() {
     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
     userRepo.deleteByUserName(authentication.getName());
return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}

}

