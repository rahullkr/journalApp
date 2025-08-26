package com.journalapp.journalapp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.journalapp.journalapp.entity.UserEntry;

public interface UserRepo extends MongoRepository<UserEntry, ObjectId> {
    // Define custom query methods if needed
    UserEntry findByUserName(String userName);
    void deleteByUserName(String userName);

}
