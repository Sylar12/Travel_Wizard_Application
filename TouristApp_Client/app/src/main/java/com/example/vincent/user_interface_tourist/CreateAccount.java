package com.example.vincent.user_interface_tourist;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import model.User;
import remote.Client;

/*The CreateAccount activity ask the user to write their information*/
public class CreateAccount extends ActionBarActivity {


    private EditText IDEditText;
    private EditText PasswordEditText;
    private EditText NameEditText;
    private EditText AgeEditText;
    private Spinner SexEditText;
    private EditText EmailEditText;
    private EditText PhoneEditText;
    private EditText AddressEditText;
    private Spinner CountryEditText;

    private Button SubmitButton;

    private String ID;
    private String Password;
    private String Name;
    private String Age;
    private int Age_int=0;
    private String Sex;
    private int Sex_int=0;
    private String Email;
    private String Phone;
    private String Address;
    private String Country;

    Client client = Client.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ID = intent.getStringExtra("ID");
            Name = intent.getStringExtra("Name");
            Age = intent.getStringExtra("Age");
            Sex = intent.getStringExtra("Sex");
            Email = intent.getStringExtra("Email");
            Phone = intent.getStringExtra("Phone");
            Address = intent.getStringExtra("Address");
            Country = intent.getStringExtra("Country");
        }
        else {}

        IDEditText = (EditText) findViewById(R.id.IDEditText);
        PasswordEditText = (EditText) findViewById(R.id.PasswordEditText);
        NameEditText = (EditText) findViewById(R.id.NameEditText);
        AgeEditText = (EditText) findViewById(R.id.AgeEditText);
        SexEditText = (Spinner) findViewById(R.id.SexEditText);
        EmailEditText = (EditText) findViewById(R.id.EmailEditText);
        PhoneEditText = (EditText) findViewById(R.id.PhoneEditText);
        AddressEditText = (EditText) findViewById(R.id.AddressEditText);
        CountryEditText = (Spinner) findViewById(R.id.CountryEditText);

        IDEditText.setText(ID);
        PasswordEditText.setText(Password);
        NameEditText.setText(Name);
        AgeEditText.setText(Age);
        //SexEditText.setText(Sex);

        EmailEditText.setText(Email);
        PhoneEditText.setText(Phone);
        AddressEditText.setText(Address);
        //CountryEditText.setText(Country);

        addListenerOnButton();

    }

    private void addListenerOnButton() {

        SubmitButton = (Button) findViewById(R.id.SubmitButton);
        SubmitButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                ID = IDEditText.getText().toString();
                Password = PasswordEditText.getText().toString();
                Name = NameEditText.getText().toString();
                Age = AgeEditText.getText().toString();
                //Sex = SexEditText.getText().toString();
                Sex = SexEditText.getSelectedItem().toString();
                Email = EmailEditText.getText().toString();
                Phone = PhoneEditText.getText().toString();
                Address = AddressEditText.getText().toString();
                Country = CountryEditText.getSelectedItem().toString();

                String[] InputItem = {ID, Password, Name, Age, Sex, Email, Phone, Address, Country};
                String[] InputItemName = {"ID", "Password", "Name", "Age", "Sex", "Email", "Phone", "Address", "Country"};

                String itemName = "";
                try {
                    for (int i = 0; i<InputItem.length; i++) {

                        if (InputItem[i].trim().length() == 0) {
                            itemName = InputItemName[i];
                            throw new Exception("Incomplete Input!");
                        }
                    }
                } catch (Exception e) {
                    //    e = new Exception("Incomplete Input!");
                    System.out.println(e.getMessage());
                    Toast.makeText(getApplicationContext(), itemName + ":" + "Incomplete Input!! Please input again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // User Object
                try {
                    Age_int = Integer.parseInt(Age);
                } catch (Exception e) {
                    e=new Exception("Invalid Age!");
                    System.out.println(e.getMessage());
                    Toast.makeText(getApplicationContext(), "Invalid Age! Please input again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    if (Sex.equals("Male")) {
                        Sex_int = 0;
                    } else {
                        Sex_int = 1;
                    }
                } catch (Exception e) {
                    e=new Exception("Invalid Sex");
                    System.out.println(e.getMessage());
                    Toast.makeText(getApplicationContext(), "Invalid Sex! Please input again.", Toast.LENGTH_SHORT).show();
                    return;
                }


                User user = new User(ID, Password, Name, Age_int, Sex_int, Email, Phone, Address, Country);
                int status = client.sendUser(user);


                //temp check

                if (status != 1) {
                    System.out.println("the status in the createaccount :" + status);
                    Toast.makeText(getApplicationContext(), "The user ID has been used. Please sign up another one.", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("the sex : " + Sex + "the sex_int : " + Sex_int);
                    Toast.makeText(getApplicationContext(), "Successfully create a new account", Toast.LENGTH_SHORT).show();


                    Intent profileIntent = new Intent(CreateAccount.this, AccountResult.class);
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
            }


        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // On menu options selected
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
