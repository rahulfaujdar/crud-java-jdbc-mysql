package com.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.user.model.User;

public class UserDao {
	private String jdbcURL = "jdbc:mysql://localhost:3306/jdbc?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "password";
	private String jdbcDriver = "com.mysql.cj.jdbc.Driver";

	private static final String INSERT_USER = "INSERT INTO users (name, mobile, address) VALUES (?, ?, ?)";
	private static final String DELETE_USER = "delete from users where id = ?";
	private static final String SELCET_USER = "SELECT * from users where id=?";
	private static final String SELECT_USERS = "SELECT * from users";
	private static final String UPDATE_USER = "update users set name=?, mobile=?, address=? where id=?";

	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName(jdbcDriver);
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public boolean insertUser(User user) throws SQLException {
		boolean inserted = false;
		try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(INSERT_USER)) {
			ps.setString(1, user.getName());
			ps.setString(2, user.getMobile());
			ps.setString(3, user.getAddress());
			inserted = ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return inserted;
	}

	public boolean deleteUser(int id) throws SQLException {
		boolean deleted = false;
		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(DELETE_USER);) {
			ps.setInt(1, id);
			deleted = ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return deleted;

	}

	public User selectUser(int id) throws SQLException {
		User user = null;
		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(SELCET_USER);) {
			ps.setInt(1, id);
			ResultSet r = ps.executeQuery();
			while (r.next()) {
				String name = r.getString("name");
				String mobile = r.getString("mobile");
				String address = r.getString("address");

				user = new User(id, name, mobile, address);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;

	}
	
	public List<User> selectUsers() throws SQLException{
		List<User> users = new ArrayList<>(); 
		try(Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(SELECT_USERS);) {
			
			ResultSet r = ps.executeQuery();
			while (r.next()) {
				int id = r.getInt("id");
				String name = r.getString("name");
				String mobile = r.getString("mobile");
				String address = r.getString("address");
				users.add(new User(id, name, mobile, address));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public boolean updateUser(User user) throws SQLException {
		boolean updated = false;
		try(Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(UPDATE_USER);) {
			ps.setString(1, user.getName());
			ps.setString(2, user.getMobile());
			ps.setString(3, user.getAddress());
			ps.setInt(4, user.getId());
			
			updated = ps.executeUpdate() > 0;
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return updated;
		
	}
}
