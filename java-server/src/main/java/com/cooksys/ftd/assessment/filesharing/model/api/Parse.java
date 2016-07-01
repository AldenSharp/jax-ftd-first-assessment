package com.cooksys.ftd.assessment.filesharing.model.api;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cooksys.ftd.assessment.filesharing.model.db.Message;

public class Parse {
	
	public static Message unmarshall(String input) throws JAXBException {
		
		Logger log = LoggerFactory.getLogger(Parse.class);
		
		Map<String, Object> properties = new HashMap<String, Object>(2);
		properties.put("eclipselink.media-type", "application/json");
		JAXBContext context = JAXBContext.newInstance(new Class[] { Message.class }, properties);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		log.info("Unmarshaller created.");
		StringReader reader = new StringReader(input);
		log.info("Stringreader declared.");

		Message m = (Message) unmarshaller.unmarshal(reader);
		log.info("Message created: " + m.getCommand() + "; " + m.getContent());
		return m;
	}
	
	public static String marshall(Message message) throws JAXBException {
		Map<String, Object> properties = new HashMap<String, Object>(2);
		properties.put("eclipselink.media-type", "application/json");
		JAXBContext jc = JAXBContext.newInstance(new Class[] { Message.class }, properties);
		StringWriter sw = new StringWriter();
		Marshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(message, sw);
		return sw.toString();
	}
}