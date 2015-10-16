package com.example.vincent.user_interface_tourist;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

import model.City;
import model.Scene;
import remote.Client;

/*The Destination activity give recommend scenes to the user. Besides, it allows the user to
* input a destination*/
public class Destination extends ActionBarActivity {

    // List view
    private ListView lv;

    // Listview Adapter
    ArrayAdapter<String> adapter;

    // Search EditText
    EditText inputSearch;
    Client client = Client.getInstance();

    private Button famousScene1;
    private Button famousScene2;
    private Button famousScene3;
    private Button famousScene4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);

        famousScene1 = (Button)findViewById(R.id.relativeScene1);
        famousScene2 = (Button)findViewById(R.id.relativeScene2);
        famousScene3 = (Button)findViewById(R.id.relativeScene3);
        famousScene4 = (Button)findViewById(R.id.relativeScene4);


        final ArrayList<Scene> famousscenes = client.getFamousScene();
        famousScene1.setText(famousscenes.get(0).getName() + ", " + famousscenes.get(0).getCity().getName());
        famousScene2.setText(famousscenes.get(1).getName() + ", " + famousscenes.get(1).getCity().getName());
        famousScene3.setText(famousscenes.get(2).getName() + ", " + famousscenes.get(2).getCity().getName());
        famousScene4.setText(famousscenes.get(3).getName() + ", " + famousscenes.get(3).getCity().getName());



        // See the Details of the scene
        famousScene1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Destination.this, Attraction.class);
                intent.putExtra("scene", famousscenes.get(0));
                Destination.this.startActivity(intent);
            }
        });

        famousScene2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Destination.this, Attraction.class);
                intent.putExtra("scene", famousscenes.get(1));
                Destination.this.startActivity(intent);
            }
        });

        famousScene3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Destination.this, Attraction.class);
                intent.putExtra("scene", famousscenes.get(2));
                Destination.this.startActivity(intent);
            }
        });

        famousScene4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Destination.this, Attraction.class);
                intent.putExtra("scene", famousscenes.get(3));
                Destination.this.startActivity(intent);
            }
        });

        // Listview Data
        final ArrayList<City> cities = client.getCities();
        if (cities == null) {
            System.out.println("city is null\n");
        }
        final int cityNum = cities.size();
        final String[] cityViews = new String[cityNum];

        for(int i=0; i<cityNum; i++) {
            cityViews[i] = cities.get(i).toView();
        }
                /* {"Mountain View, AR, US", "Mountain View, CA, US", "Mountain View, HI, US",
                "Mountain View, MO, US", "Mountain View, OK, US"}; */

        // Set autocomplete adapter

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, cityViews);

        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.inputSearch);

        textView.setAdapter(adapter);

        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inputSearch = (EditText) findViewById(R.id.inputSearch);
                String place = inputSearch.getText().toString();

                Intent intent = new Intent(Destination.this, CityAttraction.class);
                intent.putExtra("city", place);

                int findID = -1;

                for(int i=0; i<cityNum; i++) {
                    if(cityViews[i].equals(place)) {
                        System.out.println("City found!");
                        findID = i;
                        break;
                    }
                }

                if(findID == -1) {
                    return;
                }

                client.loadScene(cities.get(findID));
                Destination.this.startActivity(intent);
            }

        });



        /*
        inputSearch = (EditText) findViewById(R.id.inputSearch);

        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                MainActivity.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
        */

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_destination, menu);
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
                Intent intent = new Intent(Destination.this, LogIn.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.action_viewProfile:
                Toast.makeText(getApplicationContext(), "View Profile!!", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(Destination.this, Profile.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

