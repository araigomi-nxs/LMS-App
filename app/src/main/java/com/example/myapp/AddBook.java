package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapp.database.DBHelper;
import com.example.myapp.models.Account;
import com.example.myapp.models.Book;

public class AddBook extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_book);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button backButton = findViewById(R.id.Back);
        Button addBookButton= findViewById(R.id.btnAddBook);
        EditText bookTitleField = findViewById(R.id.etBookTitle);
        EditText bookDescriptionField = findViewById(R.id.etDescription);
        EditText bookAuthorsField = findViewById(R.id.etAuthors);
        EditText bookQuantityField = findViewById(R.id.etQuantity);

        DBHelper dbHelper = new DBHelper(AddBook.this);

        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!bookTitleField.getText().isEmpty() && !bookDescriptionField.getText().isEmpty()  && !bookAuthorsField.getText().isEmpty()  &&  !bookQuantityField.getText().isEmpty())
                {
                    Book book = new Book(0,  bookTitleField.getText().toString(), bookDescriptionField.getText().toString() , bookAuthorsField.getText().toString(), Integer.parseInt(bookQuantityField.getText().toString()));
                    dbHelper.addBook(book);
                    Intent intent = new Intent(AddBook.this, BookListActivity.class);
                    startActivity(intent);

                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddBook.this, BookListActivity.class);
                startActivity(intent);
            }
        });

    }




}