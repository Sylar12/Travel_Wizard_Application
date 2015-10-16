package com.example.vincent.user_interface_tourist;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import adapters.PlanAdapter;
import model.Scene;
import model.Schedule;
import model.User;
import remote.Client;

/*The activity shows the scenes a user has added into one scheduler. Different from the ConfirmPage,
* Currentplan allows the user to delete selected scenes */
public class CurrentPlan extends ActionBarActivity {
    private ListView listView1;

    String cityName="";

    Client client = Client.getInstance();
    Schedule schedule;
    String UserID;
    private String scheduleIntro;   // The overall introduction of the trip


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_plan);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cityName = extras.getString("city");
        }

        TextView listItemText = (TextView) this.findViewById(R.id.cityName);
        listItemText.setText(cityName);
        

        ArrayList<Scene> currentScenes = client.getScheduleScene();

        schedule = new Schedule(cityName, currentScenes);
        User currUser = client.getCurrentUser();
        System.out.println(currUser);
        UserID = currUser.getUserID();

        //instantiate custom adapter
        PlanAdapter adapter = new PlanAdapter(currentScenes, this);

        //handle listview and assign adapter
        ListView lView = (ListView) findViewById(R.id.sceneList);
        lView.setAdapter(adapter);

        Button confirmButton = (Button) this.findViewById(R.id.ConfirmButton);
        confirmButton.setOnClickListener(new MyOnClickListener(schedule, UserID));


        Button editButton = (Button) this.findViewById(R.id.EditButton);
        editButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), CityAttraction.class);
                        intent.putExtra("city", cityName);

                        startActivity(intent);
                    }
                }
        );


    }


    public class MyOnClickListener implements View.OnClickListener {

        private Schedule scheduleTemp;
        private String UserIDTemp;

        public MyOnClickListener(Schedule schedule, String UserID) {
            this.scheduleTemp = schedule;
            this.UserIDTemp = UserID;
        }

        @Override
        public void onClick(View v) {
            try {
                client.sendSchedule(scheduleTemp, UserIDTemp);

                Intent intent = new Intent(v.getContext(), ConfirmPage.class);
                intent.putExtra("city", cityName);
                startActivity(intent);
                Toast.makeText(v.getContext(), "Here custom OnClickListener",
                        Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(v.getContext(), "Failed in custom OnClickListener",
                        Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_current_plan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // On menu options selected
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), "Setting!!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_logOff:
                Toast.makeText(getApplicationContext(), "Log Off!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CurrentPlan.this, LogIn.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.action_viewProfile:
                Toast.makeText(getApplicationContext(), "View Profile!!", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(CurrentPlan.this, Profile.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
