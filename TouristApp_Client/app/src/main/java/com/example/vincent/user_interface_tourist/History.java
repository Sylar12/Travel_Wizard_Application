package com.example.vincent.user_interface_tourist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import adapters.HistoryAdapter;
import model.Schedule;
import model.User;
import remote.Client;

/*The history activity load all the schedules made by the user and show them*/
public class History extends ActionBarActivity {

    Client client = Client.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        User currUser = client.getCurrentUser();
        if(currUser==null) {
            System.out.println("NO current user!");
        }
        String currUserID = currUser.getUserID();
        System.out.println("currUserID: "+currUserID);

        ArrayList<Schedule> scheduleList = client.loadSchedules(currUserID);

        if(scheduleList==null) {
            System.out.println("schedulelist is null");
        }

        HistoryAdapter adapter = new HistoryAdapter(scheduleList, this);

        ListView lView = (ListView) findViewById(R.id.historyList);
        lView.setAdapter(adapter);

        Button newSchedule= (Button) findViewById(R.id.newSchedule);

        newSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(History.this, Destination.class);
                startActivity(intent);
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history, menu);
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
                Intent intent = new Intent(History.this, LogIn.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.action_viewProfile:
                Toast.makeText(getApplicationContext(), "View Profile!!", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(History.this, Profile.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
