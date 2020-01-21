package com.infoicon.bonjob.seeker.myOffers;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomsButton;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.main.HomeSeekerActivity;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.response.GetMyOfferSeekerResponse;
import com.infoicon.bonjob.seeker.searchJob.SearchJobFragment;
import com.infoicon.bonjob.singletons.Singleton;
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


public class MyOfferSeekerFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.textViewGuide) CustomsTextViewBold textViewGuide;
    @BindView(R.id.btnJobSearch) CustomsButton btnJobSearch;
    @BindView(R.id.tvErrorMessage) CustomsTextViewBold tvErrorMessage;
    @BindView(R.id.btnRetry) CustomsButton btnRetry;
    @BindView(R.id.rlRetry) RelativeLayout rlRetry;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.tvTitle) CustomsTextViewBold tvTitle;
    @BindView(R.id.svDefaultLayout) ScrollView svDefaultLayout;

    private MyOfferSeekerAdapter myOfferSeekerAdapter;
    private Unbinder unbinder;
    private View rootView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceGetCandidateProfile();
    }

    @Override
    public void onRefresh() {

        // Fetching data from server
        serviceGetCandidateProfile();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_my_offers, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeSeekerActivity) getActivity()).setCheckedToBottom(Keys.MY_OFFERS);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialization();
    }

    /** proceed to search job page */
    @OnClick(R.id.btnJobSearch)
    void GotoSearcJobPage()
    {
        ((HomeSeekerActivity) getActivity()).addFragment(new SearchJobFragment(),
                new Bundle(), Keys.SEARCH_JOB, false, true);
    }

    @OnClick(R.id.btnRetry)
    void retry() {
        serviceGetCandidateProfile();
    }

    private void initialization() {
        tvTitle.setText(getResources().getString(R.string.my_offers));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        myOfferSeekerAdapter = new MyOfferSeekerAdapter(getActivity());
        recyclerView.setAdapter(myOfferSeekerAdapter);
    }

    /** call service for get my offer */
    private void serviceGetCandidateProfile() {
       final SpotsDialog spotsDialog = new SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.USER_ID, Singleton.getUserInfoInstance().getUser_id());
        Call<GetMyOfferSeekerResponse> call =
                AppRetrofit.getAppRetrofitInstance()
                        .getApiServices()
                .getMyOfferSeekerResponse(jsonObject);

        ServiceCreator.enqueueCall(call, new Callback<GetMyOfferSeekerResponse>() {
            @Override
            public void onResponse(Response<GetMyOfferSeekerResponse> response, Retrofit retrofit) {
                if (spotsDialog.isShowing())
                    spotsDialog.dismiss();

                mSwipeRefreshLayout.setRefreshing(false);
                GetMyOfferSeekerResponse getMyOfferSeekerResponse = response.body();
                if (getMyOfferSeekerResponse.isSuccess()) {

                    if (getMyOfferSeekerResponse.getData().getAppliedList().size() > 0) {
                        setMyOfferVisibility(true);
                        myOfferSeekerAdapter.clear();
                        for (GetMyOfferSeekerResponse.DataBean.AppliedListBean appliedListBeanList : getMyOfferSeekerResponse.getData().getAppliedList()) {
                            myOfferSeekerAdapter.addData(appliedListBeanList);
                        }
                        myOfferSeekerAdapter.setCurrentDate(getMyOfferSeekerResponse.getData().getCurrent_time());
                    } else {
                        setMyOfferVisibility(false);
                    }
                } else {
                    setMyOfferVisibility(false);
                    if (getMyOfferSeekerResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(getActivity(), getMyOfferSeekerResponse.getMsg());
                    }
                    //  CommonUtils.showSimpleMessageBottom(getActivity(), getMyOfferSeekerResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                rlRetry.setVisibility(View.VISIBLE);
                tvErrorMessage.setText(t.getMessage());
                recyclerView.setVisibility(View.GONE);
                svDefaultLayout.setVisibility(View.GONE);
            }
        });
    }

    private void setMyOfferVisibility(boolean status) {
        rlRetry.setVisibility(View.GONE);
        if (status) {
            svDefaultLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            svDefaultLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
