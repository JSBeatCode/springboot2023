package com.example.demo.constants;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum DemoEnum {
    SUCCESS("success", 1),
    FAIL("fail", 2);
    
    private static final Map<String, DemoEnum> lookup = new HashMap<String, DemoEnum>();
    
    static {
        for (DemoEnum key: EnumSet.allOf(DemoEnum.class)) {
            lookup.put(key.getCode(), key);
        }
    }
    
    private String code;
    private String name;
    private int orderHint;
    
    private DemoEnum(String code, int orderHint) {
        this.code = code;
        this.name = "name: " + code;
        this.orderHint = orderHint;
    }
    
    public String getCode() {
        return this.code;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getOrderHint() {
        return this.orderHint;
    }
    
    public boolean equalsCode(String code) {
        return this.equals(get(code));
    }
    
    public static DemoEnum get(String code) {
        return lookup.get(code);
    }
}