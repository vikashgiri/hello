package com.infoicon.bonjob.seeker.searchJob;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomCheckBox;
import com.infoicon.bonjob.customview.CustomsButton;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.dialogs.DiplomaDialogFragment;
import com.infoicon.bonjob.dialogs.ExperienceDialogFragment;
import com.infoicon.bonjob.dialogs.FullPartTimeDialogFragment;
import com.infoicon.bonjob.dialogs.NumberOfHoursDialogFragment;
import com.infoicon.bonjob.dialogs.TypeOfContractDialogFragment;
import com.infoicon.bonjob.main.HomeSeekerActivity;
import com.infoicon.bonjob.picasso.ImageLoader;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.response.GetCompanyDetailsResponse;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

import static android.app.Activity.RESULT_OK;

/**
 * Created by infoicon on 12/6/17.
 * this class for more filtration while search job.
 */

@SuppressLint("ValidFragment")
public class MoreFilterFragment extends Fragment {

    private View rootView;
    @BindView(R.id.tvTypeDeContract) CustomsTextView tvTypeDeContract;
    @BindView(R.id.tvEducationLevel) CustomsTextView tvEducationLevel;
    @BindView(R.id.tvPartFullTime) CustomsTextView tvPartFullTime;
    @BindView(R.id.tvExperience) CustomsTextView tvExperience;
    @BindView(R.id.cbRestaurant) CustomCheckBox cbRestaurant;
    @BindView(R.id.cbSale) CustomCheckBox cbSale;
    @BindView(R.id.cbHotel) CustomCheckBox cbHotel;
    @BindView(R.id.btnSearch) CustomsButton btnSearch;
    private String typeOfContractPosition = "" ;
    private String experiencePosition = "" ;
    private String eduLevelPosition = "";
    private String fullPartTimePosition = "";
    private String filterContractType = "", filterEducationLevel = "", filterNumHours = "", filterExperience = "";
    private String filterContractTypeId="",filterEducationLevelId = "", filterNumHoursId = "", filterExperienceId = "";;

