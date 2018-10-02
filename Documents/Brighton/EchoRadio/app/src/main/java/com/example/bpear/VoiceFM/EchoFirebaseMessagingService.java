package com.example.bpear.VoiceFM;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by bpear on 6/17/2018.
 */

public class EchoFirebaseMessagingService extends FirebaseMessagingService {

    private void addNotification(Context ctx) {



        NotificationManager mNotificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);



        // The id of the channel.

        String id = "MessageReceivedChannel";



        // The user-visible name of the channel.

        CharSequence name = "Message Received";



        // The user-visible description of the channel.

        String description = "Message Received";



        int importance = NotificationManager.IMPORTANCE_HIGH;



        NotificationChannel mChannel = new NotificationChannel(id, name,importance);



        // Configure the notification channel.

        mChannel.setDescription(description);



        mChannel.enableLights(true);



        // Sets the notification light color for notifications posted to this

        // channel, if the device supports this feature.

        mChannel.setLightColor(Color.RED);



        mChannel.enableVibration(true);

        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});



        mNotificationManager.createNotificationChannel(mChannel);



        mNotificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);



        // Sets an ID for the notification, so it can be updated.

        int notifyID = 1;



        // The id of the channel.

        String CHANNEL_ID = "MessageReceivedChannel";



        // Create a notification and set the notification channel.

        Notification notification = new Notification.Builder(ctx)

                .setContentTitle("Message Received")

                .setContentText("")

                .setSmallIcon(R.drawable.exo_notification_small_icon)

                .setChannelId(CHANNEL_ID)

                .build();



        // Issue the notification.

        mNotificationManager.notify(notifyID, notification);

    }

    private static final String Tag = "EchoMessagingService";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(Tag,"FROM: " + remoteMessage.getFrom());

        //check if the message does contain any data
        if (remoteMessage.getData().size() > 0){
            Log.d(Tag, "Message Data: " + remoteMessage.getData());
        }

        if (remoteMessage.getNotification() != null){
            Log.d(Tag,"Message Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody());
        }

    }



    /** Display of notification
     * @param body
     */

    private void sendNotification(String body) {
        Intent intent = new Intent(this, ArticleActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        //set sound of notification
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notifiBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_play_arrow_black_24dp)
                .setContentTitle("EchoRadio")
                .setContentText(body)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(notificationSound);


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notifiBuilder.build());

    }
}
