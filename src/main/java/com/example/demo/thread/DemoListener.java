package com.example.demo.thread;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

//import org.springframework.bean.factory.annotation.Value;

@Slf4j
@Component
public class DemoListener implements Runnable {
    
    private Thread thread;
    
    private boolean isShutdown = false;
    
    private static Long MAX_SLEEP = 15 * 60 * 1000L;
    
    private int poolHardMaxSize = 100;
    
    private ThreadPoolExecutor threadExecutorPool;
    
    @Value("${constants.thread}")
    private boolean threadGo;
    
    @PostConstruct
    public void init() {
        threadExecutorPool = new ThreadPoolExecutor(1, poolHardMaxSize, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        startDeamon();
    }
    
    @PreDestroy
    private void beforeDestroy() throws InterruptedException {
        this.isShutdown = true;
        thread.join();
        thread = null;
        threadExecutorPool.awaitTermination(1000, TimeUnit.MILLISECONDS);
        threadExecutorPool.shutdown();
        threadExecutorPool = null;
    }
    
    private void startDeamon() {
        if (thread == null) {
            thread = new Thread(this, "Deamon thread for background Task");
        }
        
        if (!thread.isAlive()) {
            thread.start();
        }
    }
    
    @Override
    public void run() {
        log.info("thread listener {}", String.valueOf(threadGo));
        
        Thread currentThread = Thread.currentThread();
        
        if (threadGo) {
            while (currentThread == thread && !this.isShutdown) {
                log.info("thread running...");
                Long sleep = 60000L;
                
                try {
                    DemoListenerHandler handler = new DemoListenerHandler();
                    threadExecutorPool.execute(handler.setProfile("sdcho"));
                } catch (Exception e) {
                    log.error("error");
                    sleep = 1000L;
                }
                
                try { 
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                    
            } 
        }
    }
}