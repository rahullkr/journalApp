package com.journalapp.journalapp.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.journalapp.journalapp.entity.JournalEntry;
import com.journalapp.journalapp.entity.UserEntry;
import com.journalapp.journalapp.repository.JournalEntryRepo;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepo journalEntryRepo;

    @Autowired
    private UserEntryService userEntryService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {
        try {

            UserEntry user = userEntryService.findByUserName(userName);
            JournalEntry saved = journalEntryRepo.save(journalEntry);
            user.getJournalEntries().add(saved);
            userEntryService.saveUser(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepo.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepo.findAll();
    }

    public Optional<JournalEntry> getById(ObjectId id) {
        return journalEntryRepo.findById(id);
    }
    @Transactional
    public void deleteById(ObjectId id, String userName) {
        UserEntry user = userEntryService.findByUserName(userName);
        boolean removed = user.getJournalEntries().removeIf(entry -> entry.getId().equals(id));
        if(removed){
            userEntryService.saveUser(user);
        journalEntryRepo.deleteById(id);
        }
        

    }

    public JournalEntry updateById(ObjectId id, JournalEntry updatedEntry) {
        return journalEntryRepo.findById(id).map(entry -> {
            entry.setTitle(updatedEntry.getTitle());
            entry.setContent(updatedEntry.getContent());
            entry.setDate(updatedEntry.getDate());
            return journalEntryRepo.save(entry);
        }).orElse(null);
    }

    public List<JournalEntry> findByUserName(String userName) {
        UserEntry user = userEntryService.findByUserName(userName);
        return user.getJournalEntries();
    }
}
