package com.infoicon.bonjob.recruiters.search;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomEditText;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.dialogs.CompetencesDialogFragment;
import com.infoicon.bonjob.dialogs.CurrentStatusDialogFragment;
import com.infoicon.bonjob.dialogs.DiplomaDialogFragment;
import com.infoicon.bonjob.dialogs.DiplomaDialogRecruiterFragment;
import com.infoicon.bonjob.dialogs.ExperienceDialogFragment;
import com.infoicon.bonjob.dialogs.ExperienceDialogRecruiterFragment;
import com.infoicon.bonjob.dialogs.LanguageDialogFragment;
import com.infoicon.bonjob.dialogs.LanguageDialogRecruiterFragment;
import com.infoicon.bonjob.dialogs.MobilityDialogFragment;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.main.HomeRecruiterActivity;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.seeker.profile.PickLocationActivity;
import com.infoicon.bonjob.seeker.searchJob.JobSearchDropDownResponce;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.splash.GetAllDropDownResponce;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;

import org.json.JSONArray;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class TargetedSearchFilterFragment extends Fragment {


    private String TAG = this.getClass().getSimpleName();
    private View rootView;
    @BindView(R.id.textViewBack) CustomsTextView textViewBack;
    @BindView(R.id.tvLocationName) CustomsTextView tvLocationName;
    @BindView(R.id.tvTitle) CustomsTextViewBold tvTitle;
    @BindView(R.id.edSearch) CustomEditText edSearch;

    @BindView(R.id.imageViewDiploma) ImageView imageViewDiploma;
    @BindView(R.id.imageViewCompetences) ImageView imageViewCompetences;
    @BindView(R.id.imageViewExperience) ImageView imageViewExperience;
    @BindView(R.id.imageViewCurrentStatus) ImageView imageViewCurrentStatus;
    @BindView(R.id.imageViewMobility) ImageView imageViewMobility;
    @BindView(R.id.imageViewLanguage) ImageView imageViewLanguage;

    @BindView(R.id.tvDiploma) CustomsTextView tvDiploma;
    @BindView(R.id.tvCompetences) CustomsTextView tvCompetences;
    @BindView(R.id.tvExperience) CustomsTextView tvExperience;
    @BindView(R.id.tvCurrentStatus) CustomsTextView tvCurrentStatus;
    @BindView(R.id.tvMobility) CustomsTextView tvMobility;
    @BindView(R.id.tvLanguage) CustomsTextView tvLanguage;

    private String diplomaPosition;
    private String competencesPosition;
    private String experiencePosition ;
    private String currentStatusPosition ;
    private String mobilityPosition;
    private String languagePosition ;
    GetAllDropDownResponce getCompanyDetailsResponse;
    private String filterLocation = "", filterCategory = "", filterEducationLevel
            = "", filterExperience = "",
            filterActualStatus = "", filterMobility = "", filterLanguage = "";
    private String filterActualStatusId="",filterEducationLevelId = "",
            filterMobilityId = "", filterExperienceId = "",filterLanguageId=""
            ,filterCategoryId="";;

    private SearchForCandidateFragment searchForCandidateFragment;

    public TargetedSearchFilterFragment(SearchForCandidateFragment searchForCandidateFragment)
    {
        // Required empty public constructor
        this.searchForCandidateFragment = searchForCandidateFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_targeted_search_filter, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();

    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeRecruiterActivity) getActivity()).setCheckedToBottom(Keys.LOOK_FOR);
    }


    /** event for go to back */
    @OnClick(R.id.textViewBack)
    void goBack() {
        (getActivity()).onBackPressed();
    }

    /** event for find candidate  */
    @OnClick(R.id.btnFindCandidate)
    void findCandidate() {
        searchForCandidateFragment.filterLocation = filterLocation;
        searchForCandidateFragment.searchKeyword = edSearch.getText().toString().trim();

        searchForCandidateFragment.filterCategory = filterCategory;
        searchForCandidateFragment.filterCategoryId = filterCategoryId;

        searchForCandidateFragment.filterEducationLevel = filterEducationLevel;
        searchForCandidateFragment.filterEducationLevelId = filterEducationLevelId;


        searchForCandidateFragment.filterExperience = filterExperience;
        searchForCandidateFragment.filterExperienceId = filterExperienceId;


        searchForCandidateFragment.filterActualStatus = filterActualStatus;
        searchForCandidateFragment.filterActualStatusId = filterActualStatusId;


        searchForCandidateFragment.filterMobility = filterMobility;
        searchForCandidateFragment.filterMobilityId = filterMobilityId;

        searchForCandidateFragment.filterLanguage = filterLanguage;
        searchForCandidateFragment.filterLanguageId = filterLanguageId;

        searchForCandidateFragment.filterSearchKey = edSearch.getText().toString().trim();
        searchForCandidateFragment.setFilterValue();
        (getActivity()).onBackPressed();
    }

    /** event for pick location */
    @OnClick(R.id.imageViewLocation)
    void getLocation() {
        Intent intent = new Intent(getActivity(), PickLocationActivity.class);
        startActivityForResult(intent, Keys.LOCATION_CODE);
    }

    /** event for get diploma list */
    @OnClick(R.id.linearLayoutDiploma)
    void diploma() {
        FragmentManager fm = getFragmentManager();
        DiplomaDialogRecruiterFragment diplomaDialogFragment =
                new DiplomaDialogRecruiterFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Keys.TYPE, "search");


        bundle.putParcelable("CompanyDetails",
                getCompanyDetailsResponse);

        bundle.putString(Keys.SELECTED_POSITION, diplomaPosition);
        diplomaDialogFragment.setArguments(bundle);
        diplomaDialogFragment.setTargetFragment(this, Keys.DIPLOMA_REQUEST_CODE);
        diplomaDialogFragment.show(fm, Keys.TAG_DIPLOMA);
    }

    /** event for get competences list */
    @OnClick(R.id.linearLayoutCompetences)
    void competences() {
        FragmentManager fm = getFragmentManager();
        CompetencesDialogFragment competencesDialogFragment = new CompetencesDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("CompanyDetails",getCompanyDetailsResponse);

        bundle.putString(Keys.TYPE, "search");
        bundle.putString(Keys.SELECTED_POSITION, competencesPosition);
        competencesDialogFragment.setArguments(bundle);
        competencesDialogFragment.setTargetFragment(this, Keys.COMPETENCES_REQUEST_CODE);
        competencesDialogFragment.show(fm, Keys.TAG_COMPETENCES);
    }

    /** event for get experience list */
    @OnClick(R.id.linearLayoutExperience)
    void experience() {
        FragmentManager fm = getFragmentManager();
        ExperienceDialogRecruiterFragment experienceDialogFragment = new ExperienceDialogRecruiterFragment();
        Bundle bundle = new Bundle();

        bundle.putString(Keys.SELECTED_POSITION, experiencePosition);
        bundle.putParcelable("CompanyDetails",getCompanyDetailsResponse);

        bundle.putString(Keys.TYPE, "search");
        experienceDialogFragment.setArguments(bundle);
        experienceDialogFragment.setTargetFragment(this, Keys.EXPERIENCE_REQUEST_CODE);
        experienceDialogFragment.show(fm, Keys.TAG_EXPERIENCE);
    }

    /** event for get current status list */
    @OnClick(R.id.linearLayoutCurrentStatus)
    void currentStatus() {
        FragmentManager fm = getFragmentManager();
        CurrentStatusDialogFragment currentStatusDialogFragment = new CurrentStatusDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("CompanyDetails",getCompanyDetailsResponse);

        bundle.putString(Keys.SELECTED_POSITION, currentStatusPosition);
        bundle.putString(Keys.TYPE, "search");
        currentStatusDialogFragment.setArguments(bundle);
        currentStatusDialogFragment.setTargetFragment(this, Keys.CURRENT_STATUS_REQUEST_CODE);
        currentStatusDialogFragment.show(fm, Keys.TAG_CURRENT_STATUS);
    }

    /** event for get mobility list */
    @OnClick(R.id.linearLayoutMobility)
    void mobility() {
        FragmentManager fm = getFragmentManager();
        MobilityDialogFragment mobilityDialogFragment = new MobilityDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Keys.SELECTED_POSITION, mobilityPosition);
        bundle.putParcelable("CompanyDetails",getCompanyDetailsResponse);

        bundle.putString(Keys.TYPE, "search");


        mobilityDialogFragment.setArguments(bundle);
        mobilityDialogFragment.setTargetFragment(this, Keys.MOBILITY_REQUEST_CODE);
        mobilityDialogFragment.show(fm, Keys.TAG_MOBILITY);
    }

    /** event for get language list */
    @OnClick(R.id.linearLayoutLanguage)
    void language() {
        FragmentManager fm = getFragmentManager();
        LanguageDialogRecruiterFragment languageDialogFragment = new LanguageDialogRecruiterFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Keys.SELECTED_POSITION, languagePosition);
        bundle.putString(Keys.TYPE, "search");


        bundle.putParcelable("CompanyDetails",
                getCompanyDetailsResponse);

        languageDialogFragment.setArguments(bundle);
        languageDialogFragment.setTargetFragment(this, Keys.LANGUAGE_REQUEST_CODE);
        languageDialogFragment.show(fm, Keys.TAG_LANGUAGE);
    }

    private void initialize() {
        getCompanyDetailsResponse = Singleton.getUserInfoInstance().getAllDropdowns();

        edSearch.setSingleLine(true);
        tvTitle.setText(getResources().getString(R.string.target_search));
        filterLocation=searchForCandidateFragment.filterLocation;

        if (!UtilsMethods.postalCode.equals("")) {
            tvLocationName.setText(filterLocation+", "+UtilsMethods.postalCode);
        }
        else
        {
            tvLocationName.setText(filterLocation);

        }
    }

    /** getting result after fetching photo/video from camera/gallery */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Keys.LOCATION_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    filterLocation = data.getStringExtra(Keys.LOCATION_CITY);

 if (!UtilsMethods.postalCode.equals("")) {
     tvLocationName.setText(filterLocation+", "+UtilsMethods.postalCode);
 }
                else
 {
     tvLocationName.setText(filterLocation);

 }
                }
                break;
            case Keys.DIPLOMA_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    diplomaPosition = data.getStringExtra(Keys.POSITION);
                    filterEducationLevel = data.getStringExtra(Keys.DIPLOMA);
                    tvDiploma.setText(filterEducationLevel);
                    tvDiploma.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_color));
                    imageViewDiploma.setImageResource(R.drawable.icocheckmarkplein_blue);
                    if(filterEducationLevel==null)
                    {
                        filterEducationLevel="";
                        diplomaPosition="";
                        filterEducationLevelId="";

                        imageViewDiploma.setImageResource(R.drawable.pink_hat);
                    }
                    filterEducationLevelId = data.getStringExtra(Keys.DIPLOMA_ID);
                }
                break;

            case Keys.COMPETENCES_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    competencesPosition = data.getStringExtra(Keys.POSITION);
                    filterCategory = data.getStringExtra(Keys.COMPETENCES);
                    tvCompetences.setText(filterCategory);
                    tvCompetences.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_color));
                    imageViewCompetences.setImageResource(R.drawable.icocheckmarkplein_blue);
                    if(filterCategory==null)
                    {
                        filterCategoryId="";
                        filterCategory="";
                        competencesPosition="";
                        imageViewCompetences.setImageResource(R.drawable.pink_like);


                    }
                    filterCategoryId = data.getStringExtra(Keys.COMPETENCES_ID);

                }
                break;
            case Keys.EXPERIENCE_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    experiencePosition = data.getStringExtra(Keys.POSITION);
                    String exp = data.getStringExtra(Keys.EXPERIENCE);
                    filterExperience =exp;
                    tvExperience.setText(exp);
                    tvExperience.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_color));
                    imageViewExperience.setImageResource(R.drawable.icocheckmarkplein_blue);
                    if(filterExperience==null)
                    {
                        filterExperience="";
                        experiencePosition="";
                        filterExperienceId="";
                        imageViewExperience.setImageResource(R.drawable.pink_note);
                    }

                    filterExperienceId = data.getStringExtra(Keys.EXPERIENCEID);

                }
                break;
            case Keys.CURRENT_STATUS_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null)
                {
                    currentStatusPosition = data.getStringExtra(Keys.POSITION);
                    filterActualStatus = data.getStringExtra(Keys.CURRENT_STATUS);
                    tvCurrentStatus.setText(filterActualStatus);
                    tvCurrentStatus.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_color));
                    imageViewCurrentStatus.setImageResource(R.drawable.icocheckmarkplein_blue);

                    if(filterActualStatus==null)
                    {
                        filterActualStatus="";
                        filterActualStatusId="";
                        currentStatusPosition="";
                        imageViewCurrentStatus.setImageResource(R.drawable.pink_note);
                    }
                    filterActualStatusId = data.getStringExtra(Keys.CURRENT_STATUS_ID);

               }
                break;
            case Keys.MOBILITY_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null)
                {
                    mobilityPosition = data.getStringExtra(Keys.POSITION);
                    filterMobility = data.getStringExtra(Keys.MOBILITY);
                    tvMobility.setText(filterMobility);
                    tvMobility.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_color));
                    imageViewMobility.setImageResource(R.drawable.icocheckmarkplein_blue);

                    if(filterMobility==null)
                    {
                        filterMobility="";
                        filterMobilityId="";
                        mobilityPosition="";
                        imageViewMobility.setImageResource(R.drawable.pink_plane);
                    }
                    filterMobilityId = data.getStringExtra(Keys.CURRENT_STATUS_ID);

                }
                break;
            case Keys.LANGUAGE_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    languagePosition = data.getStringExtra(Keys.POSITION);
                    filterLanguage = data.getStringExtra(Keys.LANGUAGE);
                    tvLanguage.setText(filterLanguage);
                    tvLanguage.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_color));
                    imageViewLanguage.setImageResource(R.drawable.icocheckmarkplein_blue);
                    if(filterLanguage==null)
                    {
                        filterLanguage="";
                        filterLanguageId="";
                        languagePosition="";
                        imageViewLanguage.setImageResource(R.drawable.pink_globe);
                    }
                    filterLanguageId = data.getStringExtra(Keys.LANGUAGE_ID);

                     }
                break;
            case 1:
                if (resultCode == RESULT_OK && data != null) {
                    Logger.e(TAG + " :   : " + data.getIntExtra("selects", 0));
                } else {
                    Logger.e(TAG + " :   : getting null");
                }
                break;
        }
    }




/*

    private void serviceForGetData()
    {
        final SpotsDialog spotsDialog = new
                SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();

        retrofit.Call<GetTargetSearchResponce> call = AppRetrofit.
                getAppRetrofitInstance().getApiServices().getGetTargetSearchResponce
                (jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetTargetSearchResponce>() {
            @Override
            public void onResponse(retrofit.Response<GetTargetSearchResponce> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                getCompanyDetailsResponse = response.body();

                if (getCompanyDetailsResponse.isSuccess())
                {
                    initialize();

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
    }*/
}
