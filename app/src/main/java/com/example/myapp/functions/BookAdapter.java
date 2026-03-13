package com.example.myapp.functions;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapp.BookListActivity;
import com.example.myapp.LoginActivity;
import com.example.myapp.R;
import com.example.myapp.RegisterActivity;
import com.example.myapp.UserDashboardActivity;
import com.example.myapp.database.DBHelper;
import com.example.myapp.models.Account;
import com.example.myapp.models.Book;

import java.util.List;

public class BookAdapter  extends ArrayAdapter<Book> {

    Context context;
    List< Book > booklist;
    ListView listView;
    public BookAdapter(@NonNull Context context, List< Book > booklist, ListView listview) {
        super(context, 0, booklist);
        this.context = context;
        this.booklist = booklist;
        this.listView = listview;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if( convertView == null)
        {
           convertView = LayoutInflater.from( context).inflate(R.layout.book_inventory_card, parent, false);
        }

        Book book = booklist.get(position) ;
        int bookId= book.getId();

        TextView bookTitleTv, descriptionTv, authorTv, quantitytv; // initialize quant if
        Button borrowButton, deleteButton;

        bookTitleTv = convertView.findViewById(R.id.tvDisplayBookTitle);
        descriptionTv = convertView.findViewById(R.id.tvDisplayDescription );
        authorTv = convertView.findViewById(R.id.tvDisplayAuthor);
        quantitytv = convertView.findViewById(R.id.tvDisplayQuantity);
        borrowButton = convertView.findViewById(R.id.btnBorrow);
        deleteButton =  convertView.findViewById(R.id.btnDelete);

        bookTitleTv.setText(book.getBookTitle());
        descriptionTv.setText(book.getBookDescription());
        authorTv.setText(book.getBookAuthor());
         quantitytv.setText(String.valueOf(book.getBookQuantity()));
        DBHelper db = new DBHelper(context);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.deleteBook(bookId);

                BookAdapter bookAdapter = new BookAdapter(context, db.getAllBooks(), listView);
                listView.setAdapter(bookAdapter);
            }
        });

        borrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book.setAccountId(Account.getID());
                db.insertBorrowBook(book);
            }
        });



        // button functions
        //  add- goes to account borrowlist
        // delete - admin removes entry

        return convertView;
    }
}
