package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import model.City;
import model.Scene;

/*The TableCreator class is used to create the TravelAssistance Database, and the five tables in it.
 * They are user table, city table, scene table, schedule table, schedul_scene table, respectively.
 * After created, we load some cities and scenes into the database. The class should run at first*/
public class TableCreator {

	private static final String DBDRIVER = "org.gjt.mm.mysql.Driver";
	private static final String DBURL = "jdbc:mysql://localhost:3306/";
	private static final String DBUSER = "root";
	private static final String DBPASS = "";

	Connection conn;
	Statement statement;

	/* The method is used to create the TravelAssistance database */
	public void link_sql() throws Exception {
		Class.forName(DBDRIVER);
		conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

		System.out.println("create database");
		statement = conn.createStatement();

		String drop = "drop database if exists TravelAssistance";
		statement.executeUpdate(drop);

		String sql = "CREATE DATABASE IF NOT EXISTS TravelAssistance";
		statement.executeUpdate(sql);

		sql = "USE TravelAssistance";
		statement.executeUpdate(sql);

	}

	/* Add some schedule manually to make the recommendation system work. */
	public void initScheduleSample() throws Exception {
		// Class.forName(DBDRIVER);
		// conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

		System.out.println("init Schedule Sample");
		statement = conn.createStatement();

		String sql = "insert into user(id, password) values(\"1\", \"TooHardToGuess\")";
		statement.executeUpdate(sql);

		sql = "insert into schedule(cityName, userID) values(\"Houston\", \"1\")";
		statement.executeUpdate(sql);

		sql = "insert into schedule_scene(scheduleID, sceneID) values(1, 1)";
		statement.executeUpdate(sql);

		sql = "insert into schedule_scene(scheduleID, sceneID) values(1, 2)";
		statement.executeUpdate(sql);

		sql = "insert into schedule_scene(scheduleID, sceneID) values(1, 3)";
		statement.executeUpdate(sql);

		sql = "insert into schedule_scene(scheduleID, sceneID) values(1, 4)";
		statement.executeUpdate(sql);

	}

	/* Create corresponding table indicated bythe filename */
	public void createAutoTable(String filename) throws IOException,
			SQLException {
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			System.err.println("cannot find file");
		}

