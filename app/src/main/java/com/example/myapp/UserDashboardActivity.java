package com.example.myapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapp.LoginActivity;
import com.example.myapp.R;
import com.example.myapp.database.DBHelper;
import com.example.myapp.functions.BookAdapter;
import com.example.myapp.models.Account;

public class UserDashboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.user_dashboard);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView displayUsername=findViewById(R.id.tvDisplayUsername);
        displayUsername.setText(Account.getUSERNAME());
        Button back = findViewById(R.id.Back),
                borrow=findViewById(R.id.btnBorrowBooks);
        ListView testView = findViewById(R.id.listViewBooks);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboardActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        borrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboardActivity.this, BookListActivity.class);
                startActivity(intent);
            }
        });
        DBHelper db = new DBHelper(this);
        BookAdapter bookAdapter = new BookAdapter(UserDashboardActivity.this, db.getAllBorrowBooks(), testView);
        testView.setAdapter(bookAdapter);

    }
}
