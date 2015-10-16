package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.City;
import model.Scene;
import model.User;

/*The SceneTableAccess class is used to access the scene table in the TravelAssistance database*/
public class SceneTableAccess implements DBConnection {
	private String DBDRIVER = null;
	private String DBURL = null;
	private String DBUSER = null;
	private String DBPASS = null;

	private Connection conn;
	private Statement statement;

	/* The constructor is used to connect to the TravelAssistance database */
	public SceneTableAccess(String DBDRIVER, String DBURL, String DBUSER,
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

	/* insert a scene object. Return true if succeed, or return false */
	public boolean insert(Object obj) {
		Scene scene = (Scene) obj;

		PreparedStatement prest = null;

		String name = scene.getName();
		String cityname = scene.getCity().getName();
		CityTableAccess citydao = new CityTableAccess(DBDRIVER, DBURL, DBUSER,
				DBPASS);
		int id = 0;
		try {
			id = citydao.getId(cityname);
			citydao.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (id == -1) {
			return false;
		}

		String command = "insert into scene(sceneName, score, cityID, introduction) values(?, ?, ?, ?)";
		try {
			prest = conn.prepareStatement(command);

			prest.setString(1, scene.getName());
			prest.setDouble(2, scene.getScore());
			prest.setInt(3, id);
			prest.setString(4, scene.getIntroduction());

			prest.executeUpdate();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	/*
	 * Given a city, get its id, and then use it to load all the scene in the
	 * city. Return an arraylist of scene if success, else return null
	 */
	public ArrayList<Object> load(Object obj) throws SQLException {
		PreparedStatement prest = null;

		String command = "select * from scene where cityID = ?";

		prest = conn.prepareStatement(command);

		City city = (City) obj;
		prest.setInt(1, city.getCityID());
		// ArrayList<Scene> arraylist = new ArrayList<Scene>();
		ArrayList<Object> arraylist = new ArrayList<Object>();

		ResultSet rs1 = prest.executeQuery();
		if (!rs1.next()) {
			System.out.println("no source");
			return null;
		}
		rs1.beforeFirst();
		User user = null;
		while (rs1.next()) {
			String scenename = rs1.getString("sceneName");
			String intro = rs1.getString("introduction");
			double score = rs1.getDouble("score");
			Scene scene = new Scene(rs1.getInt("id"), scenename, city, intro,
					score, 0, 0);
			// scene.setId(rs1.getInt("id"));

			Object object = (Object) scene;
			arraylist.add(object);
		}
		return arraylist;
	}

	/* Close the connection when finished */
	public void close() {
		try {
			this.conn.close();
		} catch (SQLException e) {
			System.err.println("fail to close");
		}
	}

	/* Get the name of a scene by its id */
	public String getSceneName(int id) throws SQLException {
		PreparedStatement prest = null;

		String command = "select sceneName from scene where id = ?";

		prest = conn.prepareStatement(command);
		prest.setInt(1, id);

		ResultSet rs1 = prest.executeQuery();
		if (!rs1.next()) {
			System.out.println("no source");
			return null;
		}
		rs1.beforeFirst();
		String scenename = null;
		while (rs1.next()) {
			scenename = rs1.getString("sceneName");

		}
		return scenename;

	}

	/* get the scene object by its id */
	public Scene getScenebyid(int id) throws SQLException {
		PreparedStatement prest = null;

		String command = "select * from scene where id = ?";

		prest = conn.prepareStatement(command);
		prest.setInt(1, id);

		ResultSet rs1 = prest.executeQuery();

		if (!rs1.next()) {
			System.out.println("no source");
			return null;
		}
		rs1.beforeFirst();
		CityTableAccess citydao = new CityTableAccess(DBDRIVER, DBURL, DBUSER,
				DBPASS);
		City city = null;
		Scene scene = null;
		while (rs1.next()) {
			String scenename = rs1.getString("sceneName");
			String intro = rs1.getString("introduction");
			double score = rs1.getDouble("score");
			int cityID = rs1.getInt("cityID");
			city = citydao.getCitybyID(cityID);
			citydao.close();

			if (city == null) {
				System.err.println("no such city when get Scene by id!");
				return null;
			}

			scene = new Scene(rs1.getInt("id"), scenename, city, intro, score,
					0, 0);

		}

		return scene;

	}

}
