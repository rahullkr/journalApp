package com.journalapp.journalapp.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.journalapp.journalapp.entity.UserEntry;
import com.journalapp.journalapp.repository.UserRepo;

@Component
public class UserEntryService {
        @Autowired
        private UserRepo userRepo;  

        public void saveEntry(UserEntry user){
                userRepo.save(user);
        }

        public List<UserEntry> getAll() {return userRepo.findAll();}
        public Optional<UserEntry> findById(ObjectId id) {return userRepo.findById(id);}
        public void deleteById(ObjectId id) {userRepo.deleteById(id);}
        public UserEntry findByUserName(String username) {return userRepo.findByUserName(username);}
        
}
