package com.shamsul.shamsulexpense;

/**
 * Created by haseo on 2018-03-11.
 */

//this is model class
public class User {
    //variables
    int id;
    String name;
    String password;
    String email;

    // Constructor with two parameters name and password
    public User(String name,String password, String email)
    {
        this.name=name;
        this.password=password;
        this.email=email;
    }
    //Parameter constructor containing all three parameters
    public User(int id,String name,String psd,String email)
    {
        this.id=id;
        this.name=name;
        this.password=psd;
        this.email=email;
    }
    //getting id
    public int getId() {
        return id;
    }
    //setting id
    public void setId(int id) {
        this.id = id;
    }
    //getting name
    public String getName() {
        return name;
    }
    //setting name
    public void setName(String name) {
        this.name = name;
    }
    //getting password
    public String getPassword() {
        return password;
    }
    //setting password
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){this.email = email;}
    public void setPassword(String password) {
        this.password = password;
    }
}

