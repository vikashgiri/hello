package com.infoicon.bonjob.fcm;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;



public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseInstanceIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        System.out.println(TAG+" :::: "+refreshedToken);
        saveTokenPref(refreshedToken);
    }

    private void saveTokenPref(String refreshedToken) {
        MyFcmTokenModel myFcmTokenModel = new MyFcmTokenModel(getApplicationContext());
        myFcmTokenModel.setToken(refreshedToken);
    }

}
