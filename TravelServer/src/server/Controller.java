package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import dao.CityTableAccess;
import dao.SceneTableAccess;
import dao.ScheduleSceneTableAccess;
import dao.ScheduleTableAccess;
import dao.UserTableAccess;
import model.City;
import model.IdPasswordCombo;
import model.Scene;
import model.SceneIDCountCombo;
import model.Schedule;
import model.ScheduleUseridCombo;
import model.User;

/*One controller is responsible for one connection to app. The controller communicate with app by some
 * simple protocals we made. Generally, the app would send a number, which indicates the object the app
 * will send next. After transmission, the server will response to indicate whether he has successfully do
 * the work, whether store an object into database, or load objects from database.*/
public class Controller implements Runnable {
	private static final String DBDRIVER = "org.gjt.mm.mysql.Driver";
	private static final String DBURL = "jdbc:mysql://localhost:3306/";
	private static final String DBUSER = "root";
	private static final String DBPASS = "";

	private Socket socket;

	private ObjectOutputStream sendout;
	private ObjectInputStream receive;

	public Controller(Socket socket) {
		this.socket = socket;

		try {
			sendout = new ObjectOutputStream(socket.getOutputStream());
			receive = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.err.println("wrong in create sendout and sendin");
		}
	}

	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			System.err.println("error when close the socket");
		}
	}

	@Override
	public void run() {
		try {
			while (true) {
				try {
					sendout.reset();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				int num = 0;
				try {
					num = (int) receive.readObject();
					// System.out.println("the num : " + num);
				} catch (ClassNotFoundException | IOException e) {
					System.out.println("droped");
					close();
					break;
				}
				/* user */
				if (num == 11 || num == 12) {
					UserTableAccess usrdao = null;

					usrdao = new UserTableAccess(DBDRIVER, DBURL, DBUSER,
							DBPASS);

					/* insert */
					if (num == 11) {
						try {
							// System.out.println("here in insertion");
							User user = (User) receive.readObject();
							boolean isSuccess = usrdao.insert(user);
							// System.out.println("the issuccess is : " +
							// isSuccess);
							if (isSuccess) {
								sendout.writeObject(1);
							} else {
								sendout.writeObject(0);
							}

							usrdao.close();
						} catch (ClassNotFoundException | IOException e) {
							System.err.println("error when receive user class");
							close();
							usrdao.close();
							break;
						}
					} else { /* load */
						IdPasswordCombo combo = null;
						try {
							combo = (IdPasswordCombo) receive.readObject();
							// System.out.println("id + " + combo.getId());
						} catch (ClassNotFoundException | IOException e) {
							System.err
									.println("fail to trans to idpasswordcombo");
						}
						User usr = null;
						try {
							Object obj = (Object) combo;

							Object object = usrdao.load(obj);
							usr = (User) object;
						} catch (SQLException e) {
							System.err.println("sql error");
						}
						try {
							sendout.writeObject(usr);
							if (usr == null) {
								sendout.writeObject("Fall to load");
							} else {
								sendout.writeObject("Received");
							}

						} catch (IOException e) {
							System.err.println("sendout error");
							close();
							usrdao.close();
							break;
						}

						usrdao.close();
					}

				}
				/* update the user table */
				if (num == 13) {
					UserTableAccess userdao = new UserTableAccess(DBDRIVER,
							DBURL, DBUSER, DBPASS);

					try {
						User user = (User) receive.readObject();
						boolean isSuccess = userdao.update(user);
						System.out.println("Update User");
						if (isSuccess) {
							sendout.writeObject("Received");
						} else {
							sendout.writeObject("Fall to insert");
						}

						userdao.close();
					} catch (ClassNotFoundException | IOException e) {
						System.err.println("error when receive user class");
						close();
						userdao.close();
						break;
					}
				}
				/* insert city (by administrator) */
				if (num == 21) {
					CityTableAccess citydao = null;

					citydao = new CityTableAccess(DBDRIVER, DBURL, DBUSER,
							DBPASS);

					try {
						City city = (City) receive.readObject();
						boolean isSuccess = citydao.insert(city);
						if (isSuccess) {
							sendout.writeObject("Received");
						} else {
							sendout.writeObject("Fall to insert");
						}

						citydao.close();
					} catch (ClassNotFoundException | IOException e) {
						System.err.println("error when receive user class");
						close();
						citydao.close();
						break;
					}
				}
				/* load all the city (when user log in) */
				if (num == 22) {
					CityTableAccess citydao = null;

					citydao = new CityTableAccess(DBDRIVER, DBURL, DBUSER,
							DBPASS);

					try {
						ArrayList<City> arraylist = null;
						try {
							Object obj = null;
							Object object = citydao.load(obj);

							arraylist = (ArrayList<City>) object;

							sendout.writeObject(arraylist);
						} catch (SQLException e) {
							System.err.println("sqlexception");
						}
						if (arraylist != null) {
							sendout.writeObject("Received");
						} else {
							sendout.writeObject("Fall to insert");
						}

						citydao.close();
					} catch (IOException e) {
						System.err.println("error when receive user class");
						close();
						citydao.close();
						break;
					}
				}
				/* insert scene (by administrator) */
				if (num == 31) {
					SceneTableAccess scenedao = null;

					scenedao = new SceneTableAccess(DBDRIVER, DBURL, DBUSER,
							DBPASS);

					try {
						Scene scene = (Scene) receive.readObject();
						boolean isSuccess = scenedao.insert(scene);
						if (isSuccess) {
							sendout.writeObject("Received");
						} else {
							sendout.writeObject("Fall to insert");
						}

						scenedao.close();
					} catch (ClassNotFoundException | IOException e) {
						System.err.println("error when receive user class");
						close();
						scenedao.close();
						break;
					}
				}
				/*
				 * load all the scene of a selected city (when user submit the
				 * city)
				 */
				if (num == 32) {
					SceneTableAccess scenedao = null;

					scenedao = new SceneTableAccess(DBDRIVER, DBURL, DBUSER,
							DBPASS);
					City city = null;
					try {
						city = (City) receive.readObject();
						System.out.println("the city receive "
								+ city.getCityID() + " " + city.getName());
					} catch (ClassNotFoundException | IOException e1) {
						System.err.println("io exception");
						close();
						scenedao.close();
						break;
					}
					try {
						ArrayList<Scene> arraylist = null;

						try {
							Object obj = (Object) city;
							Object object = scenedao.load(obj);

							arraylist = (ArrayList<Scene>) object;

							sendout.writeObject(arraylist);
						} catch (SQLException e) {
							System.err.println("sqlexception");
						}
						if (arraylist != null) {
							sendout.writeObject("Received");
						} else {
							sendout.writeObject("Fall to insert");
						}

						scenedao.close();
					} catch (IOException e) {
						System.err.println("error when receive user class");
						close();
						scenedao.close();
						break;
					}
				}
				/* insert a schedule */
				if (num == 41) {
					ScheduleTableAccess scheduledao = null;

					scheduledao = new ScheduleTableAccess(DBDRIVER, DBURL,
							DBUSER, DBPASS);
					ScheduleUseridCombo schedulecombo = null;
					try {
						schedulecombo = (ScheduleUseridCombo) receive.readObject();
						System.err.println("After getting schedule: " + schedulecombo);
						boolean isSuccess = scheduledao.insert(schedulecombo);
						if (isSuccess) {
							sendout.writeObject("Received");
						} else {
							sendout.writeObject("Fall to insert");
						}

						scheduledao.close();
					} catch (ClassNotFoundException | IOException e) {
						System.err.println("error when receive user class");
						close();
						scheduledao.close();
						break;
					}
				}
				/* load all the schedules */
				if (num == 42) {
					ScheduleTableAccess scheduledao = null;

					scheduledao = new ScheduleTableAccess(DBDRIVER, DBURL,
							DBUSER, DBPASS);
					ArrayList<Schedule> arraylist = null;
					ArrayList<Object> arraylist_obj = null;

					try {
						String userid = (String) receive.readObject();
						Object obj = (Object) userid;

						try {
							Object object = scheduledao.load(obj);

							arraylist = (ArrayList<Schedule>) object;

							System.out
									.println("Schedules: " + arraylist.size());
							for (int i = 0; i < arraylist.size(); i++) {
								Schedule schedule = (Schedule) arraylist.get(i);
								System.out.println("name: "
										+ schedule.getName());
							}
							sendout.writeObject(arraylist);
						} catch (SQLException e) {
							System.err.println("load error");
						}
						if (arraylist != null) {
							sendout.writeObject("Received");
						} else {
							sendout.writeObject("Fall to insert");
						}

						scheduledao.close();
					} catch (ClassNotFoundException | IOException e) {
						System.err.println("error when receive user class");
						close();
						scheduledao.close();
						break;
					}
				}
				/*load four most famous cites, which is calculated from the times emerge in schedules. Then send them
				 * to the app*/
				if (num == 51) {
					ScheduleSceneTableAccess schedulescenedao = null;
					SceneTableAccess scenedao = null;
					
					schedulescenedao = new ScheduleSceneTableAccess(DBDRIVER, DBURL,
							DBUSER, DBPASS);
					scenedao = new SceneTableAccess(DBDRIVER, DBURL,
							DBUSER, DBPASS);
					
					ArrayList<SceneIDCountCombo> arraylist = null;
					ArrayList<Scene> scenelist = new ArrayList<Scene>();
					try {
						arraylist = schedulescenedao.getSceneCount();
						Collections.sort(arraylist, new Comparator<SceneIDCountCombo>() {
							public int compare (SceneIDCountCombo combo1, SceneIDCountCombo combo2) {
								int i1 = combo1.getCount();
								int i2 = combo2.getCount();
								return i2 - i1;
							}
						});
						
						System.out.print("after sort : ");
						
						try {

							int max = arraylist.size() > 4 ? 4 : arraylist.size();
							for (int i = 0; i < max; i++) {
								//scenelist.add(scenedao.getSceneName(arraylist.get(i).getSceneID()));
								scenelist.add(scenedao.getScenebyid(arraylist.get(i).getSceneID()));
							}
							
							for (int i = 0; i < max; i++) {
								System.out.println(scenelist.get(i).toString());
							}
							
							sendout.writeObject(scenelist);
							schedulescenedao.close();
							scenedao.close();
						} catch (IOException e) {
							System.err.println("wrong when send out");
						}
					} catch (SQLException e) {
						System.err.println("wrong in recommendation");
					}
				}
			}
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
