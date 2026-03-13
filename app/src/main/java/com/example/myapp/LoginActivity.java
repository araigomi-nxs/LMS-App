package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapp.database.DBHelper;
import com.example.myapp.models.Account;

public class LoginActivity extends AppCompatActivity {

    private static  boolean privilege = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText usernameField, passwordField;
        Button loginButton , registerButton;


        usernameField = findViewById(R.id.etUsername);
        passwordField = findViewById(R.id.etPassword);
        loginButton = findViewById(R.id.btnLogin);
        registerButton = findViewById(R.id.btnRegister);
        //  Switch radioButton = findViewById(R.id.btnSwitch);


        
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBHelper db = new DBHelper(LoginActivity.this);
                Account account = new Account(0, usernameField.getText().toString(), passwordField.getText().toString(), 0 );

               if(account.getUsername().equals("admin123") && account.getPassword().equals("12345678"))
               {
                       Intent intent = new Intent(LoginActivity.this, BookListActivity.class);
                       startActivity(intent);

               }
               else
               {
                   if(db.logIn(account) && db.isAccountValid(account)) {


                       Intent intent = new Intent(LoginActivity.this, UserDashboardActivity.class);
                       startActivity(intent);
                   }
               }
            }
        });



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });


    }
}