package com.infoicon.bonjob.recruiters.subscription;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomsButton;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.dialogs.SubscribePlanDialogFragment;
import com.infoicon.bonjob.dialogs.ValidatePaymentDialogFragment;
import com.infoicon.bonjob.main.HomeRecruiterActivity;
import com.infoicon.bonjob.recruiters.adapter.SubscriptionPlanAdapter;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.response.GetSubscriptionListResponse;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.CommonUtils;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;

import static android.app.Activity.RESULT_OK;


public class SubscriptionFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.textViewBack) CustomsTextView textViewBack;
    @BindView(R.id.tvTitle) CustomsTextViewBold tvTitle;
    @BindView(R.id.btnSubscribe) CustomsButton btnSubscribe;
    @BindView(R.id.linearNoSubscription) LinearLayout linearNoSubscription;
    @BindView(R.id.btnRenew) CustomsButton btnRenew;
    @BindView(R.id.btnTerminate) CustomsButton btnTerminate;
    @BindView(R.id.linearWithSubscription) LinearLayout linearWithSubscription;
    @BindView(R.id.recyclerViewPlan) RecyclerView recyclerViewPlan;
    @BindView(R.id.tvExpireDate) CustomsTextView tvExpireDate;
    @BindView(R.id.scrollView) NestedScrollView scrollView;
    private SubscriptionPlanAdapter subscriptionPlanAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_subscription, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        callServiceForSubscriptioPlan();
        return view;
    }

    /** init here */
    private void init() {
        recyclerViewPlan.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewPlan.setHasFixedSize(false);
        recyclerViewPlan.setLayoutManager(linearLayoutManager);
        subscriptionPlanAdapter = new SubscriptionPlanAdapter(getActivity(), new SubscriptionPlanAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GetSubscriptionListResponse.DataBean dataBean) {
                validatePayment(dataBean);
            }
        });
        recyclerViewPlan.setAdapter(subscriptionPlanAdapter);

       /*if (Singleton.getSubscriptionListAndMyPlan().getSubscriptionList() != null &&
                Singleton.getSubscriptionListAndMyPlan().getSubscriptionList().size() > 0) {
        }else {
            callServiceForSubscriptioPlan();
        }*/
    }

    /** go to payment plan dialog */
    private void validatePayment(GetSubscriptionListResponse.DataBean dataBean) {
        FragmentManager fm = getFragmentManager();
        ValidatePaymentDialogFragment experienceDialogFragment = new ValidatePaymentDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Keys.SUBSCRIPTION, dataBean);
        experienceDialogFragment.setArguments(bundle);
        experienceDialogFragment.setTargetFragment(this, Keys.REQUEST_CODE_SUBSCRIPTION_FORM);
        experienceDialogFragment.show(fm, Keys.TAG_SUBSCRIPTION_FORM);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textViewBack.setText(getString(R.string.settings));
        tvTitle.setText(getString(R.string.subscription));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.textViewBack, R.id.btnSubscribe, R.id.btnRenew, R.id.btnTerminate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.textViewBack:
                getActivity().onBackPressed();
                break;
            case R.id.btnSubscribe:
                purchasePlan();
                break;
            case R.id.btnRenew:
                purchasePlan();
                break;
            case R.id.btnTerminate:
                dialogSubscriptionTerminateConfirmation();
                break;
        }
    }

    /** method to show the purchase plan */
    private void purchasePlan()
    {
        FragmentManager fm = getFragmentManager();
        SubscribePlanDialogFragment experienceDialogFragment = new SubscribePlanDialogFragment();
        experienceDialogFragment.show(fm, Keys.TAG_SUBSCRIBE);
    }

    /** get the subscription list and your current plan */
    private void callServiceForSubscriptioPlan() {
        final SpotsDialog spotsDialog = new SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        retrofit.Call<GetSubscriptionListResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getSubscriptionListResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetSubscriptionListResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(retrofit.Response<GetSubscriptionListResponse> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                scrollView.setVisibility(View.VISIBLE);
                GetSubscriptionListResponse getSubscriptionListResponse = response.body();
                if (getSubscriptionListResponse.isSuccess()) {
                    Singleton.getSubscriptionListAndMyPlan().setSubscriptionList(getSubscriptionListResponse.getData());
                    Singleton.getSubscriptionListAndMyPlan().setCurrentPlan(getSubscriptionListResponse.getCurrentPlan());

                    if (getSubscriptionListResponse.getCurrentPlan().getExpiredOn().equalsIgnoreCase("")) {
                        linearNoSubscription.setVisibility(View.VISIBLE);
                        linearWithSubscription.setVisibility(View.GONE);
                    } else {
                        tvExpireDate.setText(getString(R.string.subscription_expire_on) + " " + UtilsMethods.convertDateFormat2(getSubscriptionListResponse.getCurrentPlan().getExpiredOn()));
                        linearNoSubscription.setVisibility(View.GONE);
                        linearWithSubscription.setVisibility(View.VISIBLE);

                        for (int i = 0; i < getSubscriptionListResponse.getData().size(); i++) {
                            if (getSubscriptionListResponse.getData().get(i).getSubscription_id().equalsIgnoreCase(getSubscriptionListResponse.getCurrentPlan().getSubscription_id())) {
                                subscriptionPlanAdapter.addSubscription(getSubscriptionListResponse.getData().get(i));
                            }
                        }
                    }

                } else {
                    if (getSubscriptionListResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(getActivity(), getSubscriptionListResponse.getMsg());
                    } //else
                    // CommonUtils.showSimpleMessageBottom(HomeRecruiterActivity.this, getSubscriptionListResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(getActivity(), t.getMessage());
            }
        });
    }

    /** dialog to show confirmation for subscription termination */
    public void dialogSubscriptionTerminateConfirmation() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_cancel_subscription);
        CustomsButton btnConfirm = dialog.findViewById(R.id.btnConfirm);
        CustomsButton btnCancel = dialog.findViewById(R.id.btnCancel);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
