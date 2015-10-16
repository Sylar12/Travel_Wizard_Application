package adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.example.vincent.user_interface_tourist.R;
import com.example.vincent.user_interface_tourist.Restaurant;
import com.example.vincent.user_interface_tourist.Hotel;
import java.util.ArrayList;
import java.util.List;

import local.NearbyPlace;
import remote.Client;

/**
 * Created by vincent on 15/4/30.
 */
/*NearbyAdapter is used to show the list of nearby place in the activity, which is used to search
* nearby hotels and restaurants*/
public class NearbyAdapter extends BaseAdapter implements ListAdapter {
    /* The NearbyPlace ArrayList */
    private List<NearbyPlace> list;
    private Context context;
    private Class nextClass;

    public NearbyAdapter(List<NearbyPlace> list, Context context, Class nextClass) {
        this.list = list;
        this.context = context;
        this.nextClass = nextClass;
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
            view = inflater.inflate(R.layout.single_nearby, null);
        }

        TextView nearbyName = (TextView) view.findViewById(R.id.nearbyName);
        nearbyName.setText(list.get(position).getName());

        TextView nearbyScore = (TextView) view.findViewById(R.id.nearbyScore);
        nearbyScore.setText("Rating: "+Double.toString(list.get(position).getRating()));

        TextView address = (TextView) view.findViewById(R.id.address);
        address.setText(list.get(position).getVicinity());

        Button b = (Button) view.findViewById(R.id.details);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("From NearbyAdapter, to "+nextClass.getName());
                Intent intent = new Intent(context, nextClass);
                intent.putExtra("NearbyPlace", list.get(position));
                context.startActivity(intent);
            }
        });

        return view;
    }
}
