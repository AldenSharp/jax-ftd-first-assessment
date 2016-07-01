package com.cooksys.ftd.assessment.filesharing.model.api;

import java.util.Base64;

import com.cooksys.ftd.assessment.filesharing.dao.FileDao;
import com.cooksys.ftd.assessment.filesharing.model.db.File;
import com.cooksys.ftd.assessment.filesharing.model.db.User;

public class AddFile {
	private User user;
	
	public static Response<String> newFile(String userInfo) {
		File file = new File();
		FileDao fileDao = new FileDao();
		Response<String> output = new Response<String>();
		String[] args = userInfo.split(" ");
		
		short id = GetUser.getID(args[0]).getData();
		file.setFilepath(args[1]);
		file.setFiledata(Base64.getDecoder().decode(args[2]));
		
		fileDao.addFile(file);
		
		output.setData("Uploaded file.");
		return output;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}
