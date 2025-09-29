package com.example.bankcards.util;

import com.example.bankcards.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@EnableScheduling
public class ScheduledSetExpired {
    @Autowired
    CardService cardService;

    @Scheduled(cron = "0 0 21 * * *")
    public void cleanExpiredDocuments(){
        cardService.setExpiredStatus(LocalDateTime.now());
    }
}

