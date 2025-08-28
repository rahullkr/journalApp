package com.journalapp.journalapp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.journalapp.journalapp.entity.ConfigJournalAppEntity;


public interface ConfigJournalAppRepo extends MongoRepository<ConfigJournalAppEntity, ObjectId> {

}
