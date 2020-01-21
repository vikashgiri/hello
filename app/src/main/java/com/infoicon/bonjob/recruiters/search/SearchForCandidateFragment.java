package com.infoicon.bonjob.recruiters.search;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomEditText;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.main.HomeRecruiterActivity;
import com.infoicon.bonjob.permission.MarshmallowPermission;
import com.infoicon.bonjob.permission.PermissionKeys;
import com.infoicon.bonjob.recruiters.post.PostJobOfferFragment;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.response.GetCandidateSearchResponse;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.CommonUtils;
import com.infoicon.bonjob.utils.GPSLocation;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;
import com.infoicon.bonjob.utils.WrapContentLinearLayoutManager;



import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

import static android.app.Activity.RESULT_OK;


public class SearchForCandidateFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{

    private View rootView;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.edSearch) CustomEditText edSearch;
    @BindView(R.id.tvNoResultFound) CustomsTextView tvNoResultFound;
    public String filterLocation = "", searchKeyword = "", filterCategory = "", filterEducationLevel = "", filterExperience = "",
            filterActualStatus = "", filterMobility = "", filterLanguage = "", filterSearchKey = "";

    public String filterActualStatusId="",filterEducationLevelId = "",
            filterMobilityId = "",filterLanguageId="",
            filterExperienceId = "",filterCategoryId;
    public String postal_code = "";
    private List<GetCandidateSearchResponse.AllCandidatesBean> allCandidatesBeanList;
    private ViewOnlineCandidateAdapter viewOnlineCandidateAdapter;
    private int pageNo = 0;
    private MarshmallowPermission marshmallowPermission;
    public boolean fromFilter;//this is for showing progress percentage when it comes from filter screen
    private double currentLatitude, currentLongitude;
    private int searchCandidateCount = 0;
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    public void onDestroyView() {

        super.onDestroyView();

    }

    @Override
    public void onRefresh() {
        pageNo = 0;
        serviceSearchCandidate(false, pageNo);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //searchCandidateCount = Integer.parseInt(Singleton.getSubscriptionListAndMyPlan().getCurrentPlan().getSearch_candidate_count());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_search_for_candidate, container, false);
        ButterKnife.bind(this, rootView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initialize();
        setSingleLine();
        getCurrentLocation();
        pageNo = 0;
        serviceSearchCandidate(true, pageNo);
    }

    /** get current location to search candidate */
    private void getCurrentLocation()
    {
        if (UtilsMethods.isLocationEnabled(getActivity())) {
            String[] permissions = {PermissionKeys.PERMISSION_ACCESS_FINE_LOCATION, PermissionKeys.PERMISSION_ACCESS_COARSE_LOCATION};
            if (marshmallowPermission.isPermissionGrantedAll(PermissionKeys.REQUEST_CODE_PERMISSION_ALL, permissions)) {
                GPSLocation gpsLocation = new GPSLocation(getActivity());
                currentLatitude = gpsLocation.getLatitude();
                currentLongitude = gpsLocation.getLongitude();
                filterLocation = gpsLocation.getAddress(currentLatitude, currentLongitude);

                if (!filterLocation.equals("") && currentLatitude != 0) {
                    Singleton.getCurrentLocationBean().setCurrentAddress(filterLocation);
                    Singleton.getCurrentLocationBean().setLatitude(currentLatitude);
                    Singleton.getCurrentLocationBean().setLongitude(currentLongitude);
                } else {
                   // filterLocation = Singleton.getCurrentLocationBean().getCurrentAddress();
                  //  currentLatitude = Singleton.getCurrentLocationBean().getLatitude();
                  //  currentLongitude = Singleton.getCurrentLocationBean().getLongitude();
                }

                Logger.e("Location : " + filterLocation + " ==LAT==  " + String.valueOf(currentLatitude) + "===LNG==" + String.valueOf(currentLongitude));

         //      if (UtilsMethods.isValidForSearch(getActivity())) {
                    pageNo = 0;
                    serviceSearchCandidate(true, pageNo);
                //}

            }
        }
    }

    /** get result back is permission is granted or not. */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case PermissionKeys.REQUEST_CODE_PERMISSION_ALL: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    gettingLocation();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    UtilsMethods.openAlert(getResources().getString(R.string.message_location_permission_request), getActivity());
                }
                break;
            }
        }
    }

    /** loader to wait for getting location */
    private void gettingLocation() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getResources().getString(R.string.getting_location));
        progressDialog.show();
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            progressDialog.dismiss();
            getCurrentLocation();
        }, 3000);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeRecruiterActivity) getActivity()).setCheckedToBottom(Keys.LOOK_FOR);
    }

    /** set single line to editText */
    private void setSingleLine() {
        edSearch.setSingleLine(true);
    }

    /** initialize component here */
    private void initialize() {
        // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        // recyclerView.setHasFixedSize(false);
        //   recyclerView.setLayoutManager(linearLayoutManager);
        marshmallowPermission = new MarshmallowPermission(getActivity());
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        edSearch.setOnEditorActionListener(onEditorActionListener);
    }

    /** event for filter candidate search */
    @OnClick(R.id.ivFilter)
    void filterSearch()
    {
        ((HomeRecruiterActivity) getActivity()).addFragment(new TargetedSearchFilterFragment(SearchForCandidateFragment.this), new Bundle(), Keys.FILTER_SEARCH, false, true);
    }

    /** filter job search */
    public void setFilterValue() {
        //if (UtilsMethods.isValidForSearch(getActivity())) {
            pageNo = 0;
            fromFilter = true;
           // searchCandidateCount = 1;
            serviceSearchCandidate(true, pageNo);
        //}
    }


    /** load more search content through paging */
    ViewOnlineCandidateAdapter.OnLoadMoreListener onLoadMoreListener =
            new ViewOnlineCandidateAdapter.OnLoadMoreListener()
            {
        @Override
        public void onLoadMore()
        {
            //add null , so the adapter will check view_type and show progress bar at bottom
            allCandidatesBeanList.add(null);
            recyclerView.post(new Runnable() {
                public void run() {
                    viewOnlineCandidateAdapter.notifyItemInserted(allCandidatesBeanList.size() - 1);
                }
            });
            serviceSearchCandidate(false, pageNo);
        }
    };

    /** get tha action from keyboard */
    TextView.OnEditorActionListener onEditorActionListener = (v, actionId, event) -> {

        if (actionId == EditorInfo.IME_ACTION_SEARCH)
        {
            UtilsMethods.hideSoftKeyboard(getActivity());
            pageNo = 0;
            filterSearchKey = edSearch.getText().toString().trim();
            if (allCandidatesBeanList != null)
                allCandidatesBeanList.add(null);
          //  searchCandidateCount = 1;
            filterLocation="";
            serviceSearchCandidate(true, pageNo);
            return true;
        }
        return false;
    };

    /** api for search candidate */
    private void serviceSearchCandidate(boolean loaderSDhow, int page) {
        pageNo = pageNo + 10;
        final SpotsDialog spotsDialog = new SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        if (loaderSDhow)
            spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.START, page);
        jsonObject.addProperty(Keys.SEARCH_KEY, filterSearchKey);
        jsonObject.addProperty(Keys.USER_ID, Singleton.getUserInfoInstance().
                getUser_id());
        if(filterLocation!="") {

          jsonObject.addProperty(Keys.CITY, filterLocation);
        }
        if(filterMobilityId!="") {

            jsonObject.addProperty(Keys.MOBILITY, filterMobilityId);
        }

      //  jsonObject.addProperty(Keys.EDUCATION_LEVEL, filterEducationLevel);
