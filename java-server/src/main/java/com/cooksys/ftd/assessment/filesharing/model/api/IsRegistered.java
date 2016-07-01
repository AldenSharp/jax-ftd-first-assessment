package com.cooksys.ftd.assessment.filesharing.model.api;

import java.util.List;

import com.cooksys.ftd.assessment.filesharing.dao.UserDao;

public class IsRegistered {
	
	public static Response<Boolean> getBoolean(String username) throws ClassNotFoundException {
		Response<Boolean> output = new Response<Boolean>();
		UserDao userDao = new UserDao();
		output.setData(userDao.findUser(username));
		return output;
	}
}
