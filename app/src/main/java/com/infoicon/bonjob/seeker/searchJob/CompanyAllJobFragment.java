package com.infoicon.bonjob.seeker.searchJob;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.picasso.ImageLoader;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.response.GetCompanyDetailsResponse;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;

public class CompanyAllJobFragment extends Fragment {
    private CompanyJobOfferAdapter companyJobOfferAdapter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    View rootView;
    private Unbinder unbinder;
    private String employerId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
            rootView = inflater.inflate(R.layout.fragment_company_all_jobs, container, false);
            unbinder = ButterKnife.bind(this, rootView);
            getBundles();

        } catch (InflateException e) {
            e.printStackTrace();
        }
        return rootView;
    }
    @OnClick(R.id.textViewBack)
    void GoBack() {
        (getActivity()).onBackPressed();
    } /** go back to previous screen */

    /** get bundle data */
    private void getBundles() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            employerId = bundle.getString(Keys.EMPLOYER_ID);
            serviceForGetCompanyJobList();
        }
    }
    private void serviceForGetCompanyJobList() {
        final SpotsDialog spotsDialog = new SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.EMPLOYER_ID, employerId);
        retrofit.Call<CompanyAllJobFragmentsResponce> call =
                AppRetrofit.getAppRetrofitInstance().getApiServices().getCompanyJobResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<CompanyAllJobFragmentsResponce>() {
            @Override
            public void onResponse(retrofit.Response<CompanyAllJobFragmentsResponce> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                CompanyAllJobFragmentsResponce getCompanyDetailsResponse = response.body();

                if (getCompanyDetailsResponse.isSuccess()) {
                    recyclerView.setNestedScrollingEnabled(false);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                            LinearLayoutManager.VERTICAL, false);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(layoutManager);
                    companyJobOfferAdapter = new CompanyJobOfferAdapter(getActivity(),
                            getCompanyDetailsResponse.getJobs());
                    recyclerView.setAdapter(companyJobOfferAdapter);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
            }
        });
    }



}
