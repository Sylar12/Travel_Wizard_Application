package remote;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import exceptionHelper.LostConnectException;
import model.*;

/*The client class is used to communicate with the server. We will initiate only one
* client object from the start to end.*/
public class Client {
    private static Client clientInstance=null;
    private Socket clientsocket;

    private ArrayList<City> cities;
    private ArrayList<Scene> scenes;

    private ArrayList<Scene> famousScenes;

    ArrayList<Scene> sceneOnScedule = new ArrayList<Scene>();
    private ArrayList<Schedule> schedulelist;

    private InetAddress host;
    private int port;

    private ObjectOutputStream sendout;
    private ObjectInputStream readin;

    public User currentUser;

    public int isSignupSuccess;

    /*The constructor is used to connect to the server*/
    private Client(int port){

        this.port = port;
        this.connect();

    }

    /*get the client object.We will use only one client object from start to end. If the instance
    * is not constructed, construct it*/
    public static Client getInstance() {
        if(clientInstance!=null) {
            return clientInstance;
        }
        else {
            clientInstance = new Client(44400);
            System.out.println("Client created");
            return clientInstance;
        }
    }

    public void setSendout(ObjectOutputStream sendout) {
        this.sendout = sendout;
    }

    public void setReadin(ObjectInputStream readin) {
        this.readin = readin;
    }

    public boolean repairConnection(LostConnectException le) {
        connect();
        if(clientsocket == null) {
            return false;
        }
        return true;
    }

    /*Connect to the server*/
    private void connect() {
        try {
            clientsocket = (new CreateSocket(this.port, Client.this)).execute().get();
            if(clientsocket == null) {
                System.out.println("Error in creating socket!");
            }
            else {
                System.out.println("Socket is built!");
            }
            System.out.println(clientsocket);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("fail to build a client socket");
        }
    }

    public boolean isConnected() {
        return ((sendout!=null) && (readin!=null));
    }

    /*call the UserSender class to send the information of a new user.*/
    public int sendUser(User user) {
        try {
            System.out.println(user);
            (new UserSender(sendout, readin)).execute(user).get();
            System.out.println("Send user");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("fail to send user");
        }
        System.out.println("the issignupsuccess in client : " + isSignupSuccess);
        return isSignupSuccess;
    }

    public User loadUser(String id, String password) throws LostConnectException {
        if(!isConnected()) {
            throw(new LostConnectException("Socket Lost Connection"));
        }
        try {
            (new UserLoader(sendout, readin)).execute(new IdPasswordCombo(id, password)).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("load user");
        return currentUser;
    }

    /*Use the UserUpdator to update a user's profile*/
    public void updateUser(User user) {
        try {
            (new UserUpdator(sendout, readin)).execute(user);
            System.out.println("update user");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("fail to update user");
        }
    }

    public void sendCity(City city) {
        try {
            (new CitySender(sendout, readin)).execute(city);
            System.out.println("send city");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("fail to send city");
        }
    }

    /*Load all the cities in the database*/
    public void loadCity() {
        try {
            (new CityLoader(sendout, readin)).execute().get();
            System.out.println("load city");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("fail to load city");
        }
    }


    public void sendScene(Scene scene) {
        try {
            (new SceneSender(sendout, readin)).execute(scene);
            System.out.println("send scene");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("fail to send scene");
        }
    }

    /*Use SceneLoader to load scenes which belong to the city selected.*/
    public void loadScene(City city) {
        try {
            (new SceneLoader(sendout, readin)).execute(city).get();
            System.out.println("load scene");
            for (int i = 0; i < scenes.size(); i++) {
                System.out.println(scenes.get(i).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("fail to load scene");
        }
    }

    public void sendSchedule(Schedule schedule, String userid) {
        try {
            sendout.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("in the function sendSchedule" + schedule.toString());
            (new ScheduleSender(sendout, readin, new ScheduleUseridCombo(schedule, userid))).execute();
            System.out.println("send schedule");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("fail to send schedule");
        }
    }

    /*Use SheduleLoader to load all schedules of a user*/
    public ArrayList<Schedule> loadSchedules(String userid) {
        try {
            ScheduleLoader scheLoader = (new ScheduleLoader(sendout, readin));
            scheLoader.execute(userid).get();
            System.out.println("load schedule");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("fail to load schedule");
        }
        return schedulelist;
    }

    public ArrayList<Scene> getFamousScene() {
        try {
            FamousSceneSender famoussender = (new FamousSceneSender(sendout, readin));
            famoussender.execute().get();
            System.out.println("load famous scenes");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("fail to load famous scenes");
        }
        return famousScenes;
    }


    public ArrayList<Scene> getScenes() {
        return scenes;
    }

    public ArrayList<Scene> getScheduleScene() {
        return this.sceneOnScedule;
    }


    public User getCurrentUser() {
        return currentUser;
    }

    public ArrayList<City> getCities() {
        return this.cities;
    }

    public void updateCurrentUser(User user) {
        currentUser = user;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }


    public void setCities(ArrayList<City> arraylist) {
        this.cities = arraylist;
    }


    /*The method is used to get all the scenes in a particular city in the SceneLoader*/
    public void setScenes(ArrayList<Scene> arraylist) {
        this.scenes = arraylist;
        System.out.println("setScenes");
        System.out.println(arraylist);
    }

    /*The method is used to load all the schedules made by the user in the ScheduleLoader*/
    public void setSchedulelist(ArrayList<Schedule> schedulelist) {
        this.schedulelist = schedulelist;
        System.out.println("setSchedulelist");
        System.out.println(schedulelist);
    }

    /*The method is used to get famous scenes from the server, which is used in the FamousSceneSender*/
    public void setFamousScenes(ArrayList<Scene> scenelist) {
        this.famousScenes = scenelist;

    }

    public void setIsSignupSuccess(int i) {
        System.out.println("int i in the client : " + i);
        this.isSignupSuccess = i;
    }

    public ArrayList<Schedule> getSchedulelist() {
        return schedulelist;
    }

    public void clearScheduleList() {
        sceneOnScedule.clear();
    }


}
