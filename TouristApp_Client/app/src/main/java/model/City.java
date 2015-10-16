package model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by vincent on 15/4/9.
 */

/*The city model. The model is used to indicate a city, which has its own unique id in the database, and its name, location,
 * introduction.*/

 public class City implements Serializable {

    private static final long serialVersionUID = 6228327223662352052L;
    private int cityID;
    private String name;
    private String location;// State/Province that this city locates, for example CA, WA etc.
    private String introduction;
    private double score;   // Overall score of the city
    private Scene[] famousScenes;

    public City(int cityID, String name, String location, String introduction, double score) {
        this.cityID = cityID;
        this.name = name;
        this.location = location;
        this.introduction = introduction;
        this.score = score;
    }

    public int getCityID() {
        return cityID;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getIntroduction() {
        return introduction;
    }

    public double getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", introduction='" + introduction + '\'' +
                ", score=" + score +
                ", famousScenes=" + Arrays.toString(famousScenes) +
                '}';
    }

    public String toView() {
        return name+", "+location;
    }

    public void print() {
        System.out.println(toString());
    }

}
