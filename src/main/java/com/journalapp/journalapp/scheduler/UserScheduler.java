package com.journalapp.journalapp.scheduler;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.journalapp.journalapp.entity.JournalEntry;
import com.journalapp.journalapp.entity.UserEntry;
import com.journalapp.journalapp.repository.UserRepositoryImpl;
import com.journalapp.journalapp.service.EmailService;
import com.journalapp.journalapp.service.SentimentAnalysisService;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Scheduled(cron = "0 0 0 * * *")
    public void fetchUsersAndSendSaMail(){
        List<UserEntry> users = userRepositoryImpl.getUserforSA();
        for(UserEntry user: users){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<String> collect = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getContent()).collect(Collectors.toList());
            String collectedEntry = String.join(" ", collect);
            String sentiment = sentimentAnalysisService.getSentiment(collectedEntry);
            emailService.sendEmail(user.getEmail(), "Sentiment Analysis", sentiment);
        }
    }
}
