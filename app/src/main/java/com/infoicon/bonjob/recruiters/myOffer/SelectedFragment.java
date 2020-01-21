package com.infoicon.bonjob.recruiters.myOffer;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcel;
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
import com.infoicon.bonjob.recruiters.adapter.ArchiveSelectedCandidateAdapter;
import com.infoicon.bonjob.recruiters.adapter.SelectedCandidateAdapter;
import com.infoicon.bonjob.recruiters.post.PostJobOfferFragment;
import com.infoicon.bonjob.recruiters.profile.ProfileRecruiterFragment;
import com.infoicon.bonjob.recruiters.search.SearchForCandidateFragment;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.response.GetSelectedCandidateResponse;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.TabBarChangeViewRecruiter;
import com.infoicon.bonjob.utils.TabBarChangeViewSeeker;
import com.infoicon.bonjob.utils.UtilsMethods;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SelectedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{


    View rootView;
    @BindView(R.id.recyclerSelected) RecyclerView recyclerSelected;
    @BindView(R.id.recyclerViewArchiveSelected) RecyclerView recyclerViewArchiveSelected;
    @BindView(R.id.tvDropDown) CustomsTextView tvDropDown;
    @BindView(R.id.tvErrorMessage) CustomsTextViewBold tvErrorMessage;
    @BindView(R.id.linearNoSelected) LinearLayout linearNoSelected;
    @BindView(R.id.rlRetry) RelativeLayout rlRetry;
    @BindView(R.id.progress_bar) ProgressBar progress_bar;
    private SelectedCandidateAdapter selectedCandidateAdapter;
    private ArchiveSelectedCandidateAdapter archiveSelectedCandidateAdapter;
    private int selectedCandidateCount = 0;
    private boolean upDown = true;
    SwipeRefreshLayout mSwipeRefreshLayout;
    SwipeRefreshLayout swipe_container_Archieve;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_selected, container, false);
        ButterKnife.bind(this, rootView);
        serviceSelectedCandidateList();
        return rootView;
    }

    @Override
    public void onRefresh() {

        // Fetching data from server
        serviceSelectedCandidateList();
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initialize();
    }


    @Override
    public void onResume()
    {
        super.onResume();
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(Keys.SELECTED_CANDIDATE));
    }

    /** event for proceed to search a candidate */
    @OnClick(R.id.btnSearchForCandidate)
    void searchForCandidate()
    {
        TabBarChangeViewRecruiter.changeView(getActivity()
                , Keys.LOOK_FOR);

        // if (UtilsMethods.isValidForSearch(getActivity()))
            ((HomeRecruiterActivity) getActivity()).addFragment(new SearchForCandidateFragment(), new Bundle(), Keys.VIEW_ONLINE_CANDIDATE, false, true);
    }
    /** event for proceed to post a job */
    @OnClick(R.id.btnPostAJob)
    void postAJob()
    {
        if (Singleton.getUserInfoInstance().getEnterprise_name().equalsIgnoreCase("")) {
            UtilsMethods.postCallback(getActivity(), new UtilsMethods.Callback()
            {
                @Override
                public void onYes()
                {
                    ((HomeRecruiterActivity) getActivity()).
                            addFragment(new ProfileRecruiterFragment(),
                                    new Bundle(),
                                    Keys.PROFILE,
                                    false, true);
                }
            });
        }
        else {//if (UtilsMethods.isValidForPostJob(getActivity())) {
            ((HomeRecruiterActivity) getActivity()).addFragment(new PostJobOfferFragment(), new Bundle(), Keys.POST_JOB, false, true);
        }
    }

    /** event for hide/show archive list */
    @OnClick(R.id.tvDropDown)
    void hideShowArchieve() {
        if (upDown) {
            upDown = false;
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
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

    @OnClick(R.id.btnRetry)
    void retry() {
        serviceSelectedCandidateList();
    }

    /** initialization */
    private void initialize() {
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


        recyclerSelected.setNestedScrollingEnabled(false);
        recyclerViewArchiveSelected.setNestedScrollingEnabled(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerSelected.setHasFixedSize(false);
        recyclerSelected.setLayoutManager(linearLayoutManager);
        selectedCandidateAdapter = new SelectedCandidateAdapter(getActivity(), SelectedFragment.this);
        recyclerSelected.setAdapter(selectedCandidateAdapter);

        LinearLayoutManager linearLayoutManagerArchive = new LinearLayoutManager(getActivity());
        recyclerViewArchiveSelected.setHasFixedSize(false);
        recyclerViewArchiveSelected.setLayoutManager(linearLayoutManagerArchive);
        archiveSelectedCandidateAdapter = new ArchiveSelectedCandidateAdapter(getActivity());
        recyclerViewArchiveSelected.setAdapter(archiveSelectedCandidateAdapter);


    }

    /** call service for get selected candidate list */
    private void serviceSelectedCandidateList() {
       // progress_bar.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.USER_ID, Singleton.getUserInfoInstance().getUser_id());
        retrofit.Call<GetSelectedCandidateResponse> call = AppRetrofit.getAppRetrofitInstance().
                getApiServices().getSelectedCandidateResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetSelectedCandidateResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetSelectedCandidateResponse> response, retrofit.Retrofit retrofit) {
              //  progress_bar.setVisibility(View.GONE);
                rlRetry.setVisibility(View.GONE);//retry layout
archiveSelectedCandidateAdapter.clearData();
selectedCandidateAdapter.clearData();
                mSwipeRefreshLayout.setRefreshing(false);
                swipe_container_Archieve.setRefreshing(false);
                GetSelectedCandidateResponse getSelectedCandidateResponse = response.body();
                if (getSelectedCandidateResponse.isSuccess()) {
                    //selected data
                    if (getSelectedCandidateResponse.getSelectedData().size() > 0) {
                        linearNoSelected.setVisibility(View.GONE);
                        recyclerSelected.setVisibility(View.VISIBLE);
                        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
                        for (GetSelectedCandidateResponse.SelectedDataBean selectedDataBean : getSelectedCandidateResponse.getSelectedData()) {
                            selectedCandidateAdapter.addData(selectedDataBean);
                        }
                        selectedCandidateCount = getSelectedCandidateResponse.getSelectedData().size();
                        sendBroadcast();
                    } else {
                        selectedCandidateCount = 0;
                        sendBroadcast();
                        linearNoSelected.setVisibility(View.VISIBLE);
                        recyclerSelected.setVisibility(View.GONE);
                        mSwipeRefreshLayout.setVisibility(View.GONE);
                    }
                    //not selected data
                    if (getSelectedCandidateResponse.getNotSelectedData().size() > 0) {
                        for (GetSelectedCandidateResponse.NotSelectedDataBean selectedDataBean : getSelectedCandidateResponse.getNotSelectedData()) {
                            archiveSelectedCandidateAdapter.addData(selectedDataBean);
                        }
                    }
                } else {
                    linearNoSelected.setVisibility(View.VISIBLE);
                    recyclerSelected.setVisibility(View.GONE);
                    mSwipeRefreshLayout.setVisibility(View.GONE);

                    if (getSelectedCandidateResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(getActivity(), getSelectedCandidateResponse.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                progress_bar.setVisibility(View.GONE);
                rlRetry.setVisibility(View.VISIBLE);
                recyclerSelected.setVisibility(View.GONE);
                mSwipeRefreshLayout.setVisibility(View.GONE);
                tvErrorMessage.setText(t.getMessage());
            }
        });
    }

    /**
     * add archive value to active after renew
     * @param selectedDataBean
     */
    public void nonSelectCandidate(GetSelectedCandidateResponse.SelectedDataBean selectedDataBean) {
        if (selectedCandidateAdapter != null) {
            GetSelectedCandidateResponse.NotSelectedDataBean notSelectedDataBean = new GetSelectedCandidateResponse.NotSelectedDataBean(Parcel.obtain());
            notSelectedDataBean.setAplied_id(selectedDataBean.getAplied_id());
            notSelectedDataBean.setUser_id(selectedDataBean.getUser_id());
            notSelectedDataBean.setJob_id(selectedDataBean.getJob_id());
            notSelectedDataBean.setEmployer_id(selectedDataBean.getEmployer_id());
            notSelectedDataBean.setStatus(selectedDataBean.getStatus());
            notSelectedDataBean.setCreatedOn(selectedDataBean.getCreatedOn());
            notSelectedDataBean.setUpdatedOn(selectedDataBean.getUpdatedOn());
            notSelectedDataBean.setFirst_name(selectedDataBean.getFirst_name());
            notSelectedDataBean.setLast_name(selectedDataBean.getLast_name());
            notSelectedDataBean.setUser_pic(selectedDataBean.getUser_pic());
            notSelectedDataBean.setJob_title(selectedDataBean.getJob_title());
            notSelectedDataBean.setCurrent_status(selectedDataBean.getCurrent_status());
            notSelectedDataBean.setExperience(selectedDataBean.getExperience());
            notSelectedDataBean.setSelected_id(selectedDataBean.getSelected_id());
            notSelectedDataBean.setJob_image(selectedDataBean.getJob_image());
            notSelectedDataBean.setJob_description(selectedDataBean.getJob_description());


            archiveSelectedCandidateAdapter.addData(notSelectedDataBean);
            if (selectedCandidateAdapter.getItemCount() > 1) {
                recyclerSelected.setVisibility(View.VISIBLE);
                mSwipeRefreshLayout.setVisibility(View.VISIBLE);
                linearNoSelected.setVisibility(View.GONE);
            } else {
                recyclerSelected.setVisibility(View.GONE);
                mSwipeRefreshLayout.setVisibility(View.GONE);
                linearNoSelected.setVisibility(View.VISIBLE);
            }

            //set active job count
            selectedCandidateCount = selectedCandidateCount - 1;
            sendBroadcast();
        }
    }

    /** broadcast receiver fire if candidate selected */
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (getActivity() != null && isAdded()) {
                if (selectedCandidateAdapter != null)
                    selectedCandidateAdapter.clearData();
                if (archiveSelectedCandidateAdapter != null)
                    archiveSelectedCandidateAdapter.clearData();
                serviceSelectedCandidateList();
            }
        }
    };


    /** send broadcast to update count for item on tab */
    private void sendBroadcast() {
        Intent intent = new Intent(Keys.MY_OFFERS);
        intent.putExtra(Keys.POSITION, Keys.TAB_SELECTED_CADIDATE);
        intent.putExtra(Keys.VALUE, selectedCandidateCount);
        getActivity().sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        if (broadcastReceiver != null) {
            getActivity().unregisterReceiver(broadcastReceiver);
        }
        super.onDestroy();
    }
}
