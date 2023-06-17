package com.example.demo;

import java.util.List;
import static org.assertj.core.api.Assertions.*;
import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import com.example.demo.model.DemoModel;
import com.example.demo.service.IDemoService;

import lombok.extern.slf4j.Slf4j;

@SpringJUnitConfig
@SpringJUnitWebConfig
@SpringBootTest
@Slf4j
class DemoServiceTest{
     private static HttpServletRequest request;
     
     @Autowired
     IDemoService demoService;
     
     @BeforeAll
     protected static void initControllerCommon() {
         request = new MockHttpServletRequest(); 
     }
     
     @Test
     void testGetDemoAll(){
         log.debug(request.getLocalName());
         List<DemoModel> list = demoService.getDemoAll();
         if (list.size() > 0) {
             assertThat(true);
         } else {
             fail("Not yet Implemented.");
         }
     }
 }
