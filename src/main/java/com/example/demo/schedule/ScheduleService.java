package com.example.demo.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@EnableScheduling
@Service("ScheduleService")
public class ScheduleService {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @SuppressWarnings("unused")
    @Scheduled(initialDelay=3000, fixedDelay=60000)
    public void schduleAction(){
        logger.info("schedule start!!! {}", String.valueOf(logger.isDebugEnabled()));
        logger.debug("debug schedule");
    }
    
}