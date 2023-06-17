package com.example.demo.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class DemoModel {
    private String id;
    private String code;
    private Map<String, Object> detail;
    private Map<String, Object> subModel;
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class SubModel {
        private String subId;
        private String subCode;
    }
}