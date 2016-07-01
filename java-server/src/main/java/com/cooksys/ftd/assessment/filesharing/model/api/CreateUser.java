package com.cooksys.ftd.assessment.filesharing.model.api;

import com.cooksys.ftd.assessment.filesharing.dao.UserDao;
import com.cooksys.ftd.assessment.filesharing.model.db.User;

public class CreateUser {
	
	public static Response<String> newUser(String userInfo) {
		User user = new User();
		UserDao userDao = new UserDao();
		Response<String> output = new Response<String>();
		String[] args = userInfo.split(" ");
		short check = GetUser.getId(args[0]).getData();
		
		if(check == 0) {
			user.setUsername(args[0]);
			user.setPassword(args[1]);
			userDao.createUser(user);
		} else {
			output.setError(true);
		}
	}

}
