package com.infoicon.bonjob.payment;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomsButton;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.dialogs.SubscribePlanDialogFragment;
import com.infoicon.bonjob.utils.Keys;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PaymentStatusActivity extends AppCompatActivity {

    @BindView(R.id.imageViewStatus) ImageView imageViewStatus;
    @BindView(R.id.btnPostJob) CustomsButton btnPostJob;
    @BindView(R.id.btnFindCandidate) CustomsButton btnFindCandidate;
    @BindView(R.id.linearSuccessPayment) LinearLayout linearSuccessPayment;
    @BindView(R.id.btnSubscribe) CustomsButton btnSubscribe;
    @BindView(R.id.tvContact) CustomsTextView tvContact;
    @BindView(R.id.linearFailurePayment) LinearLayout linearFailurePayment;
    @BindView(R.id.dialog_ludovic) RelativeLayout dialogLudovic;
    @BindView(R.id.tvEditProfile) CustomsTextView tvEditProfile;
    @BindView(R.id.tvPaymentStatusFailedSuccess) CustomsTextView tvPaymentStatusFailedSuccess;
    @BindView(R.id.tvPaymentStatusFailedMessage) CustomsTextView tvPaymentStatusFailedMessage;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_status);
        unbinder = ButterKnife.bind(this);
        tvEditProfile.setPaintFlags(tvEditProfile.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        String message = getIntent().getStringExtra(Keys.MESSAGE);

        String plan = Keys.SUCCESS;
        if (plan.equalsIgnoreCase(Keys.SUCCESS)) {
            linearSuccessPayment.setVisibility(View.VISIBLE);
            linearFailurePayment.setVisibility(View.GONE);
            tvPaymentStatusFailedSuccess.setText("yes");
            imageViewStatus.setImageResource(R.drawable.icocheckmarkplein);
        } else {
            linearFailurePayment.setVisibility(View.VISIBLE);
            linearSuccessPayment.setVisibility(View.GONE);
            tvPaymentStatusFailedSuccess.setText(message);
            imageViewStatus.setImageResource(R.drawable.failed);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.btnPostJob, R.id.btnFindCandidate, R.id.btnSubscribe, R.id.tvContact, R.id.tvEditProfile})
    public void onViewClicked(View view) {
        Intent intent = new Intent(Keys.SUBSCRIPTION);
        switch (view.getId()) {
            case R.id.btnPostJob:
                finish();
                intent.putExtra(Keys.FOR, Keys.POST_JOB);
                sendBroadcast(intent);
                break;
            case R.id.btnFindCandidate:
                finish();
                intent.putExtra(Keys.FOR, Keys.VIEW_ONLINE_CANDIDATE);
                sendBroadcast(intent);
                break;
            case R.id.btnSubscribe:
                finish();
                intent.putExtra(Keys.FOR, Keys.SUBSCRIPTION);
                sendBroadcast(intent);
                break;
            case R.id.tvContact:
                finish();
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "contact@bonjob.co", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.give_my_opinion));
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(emailIntent, getString(R.string.send_email)));
                break;
            case R.id.tvEditProfile:
                finish();
                intent.putExtra(Keys.FOR, Keys.EDIT_PROFILE);
                sendBroadcast(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {

    }
}
