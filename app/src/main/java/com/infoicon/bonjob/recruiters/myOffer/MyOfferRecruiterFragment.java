package com.infoicon.bonjob.recruiters.myOffer;


import android.content.Intent;
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
import com.infoicon.bonjob.recruiters.adapter.ArchiveOfferAdapter;
import com.infoicon.bonjob.recruiters.adapter.MyOfferRecruiterAdapter;
import com.infoicon.bonjob.recruiters.post.PostJobOfferFragment;
import com.infoicon.bonjob.recruiters.profile.ProfileRecruiterFragment;
import com.infoicon.bonjob.recruiters.search.SearchForCandidateFragment;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.response.GetMyOffersRecruiterResponse;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.TabBarChangeViewRecruiter;
import com.infoicon.bonjob.utils.UtilsMethods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyOfferRecruiterFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    View rootView;
    @BindView(R.id.recyclerViewOffer) RecyclerView recyclerViewOffer;
    @BindView(R.id.recyclerViewArchieve) RecyclerView recyclerViewArchieve;
    @BindView(R.id.tvDropDown) CustomsTextView tvDropDown;
    @BindView(R.id.relative) RelativeLayout relative;
    @BindView(R.id.rlRetry) RelativeLayout rlRetry;
    @BindView(R.id.linearNoOffer) LinearLayout linearNoOffer;
    @BindView(R.id.progress_bar) ProgressBar progress_bar;
    @BindView(R.id.tvErrorMessage) CustomsTextViewBold tvErrorMessage;

    private MyOfferRecruiterAdapter myOffersAdapter;
    private ArchiveOfferAdapter archiveOfferAdapter;
    private int activeJobCount = 0;
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
        serviceMyOffersList();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_my_offer_recruiter, container, false);
        ButterKnife.bind(this, rootView);
        // SwipeRefreshLayout
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

        initialization();
        serviceMyOffersList();
        return rootView;
    }

    private void initialization() {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();
    }

    /** event for proceed to search a candidate */
    @OnClick(R.id.btnSearchForCandidate)
    void searchForCandidate() {
       // if (UtilsMethods.isValidForSearch(getActivity()))
            ((HomeRecruiterActivity) getActivity()).addFragment(new SearchForCandidateFragment(), new Bundle(), Keys.VIEW_ONLINE_CANDIDATE, false, true);
    }

    /** event for proceed to post a job */
    @OnClick(R.id.btnPostAJob)
    void postAJob() {

        if (Singleton.getUserInfoInstance().getEnterprise_name().equalsIgnoreCase("")) {
            UtilsMethods.postCallback(getActivity(), new UtilsMethods.Callback() {
                @Override
                public void onYes() {
                    ((HomeRecruiterActivity) getActivity()).addFragment(new ProfileRecruiterFragment(), new Bundle(), Keys.PROFILE,
                            false, true);
                }
            });
        } else {
            //if (UtilsMethods.isValidForPostJob(getActivity())) {
            ((HomeRecruiterActivity) getActivity()).addFragment(new PostJobOfferFragment(), new Bundle(), Keys.POST_JOB, false, true);
        }
    }

    @OnClick(R.id.btnRetry)
    void retry() {
        serviceMyOffersList();
    }


    /** event for hide/show archive list */
    @OnClick(R.id.tvDropDown)
    void hideShowArchive() {
        if (upDown) {
            upDown = false;
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            tvDropDown.setLayoutParams(params);
            tvDropDown.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.toggle_down, 0);
        } else {
            /*if (myOffersAdapter.getItemCount() > 0) {
                recyclerViewOffer.setVisibility(View.VISIBLE);
                linearNoOffer.setVisibility(View.GONE);
            } else {
                recyclerViewOffer.setVisibility(View.GONE);
                linearNoOffer.setVisibility(View.VISIBLE);
            }*/
            upDown = true;
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            tvDropDown.setLayoutParams(params);
            tvDropDown.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.toggle_up, 0);

        }
    }

    /** initialization */
    private void initialize() {

        recyclerViewOffer.setNestedScrollingEnabled(false);
        recyclerViewArchieve.setNestedScrollingEnabled(false);
        //active jon
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewOffer.setHasFixedSize(false);
        recyclerViewOffer.setLayoutManager(linearLayoutManager);
        myOffersAdapter = new MyOfferRecruiterAdapter(getActivity(), MyOfferRecruiterFragment.this);
        recyclerViewOffer.setAdapter(myOffersAdapter);

        //close job
        LinearLayoutManager linearLayoutManagerArchive = new LinearLayoutManager(getActivity());
        recyclerViewArchieve.setHasFixedSize(false);
        recyclerViewArchieve.setLayoutManager(linearLayoutManagerArchive);
        archiveOfferAdapter = new ArchiveOfferAdapter(getActivity(), MyOfferRecruiterFragment.this);
        recyclerViewArchieve.setAdapter(archiveOfferAdapter);
    }


    /** call service for my offers list */
    private void serviceMyOffersList() {
        progress_bar.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.USER_ID, Singleton.getUserInfoInstance().getUser_id());
        retrofit.Call<GetMyOffersRecruiterResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getMyOffersResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetMyOffersRecruiterResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetMyOffersRecruiterResponse> response, retrofit.Retrofit retrofit) {
                recyclerViewOffer.setAdapter(myOffersAdapter);
                progress_bar.setVisibility(View.GONE);
                rlRetry.setVisibility(View.GONE);//retry layout
                mSwipeRefreshLayout.setRefreshing(false);
                swipe_container_Archieve.setRefreshing(false);
                GetMyOffersRecruiterResponse getMyOffersRecruiterResponse = response.body();
                myOffersAdapter.clearOffer();
                archiveOfferAdapter.clearOffer();
                //my offers
                if (getMyOffersRecruiterResponse.isSuccess()) {
                    List<GetMyOffersRecruiterResponse.ActiveJobsBean> activeJobsBeanList =
                            getMyOffersRecruiterResponse.getActiveJobs();
                    if (activeJobsBeanList != null && activeJobsBeanList.size() > 0) {
                        recyclerViewOffer.setVisibility(View.VISIBLE);
                        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
                        linearNoOffer.setVisibility(View.GONE);
                        for (GetMyOffersRecruiterResponse.ActiveJobsBean activeJobsBean : activeJobsBeanList) {
                            myOffersAdapter.addOffer(activeJobsBean);
                        }
                        activeJobCount = activeJobsBeanList.size();
                        sendBroadcast();
                    } else {
                        activeJobCount = 0;
                        sendBroadcast();
                        recyclerViewOffer.setVisibility(View.GONE);
                        mSwipeRefreshLayout.setVisibility(View.GONE);
                        linearNoOffer.setVisibility(View.VISIBLE);
                    }

                    //archive offers
                    List<GetMyOffersRecruiterResponse.ClosedJobsBean> closedJobsBeanList = getMyOffersRecruiterResponse.getClosedJobs();
                    for (GetMyOffersRecruiterResponse.ClosedJobsBean closedJobsBean : closedJobsBeanList) {
                        archiveOfferAdapter.addOffer(closedJobsBean);
                    }
                } else {
                    recyclerViewOffer.setVisibility(View.GONE);
                    mSwipeRefreshLayout.setVisibility(View.GONE);
                    linearNoOffer.setVisibility(View.VISIBLE);


                    if (getMyOffersRecruiterResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(getActivity(), getMyOffersRecruiterResponse.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                progress_bar.setVisibility(View.GONE);
                recyclerViewOffer.setVisibility(View.GONE);
                mSwipeRefreshLayout.setVisibility(View.GONE);
                rlRetry.setVisibility(View.VISIBLE);
                tvErrorMessage.setText(t.getMessage());
            }
        });
    }

    private void sendBroadcast() {
        Intent intent = new Intent(Keys.MY_OFFERS);
        intent.putExtra(Keys.POSITION, Keys.TABE_OFFERS);
        intent.putExtra(Keys.VALUE, activeJobCount);
        getActivity().sendBroadcast(intent);
    }


    /** add active value to archive after close */
    public void closeOffer(GetMyOffersRecruiterResponse.ActiveJobsBean activeJobsBean) {
        if (archiveOfferAdapter != null) {
            GetMyOffersRecruiterResponse.ClosedJobsBean closedJobsBean = new GetMyOffersRecruiterResponse.ClosedJobsBean();
            closedJobsBean.setJob_id(activeJobsBean.getJob_id());
            closedJobsBean.setJob_title(activeJobsBean.getJob_title());
            closedJobsBean.setJob_description(activeJobsBean.getJob_description());
            closedJobsBean.setJob_image(activeJobsBean.getJob_image());
            closedJobsBean.setJob_location(activeJobsBean.getJob_location());
            closedJobsBean.setContract_type(activeJobsBean.getContract_type());
            closedJobsBean.setEducation_level(activeJobsBean.getEducation_level());
            closedJobsBean.setExperience(activeJobsBean.getExperience());
            closedJobsBean.setLang(activeJobsBean.getLang());
            closedJobsBean.setNum_of_hours(activeJobsBean.getNum_of_hours());
            closedJobsBean.setSalarie(activeJobsBean.getSalarie());
            closedJobsBean.setStart_date(activeJobsBean.getStart_date());
            closedJobsBean.setContact_mode(activeJobsBean.getContact_mode());
            closedJobsBean.setStatus(activeJobsBean.getStatus());
            closedJobsBean.setAdded_by(activeJobsBean.getAdded_by());
            closedJobsBean.setUser_id(activeJobsBean.getUser_id());
            closedJobsBean.setCreatedOn(activeJobsBean.getCreatedOn());
            closedJobsBean.setUpdatedOn(activeJobsBean.getUpdatedOn());
            archiveOfferAdapter.addOffer(closedJobsBean);
            if (myOffersAdapter.getItemCount() > 1) {
                recyclerViewOffer.setVisibility(View.VISIBLE);
                mSwipeRefreshLayout.setVisibility(View.VISIBLE);
                linearNoOffer.setVisibility(View.GONE);
            } else {
                recyclerViewOffer.setVisibility(View.GONE);
                mSwipeRefreshLayout.setVisibility(View.GONE);
                linearNoOffer.setVisibility(View.VISIBLE);
            }
            //set active job count
            activeJobCount = activeJobCount - 1;
            sendBroadcast();
        }
    }

    /** add archive value to active after renew */
    public void renewOffer(GetMyOffersRecruiterResponse.ClosedJobsBean closedJobsBean) {
        if (myOffersAdapter != null) {
            GetMyOffersRecruiterResponse.ActiveJobsBean activeJobsBean = new GetMyOffersRecruiterResponse.ActiveJobsBean(Parcel.obtain());
            activeJobsBean.setJob_id(closedJobsBean.getJob_id());
            activeJobsBean.setJob_title(closedJobsBean.getJob_title());
            activeJobsBean.setJob_description(closedJobsBean.getJob_description());
            activeJobsBean.setJob_image(closedJobsBean.getJob_image());
            activeJobsBean.setJob_location(closedJobsBean.getJob_location());
            activeJobsBean.setContract_type(closedJobsBean.getContract_type());
            activeJobsBean.setEducation_level(closedJobsBean.getEducation_level());
            activeJobsBean.setExperience(closedJobsBean.getExperience());
            activeJobsBean.setLang(closedJobsBean.getLang());
            activeJobsBean.setNum_of_hours(closedJobsBean.getNum_of_hours());
            activeJobsBean.setSalarie(closedJobsBean.getSalarie());
            activeJobsBean.setStart_date(closedJobsBean.getStart_date());
            activeJobsBean.setContact_mode(closedJobsBean.getContact_mode());
            activeJobsBean.setStatus(closedJobsBean.getStatus());
            activeJobsBean.setAdded_by(closedJobsBean.getAdded_by());
            activeJobsBean.setUser_id(closedJobsBean.getUser_id());
            activeJobsBean.setCreatedOn(closedJobsBean.getCreatedOn());
            activeJobsBean.setUpdatedOn(closedJobsBean.getUpdatedOn());
            myOffersAdapter.addOffer(activeJobsBean);
            recyclerViewOffer.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            linearNoOffer.setVisibility(View.GONE);
            //set active job count
            activeJobCount = activeJobCount + 1;
            sendBroadcast();
        }
    }
}
