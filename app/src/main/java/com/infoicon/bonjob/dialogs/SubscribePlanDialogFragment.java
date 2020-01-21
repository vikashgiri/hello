package com.infoicon.bonjob.dialogs;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;

import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.recruiters.adapter.SubscriptionPlanAdapter;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.response.GetSubscriptionListResponse;
import com.infoicon.bonjob.retrofit.response.GetSubscriptionListResponse.DataBean;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.CommonUtils;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * Created by infoicon on 10/2/18.
 * this class is for getting salary list and showing it in dialog.
 */

public class SubscribePlanDialogFragment extends DialogFragment {

    private Unbinder unbinder;
    @BindView(R.id.recyclerViewPlan) RecyclerView recyclerViewPlan;
    @BindView(R.id.progress_bar) ProgressBar progress_bar;
    private SubscriptionPlanAdapter subscriptionPlanAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getDialog().getWindow() != null)
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(false);
        View rootView = inflater.inflate(R.layout.dialog_payment_pricing, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    /** init view */
    private void init() {
        recyclerViewPlan.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewPlan.setHasFixedSize(false);
        recyclerViewPlan.setLayoutManager(linearLayoutManager);
        subscriptionPlanAdapter = new SubscriptionPlanAdapter(getActivity(), new SubscriptionPlanAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DataBean dataBean) {
                validatePayment(dataBean);
            }
        });
        recyclerViewPlan.setAdapter(subscriptionPlanAdapter);
        if (Singleton.getSubscriptionListAndMyPlan().getSubscriptionList() != null &&
                Singleton.getSubscriptionListAndMyPlan().getSubscriptionList().size() > 0) {
            subscriptionPlanAdapter.addAllSubscription(Singleton.getSubscriptionListAndMyPlan().getSubscriptionList());
        } else {
            callServiceForSubscriptionPlan();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.imageViewClose})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewClose:
                getDialog().dismiss();
                break;
        }
    }

    private void validatePayment(DataBean dataBean) {
        FragmentManager fm = getFragmentManager();
        ValidatePaymentDialogFragment experienceDialogFragment = new ValidatePaymentDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Keys.SUBSCRIPTION, dataBean);
        experienceDialogFragment.setArguments(bundle);
        experienceDialogFragment.setTargetFragment(this, Keys.REQUEST_CODE_SUBSCRIPTION_FORM);
        experienceDialogFragment.show(fm, Keys.TAG_SUBSCRIPTION_FORM);
        getDialog().dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Keys.REQUEST_CODE_SUBSCRIPTION_FORM:
                if (resultCode == RESULT_OK && data != null) {
                }
                break;
        }
    }

    /** show progress */
    private void showProgress() {
        progress_bar.setVisibility(View.VISIBLE);
    }

    /** hide progress */
    private void hideProgress() {
        progress_bar.setVisibility(View.GONE);
    }

    /** get the subscription list and your current plan */
    private void callServiceForSubscriptionPlan()
    {
        showProgress();
        JsonObject jsonObject = new JsonObject();
        retrofit.Call<GetSubscriptionListResponse> call = AppRetrofit.
                getAppRetrofitInstance().getApiServices().getSubscriptionListResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetSubscriptionListResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetSubscriptionListResponse> response, retrofit.Retrofit retrofit) {
                hideProgress();
                GetSubscriptionListResponse getSubscriptionListResponse = response.body();
                if (getSubscriptionListResponse.isSuccess()) {
                    Singleton.getSubscriptionListAndMyPlan().setSubscriptionList(getSubscriptionListResponse.getData());
                    Singleton.getSubscriptionListAndMyPlan().setCurrentPlan(getSubscriptionListResponse.getCurrentPlan());
                    subscriptionPlanAdapter.addAllSubscription(getSubscriptionListResponse.getData());
                } else {
                    if (getSubscriptionListResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(getActivity(), getSubscriptionListResponse.getMsg());
                    } else
                        CommonUtils.showSimpleMessageBottom(getActivity(), getSubscriptionListResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                hideProgress();
                CommonUtils.showSimpleMessageBottom(getActivity(), t.getMessage());
            }
        });
    }
}
