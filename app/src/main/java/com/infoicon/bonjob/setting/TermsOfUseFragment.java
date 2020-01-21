package com.infoicon.bonjob.setting;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.response.GetTermsOfUseResponse;
import com.infoicon.bonjob.utils.CommonUtils;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class TermsOfUseFragment extends Fragment {


    private View rootView;
    @BindView(R.id.tvContent) CustomsTextView tvContent;
    @BindView(R.id.tvTitle) CustomsTextViewBold tvTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceTermsOfUse();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_terms_of_use, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTitle.setText(getResources().getString(R.string.privacy_policy));

    }

    @OnClick(R.id.textViewBack)
    void back() {
        (getActivity()).onBackPressed();
    }

    /** call service for get terms of use */
    private void serviceTermsOfUse() {
        final SpotsDialog spotsDialog = new SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.LANG_ID, UtilsMethods.getDefaultLanguage());
        retrofit.Call<GetTermsOfUseResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getTermsOfUseResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetTermsOfUseResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetTermsOfUseResponse> response, retrofit.Retrofit retrofit) {
                if (spotsDialog.isShowing())
                    spotsDialog.dismiss();

                GetTermsOfUseResponse getTermsOfUseResponse = response.body();
                if (getTermsOfUseResponse.isSuccess()) {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        tvContent.setText(Html.fromHtml(getTermsOfUseResponse.getData().get(0).getPage_content(), Html.FROM_HTML_MODE_LEGACY));
                    } else {
                        tvContent.setText(Html.fromHtml(getTermsOfUseResponse.getData().get(0).getPage_content()));
                    }
                }else {
                    if (getTermsOfUseResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(getActivity(), getTermsOfUseResponse.getMsg());
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

}
