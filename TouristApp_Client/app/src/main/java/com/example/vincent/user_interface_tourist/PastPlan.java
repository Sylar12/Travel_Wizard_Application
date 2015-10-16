package com.example.vincent.user_interface_tourist;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import adapters.PastAdapter;
import model.Scene;
import model.Schedule;


public class PastPlan extends ActionBarActivity {
    String content;
    //TextView contentText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_plan);

        Intent intent = getIntent();
        Schedule schedule = (Schedule) intent.getSerializableExtra("schedule");

        ArrayList<Scene> sceneList = schedule.getCityOnScedule();

        //contentText = (TextView) findViewById(R.id.Content);
        //contentText.setText(schedule.toString());


        //instantiate custom adapter
        PastAdapter adapter = new PastAdapter(sceneList, this);

        //handle listview and assign adapter
        ListView lView = (ListView) findViewById(R.id.pastSceneList);
        lView.setAdapter(adapter);




        Button b = (Button) findViewById(R.id.toHistory);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PastPlan.this, History.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_past_plan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        /*
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        */

        switch (id) {
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), "Setting!!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_logOff:
                Toast.makeText(getApplicationContext(), "Log Off!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PastPlan.this, LogIn.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.action_viewProfile:
                Toast.makeText(getApplicationContext(), "View Profile!!", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(PastPlan.this, Profile.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}