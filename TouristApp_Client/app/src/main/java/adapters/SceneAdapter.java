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
import model.Scene;
import remote.Client;

/* Scene Adapter, used for showing the available scenes in the selected city */
public class SceneAdapter extends BaseAdapter implements ListAdapter {
    /* The Scene ArrayList */
    private ArrayList<Scene> list = new ArrayList<>();
    private Context context;
    private Client client = Client.getInstance();

    public SceneAdapter(ArrayList<Scene> list, Context context) {
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
            view = inflater.inflate(R.layout.single_scene, null);
        }

        //Handle TextView and display string from your list
        TextView sceneName = (TextView)view.findViewById(R.id.sceneName);
        sceneName.setText(list.get(position).getName());

        TextView sceneScore = (TextView)view.findViewById(R.id.sceneScore);
        sceneScore.setText(Double.toString(list.get(position).getScore()));

        //Handle buttons and add onClickListeners
        Button sceneDetail = (Button)view.findViewById(R.id.sceneDetail);
        Button addScene = (Button)view.findViewById(R.id.addScene);

        // See the Details of the scene
        sceneDetail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Attraction.class);
                intent.putExtra("scene", list.get(position));
                context.startActivity(intent);
            }
        });

        // Add the scene to Current Schedule
        addScene.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ArrayList<Scene> currSchedScene = client.getScheduleScene();
                if(currSchedScene.contains(list.get(position))) {
                    Toast.makeText(context, "This scene is already added to schedule", Toast.LENGTH_LONG).show();
                }
                else {

                    System.out.println("currScheScene length: "+currSchedScene.size());
                    currSchedScene.add(list.get(position));
                    Toast.makeText(context, "Scene added to schedule!", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }


}