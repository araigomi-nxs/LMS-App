package com.example.myapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapp.models.Account;
import com.example.myapp.models.Book;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "db.db";
    private static int DB_VER = 1;
    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null   , DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE accounts (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE," +
                "username VARCHAR(64) UNIQUE," +
                "password TEXT)");
        db.execSQL("CREATE TABLE books(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE," +
                "account_id INTEGER," +
                "book_title VARCHAR(64) UNIQUE," +
                "book_description TEXT," +
                "book_author VARCHAR(64)," +
                "book_quantity INTEGER)");
        db.execSQL("CREATE TABLE borrow_list(" +
                "book_id INTEGER," +
                "account_id INTEGER)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS accounts");
        db.execSQL("DROP TABLE IF EXISTS books");
        db.execSQL("DROP TABLE IF EXISTS borrow_list");
        onCreate(db);
    }
    public void addBook(Book book){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("book_title",book.getBookTitle());
        values.put("book_description",book.getBookDescription());
        values.put("book_author",book.getBookAuthor());
        values.put("book_quantity",book.getBookQuantity());
        db.insert("books",null,values);
    }
    public void deleteBook(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("books","id=?",new String[] {String.valueOf(id)});
    }
    public void updateBook(Book book){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("account_id",book.getAccountId());
        values.put("book_title",book.getBookTitle());
        values.put("book_description",book.getBookDescription());
        values.put("book_author",book.getBookAuthor());
        values.put("book_quantity",book.getBookQuantity());
        db.update("books",values,"id=?",new String[]{String.valueOf(book.getId())});
    }
    public Book getBook(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Book book=null;
        try(Cursor cursor = db.query("books",null,"id=?",new String[]{String.valueOf(id)},null,null,"book_title ASC")){
            if(cursor.moveToFirst()){
                book = new Book(
                        cursor.getInt(0),       //id
                        cursor.getInt(1),       //account id
                        cursor.getString(2),    //title
                        cursor.getString(3),    //description
                        cursor.getString(4),    //author
                        cursor.getInt(5)
                );

            }
        }
        return book;
    }
    public List<Book> getAllBooks(){
        SQLiteDatabase db = this.getWritableDatabase();
        List<Book> list = new ArrayList<>();
        try(Cursor cursor = db.query("books",null,null,null,null,null,"book_title ASC")){
            if(cursor.moveToFirst()){
                do {
                    Book book = new Book(
                            cursor.getInt(0),       //id
                            cursor.getInt(1),       //account id
                            cursor.getString(2),    //title
                            cursor.getString(3),    //description
                            cursor.getString(4),    //author
                            cursor.getInt(5)
                    );
                    list.add(book);
                }while (cursor.moveToNext());
            }
        }
        return list;
    }
    public boolean isAccountValid(Account account){
        SQLiteDatabase db = this.getWritableDatabase();
        try(Cursor cursor = db.query("accounts",null,"username=?",new String[]{account.getUsername()},null,null,null)) {
            if (cursor.moveToFirst()) {
                return true;
            }
        }
        return false;
    }
    public List<Book> searchBooks(String search){
        SQLiteDatabase db = this.getWritableDatabase();
        List<Book> list = new ArrayList<>();
        try(Cursor cursor = db.query("books",null,"book_title=? OR book_author=?",new String[]{search,search},null,null,"book_title ASC")){
            if(cursor.moveToFirst()){
                do {
                    Book book = new Book(
                            cursor.getInt(0),       //id
                            cursor.getInt(1),       //account id
                            cursor.getString(2),    //title
                            cursor.getString(3),    //description
                            cursor.getString(4),    //author
                            cursor.getInt(5)        //quantity
                    );
                    list.add(book);
                }while (cursor.moveToNext());
            }
        }
        return list;
    }
    public void registerAccount(Account account){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username",account.getUsername());
        values.put("password",account.getPassword());
        db.insert("accounts",null,values);
        Account.setID(getAccountId(account.getUsername()));
    }
    public int getAccountId(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        try(Cursor cursor=db.query("accounts",null, "username=?",new String[]{username},null,null,null)){
            if(cursor.moveToFirst()){
                return cursor.getInt(0);
            }
        }
        return 0;
    }
    public boolean logIn(Account account){
        int id=getAccountId(account.getUsername());
        SQLiteDatabase db = this.getWritableDatabase();
        try(Cursor cursor = db.query("accounts",null,"username=? AND password=?",new String[]{account.getUsername(),account.getPassword()},null,null,null)){
            if (cursor.moveToFirst()){
                Account.setID(id);
                Account.setUSERNAME(account.getUsername());
                return true;
            }
        }
        return false;
    }
    public void insertBorrowBook(Book book){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("book_id",book.getId());
        values.put("account_id",Account.getID());
        db.insert("borrow_list",null,values);
    }

    public void deleteBorrowBook(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("borrow_list","book_id=? AND account_id=?",new String[]{String.valueOf(id),String.valueOf(Account.getID())});
    }
    public Book getBorrowBook(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Book book=null;
        try(Cursor cursor = db.query("borrow_list",null,"book_id=? AND account_id=?",new String[]{String.valueOf(id),String.valueOf(Account.getID())},null,null,null)){
            if(cursor.moveToFirst()){
                book=getBook(cursor.getInt(0));
            }
        }
        return book;
    }
    private Book selectBorrowBook(int account_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Book book=null;
        try(Cursor cursor = db.query("books",null,"account_id=?",new String[]{String.valueOf(account_id)},null,null,"book_title ASC")){
            if(cursor.moveToFirst()){
                book = new Book(
                        cursor.getInt(0),       //id
                        cursor.getInt(1),       //account id
                        cursor.getString(2),    //title
                        cursor.getString(3),    //description
                        cursor.getString(4),    //author
                        cursor.getInt(5)
                );

            }
        }
        return book;
    }

    public List<Book> getAllBorrowBooks(){
        SQLiteDatabase db = this.getWritableDatabase();
        List<Book> list = new ArrayList<>();
        try(Cursor cursor = db.query("books",null,"account_id=?",new String[]{String.valueOf(Account.getID())},null,null,null)){
            if(cursor.moveToFirst()){
                do{
                    Book book = new Book(
                            cursor.getInt(0),       //id
                            cursor.getInt(1),       //account id
                            cursor.getString(2),    //title
                            cursor.getString(3),    //description
                            cursor.getString(4),    //author
                            cursor.getInt(5)
                    );
                    list.add(book);
                }while(cursor.moveToNext());
            }
        }
        return list;
    }


}
