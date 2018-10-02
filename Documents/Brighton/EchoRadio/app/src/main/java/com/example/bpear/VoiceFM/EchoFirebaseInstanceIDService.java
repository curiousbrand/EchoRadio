package com.example.bpear.VoiceFM;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by bpear on 6/17/2018.
 */

public class EchoFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String Tag = "EchoFirebaseIDservice";

    @Override
    public void onTokenRefresh() {
        //Get the updated token
        String refreshtoken = FirebaseInstanceId.getInstance().getToken();
        Log.d(Tag,"NEW TOKEN: " + refreshtoken);
    }
}
