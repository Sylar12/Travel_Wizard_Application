package local;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vincent on 15/4/29.
 */
/*Review class is used to load the rating and comments information from different people about
* a certain hotel or restaurant*/
public class Review {
    private double score;
    private String text;

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Review{" +
                "score=" + score +
                ", text='" + text + '\'' +
                '}';
    }

    static Review getObjectFromJson(JSONObject jsonObject) {
        try {
            Review result = new Review();

            result.setScore(jsonObject.getDouble("rating"));
            result.setText(jsonObject.getString("text"));

            return result;
        } catch (JSONException ex) {
            Logger.getLogger(NearbyPlace.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


}
