package adapters;

/**
 * Created by CMH on 4/3/15.
 */

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vincent.user_interface_tourist.Attraction;
import com.example.vincent.user_interface_tourist.R;

import java.util.ArrayList;

import local.Review;
import model.Scene;
import remote.Client;

/* The ReviewAdapter is used for showing the rating and review information in the destination activity */
public class ReviewAdapter extends BaseAdapter implements ListAdapter {
    /* The Scene ArrayList */
    private ArrayList<Review> list = new ArrayList<>();
    private Context context;

    public ReviewAdapter(ArrayList<Review> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.single_review, null);
        }

        TextView rating = (TextView) view.findViewById(R.id.rating);
        rating.setText(Double.toString(list.get(position).getScore()));

        TextView review = (TextView) view.findViewById(R.id.review);
        review.setText(list.get(position).getText());

        return view;
    }


}