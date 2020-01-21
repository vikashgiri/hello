package com.infoicon.bonjob.recruiters.verify_phone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.infoicon.bonjob.R;
import com.infoicon.bonjob.utils.Keys;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerifyPhoneNumberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_number);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.btnCancel)
    void goBack() {
        finish();
    }

    /** event for verify the number */
    @OnClick(R.id.btnVerify)
    void verifyNumber() {
        Intent intent=new Intent(this, InputPhoneNumberActivity.class);
        startActivity(intent);
    }
}
