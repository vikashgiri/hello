package com.infoicon.bonjob.recruiters.post;


import android.app.Dialog;
import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomEditText;
import com.infoicon.bonjob.customview.CustomRadioButton;
import com.infoicon.bonjob.customview.CustomsButton;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.dialogs.DiplomaDialogFragment;
import com.infoicon.bonjob.dialogs.ExperienceDialogFragment;
import com.infoicon.bonjob.dialogs.LanguageDialogFragment;
import com.infoicon.bonjob.dialogs.NumberOfHoursDialogFragment;
import com.infoicon.bonjob.dialogs.SalaryDialogFragment;
import com.infoicon.bonjob.dialogs.TypeOfContractDialogFragment;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.main.HomeRecruiterActivity;
import com.infoicon.bonjob.multipart.MultipartWebServiceCall;
import com.infoicon.bonjob.permission.MarshmallowPermission;
import com.infoicon.bonjob.permission.PermissionKeys;
import com.infoicon.bonjob.picasso.ImageLoader;
import com.infoicon.bonjob.recruiters.myOffer.OfferTabFragment;
import com.infoicon.bonjob.recruiters.profile.ProfileRecruiterFragment;
import com.infoicon.bonjob.recruiters.search.GetTargetSearchResponce;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.ServiceUrls;
import com.infoicon.bonjob.retrofit.response.GetMyOffersRecruiterResponse;
import com.infoicon.bonjob.seeker.profile.PickLocationActivity;
import com.infoicon.bonjob.servicesConnection.INetworkResponse;
import com.infoicon.bonjob.servicesConnection.WebServiceCall;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.splash.GetAllDropDownResponce;
import com.infoicon.bonjob.utils.AppConstants;
import com.infoicon.bonjob.utils.CommonUtils;
import com.infoicon.bonjob.utils.GPSLocation;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.TakePhoto;
import com.infoicon.bonjob.utils.UtilsMethods;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.soundcloud.android.crop.Crop;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

