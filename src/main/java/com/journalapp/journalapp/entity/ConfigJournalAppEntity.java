package com.journalapp.journalapp.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@NoArgsConstructor

public class ConfigJournalAppEntity {
    @Id
    private ObjectId id;
    private String key; 
    private String value;
}
