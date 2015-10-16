package model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import util.SceneComparator;

/**
 * Created by vincent on 15/4/9.
 */
/*The schedule model is used to describe the basic information stored in the database. It has its own id, a list of
 * scenes which belong to it, and some schedule Introduction written by the user*/
public class Schedule implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 7889214152055966403L;
    private String Name;
    private ArrayList<Scene> sceneOnScedule = new ArrayList<Scene>();
    private String scheduleIntro;   // The overall introduction of the trip
    private Date startDate;
    private Date endDate;
    private ArrayList<City> cityRecommended;    // The recommended cities along the trip

    public Schedule(String cityName, ArrayList<Scene> scenes) {
        this.Name = cityName;
        if(scenes!=null) {
            this.sceneOnScedule = scenes;
        }
    }

    public ArrayList<Scene> getCityOnScedule() {
        return sceneOnScedule;
    }

    public String getName() {
        return Name;
    }

    public String getScheduleIntro() {
        return scheduleIntro;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void addScene(Scene scene) {
        this.sceneOnScedule.add(scene);
    }

    public void sortSceneByTime() {
        Collections.sort(this.sceneOnScedule, new SceneComparator());
    }

    public int getSceneNum() {
        return sceneOnScedule.size();
    }

    public Scene getScene(int index) {
        return sceneOnScedule.get(index);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "sceneOnScedule=" + sceneOnScedule +
                ", scheduleIntro='" + scheduleIntro + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", cityRecommended=" + cityRecommended +
                '}';
    }
}
