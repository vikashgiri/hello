package com.infoicon.bonjob.seeker.searchJob;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomsButton;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.main.HomeSeekerActivity;
import com.infoicon.bonjob.seeker.profile.ProfileFragment;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.Keys;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LookForFragment extends Fragment {
    private View rootView;

    @BindView(R.id.buttonSearchJob) CustomsButton buttonSearchJob;
    @BindView(R.id.btnEditProfile) CustomsButton btnEditProfile;
    @BindView(R.id.tvTitle) CustomsTextViewBold tvTitle;
    @BindView(R.id.tvUserName) CustomsTextViewBold tvUserName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_look_for, container, false);
        ButterKnife.bind(this, rootView);
        initialize();
        return rootView;
    }

    private void initialize() {
       // tvUserName.setText(UtilsMethods.capitalize(getResources().getString(R.string.secondviewfor_welcome) + " " + Singleton.getUserInfoInstance().getFirst_name()));
        tvUserName.setText(getResources().getString(R.string.secondviewfor_welcome) + " " + Singleton.getUserInfoInstance().getFirst_name());
    }


    @OnClick(R.id.btnEditProfile)
    void editProfile() {
        ((HomeSeekerActivity) getActivity()).addFragment(new ProfileFragment(), new Bundle(), Keys.PROFILE, false, true);
        // ((HomeSeekerActivity) getActivity()).replaceFragment(new EditProfileFragment(), new Bundle(), Keys.EDIT_PROFILE, false, true);
    }


    @OnClick(R.id.buttonSearchJob)
    void searchJob() {
        ((HomeSeekerActivity) getActivity()).addFragment(new SearchJobFragment(), new Bundle(), Keys.SEARCH_JOB, false, true);
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
}