import static android.app.Activity.RESULT_OK;
import static com.infoicon.bonjob.utils.UtilsMethods.showErrorAlert;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostJobOfferFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private static final int RESULT_LOAD_IMG = 1;
    View rootView;
    @BindView(R.id.tvTitle) CustomsTextViewBold tvTitle;
    @BindView(R.id.tvJobTitle) CustomsTextView tvJobTitle;
    @BindView(R.id.tvAddPhoto) CustomsTextView tvAddPhoto;
    @BindView(R.id.tvJobLocation) CustomsTextView tvJobLocation;
    @BindView(R.id.tvCompleteJobOffer) CustomsTextView tvCompleteJobOffer;
    @BindView(R.id.tvStartdate) CustomsTextView tvStartdate;
    @BindView(R.id.tvDesc) CustomsTextView tvDesc;
    @BindView(R.id.tvModeOfContract) CustomsTextView tvModeOfContract;
    @BindView(R.id.tvCountDesc) CustomsTextView tvCountDesc;
    @BindView(R.id.tvLocationName) CustomsTextView tvLocationName;
    @BindView(R.id.tvDate) CustomsTextView tvDate;
    @BindView(R.id.imageViewPhotoView)
    CircularImageView imageViewPhotoView;
    @BindView(R.id.edDesc) CustomEditText edDesc;
    @BindView(R.id.edJobOffered) public CustomEditText edJobOffered;
    @BindView(R.id.edTelephone) CustomEditText edTelephone;
    @BindView(R.id.edEmail) CustomEditText edEmail;

    @BindView(R.id.rbBonjobApp) CustomRadioButton rbBonjobApp;
    @BindView(R.id.rbPhone) CustomRadioButton rbPhone;
    @BindView(R.id.rbEmail) CustomRadioButton rbEmail;

    @BindView(R.id.imageViewTypeDeContract) ImageView imageViewTypeDeContract;
    @BindView(R.id.tvTypeDeContract) CustomsTextView tvTypeDeContract;
    @BindView(R.id.imageViewEduLevel) ImageView imageViewEduLevel;
    @BindView(R.id.tvEduLevel) CustomsTextView tvEduLevel;
    @BindView(R.id.imageViewExperience) ImageView imageViewExperience;
    @BindView(R.id.tvExperience) CustomsTextView tvExperience;
    @BindView(R.id.imageViewLanguage) ImageView imageViewLanguage;
    @BindView(R.id.tvLanguage) CustomsTextView tvLanguage;
    @BindView(R.id.imageViewNoHours) ImageView imageViewNoHours;
    @BindView(R.id.tvNoHours) CustomsTextView tvNoHours;
    @BindView(R.id.imageViewSalary) ImageView imageViewSalary;
    @BindView(R.id.tvSalary) CustomsTextView tvSalary;
    @BindView(R.id.rgContactMode) RadioGroup rgContactMode;
    @BindView(R.id.rgFlagSelection) RadioGroup rgFlagSelection;
    @BindView(R.id.rbFrenchFlag) RadioButton rbFrenchFlag;
    @BindView(R.id.rbUKingdomFlag) RadioButton rbUKingdomFlag;
    @BindView(R.id.textViewImageName) CustomsTextView textViewImageName;
    private MarshmallowPermission marshmallowPermission;
    @BindView(R.id.tvRemovePhoto) CustomsTextView tvRemovePhoto;

    private String typeOfContractPosition ;
    private String experiencePosition ;
    private String numHourPosition;
    private String languagePosition ;
    private int salPosition = -1;
    private String eduLevelPosition ;
    private String imgDecodeStringPhoto;
    private String imgDecodeString;
    private String txtDiploma = "";
    private String txtDiplomaId = "";
    private String txtSalary = "";
    private String txtSalaryId = "";
    private String txtLanguage = "";
    private String txtLanguageId = "";
    private String txtNumHour = "";
    private String txtNumHourId = "";
    private String txtExperience = "";
    private String txtExperienceId = "";
    private String txtTypeOfContract = "";
    private String txtTypeOfContractId = "";
    public String JobOfferedId = "";
    private String jobId;
    private String jobImage;

    private String loadPage;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_post_job_offer, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onViewCreated(View view,
                              @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initialize();
        //  getCurrentLocation();
        listener();
        getBundles();
    }

    /** get bundle data from job offers and set it's value to field */
    private void getBundles()
    {
        getJobpostDropDown  = Singleton.getUserInfoInstance().getAllDropdowns();

        tvLocationName.setText(Singleton.getUserInfoInstance().getCompanyAddress());
        Bundle bundle = getArguments();
        if (bundle != null)
        {
            ArrayList<GetMyOffersRecruiterResponse.ActiveJobsBean> activeJobsBeanArrayList = bundle.getParcelableArrayList(Keys.JOB_OFFERED);
            if (activeJobsBeanArrayList != null) {
                edJobOffered.setText(activeJobsBeanArrayList.get(0).getJob_title());
                JobOfferedId=activeJobsBeanArrayList.get(0).getJob_title_id();
                tvLocationName.setText(activeJobsBeanArrayList.get(0).getJob_location());
                tvDate.setText(activeJobsBeanArrayList.get(0).getStart_date());
                edDesc.setText(activeJobsBeanArrayList.get(0).getJob_description());
                jobId = activeJobsBeanArrayList.get(0).getJob_id();
                jobImage = activeJobsBeanArrayList.get(0).getJob_image();
                if(jobImage.length()>10)
                {
                    Glide.with(getActivity()).load(jobImage).into(imageViewPhotoView);
                    tvRemovePhoto.setVisibility(View.VISIBLE);
                }
                //type of contact
                txtTypeOfContract = activeJobsBeanArrayList.get(0).getContract_type();

                if (txtTypeOfContract != null && !txtTypeOfContract.equals("")&&
                        !txtTypeOfContract.equalsIgnoreCase("0"))
                {
                    for(int i=0;i<getJobpostDropDown.
                            getContractTypes().size();
                        i++)
                    {
                        if(getJobpostDropDown.getContractTypes().get(i).
                                getContract_id().equalsIgnoreCase(txtTypeOfContract))
                        {
                            txtTypeOfContractId=txtTypeOfContract;
                            txtTypeOfContract=getJobpostDropDown.getContractTypes().get(i).
                                    getContract_title();
                            break;
                        }
                    }
                    completeJobOffer(tvTypeDeContract, imageViewTypeDeContract, txtTypeOfContract);
                }

                //education level
                txtDiploma = activeJobsBeanArrayList.get(0).getEducation_level();
                if (txtDiploma != null && !txtDiploma.equals("") &&
                        !txtDiploma.equalsIgnoreCase("0")) {

                    for(int i=0;i<getJobpostDropDown.getEducationLevels().size()
                            ;i++)
                    {
                        if(getJobpostDropDown.getEducationLevels().get(i).
                                getEducation_id().equalsIgnoreCase(txtDiploma))
                        {
                            txtDiplomaId=txtDiploma;
                            txtDiploma=getJobpostDropDown.getEducationLevels().get(i).
                                    getEducation_title();
                            break;
                        }
                    }

                    completeJobOffer(tvEduLevel, imageViewEduLevel, txtDiploma);
                }

                //experience
                txtExperience = activeJobsBeanArrayList.get(0).getExperience();
                if (txtExperience != null && !txtExperience.equals("")&&
                        !txtExperience.equalsIgnoreCase("0")) {
                    for(int i=0;i<getJobpostDropDown.getExperiences().size()
                            ;i++)
                    {
                        if(getJobpostDropDown.getExperiences().get(i).
                                getExperience_id().equalsIgnoreCase(txtExperience))
                        {
                            txtExperienceId=txtExperience;
                            txtExperience=getJobpostDropDown.getExperiences().get(i).
                                    getExperience();
                            break;
                        }
                    }

                    completeJobOffer(tvExperience, imageViewExperience, txtExperience);
                }

                //language
                txtLanguage = activeJobsBeanArrayList.get(0).getLang();
                if (txtLanguage != null && !txtLanguage.equals("") &&
                        !txtLanguage.equalsIgnoreCase("0")) {
                    for(int i=0;i<getJobpostDropDown.getJobLanguages().size()
                            ;i++)
                    {
                        if(getJobpostDropDown.getJobLanguages().get(i).
                                getJob_language_id().equalsIgnoreCase(txtLanguage))
                        {
                            txtLanguageId=txtLanguage;
                            txtLanguage=getJobpostDropDown.getJobLanguages().get(i).
                                    getJob_language_title();
                            break;
                        }
                    }
                    completeJobOffer(tvLanguage, imageViewLanguage, txtLanguage);
                }

                //number of hour
                txtNumHour = activeJobsBeanArrayList.get(0).getNum_of_hours();
                if (txtNumHour != null && !txtNumHour.equals("") &&
                        !txtNumHour.equalsIgnoreCase("0")) {
                    for(int i=0;i<getJobpostDropDown.getNumberOfHours().size()
                            ;i++)
                    {
                        if(getJobpostDropDown.getNumberOfHours().get(i).
                                getHours_id().equalsIgnoreCase(txtNumHour))
                        {
                            txtNumHourId=txtNumHour;
                            txtNumHour=getJobpostDropDown.getNumberOfHours().get(i).
                                    getHours_title();
                            break;
                        }
                    }

                    completeJobOffer(tvNoHours, imageViewNoHours, txtNumHour);
                }

                //salary
                txtSalary = activeJobsBeanArrayList.get(0).getSalarie();
                if (txtSalary != null && !txtSalary.equals("") &&
                        !txtSalary.equalsIgnoreCase("0")) {
                    for(int i=0;i<getJobpostDropDown.getSalaries().size()
                            ;i++)
                    {
                        if(getJobpostDropDown.getSalaries().get(i).
                                getSalary_id().equalsIgnoreCase(txtSalary))
                        {
                            txtSalaryId=txtSalary;
                            txtSalary=getJobpostDropDown.getSalaries().get(i).
                                    getSalary_title();
                            break;
                        }
                    }
                    completeJobOffer(tvSalary, imageViewSalary, txtSalary);
                }

                //contact mode selection
                String contractMode = activeJobsBeanArrayList.get(0).getContact_mode();
                String[] contactModeArray = contractMode.split(":", 2);
                if (contactModeArray.length > 1) {
                    String modeName = contactModeArray[0];
                    String modeValue = contactModeArray[1];
                    //if (modeName.equals(getResources().getString(R.string.phone))) {
                    if (modeName.equals("Téléphone")) {
                        rbPhone.setChecked(true);
                        onCheckedChanged(rbPhone, true);
                        edTelephone.setText(modeValue);
                    } else if (modeName.equals("Email")) {
                        rbEmail.setChecked(true);
                        onCheckedChanged(rbEmail, true);
                        edEmail.setText(modeValue);
                    } else {
                        rbBonjobApp.setChecked(true);
                        onCheckedChanged(rbBonjobApp, true);
                    }
                } else {
                    rbBonjobApp.setChecked(true);
                    onCheckedChanged(rbBonjobApp, true);
                }
            }
        }
    }

    /** listener */
    private void listener()
    {
        rbBonjobApp.setOnCheckedChangeListener(this);
        rbPhone.setOnCheckedChangeListener(this);
        rbEmail.setOnCheckedChangeListener(this);
        edDesc.addTextChangedListener(textWatcherSpecification);
    }

    /** initialize */
    private void initialize() {
        tvTitle.setText(getResources().getString(R.string.job_offer));
        edJobOffered.setSingleLine(true);
        rbFrenchFlag.setChecked(true);
        marshmallowPermission = new MarshmallowPermission(getActivity());
    }

    /**
     * make selected completed job
     * @param customsTextView textView where text will set and color will change
     * @param imageView       to show checked image
     * @param text            text to show on customsTextView
     */
    private void completeJobOffer(CustomsTextView customsTextView, ImageView imageView, String text) {
        customsTextView.setText(text);
        customsTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.pink_color));
        imageView.setImageResource(R.drawable.pink_check);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeRecruiterActivity) getActivity()).setCheckedToBottom(Keys.POST_JOB);
    }

    /** get back to previous screen */
    @OnClick(R.id.textViewBack)
    void GoBack() {
        (getActivity()).onBackPressed();
    }

    /** open dialog for get type of contract */
    @OnClick(R.id.linearLayoutTypeOfContract)
    void typeOfContract() {
        FragmentManager fm = getFragmentManager();
        TypeOfContractDialogPostJobFragment typeOfContractDialogFragment =
                new TypeOfContractDialogPostJobFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("CompanyDetails",getJobpostDropDown);
        bundle.putString(Keys.SELECTED_POSITION, typeOfContractPosition);
        typeOfContractDialogFragment.setArguments(bundle);
        typeOfContractDialogFragment.setTargetFragment(this, Keys.CONTRACT_TYPE_REQUEST_CODE);
        typeOfContractDialogFragment.show(fm, Keys.TAG_TYPE_OF_CONTRACT);
    }

    /** open dialog for get education level */
    @OnClick(R.id.linearLayoutEduLevel)
    void levelOfEducation() {
        FragmentManager fm = getFragmentManager();
        DiplomaDialogJobPostFragment diplomaDialogFragment = new DiplomaDialogJobPostFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Keys.SELECTED_POSITION, eduLevelPosition);
        bundle.putParcelable("CompanyDetails",getJobpostDropDown);

        diplomaDialogFragment.setArguments(bundle);
        diplomaDialogFragment.setTargetFragment(this, Keys.DIPLOMA_REQUEST_CODE);
        diplomaDialogFragment.show(fm, Keys.TAG_DIPLOMA);
    }

    /** open dialog for get experience */
    @OnClick(R.id.linearLayoutExperience)
    void experience() {
        FragmentManager fm = getFragmentManager();
        ExperienceDialogJobPostFragment experienceDialogFragment = new
                ExperienceDialogJobPostFragment();
        Bundle bundle = new Bundle();
        //
        bundle.putString(Keys.SELECTED_POSITION, experiencePosition);
        bundle.putParcelable("CompanyDetails",getJobpostDropDown);

        experienceDialogFragment.setArguments(bundle);
        experienceDialogFragment.setTargetFragment(this, Keys.EXPERIENCE_REQUEST_CODE);
        experienceDialogFragment.show(fm, Keys.TAG_EXPERIENCE);
    }

    /** open dialog for get language */
    @OnClick(R.id.linearLayoutLanguage)
    void language() {
        FragmentManager fm = getFragmentManager();
        LanguageDialogJobPostFragment languageDialogFragment =
                new LanguageDialogJobPostFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("CompanyDetails",getJobpostDropDown);

        bundle.putString(Keys.SELECTED_POSITION, languagePosition);
        languageDialogFragment.setArguments(bundle);
        languageDialogFragment.setTargetFragment(this, Keys.LANGUAGE_REQUEST_CODE);
        languageDialogFragment.show(fm, Keys.TAG_LANGUAGE);
    }

    /** open dialog for get number of hours */
    @OnClick(R.id.linearLayoutNoHours)
    void noOfHours() {
        FragmentManager fm = getFragmentManager();
        NumberOfHoursJobPostDialogFragment numberOfHoursDialogFragment =
                new NumberOfHoursJobPostDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Keys.SELECTED_POSITION, numHourPosition);
        bundle.putParcelable("CompanyDetails",getJobpostDropDown);

        numberOfHoursDialogFragment.setArguments(bundle);
        numberOfHoursDialogFragment.setTargetFragment(this, Keys.NUM_HOUR_REQUEST_CODE);
        numberOfHoursDialogFragment.show(fm, Keys.TAG_NUM_HOUR);
    }

    /** open dialog for get salary */
    @OnClick(R.id.linearLayoutSalary)
    void salary() {
        FragmentManager fm = getFragmentManager();
        SalaryDialogFragment salaryDialogFragment = new SalaryDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Keys.SELECTED_POSITION, salPosition);
        bundle.putParcelable("CompanyDetails",getJobpostDropDown);

        salaryDialogFragment.setArguments(bundle);
        salaryDialogFragment.setTargetFragment(this, Keys.SALARY_REQUEST_CODE);
        salaryDialogFragment.show(fm, Keys.TAG_SALARY);
    }

    /** pick location */
    @OnClick(R.id.imageViewLocation)
    void getLocation() {
        Intent intent = new Intent(getActivity(), PickLocationActivity.class);
        startActivityForResult(intent, Keys.LOCATION_CODE);
    }

    /** take photo from camera/gallery */
    @OnClick(R.id.relPicture)
    void pickImage() {
        takePhotoOption();
    }

    /** get date from calender */
    @OnClick(R.id.imageViewCalender)
    void openCalender() {
        showCalender();
    }

    /** event for show overview of filling form */
    @OnClick(R.id.btnOverView)
    void overView() {
        if (validateField()) {
            overViewDialog();
        }
    }

    /** event for publish the post */
    @OnClick(R.id.btnPublish)
    void publish()
    {
        if (validateField())
        {
            if (UtilsMethods.isValidForPostJob(getActivity()))
            {
                callServiceForPostJob();
            }
        }
    }

    /** open another screen for getting job offered. */
    @OnClick(R.id.imageViewArrow)
    void selectOfferedJob() {
        ((HomeRecruiterActivity) getActivity()).addFragment(new JobOfferedFragment(this),
                new Bundle(), Keys.JOB_OFFERED, false, true);
    }

    /** text count for description */
    private final TextWatcher textWatcherSpecification = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            tvCountDesc.setText(s.length() + "/150");
        }

        public void afterTextChanged(Editable s) {
        }
    };

    /** method for open dialog post held */
    public void overViewDialog()
    {
        final Dialog dialogOverView = new Dialog(getActivity());
        dialogOverView.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialogOverView.getWindow() != null)
            dialogOverView.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogOverView.setContentView(R.layout.dialog_job_overview);
        ImageView imageViewClose = dialogOverView.findViewById(R.id.imageViewClose);
        ImageView imageViewPost = dialogOverView.findViewById(R.id.imageViewPost);
        CustomsButton btnPublish = dialogOverView.findViewById(R.id.btnPublish);


        LinearLayout linearJobTitle = dialogOverView.findViewById(R.id.linearJobTitle);
        LinearLayout linearContract = dialogOverView.findViewById(R.id.linearContract);
        LinearLayout linearEducation = dialogOverView.findViewById(R.id.linearEducation);
        LinearLayout linearExperience = dialogOverView.findViewById(R.id.linearExperience);
        LinearLayout linearLanguage = dialogOverView.findViewById(R.id.linearLanguage);
        LinearLayout linearNumHours = dialogOverView.findViewById(R.id.linearNumHours);
        LinearLayout linearSalaries = dialogOverView.findViewById(R.id.linearSalaries);
        LinearLayout linearStartDate = dialogOverView.findViewById(R.id.linearStartDate);


        if (UtilsMethods.isEmpty(tvTypeDeContract)) {
            linearContract.setVisibility(View.GONE);
        }
        if (UtilsMethods.isEmpty(tvEduLevel)) {
            linearEducation.setVisibility(View.GONE);
        }
        if (UtilsMethods.isEmpty(tvExperience)) {
            linearExperience.setVisibility(View.GONE);
        }
        if (UtilsMethods.isEmpty(tvLanguage)) {
            linearLanguage.setVisibility(View.GONE);

        }
        if (UtilsMethods.isEmpty(tvNoHours)) {
            linearNumHours.setVisibility(View.GONE);

        }
        if (UtilsMethods.isEmpty(tvSalary)) {

            linearSalaries.setVisibility(View.GONE);
        }
        if (UtilsMethods.isEmpty(tvDate)) {
            linearStartDate.setVisibility(View.GONE);
        }

        CustomsTextViewBold tvJobTitle = dialogOverView.findViewById(R.id.tvJobTitle);
        CustomsTextViewBold tvContract = dialogOverView.findViewById(R.id.tvContract);
        CustomsTextViewBold tvExperience = dialogOverView.findViewById(R.id.tvExperience);
        CustomsTextViewBold tvNoHours = dialogOverView.findViewById(R.id.tvNoHours);
        CustomsTextViewBold tvStartdate = dialogOverView.findViewById(R.id.tvStartdate);
        CustomsTextViewBold tvContact = dialogOverView.findViewById(R.id.tvContact);
        CustomsTextViewBold tvEducationLevel = dialogOverView.findViewById(R.id.tvEducationLevel);
        CustomsTextViewBold tvLanguage = dialogOverView.findViewById(R.id.tvLanguage);
        CustomsTextViewBold tvSalaries = dialogOverView.findViewById(R.id.tvSalaries);

        CustomsTextView tvDesc = dialogOverView.findViewById(R.id.tvDesc);
        CustomsTextViewBold tvLocationNames = dialogOverView.findViewById(R.id.tvLocationName);
        ProgressBar progress_bar = dialogOverView.findViewById(R.id.progress_bar);

        tvJobTitle.setText(edJobOffered.getText().toString().trim());
        tvContract.setText(txtTypeOfContract);
        tvExperience.setText(txtExperience);
        tvNoHours.setText(txtNumHour);
        tvStartdate.setText(tvDate.getText().toString().trim());
        if (!UtilsMethods.isEmpty(edDesc)) {
            tvDesc.setText(edDesc.getText().toString().trim());
        } else {
            tvDesc.setVisibility(View.GONE);
        }

        tvLocationNames.setText(tvLocationName.getText().toString());

        tvEducationLevel.setText(txtDiploma);
        tvLanguage.setText(txtLanguage);
        tvSalaries.setText(txtSalary);
        try {
            JSONObject jsonObject1 = getContactType();
            tvContact.setText(jsonObject1.getString(Keys.VALUE));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //set image to view
        if ((jobImage != null && !jobImage.equals("")) || (imgDecodeStringPhoto != null && !imgDecodeStringPhoto.equals("")))
        {
            //   imageViewPost.setScaleType(ImageView.ScaleType.FIT_XY);
            if (imgDecodeStringPhoto != null && !imgDecodeStringPhoto.equals("")) {
                progress_bar.setVisibility(View.GONE);
                imageViewPost.setImageBitmap(BitmapFactory.decodeFile(imgDecodeStringPhoto));
            } else {
                progress_bar.setVisibility(View.VISIBLE);
                ImageLoader.loadJobImageCallback(getActivity(), jobImage, imageViewPost, progress_bar);
            }
        } else {
            progress_bar.setVisibility(View.GONE);
            // imageViewPost.setScaleType(ImageView.ScaleType.FIT_CENTER);
            //   imageViewPost.setImageBitmap(BitmapFactory.decodeFile(imgDecodeStringPhoto));
        }

        imageViewClose.setOnClickListener(v -> dialogOverView.dismiss());
        btnPublish.setOnClickListener(v -> {
            dialogOverView.dismiss();
            if (validateField()) {
                {
                    if (UtilsMethods.isValidForPostJob(getActivity())) {
                        callServiceForPostJob();
                    }
                }
            }
        });
        dialogOverView.show();
    }


    /** getting result after fetching photo/video from camera/gallery */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Keys.LOCATION_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    stringLatitude = String.valueOf(data.getDoubleExtra
                            (Keys.LOCATION_LAT, 0.0));
                    stringLongitude = String.valueOf(data.getDoubleExtra
                            (Keys.LOCATION_LONG, 0.0));
                    String locationName = data.getStringExtra(Keys.LOCATION_NAME);
                    tvLocationName.setText(locationName);
                }
                break;
            case RESULT_LOAD_IMG:
                if (resultCode == RESULT_OK && data != null) {
                    // Get the Image from data
                    Uri selectedImage = data.getData();
                    String imgDecodeString = UtilsMethods.getImageFromUri(getActivity(), selectedImage);
                    imgDecodeString = UtilsMethods.compressImage(getActivity(), imgDecodeString);
                    Uri destination = Uri.fromFile(new File(UtilsMethods.getCropedPath()));
                    Crop.of(Uri.fromFile(new File(imgDecodeString)), destination).withAspect(AppConstants.ASPECT_RATIO_X, AppConstants.ASPECT_RATIO_Y).start(getActivity());
                }
                break;
            case Keys.TAKE_PHOTO_CODE:
                if (resultCode == RESULT_OK) {
                    imgDecodeString = UtilsMethods.compressImage(getActivity(), imgDecodeString);
                    Uri destination = Uri.fromFile(new File(UtilsMethods.getCropedPath()));
                    //Crop.of(Uri.fromFile(new File(imgDecodeString)), destination).asSquare().start(getActivity());
                    Crop.of(Uri.fromFile(new File(imgDecodeString)), destination).withAspect(AppConstants.ASPECT_RATIO_X, AppConstants.ASPECT_RATIO_Y).start(getActivity());
                }
                break;
            case Keys.POST_CONFIRM_CODE:
                if (resultCode == RESULT_OK) {
                    String loadPage = data.getStringExtra(Keys.VIEW_FRAGMENT);
                    if (loadPage.equals(Keys.PROFILE)) {//load profile
                        ((HomeRecruiterActivity) getActivity()).addFragment(new ProfileRecruiterFragment(), new Bundle(), Keys.PROFILE, false, true);
                    } else {//load offer
                        Bundle bundle = new Bundle();
                        bundle.putInt(Keys.POSITION, 3);
                        ((HomeRecruiterActivity) getActivity()).addFragment(new OfferTabFragment(), bundle, Keys.MY_OFFERS, false, true);
                    }
                }
                break;

            case Keys.CONTRACT_TYPE_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    typeOfContractPosition = data.getStringExtra(Keys.POSITION);
                    txtTypeOfContract = data.getStringExtra(Keys.TYPE_OF_CONTRACT);

                    txtTypeOfContractId = data.getStringExtra(Keys.TYPE_OF_CONTRACT_ID);

                    completeJobOffer(tvTypeDeContract, imageViewTypeDeContract, txtTypeOfContract);
                }
                break;
            case Keys.EXPERIENCE_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    experiencePosition = data.getStringExtra(Keys.POSITION);
                    txtExperience = data.getStringExtra(Keys.EXPERIENCE);
                    txtExperienceId = data.getStringExtra(Keys.EXPERIENCEID);
                    completeJobOffer(tvExperience, imageViewExperience, txtExperience);
                }
                break;
            case Keys.NUM_HOUR_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    numHourPosition = data.getStringExtra(Keys.POSITION);
                    txtNumHour = data.getStringExtra(Keys.NUM_HOUR);
                    txtNumHourId = data.getStringExtra(Keys.NUM_HOUR_ID);
                    completeJobOffer(tvNoHours, imageViewNoHours, txtNumHour);
                }
                break;
            case Keys.LANGUAGE_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    languagePosition = data.getStringExtra(Keys.POSITION);
                    txtLanguage = data.getStringExtra(Keys.LANGUAGE);
                    txtLanguageId = data.getStringExtra(Keys.LANGUAGEID);
                    completeJobOffer(tvLanguage, imageViewLanguage, txtLanguage);
                }
                break;
            case Keys.SALARY_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    salPosition = data.getIntExtra(Keys.POSITION, 0);
                    txtSalary = data.getStringExtra(Keys.SALARY);
                    txtSalaryId = data.getStringExtra(Keys.SALARY_ID);
                    completeJobOffer(tvSalary, imageViewSalary, txtSalary);
                }
                break;
            case Keys.DIPLOMA_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    eduLevelPosition = data.getStringExtra(Keys.POSITION);
                    txtDiploma = data.getStringExtra(Keys.DIPLOMA);
                    txtDiplomaId= data.getStringExtra(Keys.DIPLOMA_ID);
                    completeJobOffer(tvEduLevel, imageViewEduLevel, txtDiploma);
                }
                break;
            case Keys.VERIFY_PHONE:
                if (resultCode == RESULT_OK) {
                    loadPage = data.getStringExtra(Keys.VIEW_FRAGMENT);
                    if (UtilsMethods.isValidForPostJob(getActivity())) {
                        callServiceForPostJob();
                    }
                }
                break;
            case Keys.LOCATION_ENABLE_CODE:
                gettingLocation();
                break;
            case Crop.REQUEST_CROP:
                if (resultCode == RESULT_OK) {
                    imgDecodeStringPhoto = Crop.getOutput(data).getPath();
                    String filename = imgDecodeStringPhoto.substring(imgDecodeStringPhoto.lastIndexOf("/") + 1);
                    textViewImageName.setText(filename);

                    imgDecodeString = Crop.getOutput(data).getPath();
                    tvRemovePhoto.setVisibility(View.VISIBLE);

                    //compress image
                    //imgDecodeString = UtilsMethods.compressImage(getActivity(), imgDecodeString);
                    imageViewPhotoView.setImageURI(Crop.getOutput(data));

                } else if (resultCode == Crop.RESULT_ERROR) {
                    CommonUtils.showToast(getActivity(), Crop.getError(data).getMessage());
                }
                break;
            default:
                break;
        }
    }


    /** remove user photo */
    @OnClick(R.id.tvRemovePhoto)
    void removePhoto() {
        UtilsMethods.removeConfirmation(getActivity(), new UtilsMethods.Callback() {
            @Override
            public void onYes() {
                imageViewPhotoView.setImageResource(R.drawable.default_photo_deactive);
                imgDecodeStringPhoto=null;
                jobImage=null;
                tvRemovePhoto.setVisibility(View.GONE);
            }
        });
    }
    /** after verified phone number redirect to another screen */
    private void afterVerifiedPhoneNumber() {

        if (loadPage.equals(Keys.PROFILE)) {//load profile
            new Thread(() -> ((HomeRecruiterActivity) getActivity()).addFragment(new ProfileRecruiterFragment(), new Bundle(), Keys.PROFILE, false, true)).start();

        } else {//load offer
            new Thread(() -> ((HomeRecruiterActivity) getActivity()).addFragment(new OfferTabFragment(), new Bundle(), Keys.MY_OFFERS, false, true)).start();
        }
    }

    /** checked change listener for change contact mode */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()) {
            case R.id.rbBonjobApp:
                if (isChecked) {
                    edTelephone.setVisibility(View.GONE);
                    edEmail.setVisibility(View.GONE);
                }
                break;
            case R.id.rbPhone:
                if (isChecked) {
                    edTelephone.setVisibility(View.VISIBLE);
                    edEmail.setVisibility(View.GONE);
                }
                break;
            case R.id.rbEmail:
                if (isChecked) {
                    edTelephone.setVisibility(View.GONE);
                    edEmail.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }


    /** method for taking photo */
    public void takePhotoOption() {
        String[] permissions = {PermissionKeys.PERMISSION_CAMERA, PermissionKeys.PERMISSION_WRITE_EXTERNAL_STORAGE};
        if (marshmallowPermission.isPermissionGrantedAll(PermissionKeys.REQUEST_CODE_PERMISSION_ALL_PHOTO, permissions)) {
            new TakePhoto(getActivity(), (imgDecodableStrings, mCurrentPhotoPaths) -> imgDecodeString = imgDecodableStrings).dialogTakePic();
        }
    }


    /** get result back if permission is granted or not. */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionKeys.REQUEST_CODE_WRITE_EXTERNAL_STORAGE_1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhotoOption();
                } else {
                    openAlert(requestCode, getResources().getString(R.string.external_storage_perm_mess));
                }
                break;

            case PermissionKeys.REQUEST_CODE_PERMISSION_ALL:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    takePhotoOption();
                } else {
                    openAlert(requestCode, getResources().getString(R.string.camera_external));
                }
                break;
            case PermissionKeys.REQUEST_CODE_PERMISSION_ALL2: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    gettingLocation();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    UtilsMethods.openAlert(getResources().getString(R.string.message_location_permission_request), getActivity());
                }
                break;
            }
            default:
                break;
        }
    }


    /**
     * Method  to Open Alert/Dialog
     * @param requestCode request code for identify the permission request code
     * @param message     is used as a Dialog Message
     */
    public void openAlert(int requestCode, String message) {
        final Dialog dialog = new Dialog(getActivity());
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_alert);
        dialog.setCancelable(false);
        CustomsTextView textViewTitle = (CustomsTextView) dialog.findViewById(R.id.textViewTitle);
        CustomsTextView textViewMessage = (CustomsTextView) dialog.findViewById(R.id.textViewMessage);
        CustomsButton buttonYes = (CustomsButton) dialog.findViewById(R.id.buttonYes);
        CustomsButton buttonNo = (CustomsButton) dialog.findViewById(R.id.buttonNo);
        textViewTitle.setText(getResources().getString(R.string.app_name));
        textViewMessage.setText(message);
        buttonYes.setOnClickListener(view -> {
            dialog.dismiss();
            switch (requestCode) {
                case PermissionKeys.REQUEST_CODE_WRITE_EXTERNAL_STORAGE_1:
                    takePhotoOption();
                    break;
                case PermissionKeys.REQUEST_CODE_PERMISSION_ALL:
                    takePhotoOption();
                    break;
                default:
                    break;
            }
        });
        buttonNo.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

    /** show date picker dialog */
    private void showCalender() {
        try {
            Dialog dialog_date = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
            dialog_date.setContentView(R.layout.dialog_date);
            DatePicker datePicker = (DatePicker) dialog_date.findViewById(R.id.date_picker);
            CustomsButton btn_okdate = (CustomsButton) dialog_date.findViewById(R.id.btn_ok_date);
            CustomsButton btn_cancelDate = (CustomsButton) dialog_date.findViewById(R.id.btn_cancel_date);
            btn_okdate.setOnClickListener(v -> {
                int year = datePicker.getYear();
                int month = datePicker.getMonth() + 1;
                int day = datePicker.getDayOfMonth();
                tvDate.setText(day + "/" + month + "/" + year);
                dialog_date.dismiss();
            });
            btn_cancelDate.setOnClickListener(v -> dialog_date.dismiss());

            // Calendar calendar = Calendar.getInstance();
            // calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 18);
            //long endOfMonth = calendar.getTimeInMillis();
            //  datePicker.setMaxDate(endOfMonth);
            dialog_date.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** create JsonArray for contact type */
    private JSONObject getContactType() {

        JSONObject jsonObjectContact = new JSONObject();
        int selectedId = rgContactMode.getCheckedRadioButtonId();
        CustomRadioButton customRadioButton = (CustomRadioButton) rootView.findViewById(selectedId);
        String skill = customRadioButton.getText().toString();

        if (rbBonjobApp.isChecked())

        {
            try
            {
                jsonObjectContact.put(Keys.NAME, "BonJob Chat");
                jsonObjectContact.put(Keys.VALUE, skill);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        } else if (rbPhone.isChecked()) {
            try {
                jsonObjectContact.put(Keys.NAME, "Téléphone");
                jsonObjectContact.put(Keys.VALUE, edTelephone.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                jsonObjectContact.put(Keys.NAME, "Email");
                jsonObjectContact.put(Keys.VALUE, edEmail.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObjectContact;
    }

    /** validate field */
    private boolean validateField() {
        if (UtilsMethods.isEmpty(edJobOffered))
        {
            showErrorAlert(getActivity(),getResources().getString(R.string.required));
            requestFocus(edJobOffered);
            return false;
        }

    /*    if (UtilsMethods.isEmpty(textViewImageName)) {
            textViewImageName.setHint(getResources().getString(R.string.required));
            textViewImageName.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.red_color));
            //requestFocus(tvLocationName);
            textViewImageName.requestFocus();
            textViewImageName.clearFocus();
            return false;
        }*/

        if (UtilsMethods.isEmpty(tvLocationName)) {
            tvLocationName.setHint(getResources().getString(R.string.required));
            tvLocationName.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.red_color));
            //requestFocus(tvLocationName);
            tvLocationName.requestFocus();
            tvLocationName.clearFocus();
            return false;
        }



        if (rbEmail.isChecked()) {
            if (edEmail.getText().toString().trim().isEmpty()) {
                showErrorAlert(getActivity(),getResources().getString(R.string.email_empty_message));
                requestFocus(edEmail);
                return false;
            } else {
                if (!UtilsMethods.isValidEmail(edEmail.getText().toString().trim())) {
                    showErrorAlert(getActivity(),getResources().getString(R.string.email_invalid_message));
                    requestFocus(edEmail);
                    return false;
                }
            }
        }

        if (rbPhone.isChecked()) {
            if (edTelephone.getText().toString().trim().isEmpty()) {
                showErrorAlert(getActivity(),getResources().getString(R.string.enter_mobile_number));
                requestFocus(edTelephone);
                return false;
            }
        }

        if (UtilsMethods.isEmpty(tvTypeDeContract)) {
            showErrorAlert(getActivity(), getResources().getString(R.string.select_contract));
            return false;
        }
        /*if (UtilsMethods.isEmpty(tvEduLevel)) {
            CommonUtils.showToast(getActivity(), getResources().getString(R.string.select_education_lvl));
            return false;
        }*/
        if (UtilsMethods.isEmpty(tvExperience)) {
            showErrorAlert(getActivity(), getResources().getString(R.string.select_experience));
            return false;
        }
       /* if (UtilsMethods.isEmpty(tvLanguage)) {
            CommonUtils.showToast(getActivity(), getResources().getString(R.string.select_language));
            return false;
        }*/
        if (UtilsMethods.isEmpty(tvNoHours)) {
            showErrorAlert(getActivity(), getResources().getString(R.string.select_num_hour));
            return false;
        }
        if (UtilsMethods.isEmpty(tvSalary)) {
            showErrorAlert(getActivity(), getResources().getString(R.string.select_salary));
            return false;
        }

      /*  if (UtilsMethods.isEmpty(tvDate)) {
            tvDate.setHint(getResources().getString(R.string.required));
            tvDate.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.red_color));
            //  requestFocus(tvDate);
            tvDate.requestFocus();
            tvDate.clearFocus();
            return false;
        }*/
        return true;
    }


    /**
     * focus to view which view have invalid.
     * @param view focus here
     */
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.ANIMATION_CHANGED);
        }
    }


    /** create json object and multipart in this method. */
    private void callServiceForPostJob()
    {
        JSONObject jsonObject = new JSONObject();
        try {
            if (jobId != null && !jobId.equals(""))
                jsonObject.put(Keys.JOB_ID, jobId);
            else
            jsonObject.put(Keys.USER_ID, Singleton.getUserInfoInstance().getUser_id());
            jsonObject.put(Keys.JOB_TITLE, edJobOffered.getText().toString());
            jsonObject.put(Keys.JOB_TITLE_ID, JobOfferedId);
            jsonObject.put(Keys.JOB_LOCATION, tvLocationName.getText().toString());
            jsonObject.put(Keys.CONTRACT_TYPE, txtTypeOfContractId);
            jsonObject.put(Keys.EDUCATION_LEVEL, txtDiplomaId);
            jsonObject.put(Keys.EXPERIENCE, txtExperienceId);
            jsonObject.put(Keys.POST_LANGUAGE, txtLanguageId);
            jsonObject.put(Keys.NUM_HOURS, txtNumHourId);
            jsonObject.put(Keys.SALALRY, txtSalaryId);
            jsonObject.put(Keys.START_DATE, tvDate.getText().toString().trim());
            jsonObject.put(Keys.JOB_DESC, edDesc.getText().toString().trim());
            jsonObject.put(Keys.CONTACT_MODE, getContactType());
            jsonObject.put(Keys.LATTITUDE, stringLatitude);
            jsonObject.put(Keys.LONGITUDE, stringLongitude);

            jsonObject.put(Keys.job_country, UtilsMethods.country_name);
            jsonObject.put(Keys.job_postal_code, UtilsMethods.postalCode);
            jsonObject.put(Keys.job_city, UtilsMethods.City_name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

     /*   MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        Logger.e("REQUEST JSON :" + jsonObject.toString());
       if (imgDecodeStringPhoto != null && !imgDecodeStringPhoto.equals(""))
        {
            FileBody fileBodyImage = new FileBody(new File(imgDecodeStringPhoto));
            multipartEntityBuilder.addPart(Keys.JOB_IMAGE, fileBodyImage);
            multipartEntityBuilder.addPart(Keys.DATA, new StringBody(jsonObject.toString(), ContentType.TEXT_PLAIN.withCharset("UTF-8")));
            // if job is empty post the job otherwise update post
            if (jobId != null && !jobId.equals(""))
                postJobWithFile(multipartEntityBuilder, ServiceUrls.UPDATE_JOB);
            else postJobWithFile(multipartEntityBuilder, ServiceUrls.POST_JOB);
        }
        else {*/
            if (jobId != null && !jobId.equals(""))
                postJobWithoutFile(jsonObject, ServiceUrls.UPDATE_JOB);
            else postJobWithoutFile(jsonObject, ServiceUrls.POST_JOB);
       // }
    }


    /** service for post job without file */
    private void postJobWithoutFile(JSONObject jsonObjects, String jobPostUrl)
    {
        Logger.e("REQUEST URL :" + jobPostUrl);
        WebServiceCall webServiceCall = new WebServiceCall(getActivity(), new INetworkResponse() {
            @Override
            public void onSuccess(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean(Keys.SUCCESS);
                    String message = jsonObject.getString(Keys.MESSAGE);
                    if (success) {
                        JSONArray jsonArray=jsonObject.getJSONArray(Keys.DATA);
                        JSONObject jsonObject1=((JSONArray) jsonArray).getJSONObject(0);
                        JSONObject sendJsonObject = new JSONObject();

                        sendJsonObject.put(Keys.JOB_ID,
                                ((JSONObject) jsonObject1).getString(Keys.JOB_ID));
                        if (imgDecodeStringPhoto != null && !imgDecodeStringPhoto.equals(""))
                        {
                            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
                            multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                            FileBody fileBodyImage = new FileBody(new File(imgDecodeStringPhoto));
                            multipartEntityBuilder.addPart(Keys.JOB_IMAGE, fileBodyImage);
                            multipartEntityBuilder.addPart(Keys.DATA, new StringBody(sendJsonObject.toString(), ContentType.TEXT_PLAIN.withCharset("UTF-8")));
                            postJobWithFile(multipartEntityBuilder, ServiceUrls.UPDATE_JOB_image);

                        }

                        setJobPostCount();

                        startActivityForResult(new Intent(getActivity(), PostConfirmationActivity.class), Keys.POST_CONFIRM_CODE);
                    } else {
                        if (jsonObject.getString(Keys.ACTIVE_USER).
                                equals(Keys.AUTH_CODE))
                        {
                            UtilsMethods.unAuthorizeAlert(getActivity(), message);
                        } else {
                            CommonUtils.showSimpleMessageBottom(getActivity(), message);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error)
            {
                CommonUtils.showSimpleMessageBottom(getActivity(), error);
            }
        });
        webServiceCall.execute(jsonObjects, jobPostUrl);
    }

    /** service for post job with file */
    private void postJobWithFile(MultipartEntityBuilder multipartEntityBuilder,
                                 String jobPostUrl)
    {
        Logger.e("REQUEST URL :" + jobPostUrl);

       /* final SpotsDialog spotsDialog = new SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();*/
        MultipartWebServiceCall multipartWebServiceCall = new MultipartWebServiceCall(getActivity(),
                new com.infoicon.bonjob.multipart.INetworkResponse() {
            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onError(String error) {

               // spotsDialog.dismiss();
            }
        });
        multipartWebServiceCall.execute(multipartEntityBuilder, jobPostUrl);
    }

    String stringLatitude;
    String stringLongitude ;
    /** get current location to search candidate */
    private void getCurrentLocation()
    {
        if (UtilsMethods.isLocationEnabled(getActivity()))
        {
            String[] permissions = {PermissionKeys.PERMISSION_ACCESS_FINE_LOCATION, PermissionKeys.PERMISSION_ACCESS_COARSE_LOCATION};
            if (marshmallowPermission.isPermissionGrantedAll(PermissionKeys.REQUEST_CODE_PERMISSION_ALL2, permissions)) {
                GPSLocation gpsLocation = new GPSLocation(getActivity());
                double currentLatitude = gpsLocation.getLatitude();
               double  currentLongitude = gpsLocation.getLongitude();
                stringLatitude=String.valueOf(currentLatitude);
                        stringLongitude =String.valueOf(currentLongitude);
                String locationName = gpsLocation.getAddress(currentLatitude, currentLongitude);

                if (!locationName.equals("") && currentLatitude != 0) {
                    Singleton.getCurrentLocationBean().setCurrentAddress(locationName);
                    Singleton.getCurrentLocationBean().setLatitude(currentLatitude);
                    Singleton.getCurrentLocationBean().setLongitude(currentLongitude);
                } else {
                    locationName = Singleton.getCurrentLocationBean().getCurrentAddress();
                }

                Logger.e("Location : " + locationName + " ==LAT==  " + String.valueOf(currentLatitude) + "===LNG==" + String.valueOf(currentLongitude));
                tvLocationName.setText(locationName);
            }
        }
    }

    /** set total job post count */
    private void setJobPostCount()
    {
        //set total job post count
        int postCount = Singleton.getSubscriptionListAndMyPlan().getCurrentPlan().getJob_post_count();
        int totalCount = postCount + 1;
        Singleton.getSubscriptionListAndMyPlan().getCurrentPlan().setJob_post_count(totalCount);
    }

    /** loader to wait for getting location */
    private void gettingLocation()
    {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getResources().getString(R.string.getting_location));
        progressDialog.show();
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            progressDialog.dismiss();
            getCurrentLocation();
        }, 2000);
    }


    GetAllDropDownResponce getJobpostDropDown;
   /* private void serviceForGetJobPostData()
    {
        final SpotsDialog spotsDialog = new
                SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();

        retrofit.Call<GetJobpostDropDown> call = AppRetrofit.
                getAppRetrofitInstance().getApiServices().getGetJobpostDropDown
                (jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetJobpostDropDown>() {
            @Override
            public void onResponse(retrofit.Response<GetJobpostDropDown> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                getJobpostDropDown = response.body();

                if (getJobpostDropDown.isSuccess())
                {
                    getBundles();


                } else {
                    if (getJobpostDropDown.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(getActivity(), getJobpostDropDown.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();

            }
        });
    }
*/

}
