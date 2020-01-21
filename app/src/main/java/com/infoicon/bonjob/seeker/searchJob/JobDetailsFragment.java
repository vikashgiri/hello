package com.infoicon.bonjob.seeker.searchJob;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.chat.Message;
import com.infoicon.bonjob.customview.CustomsButton;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.main.HomeSeekerActivity;
import com.infoicon.bonjob.permission.MarshmallowPermission;
import com.infoicon.bonjob.permission.PermissionKeys;
import com.infoicon.bonjob.picasso.ImageLoader;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.response.GetApplyJobResponse;
import com.infoicon.bonjob.retrofit.response.GetGenerateReportResponse;
import com.infoicon.bonjob.retrofit.response.GetJobDetailsResponse;

import com.infoicon.bonjob.seeker.profile.ProfileFragment;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.splash.GetAllDropDownResponce;
import com.infoicon.bonjob.utils.CommonUtils;
import com.infoicon.bonjob.utils.GPSLocation;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

import static android.app.Activity.RESULT_OK;


public class JobDetailsFragment extends Fragment implements OnMapReadyCallback {


    @BindView(R.id.textViewLocation)
    CustomsTextView textViewLocation;
    @BindView(R.id.btnApply)
    CustomsButton btnApply;

    @BindView(R.id.imageLayout)
    LinearLayout imageLayout;

    @BindView(R.id.relativeCompanyName)
    RelativeLayout relativeCompanyName;
    @BindView(R.id.tvJobTitle)
    CustomsTextViewBold tvJobTitle;
    @BindView(R.id.linearJobTitle)
    LinearLayout linearJobTitle;
    @BindView(R.id.tvContract)
    CustomsTextViewBold tvContract;
    @BindView(R.id.linearContract)
    LinearLayout linearContract;
    @BindView(R.id.tvExperience)
    CustomsTextViewBold tvExperience;
    @BindView(R.id.linearExperience)
    LinearLayout linearExperience;
    @BindView(R.id.tvNoHours)
    CustomsTextViewBold tvNoHours;
    @BindView(R.id.linearNumHours)
    LinearLayout linearNumHours;
    @BindView(R.id.tvStartdate)
    CustomsTextViewBold tvStartdate;
    @BindView(R.id.linearStartDate)
    LinearLayout linearStartDate;
    @BindView(R.id.tvDesc)
    TextView tvDesc;


    @BindView(R.id.tvContact)
    CustomsTextViewBold tvContact;
    @BindView(R.id.linearEducation)
    LinearLayout linearEducation;
    @BindView(R.id.tvEducationLevel)
    CustomsTextViewBold tvEducationLevel;
    @BindView(R.id.linearLanguage)
    LinearLayout linearLanguage;
    @BindView(R.id.tvLanguage)
    CustomsTextViewBold tvLanguage;
    @BindView(R.id.linearSalaries)
    LinearLayout linearSalaries;
    @BindView(R.id.tvSalaries)
    CustomsTextViewBold tvSalaries;
    

    
    
    



    @BindView(R.id.linearLayoutAlreadyApplyView) LinearLayout linearLayoutAlreadyApplyView;
    @BindView(R.id.tvCompanyName) CustomsTextView tvCompanyName;
    @BindView(R.id.tvJobDesc) CustomsTextViewBold tvJobDesc;

    @BindView(R.id.tvPostedOnDateTime) CustomsTextView tvPostedOnDateTime;
    @BindView(R.id.tvPostedOnDate) CustomsTextView tvPostedOnDate;
    @BindView(R.id.textViewWebLink) CustomsTextView textViewWebLink;
    @BindView(R.id.tvApplicationActiveDate) CustomsTextView tvApplicationActiveDate;
    @BindView(R.id.tvAppliedDate) CustomsTextView tvAppliedDate;
    @BindView(R.id.aTvAppliedStatus) CustomsTextView aTvAppliedStatus;
    @BindView(R.id.tvDescComplete) CustomsTextView tvDescComplete;
    @BindView(R.id.tvAppliedStatus) CustomsTextView tvAppliedStatus;
    @BindView(R.id.imageViewJob) ImageView imageViewJob;
    @BindView(R.id.rlRetry) RelativeLayout rlRetry;
    @BindView(R.id.tvErrorMessage) CustomsTextViewBold tvErrorMessage;
    @BindView(R.id.scrollViewMain) ScrollView scrollViewMain;
    @BindView(R.id.linearCompanyWebsite) LinearLayout linearCompanyWebsite;
    @BindView(R.id.progress_bar) ProgressBar progress_bar;

