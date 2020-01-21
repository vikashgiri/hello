package com.infoicon.bonjob.seeker.profile;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomsButton;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.main.HomeSeekerActivity;
import com.infoicon.bonjob.picasso.ImageLoader;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.response.GetSeekerProfileResponse;
import com.infoicon.bonjob.seeker.adapters.LanguageListAdapter;
import com.infoicon.bonjob.seeker.adapters.ProfessionalExperienceAdapter;
import com.infoicon.bonjob.seeker.searchJob.PlayVideoFullSreenActivity;
import com.infoicon.bonjob.setting.SettingSeekerFragment;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;
import com.mikhaellopez.circularimageview.CircularImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

public class ProfileFragment extends Fragment
{


    private final String TAG = this.getClass().getSimpleName();
    private View rootView;
    @BindView(R.id.recyclerViewlanguage) RecyclerView recyclerViewlanguage;
    @BindView(R.id.tvLudovic) CustomsTextViewBold tvLudovic;
    @BindView(R.id.btnEditProfile) CustomsButton btnEditProfile;

    @BindView(R.id.tvTitle) CustomsTextViewBold tvTitle;
    @BindView(R.id.textViewAbout) CustomsTextView textViewAbout;
    @BindView(R.id.textViewjob_sought) CustomsTextView textView_job_sought;
    @BindView(R.id.linear_job_sought) LinearLayout linear_job_sought;
    @BindView(R.id.textViewLocation) CustomsTextView textViewLocation;

    @BindView(R.id.textViewFormation) CustomsTextView textViewFormation;
    @BindView(R.id.noCompanyAdded) CustomsTextView noCompanyAdded;
    @BindView(R.id.textViewActualStatus) CustomsTextView textViewActualStatus;
    @BindView(R.id.textViewMobility) CustomsTextView textViewMobility;
    @BindView(R.id.linearTitleMobility) LinearLayout linearTitleMobility;
    @BindView(R.id.linearTitleFormation) LinearLayout linearTitleFormation;
    @BindView(R.id.linearTitleLanguage) LinearLayout linearTitleLanguage;
    @BindView(R.id.linearTitleLocation) LinearLayout linearTitleLocation;
    @BindView(R.id.linearTitleActualStatus) LinearLayout linearTitleActualStatus;
    @BindView(R.id.linearTitleAbout) LinearLayout linearTitleAbout;
    @BindView(R.id.linearTitleExperience) LinearLayout linearTitleExperience;

    @BindView(R.id.textViewSkills) CustomsTextView textViewSkills;
    @BindView(R.id.linearViewSkills) LinearLayout linearViewSkills;
    @BindView(R.id.imageViewPhoto) CircularImageView imageViewPhoto;

