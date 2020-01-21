package com.infoicon.bonjob.seeker.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomsButton;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.main.HomeSeekerActivity;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.response.GetSeekerActivityResponse;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class UsersActivityFragment extends Fragment  implements SwipeRefreshLayout.OnRefreshListener{


    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.tvTitle) CustomsTextViewBold tvTitle;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tvErrorMessage) CustomsTextViewBold tvErrorMessage;
    @BindView(R.id.btnRetry) CustomsButton btnRetry;
    @BindView(R.id.rlRetry) RelativeLayout rlRetry;
    private View rootView;
    private UserActivityAdapter userActivityAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceGetSeekerActivity();
    }

    @Override
    public void onRefresh() {

        // Fetching data from server
        serviceGetSeekerActivity();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_users_activity, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialization();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeSeekerActivity) getActivity()).setCheckedToBottom(Keys.ACTIVITY);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            Logger.e("UserActivity : "+"visible");
        }else {
            Logger.e("UserActivity : "+"In visible");
        }
    }

    @OnClick(R.id.btnRetry)
    void retry() {
        serviceGetSeekerActivity();
    }


    private void initialization() {
        tvTitle.setText(getResources().getString(R.string.tabbar_active));
        // tvTitle.setTextAppearance(getActivity(), R.style.boldText);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        userActivityAdapter = new UserActivityAdapter(getActivity());
        recyclerView.setAdapter(userActivityAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
    }


    /** call service for get seeker activity */
    private void serviceGetSeekerActivity()
    {
        final SpotsDialog spotsDialog = new SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.USER_ID, Singleton.getUserInfoInstance().getUser_id());
        Call<GetSeekerActivityResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices()
                .getSeekerActivityResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new Callback<GetSeekerActivityResponse>()
        {
            @Override
            public void onResponse(Response<GetSeekerActivityResponse> response, Retrofit retrofit) {
            if (spotsDialog.isShowing())
                    spotsDialog.dismiss();
                mSwipeRefreshLayout.setRefreshing(false);
                GetSeekerActivityResponse getSeekerActivityResponse = response.body();
                if (getSeekerActivityResponse.isSuccess())
                {
                    recyclerView.setVisibility(View.VISIBLE);
                    rlRetry.setVisibility(View.GONE);
                    if (getSeekerActivityResponse.getData().size() > 0) {
                        userActivityAdapter.clear();
                        for (GetSeekerActivityResponse.DataBean dataBean : getSeekerActivityResponse.getData()) {
                            userActivityAdapter.addData(dataBean);
                        }
                        userActivityAdapter.setCurrentDate(getSeekerActivityResponse.getCurrent_time());
                    }
                } else {
                    recyclerView.setVisibility(View.GONE);
                    btnRetry.setVisibility(View.GONE);
                    rlRetry.setVisibility(View.VISIBLE);
                    tvErrorMessage.setText(getSeekerActivityResponse.getMsg());
                    if (getSeekerActivityResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(getActivity(), getSeekerActivityResponse.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                recyclerView.setVisibility(View.GONE);
                rlRetry.setVisibility(View.VISIBLE);
                btnRetry.setVisibility(View.VISIBLE);
                tvErrorMessage.setText(t.getMessage());
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }


}
