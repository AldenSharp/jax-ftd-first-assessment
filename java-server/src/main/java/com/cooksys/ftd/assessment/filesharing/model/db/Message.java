package com.cooksys.ftd.assessment.filesharing.model.db;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Message")
public class Message {
	
	@XmlElement(name = "command")
	private String command;
	
	@XmlElement(name = "content")
	private String content;
	
	public String getCommand() {
		return this.command;
	}
	
	public void setCommand(String command) {
		this.command = command;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

}
