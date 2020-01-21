package com.infoicon.bonjob.login;

import android.content.Intent;
import android.content.pm.PackageInfo;

import android.content.pm.PackageManager;
import android.content.pm.Signature;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.util.Base64;
import android.util.Log;
import android.view.WindowManager;
import com.infoicon.bonjob.R;

import com.infoicon.bonjob.customview.CustomsButton;
import com.infoicon.bonjob.utils.Keys;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;

import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
{
    @BindView(R.id.buttonRecruiteCandidate)CustomsButton buttonRecruiteCandidate;
    @BindView(R.id.buttonFindJob)CustomsButton buttonFindJob;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        initialize();
       ////
    }

    /** initialization */
    private void initialize()
    {

        //Todo set underline below text
       // buttonRecruiteCandidate.setPaintFlags(buttonRecruiteCandidate.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
       // buttonRecruiteCandidate.setText(getResources().getString(R.string.recruite_a_candidate));
        //buttonFindJob.setTextAppearance(this,R.style.boldText);
    }

    /** click event for getting option for login and sign up. */
    @OnClick(R.id.buttonFindJob)
    void findJobClicked()
    {
        Intent intent=new Intent(MainActivity.this, LoginSignupOptionActivity.class);
        intent.putExtra(Keys.FROM,Keys.FROM_FIND_JOB);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    /** event to next screen to login/signUp */
    @OnClick(R.id.buttonRecruiteCandidate)
        void recruiteACandidate()
    {
        Intent intent=new Intent(MainActivity.this, BeginRecruiterActivity.class);
      //  intent.putExtra(Keys.FROM,Keys.FROM_RECRUITER);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }

    /** get facebook hash key */
    public void printHashKey()
    {
        try
        {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.infoicon.bonjob",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures)
            {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
