package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapp.database.DBHelper;
import com.example.myapp.functions.BookAdapter;
import com.example.myapp.models.Book;

public class BookListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_borrow_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DBHelper db = new DBHelper(BookListActivity.this);
        ListView booklistView = findViewById(R.id.bookListView);

        //db.addBook(new Book( 0, "Book ni Expie", "Adventures of Expie and Joven", "Jamil", 5));

        BookAdapter bookAdapter = new BookAdapter(BookListActivity.this, db.getAllBooks(), booklistView);
        booklistView.setAdapter(bookAdapter);

        Button backButton = findViewById(R.id.Back);
        Button addBookButton = findViewById(R.id.btnAddBook);
        EditText searchView = findViewById(R.id.search);
        Button searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookAdapter bookAdapter = new BookAdapter(BookListActivity.this, db.searchBooks(searchView.getText().toString()), booklistView);
                booklistView.setAdapter(bookAdapter);

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( BookListActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent newintent = new Intent( BookListActivity.this, AddBook.class);
               startActivity(newintent);

            }
        });

        //booklistView.setOnItemClickListener();


    }
}