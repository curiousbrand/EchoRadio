package com.example.bpear.VoiceFM;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.bpear.VoiceFM.SERVICES.BackgroundService;
import com.google.android.exoplayer2.util.Util;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class MainActivity extends AppCompatActivity  {

    boolean started = false;
    int length = 0;
    boolean pause;
    boolean prepared = false;
    private Toolbar radiotoolbar;
    private SeekBar volumeBar;
    private SeekBar progress;
    TextView liveTime;
    private ProgressDialog pd;
    ImageView b_play1;



    MediaPlayer mediaPlayer;
    String stream = "http://bbcmedia.ic.llnwd.net/stream/bbcmedia_radio1xtra_mf_q?s=1531426242&e=1531440642&h=00f66a13d2babe717e13efb4fec1e9b4";


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private MediaController mediaController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        volumeBar = (SeekBar) findViewById(R.id.volumeBar);
        liveTime = findViewById(R.id.timeduration);
        progress = findViewById(R.id.seekBar2);
        progress.setEnabled(false);


        pd = new ProgressDialog(MainActivity.this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                }
            }
        };




        volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float volumeNum = progress /100f;
                mediaPlayer.setVolume(volumeNum,volumeNum);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        b_play1 = findViewById(R.id.play_or_pause);


        b_play1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //resume


                if (started) {
                    b_play1.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    mediaPlayer.pause();
                    liveTime.setText("0:00");
                    liveTime.setTextColor(getResources().getColor(R.color.black));
                    started = false;
                } else {
                    mediaPlayer.seekTo(length);
                    b_play1.setImageResource(R.drawable.ic_pause_black_24dp);
                    mediaPlayer.start();
                    started = true;
                    pd.setMessage("Buffering... Please wait");
                    pd.show();
                    new PlayerTask().execute(stream);
                }
            }
        });


        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener()

        {
            public void onPrepared(MediaPlayer mediaPlayer) {
                System.out.print("VideoPrepared");
            }
        });


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


    @Override
    protected void onPause() {
        super.onPause();

        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    class PlayerTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {


            try {
                mediaPlayer.setDataSource(stream);  // using the url initialized at the top
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        pd.cancel();
                        liveTime.setText("LIVE");
                        liveTime.setTextColor(getResources().getColor(R.color.red));
                        mp.start();
                    }
                });

                mediaPlayer.prepareAsync();// preparing to sync for streaming  //might take long for buffering

                prepared = true; // Media player is now prepared and synced

            } catch (IOException e) {
                e.getMessage();
            }


            return prepared;
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MainActivity.this, BackgroundService.class);
        Util.startForegroundService(MainActivity.this, intent);
        liveTime.setText("LIVE");
        liveTime.setTextColor(getResources().getColor(R.color.red));

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
                    mediaPlayer.start();
                    return true;
                case R.id.sign_out:
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    builder.setTitle("Logout");
                    builder.setMessage("Are you sure you want to logout?");

                    builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mAuth.signOut();
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    android.support.v7.app.AlertDialog dialog = builder.show();
            }

            // this listener will be called when there is change in firebase user session
            FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user == null) {
                        // user auth state is changed - user is null
                        // launch login activity
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    }
                }
            };
            return false;
        }

    };
}


