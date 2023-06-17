package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.constants.DemoEnum;
import com.example.demo.model.DemoModel;
import com.example.demo.service.IDemoService;
import com.example.demo.service.agent.AgentServiceFactory;
import com.example.demo.utility.endecrypt.EndecryptUtility;
import com.netflix.discovery.shared.transport.decorator.EurekaHttpClientDecorator.RequestType;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

@Slf4j
@RestController
@CrossOrigin(origins="*") // (origins="주소","주소")
@RequestMapping(value="demo") 
public class DemoController {
    
    @Autowired
    IDemoService demoService;
    
    @Value("${constants.data}")
    private String DATA;
    
    @RequestMapping(value="/properties", method=RequestMethod.GET)
    @ResponseBody
    public boolean properties(HttpServletRequest request){
        log.info(DATA);
        return true;
    }
    
    @RequestMapping(value="/enum", method=RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> enumTest(HttpServletRequest request){
        
        if (DemoEnum.SUCCESS.getCode().equals("success")) {
            log.info(DemoEnum.SUCCESS.getCode());
            log.info(DemoEnum.SUCCESS.getName());
            log.info(String.valueOf(DemoEnum.SUCCESS.getOrderHint()));
        }
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("code", DemoEnum.SUCCESS.getCode());
        resultMap.put("name", DemoEnum.SUCCESS.getName());
        resultMap.put("orderHint", String.valueOf(DemoEnum.SUCCESS.getOrderHint()));
        return resultMap;
    }
    
    @RequestMapping(value="/encrypt/{data}", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String encrypt(HttpServletRequest request,
        @PathVariable(value="data", required=true) String data,
        @RequestHeader(value=HttpHeaders.AUTHORIZATION, required=true) String token
    ) throws Exception {
        String result = EndecryptUtility.encrypt(EndecryptUtility.getKey(), data);
        log.info(result);
        return result;
    }
    
    @RequestMapping(value="/decrypt/{data}", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String decrypt(HttpServletRequest request,
        @PathVariable(value="data", required=true) String data,
        @RequestHeader(value=HttpHeaders.AUTHORIZATION, required=true) String token
    ) throws Exception {
        byte[] dec = EndecryptUtility.decrypt(EndecryptUtility.getKey(), data);
        String result = StringUtils.newStringUtf8(dec);
        log.info(result);
        return result;
    }
    
    @RequestMapping(value="/getall", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public List<DemoModel> getDemoAll(HttpServletRequest request) {
        
        List<DemoModel> result = demoService.getDemoAll();
        return result;
        
    }
    
    @RequestMapping(value="/update", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public int updateDemo(HttpServletRequest request,
        @RequestBody DemoModel params) {
        
        return demoService.updateDemo(params);
        
    }
    
    @RequestMapping(value="/rest", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Map<String, Object> testRest(HttpServletRequest request,
        @RequestBody DemoModel params) {
        
        return demoService.testRest(request, params);
        
    }
    
    @RequestMapping(value="/agent", method=RequestMethod.GET)
    @ResponseBody
    public List<DemoModel> testAgent(HttpServletRequest request) {
        IDemoService agentService = AgentServiceFactory.get("DEMO");
        return agentService.getDemoAll();
    }
    
    @RequestMapping(value="/cache", method=RequestMethod.GET)
    @ResponseBody
    public DemoModel testCache(HttpServletRequest request) {
        
        return demoService.testCache();
        
    }
    
    @RequestMapping(value="/feign", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Map<String, Object> testFeign(HttpServletRequest request,
        @RequestBody DemoModel params) {
        
        return demoService.testFeign(params);
        
    }
}