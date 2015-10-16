package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.City;
import model.User;

/*Class CityTableAccess is used to access city tables in the TravelAssistance database*/
public class CityTableAccess implements DBConnection {

	private String DBDRIVER = null;
	private String DBURL = null;
	private String DBUSER = null;
	private String DBPASS = null;

	private Connection conn;
	private Statement statement;

	/* Constructor is used to connect to TravelAssitance database */
	public CityTableAccess(String DBDRIVER, String DBURL, String DBUSER,
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

	/* insert a city. Return true if succeed, or return false */
	public boolean insert(Object obj) {
		PreparedStatement prest = null;

		City city = (City) obj;

		String cityname = city.getName();
		Double score = city.getScore();
		String location = city.getLocation();
		String introduction = city.getIntroduction();

		String command = "insert into city(cityName, score, location, introduction) values(?, ?, ?, ?)";
		try {
			prest = conn.prepareStatement(command);

			prest.setString(1, cityname);
			prest.setDouble(2, score);
			prest.setString(3, location);
			prest.setString(4, introduction);

			prest.executeUpdate();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	public ArrayList<Object> load(Object obj) throws SQLException {
		PreparedStatement prest = null;

		String command = "select * from city";

		prest = conn.prepareStatement(command);
		// ArrayList<City> arraylist = new ArrayList<City>();
		ArrayList<Object> arraylist = new ArrayList<Object>();

		ResultSet rs1 = prest.executeQuery();
		if (!rs1.next()) {
			System.out.println("no source");
			return null;
		}
		rs1.beforeFirst();
		User user = null;
		while (rs1.next()) {
			String cityname = rs1.getString("cityName");
			double score = rs1.getDouble("score");
			String location = rs1.getString("location");
			String intro = rs1.getString("introduction");
			City city = new City(rs1.getInt("id"), cityname, location, intro,
					score);
			// city.setID(rs1.getInt("id"));
			// arraylist.add(city);

			Object object = (Object) city;
			arraylist.add(object);
		}

		return arraylist;
	}

	/* get the id of the city by city name */
	public int getId(String cityname) throws SQLException {
		System.out.println(cityname);
		PreparedStatement prest = null;
		String command = "select * from city where cityName = ?";
		ResultSet rs1 = null;
		try {
			prest = conn.prepareStatement(command);
			prest.setString(1, cityname);
			rs1 = prest.executeQuery();
			if (!rs1.next()) {
				System.out.println("no such user found");
				return -1;
			}
		} catch (SQLException e) {
			System.err.println("eqlexception");
		}

		return rs1.getInt("id");

	}

	/*
	 * Return a city object by its id. this method is used to load the city
	 * object when constructing a scene object
	 */
	public City getCitybyID(int id) throws SQLException {
		PreparedStatement prest = null;

		String command = "select * from city where id = ?";
		ResultSet rs1 = null;

		prest = conn.prepareStatement(command);
		prest.setInt(1, id);
		rs1 = prest.executeQuery();

		if (!rs1.next()) {
			System.out.println("no source");
			return null;
		}
		rs1.beforeFirst();

		City city = null;
		while (rs1.next()) {
			String cityname = rs1.getString("cityName");
			double score = rs1.getDouble("score");
			String location = rs1.getString("location");
			String intro = rs1.getString("introduction");
			city = new City(id, cityname, location, intro, score);

		}

		return city;

	}

	/* Close connection when finished */
	public void close() {
		try {
			this.conn.close();
		} catch (SQLException e) {
			System.err.println("fail to close");
		}
	}

}
