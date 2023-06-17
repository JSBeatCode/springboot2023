package com.example.demo.service.agent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.service.IDemoService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AgentServiceFactory {
    
    @Autowired
    private List<IDemoService> demoService;
    
    public static final Map<String, IDemoService> agentServiceCache = new HashMap<String, IDemoService>();
    
    @PostConstruct
    public void init() {
        try {
            for (IDemoService service: demoService) {
                log.info(service.executorCode());
                agentServiceCache.put(service.executorCode(), service);
            }
        } catch (Exception e) {
            log.error("Invalid AgentSErvice {}", e.getMessage());
        }
    }
    
    public static IDemoService get(String executorCode) {
        IDemoService service = agentServiceCache.get(executorCode);
        if (service == null) {
            throw new RuntimeException("Invalid executor code: " + executorCode);
        }
        return service;
    }
}