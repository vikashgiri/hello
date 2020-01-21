package com.infoicon.bonjob.recruiters.verify_phone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomEditText;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.response.GetInputCodeNumberResponse;
import com.infoicon.bonjob.retrofit.response.GetInputPhoneNumberResponse;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.CommonUtils;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

import static com.infoicon.bonjob.utils.UtilsMethods.showErrorAlert;

public class VerificationCodeActivity extends AppCompatActivity {


    @BindView(R.id.tvTitle) CustomsTextViewBold tvTitle;
    @BindView(R.id.edVerificationCode) CustomEditText edVerificationCode;
    String phoneNum = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_varification_code);
        ButterKnife.bind(this);
        initialize();

    }

    private void initialize() {
        tvTitle.setText(getResources().getString(R.string.varification_code));
        phoneNum = getIntent().getStringExtra(Keys.MOBILE_NUMBER);
    }


    /** go back to the previous page */
    @OnClick(R.id.textViewBack)
    void GoBack() {
        finish();
    }

    /** event for get country list */
    @OnClick(R.id.buttonConfirm)
    void verifyCode() {
        if (UtilsMethods.isEmpty(edVerificationCode)) {
            showErrorAlert(this,
                    getResources().getString(R.string.enter_code));
        } else {
            serviceVerifyCode();
        }
    }

    /** event for resend the code */
    @OnClick(R.id.tvResend)
    void resendCode() {
        serviceResendVerifyCodeSend();
    }


    /** call service for verify code */
    private void serviceVerifyCode() {
        final SpotsDialog spotsDialog = new SpotsDialog(this, R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.OTP, edVerificationCode.getText().toString().trim());
        retrofit.Call<GetInputCodeNumberResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getInputCodeNumberResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetInputCodeNumberResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetInputCodeNumberResponse> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();

                GetInputCodeNumberResponse getInputCodeNumberResponse = response.body();
                if (getInputCodeNumberResponse.isSuccess()) {
                    Singleton.getUserInfoInstance().setMobile_number(phoneNum);
                    Singleton.getUserInfoInstance().setLogin(true);
                    Intent intent = new Intent(VerificationCodeActivity.this, VerifiedPhoneNumberActivity.class);
                    startActivity(intent);
                } else {
                    if (getInputCodeNumberResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(VerificationCodeActivity.this, getInputCodeNumberResponse.getMsg());
                    } else
                        CommonUtils.showSimpleMessageBottom(VerificationCodeActivity.this, getInputCodeNumberResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(VerificationCodeActivity.this, t.getMessage());
            }
        });
    }

    /** call service for verify code */
    private void serviceResendVerifyCodeSend() {
        final SpotsDialog spotsDialog = new SpotsDialog(this, R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.USER_ID, Singleton.getUserInfoInstance().getUser_id());
        jsonObject.addProperty(Keys.MOBILE_NUMBER, phoneNum);
        retrofit.Call<GetInputPhoneNumberResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getInputPhoneNumberResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetInputPhoneNumberResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetInputPhoneNumberResponse> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                GetInputPhoneNumberResponse getInputPhoneNumberResponse = response.body();
                if (getInputPhoneNumberResponse.isSuccess()) {
                    CommonUtils.showSimpleMessageBottom(VerificationCodeActivity.this, getInputPhoneNumberResponse.getMsg());
                } else {
                    if (getInputPhoneNumberResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(VerificationCodeActivity.this, getInputPhoneNumberResponse.getMsg());
                    } else
                        CommonUtils.showSimpleMessageBottom(VerificationCodeActivity.this, getInputPhoneNumberResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(VerificationCodeActivity.this, t.getMessage());
            }
        });
    }


    /**
     * focus to view which view have invalid.
     * @param view
     */
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


}
