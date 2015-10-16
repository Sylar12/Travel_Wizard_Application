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

import adapters.SceneAdapter;
import model.Scene;
import remote.Client;

/*The activity shows a list of available scenes in a choose city*/
public class CityAttraction extends ActionBarActivity {

    private String cityName;
    private TextView cityNameText;
    private Client client = Client.getInstance();
    private ArrayList<Scene> sceneList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_attraction);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cityName = extras.getString("city");
            System.out.println("Current city: "+cityName);
        }

        sceneList = client.getScenes();
        cityNameText = (TextView) findViewById(R.id.cityName);
        cityNameText.setText(cityName);

        SceneAdapter adapter = new SceneAdapter(sceneList, this);
        ListView lView = (ListView) findViewById(R.id.sceneList);
        lView.setAdapter(adapter);
        Button submitButton = (Button) this.findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CurrentPlan.class);
                intent.putExtra("city", cityName);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_city_attraction, menu);
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
                Intent intent = new Intent(CityAttraction.this, LogIn.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.action_viewProfile:
                Toast.makeText(getApplicationContext(), "View Profile!!", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(CityAttraction.this, Profile.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
