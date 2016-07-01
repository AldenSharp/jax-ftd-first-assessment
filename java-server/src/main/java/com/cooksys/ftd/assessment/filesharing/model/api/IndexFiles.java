package com.cooksys.ftd.assessment.filesharing.model.api;

import java.util.List;

import com.cooksys.ftd.assessment.filesharing.dao.FileDao;

public class IndexFiles {
	
	public static Response<List<String>> getFileList(String userInfo) {
		Response<List<String>> output = new Response<List<String>>();
		FileDao fileDao = new FileDao();
		short id = GetUser.getID(userInfo).getData();
		
		output.setData(fileDao.indexFile(id));

		return output;
	}
}
