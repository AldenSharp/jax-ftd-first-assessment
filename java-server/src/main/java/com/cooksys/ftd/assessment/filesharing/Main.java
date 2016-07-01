package com.cooksys.ftd.assessment.filesharing;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cooksys.ftd.assessment.filesharing.dao.FileDao;
import com.cooksys.ftd.assessment.filesharing.dao.UserDao;
import com.cooksys.ftd.assessment.filesharing.dao.UserFileDao;
import com.cooksys.ftd.assessment.filesharing.server.Server;

public class Main {
	private static Logger log = LoggerFactory.getLogger(Main.class);

	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/file";
	private static String username = "root";
	private static String password = "bondstone";

	public static void main(String[] args) throws ClassNotFoundException {

		Class.forName(driver); // register jdbc driver class
		ExecutorService executor = Executors.newCachedThreadPool(); // initialize thread pool

		try (Connection conn = DriverManager.getConnection(url, username, password)) {

			Server server = new Server(); // initialize server
			server.setServerSocket(new ServerSocket(667));
			server.setExecutor(executor);

			FileDao fileDao = new FileDao();
			fileDao.setConn(conn);
			server.setFileDao(fileDao);

			UserFileDao userFileDao = new UserFileDao();
			userFileDao.setConn(conn);
			server.setUserFileDao(userFileDao);

			UserDao userDao = new UserDao();
			userDao.setConn(conn);
			server.setUserDao(userDao);

			Future<?> serverFuture = executor.submit(server); // start server (asynchronously)

			serverFuture.get(); // blocks until server's run method is done, i.e. the server shuts down

		} catch (SQLException | InterruptedException | ExecutionException | IOException e) {
			log.error("Server error during server startup.", e);
		} finally {
			executor.shutdown(); // shut down the thread pool
		}
	}
}
