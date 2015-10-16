package model;

import java.io.Serializable;

/**
 * Created by vincent on 15/4/9.
 */
/*The Scene model describe the information of a scene stored in the scene table in the database, which contains its
 * unique scene id, and name, city it belongs to, corresponding introduction and score.*/

 public class Scene implements Serializable {
    private static final long serialVersionUID = -4394482917758318640L;
    private int sceneID;
    private String name;
    private City city;
    private String introduction;
    private double score;   // The overall score of the scene
    private int date;
    private int time;
    private Scene[] nearbyScenes;
    public Scene(int sceneID, String name, City city, String introduction,
                 double score, int date, int time) {
        this.sceneID = sceneID;
        this.name = name;
        this.city = city;
        this.introduction = introduction;
        this.score = score;
        this.date = date;
        this.time = time;
    }

    public int getSceneID() {
        return sceneID;
    }

    public String getName() {
        return name;
    }

    public City getCity() {
        return city;
    }

    public String getIntroduction() {
        return introduction;
    }

    public double getScore() {
        return score;
    }

    public int getDate() {
        return date;
    }

    public int getTime() {
        return time;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append("\n");
        sb.append("CityName: ").append(city).append("\n");
        sb.append("Introduction: ").append(introduction).append("\n");
        sb.append("Score: ").append(score).append("\n");
        sb.append("Date:").append(date).append("\n");
        sb.append("Time:").append(time).append("\n");
        return sb.toString();
    }
}
