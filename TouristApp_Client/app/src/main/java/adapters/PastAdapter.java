package adapters;

/**
 * Created by chosen12 on 15/4/29.
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

import com.example.vincent.user_interface_tourist.Attraction;
import com.example.vincent.user_interface_tourist.R;

import java.util.ArrayList;

import model.Scene;

/**
 * Created by CMH on 4/3/15.
 */
/*The pastAdapter is used to show the list of scenes which a schedule contains in the PastPlan Activity*/
public class PastAdapter extends BaseAdapter implements ListAdapter {
    /* The Scene ArrayList for the past plans */
    private ArrayList<Scene> list = new ArrayList<Scene>();
    private Context context;

    public PastAdapter(ArrayList<Scene> list, Context context) {
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
        //return list.get(pos).getId();
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.single_past, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.pastScene);
        listItemText.setText(list.get(position).getName());

        //Handle buttons and add onClickListeners
        Button deleteBtn = (Button)view.findViewById(R.id.seePastScene);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                Intent intent = new Intent(context, Attraction.class);
                intent.putExtra("scene", list.get(position));
                context.startActivity(intent);
            }
        });

        return view;
    }
}
