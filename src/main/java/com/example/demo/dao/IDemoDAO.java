package com.example.demo.dao;

import java.util.List;

import com.example.demo.model.DemoModel;

public interface IDemoDAO {
    
    List<DemoModel> selectAll();
    
    DemoModel selectOne(String id);
    
    int updateDemo(DemoModel model);
    
}