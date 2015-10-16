package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.IdPasswordCombo;
import model.User;

/*The UserTableAccess is used to access the user table into TravelAssistance database*/
public class UserTableAccess implements DBConnection {
	private String DBDRIVER = null;
	private String DBURL = null;
	private String DBUSER = null;
	private String DBPASS = null;

	private static final int MYSQL_DUPLICATE_PK = 1062;

	private Connection conn;
	private Statement statement;

	/* Constructor is used to connect to the TravelAssistance database */
	public UserTableAccess(String DBDRIVER, String DBURL, String DBUSER,
			String DBPASS) {
		this.DBDRIVER = DBDRIVER;
		this.DBURL = DBURL;
		this.DBUSER = DBUSER;
		this.DBPASS = DBPASS;

		try {
			Class.forName(DBDRIVER);
		} catch (ClassNotFoundException e) {
			System.err.println("error when dbdriver");
		}
		try {
			conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
		} catch (SQLException e) {
			System.err.println("error when getconnection");
		}

		try {
			statement = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql = "USE TravelAssistance";
		try {
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			System.err.println("error when use database");
		}
	}

	/* insert a user into the table. Return true when succeed. Else return false */
	public boolean insert(Object obj) {
		PreparedStatement prest = null;

		User user = (User) obj;
		String userid = user.getUserID();
		String password = user.getPassword();
		String name = user.getName();
		int age = user.getAge();
		int sex = user.getSex();
		String email = user.getEmail();
		String phone = user.getPhone();
		String address = user.getAddress();
		String country = user.getCountry();

		String command = "insert into user values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			prest = conn.prepareStatement(command);

			prest.setString(1, userid);
			prest.setString(2, password);
			prest.setString(3, name);
			prest.setInt(4, age);
			prest.setInt(5, sex);
			prest.setString(6, email);
			prest.setString(7, phone);
			prest.setString(8, address);
			prest.setString(9, country);

			prest.executeUpdate();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	/*
	 * Load a user by the id and password. If the id and password is the same as
	 * stored in database, return the user. Else return null
	 */
	public Object load(Object obj) throws SQLException {
		PreparedStatement prest = null;

		String command = "select * from user where id = ? AND password = ?";

		prest = conn.prepareStatement(command);
		IdPasswordCombo IdPasswordCombo = (IdPasswordCombo) obj;
		prest.setString(1, IdPasswordCombo.getId());
		prest.setString(2, IdPasswordCombo.getPassword());

		ResultSet rs1 = prest.executeQuery();
		if (!rs1.next()) {
			System.out.println("no such user found");
			// send signal
			return null;
		}
		rs1.beforeFirst();
		User user = null;
		if (rs1.next()) {
			String name = rs1.getString("name");
			int age = rs1.getInt("age");
			int sex = rs1.getInt("sex");
			String email = rs1.getString("email");
			String phone = rs1.getString("phone");
			String address = rs1.getString("address");
			String country = rs1.getString("country");
			user = new User(IdPasswordCombo.getId(),
					IdPasswordCombo.getPassword(), name, age, sex, email,
					phone, address, country);
		}
		Object object = (Object) user;
		return object;
	}

	/*
	 * Update the information of a user stored in the user table when the user
	 * has edited its information
	 */
	public boolean update(User user) {
		PreparedStatement prest = null;

		String command = "delete from user where id = ?";
		try {
			prest = conn.prepareStatement(command);
			prest.setString(1, user.getUserID());
			prest.executeUpdate();
		} catch (SQLException e) {
			System.err.println("sql exception");
		}

		return this.insert(user);

	}

	/* Close the connection when finish */
	public void close() {
		try {
			this.conn.close();
		} catch (SQLException e) {
			System.err.println("fail to close");
		}
	}

}
