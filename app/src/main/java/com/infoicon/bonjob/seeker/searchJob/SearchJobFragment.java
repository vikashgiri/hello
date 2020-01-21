package com.infoicon.bonjob.seeker.searchJob;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomEditText;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.main.HomeSeekerActivity;
import com.infoicon.bonjob.permission.MarshmallowPermission;
import com.infoicon.bonjob.permission.PermissionKeys;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.response.GetAdvertisementResponce;
import com.infoicon.bonjob.retrofit.response.GetSearchJobResponse;
import com.infoicon.bonjob.seeker.profile.PickLocationActivity;
import com.infoicon.bonjob.seeker.profile.ProfileFragment;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.CommonUtils;
import com.infoicon.bonjob.utils.GPSLocation;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;
import com.infoicon.bonjob.utils.WrapContentLinearLayoutManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

import static android.app.Activity.RESULT_OK;

/**
 * Created by infoicon on 9/6/17.
 */

public class SearchJobFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    private View viewRoot;
    @BindView(R.id.edSearch) CustomEditText edSearch;
    @BindView(R.id.recyclerViewLookfor) RecyclerView recyclerView;
    @BindView(R.id.recyclerViewKeywords) RecyclerView recyclerViewKeywords;
    @BindView(R.id.tvLocation) CustomsTextView tvLocation;
    @BindView(R.id.distance) CustomsTextView distance;
    @BindView(R.id.mainLayouts) LinearLayout mainLayouts;
    @BindView(R.id.select_distance) SeekBar seek;
    @BindView(R.id.tvNoResultFound) CustomsTextView tvNoResultFound;
    @BindView(R.id.relDistance)  RelativeLayout relDistance;
    private KeywordAdapter keywordAdapter;
    private AdapterLook adpterLook;
    private List<GetSearchJobResponse.DataBean.AllJobsBean> allJobsBeanList;
    public String filterContractType = "", filterEducationLevel = "", filterNumHours = "", filterExperience = "";
    public String typeOfContractPosition= "" ;
    public String experiencePosition = "";
    public String filterContractTypeId="",filterEducationLevelId = "", filterNumHoursId = "",
            filterExperienceId = "";
    public String eduLevelPosition= "";
    public String fullPartTimePosition= "";
    public String SearchDistance= "";
    private int pageNo = 1;
    private double currentLatitude = 0, currentLongitude = 0;
    private String searchLocationName = "";
    private MarshmallowPermission marshmallowPermission;
    private  GetAdvertisementResponce getAdvertisementResponce;
    SwipeRefreshLayout mSwipeRefreshLayout;
    JsonArray filterJobIdlist;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        serviceSearchJob(false, pageNo);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        viewRoot = inflater.inflate(R.layout.fragment_search_job, container, false);
        ButterKnife.bind(this, viewRoot);
        setRetainInstance(true);
        mSwipeRefreshLayout = (SwipeRefreshLayout) viewRoot.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        intiView();
        listener();
        setEditTextSingleLine();
        marshmallowPermission = new MarshmallowPermission(getActivity());
     // getCurrentLocation();
        serviceSearchJob(true, pageNo);
        return viewRoot;
    }

    /** get current location to search job */
    private void getCurrentLocation()
    {
        //715000
        if (UtilsMethods.isLocationEnabled(getActivity()))
        {
            String[] permissions = {PermissionKeys.PERMISSION_ACCESS_FINE_LOCATION, PermissionKeys.PERMISSION_ACCESS_COARSE_LOCATION};
            if (marshmallowPermission.isPermissionGrantedAll(PermissionKeys.REQUEST_CODE_PERMISSION_ALL, permissions)) {
                GPSLocation gpsLocation = new GPSLocation(getActivity());
                currentLatitude = gpsLocation.getLatitude();
                currentLongitude = gpsLocation.getLongitude();
                searchLocationName = gpsLocation.getAddress(currentLatitude, currentLongitude);

                if (!searchLocationName.equals("") && currentLatitude != 0) {
                    Singleton.getCurrentLocationBean().setCurrentAddress(searchLocationName);
                    Singleton.getCurrentLocationBean().setLatitude(currentLatitude);
                    Singleton.getCurrentLocationBean().setLongitude(currentLongitude);
                }
                else
                    {
                    currentLatitude = Singleton.getCurrentLocationBean().getLatitude();
                    currentLongitude = Singleton.getCurrentLocationBean().getLongitude();
                    searchLocationName = Singleton.getCurrentLocationBean().getCurrentAddress();
                }
                Logger.e("Location : " + searchLocationName + " ==LAT==  " + String.valueOf(currentLatitude) + "===LNG==" + String.valueOf(currentLongitude));
                tvLocation.setText(searchLocationName);
                pageNo = 1;
                serviceSearchJob(true, pageNo);
            }
        }
    }

    /** get result back is permission is granted or not. */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        switch (requestCode) {
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


    private void listener() {
        edSearch.setOnEditorActionListener(onEditorActionListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeSeekerActivity) getActivity()).setCheckedToBottom(Keys.LOOK_FOR);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @OnClick(R.id.tvLocation)
    void editLocation() {
        Intent intent = new Intent(getActivity(), PickLocationActivity.class);
        startActivityForResult(intent, Keys.LOCATION_CODE);
    }


    @OnClick(R.id.tvEditLocation)
    void editDistance() {
      /*  Intent intent = new Intent(getActivity(), PickLocationActivity.class);
        startActivityForResult(intent, Keys.LOCATION_CODE);*/
      if(relDistance.getVisibility()==View.VISIBLE) {
          edSearch.clearFocus();
          InputMethodManager in = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
          in.hideSoftInputFromWindow(edSearch.getWindowToken(), 0);
          relDistance.setVisibility(View.GONE);
      }
      else {
          relDistance.setVisibility(View.VISIBLE);
          edSearch.requestFocus();
          InputMethodManager mgr = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
          mgr.showSoftInput(edSearch, InputMethodManager.SHOW_FORCED);
      }
    }


    private void intiView()
    {
        allJobsBeanList = new ArrayList<>();
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewKeywords.setHasFixedSize(false);
        recyclerViewKeywords.setLayoutManager(layoutManager);
        keywordAdapter = new KeywordAdapter(SearchJobFragment.this);
        recyclerViewKeywords.setAdapter(keywordAdapter);

        seek.setMax(100);
        seek.setProgress(0); // Set it to zero so it will start at the left-most edge
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


                distance.setText(Integer.toString(progress) +" "+SearchJobFragment.this.getResources().getString(R.string.kilo_metter));

                SearchDistance=String.valueOf(progress);
                progress = progress + 1;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}

        });

    }

    /** load more search content through paging */
    AdapterLook.OnLoadMoreListener onLoadMoreListener = new AdapterLook.OnLoadMoreListener() {
        @Override
        public void onLoadMore() {
            //add null , so the adapter will check view_type and show progress bar at bottom
            allJobsBeanList.add(null);
            recyclerView.post(new Runnable() {
                public void run() {
                    adpterLook.notifyItemInserted(allJobsBeanList.size() - 1);
                }
            });
            serviceSearchJob(false, pageNo);
        }
    };

    /** get tha action from keyboard */
    TextView.OnEditorActionListener onEditorActionListener = (v, actionId, event) -> {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (!v.getText().toString().trim().isEmpty())
                keywordAdapter.addKeyword(v.getText().toString());

         //   UtilsMethods.hideSoftKeyboard(getActivity());
            edSearch.clearFocus();
            InputMethodManager in = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(edSearch.getWindowToken(), 0);
            relDistance.setVisibility(View.GONE);

            pageNo = 1;
            serviceSearchJob(true, pageNo);
            return true;
        }
        return false;
    };

    /** make single line */
    private void setEditTextSingleLine() {
        edSearch.setSingleLine(true);
    }

    /** filter dialog */
    @OnClick(R.id.imageViewFilter)
    void openDialogFilter() {
        Bundle bundle=new Bundle();
        bundle.putString("filterContractType",filterContractType);
        bundle.putString("filterEducationLevel",filterEducationLevel);
        bundle.putString("filterNumHours",filterNumHours);
        bundle.putString("filterExperience",filterExperience);
        bundle.putString("typeOfContractPosition",typeOfContractPosition);
        bundle.putString("experiencePosition",experiencePosition);
        bundle.putString("eduLevelPosition",eduLevelPosition);
        bundle.putString("fullPartTimePosition",fullPartTimePosition);

        ((HomeSeekerActivity) getActivity()).addFragment
                (new MoreFilterFragment(SearchJobFragment.this),
                       bundle, Keys.MORE_FILTER, false,
                        true);
    }

    /** set the searched text to search editText */
    public void setTextToEdSearch(String keywords) {
        edSearch.setText(keywords);
    }
    String searchLats=""; ; String searchLongs="";
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //  super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Keys.REQ_CODE_APPLY
                && resultCode == RESULT_OK)
        {
            // new Thread(() -> ((HomeSeekerActivity) getActivity()).addFragment(new ProfileFragment(), new Bundle(), Keys.PROFILE, false, true)).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ((HomeSeekerActivity) getActivity()).AddFragmnt(new ProfileFragment(), Keys.PROFILE);
                }
            }).start();
        }

        if (requestCode == Keys.LOCATION_CODE && resultCode == RESULT_OK && data != null)
        {
            searchLocationName = data.getStringExtra(Keys.LOCATION_CITY);
            double searchLat = data.getDoubleExtra(Keys.LOCATION_LAT, 0);
            double searchLong = data.getDoubleExtra(Keys.LOCATION_LONG, 0);
            searchLats=String.valueOf(searchLat);
            searchLongs=String.valueOf(searchLong);
            currentLatitude = data.getDoubleExtra(Keys.CURRENT_LAT, 0);
            currentLongitude = data.getDoubleExtra(Keys.CURRENT_LONG, 0);
           /* if (searchLat != 0 && currentLatitude != 0) {
                String distance = UtilsMethods.calculateDistanceInKm(String.valueOf(searchLat),
                        String.valueOf(searchLong),
                        String.valueOf(currentLatitude),
                        String.valueOf(currentLongitude));
                tvLocation.setText(searchLocationName + "-" + distance + "KM");
            } else {*/
                tvLocation.setText(searchLocationName+","+UtilsMethods.postalCode);
           // }
            pageNo = 1;
            serviceSearchJob(true, pageNo);
        }
        if (requestCode == Keys.LOCATION_ENABLE_CODE)
        {
            gettingLocation();
        }
    }


    /** filter job search */
    public void setFilterValue() {
        //  if (!UtilsMethods.isEmpty(edSearch)) {
        pageNo = 1;
        if (allJobsBeanList != null)
            allJobsBeanList.clear();
        serviceSearchJob(true, pageNo);
        // }
    }

    /** notify changes on job listing when job has appliend from job detais screen. */
    public void updateToJobApply(int pos, String apply_on) {
        if (adpterLook != null) {
            adpterLook.applyJob(pos, apply_on);
        }
    }
