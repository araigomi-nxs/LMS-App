//book model
package com.example.myapp.models;

public class Book {
    private int id;
    private int accountId;
    private String bookTitle;
    private String bookDescription;
    private String bookAuthor;
    private int bookQuantity;

    public Book(int id, String bookTitle, String bookDescription, String bookAuthor, int bookQuantity) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.bookDescription = bookDescription;
        this.bookAuthor = bookAuthor;
        this.bookQuantity = bookQuantity;
    }

    public Book(int id, int accountId, String bookTitle, String bookDescription, String bookAuthor, int bookQuantity) {
        this.id = id;
        this.accountId = accountId;
        this.bookTitle = bookTitle;
        this.bookDescription = bookDescription;
        this.bookAuthor = bookAuthor;
        this.bookQuantity = bookQuantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public int getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(int bookQuantity) {
        this.bookQuantity = bookQuantity;
    }
}
