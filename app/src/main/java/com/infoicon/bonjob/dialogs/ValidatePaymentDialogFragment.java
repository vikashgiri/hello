package com.infoicon.bonjob.dialogs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomEditText;
import com.infoicon.bonjob.customview.CustomsButton;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.main.HomeRecruiterActivity;
import com.infoicon.bonjob.payment.PaymentStatusActivity;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.response.GetMakePaymentResponse;
import com.infoicon.bonjob.retrofit.response.GetSubscriptionListResponse;
import com.infoicon.bonjob.retrofit.response.GetSubscriptionListResponse.CurrentPlanBean;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.CommonUtils;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;
import com.infoicon.bonjob.utils.textWatcher.CreditCardEditText;
import com.infoicon.bonjob.utils.textWatcher.OtherCardTextWatcher;
import com.infoicon.bonjob.utils.textWatcher.TwoDigitsCardTextWatcher;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;

import static com.infoicon.bonjob.utils.UtilsMethods.showErrorAlert;

/**
 * Created by infoicon on 15/7/17.
 * this class is for getting salary list  and showing it in dialog.
 */

public class ValidatePaymentDialogFragment extends DialogFragment {
    @BindView(R.id.tvSubscriptionPlan) CustomsTextView tvSubscriptionPlan;
    @BindView(R.id.tvSubscriptionPlanPrice) CustomsTextView tvSubscriptionPlanPrice;
    @BindView(R.id.tvTotalHtPrice) CustomsTextView tvTotalHtPrice;
    @BindView(R.id.rlTotalHt) RelativeLayout rlTotalHt;
    @BindView(R.id.tvTotalTtcPrice) CustomsTextViewBold tvTotalTtcPrice;
    @BindView(R.id.edFName) CustomEditText edFName;
    @BindView(R.id.edLName) CustomEditText edLName;
    @BindView(R.id.edEnterprise) CustomEditText edEnterprise;
    @BindView(R.id.edCodePostal) CustomEditText edCodePostal;
    @BindView(R.id.edLocation) CustomEditText edLocation;
    @BindView(R.id.edEmail) CustomEditText edEmail;
    @BindView(R.id.edCardNumber) CreditCardEditText edCardNumber;
    @BindView(R.id.edExpiry) CustomEditText edExpiry;
    @BindView(R.id.edCvc) CustomEditText edCvc;
    @BindView(R.id.btnValidatePayment) CustomsButton btnValidatePayment;
    @BindView(R.id.imageViewClose) ImageView imageViewClose;
    @BindView(R.id.dialog_ludovic) RelativeLayout dialogLudovic;
    private Unbinder unbinder;
    private GetSubscriptionListResponse.DataBean subscriptionPlan;
    private SpotsDialog spotsDialog;
    private String subscription_id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getDialog().getWindow() != null)
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(false);
        View rootView = inflater.inflate(R.layout.dialog_payment_option, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getBundleData();
        init();
    }

    /** get intents data */
    @SuppressLint("SetTextI18n")
    private void getBundleData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            subscriptionPlan = bundle.getParcelable(Keys.SUBSCRIPTION);
            if (subscriptionPlan != null) {
                rlTotalHt.setVisibility(View.GONE);
                subscription_id = subscriptionPlan.getSubscription_id();
                tvSubscriptionPlan.setText(getString(R.string.subscription) + " " + subscriptionPlan.getSubscription_title() + ":");
                String arrayPlan[] = subscriptionPlan.getDescription().split("€");
                /*if(Singleton.getUserInfoInstance().getEmail().equalsIgnoreCase("bonjobcontact@gmail.com"))
                {
                if (Integer.parseInt(subscriptionPlan.getTime_period().trim()) > 3)
                {
                   //edEmail.setText(Singleton.getUserInfoInstance().getEmail());
                    //year
                    //bonjobcontact@gmail.com
                    tvSubscriptionPlanPrice.setText(arrayPlan[0] + getString(R.string.euro) + " HT par " + getString(R.string.month));
                    rlTotalHt.setVisibility(View.VISIBLE);
                    tvTotalHtPrice.setText(subscriptionPlan.getAmount() + getString(R.string.euro));
                    tvTotalTtcPrice.setText(subscriptionPlan.getTotal_amount() + getString(R.string.euro));
                return;
                }
                }*/
                if (Integer.parseInt(subscriptionPlan.getTime_period().trim()) > 1) {
                    //year
                    //bonjobcontact@gmail.com
                    tvSubscriptionPlanPrice.setText(arrayPlan[0] + getString(R.string.euro) + "/" + getString(R.string.month));
                    rlTotalHt.setVisibility(View.VISIBLE);
                    tvTotalHtPrice.setText(subscriptionPlan.getAmount() + getString(R.string.euro));
                    tvTotalTtcPrice.setText(subscriptionPlan.getTotal_amount() + getString(R.string.euro));
                } else {
                    //month
                    tvSubscriptionPlanPrice.setText(arrayPlan[0] + getString(R.string.euro));
                    rlTotalHt.setVisibility(View.GONE);
                    tvTotalTtcPrice.setText(subscriptionPlan.getTotal_amount()+ getString(R.string.euro));
                }
            }
        }
    }

    /** init view */
    private void init()
    {
        edFName.setSingleLine(true);
        edLName.setSingleLine(true);
        edEnterprise.setSingleLine(true);
        edCodePostal.setSingleLine(true);
        edLocation.setSingleLine(true);
        edEmail.setSingleLine(true);
        edCardNumber.setSingleLine(true);
        edExpiry.setSingleLine(true);
        edCvc.setSingleLine(true);
        edCardNumber.addTextChangedListener(new OtherCardTextWatcher(edCardNumber));
        edExpiry.addTextChangedListener(new TwoDigitsCardTextWatcher(edExpiry));
        edFName.setText(Singleton.getUserInfoInstance().getFirst_name());
        edLName.setText(Singleton.getUserInfoInstance().getLast_name());
        edEnterprise.setText(Singleton.getUserInfoInstance().getEnterprise_name());
        edLocation.setText(Singleton.getUserInfoInstance().getCompanyAddress());
        edEmail.setText(Singleton.getUserInfoInstance().getEmail());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnValidatePayment, R.id.imageViewClose})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnValidatePayment:
                if (validateField()) {
                    String cardNumber = edCardNumber.getText().toString().trim();
                    String[] expArray = edExpiry.getText().toString().trim().split("/");
                    int cardExpMonth = Integer.parseInt(expArray[0]);
                    int cardExpYear = Integer.parseInt(expArray[1]);
                    String cardCVC = edCvc.getText().toString().trim();
                    payment(cardNumber, cardExpMonth, cardExpYear, cardCVC);
                }

                break;
            case R.id.imageViewClose:
                getDialog().dismiss();
                break;
        }
    }

    /**
     * payment with stripe
     * @param cardNumber   card number
     * @param cardExpMonth card expiry month
     * @param cardExpYear  card expiry year
     * @param cardCVC      cvc number of card
     */
    private void payment(String cardNumber, int cardExpMonth, int cardExpYear, String cardCVC) {
        Card card = new Card(cardNumber, cardExpMonth, cardExpYear, cardCVC);
        if (!card.validateCard()) {
            showErrorAlert(getActivity(),getString(R.string.invalid_card_number));
            requestFocus(edCardNumber);
        } else if (!card.validateExpiryDate())
        {
            showErrorAlert(getActivity(),getString(R.string.invalid_ex_date));
            requestFocus(edExpiry);
        } else if (!card.validateCVC()) {
            showErrorAlert(getActivity(),getString(R.string.invalid_cvc));
            requestFocus(edCvc);
        } else {
            spotsDialog = new SpotsDialog(getActivity(), R.style.Custom);
            spotsDialog.setCancelable(false);
            //    spotsDialog.setMessage(getString(R.string.payment_processing_message));
            spotsDialog.show();

            //Testing key : pk_test_xd43uQWxFtmWECZ6tMYLBGjj
            //Testing key : pk_test_2C79AXRNIUAygbDIVDztPLkN
            // Live Key   : pk_live_mwjwNKGvwpHzhdg7qlCH3whf

            Stripe stripe = new Stripe(getActivity(), "pk_live_mwjwNKGvwpHzhdg7qlCH3whf");
            stripe.createToken(card, new TokenCallback() {
                @Override
                public void onError(Exception e)
                {
                    if (spotsDialog != null && spotsDialog.isShowing())
                        spotsDialog.dismiss();
                    Logger.e(e.getLocalizedMessage());
                    UtilsMethods.openAlert(e.getLocalizedMessage(), getActivity());
                }

                @Override
                public void onSuccess(Token token) {
                    Logger.e("Token Id : " + token.getId());
                    callServiceForMakePayment(token.getId());
                }
            });
        }
    }

    /**
     * get the subscription list and your current plan
     * @param token
     */
    private void callServiceForMakePayment(String token)
    {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("stripe_token", token);
        jsonObject.addProperty("first_name", edFName.getText().toString().trim());
        jsonObject.addProperty("last_name", edLName.getText().toString().trim());
        jsonObject.addProperty("subscription_id", subscription_id);
        jsonObject.addProperty("enterprise", edEnterprise.getText().toString().trim());
        jsonObject.addProperty("email", edEmail.getText().toString().trim());
        jsonObject.addProperty("location", edLocation.getText().toString().trim());
        jsonObject.addProperty("postal_code", edCodePostal.getText().toString().trim());
        retrofit.Call<GetMakePaymentResponse> call = AppRetrofit.getAppRetrofitInstance()
                .getApiServices().getMakePaymentResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetMakePaymentResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetMakePaymentResponse> response,
                                   retrofit.Retrofit retrofit) {
                if (spotsDialog != null && spotsDialog.isShowing())
                    spotsDialog.dismiss();
                getDialog().dismiss();

                GetMakePaymentResponse getSubscriptionListResponse = response.body();
                if (getSubscriptionListResponse.isSuccess()) {
                    CurrentPlanBean currentPlanBean = new CurrentPlanBean();
                    currentPlanBean.setSubscription_id(getSubscriptionListResponse.getData().getSubscription_id());
                    currentPlanBean.setAmount(getSubscriptionListResponse.getData().getAmount());
                    currentPlanBean.setTotal_amount(getSubscriptionListResponse.getData().getTotal_amount());
                    currentPlanBean.setTime_period(getSubscriptionListResponse.getData().getTime_period());
                    currentPlanBean.setSubscription_title(getSubscriptionListResponse.getData().getSubscription_title());
                    currentPlanBean.setDescription(getSubscriptionListResponse.getData().getDescription());
                    currentPlanBean.setCurrent_server_date(getSubscriptionListResponse.getData().getCurrent_server_date());
                    currentPlanBean.setExpiredOn(getSubscriptionListResponse.getData().getExpiredOn());
                    currentPlanBean.setSearch_candidate_count(getSubscriptionListResponse.getData().getSearch_candidate_count());
                    currentPlanBean.setJob_post_count(getSubscriptionListResponse.getData().getJob_post_count());
                    Singleton.getSubscriptionListAndMyPlan().setCurrentPlan(currentPlanBean);
                    Intent intent = new Intent(getActivity(), PaymentStatusActivity.class);
                    intent.putExtra(Keys.MESSAGE, getSubscriptionListResponse.getMsg());
                    intent.putExtra(Keys.PLAN, Keys.SUCCESS);
                    startActivity(intent);
                } else {
                    if (getSubscriptionListResponse.getActive_user().
                            equals(Keys.AUTH_CODE))
                    {
                        UtilsMethods.unAuthorizeAlert(getActivity(), getSubscriptionListResponse.getMsg());
                    } else
                        {
                        Intent intent = new Intent(getActivity(), PaymentStatusActivity.class);
                        intent.putExtra(Keys.MESSAGE, getSubscriptionListResponse.getMsg());
                        intent.putExtra(Keys.PLAN, Keys.FAILED);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (spotsDialog != null && spotsDialog.isShowing())
                    spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(getActivity(), t.getMessage());
            }
        });
    }

    /** validate field */
    private boolean validateField() {
        if (UtilsMethods.isEmpty(edFName)) {
            showErrorAlert(getActivity(),getString(R.string.firstName_empty_message));
            requestFocus(edFName);
            return false;
        } else if (UtilsMethods.isEmpty(edLName)) {
            showErrorAlert(getActivity(),getString(R.string.name_empty_message));
            requestFocus(edLName);
            return false;
        } else if (UtilsMethods.isEmpty(edEnterprise)) {
            showErrorAlert(getActivity(),getString(R.string.enterprise_empty_message));
            requestFocus(edEnterprise);
            return false;
        } else if (UtilsMethods.isEmpty(edCodePostal)) {
            showErrorAlert(getActivity(),getString(R.string.empty_postal_code));
            requestFocus(edCodePostal);
            return false;
        } else if (UtilsMethods.isEmpty(edLocation)) {
            showErrorAlert(getActivity(),getString(R.string.location_empty_message));
            requestFocus(edLocation);
            return false;
        } else if (UtilsMethods.isEmpty(edEmail)) {
            showErrorAlert(getActivity(),getString(R.string.email_empty_message));
            requestFocus(edEmail);
            return false;
        } else if (!UtilsMethods.isValidEmail(edEmail.getText().toString().trim())) {
            showErrorAlert(getActivity(),getResources().getString(R.string.email_invalid_message));
            requestFocus(edEmail);
            return false;
        } else if (UtilsMethods.isEmpty(edCardNumber)) {
            showErrorAlert(getActivity(),getString(R.string.empty_card_msg));
            requestFocus(edCardNumber);
            return false;
        } else if (UtilsMethods.isEmpty(edExpiry)) {
            showErrorAlert(getActivity(),getString(R.string.empty_ex_date_msg));
            requestFocus(edExpiry);
            return false;
        } else if (edExpiry.getText().toString().trim().length() < 5) {
            showErrorAlert(getActivity(),getString(R.string.invalid_ex_date));
            requestFocus(edExpiry);
            return false;
        } else if (UtilsMethods.isEmpty(edCvc)) {
            showErrorAlert(getActivity(),getString(R.string.empty_cvc_msg));
            requestFocus(edCvc);
            return false;
        }
        return true;
    }

    /**
     * focus to view which view have invalid.
     * @param view
     */
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


}
