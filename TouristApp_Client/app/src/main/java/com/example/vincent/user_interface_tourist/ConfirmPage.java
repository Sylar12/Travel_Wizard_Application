package com.example.vincent.user_interface_tourist;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import model.Scene;
import remote.Client;

/*The activity shows the result of scenes a user has added into one scheduler*/
public class ConfirmPage extends ActionBarActivity {

    ArrayList<String> list = new ArrayList<String>();
    Client client = Client.getInstance();
    String cityName;
    TextView cityNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_page);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cityName = extras.getString("city");
        }
        cityNameText = (TextView) this.findViewById(R.id.cityName);
        cityNameText.setText(cityName);
        ArrayList<Scene> currentScenes = client.getScheduleScene();
        int len = currentScenes.size();
        for (int i = 0; i < len; i++) {
            list.add(currentScenes.get(i).getName());
        }
        String[] contentarray = new String[list.size()];
        list.toArray(contentarray);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, contentarray);


        //handle listview and assign adapter
        ListView lView = (ListView)findViewById(R.id.sceneList);
        lView.setAdapter(adapter);


        Button b = (Button) findViewById(R.id.seeHistory);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmPage.this, History.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_confirm_page, menu);
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
                Intent intent = new Intent(ConfirmPage.this, LogIn.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.action_viewProfile:
                Toast.makeText(getApplicationContext(), "View Profile!!", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(ConfirmPage.this, Profile.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();  // Always call the superclass method first

        // Save the note's current draft, because the activity is stopping
        // and we want to be sure the current note progress isn't lost.
        System.out.println("fuck you in the on stop");
        client.clearScheduleList();
    }
}
