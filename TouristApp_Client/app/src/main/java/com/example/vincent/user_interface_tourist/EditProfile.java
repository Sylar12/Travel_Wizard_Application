package com.example.vincent.user_interface_tourist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import model.User;
import remote.Client;

/*The EditProfile class is used to edit profile when the user click the button edit*/
public class EditProfile extends ActionBarActivity {

    private TextView IDEditText;
    private EditText PasswordEditText;
    private EditText NameEditText;
    private EditText AgeEditText;
    private Spinner SexEditText;
    private EditText EmailEditText;
    private EditText PhoneEditText;
    private EditText AddressEditText;
    private Spinner CountryEditText;

    private Button SaveButton;
    private Boolean temp = true;

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
        setContentView(R.layout.activity_edit_profile);

        IDEditText = (TextView) findViewById(R.id.IDEditText);
        PasswordEditText = (EditText) findViewById(R.id.PasswordEditText);
        NameEditText = (EditText) findViewById(R.id.NameEditText);
        AgeEditText = (EditText) findViewById(R.id.AgeEditText);
        SexEditText = (Spinner) findViewById(R.id.SexEditText);
        EmailEditText = (EditText) findViewById(R.id.EmailEditText);
        PhoneEditText = (EditText) findViewById(R.id.PhoneEditText);
        AddressEditText = (EditText) findViewById(R.id.AddressEditText);
        CountryEditText = (Spinner) findViewById(R.id.CountryEditText);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            IDEditText.setText(extras.getString("ID"));
            PasswordEditText.setText(extras.getString("Password"));
            NameEditText.setText(extras.getString("Name"));
            AgeEditText.setText(extras.getString("Age"));
            EmailEditText.setText(extras.getString("Email"));
            PhoneEditText.setText(extras.getString("Phone"));
            AddressEditText.setText(extras.getString("Address"));
            ArrayAdapter myAdap = (ArrayAdapter) CountryEditText.getAdapter();
            int spinnerPosition = myAdap.getPosition(extras.getString("Country"));
            CountryEditText.setSelection(spinnerPosition);

            ArrayAdapter myAdap1 = (ArrayAdapter) SexEditText.getAdapter();
            int spinnerPosition1 = myAdap1.getPosition(extras.getString("Sex"));
            SexEditText.setSelection(spinnerPosition1);
            //CountryEditText.setText(extras.getString("Country"));
        }

        addListenerOnButton();

    }

    private void addListenerOnButton() {
        SaveButton = (Button) findViewById(R.id.SaveButton);

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ID = IDEditText.getText().toString();
                Password = PasswordEditText.getText().toString();
                Name = NameEditText.getText().toString();
                Age = AgeEditText.getText().toString();
                Sex = SexEditText.getSelectedItem().toString();
                Email = EmailEditText.getText().toString();
                Phone = PhoneEditText.getText().toString();
                Address = AddressEditText.getText().toString();
                Country = CountryEditText.getSelectedItem().toString();

                int Age_int = Integer.parseInt(Age);
                int Sex_int = 0;
                if (Sex.equals("Male")) {
                    Sex_int = 0;
                } else {
                    Sex_int = 1;
                }

                User user = new User(ID, Password, Name, Age_int, Sex_int, Email, Phone, Address, Country);
                client.updateUser(user);
                client.updateCurrentUser(user);

                //temp check

                if (temp == true) {
                    Toast.makeText(getApplicationContext(), "Successfully Saved!", Toast.LENGTH_SHORT).show();

                    Intent profileIntent = new Intent(EditProfile.this, Profile.class);

                    startActivity(profileIntent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Update Failed!", Toast.LENGTH_SHORT).show();

                    return;
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
