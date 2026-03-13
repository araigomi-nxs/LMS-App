package com.example.myapp.models;

public class Account {
    private static int ID;
    private static String USERNAME;
    private int id;
    private String username;
    private String password;
    private int privilege;


    public static int getID() {
        return ID;
    }

    public static void setID(int ID) {
        Account.ID = ID;
    }

    public static String getUSERNAME() {
        return USERNAME;
    }

    public static void setUSERNAME(String USERNAME) {
        Account.USERNAME = USERNAME;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public Account(int id) {
        this.id = id;
    }


    public Account(int id, String username, String password, int privilege) {
        this.id =id;
        this.username = username;
        this.password = password;
        this.privilege = privilege;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPrivilege() {
        return privilege;
    }

    public void setPrivilege(int privilege) {
        this.privilege = privilege;
    }



}
