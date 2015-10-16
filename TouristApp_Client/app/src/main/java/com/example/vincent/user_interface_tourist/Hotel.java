package com.example.vincent.user_interface_tourist;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import adapters.ReviewAdapter;
import local.DetailSearch;
import local.FindPhoto;
import local.NearbyPlace;
import local.Photo;
import local.PlaceDetail;

/*Shows list of hotel nearby*/
public class Hotel extends ActionBarActivity {
    private TextView hotelName;
    private TextView address;
    private ListView listView;
    private TextView tags;
    private ImageView hotelImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Object sceneObj = extras.get("NearbyPlace");
            NearbyPlace nearbyPlace = (NearbyPlace) sceneObj;
            listView = (ListView) findViewById(R.id.reviewList);
            hotelName = (TextView) findViewById(R.id.hotelName);
            tags = (TextView) findViewById(R.id.tags);
            address = (TextView) findViewById(R.id.address);
            hotelImage = (ImageView) findViewById(R.id.hotelImage);

            hotelName.setText(nearbyPlace.getName());
            address.setText(nearbyPlace.getVicinity());
            try {
                PlaceDetail detail = (new DetailSearch(nearbyPlace.getReference())).execute().get();
                listView.setAdapter(new ReviewAdapter(detail.getReviews(), this));

                String tagString = TextUtils.join(", ", detail.getTypes());
                tags.setText(tagString);

                Photo photo = detail.getPhotos().get(0);
                Drawable drawable = (new FindPhoto(photo)).execute().get();
                System.out.println("Here is drawable:" + drawable);
                hotelImage.setImageDrawable(drawable);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Button b = (Button) findViewById(R.id.back);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hotel, menu);
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
                Intent intent = new Intent(Hotel.this, LogIn.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.action_viewProfile:
                Toast.makeText(getApplicationContext(), "View Profile!!", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(Hotel.this, Profile.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
