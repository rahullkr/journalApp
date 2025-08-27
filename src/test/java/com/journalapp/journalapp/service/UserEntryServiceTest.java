package com.journalapp.journalapp.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.journalapp.journalapp.repository.UserRepo;

@SpringBootTest
public class UserEntryServiceTest {

    @Autowired 
    private UserRepo userRepo;
    @Test
    public void testFindByUserName(){
        assertNotNull(userRepo.findByUserName("adminonly"));
        assertTrue(5>3);
    }

}