    private String TAG = this.getClass().getSimpleName();
    private View rootView;
    private SupportMapFragment mapFragment;
    private MarshmallowPermission marshmallowPermission;
    private GoogleMap googleMap;
    private double companyLat, companyLong;

    private CallbackManager callbackManager;
    private ShareDialog shareDialog;
    private String job_id;
    private int position;
    private String employerId;
    String reportId = "";
GetAllDropDownResponce GetAllDropDownResponce;
    SearchJobFragment searchJobFragment;
    private String jobImage, jobLink;

    @SuppressLint("ValidFragment")
    public JobDetailsFragment(SearchJobFragment searchJobFragment) {
        this.searchJobFragment = searchJobFragment;
    }

    public JobDetailsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(getActivity());
        shareDialog.registerCallback(callbackManager, callback);
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
            rootView = inflater.inflate(R.layout.fragment_job_details, container, false);
            ButterKnife.bind(this, rootView);
            initialization();
            getBundles();
            serviceForGetJobDetails();
        } catch (InflateException e) {
            e.printStackTrace();
        }
        GetAllDropDownResponce = Singleton.getUserInfoInstance().getAllDropdowns();
        return rootView;
    }

    private void getBundles() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            job_id = bundle.getString(Keys.JOB_ID, "");
            position = bundle.getInt(Keys.POSITION, 0);
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
    public void onPause()
    {
        Logger.e(TAG + " onPause1");
        super.onPause();
        Logger.e(TAG + " onPause2");
    }

    @OnClick(R.id.textViewBack)
    void GoBack() {
        (getActivity()).onBackPressed();
    }

    @OnClick(R.id.imageViewShare)
    void share() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/html");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.discover_job));
        sharingIntent.putExtra(Intent.EXTRA_TEXT, tvJobDesc.getText().toString().trim() + "\n" + jobLink);
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    @OnClick(R.id.imgFbShare)
    void facebookShare() {
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentTitle(getResources().getString(R.string.app_name))
                .setQuote(getResources().getString(R.string.discover_job) + ":\n" + tvJobDesc.getText().toString().trim())
                .setContentUrl(Uri.parse(jobLink))
                .build();
        shareDialog.show(content);
    }

    @OnClick(R.id.imgEmailShare)
    void emailShare() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.discover_job));
        emailIntent.putExtra(Intent.EXTRA_TEXT, tvJobDesc.getText().toString().trim() + "\n" + jobLink);
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    /** event for share on twitter. */
    @OnClick(R.id.imgTwitterShare)
    void twitterShare() {
        try {
            String link = "<a href=" + jobLink + ">" + jobLink + "</a>";
            new TweetComposer.Builder(getActivity())
                    .text(getResources().getString(R.string.discover_job) + ":\n" + tvJobDesc.getText().toString().trim())//+" "+ Html.fromHtml(link)).show();
                    //.image(Uri.parse(jobImage))
                    .url(new URL(jobLink)).show();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    /** event for share on whats app */
    @OnClick(R.id.imgWhatsAppShare)
    void whatsAppShare() {
        shareOnWhatsApp();
    }


    /** event for apply job */
    @OnClick(R.id.btnApply)
    void applyJob() {
        if (btnApply.getText().toString().equals(getResources().getString(R.string.apply))) {
            serviceForApplyJobs();
        }
    }

    /** event for report */
    @OnClick(R.id.textViewReportInAppropriate)
    void report() {
        reportMail();
    }

    /** event for proceed to company et */
    @OnClick(R.id.relativeCompanyName)
    void showCompanyDetails() {
        CompanyDetailsFragment companyDetailsFragment = new CompanyDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Keys.EMPLOYER_ID, employerId);
        bundle.putInt(Keys.POSITION, position);
        ((HomeSeekerActivity) getActivity()).addInnerFragment(companyDetailsFragment, bundle, Keys.COMPANY_DETAILS, false, true);
    }

    /** event for open browser */
    @OnClick(R.id.textViewWebLink)
    void wed() {
        String url = textViewWebLink.getText().toString().trim();
        if (!url.equals("")) {
            openWebPage(url);
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

    /** retry to load data again */
    @OnClick(R.id.btnRetry)
    void retry() {
        serviceForGetJobDetails();
    }



    void addUserOnFireBase(GetApplyJobResponse getApplyJobResponse) {
        FirebaseDatabase.
                getInstance().
                getReference()
                .child("recruiter/" +getApplyJobResponse.getEmployerInfo().getId())
                .
                        addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                HashMap mapUserInfo = (HashMap) dataSnapshot.getValue();
                               final DatabaseReference fireDatabaseReference = FirebaseDatabase.getInstance().getReference();
                                //send msg to user
                                Message newMessage = new Message();
                                newMessage.setJobImage(getApplyJobResponse.getData().get(0).getJob_image());
                                newMessage.setText(getActivity().getString(R.string.msg_apply)+" "+
                                        getApplyJobResponse.getData().get(0).getJob_title());
                                newMessage.setMsgType(getActivity().getString(R.string.msg_types));
                              //  newMessage.setText("Job appilied by candidate");
                                newMessage.setFromId(getApplyJobResponse.getSeekerInfo().getId());
                                newMessage.setToId(getApplyJobResponse.getEmployerInfo().getId());
                                newMessage.setFcmToken((String) mapUserInfo.get("fcmToken"));
                                newMessage.setTimeStamp(System.currentTimeMillis() / 1000);

                                newMessage.setReadStatus("0");               //send msg
                                Message.setUserType from = new Message.setUserType();
                                from.setName(getApplyJobResponse.getSeekerInfo().getFirst_name()
                                        + " " + getApplyJobResponse.getSeekerInfo().getLast_name());
                                from.setType("seeker");
                                from.setProfilePic(Singleton.getUserInfoInstance().getuser_pic());

                                Message.setUserType to = new Message.setUserType();
                                to.setName(getApplyJobResponse.getEmployerInfo().getFirst_name()
                                        + " " + getApplyJobResponse.getEmployerInfo().getLast_name());
                                to.setType("recruiter");
                                to.setProfilePic((String) mapUserInfo.get("profilePic"));

                                newMessage.setFrom(from);
                                newMessage.setTo(to);

                                final DatabaseReference mss = FirebaseDatabase.getInstance().getReference();
                                final String key = mss.child("Messages").push().getKey();
                                newMessage.setMessageID(key);
                                mss.child("Messages/" + key).setValue(newMessage).
                                        addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {


                                                FirebaseDatabase.getInstance().getReference().child("read-Messages").
                                                        child(getApplyJobResponse.getEmployerInfo().getId())
                                                        .child(getApplyJobResponse.getSeekerInfo().getId()
                                                                + "/unreadMessages")
                                                        .child(key).setValue(1);

                                                fireDatabaseReference.
                                                        child("User-List/" + getApplyJobResponse.getSeekerInfo().getId() + "/"
                                                                + getApplyJobResponse.getEmployerInfo().getId() + "/" + key).setValue(1);
                                                fireDatabaseReference.
                                                        child("User-List/" + getApplyJobResponse.getEmployerInfo().getId() + "/"
                                                                + getApplyJobResponse.getSeekerInfo().getId() + "/" + key).setValue(1);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Write failed
                                                // ...
                                            }
                                        });
                                ;


                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value

                            }
                        });
    }

    /** call service for apply job */
    private void serviceForApplyJobs() {
        final SpotsDialog spotsDialog = new SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.USER_ID, Singleton.getUserInfoInstance().getUser_id());
        jsonObject.addProperty(Keys.JOB_ID, job_id);
        retrofit.Call<GetApplyJobResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getApplyJobResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetApplyJobResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetApplyJobResponse> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                GetApplyJobResponse getApplyJobResponse = response.body();
                if (getApplyJobResponse.isSuccess()) {
                    //notify search adapter
                    if (position > 0) {
                        if (searchJobFragment != null)
                            searchJobFragment.updateToJobApply(position, getApplyJobResponse.getData().get(0).getApply_on());
                    }
                    addUserOnFireBase(getApplyJobResponse);
                    // add bubble on activity
                    int count = Singleton.getUserInfoInstance().getSeekerActivityCount();
                    Singleton.getUserInfoInstance().setSeekerActivityCount(count + 1);
                    getActivity().sendBroadcast(new Intent(Keys.ACTIVITY_COUNT));


                    btnApply.setText(getResources().getString(R.string.already_apply));
                    btnApply.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_button_pink));
                    linearLayoutAlreadyApplyView.setVisibility(View.VISIBLE);
                    tvApplicationActiveDate.setText(UtilsMethods.convertDateTimeFormat(getApplyJobResponse.getData().get(0).getApply_on()));
                    tvAppliedDate.setText(UtilsMethods.convertDateTimeFormat(getApplyJobResponse.getData().get(0).getApply_on()));
                    Intent intent = new Intent(getActivity(), ApplyActivity.class);
                    intent.putExtra(Keys.FROM, Keys.JOB_DETAILS);
                    startActivityForResult(intent, Keys.REQ_CODE_APPLY);
                } else {

                    if (getApplyJobResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(getActivity(), getApplyJobResponse.getMsg());
                    } else
                        CommonUtils.showSimpleMessageBottom(getActivity(), getApplyJobResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(getActivity(), t.getMessage());
            }
        });
    }

    /** retry view while not getting data from server or empty data */
    private void isShowRetry(boolean isShowRetry, String message) {
        if (isShowRetry) {
            scrollViewMain.setVisibility(View.GONE);
            rlRetry.setVisibility(View.VISIBLE);
            tvErrorMessage.setText(message);
        } else {
            scrollViewMain.setVisibility(View.VISIBLE);
            rlRetry.setVisibility(View.GONE);
        }
    }


    /** call service for get job details */
    private void serviceForGetJobDetails()
    {
        final SpotsDialog spotsDialog = new SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.USER_ID, Singleton.getUserInfoInstance().getUser_id());
        jsonObject.addProperty(Keys.JOB_ID, job_id);
        retrofit.Call<GetJobDetailsResponse> call = AppRetrofit.getAppRetrofitInstance()
                .getApiServices().getJobDetailsResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetJobDetailsResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetJobDetailsResponse> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                GetJobDetailsResponse getJobDetailsResponse = response.body();
                if (getJobDetailsResponse.isSuccess()) {
                    isShowRetry(false, "");
                    tvCompanyName.setText(getJobDetailsResponse.getData().get(0).getEnterprise_name());
                    /*tvJobDesc.setText(UtilsMethods.getDescription(getActivity(),
                            getJobDetailsResponse.getData().get(0).getJob_title(),
                            getJobDetailsResponse.getData().get(0).getNum_of_hours()));*/
                    tvJobDesc.setText(getJobDetailsResponse.getData().get(0).getJob_title());
                    tvJobTitle.setText(getJobDetailsResponse.getData().get(0).getJob_title());
                    if (getJobDetailsResponse.getData().get(0).getJob_description() != null &&
                            !getJobDetailsResponse.getData().get(0).getJob_description().equals("")) {
                        tvDescComplete.setVisibility(View.VISIBLE);
                        tvDescComplete.setText(getJobDetailsResponse.getData().get(0).getJob_description());
                    }
                    textViewLocation.setText(getJobDetailsResponse.getData().get(0).getJob_location());
                    jobLink = getJobDetailsResponse.getData().get(0).getJobLink();

                    if (getJobDetailsResponse.getData().get(0).getWebsite() != null &&
                            !getJobDetailsResponse.getData().get(0).getWebsite().equals("")) {
                        String url = getJobDetailsResponse.getData().get(0).getWebsite();
                        Uri webpage = Uri.parse(url);
                        if (!url.startsWith("http://") && !url.startsWith("https://")) {
                            webpage = Uri.parse("http://" + url);
                        }
                        textViewWebLink.setText(webpage.toString().toLowerCase());
                    } else {
                        linearCompanyWebsite.setVisibility(View.GONE);
                        textViewWebLink.setVisibility(View.GONE);
                    }

                    tvPostedOnDateTime.setText(UtilsMethods.convertDateTimeFormat(getJobDetailsResponse.getData().get(0).getOffer_posted_on()));

                    if (getActivity() != null && isAdded()) {
                        tvPostedOnDate.setText(getResources().getString(R.string.offer_posted_on) + " " + UtilsMethods.convertDateFormat(getJobDetailsResponse.getData().get(0).getOffer_posted_on()));
                    }
if(getJobDetailsResponse.getData().get(0).getOrigin()
        .equalsIgnoreCase(getResources().getString(R.string.pole_emploi)))
{
    jobImage = getJobDetailsResponse.getData().get(0).getJob_image();
    if (!jobImage.equals("")) {
        ImageLoader.loadJobImageCallback(getActivity(), jobImage, imageViewJob, progress_bar);
    } else {
        imageViewJob.setImageResource(R.drawable.pole_employ_placeholder);
    }
}

else
    {
    jobImage = getJobDetailsResponse.getData().get(0).getJob_image();
    if(jobImage != null && !jobImage.isEmpty()){
        ImageLoader.loadJobImageCallback(getActivity(), jobImage, imageViewJob, progress_bar);
    } else {
        imageViewJob.setImageResource(R.drawable.default_job);
    }
}
                    employerId = getJobDetailsResponse.getData().get(0).getUser_id();

                    if (getActivity() != null && isAdded()) {
                        //date and time of applied job
                        String applyOn = getJobDetailsResponse.getData().get(0).getApply_on();
                        if (applyOn.equals("")) {
                            btnApply.setText(getResources().getString(R.string.apply));
                        } else {
                            btnApply.setText(getResources().getString(R.string.already_apply));
                            btnApply.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_button_pink));
                            linearLayoutAlreadyApplyView.setVisibility(View.VISIBLE);
                    /*0 => "applied",1 => "Selected",2 => "Rejected/Archived",3 => "Hired"*/

                            switch (getJobDetailsResponse.getData().get(0).getApplyStatus()) {
                                case "0"://applied
                                    int minLeft = UtilsMethods.getMinutesLeft(getJobDetailsResponse.getData().get(0).getApply_on(), getJobDetailsResponse.getCurrent_time());
                                    if (minLeft > 0 && minLeft <= 1440) {//time left
                                        aTvAppliedStatus.setText(getResources().getString(R.string.active_application));
                                    } else {//time over
                                        aTvAppliedStatus.setText(getResources().getString(R.string.application_expire));
                                    }
                                    break;
                                case "1"://pre-selected
                                    aTvAppliedStatus.setText(getResources().getString(R.string.pre_selected));
                                    break;
                                case "2"://application not accepted
                                    aTvAppliedStatus.setText(getResources().getString(R.string.appl_not_accepted));
                                    break;
                                case "3"://hired
                                    aTvAppliedStatus.setText(getResources().getString(R.string.your_are_hired));
                                    break;
                                case "4"://expired
                                    aTvAppliedStatus.setText(getResources().getString(R.string.application_expire));
                                    break;
                            }
                            tvApplicationActiveDate.setText(UtilsMethods.convertDateTimeFormat(applyOn));
                            tvAppliedStatus.setText(getResources().getString(R.string.have_applied));
                            tvAppliedDate.setText(UtilsMethods.convertDateTimeFormat(applyOn));
                        }
                    }


                    if (getJobDetailsResponse.getData().get(0).getCompany_lat() != null &&
                            !getJobDetailsResponse.getData().get(0).getCompany_lat().equals("")) {
                        companyLat = Double.parseDouble(getJobDetailsResponse.getData().get(0).getCompany_lat());
                        companyLong = Double.parseDouble(getJobDetailsResponse.getData().get(0).getCompany_long());
                        showMap();
                    }

                    String contract = getJobDetailsResponse.getData().get(0).getContract_type();
                    if (contract != null && !contract.equals("")) {
                        for(int i=0;i<GetAllDropDownResponce.getContractTypes().size();i++)
                        {
                            if(GetAllDropDownResponce.getContractTypes().get(i)
                                    .getContract_id().equalsIgnoreCase(contract))
                            {
                                linearContract.setVisibility(View.VISIBLE);
                                tvContract.setText(GetAllDropDownResponce.getContractTypes().get(i)
                                        .getContract_title());
                                break;
                            }
                            else {
                                linearContract.setVisibility(View.GONE);
                            }
                        }

                    }

                    String experience = getJobDetailsResponse.getData().get(0).getExperience();
                    if (experience != null && !experience.equals("")) {
                        for(int i=0;i<GetAllDropDownResponce.getExperiences().size();i++)
                        {
                            if(GetAllDropDownResponce.getExperiences().get(i)
                                    .getExperience_id().equalsIgnoreCase(experience))
                            {
                                   linearExperience.setVisibility(View.VISIBLE);
                                tvExperience.setText(GetAllDropDownResponce.getExperiences().get(i).getExperience());
                                break;}
                            else {
                                linearExperience.setVisibility(View.GONE);
                            }
                        }


                    }
                    String hours = getJobDetailsResponse.getData().get(0).getNum_of_hours();
                    if (hours != null && !hours.equals("")) {
                        for(int i=0;i<GetAllDropDownResponce.getNumberOfHours().size();i++)
                        {
                            if(GetAllDropDownResponce.getNumberOfHours().get(i)
                                    .getHours_id().equalsIgnoreCase(hours))
                            {
                                linearNumHours.setVisibility(View.VISIBLE);
                                tvNoHours.setText(GetAllDropDownResponce.getNumberOfHours().get(i).getHours_title());
                                break; }
                            else {
                                linearNumHours.setVisibility(View.GONE);
                            }
                        }



                    }

                    String start_date = getJobDetailsResponse.getData().get(0).getOffer_posted_on();
                    if (start_date != null && !start_date.equals("")) {
                        linearStartDate.setVisibility(View.VISIBLE);
                        tvStartdate.setText(start_date);
                    } else {
                        linearStartDate.setVisibility(View.GONE);
                    }
                    String desc = getJobDetailsResponse.getData().get(0).getJob_description();
                    if (desc != null && !desc.equals("")) {
                        tvDesc.setVisibility(View.VISIBLE);
                        tvDesc.setText(desc);
                    } else {
                        tvDesc.setVisibility(View.GONE);
                    }
                    String education = getJobDetailsResponse.getData().get(0).getEducation_level();
                    if (education != null && !education.equals("")) {
                        for(int i=0;i<GetAllDropDownResponce.getEducationLevels().size();i++)
                        {
                            if(GetAllDropDownResponce.getEducationLevels().get(i)
                                    .getEducation_id().equalsIgnoreCase(education))
                            {
                                linearEducation.setVisibility(View.VISIBLE);
                                tvEducationLevel.setText(GetAllDropDownResponce.getEducationLevels().get(i).getEducation_title());
                                break;  }
                            else {
                                linearEducation.setVisibility(View.GONE);
                            }
                        }


                    }

                    String language = getJobDetailsResponse.getData().get(0).getLang();
                    if (language != null && !language.equals("")) {
                        for(int i=0;i<GetAllDropDownResponce.getJobLanguages().size();i++)
                        {
                            if(GetAllDropDownResponce.getJobLanguages().get(i)
                                    .getJob_language_id().equalsIgnoreCase(language))
                            {
                                linearLanguage.setVisibility(View.VISIBLE);
                                tvLanguage.setText(GetAllDropDownResponce.getJobLanguages().get(i).getJob_language_title());
                                break;}
                            else {
                                linearLanguage.setVisibility(View.GONE);
                            }
                        }
                      /*  linearLanguage.setVisibility(View.VISIBLE);
                        tvLanguage.setText(language);*/
                    }

                    String salary = getJobDetailsResponse.getData().get(0).getSalarie();
                    if (salary != null && !salary.equals("")) {
                        for(int i=0;i<GetAllDropDownResponce.getSalaries().size();i++)
                        {
                            if(GetAllDropDownResponce.getSalaries().get(i)
                                    .getSalary_id().equalsIgnoreCase(language))
                            {
                                linearSalaries.setVisibility(View.VISIBLE);
                                tvSalaries.setText(GetAllDropDownResponce.getSalaries().get(i).getSalary_title());
                                break; }
                            else {
                                linearSalaries.setVisibility(View.GONE);
                            }
                        }
                    /*    linearSalaries.setVisibility(View.VISIBLE);
                        tvSalaries.setText(salary);*/
                    }




                    if (getJobDetailsResponse.getData().get(0).getOrigin().
                            equalsIgnoreCase(getResources().getString(R.string.pole_emploi))) {
                        tvContact.setText(getResources().getString(R.string.pole_emploi));
                    }
                    if (getJobDetailsResponse.getData().get(0).getOrigin().
                            equalsIgnoreCase(getResources().getString(R.string.sodexo)))
                    {
                        tvContact.setText(getResources().getString(R.string.sodexo));
                    }
                    if (getJobDetailsResponse.getData().get(0).getOrigin().
                            equalsIgnoreCase(getResources().getString(R.string.app_name)))
                    {
                        String contact =getJobDetailsResponse.getData().get(0).getContact_mode();
                        if (contact != null && !contact.equals("")) {
                            String[] contactArray = contact.split(":", 2);
                            if (contact.length() > 1)
                            {
                                try {
                                    tvContact.setText(contactArray[1]);
                                } catch (ArrayIndexOutOfBoundsException ex) {
                                }
                            }
                        }
                    }


                } else {
                    if (getJobDetailsResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(getActivity(), getJobDetailsResponse.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                isShowRetry(true, t.getMessage());
                // CommonUtils.showSimpleMessageBottom(getActivity(), t.getMessage());
            }
        });
    }


    /** share on whats app */
    private void shareOnWhatsApp() {

        String shareLink = getResources().getString(R.string.discover_job) + ":\n" +
                tvJobDesc.getText().toString().trim() + "\n" +
                Html.fromHtml(jobLink);

        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, shareLink);
        whatsappIntent.setType("text/plain");
        try {
            startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            CommonUtils.showToast(getActivity(), getResources().getString(R.string.whatsapp_not_installed));
        }
    }

    /** send mail */
    private void reportMail() {
        String currentTime = DateFormat.getDateTimeInstance().format(new Date()) + "_" + job_id;
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

        /* Send it off to the Activity-Chooser */
        //startActivityForResult(Intent.createChooser(emailIntent, "Send mail..."), Keys.REQUEST_CODE_EMAIL);
    }

    /** call back for getting the status of sharing content */
    FacebookCallback<Sharer.Result> callback = new FacebookCallback<Sharer.Result>()
    {
        @Override
        public void onSuccess(Sharer.Result result)
        {
            Logger.e(TAG + " success");
        }

        @Override
        public void onCancel()
        {
            Logger.e(TAG + " cancel");
        }

        @Override
        public void onError(FacebookException error)
        {
            Logger.e(TAG + " error");
        }
    };


    /**
     * all initialization is here.
     */
    private void initialization()
    {
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        marshmallowPermission = new MarshmallowPermission(getActivity());
    }

    /**
     * show map if location is on and permission for location is granted.
     */
    private void showMap()
    {
        if (getActivity() != null && isAdded())
        {
            if (UtilsMethods.isLocationEnabled(getActivity())) {
                String[] permissions = {PermissionKeys.PERMISSION_ACCESS_FINE_LOCATION, PermissionKeys.PERMISSION_ACCESS_COARSE_LOCATION};
                if (marshmallowPermission.isPermissionGrantedAll(PermissionKeys.REQUEST_CODE_PERMISSION_ALL, permissions)) {
                    mapFragment.getMapAsync(this);
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: 9/2/17  if permission is not available
            return;
        }
        // set location enable true
        googleMap.setMyLocationEnabled(true);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(companyLat, companyLong), 15));
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(companyLat, companyLong));
        googleMap.addMarker(markerOptions);
    }

    /** get result back if location getting on/off from setting. */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Keys.LOCATION_ENABLE_CODE) {
            gettingLocation();
        }
        if (requestCode == Keys.REQ_CODE_APPLY && resultCode == RESULT_OK) {
            //  String from = data.getStringExtra(Keys.FROM);
            new Thread(() -> ((HomeSeekerActivity) getActivity()).addFragment(new ProfileFragment(), new Bundle(), Keys.PROFILE, false, true)).start();
            /*if (from.equals(Keys.JOB_DETAILS)) {
                new Thread(() ->  ((HomeSeekerActivity) getActivity()).onBackPressed()).start();
            } else {
            }*/
        }
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
            serviceReportInAppropriate();
            if (resultCode == RESULT_OK) {
                Logger.e(TAG + " Email send");
            } else {
                Logger.e(TAG + " Failed to send Email");
            }
        }

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    /** call service for send inappropriate content */
    private void serviceReportInAppropriate() {
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
    }

    /** loader to wait for getting location */
    private void gettingLocation() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getResources().getString(R.string.getting_location));
        progressDialog.show();
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            progressDialog.dismiss();
            showMap();
        }, 2000);
    }

    /** get result back is permission is granted or not. */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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


}
