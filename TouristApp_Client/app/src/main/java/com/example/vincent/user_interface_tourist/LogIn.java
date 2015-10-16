package com.example.vincent.user_interface_tourist;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import exceptionHelper.CustomException;
import exceptionHelper.LostConnectException;
import model.User;
import remote.Client;

/* Log in Page, the first Page of the App */
public class LogIn extends ActionBarActivity {

    Client client = Client.getInstance();
    private Button LoginButton;
    private Button SignUpButton;
    private String ID;
    private String Password;
    private EditText IDEditText;
    private EditText PasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        IDEditText = (EditText) findViewById(R.id.IDEditText);
        PasswordEditText = (EditText) findViewById(R.id.PasswordEditText);
        addListenerOnButton();
    }

    private void addListenerOnButton() {
        LoginButton = (Button) findViewById(R.id.LoginButton);
        SignUpButton = (Button) findViewById(R.id.SignUpButton);

        // On clicking the login Button
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID = IDEditText.getText().toString();
                Password = PasswordEditText.getText().toString();
                User user = null;
                try {
                    user = client.loadUser(ID, Password);
                    if (user!=null) {
                        client.loadCity();
                        Toast.makeText(getApplicationContext(), "Successfully Log In!", Toast.LENGTH_SHORT).show();
                        Intent enterDestination = new Intent(LogIn.this, Destination.class);
                        startActivity(enterDestination);
                    }
                    else {
                        CustomException ce = new CustomException("Inaccurate ID or password!", getApplicationContext());
                        ce.handle_exception();
                        return;
                    }
                } catch (LostConnectException le) {
                    le.setContext(getApplicationContext());
                    le.handle_exception();
                }

            }
        });
        // On clicking the sign up button
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createAccount = new Intent(LogIn.this, CreateAccount.class);
                startActivity(createAccount);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_in, menu);
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