/*void setGetAdvertisementResponce(int size)
{
    if (getAdvertisementResponce.isSuccess())
    {
       //for repeate ads when scrools
      int ind=(int) Math.floor(size/3);

   for (int i =ind; i < getAdvertisementResponce.getData().size(); i++)
        {
            GetSearchJobResponse.DataBean.AllJobsBean getSearchJobResponses = new GetSearchJobResponse.DataBean.AllJobsBean();
            getSearchJobResponses.setAdvertisement_image(getAdvertisementResponce.getData().get(i).getAdvertisement_image());
            getSearchJobResponses.setTitle(getAdvertisementResponce.getData().get(i).getTitle());
            getSearchJobResponses.setUrl(getAdvertisementResponce.getData().get(i).getUrl());
            getSearchJobResponses.setAdvertisement_file(getAdvertisementResponce.getData().get(i).getAdvertisement_file());
            getSearchJobResponses.setAdvertisement_type(getAdvertisementResponce.getData().get(i).getAdvertisement_type());
            getSearchJobResponses.setFile_type(getAdvertisementResponce.getData().get(i).getFile_type());
            getSearchJobResponses.setApp_advertisement_id(getAdvertisementResponce.getData().get(i).getApp_advertisement_id());
            int index=(3*i)+2;
            if(index<allJobsBeanList.size()) {
                allJobsBeanList.add(index, getSearchJobResponses);
            }

        }
    }
}*/
    /** call service for verify code */
    private void serviceSearchJob(boolean loaderSDhow, int page)
    {
      relDistance.setVisibility(View.GONE);
        final SpotsDialog spotsDialog = new SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);


        JsonObject jsonObject = new JsonObject();

        if (loaderSDhow)
            spotsDialog.show();
        try {

        jsonObject.addProperty(Keys.START, String.valueOf(page));
        jsonObject.addProperty(Keys.USER_ID, Singleton.getUserInfoInstance().getUser_id());
        jsonObject.addProperty(Keys.JOB_TITLE, edSearch.getText().toString());
        jsonObject.addProperty(Keys.JOB_LOCATION, searchLocationName);
if(filterContractTypeId!=""){
           String [] filterContractTypeIdstr=  filterContractTypeId
                 .split(",");
               JsonArray filterContractTypeIdlist = new JsonArray();
               for(int i=0;i<filterContractTypeIdstr.length;i++)
               {
                   filterContractTypeIdlist.add(filterContractTypeIdstr[i]);
               }
          jsonObject.add(Keys.CONTRACT_TYPE,filterContractTypeIdlist);

        }
else {
            jsonObject.addProperty(Keys.CONTRACT_TYPE, "");
        }

        if(filterEducationLevelId!="") {
    String[] filterEducationLevelIdstr = filterEducationLevelId
            .split(",");
    JsonArray filterEducationLevelIdlist = new JsonArray();
    for (int i = 0; i < filterEducationLevelIdstr.length; i++) {
        filterEducationLevelIdlist.add(filterEducationLevelIdstr[i]);
    }
    jsonObject.add(Keys.EDUCATION_LEVEL, filterEducationLevelIdlist);

}
else {
    jsonObject.addProperty(Keys.EDUCATION_LEVEL, "");
}
if(filterExperienceId!="")
{
    String[] filterExperienceIdstr = filterExperienceId
            .split(",");
    JsonArray filterExperienceIdlist = new JsonArray();
    for (int i = 0; i < filterExperienceIdstr.length; i++) {
        filterExperienceIdlist.add(filterExperienceIdstr[i]);
    }

    jsonObject.add(Keys.EXPERIENCE, filterExperienceIdlist);
}else {
    jsonObject.addProperty(Keys.EXPERIENCE, "");

}
            if(filterNumHoursId!="") {
                String[] filterNumHoursIdstr = filterNumHoursId
                        .split(",");
                JsonArray filterNumHoursIdlist = new JsonArray();
                for (int i = 0; i < filterNumHoursIdstr.length; i++) {
                    filterNumHoursIdlist.add(filterNumHoursIdstr[i]);
                }
                jsonObject.add(Keys.NUM_HOURS, filterNumHoursIdlist);
            }
            else {
                jsonObject.addProperty(Keys.NUM_HOURS, "");
            }

            if(filterJobIdlist!=null) {

                jsonObject.add(Keys.fetchedJobIds, filterJobIdlist);
            }
            else {
                jsonObject.addProperty(Keys.fetchedJobIds, "");
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        jsonObject.addProperty(Keys.LATTITUDE, searchLats);

        jsonObject.addProperty(Keys.LONGITUDE, searchLongs);
        jsonObject.addProperty(Keys.distance_radius, SearchDistance);
        jsonObject.addProperty(Keys.JOB_LOCATION, searchLocationName);

        retrofit.Call<GetSearchJobResponse> call = AppRetrofit.
                getAppRetrofitInstance().getApiServices().getSearchJobResponse(jsonObject);

        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetSearchJobResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetSearchJobResponse> response, retrofit.Retrofit retrofit) {
                if (spotsDialog.isShowing())
                    spotsDialog.dismiss();

                GetSearchJobResponse getSearchJobResponse = response.body();

                if (getSearchJobResponse.isSuccess()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    if (page == 1) {
                        filterJobIdlist = new JsonArray();


                        for (int i = 0; i < getSearchJobResponse.getData().getAllJobs().size(); i++) {
                            filterJobIdlist.add( getSearchJobResponse.getData().getAllJobs().get(i).getJob_id());
                        }

                        if (getSearchJobResponse.getData().getAllJobs().size() > 0)
                        {
                            tvNoResultFound.setVisibility(View.GONE);
                            allJobsBeanList = getSearchJobResponse.getData().getAllJobs();
                        }
                        else
                        {
                            tvNoResultFound.setVisibility(View.VISIBLE);
                            tvNoResultFound.setText(getSearchJobResponse.getMsg());
                            allJobsBeanList.clear();
                        }
                        adpterLook = new AdapterLook(getActivity(), allJobsBeanList,
                                recyclerView, SearchJobFragment.this);
                        recyclerView.setAdapter(adpterLook);


                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        adpterLook.setOnLoadMoreListener(onLoadMoreListener);
                     //  serviceGetAdvertisement(getSearchJobResponse);

                     } else {

                        for (int i = 0; i < getSearchJobResponse.getData().getAllJobs().size(); i++) {
                            filterJobIdlist.add( getSearchJobResponse.getData().getAllJobs().get(i).getJob_id());
                        }

                        //remove progress item
                        if (allJobsBeanList.size()>0){
                            allJobsBeanList.remove(allJobsBeanList.size() - 1);
                           // setGetAdvertisementResponce();
                            adpterLook.notifyItemRemoved(allJobsBeanList.size());
                        }

                        if (getSearchJobResponse.getData().getAllJobs().size() > 0) {
                         // int size =allJobsBeanList.size();
                            allJobsBeanList.addAll(getSearchJobResponse.getData().getAllJobs());
                          /*  if(getAdvertisementResponce.isSuccess()) {
                                setGetAdvertisementResponce(size);
                            }*/
                            adpterLook.notifyItemInserted(allJobsBeanList.size());
                            //add items one by one
                            adpterLook.setLoaded();
                        } else {
                           // int size =allJobsBeanList.size();
                            allJobsBeanList.addAll(getSearchJobResponse.getData().getAllJobs());
                          /*  if(getAdvertisementResponce.isSuccess()) {
                                setGetAdvertisementResponce(size);
                            }*/
                            adpterLook.notifyItemInserted(allJobsBeanList.size());
                        }
                        if (getSearchJobResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                            UtilsMethods.unAuthorizeAlert(getActivity(), getSearchJobResponse.getMsg());
                        }
                    }
                    if (tvLocation.getText().toString().length()<=0) {
                        tvLocation.setText(getSearchJobResponse.getUser_location());
                    }
                    pageNo = pageNo + 1;
                } else {
                    if (getSearchJobResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(getActivity(), getSearchJobResponse.getMsg());
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

    /** call service for verify code *//*
    private void serviceGetAdvertisement(GetSearchJobResponse getSearchJobResponse)
    {
        final SpotsDialog spotsDialog = new SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        retrofit.Call<GetAdvertisementResponce> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getAdvertisementResponse();

        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetAdvertisementResponce>()
        {
            @Override
            public void onResponse(retrofit.Response<GetAdvertisementResponce> response, retrofit.Retrofit retrofit)
            {
                if (spotsDialog.isShowing())
                    spotsDialog.dismiss();

                 getAdvertisementResponce = response.body();


                    if (getAdvertisementResponce.isSuccess())
                    {
                        //vikash
                        for (int i = 0; i <getAdvertisementResponce.getData().size(); i++)
                        {
                            GetSearchJobResponse.DataBean.AllJobsBean getSearchJobResponses =
                                    new GetSearchJobResponse.DataBean.AllJobsBean();
                            getSearchJobResponses.setAdvertisement_image(getAdvertisementResponce.getData().get(i).getAdvertisement_image());
                            getSearchJobResponses.setTitle(getAdvertisementResponce.getData().get(i).getTitle());
                            getSearchJobResponses.setUrl(getAdvertisementResponce.getData().get(i).getUrl());
                            getSearchJobResponses.setAdvertisement_file(getAdvertisementResponce.getData().get(i).getAdvertisement_file());
                            getSearchJobResponses.setAdvertisement_type(getAdvertisementResponce.getData().get(i).getAdvertisement_type());
                            getSearchJobResponses.setFile_type(getAdvertisementResponce.getData().get(i).getFile_type());
                            getSearchJobResponses.setApp_advertisement_id(getAdvertisementResponce.getData().get(i).getApp_advertisement_id());
                            int index=(3*i)+2;
                            if(allJobsBeanList.size()>index)
                            {
                                allJobsBeanList.add(index, getSearchJobResponses);
                            }
                        }

                       // setGetAdvertisementResponce();
                        adpterLook.notifyDataSetChanged();
                    }

                }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(getActivity(), t.getMessage());
            }
        });

    }*/
}
