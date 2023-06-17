package com.example.demo.utility.type;

import com.google.gson.Gson;

public class TypeUtility {
    
    public static boolean isEmpty(String input) {
        return (input == null || input.length() == 0 || input.trim().length() ==0);
    }
    
    public static <T> T fromStringToObject(final Gson gson, String input, Class<T> type) {
        String temp = cleanJsonString(input);
        return gson.fromJson(temp, type);
    }
    
    public static String cleanJsonString(String input) {
        if (TypeUtility.isEmpty(input)) {
            return null;
        }
        
        final StringBuilder sb = new StringBuilder(input.length());
        input.chars().filter(item -> ((' ' <= item && item <= '~') || 128 <= item))
            .forEach(item -> sb.append((char)item));
            
        return sb.toString();
    }
}