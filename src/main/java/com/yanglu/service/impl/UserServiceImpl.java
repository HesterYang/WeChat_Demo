package com.yanglu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yanglu.mapper.UserMapper;
import com.yanglu.pojo.User;
import com.yanglu.service.UserService;
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	public User getById(String username) {
		User user = userMapper.selectByPrimaryKey(username);
		return user;
	}

}
