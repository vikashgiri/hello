package com.infoicon.bonjob.recruiters.post;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.infoicon.bonjob.R;
import com.infoicon.bonjob.utils.Keys;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PostConfirmationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_confirmation);
        ButterKnife.bind(this);
    }

    /** result back for proceed to my offer screen */
    @OnClick(R.id.btnViewMyOffer)
    void viewMyOffer(){
        Intent intent = new Intent();
        intent.putExtra(Keys.VIEW_FRAGMENT, Keys.MY_OFFERS);
        setResult(RESULT_OK, intent);
        finish();
    }

    /** result back for proceed to profile screen */
    @OnClick(R.id.btnGotoProfile)
    void goToMyProfile(){
        Intent intent = new Intent();
        intent.putExtra(Keys.VIEW_FRAGMENT, Keys.PROFILE);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
    }
}
