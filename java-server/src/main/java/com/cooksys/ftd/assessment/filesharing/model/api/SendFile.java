package com.cooksys.ftd.assessment.filesharing.model.api;

import java.util.Base64;

import com.cooksys.ftd.assessment.filesharing.dao.FileDao;

public class SendFile {
	
	public static Response<String> getFile(String userInfo) throws ClassNotFoundException {
		FileDao fileDao = new FileDao();
		Response<String> output = new Response<String>();
		String[] args = userInfo.split(" ");
		
		byte[] data = fileDao.getFile(Short.parseShort(args[0]), Short.parseShort(args[1]));
		output.setData(Base64.getEncoder().encodeToString(data));
		
		return output;
	}
	
}
