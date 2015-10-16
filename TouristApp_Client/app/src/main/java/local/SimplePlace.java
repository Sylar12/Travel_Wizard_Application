package local;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vincent on 15/4/29.
 */
/*When we search a scene, the textsearch return simpleplace, which list all the hotels and restaurants
* nearby*/
public class SimplePlace {
    private String id;
    private String icon;
    private String name;
    private String vicinity;
    private double latitude;
    private double longitude;
    private String reference;
    private String[] types;

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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    static SimplePlace getObjectFromJson(JSONObject jsonObject) {
        try {
            SimplePlace result = new SimplePlace();
            JSONObject geometry = (JSONObject) jsonObject.get("geometry");
            JSONObject location = (JSONObject) geometry.get("location");
            result.setLatitude((Double) location.get("lat"));
            result.setLongitude((Double) location.get("lng"));
            result.setIcon(jsonObject.getString("icon"));
            result.setName(jsonObject.getString("name"));
            result.setVicinity(jsonObject.getString("formatted_address"));
            result.setId(jsonObject.getString("id"));
            result.setReference(jsonObject.getString("reference"));

            return result;
        } catch (JSONException ex) {
            Logger.getLogger(NearbyPlace.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String toString() {
        return "SimplePlace{" +
                "id='" + id + '\'' +
                ", icon='" + icon + '\'' +
                ", name='" + name + '\'' +
                ", vicinity='" + vicinity + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", reference='" + reference + '\'' +
                ", types=" + Arrays.toString(types) +
                '}';
    }
}
