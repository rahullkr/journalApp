package com.journalapp.journalapp.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.journalapp.journalapp.entity.ConfigJournalAppEntity;
import com.journalapp.journalapp.repository.ConfigJournalAppRepo;

import jakarta.annotation.PostConstruct;

@Component
public class AppCache {
     private static final Logger logger = LoggerFactory.getLogger(AppCache.class);
    @Autowired
    private ConfigJournalAppRepo configJournalAppRepo; 
    public Map<String, String>  APP_CACHE = new HashMap<>();

    @PostConstruct
    public void init() {
        List<ConfigJournalAppEntity> all = configJournalAppRepo.findAll();
        logger.info("Loaded {} config entries from DB", all.size());
        for (ConfigJournalAppEntity e : all) {
            logger.info("Caching config -> key: '{}', value: '{}'", e.getKey(), e.getValue());
            APP_CACHE.put(e.getKey(), e.getValue());
        }
        logger.info("Final cache keys: {}", APP_CACHE.keySet());
    }
}
