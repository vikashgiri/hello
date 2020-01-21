package com.infoicon.bonjob.recruiters.verify_phone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.infoicon.bonjob.R;
import com.infoicon.bonjob.login.LoginSignUpRecruiterActivity;
import com.infoicon.bonjob.main.HomeRecruiterActivity;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.Keys;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerifiedPhoneNumberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verified_phone_number);
        ButterKnife.bind(this);
    }

    /** event for verify the number */
    @OnClick(R.id.btnOk)
    void proceedToHome() {
        proceedToRecruiterHome();
    }

    /** event for verify the number */
    @OnClick(R.id.imageViewClose)
    void proceedToHomes() {
        proceedToRecruiterHome();
    }

    private void proceedToRecruiterHome() {

        Intent intent = new Intent(VerifiedPhoneNumberActivity.this, HomeRecruiterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(Keys.FROM, "");
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

}
