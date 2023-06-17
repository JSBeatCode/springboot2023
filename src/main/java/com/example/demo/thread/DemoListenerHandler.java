package com.example.demo.thread;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DemoListenerHandler implements Runnable {
    
    String profile;
    
    public DemoListenerHandler setProfile(String profile){
        this.profile = profile;
        return this;
    }
    
    @Override
    public void run () {
        log.info("handler profile {}", profile);
    }
}