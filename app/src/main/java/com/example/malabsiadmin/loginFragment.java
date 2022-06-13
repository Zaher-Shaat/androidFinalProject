package com.example.malabsiadmin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class loginFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    EditText edt_email, edt_pass;
    Button btn_login;
    TextView tv_sign_up;

    public loginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);


        firebaseAuth = FirebaseAuth.getInstance();
        edt_email = v.findViewById(R.id.edt_email);
        edt_pass = v.findViewById(R.id.edt_pass);
        btn_login = v.findViewById(R.id.btn_login);
        tv_sign_up = v.findViewById(R.id.tv_sign_up);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        tv_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity ma = (MainActivity) getActivity();
                FragmentManager f = ma.getSupportFragmentManager();
                FragmentTransaction ft = f.beginTransaction();
                signupFragment sf = new signupFragment();
                ft.replace(R.id.myFragment, sf);
                ft.commit();
            }
        });


        return v;

    }

    private void login() {
        String email = edt_email.getText().toString();
        String password = edt_pass.getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(getActivity(), "Email required", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(getActivity(), "password required", Toast.LENGTH_SHORT).show();

        } else {

            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        SharedPreferences sharedPreferences = getContext().getSharedPreferences("loginState", Context.MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();

// Storing the key and its value as the data fetched from edittext
                        myEdit.putBoolean("loginState", true);
                        myEdit.commit();
                        ///////////////////////////////////
                        getActivity().finish();
                        startActivity(new Intent(getActivity(), Store.class));
                        Toast.makeText(getActivity(), "Login Successfully", Toast.LENGTH_SHORT).show();
                        boolean myLogState = sharedPreferences.getBoolean("logState", false);
                        System.out.println(myLogState);
                    } else {
                        Toast.makeText(getActivity(), "something error", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }


}