    SearchJobFragment searchJobFragment;
    private   JobSearchDropDownResponce getCompanyDetailsResponse;
    public MoreFilterFragment(SearchJobFragment searchJobFragment) {
        this.searchJobFragment = searchJobFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_more_filters, container, false);
        ButterKnife.bind(this, rootView);
       // serviceForGetData();
        //Bundle bundle = getArguments();

/*

        if (bundle.getString("filterContractType").length()!=0) {
            // doSomething
            tvTypeDeContract.setText(bundle.getString("filterContractType"));
            tvTypeDeContract.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color));

        }
        if(bundle.getString("filterEducationLevel").length()!=0)
        {
            tvEducationLevel.setText(bundle.getString("filterEducationLevel"));
            tvEducationLevel.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color));

        }



        if(bundle.getString("filterExperience").length()>0)
        {
            tvExperience.setText(bundle.getString("filterExperience"));
            tvExperience.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color));
        }

        if (bundle.getString("typeOfContractPosition").length()>0) {
            // doSomething
            typeOfContractPosition=bundle.getString("typeOfContractPosition");
        }
     if(bundle.getString("experiencePosition").length()!=0)
        {
            experiencePosition=bundle.getString("experiencePosition");
        }

        if(bundle.getString("eduLevelPosition").length()!=0)
        {
            eduLevelPosition=bundle.getString("eduLevelPosition");
        }
        if(bundle.getString("fullPartTimePosition").length()!=0)
        {
            fullPartTimePosition=bundle.getString("fullPartTimePosition");
        }
*/

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       setFilterValue();
    }


    /** event for go back */
    @OnClick(R.id.textViewBack)
    void Back() {
        (getActivity()).onBackPressed();
    }

    /** event for get type of contract dialog */
    @OnClick(R.id.tvTypeDeContract)
    void typeOfContract() {
        FragmentManager fm = getFragmentManager();
        TypeOfContractDialogFragment typeOfContractDialogFragment = new TypeOfContractDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Keys.TYPE, "search");
        bundle.putString(Keys.SELECTED_POSITION, typeOfContractPosition);
        bundle.putParcelable("CompanyDetails",getCompanyDetailsResponse);
        bundle.putParcelable("CompanyDetails",getCompanyDetailsResponse);
        typeOfContractDialogFragment.setArguments(bundle);
        typeOfContractDialogFragment.setTargetFragment(this, Keys.CONTRACT_TYPE_REQUEST_CODE);
        typeOfContractDialogFragment.show(fm, Keys.TAG_TYPE_OF_CONTRACT);
    }

    /** event for education level dialog */
    @OnClick(R.id.tvEducationLevel)
    void educationLevel() {
        FragmentManager fm = getFragmentManager();
        DiplomaDialogFragment diplomaDialogFragment = new DiplomaDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Keys.TYPE, "search");
        bundle.putString(Keys.SELECTED_POSITION, eduLevelPosition);
        bundle.putParcelable("CompanyDetails",getCompanyDetailsResponse);

        diplomaDialogFragment.setArguments(bundle);
        diplomaDialogFragment.setTargetFragment(this, Keys.DIPLOMA_REQUEST_CODE);
        diplomaDialogFragment.show(fm, Keys.TAG_DIPLOMA);
    }

    /** event for get full part time dialog */
    @OnClick(R.id.tvPartFullTime)
    void partFullTime() {
        FragmentManager fm = getFragmentManager();
        NumberOfHoursDialogFragment numberOfHoursDialogFragment = new NumberOfHoursDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Keys.TYPE, "search");
        bundle.putString(Keys.SELECTED_POSITION, fullPartTimePosition);
        bundle.putParcelable("CompanyDetails",getCompanyDetailsResponse);

        numberOfHoursDialogFragment.setArguments(bundle);
        numberOfHoursDialogFragment.setTargetFragment(this, Keys.NUM_HOUR_REQUEST_CODE);
        numberOfHoursDialogFragment.show(fm, Keys.TAG_NUM_HOUR);
    }

    /** event for get experience dialog */
    @OnClick(R.id.tvExperience)
    void experience() {
        FragmentManager fm = getFragmentManager();
        ExperienceDialogFragment experienceDialogFragment = new ExperienceDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Keys.SELECTED_POSITION, experiencePosition);
        bundle.putString(Keys.TYPE, "search");
        bundle.putParcelable("CompanyDetails",getCompanyDetailsResponse);

        experienceDialogFragment.setArguments(bundle);
        experienceDialogFragment.setTargetFragment(this, Keys.EXPERIENCE_REQUEST_CODE);
        experienceDialogFragment.show(fm, Keys.TAG_EXPERIENCE);
    }


    @OnClick(R.id.btnSearch)
    void search() {
        searchJobFragment.filterContractType = filterContractType;
        searchJobFragment.filterContractTypeId= filterContractTypeId;
        searchJobFragment.filterEducationLevel = filterEducationLevel;
        searchJobFragment.filterEducationLevelId = filterEducationLevelId;
        searchJobFragment.filterNumHours = filterNumHours;
        searchJobFragment.filterNumHoursId = filterNumHoursId;
        searchJobFragment.filterExperience = filterExperience;
        searchJobFragment.filterExperienceId = filterExperienceId;

        searchJobFragment.typeOfContractPosition = typeOfContractPosition;
        searchJobFragment.experiencePosition = experiencePosition;
        searchJobFragment.eduLevelPosition = eduLevelPosition;
        searchJobFragment.fullPartTimePosition = fullPartTimePosition;
        searchJobFragment.setFilterValue();
        (getActivity()).onBackPressed();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeSeekerActivity) getActivity()).setCheckedToBottom(Keys.LOOK_FOR);
    }


    /** set filter value for already selection */
    private void setFilterValue() {


        if (!searchJobFragment.filterContractType.equals("")) {
            filterContractTypeId = searchJobFragment.filterContractTypeId;
            filterContractType = searchJobFragment.filterContractType;
            typeOfContractPosition=searchJobFragment.typeOfContractPosition;
            tvTypeDeContract.setText(filterContractType);
            tvTypeDeContract.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color));
        }

        if (!searchJobFragment.filterEducationLevel.equals("")) {
            filterEducationLevel = searchJobFragment.filterEducationLevel;
            filterEducationLevelId = searchJobFragment.filterEducationLevelId;
            tvEducationLevel.setText(filterEducationLevel);
            eduLevelPosition= searchJobFragment.eduLevelPosition;

            tvEducationLevel.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color));
        }

        if (!searchJobFragment.filterNumHours.equals("")) {
            filterNumHours = searchJobFragment.filterNumHours;
            filterNumHoursId = searchJobFragment.filterNumHoursId;
            fullPartTimePosition=searchJobFragment.fullPartTimePosition;

            tvPartFullTime.setText(filterNumHours);
            tvPartFullTime.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color));
        }

        if (!searchJobFragment.filterExperience.equals("")) {
            experiencePosition=searchJobFragment.experiencePosition;
            filterExperienceId=searchJobFragment.filterExperienceId;

            filterExperience = searchJobFragment.filterExperience;
            tvExperience.setText(filterExperience);
            tvExperience.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color));
        }

    }


    /** result */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Keys.CONTRACT_TYPE_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    typeOfContractPosition = data.getStringExtra(Keys.POSITION);
                    tvTypeDeContract.setText(data.getStringExtra(Keys.TYPE_OF_CONTRACT));
                    tvTypeDeContract.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color));
                    filterContractType = data.getStringExtra(Keys.TYPE_OF_CONTRACT);

                    filterContractTypeId = data.getStringExtra(Keys.TYPE_OF_CONTRACT_ID);

                    if(filterContractType==null)
                    {
                        filterContractType="";
                        typeOfContractPosition="";
                        filterContractTypeId="";
                        tvTypeDeContract.setText(getString(R.string.type_of_contract));
                        tvTypeDeContract.setTextColor(ContextCompat.getColor(getActivity(), R.color.pink_color));
                    }
                }
                break;
            case Keys.EXPERIENCE_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    experiencePosition = data.getStringExtra(Keys.POSITION);
                    tvExperience.setText(data.getStringExtra(Keys.EXPERIENCE));
                    tvExperience.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color));
                    filterExperience = data.getStringExtra(Keys.EXPERIENCE);
                    if(filterExperience==null)
                    {
                        filterExperience="";
                        filterExperienceId="";

                        experiencePosition="";
                        tvExperience.setText(getString(R.string.level_of_education));
                        tvExperience.setTextColor(ContextCompat.getColor(getActivity(), R.color.pink_color));

                    }
                    filterExperienceId = data.getStringExtra(Keys.EXPERIENCE_ID);

                }
                break;
            case Keys.DIPLOMA_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    eduLevelPosition = data.getStringExtra(Keys.POSITION);
                    tvEducationLevel.setText(data.getStringExtra(Keys.DIPLOMA));
                    tvEducationLevel.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color));
                    filterEducationLevel = data.getStringExtra(Keys.DIPLOMA);
                 //   filterEducationLevel = data.getStringExtra(Keys.EXPERIENCE);
                    if(filterEducationLevel==null)
                    {
                        filterEducationLevel="";
                        filterEducationLevelId="";
                        eduLevelPosition="";
                        tvEducationLevel.setText(getString(R.string.full_part_time));
                        tvEducationLevel.setTextColor(ContextCompat.getColor(getActivity(), R.color.pink_color));

                    }
                    filterEducationLevelId = data.getStringExtra(Keys.DIPLOMA_ID);
                }
                break;
            case Keys.NUM_HOUR_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    fullPartTimePosition = data.getStringExtra(Keys.POSITION);
                    tvPartFullTime.setText(data.getStringExtra(Keys.NUM_HOUR));
                    tvPartFullTime.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color));
                    filterNumHours = data.getStringExtra(Keys.NUM_HOUR);
                   // filterEducationLevel = data.getStringExtra(Keys.DIPLOMA);
                    //   filterEducationLevel = data.getStringExtra(Keys.EXPERIENCE);
                    if(filterNumHours==null)
                    {
                        filterNumHours="";
                        filterNumHoursId="";
                        fullPartTimePosition="";
                        tvPartFullTime.setText(getString(R.string.experience));
                        tvPartFullTime.setTextColor(ContextCompat.getColor(getActivity(), R.color.pink_color));

                    }
                    filterNumHoursId = data.getStringExtra(Keys.NUM_HOUR_ID);

                }
                break;

        }

    }

    private void serviceForGetData()
    {
        final SpotsDialog spotsDialog = new
                SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();

        retrofit.Call<JobSearchDropDownResponce> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getJobSearchDropDownResponce
                (jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<JobSearchDropDownResponce>() {
            @Override
            public void onResponse(retrofit.Response<JobSearchDropDownResponce> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                 getCompanyDetailsResponse = response.body();

                if (getCompanyDetailsResponse.isSuccess())
                {


                } else {
                    if (getCompanyDetailsResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(getActivity(), getCompanyDetailsResponse.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();

            }
        });
    }
}
