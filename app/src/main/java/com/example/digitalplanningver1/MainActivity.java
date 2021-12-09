package com.example.digitalplanningver1;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText email, pass;
    FirebaseAuth fAuth;

    String e, p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email= (EditText) findViewById(R.id.email);
        pass= (EditText) findViewById(R.id.pass);

        fAuth= FirebaseAuth.getInstance();


    }

    /**
     * Takes the email and password and checks if the information matches a user.
     * @param view
     */
    public void LogIn(View view) {
        e= email.getText().toString();
        p= pass.getText().toString();

        if (TextUtils.isEmpty(e)){
            email.setError("Email Is Required.");
        }
        if (TextUtils.isEmpty(p)){
            pass.setError("Password Is Required.");
        }
        else {
            if (p.length() < 6) {
                pass.setError("Password must be longer than 6 digits.");
            }
            else{
                fAuth.signInWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "User Signed.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Error !"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    /**
     * Takes the email and password and checks if the information matches a user.
     * @param view
     */

    public void signIn(View view) {

        e= email.getText().toString();
        p= pass.getText().toString();

        if (TextUtils.isEmpty(e)){
            email.setError("Email Is Required.");
        }
        if (TextUtils.isEmpty(p)){
            pass.setError("Password Is Required.");
        }
        else{
            if(p.length()<6){
                pass.setError("Password must be longer than 6 digits.");
            }
            else{
                fAuth.createUserWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Error !"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    public void calendar(MenuItem item){
        Intent i= new Intent(this, Calendar.class);
        startActivity(i);
    }

    public void gal(MenuItem item){
        Intent i= new Intent(this, Gallery.class);
        startActivity(i);
    }
    public void auth(MenuItem item){
        Toast.makeText(this, "You are already here!😊", Toast.LENGTH_SHORT).show();
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