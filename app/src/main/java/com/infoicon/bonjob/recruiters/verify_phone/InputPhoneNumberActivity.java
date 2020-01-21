package com.infoicon.bonjob.recruiters.verify_phone;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomEditText;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
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

public class InputPhoneNumberActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();
    @BindView(R.id.tvTitle) CustomsTextViewBold tvTitle;
    @BindView(R.id.tvCountryCode) CustomsTextView tvCountryCode;
    @BindView(R.id.tvCountryName) CustomsTextView tvCountryName;
    @BindView(R.id.edMobileNumber) CustomEditText edMobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_phone_number);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        tvTitle.setText(getResources().getString(R.string.phone_number));
        edMobileNumber.setFilters(new InputFilter[]{filter});
    }

    /** go back to previous screen */
    @OnClick(R.id.textViewBack)
    void GoBack() {
        finish();
    }


    @OnClick(R.id.buttonValidate)
    void validate() {
        if (UtilsMethods.isEmpty(edMobileNumber)) {
            showErrorAlert(this,getResources().getString(R.string.enter_mobile_number));
            requestFocus(edMobileNumber);
        } else {
            serviceVerifyMobNumber();
        }

    }

    /** event for get country list */
    @OnClick(R.id.tvCountryName)
    void getCountryList() {
        countryCodeDialog();
    }

    /** method for open dialog to get country list */
    public void countryCodeDialog() {
        final Dialog dialogCountryCode = new Dialog(this);
        dialogCountryCode.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialogCountryCode.getWindow() != null)
            dialogCountryCode.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogCountryCode.setContentView(R.layout.dialog_type_of_contract);
        CustomsTextViewBold tvTitle = (CustomsTextViewBold) dialogCountryCode.findViewById(R.id.tvTitle);
        tvTitle.setText(getResources().getString(R.string.country));
        ListView listView = (ListView) dialogCountryCode.findViewById(R.id.listView);
        ImageView imageViewClose = (ImageView) dialogCountryCode.findViewById(R.id.imageViewClose);
        String[] arrayCountryName = getResources().getStringArray(R.array.array_country_name);
        String[] arrayCountryCode = getResources().getStringArray(R.array.array_country_code);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item_single_choice, arrayCountryName);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setAdapter(arrayAdapter);
        dialogCountryCode.show();
        listView.setOnItemClickListener((parent, view, position, id) -> {
            tvCountryName.setText(arrayCountryName[position]);
            tvCountryCode.setText(arrayCountryCode[position]);
            dialogCountryCode.dismiss();
        });
        imageViewClose.setOnClickListener(v -> dialogCountryCode.dismiss());
    }




    /** filter to remove whitespace from editext */
    InputFilter filter = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            String filtered = "";
            for (int i = start; i < end; i++) {
                char character = source.charAt(i);
                if (dstart == 0) {
                    char character2 = source.charAt(start);
                    if (character2 != '0' && !Character.isWhitespace(character)) {
                        filtered += character;
                    }
                } else {
                    if (!Character.isWhitespace(character)) {
                        filtered += character;
                    }
                }

            }


            return filtered;
        }

    };

    /** call service for verify number */
    private void serviceVerifyMobNumber() {

        String phoneNum = tvCountryCode.getText().toString().trim() + "" + edMobileNumber.getText().toString().trim();
        phoneNum = phoneNum.substring(1, phoneNum.length());
        // String phoneNum="919069720406";
        final SpotsDialog spotsDialog = new SpotsDialog(this, R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.USER_ID, Singleton.getUserInfoInstance().getUser_id());
        jsonObject.addProperty(Keys.MOBILE_NUMBER, phoneNum);
        retrofit.Call<GetInputPhoneNumberResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getInputPhoneNumberResponse(jsonObject);
        String finalPhoneNum = phoneNum;
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetInputPhoneNumberResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetInputPhoneNumberResponse> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                GetInputPhoneNumberResponse getInputPhoneNumberResponse = response.body();
                if (getInputPhoneNumberResponse.isSuccess()) {
                    Intent intent = new Intent(InputPhoneNumberActivity.this, VerificationCodeActivity.class);
                    intent.putExtra(Keys.MOBILE_NUMBER, finalPhoneNum);
                    startActivity(intent);
                } else {
                    if (getInputPhoneNumberResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(InputPhoneNumberActivity.this, getInputPhoneNumberResponse.getMsg());
                    } else
                        CommonUtils.showSimpleMessageBottom(InputPhoneNumberActivity.this, getInputPhoneNumberResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(InputPhoneNumberActivity.this, t.getMessage());
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
