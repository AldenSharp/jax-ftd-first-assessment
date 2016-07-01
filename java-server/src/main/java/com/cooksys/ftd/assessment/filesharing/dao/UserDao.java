package com.cooksys.ftd.assessment.filesharing.dao;

import com.cooksys.ftd.assessment.filesharing.model.db.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import com.cooksys.ftd.assessment.filesharing.model.db.User;

public class UserDao extends AbstractDao {
	
	private static Logger log = LoggerFactory.getLogger(FileDao.class);
	
	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/file";
	private static String username = "root";
	private static String password = "bondstone";
	
	public void addUser(User user) throws ClassNotFoundException {
		Class.forName(driver);
		
		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			
			String sql = "insert into user (username, password) values (?, ?)";
			
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			
			stmt.execute();
			
			log.info("New user added to database.");
		} catch (SQLException e) {
			log.error("Query Error from UserDao.addUser method.");
		}
	}
	
	public Boolean findUser(String username) throws ClassNotFoundException {
		Class.forName(driver);
		
		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			
			String sql = "select username from user";
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				if(username == rs.getString("username")) {
					return true;
				}
			}
			
			return false;
		} catch (SQLException e) {
			log.error("Query Error from UserDao.findUser method. This method will now return null.");
			return null;
		}
	}
	
	public Optional<Short> getUserID(String uname) throws ClassNotFoundException {
		Class.forName(driver);
		
		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			
			String sql = "select u.user_id"
					+ " from user u"
					+ " where u.username = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, uname);
			
			ResultSet rs = stmt.executeQuery();
			
			Short output = rs.getShort("u.user_id");
			
			return Optional.of(output);
			
		} catch (SQLException e) {
			log.error("Query Error from UserDao.getUserID method. This method will now return null.");
			return null;
		}
	}
	
	public Optional<Short> getPassword(String uname) throws ClassNotFoundException {
		Class.forName(driver);
		
		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			
			String sql = "select u.password"
					+ " from user u"
					+ " where u.username = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, uname);
			
			ResultSet rs = stmt.executeQuery();
			
			Short output = rs.getShort("u.password");
			
			return Optional.of(output);
			
		} catch (SQLException e) {
			log.error("Query Error from UserDao.getPassword method. This method will now return null.");
			return null;
		}
	}
	
}
