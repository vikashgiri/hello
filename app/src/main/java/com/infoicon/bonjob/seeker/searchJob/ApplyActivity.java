package com.infoicon.bonjob.seeker.searchJob;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomsButton;
import com.infoicon.bonjob.utils.Keys;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApplyActivity extends AppCompatActivity {


    @BindView(R.id.btnContinueMySearch) CustomsButton btnContinueMySearch;
    @BindView(R.id.btnGotoProfile) CustomsButton btnGotoProfile;
    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        ButterKnife.bind(this);

        from = getIntent().getStringExtra(Keys.FROM);
    }


    @OnClick(R.id.btnContinueMySearch)
    void GotoSearchPage() {
       /* if (from.equals(Keys.JOB_DETAILS)) {
            Intent intent = new Intent();
            intent.putExtra(Keys.FROM,from);
            setResult(RESULT_OK, intent);
        }*/
        finish();
    }

    @OnClick(R.id.btnGotoProfile)
    void GotoMyProfile() {
        Intent intent = new Intent();
   //     intent.putExtra(Keys.FROM,"");
        setResult(RESULT_OK, intent);
        finish();
    }

}
