package local;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vincent on 15/4/20.
 */
/*When user select a scene, and want to view details. The NearbyPlace class is invoked to
* provide with user names of the nearby restaurants and hotels.*/
public class NearbyPlace implements Serializable {

    private static final long serialVersionUID = -6062446004647696218L;
    private String id;
    private String icon;
    private String name;
    private String vicinity;
    private double latitude;
    private double longitude;
    private double rating;
    private String reference;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    static NearbyPlace getObjectFromJson(JSONObject jsonObject) {
        try {
            NearbyPlace result = new NearbyPlace();
            JSONObject geometry = (JSONObject) jsonObject.get("geometry");
            JSONObject location = (JSONObject) geometry.get("location");
            result.setLatitude(location.getDouble("lat"));
            result.setLongitude(location.getDouble("lng"));
            result.setIcon(jsonObject.getString("icon"));
            result.setName(jsonObject.getString("name"));
            result.setVicinity(jsonObject.getString("vicinity"));
            result.setId(jsonObject.getString("id"));
            try {
                result.setRating(jsonObject.getDouble("rating"));
            } catch(Exception e) {
                System.out.println("No rating");
                result.setRating(0);
            }
            result.setReference(jsonObject.getString("reference"));
            return result;
        } catch (JSONException ex) {
            Logger.getLogger(NearbyPlace.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String toString() {
        return "NearbyPlace{" +
                "id='" + id + '\'' +
                ", icon='" + icon + '\'' +
                ", name='" + name + '\'' +
                ", vicinity='" + vicinity + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", rating=" + rating +
                ", reference='" + reference + '\'' +
                '}';
    }
}