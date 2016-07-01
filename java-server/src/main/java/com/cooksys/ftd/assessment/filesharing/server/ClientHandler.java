package com.cooksys.ftd.assessment.filesharing.server;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.eclipse.persistence.jaxb.MarshallerProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cooksys.ftd.assessment.filesharing.model.api.*;
import com.cooksys.ftd.assessment.filesharing.model.db.Message;
import com.cooksys.ftd.assessment.filesharing.dao.*;

public class ClientHandler implements Runnable {
	
	private Logger log = LoggerFactory.getLogger(ClientHandler.class);

	private BufferedReader reader;
	private PrintWriter writer;
	
	private JAXBContext jaxb;
	private Marshaller marshaller;
	private Unmarshaller unmarshaller;

	private FileDao fileDao;
	private UserFileDao userFileDao;
	private UserDao userDao;
	
	public void respond(Response temp) {
		StringWriter w = new StringWriter();
		this.jaxb = JAXBContext.newInstance(Response.class);
		marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
		marshaller.marshal(temp.getData(), w);
		this.writer.println(w.toString());
		this.writer.flush();
	}

	@Override
	public void run() {
		
		try {
			StringReader r = new StringReader(this.reader.readLine());
			this.jaxb = JAXBContext.newInstance(Message.class);
			Message message = (Message) unmarshaller.unmarshal(r);
			if (message.getCommand() == "register") {
				Response<String> temp = CreateUser.newUser(message.getContent());
				respond(temp);
			} else if (message.getCommand() == "files") {
				Response<List<String>> temp = IndexFiles.getFileList(message.getContent());
				respond(temp);
			} else if (message.getCommand() == "upload") {
				Response<String> temp = AddFile.newFile(message.getContent());
				respond(temp);
			} else if (message.getCommand() == "download") {
				Response<String> temp = SendFile.getFile(message.getContent());
				respond(temp);
			}
		} catch (IOException | JAXBException e) {
			log.error("Client handler error.");
		}
	}

	public BufferedReader getReader() {
		return reader;
	}

	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}

	public PrintWriter getWriter() {
		return writer;
	}

	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}

	public FileDao getFileDao() {
		return fileDao;
	}

	public void setFileDao(FileDao fileDao) {
		this.fileDao = fileDao;
	}

	public UserFileDao getUserFileDao() {
		return userFileDao;
	}

	public void setUserFileDao(UserFileDao userFileDao) {
		this.userFileDao = userFileDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
