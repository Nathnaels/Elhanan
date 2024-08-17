package com.example.elhanen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SongContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_content);


        final int Home = R.id.nav_Home;
        final int song =R.id.nav_songs;
        final int msg =R.id.nav_Direct_message;

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setSelectedItemId(R.id.nav_songs);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId()==Home){
                    Intent intent=new Intent(getApplicationContext(), main.class);
                    startActivity(intent);
                }
                if (item.getItemId()==song){
                    Intent intent1=new Intent(getApplicationContext(), songs.class);
                    startActivity(intent1);
                }
                if (item.getItemId()==msg){
                    Intent intent2=new Intent(getApplicationContext(), Communication.class);
                    startActivity(intent2);
                }
                return false;
            }
        });



        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");

        TextView titleTextView = findViewById(R.id.songTitle);
        TextView contentTextView = findViewById(R.id.songContent);

        titleTextView.setText(title);
        contentTextView.setText(content);
    }
}