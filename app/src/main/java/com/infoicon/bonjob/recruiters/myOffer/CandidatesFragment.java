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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.main.HomeRecruiterActivity;
import com.infoicon.bonjob.recruiters.adapter.ArchiveCandidateAdapter;
import com.infoicon.bonjob.recruiters.adapter.CandidateAdapter;
import com.infoicon.bonjob.recruiters.post.PostJobOfferFragment;
import com.infoicon.bonjob.recruiters.profile.ProfileRecruiterFragment;
import com.infoicon.bonjob.recruiters.search.SearchForCandidateFragment;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.response.GetCandidateListResponse;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.TabBarChangeViewRecruiter;
import com.infoicon.bonjob.utils.UtilsMethods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CandidatesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    View rootView;
    @BindView(R.id.recyclerViewCandidate) RecyclerView recyclerViewCandidate;
    @BindView(R.id.recyclerViewArchieveCandidate) RecyclerView recyclerViewArchieveCandidate;
    @BindView(R.id.tvDropDown) CustomsTextView tvDropDown;
    @BindView(R.id.tvErrorMessage) CustomsTextViewBold tvErrorMessage;
    @BindView(R.id.linearNoCandidate) LinearLayout linearNoCandidate;
    @BindView(R.id.rlRetry) RelativeLayout rlRetry;
    @BindView(R.id.progress_bar) ProgressBar progress_bar;
    private CandidateAdapter candidateAdapter;
    private ArchiveCandidateAdapter archiveCandidateAdapter;
    private int appliedCandidateCount = 0;
    private boolean upDown = true;
    SwipeRefreshLayout mSwipeRefreshLayout;
    SwipeRefreshLayout swipe_container_Archieve;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onRefresh() {

        // Fetching data from server
        serviceCandidateList();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_candidates, container, false);
        ButterKnife.bind(this, rootView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        swipe_container_Archieve = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container_Archieve);
        swipe_container_Archieve.setOnRefreshListener(this);
        swipe_container_Archieve.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        serviceCandidateList();
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();
    }

    /** event for proceed to search a candidate */
    @OnClick(R.id.btnSearchForCandidate)
    void searchForCandidate() {
        TabBarChangeViewRecruiter.changeView(getActivity()
                , Keys.LOOK_FOR);
       // if (UtilsMethods.isValidForSearch(getActivity()))
            ((HomeRecruiterActivity) getActivity()).addFragment(new SearchForCandidateFragment(),
                    new Bundle(), Keys.VIEW_ONLINE_CANDIDATE, false, true);
    }

    /** event for proceed to post a job */
    @OnClick(R.id.btnPostAJob)
    void postAJob() {
        if (Singleton.getUserInfoInstance().getEnterprise_name().equalsIgnoreCase("")) {
            UtilsMethods.postCallback(getActivity(), new UtilsMethods.Callback() {
                @Override
                public void onYes() {
                    ((HomeRecruiterActivity) getActivity()).addFragment(new ProfileRecruiterFragment(),
                            new Bundle(), Keys.PROFILE, false, true);
                }
            });
        }
        else{
            //if (UtilsMethods.isValidForPostJob(getActivity())) {
            ((HomeRecruiterActivity) getActivity()).addFragment(new PostJobOfferFragment(), new Bundle(),
                    Keys.POST_JOB, false, true);
        }
    }

    @OnClick(R.id.btnRetry)
    void retry() {
        serviceCandidateList();
    }

    /** event for hide/show archive list */
    @OnClick(R.id.tvDropDown)
    void hideShowArchieve() {
        if (upDown) {
            upDown = false;
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            tvDropDown.setLayoutParams(params);
            tvDropDown.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.toggle_down, 0);
        } else {
            upDown = true;
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            tvDropDown.setLayoutParams(params);
            tvDropDown.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.toggle_up, 0);

        }
    }

    /** initialize view */
    private void initialize() {

        recyclerViewCandidate.setNestedScrollingEnabled(false);
        recyclerViewArchieveCandidate.setNestedScrollingEnabled(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewCandidate.setHasFixedSize(false);
        recyclerViewCandidate.setLayoutManager(linearLayoutManager);
        candidateAdapter = new CandidateAdapter(getActivity());
        recyclerViewCandidate.setAdapter(candidateAdapter);

        LinearLayoutManager linearLayoutManagerArchive = new LinearLayoutManager(getActivity());
        recyclerViewArchieveCandidate.setHasFixedSize(false);
        recyclerViewArchieveCandidate.setLayoutManager(linearLayoutManagerArchive);
        archiveCandidateAdapter = new ArchiveCandidateAdapter(getActivity());
        recyclerViewArchieveCandidate.setAdapter(archiveCandidateAdapter);
    }

    /** call service for get candidate list */
    private void serviceCandidateList()
    {
     //   progress_bar.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.USER_ID, Singleton.getUserInfoInstance().getUser_id());
        retrofit.Call<GetCandidateListResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getCandidateListResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetCandidateListResponse>()
        {
            @Override
            public void onResponse(retrofit.Response<GetCandidateListResponse> response, retrofit.Retrofit retrofit)
            {
            //    progress_bar.setVisibility(View.GONE);
                rlRetry.setVisibility(View.GONE);//retry layout
                candidateAdapter.clear();
                archiveCandidateAdapter. clearArchiveData();
                mSwipeRefreshLayout.setRefreshing(false);
                swipe_container_Archieve.setRefreshing(false);
                GetCandidateListResponse getCandidateListResponse = response.body();
                if (getCandidateListResponse.isSuccess()) {
                    List<GetCandidateListResponse.DataBean.AppliedListBean> appliedListBeen = getCandidateListResponse.getData().getAppliedList();
                    if (appliedListBeen != null && appliedListBeen.size() > 0)
                    {
                        recyclerViewCandidate.setVisibility(View.VISIBLE);
                        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
                        linearNoCandidate.setVisibility(View.GONE);

                        for (GetCandidateListResponse.DataBean.AppliedListBean listBean : appliedListBeen) {
                            candidateAdapter.addCandidateData(listBean);
                        }
                        candidateAdapter.setCurrentDate(getCandidateListResponse.getData().getCurrent_date());

                        appliedCandidateCount = appliedListBeen.size();
                        sendBroadcast();
                    } else {
                        appliedCandidateCount = 0;
                        sendBroadcast();
                        linearNoCandidate.setVisibility(View.VISIBLE);
                        recyclerViewCandidate.setVisibility(View.GONE);
                        mSwipeRefreshLayout.setVisibility(View.GONE);
                    }

                    List<GetCandidateListResponse.DataBean.ArchivedListBean> archivedListBeen = getCandidateListResponse.getData().getArchivedList();
                    if (archivedListBeen != null && archivedListBeen.size() > 0) {
                        for (GetCandidateListResponse.DataBean.ArchivedListBean listBean : archivedListBeen) {
                            archiveCandidateAdapter.addArchiveData(listBean);
                        }
                    }
                } else {
                    linearNoCandidate.setVisibility(View.VISIBLE);
                    recyclerViewCandidate.setVisibility(View.GONE);
                    mSwipeRefreshLayout.setVisibility(View.GONE);

                    if (getCandidateListResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(getActivity(), getCandidateListResponse.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                progress_bar.setVisibility(View.GONE);
                recyclerViewCandidate.setVisibility(View.GONE);
                mSwipeRefreshLayout.setVisibility(View.GONE);
                rlRetry.setVisibility(View.VISIBLE);
                tvErrorMessage.setText(t.getMessage());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(Keys.APPLIED_CANDIDATE));
        getActivity().registerReceiver(broadcastReceiverOnNotification, new IntentFilter(Keys.APPLY_CANDIDATE_NOTIFY_TO_RECRUITER));
    }

    @Override
    public void onDestroy() {
        if (broadcastReceiver != null) {
            getActivity().unregisterReceiver(broadcastReceiver);
        }
        if (broadcastReceiverOnNotification != null) {
            getActivity().unregisterReceiver(broadcastReceiverOnNotification);
        }
        super.onDestroy();
    }

    /** broadcast to remove candidate from list when user will be selected */
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (getActivity() != null && isAdded()) {
                if (candidateAdapter != null) {
                    int pos = intent.getIntExtra(Keys.POSITION, 0);
                    candidateAdapter.removeCandidate(pos);
                    appliedCandidateCount = candidateAdapter.getItemCount();

                    if (appliedCandidateCount == 0) {
                        linearNoCandidate.setVisibility(View.VISIBLE);
                        recyclerViewCandidate.setVisibility(View.GONE);
                        mSwipeRefreshLayout.setVisibility(View.GONE);
                    }
                    sendBroadcast();
                }
            }
        }
    };

    /** broadcast receive on real time when candidate apply job of recruiter. */
    BroadcastReceiver broadcastReceiverOnNotification = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (getActivity() != null && isAdded()) {
                if (candidateAdapter != null) {
                    UtilsMethods.clearNotification(getActivity());
                    candidateAdapter.clear();
                    serviceCandidateList();
                }
            }
        }
    };


    /** send broadcast for increase the applied count on selected tab */
    private void sendBroadcast() {
        Intent intent = new Intent(Keys.MY_OFFERS);
        intent.putExtra(Keys.POSITION, Keys.TAB_CADIDATE);
        intent.putExtra(Keys.VALUE, appliedCandidateCount);
        getActivity().sendBroadcast(intent);
    }
}
