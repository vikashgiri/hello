package com.infoicon.bonjob.fcm;

import android.app.IntentService;
import android.content.Intent;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;

/**
 * Created by Pramod on 16/1/18.
 */

public class RefreshTokenService extends IntentService
{
    public static final String TAG = RefreshTokenService.class.getSimpleName();

    public RefreshTokenService()
    {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        try {
            // Resets Instance ID and revokes all tokens.
            FirebaseInstanceId.getInstance().deleteInstanceId();
            // Now manually call onTokenRefresh()
            FirebaseInstanceId.getInstance().getToken();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
