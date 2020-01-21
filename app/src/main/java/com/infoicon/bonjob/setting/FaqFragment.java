package com.infoicon.bonjob.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.response.GetFaqResponse;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.CommonUtils;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

/**
 * Created by sumit on 31/1/17.
 */

public class FaqFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private FAQExpandableListAdapter listAdapter;

    private List<String> listDataHeader;
    private List<String> listDataChild;

    @BindView(R.id.textViewBack) CustomsTextView textViewBack;
    @BindView(R.id.tvTitle) CustomsTextViewBold tvTitle;
    @BindView(R.id.expendableListView) ExpandableListView expendableListView;
    @BindView(R.id.layout_refresh) LinearLayout layout_refresh;
    @BindView(R.id.imageViewReload) ImageView imageViewReload;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceGetFaq();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_faq, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        initListeners();
    }

    /**
     * initializing views elements
     */

    public void initViews() {
        textViewBack.setText(getResources().getString(R.string.settings));
        tvTitle.setText(getResources().getString(R.string.faq));
        tvTitle.setTextAppearance(getActivity(), R.style.boldText);


        listDataHeader = new ArrayList<>();
        listDataChild = new ArrayList<>();

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        expendableListView.setIndicatorBounds(width - GetPixelFromDips(50), width - GetPixelFromDips(10));
        layout_refresh.setVisibility(View.GONE);
    }

    /**
     * initializing click listeners
     */

    public void initListeners() {

        imageViewReload.setOnClickListener(this);
    }

    @OnClick(R.id.textViewBack)
    void GoBack() {
        (getActivity()).onBackPressed();
    }


    public int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }


    @Override
    public void onClick(View view) {
        if (view == imageViewReload) {
            serviceGetFaq();
        }
    }


    /** call service for get my offer */
    private void serviceGetFaq() {
        final SpotsDialog spotsDialog = new SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.LANG_ID, UtilsMethods.getDefaultLanguage());
        retrofit.Call<GetFaqResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getFaqResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetFaqResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetFaqResponse> response, retrofit.Retrofit retrofit) {
                if (spotsDialog.isShowing())
                    spotsDialog.dismiss();

                GetFaqResponse getFaqResponse = response.body();
                if (getFaqResponse.isSuccess()) {

                    if (Singleton.getUserInfoInstance().isLoginRecriter()) {
                        for (int i = 0; i < getFaqResponse.getData().size(); i++) {
                            if (getFaqResponse.getData().get(i).getUser_type().equals("employer")) {
                                listDataHeader.add(getFaqResponse.getData().get(i).getPage_title());
                                listDataChild.add(getFaqResponse.getData().get(i).getPage_content());
                            }
                        }
                    } else {
                        for (int i = 0; i < getFaqResponse.getData().size(); i++) {
                            if (getFaqResponse.getData().get(i).getUser_type().equals("seeker")) {
                                listDataHeader.add(getFaqResponse.getData().get(i).getPage_title());
                                listDataChild.add(getFaqResponse.getData().get(i).getPage_content());
                            }
                        }
                    }
                    listAdapter = new FAQExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
                    expendableListView.setAdapter(listAdapter);

                } else {
                    // setMyOfferVisibility(false);
                    if (getFaqResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(getActivity(), getFaqResponse.getMsg());
                    } else
                        CommonUtils.showSimpleMessageBottom(getActivity(), getFaqResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(getActivity(), t.getMessage());
            }
        });
    }
}
