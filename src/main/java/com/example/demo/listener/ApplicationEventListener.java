package com.example.demo.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;

public class ApplicationEventListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    
    // logback을 활용한 customizing logger 사용하려면 이렇게 쓰고 아니면 @Slf4j 어노테이션을 쓰고 log.info 이렇게 사용함
    private static final Logger logger = LoggerFactory.getLogger(ApplicationEventListener.class);
    
	@Override
	public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        Boolean logibatis = event.getEnvironment().getProperty("log.ibatis", boolean.class);
        
        logger.info("logibatis {}", logibatis);
        
        if (logibatis == null) {
            logibatis = false;
        }
        
        if (logibatis) {
            org.apache.ibatis.logging.LogFactory.useSlf4jLogging();;
        } else {
            org.apache.ibatis.logging.LogFactory.useNoLogging();
        }
	}
}