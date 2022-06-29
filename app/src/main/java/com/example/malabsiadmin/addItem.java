package com.example.malabsiadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;
//malabsiAdmin
public class addItem extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;
    Button btn_create;
    Uri selectedImage;
    ImageView create_image;
    EditText clothes_code, price,descEdit;
    FloatingActionButton btn_img;
    FirebaseStorage storage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        initViews();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != 0) {
            String[] premission = new String[1];
            premission[0] = Manifest.permission.READ_EXTERNAL_STORAGE;
            ActivityCompat.requestPermissions(this, premission, 1211);
        }
        btn_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE);
            }
        });
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItemToDatabase();
            }

            private void addItemToDatabase() {
                String code = clothes_code.getText().toString();
                String price_item = price.getText().toString();
                String desc =descEdit.getText().toString();
                if (code.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Invalid code item", Toast.LENGTH_LONG).show();
                } else if (price_item.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Invalid price item", Toast.LENGTH_LONG).show();

                } else {
                    if (selectedImage != null) {
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference ref = storage.getReference("images/" + UUID.randomUUID().toString());
                        ref.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if (task.isSuccessful()) {
                                    ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            if (task.isSuccessful()){
                                                String imageUrl = task.getResult().toString();
                                                addToDatabase(code, price_item,imageUrl,desc);
                                            }
                                        }
                                    });
                                }else{
                                    Toast.makeText(addItem.this, "Upload image failed ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        addToDatabase(code, price_item,""," ");
                    }
                }
            }

            public void addToDatabase(String code, String price_item, String imageUrl,String desc) {
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference ref = db.getReference("items");
                String id = ref.push().getKey();
                item i = new item(id, code, price_item, imageUrl,desc);

                ref.child(id).setValue(i).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(addItem.this, "success", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(addItem.this, "failed " + error, Toast.LENGTH_SHORT).show();

                        }

                    }
                });
            }
        });


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {

            selectedImage = data.getData();
            create_image.setImageURI(selectedImage);
        }
    }


    private void initViews() {
        create_image = findViewById(R.id.create_image);
        clothes_code = findViewById(R.id.clothes_code);
        price = findViewById(R.id.price);
        btn_img = findViewById(R.id.btn_img);
        btn_create = findViewById(R.id.btn_create);
        descEdit=findViewById(R.id.descEdit);
    }
}