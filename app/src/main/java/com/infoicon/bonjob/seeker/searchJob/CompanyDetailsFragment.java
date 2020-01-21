package com.infoicon.bonjob.seeker.searchJob;


import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CircleImageView;
import com.infoicon.bonjob.customview.CustomRadioButton;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.main.HomeSeekerActivity;
import com.infoicon.bonjob.permission.MarshmallowPermission;
import com.infoicon.bonjob.permission.PermissionKeys;
import com.infoicon.bonjob.picasso.ImageLoader;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.response.GetCompanyDetailsResponse;
import com.infoicon.bonjob.retrofit.response.GetGenerateReportResponse;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.CommonUtils;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;

import static android.app.Activity.RESULT_OK;


public class CompanyDetailsFragment extends Fragment implements OnMapReadyCallback {

    private String TAG = this.getClass().getSimpleName();
    View rootView;
    @BindView(R.id.videoView) VideoView videoView;
    @BindView(R.id.rlController) RelativeLayout rlController;
    @BindView(R.id.imageViewPlay) ImageView imageViewPlay;
    @BindView(R.id.imageViewPause) ImageView imageViewPause;
    @BindView(R.id.imageViewFull) ImageView imageViewFull;
    @BindView(R.id.tvTitle) CustomsTextViewBold tvTitle;
    @BindView(R.id.imgViewCompany) ImageView imgViewCompany;
    @BindView(R.id.tvAbout) CustomsTextView tvAbout;
    @BindView(R.id.proper) LinearLayout proper;
    @BindView(R.id.textViewWebLink) CustomsTextView textViewWebLink;
    @BindView(R.id.textViewRecruiterName) CustomsTextView textViewRecruiterName;
    @BindView(R.id.rbActive) CustomRadioButton rbActive;
    @BindView(R.id.imgViewJob) CircleImageView imgViewRecruiter;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.rlRetry) RelativeLayout rlRetry;
    @BindView(R.id.tvErrorMessage) CustomsTextViewBold tvErrorMessage;
    @BindView(R.id.scrollViewMain) NestedScrollView scrollViewMain;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.rlthumbNail) RelativeLayout rlthumbNail;
    @BindView(R.id.rlVideoMain) RelativeLayout rlVideoMain;
    @BindView(R.id.imageViewThumb) ImageView imageViewThumb;
    @BindView(R.id.imageViewPlayIcon) ImageView imageViewPlayIcon;
    @BindView(R.id.linearCompanyWebsite) LinearLayout linearCompanyWebsite;
    @BindView(R.id.linearVideo) LinearLayout linearVideo;
    private Unbinder unbinder;

    private CompanyJobOfferAdapter companyJobOfferAdapter;
    private String videoLink = "";
    private MarshmallowPermission marshmallowPermission;
    private SupportMapFragment mapFragment;
    private double companyLat, companyLong;
    private String employerId;
    private int position;
    private String reportId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
            rootView = inflater.inflate(R.layout.fragment_company_details, container, false);
            unbinder = ButterKnife.bind(this, rootView);
            initialize();
            getBundles();
            serviceForGetCompanyDetails();
        } catch (InflateException e) {
            e.printStackTrace();
        }
        return rootView;
    }

    /** get bundle data */
    private void getBundles() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            employerId = bundle.getString(Keys.EMPLOYER_ID);
            position = bundle.getInt(Keys.POSITION);
            checkUserOnlineStatus();
        }
    }

    /** check user is online or not */
    private void checkUserOnlineStatus() {
        if (Singleton.getOnlineList().containsKey(Keys.BONJOB_ + employerId)) {
            String status = Singleton.getOnlineList().get(Keys.BONJOB_ + employerId);
            if (status.equals(Keys.AVAILABLE)) {
                rbActive.setChecked(true);
                rbActive.setText(getResources().getString(R.string.active));
            } else {
                rbActive.setChecked(false);
                rbActive.setText(getResources().getString(R.string.offline));
            }
        }
    }


    @Override
    public void onResume() {
        Logger.e(TAG + " onResume1");
        super.onResume();
        Logger.e(TAG + " onResume2");
        ((HomeSeekerActivity) getActivity()).setCheckedToBottom(Keys.LOOK_FOR);
    }

    @Override
    public void onPause() {
        Logger.e(TAG + " onPause1");
        super.onPause();
        Logger.e(TAG + " onPause2");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initialize() {
        // Start the MediaController
        MediaController mediacontroller = new MediaController(getActivity());
        mediacontroller.setAnchorView(videoView);
        // Get the URL from String VideoURL

        videoView.setOnCompletionListener(onCompletionListener);
        videoView.setOnTouchListener(onTouchListener);
        videoView.setOnErrorListener(onErrorListener);
        videoView.setOnPreparedListener(onPreparedListener);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        marshmallowPermission = new MarshmallowPermission(getActivity());

/*        recyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);*/
    }
    /** go back to previous screen */
    @OnClick(R.id.textViewBack)
    void GoBack() {
        (getActivity()).onBackPressed();
    } /** go back to previous screen */

    @OnClick(R.id.all_jobs)
    void AllJObs() {
        CompanyAllJobFragment companyAllJobFragment = new CompanyAllJobFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Keys.EMPLOYER_ID,employerId);
        ((HomeSeekerActivity) getActivity()).addInnerFragment(companyAllJobFragment,
                bundle, Keys.COMPANY_JOBS, false, true);

    }

    /** event for play video */
    @OnClick(R.id.imageViewPlay)
    void play() {
        if (!videoView.isPlaying()) {
            hidePlayPauseController(0);
            hideShowView(0);
            videoView.start();
            progressBar.setVisibility(View.VISIBLE);
            imageViewPlayIcon.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.imageViewPlayIcon)
    void PlayFirstTime() {
        if (!videoView.isPlaying()) {
            hidePlayPauseController(0);
            hideShowView(0);
            videoView.setVideoPath(videoLink);
            UtilsMethods.disableSSLCertificateChecking();
            videoView.start();
            progressBar.setVisibility(View.VISIBLE);
            imageViewPlayIcon.setVisibility(View.GONE);
        }
    }


    /** event for pause video */
    @OnClick(R.id.imageViewPause)
    void pause() {
        if (videoView.isPlaying()) {
            hidePlayPauseController(1);
            hideShowView(1);
            progressBar.setVisibility(View.GONE);
            videoView.pause();
        }
    }

    /** evet for play video full screen */
    @OnClick(R.id.imageViewFull)
    void fullView() {
        //  File file = new File("fileUri");
        Intent intent = new Intent(getActivity(), PlayVideoFullSreenActivity.class);
        intent.putExtra(Keys.PATCH_VIDEO, videoLink);
        startActivity(intent);
    }


    /** event for report mail */
    @OnClick(R.id.textViewReportInAppropriate)
    void report() {
        reportMail();
    }

    /** event for redirect to web */
    @OnClick(R.id.textViewWebLink)
    void wed() {
        String url = textViewWebLink.getText().toString().trim();
        if (!url.equals("")) {
            openWebPage(url);
        }
    }

    /** retry to load data again */
    @OnClick(R.id.btnRetry)
    void retry() {
        serviceForGetCompanyDetails();
    }


    /**
     * show map if location is on and permission for location is granted.
     */
    private void showMap() {
        if (getActivity() != null && isAdded()) {
            if (UtilsMethods.isLocationEnabled(getActivity())) {
                String[] permissions = {PermissionKeys.PERMISSION_ACCESS_FINE_LOCATION, PermissionKeys.PERMISSION_ACCESS_COARSE_LOCATION};
                if (marshmallowPermission.isPermissionGrantedAll(PermissionKeys.REQUEST_CODE_PERMISSION_ALL, permissions)) {
                    mapFragment.getMapAsync(this);
                }
            }
        }
    }

    /** open browser */
    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            webpage = Uri.parse("http://" + url);
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /** send mail */
    private void reportMail()
    {

        String currentTime = DateFormat.getDateTimeInstance().format(new Date()) + "_" + employerId;
        byte[] data;
        try {
            data = currentTime.getBytes("UTF-8");
            reportId = Base64.encodeToString(data, Base64.DEFAULT);
            Logger.e(TAG + " Report Id : " + reportId);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String name = Singleton.getUserInfoInstance().getFirst_name() + " " + Singleton.getUserInfoInstance().getLast_name();
        String email = Singleton.getUserInfoInstance().getEmail();
        String message = getResources().getString(R.string.reason_of_your_message) + "\n\n\n\n\n\n\n" +
                getResources().getString(R.string.reoprt_content) + "\n" +
                getResources().getString(R.string.report_id) + " " + reportId + "\n" +
                getResources().getString(R.string.user) + " " + name + " - " + email;

        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        /* Fill it with Data */
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{Keys.MAIL_ID});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getResources().getString(R.string.subject));
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);


        List<Intent> targetedShareIntents = new ArrayList<Intent>();
        List<ResolveInfo> resInfo = getActivity().getPackageManager().queryIntentActivities(emailIntent, 0);
        if (!resInfo.isEmpty()) {
            for (ResolveInfo info : resInfo) {
                Intent targetedShare = new Intent(android.content.Intent.ACTION_SEND);
                targetedShare.setType("text/plain"); // put here your mime type
                targetedShare.setPackage(info.activityInfo.packageName.toLowerCase());
                targetedShareIntents.add(targetedShare);
            }
            // Then show the ACTION_PICK_ACTIVITY to let the user select it
            Intent intentPick = new Intent();
            intentPick.setAction(Intent.ACTION_PICK_ACTIVITY);
            // Set the title of the dialog
            intentPick.putExtra(Intent.EXTRA_TITLE, getResources().getString(R.string.about));
            intentPick.putExtra(Intent.EXTRA_INTENT, emailIntent);
            intentPick.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray());
            // Call StartActivityForResult so we can get the app name selected by the user
            startActivityForResult(intentPick, Keys.REQUEST_CODE_EMAIL);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Keys.REQUEST_CODE_EMAIL) {
            if (data != null && data.getComponent() != null && !TextUtils.isEmpty(data.getComponent().flattenToShortString())) {
                String appName = data.getComponent().flattenToShortString();
                Logger.e(TAG + " : " + appName);
                // Now you know the app being picked.
                // data is a copy of your launchIntent with this important extra info added.

                // Start the selected activity
                startActivityForResult(data, Keys.REQUEST_CODE_SEND_EMAIL);
            }
        }
        if (requestCode == Keys.REQUEST_CODE_SEND_EMAIL) {
            //   serviceReportInAppropriate();
            if (resultCode == RESULT_OK) {
                Logger.e(TAG + " Email send");
            } else {
                Logger.e(TAG + " Failed to send Email");
            }
        }
    }

    /** call service for send inappropriate content */
    /*private void serviceReportInAppropriate() {
        final SpotsDialog spotsDialog = new SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.REPORT_ID, reportId);
        jsonObject.addProperty(Keys.JOB_ID, job_id);
        retrofit.Call<GetGenerateReportResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getGenerateReportResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetGenerateReportResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetGenerateReportResponse> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                GetGenerateReportResponse getApplyJobResponse = response.body();
                if (getApplyJobResponse.isSuccess()) {
                    //notify search adapter

                } else {

                    if (getApplyJobResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(getActivity(), getApplyJobResponse.getMsg());
                    }// else
                    //  CommonUtils.showSimpleMessageBottom(getActivity(), getApplyJobResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(getActivity(), t.getMessage());
            }
        });
    }*/

    /** call service for get company details */
    private void serviceForGetCompanyDetails() {
        final SpotsDialog spotsDialog = new SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.EMPLOYER_ID, employerId);
        retrofit.Call<GetCompanyDetailsResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getCompanyDetailsResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetCompanyDetailsResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetCompanyDetailsResponse> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                GetCompanyDetailsResponse getCompanyDetailsResponse = response.body();

                if (getCompanyDetailsResponse.isSuccess()) {
                    isShowRetry(false, "");
                    GetCompanyDetailsResponse.DataBean.CompanyDetailsBean companyDetailsBean = getCompanyDetailsResponse.getData().getCompany_details();
                    if (!companyDetailsBean.getEnterprise_pic().equals(""))
                        if (getActivity() != null && isAdded())
                            ImageLoader.loadImage(getActivity(), companyDetailsBean.getEnterprise_pic(), imgViewCompany);

                    if (companyDetailsBean.getAbout() != null
                            && !companyDetailsBean.getAbout().trim().equalsIgnoreCase("")
                            && !companyDetailsBean.getAbout().trim().isEmpty()) {
                        tvAbout.setText(companyDetailsBean.getAbout());
                    }
                    else
                    {
                        proper.setVisibility(View.GONE);
                    }

                    // check current activity is added or not null because tapping multiple time in job list company name app got crash
                    if (getActivity() != null && isAdded()) {
                        String url = companyDetailsBean.getWebsite();
                        if (!url.equals("")) {
                            Uri webpage = Uri.parse(url);
                            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                                webpage = Uri.parse("http://" + url);
                            }
                            textViewWebLink.setText(webpage.toString().toLowerCase());
                        } else {
                            textViewWebLink.setVisibility(View.GONE);
                            linearCompanyWebsite.setVisibility(View.GONE);
                        }
                    }

                    if (getActivity() != null && isAdded()) {
                        if (!companyDetailsBean.getUser_pic().equals(""))
                            ImageLoader.loadImageWithCircle(getActivity(), companyDetailsBean.getUser_pic(), imgViewRecruiter);
                        textViewRecruiterName.setText(companyDetailsBean.getFirst_name() + " " + companyDetailsBean.getLast_name());
                        tvTitle.setText(companyDetailsBean.getEnterprise_name());
                    }
                    if (getActivity() != null && isAdded()) {
                        if (!companyDetailsBean.getPatch_video().equals("") && !companyDetailsBean.getPatch_video_thumbnail().equals("")) {
                            videoLink = companyDetailsBean.getPatch_video();
                            ImageLoader.loadImage2(getActivity(), companyDetailsBean.getPatch_video_thumbnail(), imageViewThumb);
                        } else {
                            rlVideoMain.setVisibility(View.GONE);
                            linearVideo.setVisibility(View.GONE);
                        }
                    }

                    if (companyDetailsBean.getCompany_lat() != null && !companyDetailsBean.getCompany_lat().equals("")) {
                        companyLat = Double.parseDouble(companyDetailsBean.getCompany_lat());
                        companyLong = Double.parseDouble(companyDetailsBean.getCompany_long());
                        showMap();
                    }

              /*      if (getActivity() != null && isAdded()) {
                        companyJobOfferAdapter = new CompanyJobOfferAdapter(getActivity(), getCompanyDetailsResponse.getData().getAllJobs());
                        recyclerView.setAdapter(companyJobOfferAdapter);
                    }

*/
                } else {
                    if (getCompanyDetailsResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(getActivity(), getCompanyDetailsResponse.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                isShowRetry(true, t.getMessage());
            }
        });
    }


    /** show retry option if data is not loading from server */
    private void isShowRetry(boolean isShowRetry, String message) {
        if (getActivity() != null && isAdded()) {
            if (isShowRetry) {
                scrollViewMain.setVisibility(View.GONE);
                rlRetry.setVisibility(View.VISIBLE);
                tvErrorMessage.setText(message);
            } else {
                scrollViewMain.setVisibility(View.VISIBLE);
                rlRetry.setVisibility(View.GONE);
            }
        }
    }

    MediaPlayer.OnPreparedListener onPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            Logger.e(TAG + "onPrepared ");
            progressBar.setVisibility(View.GONE);
            rlthumbNail.setVisibility(View.GONE);
            mp.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    Logger.e(TAG + " onBufferingUpdate : " + percent);
                    if (percent == 100) {
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        }
    };


    /** media complete listener */
    MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            Logger.e(TAG + "onCompletion ");
            progressBar.setVisibility(View.GONE);
            hideShowView(1);
            hidePlayPauseController(1);
        }
    };

    /** error listener while playing video */
    MediaPlayer.OnErrorListener onErrorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            Logger.e(TAG + "onError ");
            progressBar.setVisibility(View.GONE);
            hidePlayPauseController(1);
            return true;
        }
    };

    /** touch view for video */
    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Logger.e(TAG + "onTouch ");
            if (videoView.isPlaying() && rlController.getVisibility() == View.VISIBLE) {
                hideShowView(0);
            } else {
                hideShowView(1);
            }

            return false;
        }
    };

    /** hide show controller on basis of status */
    private void hideShowView(int status) {

        if (rlController == null)
            return;

        if (status == 0) {
            rlController.animate()
                    .alpha(0.0f)
                    .setDuration(1000)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            if (rlController != null)
                                rlController.setVisibility(View.GONE);
                        }
                    });
        } else {
            rlController.animate()
                    .alpha(1.0f)
                    .setDuration(1000)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            if (rlController != null)
                                rlController.setVisibility(View.VISIBLE);
                        }
                    });
        }

    }


    /**
     * hide play pause controller
     * @param status 0-> hide play icon ,1->hide pause icon
     */
    void hidePlayPauseController(int status) {
        if (status == 0) {
            imageViewPlay.setVisibility(View.GONE);
            imageViewPause.setVisibility(View.VISIBLE);
        } else {
            imageViewPlay.setVisibility(View.VISIBLE);
            imageViewPause.setVisibility(View.GONE);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        //this.googleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: 9/2/17  if permission is not available
            return;
        }
        // set location enable true
        googleMap.setMyLocationEnabled(true);
        //set camera position in  map
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(companyLat, companyLong), 15));
        MarkerOptions markerOptions = new MarkerOptions();
        //current location marker
        //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).title("My Location");
        //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.category)).title("My Location");
        markerOptions.position(new LatLng(companyLat, companyLong));
        googleMap.addMarker(markerOptions);
    }
}
