package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IDemoDAO;
import com.example.demo.model.DemoModel;
import com.example.demo.proxy.DemoFeignProxy;
import com.example.demo.proxy.IDemoProxy;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import lombok.extern.slf4j.Slf4j;

@Service("demoService")
@Slf4j
public class DemoServiceImple implements IDemoService {
    
    @Autowired
    IDemoDAO demoDAO;
    
    @Autowired
    IDemoProxy demoProxy;
    
    @Autowired
    DemoFeignProxy demoFeignClient;
    
    protected static LoadingCache<String, DemoModel> demoCache;
    
    @PostConstruct
    public void init(){
        demoCache = CacheBuilder.newBuilder()
            .expireAfterAccess(60, TimeUnit.SECONDS)
            .build(new CacheLoader<String, DemoModel>(){
               
               @SuppressWarnings("rawtypes")
               @Override
               public DemoModel load(String key) throws Exception {
                   DemoModel fromDB = demoDAO.selectOne(key);
                   return fromDB;
               }
            });
    }
    
    @Async
    @Override
    public void testAsync() {
        log.info("it is working as asyncronized");
    }
    
    @Override
    public String executorCode() {
        return "DEMO";
    }
    
    @Override
    public List<DemoModel> getDemoAll(){
        
        List<DemoModel> result = new ArrayList<DemoModel>();
        try {
            result = demoDAO.selectAll();
            log.info("result: {}", result.toString());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getDemoAll Error: {}", e.getMessage());
        }
        return result;
    }
    
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED) // 
    public int updateDemo(DemoModel model){
        int result = 0;
        try {
            result = demoDAO.updateDemo(model);
            // 일부러 에러
            String test = null;
            test.length();
        } catch (Exception e) {
            log.error("updateDemo Error: {}", e.getMessage());;
            throw new Error("updateDemo error: "+ e.getMessage());
        }
        return result;
    }
    
    @Override
    public Map<String, Object> testRest(HttpServletRequest request, DemoModel params) {
        return demoProxy.testRest(request, params);
    }
     
    @Override
    public DemoModel testCache() {
        DemoModel result = demoCache.getUnchecked("id001");
        return result;
    }
    
    @Override
    public Map<String, Object> testFeign(DemoModel params) {
        return demoFeignClient.feignTest(params);
    }
}