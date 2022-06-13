package com.example.malabsiadmin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Store extends AppCompatActivity {
    private static final String TAG = "store";
    BottomNavigationView btn;
    FloatingActionButton btn_f, signOutBtn;
    ArrayList<com.example.malabsiadmin.item> items = new ArrayList<>();
    private RecyclerView store_clothes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        store_clothes = findViewById(R.id.store_clothes);
        btn_f = findViewById(R.id.btn_f);
        signOutBtn = findViewById(R.id.signOutBtn);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        btn_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Store.this, addItem.class);
                startActivity(i);
            }
        });
        store_clothes.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        store_clothes.setLayoutManager(gridLayoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        readData();
    }

    private void readData() {
        items.clear();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("items");
        Task<DataSnapshot> task = ref.get();
        task.addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Iterable<DataSnapshot> data = task.getResult().getChildren();
                    for (DataSnapshot snap : data) {
                        item i = snap.getValue(item.class);
                        items.add(i);

                    }
                    clothesAdapter adapter = new clothesAdapter(items);
                    store_clothes.setAdapter(adapter);


                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(Store.this, "failed " + error, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void signOut() {


        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
        SharedPreferences sharedPreferences = getSharedPreferences("loginState", Context.MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

// Storing the key and its value as the data fetched from edittext
        myEdit.putBoolean("loginState", false);
        myEdit.commit();
        Intent signOutIntent = new Intent(Store.this, MainActivity.class);
        signOutIntent.putExtra("LoginState", false);
        startActivity(signOutIntent);

    }
}