package com.infoicon.bonjob.splash;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.WindowManager;

import com.infoicon.bonjob.login.MainActivity;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.main.HomeRecruiterActivity;
import com.infoicon.bonjob.main.HomeSeekerActivity;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Short Description*******************
 * Created By
 * Created On
 * Updated By
 * Updated On
 */

public class SplashActivity extends AppCompatActivity {

    private static String TAG="SplashActivity";

    private int _splashTime = 3 * 1000;
    //Iqrv4qwhjj1gkt+iefQx75P5XRA=
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
       UtilsMethods.serviceGetCandidateProfile(SplashActivity.this);
        printHashKey();
        setTimerToStartActivity();
    }

    /** wait for 3 minutes. */
    private void setTimerToStartActivity() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            finish();
            // TODO: check the user is already login
            if (Singleton.getUserInfoInstance().isLogin() ) {
                // TODO: if user is login then check the user is recruiter or job seeker
                if (Singleton.getUserInfoInstance().isLoginRecriter() || Singleton.getUserInfoInstance().getLoginAdmin()) {
                    Intent intent = new Intent(SplashActivity.this, HomeRecruiterActivity.class);
                    intent.putExtra(Keys.FROM, "");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                else {
                    Intent intent = new Intent(SplashActivity.this, HomeSeekerActivity.class);
                    intent.putExtra(Keys.FROM, "");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            } else {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.putExtra(Keys.FROM, "");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }, _splashTime);
    }

    /** print hash key */
    public  void printHashKey() {
        try
        {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.e("Hash Key: ", hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "printHashKey()", e);
        } catch (Exception e) {
            Log.e(TAG, "printHashKey()", e);
        }
    }




}
