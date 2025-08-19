package com.journalapp.journalapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.journalapp.journalapp.repository.UserRepo;

@Component
public class UserEntryService {
        @Autowired
        private UserRepo userRepo;  
}
