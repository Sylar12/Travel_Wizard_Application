package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import model.City;
import model.Scene;
import model.Schedule;
import model.ScheduleUseridCombo;

/*The ScheudlTableAccess class is used to access Schedule table in the TravelAssitance Database*/
public class ScheduleTableAccess implements DBConnection {
	private String DBDRIVER = null;
	private String DBURL = null;
	private String DBUSER = null;
	private String DBPASS = null;

	private Connection conn;
	private Statement statement;

	/* The constructor is used to connect to the TravelAssistance database */
	public ScheduleTableAccess(String DBDRIVER, String DBURL, String DBUSER,
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

	/*
	 * insert a schedule into the table. Return ture when success, else return
	 * false
	 */
	public boolean insert(Object obj) {

		try {
			String query = "INSERT INTO `schedule` (cityName, userID) VALUES ( ? , ?)";
			PreparedStatement prest = conn.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);

			ScheduleUseridCombo ScheduleUseridCombo = (ScheduleUseridCombo) obj;
			prest.setString(1, ScheduleUseridCombo.getSchedule().getName());
			prest.setString(2, ScheduleUseridCombo.getUserid());

			prest.executeUpdate();

			ResultSet rs = prest.getGeneratedKeys();
			int numero = -1;
			if (rs != null && rs.next()) {
				numero = (int) rs.getLong(1);
			}

			prest.close();

			System.out.println("Here is Schedule table: schedule ID = "
					+ numero);

			query = "INSERT INTO `schedule_scene` (scheduleID, sceneID, date, time) VALUES\n";
			int len = ScheduleUseridCombo.getSchedule().getSceneNum();
			System.out.println("Here is schedule scene number: " + len);
			if (len == 0) {
				return true;
			}
			Statement stmt = conn.createStatement();
			Scene scene = ScheduleUseridCombo.getSchedule().getScene(0);
			query += "(" + numero + ", " + scene.getSceneID() + ", "
					+ scene.getDate() + ", " + scene.getTime() + ")";
			for (int i = 1; i < len; i++) {
				scene = ScheduleUseridCombo.getSchedule().getScene(i);
				query += ",\n(" + numero + ", " + scene.getSceneID() + ", "
						+ scene.getDate() + ", " + scene.getTime() + ")";
			}
			stmt.executeUpdate(query);
			stmt.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/*
	 * load all the schedules of a user. Return an arraylist of Schedule Class
	 * when succeed. Else return null
	 */
	public ArrayList<Object> load(Object obj) throws SQLException {
		PreparedStatement prest = null;
		TreeMap<Integer, Schedule> schedules = new TreeMap<Integer, Schedule>();
		HashMap<Integer, City> cities = new HashMap<Integer, City>();

		String command = "SELECT schedule.id as schedule_id, schedule.cityName as cityName,"
				+ "scene.sceneName as scene_name, scene.score as scene_score, scene.introduction as scene_intro,"
				+ "city.cityName as city_name,city.score as city_score,city.location as city_location, city.introduction as city_intro,"
				+ "city.id as city_id, schedule_scene.date as scene_date, schedule_scene.time as scene_time,"
				+ "scene.id as scene_id FROM schedule_scene inner join schedule on schedule_scene.scheduleID = schedule.id"
				+ " inner join scene on schedule_scene.sceneID = scene.id "
				+ "inner join city on scene.cityID = city.id where schedule.userID = ?";

		prest = conn.prepareStatement(command);
		String userid = (String) obj;
		prest.setString(1, userid);
		ResultSet rs1 = prest.executeQuery();
		while (rs1.next()) {
			int schedule_id = rs1.getInt("schedule_id");
			Schedule schedule = null;
			if (schedules.containsKey(schedule_id)) {
				schedule = schedules.get(schedule_id);
			} else {
				String schedule_intro = rs1.getString("cityName");
				schedule = new Schedule(schedule_intro, null);
				schedules.put(schedule_id, schedule);
			}
			int city_id = rs1.getInt("city_id");
			City city = null;
			if (cities.containsKey(city_id)) {
				city = cities.get(city_id);
			} else {
				String city_name = rs1.getString("city_name");
				double city_score = rs1.getDouble("city_score");
				String city_location = rs1.getString("city_location");
				String city_intro = rs1.getString("city_intro");
				city = new City(city_id, city_name, city_location, city_intro,
						city_score);
				// city.setID(city_id);
				cities.put(city_id, city);
			}
			String scene_name = rs1.getString("scene_name");
			double scene_score = rs1.getDouble("scene_score");
			String scene_intro = rs1.getString("scene_intro");
			int scene_date = rs1.getInt("scene_date");
			int scene_time = rs1.getInt("scene_time");
			int scene_id = rs1.getInt("scene_id");

			Scene scene = new Scene(scene_id, scene_name, city, scene_intro,
					scene_score, scene_date, scene_time);
			// scene.setId(scene_id);
			schedule.addScene(scene);
		}
		ArrayList<Schedule> arr = new ArrayList<Schedule>(schedules.values());

		ArrayList<Object> arraylist = new ArrayList<Object>();

		for (int i = 0; i < arr.size(); i++) {
			Object object = (Object) arr.get(i);
			arraylist.add(object);
		}

		return arraylist;
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
