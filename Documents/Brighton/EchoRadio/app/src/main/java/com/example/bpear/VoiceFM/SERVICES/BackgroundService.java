package com.example.bpear.VoiceFM.SERVICES;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.bpear.VoiceFM.MainActivity;
import com.example.bpear.VoiceFM.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * Created by bpear on 7/13/2018.
 */

public class BackgroundService extends Service {
    private SimpleExoPlayer simpleExoPlayer;
    private PlayerNotificationManager playerNotificationManager;
    final String CHANNEL_ID = "AUDIO_01";
    final int PLAYBACK_NOTIFICATION_ID = 10001;

    public void onCreate() {
        super.onCreate();
        final Context context = this;


        // String with the url of the radio you want to play
        String stream = "http://bbcmedia.ic.llnwd.net/stream/bbcmedia_radio1xtra_mf_q?s=1531426242&e=1531440642&h=00f66a13d2babe717e13efb4fec1e9b4";
        Uri radioUri = Uri.parse(stream);
        // Settings for exoPlayer
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context, new DefaultTrackSelector());
        DefaultDataSource.Factory dataSource = new DefaultDataSourceFactory(context, Util.getUserAgent(context, "96.1 FM "));
        MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSource).createMediaSource(radioUri);
        // Prepare ExoPlayer
        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.setPlayWhenReady(true);

        playerNotificationManager = PlayerNotificationManager.createWithNotificationChannel(
                context, CHANNEL_ID, R.string.channel_name, PLAYBACK_NOTIFICATION_ID, new PlayerNotificationManager.MediaDescriptionAdapter() {
                    @Override
                    public String getCurrentContentTitle(Player player) {
                        return null;
                    }

                    @Nullable
                    @Override
                    public PendingIntent createCurrentContentIntent(Player player) {
                        Intent appIntent = new Intent(context, MainActivity.class);
                        return PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    }

                    @Nullable
                    @Override
                    public String getCurrentContentText(Player player) {
                        return null;
                    }

                    @Nullable
                    @Override
                    public Bitmap getCurrentLargeIcon(Player player, PlayerNotificationManager.BitmapCallback callback) {
                        return null;
                    }
                }
        );

        playerNotificationManager.setNotificationListener(new PlayerNotificationManager.NotificationListener() {
            @Override
            public void onNotificationStarted(int notificationId, Notification notification) {
                startForeground(notificationId, notification);
            }

            @Override
            public void onNotificationCancelled(int notificationId) {
                stopSelf();
            }
        });
        playerNotificationManager.setPlayer(simpleExoPlayer);
    }

    @Override
    public void onDestroy() {
        playerNotificationManager = null;
        simpleExoPlayer.release();
        simpleExoPlayer = null;
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
}
