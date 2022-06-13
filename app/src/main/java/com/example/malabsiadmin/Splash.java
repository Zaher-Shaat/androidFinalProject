package com.example.malabsiadmin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {
    SharedPreferences sh;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sh = getSharedPreferences("loginState", Context.MODE_PRIVATE);

// The value will be default as empty string because for
// the very first time when the app is opened, there is nothing to show
        boolean myLogState = sh.getBoolean("loginState", false);
//        SharedPreferences sharedPreferences = this.getSharedPreferences("logState", Context.MODE_PRIVATE);
//        boolean myLogState = sharedPreferences.getBoolean("logState");
        Log.d("myLogState", Boolean.toString(myLogState));

        if (myLogState) {
            intent = new Intent(Splash.this, MainActivity.class);
            intent.putExtra("LoginState", myLogState);
            startActivity(intent);
            finish();
        } else {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    intent = new Intent(Splash.this, MainActivity
                            .class);
                    intent.putExtra("LoginState", myLogState);
                    startActivity(intent);
                    finish();
                }
            }, 3000);
        }
    }
}