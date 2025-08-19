package com.journalapp.journalapp.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.journalapp.journalapp.entity.JournalEntry;
import com.journalapp.journalapp.service.JournalEntryService;

@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> getAll() {
        return journalEntryService.getAll();
    }

    @PostMapping
    public String createJournalEntry(@RequestBody JournalEntry myEntry) {
        // Logic to create a journal entry
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
        return "Journal entry created";
    }

    @GetMapping("/id/{myId}")
    public JournalEntry getById(@PathVariable ObjectId myId) {
        return journalEntryService.getById(myId);
    }

    @DeleteMapping("/id/{myId}")
    public String deleteById(@PathVariable ObjectId myId) {
        journalEntryService.deleteById(myId);
        return "Journal entry deleted";
    }
    @PutMapping("/id/{myId}")
    public JournalEntry updateById(@PathVariable ObjectId myId, @RequestBody JournalEntry updatedEntry) {
        JournalEntry old = journalEntryService.getById(myId);
        if(old!=null){
            old.setContent(updatedEntry.getContent()!= null && !updatedEntry.getContent().equals("") ? updatedEntry.getContent() : old.getContent());
            old.setTitle(updatedEntry.getTitle()!= null && !updatedEntry.getTitle().equals("") ? updatedEntry.getTitle() : old.getTitle());

        }
        updatedEntry.setId(myId);
        return journalEntryService.updateById(myId, updatedEntry);
    }

}
