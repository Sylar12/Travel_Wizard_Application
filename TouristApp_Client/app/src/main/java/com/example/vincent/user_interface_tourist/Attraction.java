package com.example.vincent.user_interface_tourist;



import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.net.URLEncoder;
import java.util.ArrayList;
import local.NearbySearch;
import local.SimplePlace;
import local.TextSearch;
import model.Scene;
import remote.Client;

/* Attraction Page, for displaying the information of the single attraction, which contains the
* introduction loaded from database, the name of hotels and restaurants nearby.*/
public class Attraction extends ActionBarActivity {

    private Button AddToSchedule;
    private Button back;
    private ImageButton show;
    private ImageButton hide;
    private Scene scene;
    private Client client = Client.getInstance();

    private TextView sceneName;
    private TextView tview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction);


        sceneName = (TextView) findViewById(R.id.ScenicSpotText);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            Object sceneObj = extras.get("scene");
            scene = (Scene)sceneObj;

            sceneName.setText(scene.getName());
            tview = (TextView) findViewById(R.id.IntroductionContent);
            tview.setText(scene.getIntroduction());



            String searchText = scene.getName();
            SimplePlace simplePlace = null;
            try {
                simplePlace = (new TextSearch(URLEncoder.encode(searchText, "UTF-8"))).execute().get();
            } catch(Exception e) {
                e.printStackTrace();
                System.out.println("Error finding surrounding places!");
            }
            if(simplePlace==null) {
                System.out.println("Simple Place is null!");
            }
            else {
                System.out.println("SimplePlace is\n" + simplePlace);
            }

            // Search for nearby restaurants of the attraction
            try {
                (new NearbySearch(simplePlace.getLatitude(), simplePlace.getLongitude(), 3000.0, "restaurant", this)).execute();
            } catch(Exception e) {
                e.printStackTrace();
                System.out.println("Error finding surrounding places!");
            }

            try {
                (new NearbySearch(simplePlace.getLatitude(), simplePlace.getLongitude(), 3000.0, "lodging", this)).execute();
            } catch(Exception e) {
                e.printStackTrace();
                System.out.println("Error finding surrounding places!");
            }
        }
        addListenerOnButton();
    }



    private void addListenerOnButton() {
        AddToSchedule = (Button) findViewById(R.id.AddToSchedule);
        back = (Button) findViewById(R.id.back);
        AddToSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Scene> currSchedScene = client.getScheduleScene();
                boolean flag = false;
                String sceneName = scene.getName();
                for (int i=0; i<currSchedScene.size(); i++) {
                    if(sceneName.equals(currSchedScene.get(i).getName())) {
                        flag = true;
                        break;
                    }
                }

                if(flag) {
                    Toast.makeText(v.getContext(), "This scene is already added to schedule", Toast.LENGTH_LONG).show();
                }
                else {
                    System.out.println("currScheScene length: "+currSchedScene.size());
                    currSchedScene.add(scene);
                    Toast.makeText(v.getContext(), "Scene added to schedule!", Toast.LENGTH_LONG).show();
                }

                Toast.makeText(getApplicationContext(), "Item added to schedule!", Toast.LENGTH_SHORT).show();
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        show = (ImageButton) findViewById(R.id.show);
        hide = (ImageButton) findViewById(R.id.hide);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Show button");
                show.setVisibility(View.INVISIBLE);
                hide.setVisibility(View.VISIBLE);
                tview.setMaxLines(Integer.MAX_VALUE);
            }
        });

        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Hide button");
                hide.setVisibility(View.INVISIBLE);
                show.setVisibility(View.VISIBLE);
                tview.setMaxLines(1);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_attraction, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), "Setting!!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_logOff:
                Toast.makeText(getApplicationContext(), "Log Off!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Attraction.this, LogIn.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.action_viewProfile:
                Toast.makeText(getApplicationContext(), "View Profile!!", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(Attraction.this, Profile.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
