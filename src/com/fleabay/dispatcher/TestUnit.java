package com.fleabay.dispatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fleabay.database.data.Good;

@Controller
public class TestUnit {
	@RequestMapping("test")
	@ResponseBody
	public List<Good> test(@RequestBody Map<String, Object> requestData, HttpServletRequest request) {
		// 处理请求数据
		System.out.println(requestData);
		// 生成响应数据
		List<Good> responseData = new ArrayList<Good>();
		Good apple = new Good(), banana = new Good();
		apple.setId("1");
		apple.setName("apple");
		banana.setId("2");
		banana.setName("banana");
		responseData.add(apple);
		responseData.add(banana);
		// 响应请求
		return responseData;
	}
}
