package com.infoicon.bonjob.recruiters.search;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.dialogs.ExperienceDialogFragment;
import com.infoicon.bonjob.dialogs.SubscribePlanDialogFragment;
import com.infoicon.bonjob.main.HomeRecruiterActivity;
import com.infoicon.bonjob.recruiters.post.PostJobOfferFragment;
import com.infoicon.bonjob.recruiters.profile.ProfileRecruiterFragment;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.TabBarChangeViewRecruiter;
import com.infoicon.bonjob.utils.UtilsMethods;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class LookForRecruiterFragment extends Fragment {
    private View rootView;


    @BindView(R.id.btnEditProfile) CustomsTextView btnEditProfile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_look_for_recruiter, container, false);
        ButterKnife.bind(this, rootView);
        initialize();
        return rootView;
    }

    private void initialize() {
        btnEditProfile.setPaintFlags(btnEditProfile.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    @OnClick(R.id.btnEditProfile)
    void editProfile() {
        ((HomeRecruiterActivity) getActivity()).addFragment(new ProfileRecruiterFragment(), new Bundle(), Keys.PROFILE, false, true);
    }


    @OnClick(R.id.btnPostJob)
    void PostJob() {
        if (Singleton.getUserInfoInstance().getEnterprise_name().equalsIgnoreCase("")) {
            UtilsMethods.postCallback(getActivity(), new UtilsMethods.Callback() {
                @Override
                public void onYes() {
                    TabBarChangeViewRecruiter.changeView(getActivity(), Keys.PROFILE);
                    ((HomeRecruiterActivity) getActivity()).addFragment(new ProfileRecruiterFragment(), new Bundle(), Keys.PROFILE, false, true);
                }
            });

        } else{
            // (UtilsMethods.isValidForPostJob(getActivity())) {
            ((HomeRecruiterActivity) getActivity()).addFragment(new PostJobOfferFragment(), new Bundle(), Keys.POST_JOB, false, true);
        }
    }

    @OnClick(R.id.tvViewOnlineCandidate)
    void ViewOnlineCandidate() {
        if (UtilsMethods.isValidForSearch(getActivity()))
            ((HomeRecruiterActivity) getActivity()).addFragment(new SearchForCandidateFragment(), new Bundle(), Keys.VIEW_ONLINE_CANDIDATE, false, true);
    }

    @OnClick(R.id.btnSubscribe)
    void subscribe() {
        if (!UtilsMethods.isInternetConnected(getActivity())) {
            UtilsMethods.openCustumAlert(getActivity(), getString(R.string.internet_connection));
        } else {
            FragmentManager fm = getFragmentManager();
            SubscribePlanDialogFragment experienceDialogFragment = new SubscribePlanDialogFragment();
            Bundle bundle = new Bundle();
            experienceDialogFragment.setArguments(bundle);
            experienceDialogFragment.setTargetFragment(this, Keys.REQUEST_CODE_SUBSCRIBE_PLAN);
            experienceDialogFragment.show(fm, Keys.TAG_SUBSCRIBE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeRecruiterActivity) getActivity()).setCheckedToBottom(Keys.LOOK_FOR);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if (requestCode == Keys.VERIFY_PHONE) {
            if (resultCode == RESULT_OK) {
                String loadPage = data.getStringExtra(Keys.VIEW_FRAGMENT);
                if (loadPage.equals(Keys.PROFILE)) {//load profile
                    new Thread(() -> ((HomeRecruiterActivity) getActivity()).addFragment(new ProfileRecruiterFragment(), new Bundle(), Keys.PROFILE, false, true)).start();

                } else {//load offer
                    new Thread(() -> ((HomeRecruiterActivity) getActivity()).addFragment(new OfferTabFragment(), new Bundle(), Keys.MY_OFFERS, false, true)).start();
                }

            }
        }*/


        switch (requestCode) {
            case Keys.REQUEST_CODE_SUBSCRIBE_PLAN:
                if (resultCode == RESULT_OK && data != null) {

                }
                break;
        }


    }
}
