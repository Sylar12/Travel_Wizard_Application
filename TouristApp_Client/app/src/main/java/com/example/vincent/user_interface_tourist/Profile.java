package com.example.vincent.user_interface_tourist;



import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import model.User;
import remote.Client;

/*The Profile activity allows the user to view their own profile*/
public class Profile extends ActionBarActivity {

    private TextView IDEditTextResult;
    private TextView PasswordEditTextResult;
    private TextView NameEditTextResult;
    private TextView AgeEditTextResult;
    private TextView SexEditTextResult;
    private TextView EmailEditTextResult;
    private TextView PhoneEditTextResult;
    private TextView AddressEditTextResult;
    private TextView CountryEditTextResult;

    private Button EditButton;
    private Button ConfirmButton;
    private Button HistoryButton;

    private String ID;
    private String Password;
    private String Name;
    private String Age;
    private String Sex;
    private String Email;
    private String Phone;
    private String Address;
    private String Country;

    Client client = Client.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        User user = client.getCurrentUser();


        ID = user.getUserID();
        Password = user.getPassword();
        Name = user.getName();
        Age = Integer.toString(user.getAge());
        Sex = Integer.toString(user.getSex());
        Email = user.getEmail();
        Phone = user.getPhone();
        Address = user.getAddress();
        Country = user.getCountry();

        IDEditTextResult = (TextView) findViewById(R.id.IDEditTextResult);
        PasswordEditTextResult = (TextView) findViewById(R.id.PasswordEditText);
        NameEditTextResult = (TextView) findViewById(R.id.NameEditTextResult);
        AgeEditTextResult = (TextView) findViewById(R.id.AgeEditTextResult);
        SexEditTextResult = (TextView) findViewById(R.id.SexEditTextResult);
        EmailEditTextResult = (TextView) findViewById(R.id.EmailEditTextResult);
        PhoneEditTextResult = (TextView) findViewById(R.id.PhoneEditTextResult);
        AddressEditTextResult = (TextView) findViewById(R.id.AddressEditTextResult);
        CountryEditTextResult = (TextView) findViewById(R.id.CountryEditTextResult);

        IDEditTextResult.setText(ID);
        PasswordEditTextResult.setText("**********");
        NameEditTextResult.setText(Name);
        AgeEditTextResult.setText(Age);
        if (Sex.equals("0")) {
            SexEditTextResult.setText("Male");
        } else {
            SexEditTextResult.setText("Female");
        }
        EmailEditTextResult.setText(Email);
        PhoneEditTextResult.setText(Phone);
        AddressEditTextResult.setText(Address);
        CountryEditTextResult.setText(Country);

        addListenerOnButton();

    }

    private void addListenerOnButton() {
        EditButton = (Button) findViewById(R.id.EditButton);
        ConfirmButton = (Button) findViewById(R.id.ConfirmButton);
        HistoryButton = (Button) findViewById(R.id.HistoryButton);

        EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(Profile.this, EditProfile.class);
                profileIntent.putExtra("ID", ID);
                profileIntent.putExtra("Password", Password);
                profileIntent.putExtra("Name", Name);
                profileIntent.putExtra("Age", Age);
                profileIntent.putExtra("Sex", Sex);
                profileIntent.putExtra("Email", Email);
                profileIntent.putExtra("Phone", Phone);
                profileIntent.putExtra("Address", Address);
                profileIntent.putExtra("Country", Country);

                startActivity(profileIntent);
            }
        });

        ConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(Profile.this, Destination.class);
                startActivity(profileIntent);
            }
        });

        HistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, History.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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
                Intent intent = new Intent(Profile.this, LogIn.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
