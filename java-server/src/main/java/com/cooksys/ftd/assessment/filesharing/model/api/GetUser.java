package com.cooksys.ftd.assessment.filesharing.model.api;

import java.util.Optional;

import com.cooksys.ftd.assessment.filesharing.dao.UserDao;

public class GetUser {

	public static Response<Short> getID(String username) {
		UserDao userDao = new UserDao();
		Response<Short> output = new Response<Short>();
		Optional<Short> temp = userDao.getUserID(username);
		output.setData(temp.get());
		return output;
	}

	public static Response<Short> getPassword(String username) {
		UserDao userDao = new UserDao();
		Response<Short> output = new Response<Short>();
		Optional<Short> temp = userDao.getPassword(username);
		output.setData(temp.get());
		return output;
	}

}
