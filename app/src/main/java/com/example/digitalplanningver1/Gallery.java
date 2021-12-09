package com.example.digitalplanningver1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class Gallery extends AppCompatActivity {
    ImageView ima;

    public static int count=0;

    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseAuth auth;

    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        ima= (ImageView) findViewById(R.id.cam);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        auth= FirebaseAuth.getInstance();
    }

    public void select(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultcode, Intent data) {
        super.onActivityResult(requestCode, resultcode, data);
        if (requestCode == PICK_IMAGE_REQUEST
                && resultcode == RESULT_OK
                && data != null
                && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                ima.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    public void uploadImage(View view) {
        if (filePath != null) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference ref = storageReference.child("images/users/" +auth.getCurrentUser().getUid()+"-"+count);
            count++;
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                   @Override
                   public void onSuccess(
                   UploadTask.TaskSnapshot taskSnapshot) {
                   progressDialog.dismiss();
                   Toast.makeText(Gallery.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                   }
                   }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(Gallery.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(
                        UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        progressDialog.setMessage("Uploaded " + (int)progress + "%");
                        }
                });
        }

    }

    public void calendar(MenuItem item){
        Intent i= new Intent(this, Calendar.class);
        startActivity(i);
    }

    public void gal(MenuItem item){
        Toast.makeText(this, "You are already here!😊", Toast.LENGTH_SHORT).show();
    }

    public void auth(MenuItem item){
        Intent i= new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void camera(MenuItem item){
        Intent i= new Intent(this, Camera.class);
        startActivity(i);
    }

    public void block(MenuItem item) {
        Intent i= new Intent(this, Block.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}