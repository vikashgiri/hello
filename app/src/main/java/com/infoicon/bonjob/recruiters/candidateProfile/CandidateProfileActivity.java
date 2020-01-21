package com.infoicon.bonjob.recruiters.candidateProfile;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.VideoView;

import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomRadioButton;
import com.infoicon.bonjob.customview.CustomsButton;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.interfaces.FragmentChanger;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.login.LoginSignUpRecruiterActivity;
import com.infoicon.bonjob.main.HomeRecruiterActivity;
import com.infoicon.bonjob.permission.MarshmallowPermission;
import com.infoicon.bonjob.permission.PermissionKeys;
import com.infoicon.bonjob.picasso.ImageLoader;
import com.infoicon.bonjob.recruiters.adapter.ProfileVisitorAdapter;
import com.infoicon.bonjob.recruiters.myOffer.OfferTabFragment;
import com.infoicon.bonjob.recruiters.post.GetJobTitleResponce;
import com.infoicon.bonjob.recruiters.post.GetJobpostDropDown;
import com.infoicon.bonjob.recruiters.post.PostConfirmationActivity;
import com.infoicon.bonjob.recruiters.post.PostJobOfferFragment;
import com.infoicon.bonjob.recruiters.profile.ProfileRecruiterFragment;
import com.infoicon.bonjob.recruiters.search.ViewOnlineCandidateAdapter;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.ServiceUrls;
import com.infoicon.bonjob.retrofit.response.GetHireResponse;
import com.infoicon.bonjob.retrofit.response.GetMyOffersRecruiterResponse;
import com.infoicon.bonjob.retrofit.response.GetSeekerProfileResponse;
import com.infoicon.bonjob.retrofit.response.GetSelectCandidateBySearchResponse;
import com.infoicon.bonjob.retrofit.response.GetSelectCandidateResponse;
import com.infoicon.bonjob.seeker.adapters.LanguageListAdapter;
import com.infoicon.bonjob.seeker.adapters.ProfessionalExperienceAdapter;
import com.infoicon.bonjob.seeker.searchJob.PlayVideoFullSreenActivity;
import com.infoicon.bonjob.servicesConnection.INetworkResponse;
import com.infoicon.bonjob.servicesConnection.WebServiceCall;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.AppConstants;
import com.infoicon.bonjob.utils.CommonUtils;
import com.infoicon.bonjob.utils.GPSLocation;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.TabBarChangeViewRecruiter;
import com.infoicon.bonjob.utils.UtilsMethods;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.soundcloud.android.crop.Crop;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

public class CandidateProfileActivity extends AppCompatActivity implements FragmentChanger {
    private String TAG = this.getClass().getSimpleName();

    @BindView(R.id.tvTitle) CustomsTextViewBold tvTitle;
    @BindView(R.id.btnSelectCandidate) CustomsButton btnSelectCandidate;
    @BindView(R.id.linearChatHireOption) LinearLayout linearChatHireOption;

    @BindView(R.id.recyclerViewlanguage) RecyclerView recyclerViewlanguage;
    @BindView(R.id.tvLudovic) CustomsTextViewBold tvLudovic;

    @BindView(R.id.textViewAbout) CustomsTextView textViewAbout;
    @BindView(R.id.textViewLocation) CustomsTextView textViewLocation;
    @BindView(R.id.rlRetry) RelativeLayout rlRetry;
    @BindView(R.id.tvErrorMessage) CustomsTextViewBold tvErrorMessage;