    @BindView(R.id.imageViewVideo) CircularImageView imageViewVideo;
    @BindView(R.id.recyclerViewExperience) RecyclerView recyclerViewExperience;
    @BindView(R.id.linearLayoutMain) LinearLayout linearLayoutMain;
    @BindView(R.id.rlRetry) RelativeLayout rlRetry;
    @BindView(R.id.tvErrorMessage) CustomsTextViewBold tvErrorMessage;
    @BindView(R.id.relativeLayoutUserPic) public RelativeLayout relativeLayoutUserPic;
    @BindView(R.id.relativeLayoutPitchVideo) public RelativeLayout relativeLayoutPitchVideo;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    public GetSeekerProfileResponse getSeekerProfileResponse;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        callWebServiceForMyProfile(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, rootView);
        setRetainInstance(true);
        initView();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.e(TAG + " : " + "register receiver");
        ((HomeSeekerActivity) getActivity()).setCheckedToBottom(Keys.PROFILE);
        getActivity().registerReceiver(broadcastReceiverProfileUpdate, new IntentFilter(Keys.PROFILE));
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        Logger.e(TAG + " : " + "unregister receiver");
        if (broadcastReceiverProfileUpdate != null)
            getActivity().unregisterReceiver(broadcastReceiverProfileUpdate);
        super.onDestroy();
    }

    /**
     * receive status if any changes in profile update
     */
    BroadcastReceiver broadcastReceiverProfileUpdate =
            new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            // TODO: 1/2/17 Get music related information.
            if (getActivity() != null && isAdded())
                callWebServiceForMyProfile(false);
        }
    };

    /** init view */
    private void initView() {

        recyclerViewlanguage.setNestedScrollingEnabled(false);
        recyclerViewExperience.setNestedScrollingEnabled(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewlanguage.setHasFixedSize(false);
        recyclerViewlanguage.setLayoutManager(linearLayoutManager);

        LinearLayoutManager linearLayoutManagerExp = new LinearLayoutManager(getActivity());
        recyclerViewExperience.setHasFixedSize(false);
        recyclerViewExperience.setLayoutManager(linearLayoutManagerExp);

    }

    /** event for proceed to setting page */
    @OnClick(R.id.imageViewSetting)
    void proceedToSettingPage() {
        ((HomeSeekerActivity) getActivity()).addFragment(new SettingSeekerFragment(), new Bundle(), Keys.SETTINGS, false, true);
    }

    /** event for proceed to edit profile */
    @OnClick(R.id.btnEditProfile)
    void proceedToEditProfile() {
        //  Bundle bundle = new Bundle();
        //  bundle.putParcelable(Keys.PROFILE, getSeekerProfileResponse.getData());
        ((HomeSeekerActivity) getActivity()).addFragment(new EditProfileFragment(ProfileFragment.this), new Bundle(), Keys.EDIT_PROFILE, false, true);
    }

    /** event for retry if profile has not loaded */
    @OnClick(R.id.btnRetry)
    void retry() {
        callWebServiceForMyProfile(true);
    }

    /** event to show image on dialog */
    @OnClick(R.id.imageViewPhoto)
    void viewPhoto() {
        dialogViewPhoto();
    }

    /** event to show video on dialog */
    @OnClick(R.id.imageViewVideo)
    void viewVideo() {
        dialogViewVideo();
    }


    /** call service for getting seeker profile */
    private void callWebServiceForMyProfile(boolean isLoaderShow) {
       final SpotsDialog spotsDialog = new SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        if (isLoaderShow)
            spotsDialog.show();
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty(Keys.USER_ID, Singleton.getUserInfoInstance().getUser_id());
        retrofit.Call<GetSeekerProfileResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getMyProfileResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetSeekerProfileResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(retrofit.Response<GetSeekerProfileResponse> response, retrofit.Retrofit retrofit) {
               spotsDialog.dismiss();
                getSeekerProfileResponse = response.body();
                if (getSeekerProfileResponse.isSuccess()) {
                    rlRetry.setVisibility(View.GONE);
                    linearLayoutMain.setVisibility(View.VISIBLE);

                    // load photo
                    if (!getSeekerProfileResponse.getData().getUser_pic().equals("")) {
                        relativeLayoutUserPic.setVisibility(View.VISIBLE);
                        ImageLoader.loadImage(getActivity(), getSeekerProfileResponse.getData().getUser_pic(), imageViewPhoto);
                    } else {
                        relativeLayoutUserPic.setVisibility(View.GONE);
                    }

                    //load thumbnail from url
                    if (!getSeekerProfileResponse.getData().getPatch_video_thumbnail().equals("") &&
                            !getSeekerProfileResponse.getData().getPatch_video().equals("")) {
                        relativeLayoutPitchVideo.setVisibility(View.VISIBLE);
                        ImageLoader.loadImage(getActivity(), getSeekerProfileResponse.getData().getPatch_video_thumbnail(), imageViewVideo);
                    } else {
                        relativeLayoutPitchVideo.setVisibility(View.GONE);
                    }

                    tvLudovic.setText(getSeekerProfileResponse.getData().getFirst_name() + " " + getSeekerProfileResponse.getData().getLast_name());

                    if (getSeekerProfileResponse.getData().getCity() == null
                            || getSeekerProfileResponse.getData().getCity().equalsIgnoreCase("")) {
                        textViewLocation.setVisibility(View.GONE);
                        linearTitleLocation.setVisibility(View.GONE);
                    } else {
                        textViewLocation.setVisibility(View.VISIBLE);
                        linearTitleLocation.setVisibility(View.VISIBLE);
                        textViewLocation.setText(getSeekerProfileResponse.getData().getCity());
                    }

                    if (getSeekerProfileResponse.getData().getAbout() == null
                            || getSeekerProfileResponse.getData().getAbout().equalsIgnoreCase("")) {
                        textViewAbout.setVisibility(View.GONE);
                        linearTitleAbout.setVisibility(View.GONE);
                    } else {
                        textViewAbout.setVisibility(View.VISIBLE);
                        linearTitleAbout.setVisibility(View.VISIBLE);
                        textViewAbout.setText(getSeekerProfileResponse.getData().getAbout());
                    }

                    if (getSeekerProfileResponse.getData().getCurrent_status_name() == null
                            || getSeekerProfileResponse.getData().getCurrent_status_name().equalsIgnoreCase("")) {
                        textViewActualStatus.setVisibility(View.GONE);
                        linearTitleActualStatus.setVisibility(View.GONE);
                    } else {
                        textViewActualStatus.setVisibility(View.VISIBLE);
                        linearTitleActualStatus.setVisibility(View.VISIBLE);
                        textViewActualStatus.setText(getSeekerProfileResponse.getData().getCurrent_status_name());
                    }

                    if (getSeekerProfileResponse.getData().getMobility_name() == null
                            || getSeekerProfileResponse.getData().getMobility_name().equalsIgnoreCase("")) {
                        textViewMobility.setVisibility(View.GONE);
                        linearTitleMobility.setVisibility(View.GONE);
                    } else {
                        textViewMobility.setVisibility(View.VISIBLE);
                        linearTitleMobility.setVisibility(View.VISIBLE);
                        textViewMobility.setText(getSeekerProfileResponse.getData().
                                getMobility_name());
                    }if (getSeekerProfileResponse.getData().getCandidate_seek_name() == null
                            || getSeekerProfileResponse.getData().getCandidate_seek_name().equalsIgnoreCase("")) {
                        linear_job_sought.setVisibility(View.GONE);
                        textView_job_sought.setVisibility(View.GONE);
                    } else {
                        linear_job_sought.setVisibility(View.VISIBLE);
                        textView_job_sought.setVisibility(View.VISIBLE);
                        textView_job_sought.setText(getSeekerProfileResponse.getData().
                                getCandidate_seek_name());
                    }

                     if (getSeekerProfileResponse.getData().getSkills().isEmpty()) {
                         textViewSkills.setVisibility(View.GONE);
                         linearViewSkills.setVisibility(View.GONE);
                     }else{
                        textViewSkills.setVisibility(View.VISIBLE);
                         linearViewSkills.setVisibility(View.VISIBLE);
                         String skills="";
                         for(int i=0;i<getSeekerProfileResponse.getData().getSkills().size();i++) {
                           skills=skills+getSeekerProfileResponse.getData().getSkills().
                                     get(i).getSkills_name()+"\n";
                         }
                         textViewSkills.setText(skills);
                    }
                    if (getSeekerProfileResponse.getData().getExperience() != null &&
                    !getSeekerProfileResponse.getData().getExperience().get(0).
                            getExperience().equalsIgnoreCase("1"))
                    {
                        ProfessionalExperienceAdapter professionalExperienceAdapter = new ProfessionalExperienceAdapter(getActivity(),
                                getSeekerProfileResponse.getData().getExperience());
                        recyclerViewExperience.setAdapter(professionalExperienceAdapter);
                        recyclerViewExperience.setVisibility(View.VISIBLE);
                        noCompanyAdded.setVisibility(View.GONE);
                    }
                    else
                    {
                        recyclerViewExperience.setVisibility(View.GONE);
                        noCompanyAdded.setVisibility(View.VISIBLE);
                    }

                    if ((getSeekerProfileResponse.getData().getTraining() == null
                            || getSeekerProfileResponse.getData().getTraining().equalsIgnoreCase(""))
                            && (getSeekerProfileResponse.getData().getEducation_level_name() == null
                            || getSeekerProfileResponse.getData().getEducation_level_name().equalsIgnoreCase(""))) {
                        textViewFormation.setVisibility(View.GONE);
                        linearTitleFormation.setVisibility(View.GONE);
                    } else if (!getSeekerProfileResponse.getData().getTraining().equalsIgnoreCase("")
                            && !getSeekerProfileResponse.getData().getEducation_level_name().equalsIgnoreCase("")) {
                        textViewFormation.setVisibility(View.VISIBLE);
                        linearTitleFormation.setVisibility(View.VISIBLE);
                        textViewFormation.setText(getSeekerProfileResponse.getData().getEducation_level_name() + " - " +
                                getSeekerProfileResponse.getData().getTraining());
                    } else if (getSeekerProfileResponse.getData().getTraining() != null
                            && !getSeekerProfileResponse.getData().getTraining().equalsIgnoreCase("")) {
                        textViewFormation.setVisibility(View.VISIBLE);
                        linearTitleFormation.setVisibility(View.VISIBLE);
                        textViewFormation.setText(getSeekerProfileResponse.getData().getTraining());
                    } else if (getSeekerProfileResponse.getData().getEducation_level_name() != null
                            && !getSeekerProfileResponse.getData().getEducation_level_name().equalsIgnoreCase("")) {
                        textViewFormation.setVisibility(View.VISIBLE);
                        linearTitleFormation.setVisibility(View.VISIBLE);
                        textViewFormation.setText(getSeekerProfileResponse.getData().getEducation_level_name());
                    }



                    if (getSeekerProfileResponse.getData().getLanguages() != null
                            && getSeekerProfileResponse.getData().getLanguages().size() > 0) {
                        LanguageListAdapter languageListAdapter = new LanguageListAdapter(getActivity(), getSeekerProfileResponse.getData().getLanguages());
                        recyclerViewlanguage.setAdapter(languageListAdapter);
                        linearTitleLanguage.setVisibility(View.VISIBLE);
                        recyclerViewlanguage.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        linearTitleLanguage.setVisibility(View.GONE);
                        recyclerViewlanguage.setVisibility(View.GONE);
                    }


                    Intent intentVolume = new Intent(Keys.EDIT_PROFILE);
                    getActivity().sendBroadcast(intentVolume);

                } else {
                    if (isLoaderShow) {
                        rlRetry.setVisibility(View.VISIBLE);
                        linearLayoutMain.setVisibility(View.GONE);
                        // CommonUtils.showSimpleMessageBottom(getActivity(), getSeekerProfileResponse.getMsg());
                    }

                    if (getSeekerProfileResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(getActivity(), getSeekerProfileResponse.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                rlRetry.setVisibility(View.VISIBLE);
                tvErrorMessage.setText(t.getMessage());
                linearLayoutMain.setVisibility(View.GONE);
                //CommonUtils.showSimpleMessageBottom(getActivity(), t.getMessage());
            }
        });
    }

    /** method for open dialog to view photo */
    public void dialogViewPhoto() {
        final Dialog dialogVideoInfo = new Dialog(getActivity());
        dialogVideoInfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialogVideoInfo.getWindow() != null)
            dialogVideoInfo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogVideoInfo.setContentView(R.layout.dialog_photo_view_square);
        ImageView imageViewClose = (ImageView) dialogVideoInfo.findViewById(R.id.imageViewClose);
        ImageView imageViewPhoto = (ImageView) dialogVideoInfo.findViewById(R.id.imageViewPhoto);

        showProgress(true);
        ImageLoader.loadImageRoundCallBack(getActivity(), getSeekerProfileResponse.getData().getUser_pic(), imageViewPhoto, new ImageLoader.CallBack() {
            @Override
            public void onSuccess() {
                if (getActivity() != null && isAdded()) {
                    showProgress(false);
                    new Handler().postDelayed(dialogVideoInfo::show, 200);
                }
            }

            @Override
            public void onError() {
                if (getActivity() != null && isAdded()) {
                    showProgress(false);
                }
            }
        });
        imageViewClose.setOnClickListener(v -> dialogVideoInfo.dismiss());
        dialogVideoInfo.show();
    }

    /** dialog to play video */
    private void dialogViewVideo() {
        final Dialog dialogVideoInfo = new Dialog(getActivity());
        dialogVideoInfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialogVideoInfo.getWindow() != null)
            dialogVideoInfo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogVideoInfo.setContentView(R.layout.dialog_video_view);
        ImageView imageViewClose = (ImageView) dialogVideoInfo.findViewById(R.id.imageViewClose);
        VideoView videoView = (VideoView) dialogVideoInfo.findViewById(R.id.videoView);
        //    videoView.setVisibility(View.INVISIBLE);
        ProgressBar progressBar = (ProgressBar) dialogVideoInfo.findViewById(R.id.progressBar);
        RelativeLayout rlController = (RelativeLayout) dialogVideoInfo.findViewById(R.id.rlController);
        ImageView imageViewPlay = (ImageView) dialogVideoInfo.findViewById(R.id.imageViewPlay);
        ImageView imageViewPause = (ImageView) dialogVideoInfo.findViewById(R.id.imageViewPause);
        ImageView imageViewFull = (ImageView) dialogVideoInfo.findViewById(R.id.imageViewFull);
        imageViewClose.setOnClickListener(v -> dialogVideoInfo.dismiss());

        MediaController mediacontroller = new MediaController(getActivity());
        mediacontroller.setAnchorView(videoView);

        Logger.e(TAG + " video Url : " + getSeekerProfileResponse.getData().getPatch_video());
        videoView.setVideoPath(getSeekerProfileResponse.getData().getPatch_video());

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
            Intent intent = new Intent(getActivity(), PlayVideoFullSreenActivity.class);
            intent.putExtra(Keys.PATCH_VIDEO, getSeekerProfileResponse.getData().getPatch_video());
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

        /** hide/show custom controller on touch VideoView */
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
        videoView.setOnTouchListener((v, event) -> {
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
}
