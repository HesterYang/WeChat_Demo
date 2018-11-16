package com.yanglu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yanglu.pojo.User;
import com.yanglu.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/test/{username}")
	@ResponseBody
	public User getUser(@PathVariable String username) {	 
		User user = userService.getById(username);
		System.out.println(username);
		return user;
	}
}
