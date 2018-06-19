package com.example.bpear.echoradio;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
                .setSmallIcon(R.mipmap.ic_echolauncher)
                .setContentTitle("EchoRadio")
                .setContentText(body)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setSound(notificationSound);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notifiBuilder.build());

    }
}
