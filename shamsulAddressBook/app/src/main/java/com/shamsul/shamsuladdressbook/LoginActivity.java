package com.shamsul.shamsuladdressbook;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.shamsul.shamsuladdressbook.data.DbHandler;

import java.io.File;

public class LoginActivity extends AppCompatActivity {

    private Button buttonSignUp;
    private Button buttonExit;
    public String savedUname= null;
    public String savedPword = null;
    private String userName;
    private String passWord;
    private EditText inputUserNDesc;
    private EditText inputPassDesc;
    DbHandler db;
    public int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonSignUp = (Button)findViewById(R.id.login_button);
        buttonExit = (Button)findViewById(R.id.button_exit);

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true); //Move the task containing this activity to the back of the activity stack.
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);

               // startActivity(intent);
                //File dbFile= LoginActivity.getDatabasePath("AddressBook.db");
                 //SQLiteDatabase database = SQLiteDatabase.openDatabase("/data/data/com.shamsul.shamsuladdressbook/databases/AddressBook.db", null, MODE_PRIVATE);


                inputUserNDesc = (EditText) findViewById(R.id.username_editText);
                String inputUname = inputUserNDesc.getText().toString();

                inputPassDesc = (EditText) findViewById(R.id.password_editText);
                String inputPass = inputPassDesc.getText().toString();


                id= checkUser(new User(inputUname,inputPass));
                if(id==-1)
                {
                    Toast.makeText(LoginActivity.this,"Creating new user",Toast.LENGTH_SHORT).show();
                    db.addUser(new User(inputUname, inputPass));
                    id= checkUser(new User(inputUname,inputPass));
                    intent.putExtra("uid",id);
                    startActivity(intent);

                }
                else
                {
                    if(checkPass(new User(inputUname,inputPass))==1) {
                        Toast.makeText(LoginActivity.this, "Logging in User:" + inputUname, Toast.LENGTH_SHORT).show();
                        intent.putExtra("uid",id);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Incorrect Password. Try Again", Toast.LENGTH_SHORT).show();
                    }
                }



                }
        });

        db=new DbHandler(LoginActivity.this);
        //these are dummy users
        db.addUser(new User("Shamsul", "123easy"));
        db.addUser(new User("Taziur", "agile"));
        db.addUser(new User("Lavanya", "snake"));
    }

    public int checkUser(User user)
    {
        return db.checkUser(user);
    }

    public int checkPass(User user)
    {
        return db.checkPass(user);
    }

    public int getId(){
        return id;
    }
}

