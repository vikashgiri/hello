package com.infoicon.bonjob.setting;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomsButton;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.main.HomeSeekerActivity;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.response.GetNotiEmailResponse;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.CommonUtils;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by infoicon on 8/6/17.
 */

public class SettingSeekerFragment extends Fragment {


    private View rootView;
    @BindView(R.id.buttonSignOut) CustomsButton buttonSignOut;
    @BindView(R.id.tvTitle) CustomsTextViewBold tvTitle;
    private Unbinder unbinder;
    @BindView(R.id.toogleButtonReciveNotification) Switch toogleButtonReciveNotification;
    @BindView(R.id.toogleButtonReciveEmails) Switch toogleButtonReciveEmails;
    @BindView(R.id.rlSubscription) RelativeLayout rlSubscription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        init();
        listener();
        return rootView;
    }

    private void init() {
        if (Singleton.getUserInfoInstance().getEmail_notification().equals("1"))
        {
            toogleButtonReciveEmails.setChecked(true);
        } else {
            toogleButtonReciveEmails.setChecked(false);
        }

        if (Singleton.getUserInfoInstance().getMobile_notification().equals("1")) {
            toogleButtonReciveNotification.setChecked(true);
        } else {
            toogleButtonReciveNotification.setChecked(false);
        }

        rlSubscription.setVisibility(View.GONE);

    }

    /* listener on view*/
    private void listener() {
        toogleButtonReciveNotification.setOnCheckedChangeListener(onCheckedChangeListener);
        toogleButtonReciveEmails.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    /** switch listener implementation */
    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.toogleButtonReciveNotification:
                    if (isChecked) {
                        serviceEnableDisable(Singleton.getUserInfoInstance().getEmail_notification(), "1");
                    } else {
                        serviceEnableDisable(Singleton.getUserInfoInstance().getEmail_notification(), "2");
                    }

                    break;
                case R.id.toogleButtonReciveEmails:
                    if (isChecked) {
                        serviceEnableDisable("1", Singleton.getUserInfoInstance().getMobile_notification());
                    } else {
                        serviceEnableDisable("2", Singleton.getUserInfoInstance().getMobile_notification());
                    }
                    break;
            }
        }
    };

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTextStyleBold();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void setTextStyleBold() {
        //  buttonSignOut.setTextAppearance(getActivity(), R.style.boldText);
        tvTitle.setTextAppearance(getActivity(), R.style.boldText);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeSeekerActivity) getActivity()).setCheckedToBottom(Keys.PROFILE);
    }

    @OnClick(R.id.rlFaq)
    void openFaq() {
        ((HomeSeekerActivity) getActivity()).addFragment(new FaqFragment(), new Bundle(), Keys.FAQ, false, true);
    }


    @OnClick(R.id.textViewBack)
    void GoBack() {
        (getActivity()).onBackPressed();
    }

    @OnClick(R.id.rlGiveMyOpinion)
    void giveMyOpinion() {
        dialogGiveMyOpenion();
    }

    @OnClick(R.id.rlTermsOfUse)
    void termsOfUse() {
        dialogTermsOfUse();
    }

    @OnClick(R.id.buttonSignOut)
    void signout() {
        UtilsMethods.logoutAlert(getActivity());
    }

    /**
     * method for open dialog post held
     */
    public void dialogGiveMyOpenion() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_give_my_opinion);
        CustomsTextViewBold textViewcancel = (CustomsTextViewBold) dialog.findViewById(R.id.textViewcancel);
        CustomsTextViewBold tvSendEmail = (CustomsTextViewBold) dialog.findViewById(R.id.tvSendEmail);
        CustomsTextViewBold tvTitle = (CustomsTextViewBold) dialog.findViewById(R.id.tvTitle);
        tvTitle.setTextAppearance(getActivity(), R.style.boldText);
        tvSendEmail.setTextAppearance(getActivity(), R.style.boldText);
        textViewcancel.setTextAppearance(getActivity(), R.style.boldText);
        textViewcancel.setOnClickListener(v -> dialog.dismiss());
        tvSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "contact@bonjob.co", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Bonjob");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Your query here");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });
        dialog.show();
    }

    /**
     * method to show dialog for terms of use and privacy policy.
     */
    public void dialogTermsOfUse() {
        final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_terms_of_use);
        LinearLayout linearCancel = (LinearLayout) dialog.findViewById(R.id.linearCancel);
        CustomsTextView textViewTermsOfUseInside = (CustomsTextView) dialog.findViewById(R.id.textViewTermsOfUseInside);
        CustomsTextView textViewPrivacyPolicy = (CustomsTextView) dialog.findViewById(R.id.textViewPrivcyPolicy);
        assert textViewTermsOfUseInside != null;
        textViewTermsOfUseInside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                UtilsMethods.openBrowser(getActivity(), Keys.TERM_OF_USE_URL);
                // ((HomeSeekerActivity)getActivity()).addFragment(new TermsOfUseFragment(),new Bundle(),Keys.TERMS_OF_USE,false,true);
            }
        });

        assert textViewPrivacyPolicy != null;
        textViewPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                UtilsMethods.openBrowser(getActivity(), Keys.PRIVACY_POLICY_URL);
                //  ((HomeSeekerActivity)getActivity()).addFragment(new PrivacyPolicyFragment(),new Bundle(),Keys.PRIVACY_POLICY,false,true);
            }
        });

        assert linearCancel != null;
        linearCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    /** call service for enable disable email and notification */
    private void serviceEnableDisable(String email, String mobile) {
        final SpotsDialog spotsDialog = new SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.USER_ID, Singleton.getUserInfoInstance().getUser_id());
        jsonObject.addProperty(Keys.EMAIL_NOTIFICATION, email);
        jsonObject.addProperty(Keys.MOBILE_NOTIFICATION, mobile);
        Call<GetNotiEmailResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getNotificationResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new Callback<GetNotiEmailResponse>() {
            @Override
            public void onResponse(Response<GetNotiEmailResponse> response, Retrofit retrofit) {
                if (spotsDialog.isShowing())
                    spotsDialog.dismiss();

                GetNotiEmailResponse getNotiEmailResponse = response.body();
                if (getNotiEmailResponse.isSuccess()) {
                    Singleton.getUserInfoInstance().setMobile_notification(getNotiEmailResponse.getData().getMobile_notification());
                    Singleton.getUserInfoInstance().setEmail_notification(getNotiEmailResponse.getData().getEmail_notification());
                    CommonUtils.showSimpleMessageBottom(getActivity(), getNotiEmailResponse.getMsg());
                } else {
                    if (getNotiEmailResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(getActivity(), getNotiEmailResponse.getMsg());
                    } else
                        CommonUtils.showSimpleMessageBottom(getActivity(), getNotiEmailResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(getActivity(), t.getMessage());
            }
        });
    }

}
