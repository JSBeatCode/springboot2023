package com.example.demo.proxy;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.model.DemoModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@Component
@Service("demoProxy")
public class DemoProxyImple implements IDemoProxy {
    
    @Autowired
    RestTemplate restCaller;
    
    static Gson gson = new Gson();
    
    @Override
    public Map<String, Object> testRest(HttpServletRequest request, DemoModel params) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON_UTF8);
        header.add("Authorization", request.getHeader("Authorization"));
        
        String body = new GsonBuilder().create().toJson(params);
        URI uri = UriComponentsBuilder.fromUriString("http://localhost:7771").path("/hello").build().toUri();
        
        RequestEntity<String> restRequest = new RequestEntity<String>(
            body,
            header,
            HttpMethod.POST,
            uri
            );
            
        ResponseEntity<String> responseEntity = restCaller.exchange(restRequest, String.class);
        String stringObj = responseEntity.getBody();
        Map<String, Object> result = gson.fromJson(stringObj, HashMap.class);
        return result;
    }
    
}