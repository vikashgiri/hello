package com.infoicon.bonjob.login;

import android.content.Intent;
import android.graphics.Paint;
import android.os.CountDownTimer;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Toast;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomsButton;

import com.infoicon.bonjob.utils.Keys;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import butterknife.ButterKnife;

import butterknife.OnClick;

import me.leolin.shortcutbadger.ShortcutBadger;

public class BeginRecruiterActivity extends AppCompatActivity
{
    @BindView(R.id.buttonRecruiteCandidate)
    CustomsButton buttonRecruiteCandidate;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin_recruiter);
        ButterKnife.bind(this);
        initialize();
    }
    /** initialization of classes */
    private void initialize()
    {
        buttonRecruiteCandidate.setPaintFlags
                (buttonRecruiteCandidate.getPaintFlags() |
                        Paint.UNDERLINE_TEXT_FLAG);
    }

    /** event for proceed to recruit candidate */
    @OnClick(R.id.buttonRecruiteCandidate)
    void recruiteCandidate()
    {
       // Intent intent=new Intent(BeginRecruiterActivity.this, LoginSignupOptionActivity.class);
       // intent.putExtra(Keys.FROM,Keys.FROM_RECRUITER);
       // startActivity(intent);
       // overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    /** event for proceed to recruit candidate */

    @OnClick(R.id.btnRecruit)
    void recruitecandidate()
    {
        Intent intent=new Intent(BeginRecruiterActivity.this, LoginSignupOptionActivity.class);
        intent.putExtra(Keys.FROM,Keys.FROM_RECRUITER);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

 /** event for not proceed as a recruiter */
    @OnClick(R.id.buttonCancel)
    void cancel()
    {
        finish();
    }
}
