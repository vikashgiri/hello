package com.infoicon.bonjob.forgotPassword;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomEditText;
import com.infoicon.bonjob.customview.CustomsButton;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.response.GetForgotPasswordResponse;
import com.infoicon.bonjob.utils.CommonUtils;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

import static com.infoicon.bonjob.utils.UtilsMethods.showErrorAlert;

public class ForgetPasswordActivity extends Activity
{

    @BindView(R.id.tvTitle) CustomsTextViewBold tvTitle;
    @BindView(R.id.edEmail) CustomEditText edEmail;
    @BindView(R.id.btnSubmit) CustomsButton btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize()
    {
        tvTitle.setText(getResources().getString(R.string.forgot_pwd));
    }


    @OnClick(R.id.textViewBack)
    void GoBack()
    {
        finish();
    }

    @OnClick(R.id.btnSubmit)
    void submit() {
        if (validateEmail()) {
            callWebServiceForgotPassword();
        }
    }


    /**
     * call web service for get login
     */
    private void callWebServiceForgotPassword()
    {
        final SpotsDialog spotsDialog = new SpotsDialog(this, R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty(Keys.EMAIL, edEmail.getText().toString().trim());
        retrofit.Call<GetForgotPasswordResponse> call;
        if(getIntent().getStringExtra("type").equalsIgnoreCase("Seeker"))
        {

            call = AppRetrofit.getAppRetrofitInstance().getApiServices()
                    .getEmpForgotPasswordResponse(jsonObject);}
        else {
            call = AppRetrofit.getAppRetrofitInstance().getApiServices()
                    .getForgotPasswordResponse(jsonObject);
        }
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetForgotPasswordResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetForgotPasswordResponse> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                GetForgotPasswordResponse getForgotPasswordResponse = response.body();
                if (getForgotPasswordResponse.isSuccess()) {
                    openAlert(getForgotPasswordResponse.getMsg());
                } else {
                    CommonUtils.showSimpleMessageBottom(ForgetPasswordActivity.this,
                            getForgotPasswordResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(ForgetPasswordActivity.this, t.getMessage());
            }
        });
    }


    public void openAlert(String message)
    {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_alert_ok);
        dialog.setCancelable(false);
        CustomsTextView textViewTitle = (CustomsTextView) dialog.findViewById(R.id.textViewTitle);
        CustomsTextView textViewMessage = (CustomsTextView) dialog.findViewById(R.id.textViewMessage);
        CustomsButton buttonOk = (CustomsButton) dialog.findViewById(R.id.buttonOk);
        textViewTitle.setText(getResources().getString(R.string.app_name));
        textViewMessage.setText(message);
        buttonOk.setOnClickListener(view -> {
            dialog.dismiss();
            finish();
        });
        dialog.show();
    }


    private boolean validateEmail()
    {
        if (UtilsMethods.isEmpty(edEmail))
        {
            showErrorAlert(this,getResources().getString(R.string.email_empty_message));
            requestFocus(edEmail);
            return false;
        }
        if (!UtilsMethods.isValidEmail(edEmail.getText().toString().trim())) {
            showErrorAlert(this,getResources().getString(R.string.email_invalid_message));
            requestFocus(edEmail);
            return false;
         }
        return true;
    }

    /**
     * focus to view which view have invalid.
     * @param view
     */
    private void requestFocus(View view)
    {
        if (view.requestFocus())
        {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