if(filterEducationLevelId!="") {
    JsonArray jsonArray = new JsonArray();
    if (!filterEducationLevel.equals("")) {
        String[] values = filterEducationLevelId.split(",");
        for (int i = 0; i < values.length; i++) {
            jsonArray.add(values[i]);
        }
    }

    jsonObject.add(Keys.EDUCATION_LEVEL, jsonArray);
}
else
{
   // jsonObject.addProperty(Keys.EDUCATION_LEVEL, "");

}

if(filterActualStatusId!="") {


    JsonArray jsonArray1 = new JsonArray();
    if (!filterActualStatus.equals("")) {
        String[] values = filterActualStatusId.split(",");
        for (int i = 0; i < values.length; i++) {
            jsonArray1.add(values[i]);
        }
    }
   jsonObject.add(Keys.CURRENT_STATUS, jsonArray1);

}
else {
   // jsonObject.addProperty(Keys.CURRENT_STATUS, "");

}



        JsonArray jsonArray2=new JsonArray();
        if(!filterCategory.equals("")){
            String[] values = filterCategoryId.split(",");
            for(int i=0;i<values.length;i++){
                jsonArray2.add(values[i]);
            }
            jsonObject.add(Keys.INDUSTRY_TYPE, jsonArray2);
            jsonObject.addProperty(Keys.SKILL, "");

        }
else {
       //     jsonObject.addProperty(Keys.INDUSTRY_TYPE, "");
         //   jsonObject.addProperty(Keys.SKILL, "");
        }



       // jsonObject.add(Keys.INDUSTRY_TYPE, jsonArray2);


        JsonArray jsonArray3=new JsonArray();
        if(!filterLanguage.equals("")){
            String[] values = filterLanguageId.split(",");
            for(int i=0;i<values.length;i++){
                jsonArray3.add(values[i]);
            }
            jsonObject.add(Keys.LANGUAGE, jsonArray3);

        }
        else {
           // jsonObject.addProperty(Keys.LANGUAGE, "");
        }

        JsonArray jsonArray4=new JsonArray();
        if(!filterExperience.equals("")){
            String[] values = filterExperienceId.split(",");
            for(int i=0;i<values.length;i++){
                jsonArray4.add(values[i]);
            }
            jsonObject.add(Keys.EXPERIENCE, jsonArray4);

        }
