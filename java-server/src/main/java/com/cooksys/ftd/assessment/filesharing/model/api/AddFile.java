package com.cooksys.ftd.assessment.filesharing.model.api;

import com.cooksys.ftd.assessment.filesharing.model.db.User;

public class AddFile {
	private User user;
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}
