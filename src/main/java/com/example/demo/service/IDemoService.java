package com.example.demo.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.model.DemoModel;

public interface IDemoService {

	Map<String, Object> testFeign(DemoModel params);

	DemoModel testCache();

	Map<String, Object> testRest(HttpServletRequest request, DemoModel params);

	int updateDemo(DemoModel model);

	List<DemoModel> getDemoAll();

	String executorCode();

	void testAsync();

}
