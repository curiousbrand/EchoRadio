package com.example.bpear.echoradio;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {


        private Toolbar radiotoolbar;
        ImageView b_play1;
        MediaPlayer mediaPlayer;
        boolean prepared;
        String stream = "https://tunein.com/radio/Radio-X-UK-s254741/";
        private boolean started;

        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            radiotoolbar = findViewById(R.id.Radio_toolbar);
            radiotoolbar.setTitle("Live Stream");
            b_play1 = findViewById(R.id.buttonPlay);
            b_play1.setEnabled(false);

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            new PlayerTask().execute(stream);

            b_play1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (started) {
                        started = false;
                        mediaPlayer.pause();
                    } else {
                        started = true;
                        mediaPlayer.start();


                    }
                }
            });

            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        }

        @Override
        protected void onPause () {
            super.onPause();
            if (started) {
                mediaPlayer.pause();
            }
        }

        @Override
        protected void onResume () {
            super.onResume();
            if (started) {
                mediaPlayer.start();
            }
        }

        @Override
        protected void onDestroy () {
            super.onDestroy();
            if (prepared) {
                mediaPlayer.release();
            }
        }




        class PlayerTask extends AsyncTask<String, Void, Boolean> {
            @Override
            protected Boolean doInBackground(String... strings) {
                try {
                    mediaPlayer.setDataSource(strings[0]);
                    mediaPlayer.prepare();
                    prepared = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return prepared;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                b_play1.setEnabled(true);
            }
        }



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_radio:
                    return true;
                case R.id.navigation_articles:
                    startActivity(new Intent(MainActivity.this, ArticleActivity.class));
                    return true;
            }
            return false;
        }
    };
}
