package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.City;
import model.SceneIDCountCombo;
import model.User;

/*The ScheduleSceneTableAccess class is used to access the schedule_scene table in the TravelAssistance database*/
public class ScheduleSceneTableAccess {
	private String DBDRIVER = null;
	private String DBURL = null;
	private String DBUSER = null;
	private String DBPASS = null;

	private Connection conn;
	private Statement statement;

	/* The constructor is used to connect to the TravelAssistance database */
	public ScheduleSceneTableAccess(String DBDRIVER, String DBURL,
			String DBUSER, String DBPASS) {
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

	/*
	 * The function is used to get the sceneID and its corresponding counts in
	 * the schedule_scene table. It is used for the recommendation system
	 */
	public ArrayList<SceneIDCountCombo> getSceneCount() throws SQLException {
		PreparedStatement prest = null;

		String command = "select sceneID, count(*)  from schedule_scene group by sceneID ";

		prest = conn.prepareStatement(command);
		ArrayList<SceneIDCountCombo> arraylist = new ArrayList<SceneIDCountCombo>();
		ResultSet rs1 = prest.executeQuery();
		if (!rs1.next()) {
			System.out.println("no source");
			return null;
		}
		rs1.beforeFirst();

		while (rs1.next()) {
			int sceneID = rs1.getInt("sceneID");
			int count = rs1.getInt("count(*)");
			SceneIDCountCombo combo = new SceneIDCountCombo(sceneID, count);

			arraylist.add(combo);
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
}
