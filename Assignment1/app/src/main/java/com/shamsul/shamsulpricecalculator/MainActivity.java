package com.shamsul.shamsulpricecalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void closeClicked(View view) {

        moveTaskToBack(true); //Move the task containing this activity to the back of the activity stack.
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);

    }

    public void launchSecondActivity(View view) {
       // Log.d(LOG_TAG, "Button clicked!");

        Intent intent = new Intent( MainActivity.this, Main2Activity.class);
        startActivity(intent);
        //String message = mMessageEditText.getText().toString();

        //intent.putExtra(EXTRA_MESSAGE, message);
        //startActivityForResult(intent, TEXT_REQUEST);
    }
}
