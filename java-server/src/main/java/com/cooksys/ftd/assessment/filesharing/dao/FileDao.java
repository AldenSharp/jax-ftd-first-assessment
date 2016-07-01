package com.cooksys.ftd.assessment.filesharing.dao;

import com.cooksys.ftd.assessment.filesharing.model.db.File;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileDao extends AbstractDao {
	
	private static Logger log = LoggerFactory.getLogger(FileDao.class);
	
	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/file";
	private static String username = "root";
	private static String password = "bondstone";
	
	public void addFile(File f) throws ClassNotFoundException {
		
		Class.forName(driver);
		
		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			
			String sql = "insert into file (filepath, file_data) values (?, ?)";
			
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, f.getFilepath());
			stmt.setBytes(2, f.getFiledata());
			
			stmt.execute();
			
			log.info("File added to database.");
		} catch (SQLException e) {
			log.error("Query Error from FileDao.addFile method.");
		}
	}
	
	public List<String> getFileList(short userId) throws ClassNotFoundException {
		
		Class.forName(driver);
		
		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			
			String sql = "select f.filepath, f.file_data"
					+ " from file f, user_file uf"
					+ " where f.file_id = fc.file_id"
					+ " and fc.user_id = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			stmt.setShort(1, userId);
			
			ResultSet rs = stmt.executeQuery();
			
			List<String> output = new ArrayList<String>();
			
			while (rs.next()) {
				String filepath = rs.getString("f.filepath");
				String fileData = rs.getString("f.file_data");
				output.add(filepath + " - " + fileData);
			}
			
			return output;
		} catch (SQLException e) {
			log.error("Query Error from FileDao.getFileList method. This method will now return null.");
			return null;
		}
	}
	
	public byte[] getFile(short fileId, short altPath) throws ClassNotFoundException {
		
		Class.forName(driver);
		
		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			
			String sql = "select f.filepath, f.file_data"
					+ " from file f"
					+ " where f.file_id = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			stmt.setShort(1, fileId);
			
			ResultSet rs = stmt.executeQuery();
			
			return rs.getBytes("f.file_data");
			
		} catch (SQLException e) {
			log.error("Query Error from FileDao.getFile method. This method will now return null.");
			return null;
		}
	}
	
}
