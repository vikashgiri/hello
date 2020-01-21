package com.infoicon.bonjob.recruiters.myOffer;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.main.HomeRecruiterActivity;
import com.infoicon.bonjob.recruiters.adapter.HiredCandidateAdapter;
import com.infoicon.bonjob.recruiters.post.PostJobOfferFragment;
import com.infoicon.bonjob.recruiters.profile.ProfileRecruiterFragment;
import com.infoicon.bonjob.recruiters.search.SearchForCandidateFragment;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.response.GetHiredCandidateResponse;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.TabBarChangeViewRecruiter;
import com.infoicon.bonjob.utils.UtilsMethods;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HiredFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{


    View rootView;
    @BindView(R.id.recyclerViewHired) RecyclerView recyclerViewHired;
    private HiredCandidateAdapter hiredCandidateAdapter;
    @BindView(R.id.tvErrorMessage) CustomsTextViewBold tvErrorMessage;
    @BindView(R.id.linearNoHired) LinearLayout linearNoHired;
    @BindView(R.id.rlRetry) RelativeLayout rlRetry;
    @BindView(R.id.progress_bar) ProgressBar progress_bar;
    private int hiredCandidateCount = 0;

    SwipeRefreshLayout swipe_container;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_hired, container, false);
        swipe_container = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        swipe_container.setOnRefreshListener(this);
        swipe_container.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onRefresh() {

        // Fetching data from server
        serviceGetHiredList();
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();

        serviceGetHiredList();
    }


    /** event for proceed to search a candidate */
    @OnClick(R.id.btnSearchForCandidate)
    void searchForCandidate() {
        //if (UtilsMethods.isValidForSearch(getActivity()))
            ((HomeRecruiterActivity) getActivity()).addFragment(new SearchForCandidateFragment(), new Bundle(), Keys.VIEW_ONLINE_CANDIDATE, false, true);
    }

    /** event for proceed to post a job */
    @OnClick(R.id.btnPostAJob)
    void postAJob() {

        if (Singleton.getUserInfoInstance().getEnterprise_name().equalsIgnoreCase(""))
        {
            UtilsMethods.postCallback(getActivity(), new UtilsMethods.Callback()
            {
                @Override
                public void onYes() {
                    ((HomeRecruiterActivity) getActivity()).addFragment(new ProfileRecruiterFragment(), new Bundle(), Keys.PROFILE, false, true);
                }
            });
        }
        else
        {
            //if (UtilsMethods.isValidForPostJob(getActivity())) {
            ((HomeRecruiterActivity) getActivity()).addFragment(new PostJobOfferFragment(), new Bundle(), Keys.POST_JOB, false, true);
      }
    }

    /** initialization */
    private void initialize() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewHired.setHasFixedSize(false);
        recyclerViewHired.setLayoutManager(linearLayoutManager);
        hiredCandidateAdapter = new HiredCandidateAdapter(getActivity());
        recyclerViewHired.setAdapter(hiredCandidateAdapter);


    }

    @OnClick(R.id.btnRetry)
    void retry() {
        serviceGetHiredList();
    }


    /** call service for hired candidate */
    private void serviceGetHiredList()
    {
       // progress_bar.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.USER_ID, Singleton.getUserInfoInstance().getUser_id());
        retrofit.Call<GetHiredCandidateResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getHiredCandidateResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetHiredCandidateResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetHiredCandidateResponse> response, retrofit.Retrofit retrofit) {
            //    progress_bar.setVisibility(View.GONE);
                rlRetry.setVisibility(View.GONE);//retry layout
                hiredCandidateAdapter.clearData();
                swipe_container.setRefreshing(false);
                GetHiredCandidateResponse getHiredCandidateResponse = response.body();
                if (getHiredCandidateResponse.isSuccess()) {

                    if (getHiredCandidateResponse.getData() != null && getHiredCandidateResponse.getData().size() > 0) {
                        recyclerViewHired.setVisibility(View.VISIBLE);
                        swipe_container.setVisibility(View.VISIBLE);
                        linearNoHired.setVisibility(View.GONE);
                        for (GetHiredCandidateResponse.DataBean dataBean : getHiredCandidateResponse.getData()) {
                            hiredCandidateAdapter.addData(dataBean);
                        }
                        hiredCandidateCount = getHiredCandidateResponse.getData().size();
                        sendBroadcast();
                    } else {
                        hiredCandidateCount = 0;
                        sendBroadcast();
                        linearNoHired.setVisibility(View.VISIBLE);
                        recyclerViewHired.setVisibility(View.GONE);
                        swipe_container.setVisibility(View.GONE);
                    }

                } else {
                    linearNoHired.setVisibility(View.VISIBLE);
                    recyclerViewHired.setVisibility(View.GONE);
                    swipe_container.setVisibility(View.GONE);
                    if (getHiredCandidateResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(getActivity(), getHiredCandidateResponse.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                progress_bar.setVisibility(View.GONE);
                rlRetry.setVisibility(View.VISIBLE);
                recyclerViewHired.setVisibility(View.GONE);
                swipe_container.setVisibility(View.GONE);
                tvErrorMessage.setText(t.getMessage());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(Keys.HIRE));
    }

    @Override
    public void onDestroy() {
        if (broadcastReceiver != null) {
            getActivity().unregisterReceiver(broadcastReceiver);
        }
        super.onDestroy();
    }

    /** broadcast receiver fire if candidate selected */
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (getActivity() != null && isAdded()) {
                if (hiredCandidateAdapter != null)
                    hiredCandidateAdapter.clearData();
                serviceGetHiredList();
            }
        }
    };


    private void sendBroadcast() {
        Intent intent = new Intent(Keys.MY_OFFERS);
        intent.putExtra(Keys.POSITION, Keys.TABE_HIRED);
        intent.putExtra(Keys.VALUE, hiredCandidateCount);
        getActivity().sendBroadcast(intent);
    }
}