		String content = null;
		StringBuffer sb = new StringBuffer();
		while ((content = br.readLine()) != null) {
			sb.append(content);
		}
		// System.out.println(sb.toString());
		statement.executeUpdate(sb.toString());
	}

	/* Insert some city and scene sample manually */
	public void insertSample() {

		City city1 = new City(
				0,
				"Houston",
				"US",
				"US Houston is the most populous city in Texas,"
						+ "and the fourth most populous city in the United States."
						+ " With a census-estimated 2013 population of 2.2 million people within a land area of "
						+ "599.6 square miles Houston is the largest city in the Southern United States,"
						+ " the seat of Harris County, and fifth-most populated metropolitan area in the United States.",
				9.8);
		City city2 = new City(
				0,
				"Las Vegas",
				"US",
				"US Las Vegas, officially the City of Las Vegas and often known as simply Vegas,"
						+ " is a city in the United States, the most populous city in the state of Nevada,"
						+ " the county seat of Clark County, and the city proper of the Las Vegas Valley.",
				9.9);
		City city3 = new City(
				0,
				"Los Angeles",
				"US",
				" Los Angeles is the second largest city in the United States in terms of population and one of the largest in terms of area."
						+ " It is the center of a five-county metropolitan area and is considered the prototype of the future metropolis—a city on the cutting edge of all of the advantages "
						+ "and the problems of large urban areas.", 9.9);
		City city4 = new City(
				0,
				"San Francisco",
				"US",
				"The term melting pot is used to describe many American cities and towns. This is indeed true for San Francisco,"
						+ " one of the few truly international cities in the United States. The neighborhoods are varied, yet each features a cohesiveness as unique as its inhabitants."
						+ " Rows of elegant houses, the famous cable cars, clusters of ethnic neighborhoods, and the colorful waterfront all add to the distinctive international flavor of the city. "
						+ "Nearly half of those who live in the Bay Area were born outside of the United States or have at least one nonnative parent. ",
				9.9);
		City city5 = new City(
				0,
				"New York",
				"US",
				"US  Often called New York City or the City of New York to distinguish it from the State of New York, of which it is a part"
						+ " – is the most populous city in the United States and the center of the New York metropolitan area, the premier gateway for legal immigration to the United States."
						+ " and one of the most populous urban agglomerations in the world.",
				9.9);
		City city6 = new City(
				0,
				"Pittsburgh",
				"US",
				"US Pittsburgh is the second-largest city in the U.S. state of Pennsylvania with a population of 305,842 and the county seat of Allegheny County. ",
				9.5);
		CityTableAccess citydao = new CityTableAccess(DBDRIVER, DBURL, DBUSER,
				DBPASS);
		citydao.insert(city1);
		citydao.insert(city2);
		citydao.insert(city3);
		citydao.insert(city4);
		citydao.insert(city5);
		citydao.insert(city6);
		citydao.close();
		SceneTableAccess scenedao = new SceneTableAccess(DBDRIVER, DBURL,
				DBUSER, DBPASS);
		Scene scene1 = new Scene(
				0,
				"Houston Museum",
				city1,
				"The Houston Museum of Natural Science is a science museum located on the northern border of Hermann Park in Houston,"
						+ " Texas, USA.", 92.0, 20141012, 160000);
		Scene scene2 = new Scene(
				0,
				"Space Center",
				city1,
				"Space Center Houston is the official visitor center of the Lyndon B. Johnson Space Center—the National Aeronautics and Space Administration's center"
						+ " for human spaceflight activities—located in Houston, Texas.",
				90.0, 20141012, 180000);
		Scene scene3 = new Scene(
				0,
				"Houston Zoo",
				city1,
				"The Houston Zoo is a 55-acre zoological park located within Hermann Park in Houston,"
						+ "	 Texas, United States. ", 90.0, 20141012, 160000);
		Scene scene4 = new Scene(
				0,
				"Downtown Aquarium",
				city1,
				" Downtown Aquarium is a public aquarium and restaurant located in Houston, Texas, United States that was developed from two Houston landmarks:"
						+ " Fire Station No. 1 and the Central Waterworks Building.",
				91.0, 20141012, 160000);
		Scene scene5 = new Scene(
				0,
				"Six Flags AstroWorld",
				city1,
				"Six Flags AstroWorld was a seasonally-operated theme park located on approximately 57 acres of land between Kirby Drive and Fannin Avenue,"
						+ " directly south of Loop 610 in Houston, USA.", 93.0,
				20141012, 160000);
		scenedao.insert(scene1);
		scenedao.insert(scene2);
		scenedao.insert(scene3);
		scenedao.insert(scene4);
		scenedao.insert(scene5);
		scene1 = new Scene(
				0,
				"Bellagio",
				city2,
				"Bellagio is a resort, luxury hotel and casino on the Las Vegas Strip in Paradise, Nevada. It is owned by MGM Resorts International and was built on the site of the demolished"
						+ " Dunes hotel and casino.", 99.0, 20150412, 160000);
		scene2 = new Scene(
				0,
				"Fremont Street",
				city2,
				"The Fremont Street Experience is a pedestrian mall and attraction in downtown Las Vegas, Nevada. The FSE occupies the westernmost five blocks of Fremont Street,"
						+ " including the area known for years.", 92.0,
				20150412, 170000);
		scene3 = new Scene(
				0,
				"The Mirage",
				city2,
				"The Mirage is a 3,044 room Polynesian-themed hotel and casino resort located on the Las Vegas Strip in Paradise, Nevada, United States. The resort was built by developer Steve Wynn"
						+ " and is currently owned and operated by MGM Resorts International.",
				96.0, 20150412, 160000);
		scene4 = new Scene(
				0,
				"The Venetian",
				city2,
				"The Venetian Resort Hotel Casino is a five-diamond luxury hotel and casino resort situated between Harrah's and The Palazzo on the east side of the Las Vegas Strip in Paradise,"
						+ " Nevada, United States, on the site of the old Sands Hotel.",
				97.0, 20150412, 160000);
		scene5 = new Scene(
				0,
				"Red Rock Canyon National Conservation",
				city2,
				"The Red Rock Canyon National Conservation Area in Nevada is an area managed by the Bureau of Land Management as part of its National Landscape Conservation System,"
						+ " and protected as a National Conservation Area.",
				99.0, 20150412, 160000);
		scenedao.insert(scene1);
		scenedao.insert(scene2);
		scenedao.insert(scene3);
		scenedao.insert(scene4);
		scenedao.insert(scene5);

		scene1 = new Scene(
				0,
				"Universal Studios Hollywood",
				city3,
				"Go behind the scenes on the world famous Studio Tour to explore where Hollywood movies are made. Visit the sets of some of your favorite shows and movies, like ABC’s hit series Desperate Housewives or the incredible airline crash from the blockbuster The War of the Worlds. And, survive firsthand the world’s largest 3-D experience,"
						+ " King Kong 360 3-D, created by Peter Jackson.",
				100.0, 20150412, 160000);
		scene2 = new Scene(
				0,
				"Disneyland Park",
				city3,
				"Enter a magical kingdom where you can sail with pirates, explore exotic jungles, meet fairy-tale princesses,"
						+ " dive under the ocean and rocket through the stars- all in the same day!",
				92.0, 20150412, 170000);
		scene3 = new Scene(
				0,
				"Hollywood Walk of Fame",
				city3,
				"The Hollywood Walk of Fame comprises more than 2,500 five-pointed terrazzo and brass stars embedded in thesidewalks along 15 blocks of Hollywood Boulevard and three blocks of Vine Street in Hollywood, California. The stars are permanent public monuments to achievement in the entertainment industry, bearing the names of a mix of actors, musicians, directors, producers,"
						+ " musical and theatrical groups, fictional characters, and others.",
				94.0, 20150412, 160000);
		scene4 = new Scene(
				0,
				"Hollywood Sign",
				city3,
				"The Hollywood Sign (formerly the Hollywoodland Sign) is a landmark and American cultural icon located in Los Angeles,California. It is situated on Mount Lee in the Hollywood Hills area of the Santa Monica Mountains."
						+ " The sign overlooksHollywood, Los Angeles.", 90.0,
				20150412, 160000);
		scene5 = new Scene(
				0,
				"Griffith Park",
				city3,
				"Griffith Park is a large municipal park at the eastern end of the Santa Monica Mountains in the Los Feliz neighborhood of Los Angeles, California. The park covers 4,310 acres of land,"
						+ " making it one of the largest urban parks in North America.",
				94.0, 20150412, 160000);
		scenedao.insert(scene1);
		scenedao.insert(scene2);
		scenedao.insert(scene3);
		scenedao.insert(scene4);
		scenedao.insert(scene5);

		scene1 = new Scene(
				0,
				"Golden Gate Bridge",
				city4,
				"The Golden Gate Bridge is a suspension bridge spanning the Golden Gate strait, the mile-wide, three-mile-long channel between San Francisco Bay and the Pacific Ocean. The structure links the U.S. city of San Francisco,"
						+ " on the northern tip of the San Francisco Peninsula, ",
				95.0, 20150412, 160000);
		scene2 = new Scene(
				0,
				"Fisherman's Wharf",
				city4,
				"Fisherman's Wharf is a neighborhood and popular tourist attraction in San Francisco, California. It roughly encompasses the northern waterfront area of San Francisco from Ghirardelli Square or Van Ness Avenue "
						+ "east to Pier 35 or Kearny Street. ", 92.0, 20150412,
				170000);
		scene3 = new Scene(
				0,
				"Alcatraz Island",
				city4,
				"Today, the island's facilities are managed by the National Park Service as part of the Golden Gate National Recreation Area; it is open to tours. Visitors can reach the island by ferry ride from Pier 33, near Fisherman's Wharf, San Francisco. Hornblower Cruises and Events, operating under the name Alcatraz Cruises,"
						+ " is the official ferry provider to and from the island. ",
				93.0, 20150412, 160000);
		scene4 = new Scene(
				0,
				"ChinaTown",
				city4,
				"San Francisco's Chinatown is home to the Chinese Consolidated Benevolent Association (known as the Chinese Six Companies), which is the umbrella organization for local Chinese family and regional associations in Chinatown. It has spawned lodges in other Chinatowns in the late 19th and early 20th centuries,"
						+ " including Chinatown, Los Angeles and Chinatown, Portland.",
				90.0, 20150412, 160000);
		scene5 = new Scene(
				0,
				"golden gate park",
				city4,
				"Golden Gate Park, located in San Francisco, California, United States, is a large urban park consisting of 1,017 acres (412 ha) of public grounds. It is administered by the San Francisco Recreation & Parks Department, which began in 1871 to oversee the development of Golden Gate Park.",
				94.0, 20150412, 160000);
		scenedao.insert(scene1);
		scenedao.insert(scene2);
		scenedao.insert(scene3);
		scenedao.insert(scene4);
		scenedao.insert(scene5);

		scene1 = new Scene(
				0,
				"Empire State Building",
				city5,
				"The Empire State Building is a 102-story skyscraper located in Midtown Manhattan, New York City, on Fifth Avenue between West 33rd and 34th Streets. It has a roof height of 1,250 feet (380 m), and with its antenna spire included, it stands a total of 1,454 feet (443 m) high.",
				97.0, 20150412, 160000);
		scene2 = new Scene(
				0,
				"Central park",
				city5,
				"Central Park is an urban park in the central part of the borough of Manhattan, New York City. It was initially opened in 1857, on 778 acres (315 ha) of city-owned land, later expanding to its current size of 843 acres (341 ha).",
				92.0, 20150412, 170000);
		scene3 = new Scene(
				0,
				"Statue of Liberty",
				city5,
				"The origin of the Statue of Liberty project is sometimes traced to a comment made by French law professor and politician  in mid-1865.",
				93.0, 20150412, 160000);
		scene4 = new Scene(
				0,
				"Times Square",
				city5,
				"Times Square is a major commercial intersection and a neighborhood in Midtown Manhattan, New York City. It is located at the junction of Broadway (now converted into a pedestrian plaza) and Seventh Avenue, and stretches from West 42nd to West 47th Streets. ",
				90.0, 20150412, 160000);
		scene5 = new Scene(
				0,
				"Metropolitan Museum of Art",
				city5,
				"The Metropolitan Museum of Art (colloquially The Met), located in New York City, is the largest art museum in the United States and one of the ten largest in the world.Its permanent collection contains more than two million works,"
						+ " divided among seventeen curatorial departments.",
				94.0, 20150412, 160000);
		scenedao.insert(scene1);
		scenedao.insert(scene2);
		scenedao.insert(scene3);
		scenedao.insert(scene4);
		scenedao.insert(scene5);

		scene1 = new Scene(
				0,
				"Pittsburgh Zoo",
				city6,
				"The Pittsburgh Zoo is one of only six major zoo and aquarium combinations in the United States.",
				90.0, 20150412, 160000);
		scene2 = new Scene(
				0,
				"Mount Washington",
				city6,
				"t is known for its steep hill overlooking the Pittsburgh skyline, which was rated the second most beautiful vista in America by USA Weekend (and the best urban vista).",
				92.0, 20150412, 170000);
		scene3 = new Scene(
				0,
				"Andy Warhol museum",
				city6,
				"The Andy Warhol Museum is located on the North Shore of Pittsburgh, Pennsylvania, in the United States. It is the largest museum in the country dedicated to a single artist.",
				94.0, 20150412, 160000);
		scene4 = new Scene(
				0,
				"National Aviary",
				city6,
				"The National Aviary, located in Pittsburgh, Pennsylvania, is the only independent indoor nonprofit aviary in the United States. It is also the country's largest aviary, and the only accorded honorary National status by the United States Congress.",
				90.0, 20150412, 160000);
		scene5 = new Scene(
				0,
				"PNC Park",
				city6,
				"PNC Park is a baseball park located on the North Shore of Pittsburgh, Pennsylvania. It is the fifth home of the Pittsburgh Pirates, the city's Major League Baseball franchise.",
				90.0, 20150412, 160000);
		scenedao.insert(scene1);
		scenedao.insert(scene2);
		scenedao.insert(scene3);
		scenedao.insert(scene4);
		scenedao.insert(scene5);

		scenedao.close();

		UserTableAccess userdao = new UserTableAccess(DBDRIVER, DBURL, DBUSER,
				DBPASS);

	}

	/* Close the connection when finish */
	public void closeConn() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		try {
			TableCreator talbecreator = new TableCreator();
			talbecreator.link_sql();
			talbecreator.createAutoTable("city.sql");
			talbecreator.createAutoTable("scene.sql");
			talbecreator.createAutoTable("user.sql");
			talbecreator.createAutoTable("schedule.sql");
			talbecreator.createAutoTable("schedule_scene.sql");
			talbecreator.insertSample();
			talbecreator.initScheduleSample();
			talbecreator.closeConn();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
