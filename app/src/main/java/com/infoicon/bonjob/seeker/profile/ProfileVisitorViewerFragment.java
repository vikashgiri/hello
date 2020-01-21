package com.infoicon.bonjob.seeker.profile;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.infoicon.bonjob.R;
import com.infoicon.bonjob.recruiters.adapter.ProfileVisitorAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ProfileVisitorViewerFragment extends Fragment {


    private View rootView;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile_visitor_viewer, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    /** event for go back page */
    @OnClick(R.id.imgViewBack)
    void GoBack() {

    }

    /** event for setting page */
    @OnClick(R.id.imageViewSetting)
    void preceedToSettingPage() {

    }

    /** init view */
    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(linearLayoutManager);

    }
}
