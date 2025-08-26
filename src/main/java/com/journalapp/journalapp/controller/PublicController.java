package com.journalapp.journalapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.journalapp.journalapp.entity.UserEntry;
import com.journalapp.journalapp.service.UserEntryService;

@RestController
@RequestMapping("/public")
public class PublicController {

        @Autowired    private UserEntryService userEntryService;

         @PostMapping("/create-user")
        public void CreateUser( @RequestBody UserEntry user){
            System.out.println("Creating user: " + user.getUserName()); 
            userEntryService.saveEntry((user));
        }
}
