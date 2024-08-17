package com.example.elhanen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class songs extends AppCompatActivity {

    private SongAdapter songAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);

        final int Home = R.id.nav_Home;
        final int Songs =R.id.nav_songs;
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
                if (item.getItemId()==Songs){
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



        TextView marqueeTextView = findViewById(R.id.tit);
        marqueeTextView.setSelected(true);



        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Song> songs = Utils.getSongs(this);
        songAdapter = new SongAdapter(songs, song -> {
            Intent intent = new Intent(getApplicationContext(), SongContentActivity.class);
            intent.putExtra("title", song.getTitle());
            intent.putExtra("content", song.getContent());
            startActivity(intent);
        });
        recyclerView.setAdapter(songAdapter);
    }

    }
