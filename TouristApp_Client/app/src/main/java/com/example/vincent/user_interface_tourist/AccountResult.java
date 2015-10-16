package com.example.vincent.user_interface_tourist;



import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/* Account setting result */
/*This activity is used to show the account information of a user*/
public class AccountResult extends ActionBarActivity {

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
    private Button GoToLoginPageButton;

    private String ID;
    private String Password;
    private String Name;
    private String Age;
    private String Sex;
    private String Email;
    private String Phone;
    private String Address;
    private String Country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_result);

        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");
        Name = intent.getStringExtra("Name");
        Age = intent.getStringExtra("Age");
        Sex = intent.getStringExtra("Sex");
        Email = intent.getStringExtra("Email");
        Phone = intent.getStringExtra("Phone");
        Address = intent.getStringExtra("Address");
        Country = intent.getStringExtra("Country");

        IDEditTextResult = (TextView) findViewById(R.id.IDEditTextResult);
        PasswordEditTextResult = (TextView) findViewById(R.id.PasswordEditTextResult);
        NameEditTextResult = (TextView) findViewById(R.id.NameEditTextResult);
        AgeEditTextResult = (TextView) findViewById(R.id.AgeEditTextResult);
        SexEditTextResult = (TextView) findViewById(R.id.SexEditTextResult);
        EmailEditTextResult = (TextView) findViewById(R.id.EmailEditTextResult);
        PhoneEditTextResult = (TextView) findViewById(R.id.PhoneEditTextResult);
        AddressEditTextResult = (TextView) findViewById(R.id.AddressEditTextResult);
        CountryEditTextResult = (TextView) findViewById(R.id.CountryEditTextResult);

        IDEditTextResult.setText(ID);
        //   PasswordEditTextResult.setText(Password);
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
        GoToLoginPageButton = (Button) findViewById(R.id.GoToLoginPageButton);
        EditButton = (Button) findViewById(R.id.EditButton);

        GoToLoginPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(AccountResult.this, LogIn.class);
                startActivity(Intent);
            }
        });

        EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent profileIntent = new Intent(AccountResult.this, CreateAccount.class);
                profileIntent.putExtra("ID", ID);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_account_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
