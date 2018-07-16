package com.example.bpear.echoradio;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.Timer;

public class MainActivity extends AppCompatActivity implements MediaController.MediaPlayerControl {

    boolean started = false;
    int length = 0;
    boolean pause;
    boolean prepared = false;
    private Toolbar radiotoolbar;
    private SeekBar seekbar;
    private ProgressDialog pd;
    ImageView b_play1;
    Button bpause;


    MediaPlayer mediaPlayer;
    String stream = "http://vis.media-ice.musicradio.com/RadioXUKMP3";


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Timer timer;
    private MediaController mediaController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekbar = (SeekBar) findViewById(R.id.seekBar);
        final TextView timeDuration = findViewById(R.id.timeduration);

        mediaController = new MediaController(this);
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


        radiotoolbar = findViewById(R.id.Radio_toolbar);
        radiotoolbar.setTitle("Live Stream");
        b_play1 = findViewById(R.id.play_or_pause);


        b_play1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //resume


                if (started) {
                    b_play1.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    mediaPlayer.pause();
                    started = false;
                } else {
                    mediaPlayer.seekTo(length);
                    b_play1.setImageResource(R.drawable.ic_pause_black_24dp);
                    mediaPlayer.start();

                    /*timeDuration.setText(String.format("%d : %02d",
                            TimeUnit.MILLISECONDS.toMinutes(duration),
                            TimeUnit.MILLISECONDS.toSeconds(duration) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
                    ));*/
                   /* Intent intent = new Intent(MainActivity.this, BackgroundService.class);
                    Util.startForegroundService(MainActivity.this, intent);*/

                    started = true;


                    pd.setMessage("Buffering...");
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
        length = mediaPlayer.getCurrentPosition();
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
        if (prepared) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void start() {
        mediaPlayer.start();
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos) {
        mediaPlayer.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return false;
    }

    @Override
    public boolean canSeekBackward() {
        return false;
    }

    @Override
    public boolean canSeekForward() {
        return false;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    class PlayerTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {


            try {
                mediaPlayer.setDataSource(stream);  // using the url initialized at the top
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaController.setMediaPlayer(MainActivity.this);
                        mediaController.setAnchorView(findViewById(R.id.navigation));
                        mediaController.setEnabled(true);
                        mediaController.show();
                        pd.cancel();
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


