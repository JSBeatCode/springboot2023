package com.example.demo.proxy;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.model.DemoModel;

@FeignClient(name="demoFeignClient", url="http://localhost:7771")
public interface DemoFeignProxy {
    
    @RequestMapping(value="/hello", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method=RequestMethod.POST)
    // @PostMapping("/hello")
    public Map<String, Object> feignTest(
        // @RequestHeader("Authorization") String token,
        // @PathVariable("userId") String userId,
        // @RequestParam(value="from", required=false) String from,
        @RequestBody DemoModel params
        );
}