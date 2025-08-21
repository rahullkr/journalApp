package com.journalapp.journalapp.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.journalapp.journalapp.entity.JournalEntry;
import com.journalapp.journalapp.entity.UserEntry;
import com.journalapp.journalapp.service.JournalEntryService;
import com.journalapp.journalapp.service.UserEntryService;

@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserEntryService userEntryService;

    @GetMapping("/{userName}")
    public ResponseEntity<?> getAll(@PathVariable String userName) {
        UserEntry user = userEntryService.findByUserName(userName);  
        List<JournalEntry> journalEntries = user.getJournalEntries();
        if(journalEntries != null && !journalEntries.isEmpty()){
            return new ResponseEntity<>(journalEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{userName}")
    public ResponseEntity<JournalEntry> createJournalEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName ) {
        // Logic to create a journal entry
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry, userName);
        return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getById(@PathVariable ObjectId myId) {
        Optional<JournalEntry> journalEntry = journalEntryService.getById(myId);
        if (journalEntry.isPresent()) {
            System.out.println("Journal entry found: " + journalEntry.get());
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{userName}/{myId}")
    public String deleteById(@PathVariable ObjectId myId, @PathVariable String userName) {
        journalEntryService.deleteById(myId, userName);
        return "Journal entry deleted";
    }

    @PutMapping("/id/{userName}/{myId}")
    public ResponseEntity<?> updateById(@PathVariable ObjectId myId, @PathVariable String userName,
            @RequestBody JournalEntry updatedEntry) {
        JournalEntry old = journalEntryService.getById(myId).orElse(null);
        if (old != null) {
            old.setContent(updatedEntry.getContent() != null && !updatedEntry.getContent().equals("")
                    ? updatedEntry.getContent()
                    : old.getContent());
            old.setTitle(
                    updatedEntry.getTitle() != null && !updatedEntry.getTitle().equals("") ? updatedEntry.getTitle()
                            : old.getTitle());
           journalEntryService.saveEntry(old);
           return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
