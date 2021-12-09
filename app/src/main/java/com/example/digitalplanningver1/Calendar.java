package com.example.digitalplanningver1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Calendar extends AppCompatActivity {

    EditText title, location, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        title=(EditText) findViewById(R.id.title);
        location=(EditText) findViewById(R.id.location);
        description=(EditText) findViewById(R.id.description);

    }

    public void AddEvent(View view) {
        if (!title.getText().toString().isEmpty()
                && !location.getText().toString().isEmpty()
                && !description.getText().toString().isEmpty()) {
            Intent info = new Intent(Intent.ACTION_INSERT)
                    .setData(CalendarContract.Events.CONTENT_URI)
                    .putExtra(CalendarContract.Events.TITLE, title.getText().toString())
                    .putExtra(CalendarContract.Events.DESCRIPTION, description.getText().toString())
                    .putExtra(CalendarContract.Events.EVENT_LOCATION, location.getText().toString());
            startActivity(info);
        }
        else{
            Toast.makeText(this, "Please fill the the fields.", Toast.LENGTH_SHORT).show();
        }
    }

    public void calendar(MenuItem item){
        Toast.makeText(this, "You are already here!😊", Toast.LENGTH_SHORT).show();
    }

    public void gal(MenuItem item){
        Intent i= new Intent(this, Gallery.class);
        startActivity(i);
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