    @BindView(R.id.textViewFormation) CustomsTextView textViewFormation;
    @BindView(R.id.textViewActualStatus) CustomsTextView textViewActualStatus;
    @BindView(R.id.textViewMobility) CustomsTextView textViewMobility;
    @BindView(R.id.imageViewPhoto) CircularImageView imageViewPhoto;
    @BindView(R.id.imageViewVideo) CircularImageView imageViewVideo;
    @BindView(R.id.recyclerViewExperience) RecyclerView recyclerViewExperience;
    @BindView(R.id.recyclerViewGallery) RecyclerView recyclerViewGallery;
    @BindView(R.id.linearLayoutMain) LinearLayout linearLayoutMain;
    @BindView(R.id.rlUserPhoto) RelativeLayout rlUserPhoto;
    @BindView(R.id.rlUserVideo) RelativeLayout rlUserVideo;
    @BindView(R.id.btnChat) CustomsButton btnChat;
    @BindView(R.id.btnHire) CustomsButton btnHire;
    @BindView(R.id.linearGallery) LinearLayout linearGallery;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.imageViewPlayIcon) ImageView imageViewPlayIcon;
    @BindView(R.id.textViewActualSkills) LinearLayout textViewActualSkills;
    @BindView(R.id.linearTitleLocation) LinearLayout linearTitleLocation;
    @BindView(R.id.linearTitleExperience) LinearLayout linearTitleExperience;
    @BindView(R.id.linearTitleFormation) LinearLayout linearTitleFormation;
    @BindView(R.id.linearTitleLanguage) LinearLayout linearTitleLanguage;
    @BindView(R.id.linearTitleAbout) LinearLayout linearTitleAbout;
    @BindView(R.id.linearTitleActualStatus) LinearLayout linearTitleActualStatus;
    @BindView(R.id.linearTitleMobility) LinearLayout linearTitleMobility;
    @BindView(R.id.textViewSkills) CustomsTextView textViewSkills;
    private double currentLatitude, currentLongitude;

    private String candidateId;
    private String applied_id;
    private int position;
    private String from;
    private String firstName;
    private String lastName;
    private String job_title;
    private String user_pic = "";
    private String userPatchVideo = "";
    private String filterLocation = "";
    private MarshmallowPermission marshmallowPermission;

    /** get current location to search candidate */
    private void getCurrentLocation() {
        marshmallowPermission = new MarshmallowPermission(this);

        if (UtilsMethods.isLocationEnabled(this)) {
            String[] permissions = {PermissionKeys.PERMISSION_ACCESS_FINE_LOCATION, PermissionKeys.PERMISSION_ACCESS_COARSE_LOCATION};
            if (marshmallowPermission.isPermissionGrantedAll(PermissionKeys.REQUEST_CODE_PERMISSION_ALL, permissions)) {
                GPSLocation gpsLocation = new GPSLocation(this);
                currentLatitude = gpsLocation.getLatitude();
                currentLongitude = gpsLocation.getLongitude();
                filterLocation = gpsLocation.getAddress(currentLatitude, currentLongitude);

                if (!filterLocation.equals("") && currentLatitude != 0) {
                    Singleton.getCurrentLocationBean().setCurrentAddress(filterLocation);
                    Singleton.getCurrentLocationBean().setLatitude(currentLatitude);
                    Singleton.getCurrentLocationBean().setLongitude(currentLongitude);
                } else {
                    filterLocation = Singleton.getCurrentLocationBean().getCurrentAddress();
                    currentLatitude = Singleton.getCurrentLocationBean().getLatitude();
                    currentLongitude = Singleton.getCurrentLocationBean().getLongitude();
                }



            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_profile);
        ButterKnife.bind(this);
        getCurrentLocation();
        serviceForGetJobTitle();
        initView();
        getIntents();
        callWebServiceForMyProfile();
    }

    /** get intent data for show */
    private void getIntents() {
        candidateId = getIntent().getStringExtra(Keys.CANDIDATE_ID);
        applied_id = getIntent().getStringExtra(Keys.APPLIED_ID);
        job_title = getIntent().getStringExtra(Keys.JOB_TITLE);
        from = getIntent().getStringExtra(Keys.FROM);

        switch (from) {
            case Keys.FROM_APPLIED_CANDIDATE:
                position = getIntent().getIntExtra(Keys.POSITION, 0);
                btnSelectCandidate.setVisibility(View.VISIBLE);
                linearChatHireOption.setVisibility(View.GONE);
                break;
            case Keys.FROM_SEARCH_CANDIDATE:
                position = getIntent().getIntExtra(Keys.POSITION, 0);
                btnSelectCandidate.setVisibility(View.VISIBLE);
                linearChatHireOption.setVisibility(View.GONE);
                break;
            case Keys.SHOW_CHAT_HIRE_OPTION:
                if (applied_id.equals("")) {
                    linearChatHireOption.setVisibility(View.GONE);
                } else {
                    linearChatHireOption.setVisibility(View.VISIBLE);
                }
                btnSelectCandidate.setVisibility(View.GONE);
                break;
            case Keys.FROM_HIRED_CANDIDATE:
                btnSelectCandidate.setVisibility(View.GONE);
                linearChatHireOption.setVisibility(View.VISIBLE);
                btnChat.setVisibility(View.VISIBLE);
                btnHire.setVisibility(View.GONE);
                break;
            case Keys.FROM_CHAT:
                btnSelectCandidate.setVisibility(View.GONE);
                linearChatHireOption.setVisibility(View.VISIBLE);
                btnChat.setVisibility(View.VISIBLE);
                btnHire.setVisibility(View.GONE);
                break;
        }

       /* if (from.equals(Keys.FROM_APPLIED_CANDIDATE)) {
            position = getIntent().getIntExtra(Keys.POSITION, 0);
            btnSelectCandidate.setVisibility(View.VISIBLE);
            linearChatHireOption.setVisibility(View.GONE);
        } else if (from.equals(Keys.FROM_SEARCH_CANDIDATE)) {
            position = getIntent().getIntExtra(Keys.POSITION, 0);
            btnSelectCandidate.setVisibility(View.VISIBLE);
            linearChatHireOption.setVisibility(View.GONE);
        } else if (from.equals(Keys.SHOW_CHAT_HIRE_OPTION)) {
            if (applied_id.equals("")) {
                linearChatHireOption.setVisibility(View.GONE);
            } else {
                linearChatHireOption.setVisibility(View.VISIBLE);
            }
            btnSelectCandidate.setVisibility(View.GONE);

        } else if (from.equals(Keys.FROM_HIRED_CANDIDATE)) {
            btnSelectCandidate.setVisibility(View.GONE);
            linearChatHireOption.setVisibility(View.VISIBLE);
            btnChat.setVisibility(View.VISIBLE);
            btnHire.setVisibility(View.GONE);
        } else if (from.equals(Keys.FROM_CHAT)) {
            btnSelectCandidate.setVisibility(View.GONE);
            linearChatHireOption.setVisibility(View.VISIBLE);
            btnChat.setVisibility(View.VISIBLE);
            btnHire.setVisibility(View.GONE);
        }*/
    }

    /** check the current fragment is visible */
    private boolean isItCurrentFragment(String tag) {
        Fragment hm = getSupportFragmentManager().findFragmentByTag(tag);
        if (hm != null && hm.isVisible()) {
            FragmentManager fm = getSupportFragmentManager();
            int count = fm.getBackStackEntryCount();
            Logger.e("isItCurrentFragment count : " + count);
            String tagTop = "";
            if (count > 0) {
                FragmentManager.BackStackEntry backEntry = fm.getBackStackEntryAt(count - 1);
                tagTop = backEntry.getName();
            }
            if (tagTop.equals(tag)) {
                return true;
            } else return false;
        } else {
            return false;
        }
    }

    /** get index of the fragment by tag */
    private int getIndex(String tagname) {
        FragmentManager manager = getSupportFragmentManager();
        for (int i = 0; i < manager.getBackStackEntryCount(); i++) {
            if (manager.getBackStackEntryAt(i).getName().equalsIgnoreCase(tagname)) {
                return i;
            }
        }
        return -1;
    }
    @Override
    public void addInnerFragment(Fragment fragment, Bundle bundle, String tag, boolean isRemoveBackStack, boolean isAddToBackStack)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        if (isRemoveBackStack) {
            for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                fm.popBackStack();
            }
        }

        fragment.setArguments(bundle);
        //  fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, 0, 0, R.anim.slide_out_left);
        if (isAddToBackStack) {
            fragmentTransaction.add(R.id.frame_container, fragment, tag).addToBackStack(null).commit();
        } else {
            fragmentTransaction.add(R.id.frame_container, fragment, tag).commit();
        }
    }

    /**
     * remove fragment from backStack
     */
    @Override
    public void removeFragment()
    {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();
    }

    @Override
    public void addFragment(Fragment fragment, Bundle bundle, String tag,
                            boolean isRemoveBackStack,
                            boolean isAddToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        if (isRemoveBackStack) {
            //fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                fm.popBackStack();
            }
        }
        fragment.setArguments(bundle);

        if (getIndex(tag) >= 0) {
            if (isAddToBackStack) {
                Logger.e(TAG + " Replace  InBackStack");
                fm.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentTransaction.replace(R.id.frame_container, fragment, tag).addToBackStack(tag).commit();
                // fragmentTransaction.replace(R.id.frame_container, fragment, tag).commit();
            } else {
                Logger.e(TAG + " Replace  InBackStack Not");
                fragmentTransaction.replace(R.id.frame_container, fragment, tag).commit();
            }
        } else {
            if (isAddToBackStack) {
                Logger.e(TAG + " Add  InBackStack");
                fragmentTransaction.add(R.id.frame_container, fragment, tag).addToBackStack(tag).commit();
                // fragmentTransaction.add(R.id.frame_container, fragment, tag).commit();
            } else {
                Logger.e(TAG + " Add  InBackStack Not");
                fragmentTransaction.add(R.id.frame_container, fragment, tag).commit();
            }
        }
    }
    /** go back */
    @OnClick(R.id.textViewBack)
    void goBack() {
        finish();
    }

    /** event for redirect to selected candidate. */
    @OnClick(R.id.btnSelectCandidate)
    void selectCandidate() {

        if (from.equals(Keys.FROM_APPLIED_CANDIDATE))
        {
            if (UtilsMethods.isValidForSlectCandidate(CandidateProfileActivity.this) )
            {

                serviceSelectCandidate();
            }

        } else {
            if (UtilsMethods.isValidForSlectCandidate(CandidateProfileActivity.this) ) {

                serviceSelectCandidate();


                if (!isItCurrentFragment(Keys.POST_JOB)) {
                    if (Singleton.getUserInfoInstance().getEnterprise_name().equalsIgnoreCase("")) {
                        UtilsMethods.postCallback(this, new UtilsMethods.Callback() {
                            @Override
                            public void onYes() {
                                if (!isItCurrentFragment(Keys.PROFILE)) {
                                    Intent intent = new Intent(CandidateProfileActivity.this, HomeRecruiterActivity.class);
                                    intent.putExtra(Keys.FROM_CANDIDATE, "FROM_CANDIDATE");
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.putExtra(Keys.FROM, "");
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });

                    } else {
                        serviceMyOffersList();
                    }
                }
            }
        }
    }

    /** event for set chat result */
    @OnClick(R.id.btnChat)
    void chat() {
        if (from.equals(Keys.FROM_HIRED_CANDIDATE) || from.equals(Keys.SHOW_CHAT_HIRE_OPTION)) {

            if (!Singleton.getDbHelper().isTableExists(Singleton.getUserInfoInstance().getChatId() + Keys.BONJOB_ + "" + candidateId)) {
                Singleton.getDbHelper().createTableUser(Singleton.getUserInfoInstance().getChatId(), Keys.BONJOB_ + "" + candidateId);
            }

            Intent intent = new Intent();
            intent.putExtra(Keys.FOR, Keys.CHAT);
            intent.putExtra(Keys.CANDIDATE_ID, candidateId);
            intent.putExtra(Keys.FIRST_NAME, firstName);
            intent.putExtra(Keys.LAST_NAME, lastName);
            intent.putExtra(Keys.JOB_TITLE, job_title);
            intent.putExtra(Keys.USER_PIC, user_pic);
            intent.putExtra(Keys.JOB_IMAGE, getIntent().getStringExtra(Keys.JOB_IMAGE));
            intent.putExtra(Keys.DESCRIPTION, getIntent().getStringExtra(Keys.DESCRIPTION));
            setResult(RESULT_OK, intent);
            finish();
        } else if (from.equals(Keys.FROM_CHAT)) {
            finish();
        }
    }

    /** event for set hire result */
    @OnClick(R.id.btnHire)
    void hire() {
        serviceHireCandidate();
    }

    /** retry to get profile */
    @OnClick(R.id.btnRetry)
    void retry() {
        callWebServiceForMyProfile();
    }

    /** event to view video */
    @OnClick(R.id.imageViewVideo)
    void viewVideo() {
        dialogViewVideo();
    }

    /** init view */
    private void initView()
    {
        recyclerViewlanguage.setNestedScrollingEnabled(false);
        recyclerViewExperience.setNestedScrollingEnabled(false);
        recyclerViewGallery.setNestedScrollingEnabled(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewlanguage.setHasFixedSize(false);
        recyclerViewlanguage.setLayoutManager(linearLayoutManager);

        LinearLayoutManager linearLayoutManagerExp = new LinearLayoutManager(this);
        recyclerViewExperience.setHasFixedSize(false);
        recyclerViewExperience.setLayoutManager(linearLayoutManagerExp);

        LinearLayoutManager linearLayoutManagerGallery = new LinearLayoutManager(this);
        recyclerViewGallery.setHasFixedSize(false);
        recyclerViewGallery.setLayoutManager(linearLayoutManagerGallery);
    }

    /** event to show image on dialog */
    @OnClick(R.id.imageViewPhoto)
    void viewPhoto() {
        dialogViewPhoto();
    }

    /** call service for getting seeker profile */
    private void callWebServiceForMyProfile() {
        final SpotsDialog spotsDialog = new SpotsDialog(this, R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty(Keys.USER_ID, candidateId);
        retrofit.Call<GetSeekerProfileResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getMyProfileResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetSeekerProfileResponse>()
        {
            @Override
            public void onResponse(retrofit.Response<GetSeekerProfileResponse> response,
                                   retrofit.Retrofit retrofit)
            {
                spotsDialog.dismiss();
                GetSeekerProfileResponse getSeekerProfileResponse = response.body();
                if (getSeekerProfileResponse.isSuccess())
                {
                    rlRetry.setVisibility(View.GONE);
                    linearLayoutMain.setVisibility(View.VISIBLE);
                    user_pic = getSeekerProfileResponse.getData().getUser_pic();
                    // load photo
                    if (user_pic != null && !user_pic.equals("")) {
                        ImageLoader.loadImage(CandidateProfileActivity.this, getSeekerProfileResponse.getData().getUser_pic(), imageViewPhoto);
                    }
                    else
                        {
                        rlUserPhoto.setVisibility(View.GONE);
                    }
                    //load thumbnail and patch video from url
                    if (!getSeekerProfileResponse.getData().getPatch_video_thumbnail().equals("") &&
                            !getSeekerProfileResponse.getData().getPatch_video().equals(""))
                    {
                        userPatchVideo = getSeekerProfileResponse.getData().getPatch_video();
                        ImageLoader.loadImage(CandidateProfileActivity.this, getSeekerProfileResponse.getData().getPatch_video_thumbnail(), imageViewVideo);
                    }
                    else
                        {
                        rlUserVideo.setVisibility(View.GONE);
                    }
                    firstName = getSeekerProfileResponse.getData().getFirst_name();
                    lastName = getSeekerProfileResponse.getData().getLast_name();
                    if (from.equals(Keys.FROM_SEARCH_CANDIDATE))
                    {
                        tvLudovic.setVisibility(View.GONE);
                        tvTitle.setText(firstName + " " + lastName);
                    }
                    else
                        {
                        tvLudovic.setVisibility(View.VISIBLE);
                        tvLudovic.setText(firstName + " " + lastName);
                        tvTitle.setText(job_title);
                    }
                    if (getSeekerProfileResponse.getData().getCity() == null
                            || getSeekerProfileResponse.getData().getCity().
                            equalsIgnoreCase(""))
                    {
                        textViewLocation.setVisibility(View.GONE);
                        linearTitleLocation.setVisibility(View.GONE);
                    }
                    else
                        {
                        // textViewLocation.setText(getSeekerProfileResponse.getData().getCity());
                        if (!String.valueOf(currentLatitude).equals("") &&
                                !String.valueOf(currentLongitude).equals(""))
                        {
                            if (String.valueOf(currentLongitude) != null &&
                                    getSeekerProfileResponse.getData().getLattitude() != null &&
                                    !getSeekerProfileResponse.getData().getLattitude().equals("") &&
                                    !getSeekerProfileResponse.getData().getLongitude().equals("0.000000")&&
                            getSeekerProfileResponse.getData().getLongitude().equals(""))
                            {
                                String distance = UtilsMethods.calculateDistanceInKm(String.valueOf(currentLatitude),
                                        String.valueOf(currentLongitude),
                                        String.valueOf(getSeekerProfileResponse.getData().getLattitude()),
                                        String.valueOf(getSeekerProfileResponse.getData().getLongitude()));
                                textViewLocation.setText(distance + "KM - " + getSeekerProfileResponse.getData().getCity());
                            } else
                                textViewLocation.setText(getSeekerProfileResponse.getData().getCity());
                        } else
                            textViewLocation.setText(getSeekerProfileResponse.getData().getCity());
                    }


                    if (getSeekerProfileResponse.getData().getAbout() == null
                            || getSeekerProfileResponse.getData().getAbout().equalsIgnoreCase("")) {
                        textViewAbout.setVisibility(View.GONE);
                        linearTitleAbout.setVisibility(View.GONE);
                    } else {
                        textViewAbout.setText(getSeekerProfileResponse.getData().getAbout());
                    }

                    if (getSeekerProfileResponse.getData().getCurrent_status_name() == null
                            || getSeekerProfileResponse.getData().getCurrent_status_name ().equalsIgnoreCase("")) {
                        textViewActualStatus.setVisibility(View.GONE);
                        linearTitleActualStatus.setVisibility(View.GONE);
                    } else {
                        textViewActualStatus.setText(getSeekerProfileResponse.getData().
                                getCurrent_status_name());
                    }

                    if (getSeekerProfileResponse.getData().getSkills_name() == null
                            || getSeekerProfileResponse.getData().getSkills_name().equalsIgnoreCase("")) {
                        textViewSkills.setVisibility(View.GONE);
                        textViewActualSkills.setVisibility(View.GONE);
                    }else{
                        textViewSkills.setVisibility(View.VISIBLE);
                        textViewSkills.setText(getSeekerProfileResponse.getData().getSkills_name());
                    }
                    if (getSeekerProfileResponse.getData().getMobility_name() == null
                            || getSeekerProfileResponse.getData().getMobility_name().
                            equalsIgnoreCase("")) {
                        textViewMobility.setVisibility(View.GONE);
                        linearTitleMobility.setVisibility(View.GONE);
                    } else {
                        textViewMobility.setText(getSeekerProfileResponse.getData().getMobility_name());
                    }

                    if (getSeekerProfileResponse.getData().getExperience() != null
                            && getSeekerProfileResponse.getData().getExperience().size() > 0) {
                        ProfessionalExperienceAdapter professionalExperienceAdapter = new ProfessionalExperienceAdapter(CandidateProfileActivity.this, getSeekerProfileResponse.getData().getExperience());
                        recyclerViewExperience.setAdapter(professionalExperienceAdapter);
                    } else {
                        linearTitleExperience.setVisibility(View.GONE);
                        recyclerViewExperience.setVisibility(View.GONE);
                    }

                    if ((getSeekerProfileResponse.getData().getTraining() == null
                            || getSeekerProfileResponse.getData().getTraining()
                            .equalsIgnoreCase(""))
                            && (getSeekerProfileResponse.getData().
                            getEducation_level_name() == null
                            || getSeekerProfileResponse.getData().
                            getEducation_level_name().equalsIgnoreCase("")))
                    {
                        textViewFormation.setVisibility(View.GONE);
                        linearTitleFormation.setVisibility(View.GONE);
                    }
                    else
                        {
                        textViewFormation.setVisibility(View.VISIBLE);
                        linearTitleFormation.setVisibility(View.VISIBLE);
                        if (!getSeekerProfileResponse.getData().getTraining().
                                equalsIgnoreCase("")
                                && !getSeekerProfileResponse.getData().
                                getEducation_level().equalsIgnoreCase("")) {
                            textViewFormation.setText
                                    (getSeekerProfileResponse.getData
                                    ().getEducation_level_name() + " - " + getSeekerProfileResponse.
                                    getData().getTraining());
                        }
                        else if (!getSeekerProfileResponse.getData().getTraining().
                                equalsIgnoreCase(""))
                        {
                            textViewFormation.setText(getSeekerProfileResponse.getData().
                                    getTraining());
                        }
                        else if (!getSeekerProfileResponse.getData().getEducation_level_name().
                                equalsIgnoreCase(""))
                        {
                            textViewFormation.setText(getSeekerProfileResponse.getData().
                                    getEducation_level_name());
                        }
                        //   textViewFormation.setText(getSeekerProfileResponse.getData().getTraining());
                    }


                    if (getSeekerProfileResponse.getData().getLanguages() != null
                            && getSeekerProfileResponse.getData().getLanguages().size() > 0)
                    {
                        LanguageListAdapter languageListAdapter = new LanguageListAdapter(CandidateProfileActivity.this, getSeekerProfileResponse.getData().getLanguages());
                        recyclerViewlanguage.setAdapter(languageListAdapter);
                    } else {
                        linearTitleLanguage.setVisibility(View.GONE);
                        recyclerViewlanguage.setVisibility(View.GONE);
                    }

                    if (getSeekerProfileResponse.getData().getGallery() != null
                            && getSeekerProfileResponse.getData().getGallery().size() > 0) {
                        ProfileVisitorAdapter profileVisitorAdapter = new ProfileVisitorAdapter(CandidateProfileActivity.this, getSeekerProfileResponse.getData().getGallery());
                        recyclerViewGallery.setAdapter(profileVisitorAdapter);
                    } else linearGallery.setVisibility(View.GONE);

                } else {
                    rlRetry.setVisibility(View.VISIBLE);
                    linearLayoutMain.setVisibility(View.GONE);
                    //CommonUtils.showSimpleMessageBottom(CandidateProfileActivity.this, getSeekerProfileResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                rlRetry.setVisibility(View.VISIBLE);
                linearLayoutMain.setVisibility(View.GONE);
                CommonUtils.showSimpleMessageBottom(CandidateProfileActivity.this, t.getMessage());
            }
        });
    }

    /** call service for select candidate */
    private void serviceSelectCandidate()
    {
        final SpotsDialog spotsDialog = new SpotsDialog(this, R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.APPLIED_ID, applied_id);
        retrofit.Call<GetSelectCandidateResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getSelectCandidateResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetSelectCandidateResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetSelectCandidateResponse> response, retrofit.Retrofit retrofit) {
                if (spotsDialog.isShowing())
                    spotsDialog.dismiss();
                GetSelectCandidateResponse getSelectCandidateResponse = response.body();
                if (getSelectCandidateResponse.isSuccess()) {
                    int selectedCount =Integer.parseInt(Singleton.getSubscriptionListAndMyPlan().
                            getCurrentPlan().getSelect_candidate_count());
                    int totalCount = selectedCount + 1;
                    Singleton.getSubscriptionListAndMyPlan().getCurrentPlan().setSelect_candidate_count(""+totalCount);
                    openAlert(getSelectCandidateResponse.getMsg(), Keys.SELECTED_CANDIDATE);
                } else {
                    CommonUtils.showSimpleMessageBottom(CandidateProfileActivity.this, getSelectCandidateResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(CandidateProfileActivity.this, t.getMessage());
            }
        });
    }

    /** call service for hire candidate */
    private void serviceHireCandidate() {
        final SpotsDialog spotsDialog = new SpotsDialog(this, R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.APPLIED_ID, applied_id);
        retrofit.Call<GetHireResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getHireResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetHireResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetHireResponse> response, retrofit.Retrofit retrofit) {
                if (spotsDialog.isShowing())
                    spotsDialog.dismiss();
                GetHireResponse getHireResponse = response.body();
                if (getHireResponse.isSuccess()) {
                    //add bubble to Mes offer
                    int count = Singleton.getUserInfoInstance().getRecruiterOfferCount();
                    Singleton.getUserInfoInstance().setRecruiterOfferCount(count + 1);
                    sendBroadcast(new Intent(Keys.MY_OFFER_COUNT));
                    openAlert(getHireResponse.getMsg(), Keys.HIRE);
                } else {
                    CommonUtils.showSimpleMessageBottom(CandidateProfileActivity.this, getHireResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(CandidateProfileActivity.this, t.getMessage());
            }
        });
    }

    /** call service for my offers list */
    private void serviceMyOffersList() {
        final SpotsDialog spotsDialog = new SpotsDialog(this, R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.USER_ID, Singleton.getUserInfoInstance().getUser_id());
        retrofit.Call<GetMyOffersRecruiterResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getMyOffersResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetMyOffersRecruiterResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetMyOffersRecruiterResponse> response, retrofit.Retrofit retrofit) {
                if (spotsDialog.isShowing())
                    spotsDialog.dismiss();
                GetMyOffersRecruiterResponse getMyOffersRecruiterResponse = response.body();
                //my offers
                if (getMyOffersRecruiterResponse.isSuccess()) {
                    List<GetMyOffersRecruiterResponse.ActiveJobsBean> activeJobsBeanList = getMyOffersRecruiterResponse.getActiveJobs();
                    if (activeJobsBeanList != null && activeJobsBeanList.size() > 0) {
                        dialogMyOffers(activeJobsBeanList);
                    } else {
                        if (UtilsMethods.isValidForPostJob(CandidateProfileActivity.this)) {

                            publishJobOfferDialog();
                        }
                    }
                } else {
                    if (getMyOffersRecruiterResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(CandidateProfileActivity.this, getMyOffersRecruiterResponse.getMsg());
                    } else {
                        if (getMyOffersRecruiterResponse.getActiveJobs().size() <= 0) {
                            //UtilsMethods.openCustumAlert(CandidateProfileActivity.this, getResources().getString(R.string.post_job_offer_message));
                            if (UtilsMethods.isValidForPostJob(CandidateProfileActivity.this)) {
                                publishJobOfferDialog();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(CandidateProfileActivity.this, t.getMessage());
            }
        });
    }
    /** set total job post count */
    private void setJobPostCount() {
        //set total job post count
        int postCount = Singleton.getSubscriptionListAndMyPlan().getCurrentPlan().getJob_post_count();
        int totalCount = postCount + 1;
        Singleton.getSubscriptionListAndMyPlan().getCurrentPlan().setJob_post_count(totalCount);
    }
    /**
     * call service for hire candidate
     * @param job_id
     */
    private void serviceSelectCandidateBySearch(String job_id) {
        final SpotsDialog spotsDialog = new SpotsDialog(this, R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.USER_ID, candidateId);
        jsonObject.addProperty(Keys.JOB_ID, job_id);
        retrofit.Call<GetSelectCandidateBySearchResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getSelectCandidateBySearchResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetSelectCandidateBySearchResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetSelectCandidateBySearchResponse> response, retrofit.Retrofit retrofit) {
                if (spotsDialog.isShowing())
                    spotsDialog.dismiss();
                GetSelectCandidateBySearchResponse selectCandidateBySearchResponse = response.body();
                if (selectCandidateBySearchResponse.isSuccess()) {
                    // ad bubble on Mes Offer
                    int selectedCount =Integer.parseInt(Singleton.getSubscriptionListAndMyPlan().
                            getCurrentPlan().getSelect_candidate_count());
                    int totalCount = selectedCount + 1;
                    Singleton.getSubscriptionListAndMyPlan().getCurrentPlan().setSelect_candidate_count(""+totalCount);
                    int count = Singleton.getUserInfoInstance().getRecruiterOfferCount();
                    Singleton.getUserInfoInstance().setRecruiterOfferCount(count + 1);
                    sendBroadcast(new Intent(Keys.MY_OFFER_COUNT));

                    openAlert(selectCandidateBySearchResponse.getMsg(), "");
                } else {
                    if (selectCandidateBySearchResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(CandidateProfileActivity.this, selectCandidateBySearchResponse.getMsg());
                    } else {
                        CommonUtils.showSimpleMessageBottom(CandidateProfileActivity.this, selectCandidateBySearchResponse.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(CandidateProfileActivity.this, t.getMessage());
            }
        });
    }

    /**
     * show message on alert
     * @param message message to show on dialog
     * @param type    to identify the candidate is selected or hired
     */
    public void openAlert(String message, String type) {
        final Dialog dialog = new Dialog(this);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_alert_ok);
        dialog.setCancelable(false);
        CustomsTextView textViewTitle = (CustomsTextView) dialog.findViewById(R.id.textViewTitle);
        CustomsTextView textViewMessage = (CustomsTextView) dialog.findViewById(R.id.textViewMessage);
        CustomsButton buttonOk = (CustomsButton) dialog.findViewById(R.id.buttonOk);
        textViewTitle.setText(getResources().getString(R.string.app_name));
        textViewMessage.setText(message);
        buttonOk.setOnClickListener(view -> {
            dialog.dismiss();
            if (type.equals(Keys.SELECTED_CANDIDATE)) {
                Intent intent = new Intent();
                intent.putExtra(Keys.POSITION, position);
                setResult(RESULT_OK, intent);
                finish();
            } else if (type.equals(Keys.HIRE)) {
                Intent intent = new Intent();
                intent.putExtra(Keys.FOR, Keys.HIRE);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                finish();
            }
        });
        dialog.show();
    }

    /**
     * method for open dialog to show job list
     * @param activeJobsBeanList
     */
    public void dialogMyOffers(List<GetMyOffersRecruiterResponse.ActiveJobsBean> activeJobsBeanList) {
        final Dialog dialogVideoInfo = new Dialog(this);
        dialogVideoInfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialogVideoInfo.getWindow() != null)
            dialogVideoInfo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogVideoInfo.setContentView(R.layout.dialog_active_job_list);
        ImageView imageViewClose = dialogVideoInfo.findViewById(R.id.imageViewClose);
        Spinner spinner = dialogVideoInfo.findViewById(R.id.spinner1);
        CustomsButton buttonvalidate = dialogVideoInfo.findViewById(R.id.buttonvalidat);
        ImageView drop_down = dialogVideoInfo.findViewById(R.id.drop_down);
drop_down.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        spinner.performClick();
    }
});
        buttonvalidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogVideoInfo.dismiss();
                serviceSelectCandidateBySearch(activeJobsBeanList.get(spinner.getSelectedItemPosition()).getJob_id());
            }
        });
        spinner.setEnabled(false);
       // String[]
         //array_list_title = getResources().getStringArray(R.array.array_job_offered);

        MyOffersAdapter myOffersAdapter = new MyOffersAdapter(CandidateProfileActivity.this, activeJobsBeanList);
        spinner.setAdapter(myOffersAdapter);
      /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialogVideoInfo.dismiss();
                serviceSelectCandidateBySearch(activeJobsBeanList.get(position).getJob_id());
            }
        });*/
       /* spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                dialogVideoInfo.dismiss();
                serviceSelectCandidateBySearch(activeJobsBeanList.get(pos).getJob_id());
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        imageViewClose.setOnClickListener(v -> dialogVideoInfo.dismiss());
        dialogVideoInfo.show();
    }

    /** method for open dialog to view photo */
    public void dialogViewPhoto() {
        final Dialog dialogVideoInfo = new Dialog(this);
        dialogVideoInfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialogVideoInfo.getWindow() != null)
            dialogVideoInfo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogVideoInfo.setContentView(R.layout.dialog_photo_view_square);
        ImageView imageViewClose = dialogVideoInfo.findViewById(R.id.imageViewClose);
        ImageView imageViewPhoto = dialogVideoInfo.findViewById(R.id.imageViewPhoto);
        showProgress(true);
        ImageLoader.loadImageRoundCallBack(this, user_pic, imageViewPhoto, new ImageLoader.CallBack() {
            @Override
            public void onSuccess() {
                if (!isFinishing()) {
                    //show dialog
                    showProgress(false);
                    new Handler().postDelayed(dialogVideoInfo::show, 200);
                }

            }

            @Override
            public void onError() {
                if (!isFinishing()) {
                    showProgress(false);
                }
            }
        });
        imageViewClose.setOnClickListener(v -> {
            showProgress(false);
            dialogVideoInfo.dismiss();
        });
        dialogVideoInfo.show();
    }

    /** dialog to play video */
    private void dialogViewVideo() {
        final Dialog dialogVideoInfo = new Dialog(this);
        dialogVideoInfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialogVideoInfo.getWindow() != null)
            dialogVideoInfo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogVideoInfo.setContentView(R.layout.dialog_video_view);
        ImageView imageViewClose = dialogVideoInfo.findViewById(R.id.imageViewClose);
        VideoView videoView = dialogVideoInfo.findViewById(R.id.videoView);
        ProgressBar progressBar = dialogVideoInfo.findViewById(R.id.progressBar);
        RelativeLayout rlController = dialogVideoInfo.findViewById(R.id.rlController);
        ImageView imageViewPlay = dialogVideoInfo.findViewById(R.id.imageViewPlay);
        ImageView imageViewPause = dialogVideoInfo.findViewById(R.id.imageViewPause);
        ImageView imageViewFull = dialogVideoInfo.findViewById(R.id.imageViewFull);
        imageViewClose.setOnClickListener(v -> dialogVideoInfo.dismiss());

        MediaController mediacontroller = new MediaController(this);
        mediacontroller.setAnchorView(videoView);

        Logger.e(TAG + " video Url : " + userPatchVideo);
        videoView.setVideoPath(userPatchVideo);
        UtilsMethods.disableSSLCertificateChecking();
        //start video
        hidePlayPauseController(0, imageViewPlay, imageViewPause);
        hideShowView(0, rlController);
        videoView.start();
        progressBar.setVisibility(View.VISIBLE);

        /** event for play video */
        imageViewPlay.setOnClickListener(v -> {
            if (!videoView.isPlaying()) {
                hidePlayPauseController(0, imageViewPlay, imageViewPause);
                hideShowView(0, rlController);
                videoView.start();
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        /** event for pause video */
        imageViewPause.setOnClickListener(v -> {
            if (videoView.isPlaying()) {
                hideShowView(1, rlController);
                hidePlayPauseController(1, imageViewPlay, imageViewPause);
                progressBar.setVisibility(View.GONE);
                videoView.pause();
            }
        });
        /** event for full screen view  image*/
        imageViewFull.setOnClickListener(v -> {
            Intent intent = new Intent(CandidateProfileActivity.this, PlayVideoFullSreenActivity.class);
            intent.putExtra(Keys.PATCH_VIDEO, userPatchVideo);
            startActivity(intent);
        });

        /** media complete listener */
        videoView.setOnCompletionListener(mp -> {
            Logger.e(TAG + "  onCompletion ");
            progressBar.setVisibility(View.GONE);
            hideShowView(1, rlController);
            hidePlayPauseController(1, imageViewPlay, imageViewPause);
        });
        /** error listener while playing video */
        videoView.setOnErrorListener((mp, what, extra) -> {
            Logger.e(TAG + "  onError ");
            progressBar.setVisibility(View.GONE);
            hidePlayPauseController(1, imageViewPlay, imageViewPause);
            return true;
        });
        /** prepare listener  */
        videoView.setOnPreparedListener(mp -> {
            Logger.e(TAG + " onPrepared ");
            progressBar.setVisibility(View.GONE);
            mp.setOnBufferingUpdateListener((mp1, percent) -> {
                Logger.e(TAG + " onBufferingUpdate : " + percent);
                if (percent == 100) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        });
        /** hide/show custom controller on touch VideoView */
        videoView.setOnTouchListener((View v, MotionEvent event) -> {
            Logger.e(TAG + "onTouch ");
            if (videoView.isPlaying() && rlController.getVisibility() == View.VISIBLE) {
                hideShowView(0, rlController);
            } else {
                hideShowView(1, rlController);
            }
            return false;
        });

        dialogVideoInfo.show();
    }

    /**
     * hide play pause controller
     * @param status 0-> hide play icon ,1->hide pause icon
     */
    void hidePlayPauseController(int status, ImageView imageViewPlay, ImageView imageViewPause) {
        if (status == 0) {
            imageViewPlay.setVisibility(View.GONE);
            imageViewPause.setVisibility(View.VISIBLE);
        } else {
            imageViewPlay.setVisibility(View.VISIBLE);
            imageViewPause.setVisibility(View.GONE);
        }
    }

    /** hide show controller on basis of status */
    private void hideShowView(int status, RelativeLayout rlController) {
        if (status == 0) {
            rlController.animate()
                    .alpha(0.0f)
                    .setDuration(1000)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
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
                            rlController.setVisibility(View.VISIBLE);
                        }
                    });
        }
    }

    /** show progressbar */
    private void showProgress(boolean status) {
        if (status) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    GetJobTitleResponce getCompanyDetailsResponse;

    private void serviceForGetJobTitle()
    {
        final SpotsDialog spotsDialog = new
                SpotsDialog(this, R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();

        retrofit.Call<GetJobTitleResponce> call = AppRetrofit.
                getAppRetrofitInstance().getApiServices().getGetJobTitleResponce
                (jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetJobTitleResponce>() {
            @Override
            public void onResponse(retrofit.Response<GetJobTitleResponce> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                getCompanyDetailsResponse = response.body();

                if (getCompanyDetailsResponse.isSuccess())
                {


                } else {
                    if (getCompanyDetailsResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(CandidateProfileActivity.this, getCompanyDetailsResponse.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();

            }
        });
    }
    private String[] array_list_title;
    /** publish job offer pop if no job offer. */
    private void publishJobOfferDialog() {
        final Dialog dialogVideoInfo = new Dialog(CandidateProfileActivity.this);
        dialogVideoInfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialogVideoInfo.getWindow() != null)
            dialogVideoInfo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogVideoInfo.setContentView(R.layout.dialog_no_posted_job);
        ImageView imageView = dialogVideoInfo.findViewById(R.id.imageViewClose);
        CustomsButton btnPostJob = dialogVideoInfo.findViewById(R.id.btnPostJob);
        imageView.setOnClickListener(v -> dialogVideoInfo.dismiss());
        Spinner spinner = dialogVideoInfo.findViewById(R.id.spinner1);
       // String[] array_list_title = getResources().getStringArray(R.array.array_job_offered);
        array_list_title=  new String[getCompanyDetailsResponse
                .getJobTitles()
                .size()];
        for(int i=0;i< getCompanyDetailsResponse.getJobTitles().size();i++)
        {
            array_list_title[i]=getCompanyDetailsResponse.getJobTitles().get(i)
                    .getJob_title();

            // String[] mTestArray = getResources().getStringArray(R.array.array_type_of_contract);
        }
        // array_list_title = getResources().getStringArray(R.array.array_job_offered);

        ImageView drop_down = dialogVideoInfo.findViewById(R.id.drop_down);

        drop_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.performClick();
            }
        });

        spinner.setEnabled(false);
        btnPostJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();*/

                callServiceForPostJob(getCompanyDetailsResponse.getJobTitles()
                        .get(spinner.getSelectedItemPosition()).getJob_title(),
                        getCompanyDetailsResponse.getJobTitles()
                        .get(spinner.getSelectedItemPosition()).getJob_title_id());
            }
        });



        newJobAdapter myOffersAdapter = new newJobAdapter(CandidateProfileActivity.this, array_list_title);
        spinner.setAdapter(myOffersAdapter);
      /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialogVideoInfo.dismiss();
                serviceSelectCandidateBySearch(activeJobsBeanList.get(position).getJob_id());
            }
        });*/
       /* spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                dialogVideoInfo.dismiss();
                serviceSelectCandidateBySearch(activeJobsBeanList.get(pos).getJob_id());
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
        dialogVideoInfo.show();
    }









    /** create json object and multipart in this method. */
    private void callServiceForPostJob(String job_title,String job_title_id) {
        JSONObject jsonObject = new JSONObject();
        try {


    if (!UtilsMethods.postalCode.equals("")) {
        filterLocation=  filterLocation+", "+UtilsMethods.postalCode;
    }
            jsonObject.put(Keys.USER_ID, Singleton.getUserInfoInstance().getUser_id());
            jsonObject.put(Keys.JOB_TITLE, job_title);
            jsonObject.put(Keys.JOB_TITLE_ID, job_title_id);

            jsonObject.put(Keys.JOB_LOCATION, filterLocation);
            jsonObject.put(Keys.CONTRACT_TYPE, "");
            jsonObject.put(Keys.EDUCATION_LEVEL,"");
            jsonObject.put(Keys.EXPERIENCE, "");
            jsonObject.put(Keys.POST_LANGUAGE, "");
            jsonObject.put(Keys.NUM_HOURS, "");
            jsonObject.put(Keys.SALALRY, "");
            jsonObject.put(Keys.START_DATE, "");
            jsonObject.put(Keys.JOB_DESC, "");
            jsonObject.put(Keys.CONTACT_MODE, getContactType());
            jsonObject.put(Keys.LATTITUDE, currentLatitude);
            jsonObject.put(Keys.LONGITUDE, currentLongitude);

            jsonObject.put(Keys.job_country, UtilsMethods.country_name);
            jsonObject.put(Keys.job_postal_code, UtilsMethods.postalCode);
            jsonObject.put(Keys.job_city, UtilsMethods.City_name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        postJobWithoutFile(jsonObject, ServiceUrls.POST_JOB);
        }

    /** service for post job without file */
    private void postJobWithoutFile(JSONObject jsonObject, String jobPostUrl) {
        Logger.e("REQUEST URL :" + jobPostUrl);
        WebServiceCall webServiceCall = new WebServiceCall(this, new INetworkResponse() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean(Keys.SUCCESS);
                    String message = jsonObject.getString(Keys.MESSAGE);
                    if (success)
                    {
                       setJobPostCount();
                        serviceMyOffersNewList();
                    }                                                                                                                                                                    else
                        {
                        if (jsonObject.getString(Keys.ACTIVE_USER).equals(Keys.AUTH_CODE))
                        {
                            UtilsMethods.unAuthorizeAlert(CandidateProfileActivity.this, message);
                        } else {
                            CommonUtils.showSimpleMessageBottom(CandidateProfileActivity.this, message);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error)
            {
                CommonUtils.showSimpleMessageBottom(CandidateProfileActivity.this, error);
            }
        });
        webServiceCall.execute(jsonObject, jobPostUrl);
    }

    /** create JsonArray for contact type */
    private JSONObject getContactType() {
        JSONObject jsonObjectContact = new JSONObject();


            try {
                jsonObjectContact.put(Keys.NAME, "BonJob Chat");
                jsonObjectContact.put(Keys.VALUE, "BonJob Chat");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        return jsonObjectContact;
    }


    /** getting result after fetching photo/video from camera/gallery */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Keys.POST_CONFIRM_CODE:
                if (resultCode == RESULT_OK) {
                    String loadPage = data.getStringExtra(Keys.VIEW_FRAGMENT);
                    if (loadPage.equals(Keys.PROFILE)) {//load profile
                        new Thread(() -> (CandidateProfileActivity.this).addFragment(new ProfileRecruiterFragment(), new Bundle(), Keys.PROFILE, false, true)).start();
                    } else {//load offer
                        Bundle bundle = new Bundle();
                        bundle.putInt(Keys.POSITION, 3);
                        new Thread(() -> (CandidateProfileActivity.this).addFragment(new OfferTabFragment(),
                                bundle, Keys.MY_OFFERS, false, true)).start();
                    }
                }
                break;

            default:
                break;
        }
    }
    /** call service for my offers list */
    private void serviceMyOffersNewList() {
        final SpotsDialog spotsDialog = new SpotsDialog(this, R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.USER_ID, Singleton.getUserInfoInstance().getUser_id());
        retrofit.Call<GetMyOffersRecruiterResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getMyOffersResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetMyOffersRecruiterResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetMyOffersRecruiterResponse> response, retrofit.Retrofit retrofit) {
                if (spotsDialog.isShowing())
                    spotsDialog.dismiss();
                GetMyOffersRecruiterResponse getMyOffersRecruiterResponse = response.body();
                //my offers
                if (getMyOffersRecruiterResponse.isSuccess()) {
                    List<GetMyOffersRecruiterResponse.ActiveJobsBean> activeJobsBeanList = getMyOffersRecruiterResponse.getActiveJobs();
                    if (activeJobsBeanList != null && activeJobsBeanList.size() > 0) {
                        serviceSelectCandidateBySearch(activeJobsBeanList.get(0).getJob_id());
                    }
                }

            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(CandidateProfileActivity.this, t.getMessage());
            }
        });
    }
}
