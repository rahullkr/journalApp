package com.journalapp.journalapp.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.journalapp.journalapp.entity.JournalEntry;
import com.journalapp.journalapp.entity.UserEntry;
import com.journalapp.journalapp.repository.JournalEntryRepo;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepo journalEntryRepo;

    @Autowired
    private UserEntryService userEntryService;


    public void saveEntry(JournalEntry journalEntry, String userName) {
        UserEntry user = userEntryService.findByUserName(userName);
      
        JournalEntry saved =  journalEntryRepo.save(journalEntry);
        user.getJournalEntries().add(saved);
        userEntryService.saveEntry(user);
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

    public void deleteById(ObjectId id, String userName) {
        UserEntry user = userEntryService.findByUserName(userName);
        user.getJournalEntries().removeIf(entry -> entry.getId().equals(id));
        userEntryService.saveEntry(user);
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
