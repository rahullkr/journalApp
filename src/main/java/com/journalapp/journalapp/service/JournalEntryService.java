package com.journalapp.journalapp.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.journalapp.journalapp.entity.JournalEntry;
import com.journalapp.journalapp.repository.JournalEntryRepo;

@Component
public class JournalEntryService {
    @Autowired 
    private JournalEntryRepo journalEntryRepo;

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepo.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepo.findAll();
    }
    public JournalEntry getById(ObjectId id) {
        return journalEntryRepo.findById(id).orElse(null);
    }

    public void deleteById(ObjectId id) {
        journalEntryRepo.deleteById(id);
    }

    public JournalEntry updateById(ObjectId id, JournalEntry updatedEntry) {
        return journalEntryRepo.findById(id).map(entry -> {
            entry.setTitle(updatedEntry.getTitle());
            entry.setContent(updatedEntry.getContent());
            entry.setDate(updatedEntry.getDate());
            return journalEntryRepo.save(entry);
        }).orElse(null);
    }
}
