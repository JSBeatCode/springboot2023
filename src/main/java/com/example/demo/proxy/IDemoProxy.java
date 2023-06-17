package com.example.demo.proxy;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.model.DemoModel;

public interface IDemoProxy {
    
    Map<String, Object> testRest(HttpServletRequest request, DemoModel params);
    
}