else {
         //   jsonObject.addProperty(Keys.EXPERIENCE, "");
        }

    //    jsonObject.addProperty(Keys.SEARCH_CANDIDATE_COUNT, searchCandidateCount);

        retrofit.Call<GetCandidateSearchResponse> call =
                AppRetrofit.getAppRetrofitInstance().getApiServices().getCandidateSearchResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetCandidateSearchResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetCandidateSearchResponse> response, retrofit.Retrofit retrofit) {
                if (spotsDialog.isShowing())
                    spotsDialog.dismiss();
                mSwipeRefreshLayout.setRefreshing(false);
                GetCandidateSearchResponse candidateSearchResponse = response.body();

                //set total search count

                //vikash comments
               /* if (searchCandidateCount == 1) {
                    String searchCount = Singleton.getSubscriptionListAndMyPlan().getCurrentPlan().getSearch_candidate_count();
                    int totalCount = Integer.parseInt(searchCount) + 1;
                    Singleton.getSubscriptionListAndMyPlan().getCurrentPlan().setSearch_candidate_count(String.valueOf(totalCount));
                }*/

                if (candidateSearchResponse.isSuccess()) {
                    if (page == 0) {
                        if (candidateSearchResponse.getAllCandidates().size() > 0) {
                            tvNoResultFound.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            allCandidatesBeanList = candidateSearchResponse.getAllCandidates();
                            viewOnlineCandidateAdapter = new ViewOnlineCandidateAdapter(getActivity(), allCandidatesBeanList, recyclerView, SearchForCandidateFragment.this);
                            recyclerView.setAdapter(viewOnlineCandidateAdapter);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            viewOnlineCandidateAdapter.setOnLoadMoreListener(onLoadMoreListener);

                            //viewOnlineCandidateAdapter.addLatlong(candidateSearchResponse.getCompany_lat(), candidateSearchResponse.getCompany_long());
                            viewOnlineCandidateAdapter.addLatlong(currentLatitude, currentLongitude);
                        } else {
                            tvNoResultFound.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                            allCandidatesBeanList.clear();
                            viewOnlineCandidateAdapter = new ViewOnlineCandidateAdapter(getActivity(), allCandidatesBeanList, recyclerView, SearchForCandidateFragment.this);
                            recyclerView.setAdapter(viewOnlineCandidateAdapter);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            viewOnlineCandidateAdapter.setOnLoadMoreListener(onLoadMoreListener);
                            //  viewOnlineCandidateAdapter.setLoaded();
                        }
                    } else {
                        //remove progress item
                        if (candidateSearchResponse.getAllCandidates().size() > 0) {
                            allCandidatesBeanList.remove(allCandidatesBeanList.size() - 1);
                            viewOnlineCandidateAdapter.notifyItemRemoved(allCandidatesBeanList.size());
                            allCandidatesBeanList.addAll(candidateSearchResponse.getAllCandidates());

                            // recyclerView.getRecycledViewPool().clear();
                            // viewOnlineCandidateAdapter.notifyDataSetChanged();
                            viewOnlineCandidateAdapter.notifyItemInserted(allCandidatesBeanList.size());
                            // add items one by one
                            // viewOnlineCandidateAdapter.notifyItemInserted(allCandidatesBeanList.size());
                            viewOnlineCandidateAdapter.setLoaded();
                        }
                    }
                    // pageNo = pageNo + 10;
                } else {
                    if (page == 0) {
                        if (allCandidatesBeanList != null) allCandidatesBeanList.clear();
                        if (viewOnlineCandidateAdapter != null)
                            viewOnlineCandidateAdapter.notifyDataSetChanged();
                    }
                    //  if (viewOnlineCandidateAdapter != null) viewOnlineCandidateAdapter.setLoaded();

                    if (candidateSearchResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(getActivity(), candidateSearchResponse.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(getActivity(), t.getMessage());
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Keys.CANDIDATE_SELECT_CODE) {
            if (resultCode == RESULT_OK) {
                //new Thread(() -> ((HomeRecruiterActivity) getActivity()).addFragment(new OfferTabFragment(), new Bundle(), Keys.MY_OFFERS, false, true)).start();
                new Thread(() -> ((HomeRecruiterActivity) getActivity()).addFragment(new PostJobOfferFragment(), new Bundle(), Keys.POST_JOB, false, true)).start();
            }
        }
        if (requestCode == Keys.LOCATION_ENABLE_CODE) {
            gettingLocation();
        }
    }
}
