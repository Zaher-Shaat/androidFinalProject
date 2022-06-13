package com.example.malabsiadmin;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    boolean isAnyUserHere;
    Intent intent;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = getIntent();
        isAnyUserHere = intent.getBooleanExtra("LoginState", false);
        Log.d("LoginState", String.valueOf(isAnyUserHere));

        signupFragment SF = new signupFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Store store = new Store();

        if (isAnyUserHere) {
            Intent goToStoreIntent = new Intent(MainActivity.this, Store.class);
            startActivity(goToStoreIntent);
        } else {
            ft.replace(R.id.myFragment, SF);
        }
        ft.commit();
    }
}