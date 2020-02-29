package com.zw.service.imp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zw.dao.UsersMapper;
import com.zw.model.Users;
import com.zw.service.UserService;

@Service
public class UserServiceImp implements UserService{

	@Resource
	UsersMapper usersMapper;
	
	@Override
	public int register(String userName, String password) {
		Users user=new Users();
		user.setUsername(userName);
		user.setPasswd(password);
		return usersMapper.insertSelective(user);
		
	}

}
