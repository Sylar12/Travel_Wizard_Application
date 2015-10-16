package adapters;

/**
 * Created by chosen12 on 15/4/11.
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
import com.example.vincent.user_interface_tourist.CityAttraction;
import com.example.vincent.user_interface_tourist.History;
import com.example.vincent.user_interface_tourist.PastPlan;
import com.example.vincent.user_interface_tourist.R;

import java.util.ArrayList;

import model.City;
import model.Scene;
import model.Schedule;
import remote.Client;

/* History Adapter, used for showing the schedule history */
public class HistoryAdapter extends BaseAdapter implements ListAdapter {
    /* The Schedule ArrayList */
    private ArrayList<Schedule> list = new ArrayList<>();
    private Context context;

    public HistoryAdapter(ArrayList<Schedule> list, Context context) {
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
            view = inflater.inflate(R.layout.single_history, null);
        }

        // Handle TextView and display string from your list
        TextView sceneName = (TextView)view.findViewById(R.id.cityName);
        sceneName.setText(list.get(position).getName());

        // Handle buttons and add onClickListeners
        Button seeButton = (Button)view.findViewById(R.id.seeButton);

        // See details of the Schedule
        seeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PastPlan.class);
                intent.putExtra("schedule", list.get(position));
                context.startActivity(intent);
            }
        });
        return view;
    }

}
