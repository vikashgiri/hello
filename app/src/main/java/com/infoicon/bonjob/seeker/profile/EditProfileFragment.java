package com.infoicon.bonjob.seeker.profile;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomCheckBox;
import com.infoicon.bonjob.customview.CustomEditText;
import com.infoicon.bonjob.customview.CustomRadioButton;
import com.infoicon.bonjob.customview.CustomsButton;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.customview.ToggleButtonGroupTableLayout;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.main.HomeSeekerActivity;
import com.infoicon.bonjob.multipart.MultipartWebServiceCall;
import com.infoicon.bonjob.permission.MarshmallowPermission;
import com.infoicon.bonjob.permission.PermissionKeys;
import com.infoicon.bonjob.picasso.ImageLoader;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.ServiceUrls;
import com.infoicon.bonjob.retrofit.response.GetDeleteGalleryResponse;
import com.infoicon.bonjob.retrofit.response.GetPhotoVideoRemoveResponse;
import com.infoicon.bonjob.retrofit.response.GetSeekerProfileResponse;
import com.infoicon.bonjob.retrofit.response.GetUpdateDiscriptionResponse;
import com.infoicon.bonjob.seeker.adapters.GalleryAdapter;
import com.infoicon.bonjob.seeker.searchJob.PlayVideoFullSreenActivity;
import com.infoicon.bonjob.servicesConnection.INetworkResponse;
import com.infoicon.bonjob.servicesConnection.WebServiceCall;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.splash.GetAllDropDownResponce;
import com.infoicon.bonjob.utils.AppConstants;
import com.infoicon.bonjob.utils.CommonUtils;
import com.infoicon.bonjob.utils.CompressVideo;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.TakePhoto;
import com.infoicon.bonjob.utils.TakeVideo;
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
import java.security.interfaces.DSAKey;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import static android.app.Activity.RESULT_OK;
import static com.infoicon.bonjob.utils.UtilsMethods.showErrorAlert;


@SuppressLint("ValidFragment")
public class EditProfileFragment extends Fragment {

    private static final int RESULT_LOAD_IMG = 1;
    private static final int RESULT_LOAD_VID = 2;
    private final String TAG = this.getClass().getSimpleName();
    private static final int MB_SIZE = 1024;
    private static final int VIDEO_MAX_SIZE = 2048;
    View rootView;
    @BindView(R.id.scrollView) NestedScrollView scrollView;
    @BindView(R.id.tvEducationLevel) public CustomsTextView tvEducationLevel;
    @BindView(R.id.tvjob_sought) public CustomsTextView tvJobSought;

    public String tvEducationLevelId;
    public String tvJobSoughtId;

    @BindView(R.id.linearLanguageContainer) LinearLayout linearLanguageContainer;
    @BindView(R.id.edAbout) EditText edAbout;
    @BindView(R.id.edSpecification) EditText edSpecification;
    @BindView(R.id.tvCountAbout) CustomsTextView tvCountAbout;
    @BindView(R.id.tvCountSpec) CustomsTextView tvCountSpec;
    @BindView(R.id.recyclerViewGallery) RecyclerView recyclerViewGallery;
    @BindView(R.id.textViewBorn) CustomsTextView textViewBorn;
    @BindView(R.id.textViewExperience) CustomsTextView textViewExperience;
    @BindView(R.id.imageViewPhotoView) CircularImageView imageViewPhotoView;
    @BindView(R.id.imageViewPitchVideoView) CircularImageView imageViewPitchVideoView;
    @BindView(R.id.imageViewGallery) ImageView imageViewGallery;
    @BindView(R.id.edFName) EditText edFName;
    @BindView(R.id.edName) EditText edName;
    @BindView(R.id.tvLocation) CustomsTextView tvLocation;
    // @BindView(R.id.radGroupActualStatus) ToggleButtonGroupTableLayout radGroupActualStatus;
    ToggleButtonGroupTableLayout radGroupActualStatus;
    @BindView(R.id.rbStudent) CustomRadioButton rbStudent;

    @BindView(R.id.rbAppretice) CustomRadioButton rbAppretice;
    @BindView(R.id.rbActive) CustomRadioButton rbActive;
    @BindView(R.id.rbJobSeeker) CustomRadioButton rbJobSeeker;
    @BindView(R.id.rbInActive) CustomRadioButton rbInActive;

    @BindView(R.id.rgMobility) RadioGroup rgMobility;
    @BindView(R.id.rbYes) CustomRadioButton rbYes;
    @BindView(R.id.rbNo) CustomRadioButton rbNo;

    @BindView(R.id.cbCuisin) CustomCheckBox cbCuisin;
    @BindView(R.id.cbSalle) CustomCheckBox cbSalle;
    @BindView(R.id.cbHotteler) CustomCheckBox cbHotteler;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    @BindView(R.id.tvRemovePhoto) CustomsTextView tvRemovePhoto;
    @BindView(R.id.tvRemoveVideo) CustomsTextView tvRemoveVideo;
    @BindView(R.id.imageViewPlayIcon) ImageView imageViewPlayIcon;

    private MarshmallowPermission marshmallowPermission;

    private String mCurrentVideoPath;
    private String imgDecodeString;
    private String imgDecodeStringForGallery;
    private String vidDecodeString;
    private MultipartEntityBuilder multipartEntityBuilder;
    private FileBody fileBodyImage;
    private boolean willCallService = false;
    private List<GetSeekerProfileResponse.DataBean.ExperienceBean> experienceBeanList;
    private ProfileFragment profileFragment;
    private GetSeekerProfileResponse.DataBean dataBean;
    private GalleryAdapter galleryAdapter;
    private String fromPage;
    private String dob = "";

    private boolean onActivityUserPhotoCall;
    private boolean onActivityPitchVideoCall;
    private String seekerLat, seekerLng;
    private String pitchVideoThumbNail;
private int radioId=-1;
boolean mobilityCheck=true,skillsCheck=true;
    private String urlUserPic = "";
    private String urlPatchVideo = "";
    private Set<String> selectedLanguage;
private  int skillsId;
    @SuppressLint("ValidFragment")
    public EditProfileFragment(ProfileFragment profileFragment) {
        this.profileFragment = profileFragment;
    }
    CustomRadioButton rgSkillcustomRadioButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        ButterKnife.bind(this, rootView);
      //  serviceForGetlanguageSelectionData();
        initialize();
        setTypeFace();
        listener();
        getBundleData();
        rgMobility.setOnCheckedChangeListener(new
                                                               RadioGroup.
                                OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioId = radioGroup.getCheckedRadioButtonId();

                if (radioId == -1) {
                    return;
                }
                mobilityCheck = true;

                rootView.findViewById(radioId).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (mobilityCheck) {
                            mobilityCheck = false;
                        } else {
                            rgMobility.clearCheck();
                            mobilityCheck = true;
                        }
                    }
                });
            }
        });
        return rootView;
    }

    /** get bundle data from profile screen. */
    private void getBundleData()
    {
        dataBean = profileFragment.getSeekerProfileResponse.getData();
        if (dataBean != null) {

            edFName.setText(dataBean.getFirst_name());
            edName.setText(dataBean.getLast_name());
            if (dataBean.getDob() != null) {
                if (!dataBean.getDob().equalsIgnoreCase("0000-00-00")) {
                    dob = dataBean.getDob();
                    textViewBorn.setText(UtilsMethods.convertDateToYYMMDD(dob));
                }
            }

            if (dataBean.getCity() != null)
                tvLocation.setText(dataBean.getCity());

            if (dataBean.getUser_pic() != null && !dataBean.getUser_pic().equals(""))
            {
                urlUserPic = dataBean.getUser_pic();
                ImageLoader.loadImage(getActivity(), urlUserPic, imageViewPhotoView);
                tvRemovePhoto.setVisibility(View.VISIBLE);
            }

            if (dataBean.getPatch_video_thumbnail() != null && !dataBean.getPatch_video_thumbnail().equals("") &&
                dataBean.getPatch_video() != null && !dataBean.getPatch_video().equals("")) {
                urlPatchVideo = dataBean.getPatch_video();
                ImageLoader.loadImage(getActivity(), dataBean.getPatch_video_thumbnail(), imageViewPitchVideoView);
                tvRemoveVideo.setVisibility(View.VISIBLE);
                imageViewPlayIcon.setVisibility(View.VISIBLE);
            }

            tvEducationLevel.setText(dataBean.getEducation_level_name());
            tvEducationLevelId=dataBean.getEducation_level();
            tvJobSought.setText(dataBean.getCandidate_seek_name());
            tvJobSoughtId=dataBean.getCandidate_seek_id();
            edSpecification.setText(dataBean.getTraining());
            //training
            if (dataBean.getTraining() != null)
                tvCountSpec.setText(dataBean.getTraining().length() + "/100");
            //about
            edAbout.setText(dataBean.getAbout());
            if (dataBean.getAbout() != null)
                tvCountAbout.setText(dataBean.getAbout().length() + "/200");

            //set value for mobility
            String mobility = "";
            if (dataBean.getMobility_name() != null)
            {
                         mobility = dataBean.getMobility_name();
            }
            rgMobility.clearCheck();
            if (mobility.equalsIgnoreCase(getResources().getString(R.string.profilefirstcell_yes)))
            {
                rbYes.setChecked(true);
                mobilityCheck = false;
            }
            if (mobility.equalsIgnoreCase(getResources().getString(R.string.profilesecondcell_no)))
            {
                rbNo.setChecked(true);
                mobilityCheck = false;
            }

            //set skill value
           // rgSkill.clearCheck();

            if (!dataBean.getSkills().isEmpty())
            {
                for (int i = 0; i < dataBean.getSkills().size(); i++) {
                    String skills = dataBean.getSkills().get(i).getSkills();
                    if (skills.equalsIgnoreCase("1")) {
                        cbCuisin.setChecked(true);
                    } else if (skills.equalsIgnoreCase("2")) {
                        cbSalle.setChecked(true);
                    } else if (skills.equalsIgnoreCase("3")) {
                        cbHotteler.setChecked(true);
                        //  skillsCheck = false;
                    }
                }
            }
            if (dataBean.getLattitude() != null) {
                seekerLat = dataBean.getLattitude();
                seekerLng = dataBean.getLongitude();
            }

            // set current status
            String current_status = "";
            if (dataBean.getCurrent_status() != null)
            {
                current_status = dataBean.getCurrent_status();
            }
            if (current_status.equalsIgnoreCase("2"))
            {
                radGroupActualStatus.onClick(rbAppretice);
            }
            else if (current_status.equalsIgnoreCase("3"))
            {
                radGroupActualStatus.onClick(rbActive);
            }
            else if (current_status.equalsIgnoreCase("4"))
            {
                radGroupActualStatus.onClick(rbJobSeeker);
            }
            else if (current_status.equalsIgnoreCase("5"))
            {
                radGroupActualStatus.onClick(rbInActive);
            }
            if (current_status.equalsIgnoreCase("1"))
            {
                radGroupActualStatus.onClick(rbStudent);
            }

            //get experience data
          /*  if (dataBean.getExperience().size()>0 ||
                    !dataBean.getExperience().isEmpty() ||
                    !dataBean.getExperience().get(0).getExperience().
                            equalsIgnoreCase("1"))*/
            if (dataBean.getExperience() != null
                    &&
                    !dataBean.getExperience().get(0).
                            getExperience().equalsIgnoreCase("1"))

            {
                experienceBeanList = dataBean.getExperience();
                textViewExperience.setText(experienceBeanList.get(0).getPosition_held_name());
            }
            else
                {
                textViewExperience.setText(getString(R.string.
                        no_company_added));
            }

            //set value of language
            if (dataBean.getLanguages() != null)
            {
                if (dataBean.getLanguages().size() > 0)
                {
                    for (int i = 0; i < dataBean.getLanguages().size(); i++) {
                        if (!selectedLanguage.contains(dataBean.getLanguages().
                                get(i).getSeeker_lang_name())) {
                            addViewLanguage(true, i);
                        }
                        addLanguage(dataBean.getLanguages().get(i).getSeeker_lang_name());
                    }
                }
                else
                    {
                    addViewLanguage(false, 0);
                }
            }
            else{
                addViewLanguage(false, 0);
            }

            if (galleryAdapter != null) {
                if (dataBean.getGallery() != null && dataBean.getGallery().size() > 0) {
                    for (int i = 0; i < dataBean.getGallery().size(); i++) {
                        galleryAdapter.addPhoto(dataBean.getGallery().get(i));
                    }
                }
            }
        }
    }

    /** add language */
    private void addLanguage(String language) {
        selectedLanguage.add(language);

        Iterator itr = selectedLanguage.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
    }

    /** remove language */
    private void removeLanguage(String language) {
        selectedLanguage.remove(language);

        Iterator itr = selectedLanguage.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
    }

    /**
     * receive status if any changes in profile update
     */
    BroadcastReceiver broadcastReceiverProfileUpdate = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO: 1/2/17 Get music related information.
            if (getActivity() != null && isAdded())
                getBundleData();
        }
    };

    /** listener */
    private void listener() {
        edSpecification.addTextChangedListener(textWatcherSpecification);
        edAbout.addTextChangedListener(textWatcherAbout);
    }

    @Override
    public void onDestroy()
    {
        Logger.e(TAG + " : " + "unregister receiver");
        if (broadcastReceiverProfileUpdate != null)
            getActivity().unregisterReceiver(broadcastReceiverProfileUpdate);

        UtilsMethods.deleteAllBonjobCreatedFile();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.e(TAG + " : " + "register receiver");
        ((HomeSeekerActivity) getActivity()).setCheckedToBottom(Keys.PROFILE);
        getActivity().registerReceiver(broadcastReceiverProfileUpdate, new IntentFilter(Keys.EDIT_PROFILE));
    }


    /** set typeface to editText */
    private void setTypeFace()
    {
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "HelveticaNeueLTCom-LtEx.ttf");
        edFName.setTypeface(font);
        edName.setTypeface(font);
    }

    /** initialization */
    private void initialize() {
        getLanguageResponce=Singleton.getUserInfoInstance().getAllDropdowns();
        marshmallowPermission = new MarshmallowPermission(getActivity());
        experienceBeanList = new ArrayList<>();
        selectedLanguage = new HashSet<>();
        radGroupActualStatus = (ToggleButtonGroupTableLayout) rootView.findViewById(R.id.radGroupActualStatus);

        tvRemovePhoto.setPaintFlags(tvRemovePhoto.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvRemoveVideo.setPaintFlags(tvRemoveVideo.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        //gallery
        GridLayoutManager lLayout = new GridLayoutManager(getActivity(), 3);
        recyclerViewGallery.setHasFixedSize(true);
        recyclerViewGallery.setLayoutManager(lLayout);
        galleryAdapter = new GalleryAdapter(getActivity(), EditProfileFragment.this);
        recyclerViewGallery.setAdapter(galleryAdapter);
    }

    /** event for go back page */
    @OnClick(R.id.textViewBack)
    void GoBack() {
        (getActivity()).onBackPressed();
    }

    @OnClick(R.id.imgViewExperience)
    void proceedToUserExperiencePage() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Keys.EXPERIENCE,
                (ArrayList<GetSeekerProfileResponse.DataBean.ExperienceBean>)
                        experienceBeanList);
        ((HomeSeekerActivity) getActivity()).addFragment(new ExperienceFragment(),
                bundle, Keys.EXPERIENCE, false, true);
    }

    @OnClick(R.id.textViewBorn )
    void openCalender() {
        showCalender();
        // getDates();

        /*FragmentManager fm = getFragmentManager();
        DatePickFragment companyCategoryDialogFragment = new DatePickFragment();
        companyCategoryDialogFragment.show(fm, Keys.TAG_COMPANY_CATEGORY);*/

    }

    /** event for take photo */
    @OnClick(R.id.imageViewPhoto)
    void takePhoto() {
        takePhotoOption("");
    }

    /** event for take video */
    @OnClick(R.id.imageViewPitchVideo)
    void takeVideo() {
        if (!Singleton.getUserInfoInstance().isPitchInfoView()) {
            Singleton.getUserInfoInstance().setPitchInfoView(true);
            UtilsMethods.dialogPitchVideInfo(getActivity());
        } else {
            takeVideoOption();
        }
    }

    /** event for make gallery visible */
    @OnClick(R.id.imageViewGallery)
    void openGallery() {
        recyclerViewGallery.setVisibility(View.VISIBLE);
        new Handler().postDelayed(() -> {
            int scrollTo = ((View) recyclerViewGallery.getParent().getParent()).getBottom() + recyclerViewGallery.getBottom();
            scrollView.smoothScrollTo(0, scrollTo);
        }, 500);
    }

    /** event for save edited profile */
    @OnClick(R.id.tvSave)
    void save() {
        getLanguageArray();
    }


    /** event for proceed to next screen education level */
    @OnClick(R.id.tvEducationLevel)
    void getEducationLevel() {
        ((HomeSeekerActivity) getActivity()).
                addFragment(new EducationLevelFragment(EditProfileFragment.this),
                        new Bundle(), Keys.EDUCATION_LEVEL, false, true);
    }


    /** event for proceed to next screen education level */
    @OnClick(R.id.tvjob_sought)
    void getJobSoughts() {
        ((HomeSeekerActivity) getActivity()).addFragment(new
                JobSoughtFragment(EditProfileFragment.this), new Bundle(),
                Keys.EDUCATION_LEVEL, false, true);
    }
    /** event for pick location */
    @OnClick(R.id.tvLocation)
    void pickLocation() {
        Intent intent = new Intent(getActivity(), PickLocationActivity.class);
        startActivityForResult(intent, Keys.LOCATION_CODE);
    }

    /** remove user photo */
    @OnClick(R.id.tvRemovePhoto)
    void removePhoto() {
        UtilsMethods.removeConfirmation(getActivity(), new UtilsMethods.Callback() {
            @Override
            public void onYes() {
                removePhotoVideo(Keys.USER_PIC);
            }
        });
    }

    /** remove patch video */
    @OnClick(R.id.tvRemoveVideo)
    void removeVideo() {
        UtilsMethods.removeConfirmation(getActivity(), new UtilsMethods.Callback() {
            @Override
            public void onYes() {
                removePhotoVideo(Keys.PATCH_VIDEO);
            }
        });
    }


    @OnClick(R.id.imageViewPhotoView)
    void viewPhoto() {
        if (onActivityUserPhotoCall || !urlUserPic.equals(""))
            dialogViewPhoto();
    }

    @OnClick(R.id.imageViewPitchVideoView)
    void viewPatchVideo() {
        if (onActivityPitchVideoCall || !urlPatchVideo.equals(""))
            dialogViewVideo();
    }


    /** method for taking photo */
    public void takePhotoOption(String from) {
        fromPage = from;
        String[] permissions = {PermissionKeys.PERMISSION_CAMERA, PermissionKeys.PERMISSION_WRITE_EXTERNAL_STORAGE};
        if (marshmallowPermission.isPermissionGrantedAll(PermissionKeys.REQUEST_CODE_PERMISSION_ALL_PHOTO, permissions)) {
            new TakePhoto(getActivity(), new TakePhoto.CallbackTakePhoto() {
                @Override
                public void getPath(String imgDecodableStrings, String mCurrentPhotoPaths) {
                    if (from.equals(Keys.FROM_GALLERY_ADAPTER)) {
                        imgDecodeStringForGallery = imgDecodableStrings;
                    } else {
                        onActivityUserPhotoCall = false;
                        imgDecodeString = imgDecodableStrings;
                    }
                }
            }).dialogTakePic();
        }
    }

    /** method for get video from camera/gallery */
    private void takeVideoOption() {
        String[] permissions = {PermissionKeys.PERMISSION_CAMERA, PermissionKeys.PERMISSION_WRITE_EXTERNAL_STORAGE};
        if (marshmallowPermission.isPermissionGrantedAll(PermissionKeys.REQUEST_CODE_PERMISSION_ALL_VIDEO, permissions)) {
            new TakeVideo(getActivity(), new TakeVideo.CallbackTakeVideo() {
                @Override
                public void getPath(String vidDecodableStrings, String mCurrentVideoPaths) {
                    vidDecodeString = vidDecodableStrings;
                    mCurrentVideoPath = mCurrentVideoPaths;
                    onActivityPitchVideoCall = false;
                }
            }).dialogTakeVideo();
        }
    }


    /**
     * load image from gallery
     * Create intent to Open Image applications like Gallery, Google Photos
     */
    private void loadImagefromGallery() {
        if (marshmallowPermission.isPermissionGranted(PermissionKeys.PERMISSION_WRITE_EXTERNAL_STORAGE,
                PermissionKeys.REQUEST_CODE_WRITE_EXTERNAL_STORAGE_1))
        {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
        }
    }

    /**
     * load video from gallery
     * Create intent to Open video applications like Gallery, Google Photos
     */
    private void loadVideofromGallery() {
        if (marshmallowPermission.isPermissionGranted(PermissionKeys.PERMISSION_WRITE_EXTERNAL_STORAGE, PermissionKeys.REQUEST_CODE_WRITE_EXTERNAL_STORAGE_2)) {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, RESULT_LOAD_VID);
        }
    }


    /** get result back if permission is granted or not. */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionKeys.REQUEST_CODE_WRITE_EXTERNAL_STORAGE_1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadImagefromGallery();
                } else {
                    openAlert(requestCode, getResources().getString(R.string.external_storage_perm_mess));
                }
                break;
            case PermissionKeys.REQUEST_CODE_WRITE_EXTERNAL_STORAGE_2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadVideofromGallery();
                } else {
                    openAlert(requestCode, getResources().getString(R.string.external_storage_perm_mess));
                }

                break;
            case PermissionKeys.REQUEST_CODE_PERMISSION_ALL_PHOTO:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    takePhotoOption(fromPage);
                } else {
                    openAlert(requestCode, getResources().getString(R.string.camera_external));
                }
                break;
            case PermissionKeys.REQUEST_CODE_PERMISSION_ALL_VIDEO:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    takeVideoOption();
                } else {
                    openAlert(requestCode, getResources().getString(R.string.camera_external));
                }
                break;
            default:
                break;
        }
    }

    /** get intent data from other application. */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_LOAD_IMG:
                if (resultCode == RESULT_OK && data != null) {
                    // Get the Image from data
                    Uri selectedImage = data.getData();
                    if (fromPage.equals(Keys.FROM_GALLERY_ADAPTER)) {
                        imgDecodeStringForGallery = UtilsMethods.getImageFromUri(getActivity(), selectedImage);
                        imgDecodeStringForGallery = UtilsMethods.compressImage(getActivity(), imgDecodeStringForGallery);
                        Uri destination = Uri.fromFile(new File(UtilsMethods.getCropedPath()));
                        //   Crop.of(selectedImage, destination).asSquare().start(getActivity());
                        Crop.of(Uri.fromFile(new File(imgDecodeStringForGallery)), destination).asSquare().start(getActivity());
                        // uploadGallery("", "");
                    } else {
                        imgDecodeString = UtilsMethods.getImageFromUri(getActivity(), selectedImage);
                        imgDecodeString = UtilsMethods.compressImage(getActivity(), imgDecodeString);
                        // imageViewPhotoView.setImageBitmap(BitmapFactory.decodeFile(imgDecodeString));
                        Uri destination = Uri.fromFile(new File(UtilsMethods.getCropedPath()));
                        //   Crop.of(selectedImage, destination).asSquare().start(getActivity());

                        Crop.of(Uri.fromFile(new File(imgDecodeString)), destination).asSquare().start(getActivity());
                    }
                }
                break;
            case RESULT_LOAD_VID:
                if (resultCode == RESULT_OK && data != null) {
                    // Get the Image from data
                    Uri selectedVideo = data.getData();
                    vidDecodeString = UtilsMethods.getVideoFromUri(getActivity(), selectedVideo);

                    pitchVideoThumbNail = UtilsMethods.getThumbnailPathForLocalFile(getActivity(), selectedVideo);
                    Logger.e("Thumbnail Path : " + pitchVideoThumbNail);

                    mCurrentVideoPath = "file:" + vidDecodeString;
                    long dur = getVideoDuration(vidDecodeString);
                    if (dur > 60) {
                        vidDecodeString = null;
                        UtilsMethods.openCustumAlert(getActivity(), getResources().getString(R.string.video_length_message));
                    } else {
                        Bitmap bmThumbnail = ThumbnailUtils.createVideoThumbnail(vidDecodeString, MediaStore.Video.Thumbnails.MICRO_KIND);
                        imageViewPitchVideoView.setImageBitmap(bmThumbnail);

                        // vidDecodeString = outpath;
                       callServiceDataForEditProfileImages("video");

                      //  compressingVideo();
                    }
                }

                break;
            case Keys.LOCATION_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    String locationName = data.getStringExtra(Keys.LOCATION_NAME);
                    seekerLat = String.valueOf(data.getDoubleExtra(Keys.LOCATION_LAT, 0.0));
                    seekerLng = String.valueOf(data.getDoubleExtra(Keys.LOCATION_LONG, 0.0));
                    tvLocation.setText(locationName);
                }
                break;
            case Keys.TAKE_PHOTO_CODE:
                if (resultCode == RESULT_OK)
                {
                    if (fromPage.equals(Keys.FROM_GALLERY_ADAPTER))
                    {

                        imgDecodeStringForGallery = UtilsMethods.compressImage(getActivity(), imgDecodeStringForGallery);
                        Uri destination = Uri.fromFile(new File(UtilsMethods.getCropedPath()));
                        Crop.of(Uri.fromFile(new File(imgDecodeStringForGallery)), destination).asSquare().start(getActivity());
                        // imageEdit();
                    }
                    else
                        {
                        imgDecodeString = UtilsMethods.compressImage(getActivity(), imgDecodeString);
                        //  imageViewPhotoView.setImageBitmap(BitmapFactory.decodeFile(imgDecodeString));
                        Uri destination = Uri.fromFile(new File(UtilsMethods.getCropedPath()));
                        Crop.of(Uri.fromFile(new File(imgDecodeString)), destination).asSquare().start(getActivity());
                    }
                }
                break;
            case Keys.TAKE_VIDEO_CODE:
                if (resultCode == RESULT_OK)
                {
                    File file = new File(vidDecodeString);
                    long length = file.length();
                    length = length / MB_SIZE;
                    Logger.e(TAG + " :   File Path : " + file.getPath() + ",check File size : " + length + " KB");

                    Uri videoUri = Uri.parse(mCurrentVideoPath);
                    Bitmap bmThumbnail = ThumbnailUtils.createVideoThumbnail(videoUri.getPath(), MediaStore.Video.Thumbnails.MICRO_KIND);
                    pitchVideoThumbNail = UtilsMethods.saveImageToInternalStorage(getActivity(), bmThumbnail);
                    Logger.e("Thumbnail take video2 : " + pitchVideoThumbNail);
                    Bitmap bmThumbnails = ThumbnailUtils.createVideoThumbnail(vidDecodeString, MediaStore.Video.Thumbnails.MICRO_KIND);
                    imageViewPitchVideoView.setImageBitmap(bmThumbnails);

                    callServiceDataForEditProfileImages("video");

                    //  compressingVideo();

                   /* if (length > VIDEO_MAX_SIZE) {
                        compressingVideo();
                    } else {
                        // Uri videoUri = Uri.parse(mCurrentVideoPath);
                        //  Bitmap bmThumbnail = ThumbnailUtils.createVideoThumbnail(videoUri.getPath(), MediaStore.Video.Thumbnails.MICRO_KIND);
                        imageViewPitchVideoView.setImageBitmap(bmThumbnail);
                        onActivityPitchVideoCall = true;
                        tvRemoveVideo.setVisibility(View.GONE);
                    }*/
                }
                break;

            case Crop.REQUEST_CROP:
                if (resultCode == RESULT_OK) {
                    imgDecodeStringForGallery= Crop.getOutput(data).getPath();

                    if (fromPage.equals(Keys.FROM_GALLERY_ADAPTER)) {
                        uploadGallery("", "");
                    } else {
                        onActivityUserPhotoCall = true;
                        tvRemovePhoto.setVisibility(View.GONE);
                        imgDecodeString = Crop.getOutput(data).getPath();
                        //compress image
                        //imgDecodeString = UtilsMethods.compressImage(getActivity(), imgDecodeString);
                        imageViewPhotoView.setImageURI(Crop.getOutput(data));
                        callServiceDataForEditProfileImages("image");
                    }

                } else if (resultCode == Crop.RESULT_ERROR) {
                    CommonUtils.showToast(getActivity(), Crop.getError(data).getMessage());
                }
                break;
            default:
                break;
        }
    }

    /** compressing video */
    private void compressingVideo() {
        new CompressVideo(getActivity(), vidDecodeString, (length, outpath) -> {
            Logger.e(TAG + " Video uploaded path  : " + outpath + ", File size : " + length + " KB");

            if (length > AppConstants.VIDEO_MAX_SIZE) {
                vidDecodeString = null;
                imageViewPitchVideoView.setImageResource(R.drawable.play_icon);
                openAlert(getResources().getString(R.string.video_size_message));
            } else {
                vidDecodeString = outpath;
                onActivityPitchVideoCall = true;
                tvRemoveVideo.setVisibility(View.GONE);
                imageViewPlayIcon.setVisibility(View.VISIBLE);
                Uri videoUri = Uri.parse(vidDecodeString);
                CommonUtils.showToast(getActivity(), getResources().getString(R.string.compression_success));
                Bitmap bmThumbnail = ThumbnailUtils.createVideoThumbnail(videoUri.getPath(), MediaStore.Video.Thumbnails.MICRO_KIND);
                imageViewPitchVideoView.setImageBitmap(bmThumbnail);
              //  callServiceDataForEditProfileImages("video");
            }
        }).compress(getActivity());
    }

    /** get duration form video path */
    private long getVideoDuration(String vidDecodableString) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(vidDecodableString);
        String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        long timeInMilliSec = Long.parseLong(time);
        return timeInMilliSec / 1000;

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
                dob = year + "-" + month + "-" + day;
                textViewBorn.setText(day + "-" + month + "-" + year);
                dialog_date.dismiss();
            });
            btn_cancelDate.setOnClickListener(v -> dialog_date.dismiss());

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 18);
            long endOfMonth = calendar.getTimeInMillis();
            datePicker.setMaxDate(endOfMonth);
            dialog_date.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * add dynamic view for add multiple language
     * @param isAddBundleData if data is coming from bundle then data will autofill in the field.
     * @param pos             if isAddedBundle is true then pos is for getting the data from the list.
     *                        http://android-er.blogspot.in/2013/05/add-and-remove-view-dynamically.html
     */

    public void addViewLanguage(boolean isAddBundleData, int pos)
    {
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(R.layout.row_language, null);
        CustomsButton btnAddLanguage = (CustomsButton) addView.findViewById(R.id.btnAddLanguage);
        CustomsButton btnRemove = (CustomsButton) addView.findViewById(R.id.btnRemove);
        CustomsTextView tvLanguage = (CustomsTextView) addView.findViewById(R.id.tvLanguage);
        CustomsTextView tvSelectLanguage = (CustomsTextView) addView.findViewById(R.id.tvSelectLanguage);
        ToggleButtonGroupTableLayout groupTableLayout = (ToggleButtonGroupTableLayout) addView.findViewById(R.id.radGroup1);

        CustomRadioButton rbBeginner = (CustomRadioButton) addView.findViewById(R.id.rbBeginner);
        CustomRadioButton rbIntermediate = (CustomRadioButton) addView.findViewById(R.id.rbIntermediate);
        CustomRadioButton rbAdvanced = (CustomRadioButton) addView.findViewById(R.id.rbAdvanced);
        CustomRadioButton rbCurrent = (CustomRadioButton) addView.findViewById(R.id.rbCurrent);
        /*rbBeginner.setText(getLanguageResponce.getLanguageProficiencies()
        .get(0).getLanguage_proficiency_title());*/
        for(int i=0;i<getLanguageResponce.getLanguageProficiencies().size();
                i++)
        {
            if(i==0)
            rbBeginner.setText(getLanguageResponce.getLanguageProficiencies().
                    get(i).getLanguage_proficiency_title());
            if(i==1)
                rbIntermediate.setText(getLanguageResponce.getLanguageProficiencies().
                        get(i).getLanguage_proficiency_title());

                if(i==2)
                    rbAdvanced.setText(getLanguageResponce.getLanguageProficiencies().
                            get(i).getLanguage_proficiency_title());

                    if(i==3)
                        rbCurrent.setText(getLanguageResponce.getLanguageProficiencies().
                                get(i).getLanguage_proficiency_title());
        }
       // groupTableLayout.onClick(rbBeginner);
        if (isAddBundleData) {
            tvLanguage.setText(dataBean.getLanguages().get(pos).getSeeker_lang());
            String lang_proficiency = dataBean.getLanguages().get(pos).
                    getLang_proficiency();
            if (lang_proficiency.equalsIgnoreCase
                    ("2")) {
                groupTableLayout.onClick(rbIntermediate);
            } else if (lang_proficiency.equalsIgnoreCase
                    ("3")) {
                groupTableLayout.onClick(rbAdvanced);
            } else if (lang_proficiency.equalsIgnoreCase("4"))
            {
                groupTableLayout.onClick(rbCurrent);
            }
            else {
                groupTableLayout.onClick(rbBeginner);
            }
        }

        if (linearLanguageContainer.getChildCount() > 0) {
            btnRemove.setVisibility(View.VISIBLE);
            btnAddLanguage.setVisibility(View.GONE);
        } else {
            btnRemove.setVisibility(View.GONE);
        }

        //remove view
        btnRemove.setOnClickListener(v -> {
            ((LinearLayout) addView.getParent()).removeView(addView);

            if (!UtilsMethods.isEmpty(tvLanguage)) {
                removeLanguage(tvLanguage.getText().toString().trim());
            }

            if (linearLanguageContainer.getChildCount() > 0) {
                btnRemove.setVisibility(View.VISIBLE);
            } else {
                btnRemove.setVisibility(View.INVISIBLE);
            }

        });

        //dialog to get language list
        tvLanguage.setOnClickListener(v -> languageSelectionDialog(tvLanguage, tvSelectLanguage));

        // add another view
        btnAddLanguage.setOnClickListener(v -> {
            addViewLanguage(false, 0);
            new Handler().postDelayed(() -> {
                // scrollView.smoothScrollTo(0, linearLanguageContainer.getScrollY());
            }, 500);
        });
        linearLanguageContainer.addView(addView);

        LayoutTransition transition = new LayoutTransition();
        linearLanguageContainer.setLayoutTransition(transition);
    }


    /** method for getting language array */
    private void getLanguageArray()
    {
        String[] arrayLang = new String[linearLanguageContainer.getChildCount()];
        String[] arrayLangStatus = new String[linearLanguageContainer.getChildCount()];
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < linearLanguageContainer.getChildCount(); i++) {

            View childView = linearLanguageContainer.getChildAt(i);
            CustomsTextView tvLanguage = (CustomsTextView) childView.findViewById(R.id.tvLanguage);
            CustomsTextView tvSelectLanguage = (CustomsTextView) childView.findViewById(R.id.tvSelectLanguage);
            arrayLang[i] = tvLanguage.getText().toString().trim();

            ToggleButtonGroupTableLayout groupTableLayout = (ToggleButtonGroupTableLayout) childView.findViewById(R.id.radGroup1);

            int selectedId = groupTableLayout.getCheckedRadioButtonId();
            if (selectedId == -1)
break;
            CustomRadioButton customRadioButton = (CustomRadioButton) childView.findViewById(selectedId);
            arrayLangStatus[i] = customRadioButton.getText().toString();

            System.out.println(TAG + "   :  " + arrayLang[i] + " :::  " + arrayLangStatus[i]);

            if (validateField(tvLanguage, tvSelectLanguage)) {
                willCallService = true;
            } else {
                willCallService = false;
                break;
            }
            if (!arrayLang[i].equals("")) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    for (int j = 0; j < getLanguageResponce.
                            getLanguageProficiencies().size(); j++) {

                        if (arrayLangStatus[i].
                                equalsIgnoreCase(getLanguageResponce
                                        .getLanguageProficiencies().get(j).
                                                getLanguage_proficiency_title())) {


                            jsonObject.put("lang_proficiency_name",
                                    arrayLangStatus[i]);

                            jsonObject.put(Keys.LANG_PROFICIENCY, getLanguageResponce
                                    .getLanguageProficiencies().get(j).getLanguage_proficiency_id());
                            break;
                        }
                    }
                    for (int j = 0; j < getLanguageResponce.
                            getJobLanguages().size(); j++) {

                        if (arrayLang[i].
                                equalsIgnoreCase(getLanguageResponce
                                        .getJobLanguages().get(j).
                                                getJob_language_title())) {

                            jsonObject.put("seeker_lang", arrayLang[i]);
                            jsonObject.put("seeker_lang_id", getLanguageResponce
                                    .getJobLanguages().get(j).
                                            getJob_language_id());
                            jsonObject.put(Keys.SEEKER_LANG_NAME, getLanguageResponce
                                    .getJobLanguages().get(j).
                                            getJob_language_id());
                            jsonObject.put(Keys.USER_ID, Singleton.getUserInfoInstance().getUser_id());

                            break;
                        }
                    }


                    jsonArray.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }


        //if (willCallService)
            callServiceDataForEditProfile(jsonArray);
    }

    /**
     * getting data for edit profile from field
     * @param jArry json array
     */
    private void callServiceDataForEditProfile(JSONArray jArry)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Keys.USER_ID, Singleton.getUserInfoInstance().getUser_id());
            jsonObject.put(Keys.FIRST_NAME, edFName.getText().toString().trim());
            jsonObject.put(Keys.LAST_NAME, edName.getText().toString().trim());
            jsonObject.put(Keys.DOB, dob);
            jsonObject.put(Keys.CITY, tvLocation.getText().toString().trim());
            jsonObject.put(Keys.DEVICE_TOKEN, "");
            jsonObject.put(Keys.DEVICE_ID, "");
            jsonObject.put(Keys.LATITUDE, seekerLat);
            jsonObject.put(Keys.LONGITUDE, seekerLng);

           /* int selectedId = rgMobility.getCheckedRadioButtonId();
            CustomRadioButton customRadioButton = (CustomRadioButton) rootView.findViewById(selectedId);
            String mobility = customRadioButton.getText().toString();*/

            /*int selectedIdSkill = rgSkill.getCheckedRadioButtonId();
            CustomRadioButton customRadioButtonSkill = (CustomRadioButton) rootView.findViewById(selectedIdSkill);
            String skill = customRadioButtonSkill.getText().toString();
*/


            JSONArray jsonArray = new JSONArray();


if(cbCuisin.isChecked())
{
    jsonArray.put(1);
}
            if(cbHotteler.isChecked())
            {
                jsonArray.put(3);
            }
            if(cbSalle.isChecked())
            {
                jsonArray.put(2);
            }
            if(!jsonArray.isNull(0))
            {
                jsonObject.put(Keys.SKILL, jsonArray);
            }
            else {
     jsonObject.put(Keys.SKILL, jsonArray);

            }

int indexMobility = rgMobility.indexOfChild((CustomRadioButton) rootView.
                            findViewById(rgMobility.getCheckedRadioButtonId()));
if(indexMobility!=-1)
{
    jsonObject.put(Keys.MOBILITY, String.valueOf(indexMobility+1));
}
else {
    jsonObject.put(Keys.MOBILITY,"");
}

            jsonObject.put(Keys.ABOUT, edAbout.getText().toString().trim());
            jsonObject.put(Keys.TRAINING, edSpecification.getText().toString().trim());
           // jsonObject.put(Keys.EDUCATION_LEVEL, tvEducationLevel.getText().toString().trim());
            jsonObject.put(Keys.EDUCATION_LEVEL, tvEducationLevelId);

            jsonObject.put(Keys.CANDIDATE_SEEK_ID, tvJobSoughtId);

           int selectedId2 = radGroupActualStatus.getCheckedRadioButtonId();
           if(selectedId2!=-1) {
               CustomRadioButton customRadioButton2 = (CustomRadioButton) rootView.findViewById(selectedId2);
               String actualStatus = customRadioButton2.getText().toString();

               if (actualStatus.equalsIgnoreCase(getString(R.string.profilesecondcell_appretice))) {
                   actualStatus = "2";
               } else if (actualStatus.equalsIgnoreCase(getString(R.string.profilefirstcell_student))) {
                   actualStatus = "1";
               } else if (actualStatus.equalsIgnoreCase(getString(R.string.profilesecondcell_activr))) {
                   actualStatus = "3";
               } else if (actualStatus.equalsIgnoreCase(getString(R.string.profilesecondcell_job_seeker))) {
                   actualStatus = "4";
               } else if (actualStatus.equalsIgnoreCase(getString(R.string.profilesecondcell_inactive))) {
                   actualStatus = "5";
               }
               jsonObject.put(Keys.CURRENT_STATUS, actualStatus);
           }
           else {
               jsonObject.put(Keys.CURRENT_STATUS, "");

           }
            jsonObject.put(Keys.LANGUAGE, jArry);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        try {
            if (imgDecodeString != null && !imgDecodeString.equals("") && onActivityUserPhotoCall) {
                fileBodyImage = new FileBody(new File(imgDecodeString));
                multipartEntityBuilder.addPart(Keys.USER_PIC, fileBodyImage);
            }
            if (vidDecodeString != null && !vidDecodeString.equals("") && onActivityPitchVideoCall) {
                FileBody fileBodyVideo = new FileBody(new File(vidDecodeString));
                multipartEntityBuilder.addPart(Keys.PATCH_VIDEO, fileBodyVideo);

                try {
                    FileBody fileBodyVideoThumbNail = new FileBody(new File(pitchVideoThumbNail));
                    multipartEntityBuilder.addPart(Keys.PATCH_VIDEO_THUMBNAIL, fileBodyVideoThumbNail);
                } catch (Exception e) {

                }
            }

           /* if (!onActivityUserPhotoCall && !onActivityPitchVideoCall) {
                callEditProfileServiceWithoutFile(jsonObject);
            }*//* else {
                multipartEntityBuilder.addPart(Keys.DATA, new StringBody(jsonObject.toString(), ContentType.TEXT_PLAIN.withCharset("UTF-8")));
                callEditProfileServiceWithoutFile(multipartEntityBuilder);
            }*/
            callEditProfileServiceWithoutFile(jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void callServiceDataForEditProfileImages(String type)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Keys.USER_ID, Singleton.getUserInfoInstance().getUser_id());
                jsonObject.put(Keys.USER_TYPE, Keys.SEEKER);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        try {
            if (type.equalsIgnoreCase("image")) {
                fileBodyImage = new FileBody(new File(imgDecodeString));
                multipartEntityBuilder.addPart(Keys.USER_PIC, fileBodyImage);
            }
            else
            {
                FileBody fileBodyVideo = new FileBody(new File(vidDecodeString));
                multipartEntityBuilder.addPart(Keys.PATCH_VIDEO, fileBodyVideo);

                try {
                    FileBody fileBodyVideoThumbNail = new FileBody(new File(pitchVideoThumbNail));
                    multipartEntityBuilder.addPart(Keys.PATCH_VIDEO_THUMBNAIL, fileBodyVideoThumbNail);
                } catch (Exception e) {

                }
            }

           /* if (!onActivityUserPhotoCall && !onActivityPitchVideoCall) {
                callEditProfileServiceWithoutFile(jsonObject);
            }*/
                multipartEntityBuilder.addPart(Keys.DATA,
                        new StringBody(jsonObject.toString(),
                                ContentType.TEXT_PLAIN.withCharset("UTF-8")));
                callEditProfileServiceWithFile(multipartEntityBuilder,type);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /** call service for update profile  with file */
    private void callEditProfileServiceWithFile(MultipartEntityBuilder multipartEntityBuilder,String type)
    {
        final SpotsDialog spotsDialog = new SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        MultipartWebServiceCall multipartWebServiceCall =
                new MultipartWebServiceCall(getActivity(),
                        new com.infoicon.bonjob.multipart.INetworkResponse() {
            @Override
            public void onSuccess(String response) {
                JSONObject jsonObject = null;
                if (spotsDialog.isShowing() && !getFragmentManager().isDestroyed())
                    spotsDialog.dismiss();
                try {
                    jsonObject = new JSONObject(response);
                    String msg = jsonObject.getString(Keys.MESSAGE);
                    boolean success = jsonObject.getBoolean(Keys.SUCCESS);
                    if (success) {
                        JSONObject user_pic_json = jsonObject.getJSONObject(Keys.DATA);
                        final DatabaseReference fireDatabaseReference = FirebaseDatabase.getInstance().getReference();

                        fireDatabaseReference.
                                child("seeker/" + Singleton.getUserInfoInstance().getUser_id()+"/profilePic")
                                .setValue(user_pic_json.getString(Keys.USER_PIC));
                        Intent intentVolume = new Intent(Keys.PROFILE);
                        getActivity().sendBroadcast(intentVolume);
                        Singleton.getUserInfoInstance().setuser_pic(user_pic_json.getString(Keys.USER_PIC));


                          //delete video
                        UtilsMethods.deleteAllBonjobCreatedFile();
                        //delete thumbnail
                        //UtilsMethods.deleteFile(pitchVideoThumbNail);
                        //delete  user pic
                        //UtilsMethods.deleteFile(imgDecodeString);
                      //  openAlert(msg);
                    } else {
                        if (jsonObject.getString(Keys.ACTIVE_USER).equals(Keys.AUTH_CODE)) {
                            UtilsMethods.unAuthorizeAlert(getActivity(), msg);
                        } else {
                            CommonUtils.showSimpleMessageBottom(getActivity(), msg);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {
                spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(getActivity(), error);
            }
        });
        if (type.equalsIgnoreCase("image")) {
            multipartWebServiceCall.execute(multipartEntityBuilder, ServiceUrls.uploadUserPic);
        }
        else {
            multipartWebServiceCall.execute(multipartEntityBuilder, ServiceUrls.uploadPatchVideo);
        }
    }



    /** call service for update profile without images */
    private void callEditProfileServiceWithoutFile(JSONObject jsonObject) {
        WebServiceCall webServiceCall = new WebServiceCall(getActivity(),
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean(Keys.SUCCESS);
                            String message = jsonObject.getString(Keys.MESSAGE);
                            if (success)
                            {
                                Intent intentVolume = new Intent(Keys.PROFILE);
                                getActivity().sendBroadcast(intentVolume);
                                openAlert(message);
                            } else {
                                if (jsonObject.getString(Keys.ACTIVE_USER).equals(Keys.AUTH_CODE))
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
                    public void onError(String error) {
                        CommonUtils.showSimpleMessageBottom(getActivity(), error);
                    }
                });
        webServiceCall.execute(jsonObject, ServiceUrls.EDIT_PROFILE);

    }
    /** show message on alert */
    public void openAlert(String message) {
        final Dialog dialog = new Dialog(getActivity());
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_alert_ok);
        dialog.setCancelable(false);
        CustomsTextView textViewTitle = (CustomsTextView) dialog.findViewById(R.id.textViewTitle);
        CustomsTextView textViewMessage = (CustomsTextView) dialog.findViewById(R.id.textViewMessage);
        CustomsButton buttonOk = (CustomsButton) dialog.findViewById(R.id.buttonOk);
        textViewTitle.setText(getResources().getString(R.string.app_name));
        textViewMessage.setText(message);
        buttonOk.setOnClickListener(view -> {
            dialog.dismiss();
            (getActivity()).onBackPressed();
        });

        dialog.show();
    }


    /**
     * edit image gallery
     */
    public void imageEdit(String gallery_id, String desc, String imgUrl, int pos)
    {
        final Dialog dialogImageEdit = new Dialog(getActivity());
        dialogImageEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogImageEdit.setCancelable(false);
        if (dialogImageEdit.getWindow() != null)

            dialogImageEdit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogImageEdit.setContentView(R.layout.dialog_image_view);
        DisplayMetrics displayMetrics = new DisplayMetrics();
       getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
       int width = displayMetrics.widthPixels;
      RelativeLayout root = (RelativeLayout) dialogImageEdit.findViewById(R.id.root);
        int valueInPixels = (int) getResources().getDimension(R.dimen.default_margin);
        root.getLayoutParams().height = width-(valueInPixels*5);


        ImageView imgViewClose = (ImageView) dialogImageEdit.findViewById(R.id.imgViewClose);
        ImageView imageViewBg = (ImageView) dialogImageEdit.findViewById(R.id.imageViewBg);
        CheckBox cbEdit = (CheckBox) dialogImageEdit.findViewById(R.id.cbEdit);
        CustomEditText edDesc = (CustomEditText) dialogImageEdit.findViewById(R.id.edDesc);
        edDesc.setText(desc);
        edDesc.setEnabled(false);
        CustomsButton btnDeletePhoto = (CustomsButton) dialogImageEdit.findViewById(R.id.btnDeletePhoto);

        showProgress(true);
        ImageLoader.loadImageRoundCallBack(getActivity(), imgUrl, imageViewBg, new ImageLoader.CallBack() {
            @Override
            public void onSuccess() {
                showProgress(false);
                new Handler().postDelayed(dialogImageEdit::show, 200);
            }

            @Override
            public void onError() {
                showProgress(false);

            }
        });
        imgViewClose.setOnClickListener(v -> {
            imgDecodeStringForGallery = null;
            dialogImageEdit.dismiss();
        });

        //event for delete photo
        btnDeletePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callWebServiceForDeleteGallery(gallery_id, pos, dialogImageEdit);
            }
        });

        //event for edit description
        cbEdit.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                edDesc.setEnabled(true);
                edDesc.setSelection(edDesc.getText().length());
            } else {
                callWebServiceForUpdateDescription(gallery_id, edDesc.getText().toString().trim(), pos, dialogImageEdit);
                edDesc.setEnabled(false);

            }
        });

       dialogImageEdit.show();

    }

    /** show progressbar */
    private void showProgress(boolean status) {
        if (status) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }


    /** add json data in request to update gallery */
    private void uploadGallery(String gallery_id, String desc) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Keys.GALLERY_ID, gallery_id);
            jsonObject.put(Keys.USER_ID, Singleton.getUserInfoInstance().getUser_id());
            jsonObject.put(Keys.DESCRIPTION, desc);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        try {
            if (imgDecodeStringForGallery != null && !imgDecodeStringForGallery.equals("")) {
                fileBodyImage = new FileBody(new File(imgDecodeStringForGallery));
                multipartEntityBuilder.addPart(Keys.GALLERY, fileBodyImage);
            }


            Logger.e("REQUEST : " + jsonObject.toString());
            multipartEntityBuilder.addPart(Keys.DATA, new StringBody(jsonObject.toString(), ContentType.TEXT_PLAIN));
            callServiceForUploadGallery(multipartEntityBuilder);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /** call web service for upload images on gallery */
    private void callServiceForUploadGallery(MultipartEntityBuilder multipartEntityBuilder) {
        final SpotsDialog spotsDialog = new SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        MultipartWebServiceCall multipartWebServiceCall = new MultipartWebServiceCall(getActivity(),
                new com.infoicon.bonjob.multipart.INetworkResponse() {
            @Override
            public void onSuccess(String response) {

                JSONObject jsonObject;
                spotsDialog.dismiss();
                try {
                    jsonObject = new JSONObject(response);
                    String msg = jsonObject.getString(Keys.MESSAGE);
                    boolean success = jsonObject.getBoolean(Keys.SUCCESS);
                    if (success) {

                        JSONObject jsonObjectData = jsonObject.getJSONObject(Keys.DATA);
                        //List<GetSeekerProfileResponse.DataBean.GalleryBean> galleryBeanArrayList = new ArrayList<>();
                        GetSeekerProfileResponse.DataBean.GalleryBean galleryBean = new GetSeekerProfileResponse.DataBean.GalleryBean(Parcel.obtain());
                        galleryBean.setGallery_id(jsonObjectData.getString(Keys.GALLERY_ID));
                        galleryBean.setUser_id(jsonObjectData.getString(Keys.USER_ID));
                        galleryBean.setImage(jsonObjectData.getString(Keys.IMAGE));
                        galleryBean.setDescription(jsonObjectData.getString(Keys.DESCRIPTION));
                        galleryBean.setCreatedOn(jsonObjectData.getString(Keys.CREATED_ON));
                        galleryBean.setUpdatedOn(jsonObjectData.getString(Keys.CREATED_ON));

                       /* //add all previous gallery data
                        if (profileFragment.getSeekerProfileResponse.getData().getGallery().size() > 0)
                            galleryBeanArrayList.addAll(profileFragment.getSeekerProfileResponse.getData().getGallery());
                        //add new added data to list
                        galleryBeanArrayList.add(galleryBean);
                         profileFragment.getSeekerProfileResponse.getData().setGallery(galleryBeanArrayList);*/

                        if (galleryAdapter != null) {
                            galleryAdapter.addPhoto(galleryBean);
                        }

                        profileFragment.getSeekerProfileResponse.getData().getGallery().add(galleryBean);
                        CommonUtils.showSimpleMessageBottom(getActivity(), msg);
                    } else {
                        if (jsonObject.getString(Keys.ACTIVE_USER).equals(Keys.AUTH_CODE)) {
                            UtilsMethods.unAuthorizeAlert(getActivity(), msg);
                        } else {
                            CommonUtils.showSimpleMessageBottom(getActivity(), msg);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {
                spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(getActivity(), error);
            }
        });

        multipartWebServiceCall.execute(multipartEntityBuilder, ServiceUrls.UPDATE_GALLERY);
    }


    /** call web service for delete images from gallery */
    private void callWebServiceForDeleteGallery(String gallery_id, int pos, Dialog dialogImageEdit) {
        SpotsDialog spotsDialog = new SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.GALLERY_ID, gallery_id);
        Call<GetDeleteGalleryResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getDeleteGalleryResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new Callback<GetDeleteGalleryResponse>() {
            @Override
            public void onResponse(Response<GetDeleteGalleryResponse> response, Retrofit retrofit) {
                spotsDialog.dismiss();
                dialogImageEdit.dismiss();
                GetDeleteGalleryResponse getDeleteGalleryResponse = response.body();
                if (getDeleteGalleryResponse.isSuccess()) {
                    if (profileFragment.getSeekerProfileResponse.getData().getGallery().size() > 0)
                        profileFragment.getSeekerProfileResponse.getData().getGallery().remove(pos);
                    //delete item from gallery
                    if (galleryAdapter != null)
                        galleryAdapter.removePhoto(pos);
                    CommonUtils.showSimpleMessageBottom(getActivity(), getDeleteGalleryResponse.getMsg());
                } else {
                    if (getDeleteGalleryResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(getActivity(), getDeleteGalleryResponse.getMsg());
                    } else
                        CommonUtils.showSimpleMessageBottom(getActivity(), getDeleteGalleryResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(getActivity(), t.getMessage());
            }
        });

    }

    /** call web service for update gallery description. */
    private void callWebServiceForUpdateDescription(String gallery_id, String description, int pos, Dialog dialogImageEdit) {
        SpotsDialog spotsDialog = new SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.GALLERY_ID, gallery_id);
        jsonObject.addProperty(Keys.DESCRIPTION, description);
        Call<GetUpdateDiscriptionResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getUpdateDescriptionResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new Callback<GetUpdateDiscriptionResponse>() {
            @Override
            public void onResponse(Response<GetUpdateDiscriptionResponse> response, Retrofit retrofit) {
                spotsDialog.dismiss();
                dialogImageEdit.dismiss();
                GetUpdateDiscriptionResponse getUpdateDiscriptionResponse = response.body();
                if (getUpdateDiscriptionResponse.isSuccess()) {
                    if (galleryAdapter != null) {
                        galleryAdapter.updateDescription(pos, description);
                    }
                    profileFragment.getSeekerProfileResponse.getData().getGallery().get(pos).setDescription(description);
                    CommonUtils.showSimpleMessageBottom(getActivity(), getUpdateDiscriptionResponse.getMsg());
                } else {
                    if (getUpdateDiscriptionResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(getActivity(), getUpdateDiscriptionResponse.getMsg());
                    } else
                        CommonUtils.showSimpleMessageBottom(getActivity(), getUpdateDiscriptionResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(getActivity(), t.getMessage());
            }
        });

    }

    /**
     * method for open dialog to get language list
     * @param tvLanguage       view where set the language name after item click event.
     * @param tvSelectLanguage show the message if not language selected
     */
    public void languageSelectionDialog(CustomsTextView tvLanguage, CustomsTextView tvSelectLanguage) {
        final Dialog dialogActivityArea = new Dialog(getActivity());
        dialogActivityArea.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialogActivityArea.getWindow() != null)
            dialogActivityArea.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogActivityArea.setContentView(R.layout.dialog_activity_area);
        ListView listView = (ListView) dialogActivityArea.findViewById(R.id.listView);
        ImageView imageViewClose = (ImageView) dialogActivityArea.findViewById(R.id.imageViewClose);
        CustomsTextViewBold textViewTitle = (CustomsTextViewBold) dialogActivityArea.findViewById(R.id.textViewTitle);
        textViewTitle.setText(getResources().getString(R.string.profilefirstcell_language));

        //String[] mTestArray = getResources().getStringArray(R.array.array_language);
        String[] mTestArray=  new String[getLanguageResponce
                .getJobLanguages()
                .size()];
        for(int i=0;i< getLanguageResponce.getJobLanguages().size();i++)
        {
            mTestArray[i]=getLanguageResponce.getJobLanguages()
                    .get(i).getJob_language_title();

            // String[] mTestArray = getResources().getStringArray(R.array.array_type_of_contract);
        }
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(getActivity(), R.layout.single_item_list_post_held, mTestArray);
        listView.setAdapter(itemsAdapter);
        dialogActivityArea.show();
        listView.setOnItemClickListener((parent, view, position, id) -> {

            if (selectedLanguage.contains(mTestArray[position])) {
                CommonUtils.showToast(getActivity(), getResources().getString(R.string.language_taken));
            } else {
                if (selectedLanguage.contains(tvLanguage.getText().toString().trim())) {
                    removeLanguage(tvLanguage.getText().toString().trim());
                }
                tvLanguage.setText(mTestArray[position]);
                tvSelectLanguage.setVisibility(View.GONE);
                addLanguage(mTestArray[position]);
            }
            dialogActivityArea.dismiss();

        });
        imageViewClose.setOnClickListener(v -> dialogActivityArea.dismiss());
    }
    GetAllDropDownResponce getLanguageResponce;
  /*  private void serviceForGetlanguageSelectionData()
    {
        final SpotsDialog spotsDialog = new
                SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();

        retrofit.Call<GetLanguageResponce> call = AppRetrofit.getAppRetrofitInstance().
                getApiServices().getGetGetLanguageResponce
                (jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetLanguageResponce>() {
            @Override
            public void onResponse(retrofit.Response<GetLanguageResponce> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                  getLanguageResponce = response.body();

                if (getLanguageResponce.isSuccess())
                {

                    initialize();
                    setTypeFace();
                    listener();
                    getBundleData();

                } else {
                    if (getLanguageResponce.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(getActivity(), getLanguageResponce.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();

            }
        });
    }*/
    /**
     * Method  to Open Alert/Dialog
     * @param requestCode for identify permission requested code.
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
                    loadImagefromGallery();
                    break;
                case PermissionKeys.REQUEST_CODE_WRITE_EXTERNAL_STORAGE_2:
                    loadVideofromGallery();
                    break;
                case PermissionKeys.REQUEST_CODE_PERMISSION_ALL_PHOTO:
                    takePhotoOption(fromPage);
                    break;
                case PermissionKeys.REQUEST_CODE_PERMISSION_ALL_VIDEO:
                    takeVideoOption();
                    break;
                default:
                    break;
            }
        });
        buttonNo.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }


    /** text count for specification */
    private final TextWatcher textWatcherSpecification = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            tvCountSpec.setText(s.length() + "/100");
        }

        public void afterTextChanged(Editable s) {
        }
    };

    /** text count for about */
    private final TextWatcher textWatcherAbout = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            tvCountAbout.setText(s.length() + "/200");
        }

        public void afterTextChanged(Editable s) {
        }
    };


    /**
     * validate the field
     * @param language   view for language
     * @param tvLanguage make visible to show message "Select language"
     */
    private boolean validateField(CustomsTextView language, CustomsTextView tvLanguage) {
        if (UtilsMethods.isEmpty(edFName)) {
            showErrorAlert(getActivity(),getResources().getString(R.string.firstName_empty_message));
            requestFocus(edFName);
            return false;
        }
        if (UtilsMethods.isEmpty(edName)) {
            showErrorAlert(getActivity(),getResources().getString(R.string.name_empty_message));
           // showErrorAlert(getActivity(),requestFocus(edName);
            return false;
        }

       /* if (UtilsMethods.isEmpty(textViewBorn)) {
            textViewBorn.setHint(getResources().getString(R.string.required));
            textViewBorn.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.red_color));
            requestFocus(textViewBorn);
            return false;
        }
        if (UtilsMethods.isEmpty(tvLocation)) {
            tvLocation.setHint(getResources().getString(R.string.required));
            tvLocation.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.red_color));
            requestFocus(tvLocation);
            return false;
        }*/
       /* if (UtilsMethods.isEmpty(language)) {
            tvLanguage.setVisibility(View.VISIBLE);
            language.getParent().requestChildFocus(tvLanguage, language);
            return false;
        }*/
        return true;
    }

    /**
     * focus to view which view have invalid.
     * @param view where request will focus
     */
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    /** service for logout from app. */
    private void removePhotoVideo(String fieldName) {
        final SpotsDialog spotsDialog = new SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.FIELD_NAME, fieldName);

        retrofit.Call<GetPhotoVideoRemoveResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getRemovePhotoVideoResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetPhotoVideoRemoveResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetPhotoVideoRemoveResponse> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                GetPhotoVideoRemoveResponse getPhotoVideoRemoveResponse = response.body();
                if (getPhotoVideoRemoveResponse.isSuccess()) {
                    if (fieldName.equals(Keys.USER_PIC)) {
                        imageViewPhotoView.setImageResource(R.drawable.default_photo_deactive);
                        onActivityUserPhotoCall = false;
                        urlUserPic = "";
                        profileFragment.getSeekerProfileResponse.getData().setUser_pic("");
                        profileFragment.relativeLayoutUserPic.setVisibility(View.GONE);
                        tvRemovePhoto.setVisibility(View.GONE);
                        final DatabaseReference fireDatabaseReference = FirebaseDatabase.getInstance().getReference();

                        fireDatabaseReference.
                                child("seeker/" + Singleton.getUserInfoInstance().getUser_id()+"/profilePic")
                                .setValue("");


                    } else if (fieldName.equals(Keys.PATCH_VIDEO)) {
                        imageViewPitchVideoView.setImageResource(R.drawable.play_icon_deactive);
                        onActivityPitchVideoCall = false;
                        urlPatchVideo = "";
                        profileFragment.getSeekerProfileResponse.getData().setPatch_video("");
                        profileFragment.getSeekerProfileResponse.getData().setPatch_video_thumbnail("");
                        profileFragment.relativeLayoutPitchVideo.setVisibility(View.GONE);
                        tvRemoveVideo.setVisibility(View.GONE);
                        imageViewPlayIcon.setVisibility(View.GONE);
                    }
                    UtilsMethods.openCustumAlert(getActivity(), getPhotoVideoRemoveResponse.getMsg());
                } else {
                    CommonUtils.showSimpleMessageBottom(getActivity(), getPhotoVideoRemoveResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(getActivity(), t.getMessage());
            }
        });
    }

    /** method for open dialog to view photo */
    public void dialogViewPhoto() {
        final Dialog dialogVideoInfo = new Dialog(getActivity());
        dialogVideoInfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialogVideoInfo.getWindow() != null)
            dialogVideoInfo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogVideoInfo.setContentView(R.layout.dialog_photo_view_square);
        ImageView imageViewClose = (ImageView) dialogVideoInfo.findViewById(R.id.imageViewClose);
        ImageView imageViewPhoto = (ImageView) dialogVideoInfo.findViewById(R.id.imageViewPhoto);
        CustomsTextView tvRemove = (CustomsTextView) dialogVideoInfo.findViewById(R.id.tvRemove);

        if (!onActivityUserPhotoCall) {
            tvRemove.setVisibility(View.VISIBLE);
            showProgress(true);
            ImageLoader.loadImageRoundCallBack(getActivity(), urlUserPic, imageViewPhoto, new ImageLoader.CallBack() {
                @Override
                public void onSuccess() {
                    if (getActivity() != null && isAdded()) {
                        showProgress(false);
                        new Handler().postDelayed(dialogVideoInfo::show, 300);
                    }
                }

                @Override
                public void onError() {
                    if (getActivity() != null && isAdded()) {
                        showProgress(false);
                    }
                }
            });
        } else {
            imageViewPhoto.setImageBitmap(BitmapFactory.decodeFile(imgDecodeString));
        }
        //close dialog
        imageViewClose.setOnClickListener(v -> dialogVideoInfo.dismiss());
        //remove photo
        tvRemove.setOnClickListener(v -> {
            UtilsMethods.removeConfirmation(getActivity(), () -> {
                dialogVideoInfo.dismiss();
                removePhotoVideo(Keys.USER_PIC);
            });
        });
        dialogVideoInfo.show();
    }


    /** dialog to play video */
    private void dialogViewVideo() {
        final Dialog dialogVideoInfo = new Dialog(getActivity());
        dialogVideoInfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialogVideoInfo.getWindow() != null)
            dialogVideoInfo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogVideoInfo.setContentView(R.layout.dialog_video_view);
        ImageView imageViewClose = (ImageView) dialogVideoInfo.findViewById(R.id.imageViewClose);
        VideoView videoView = (VideoView) dialogVideoInfo.findViewById(R.id.videoView);
        //    videoView.setVisibility(View.INVISIBLE);
        ProgressBar progressBar = (ProgressBar) dialogVideoInfo.findViewById(R.id.progressBar);
        RelativeLayout rlController = (RelativeLayout) dialogVideoInfo.findViewById(R.id.rlController);
        ImageView imageViewPlay = (ImageView) dialogVideoInfo.findViewById(R.id.imageViewPlay);
        ImageView imageViewPause = (ImageView) dialogVideoInfo.findViewById(R.id.imageViewPause);
        ImageView imageViewFull = (ImageView) dialogVideoInfo.findViewById(R.id.imageViewFull);
        CustomsTextView tvRemove = (CustomsTextView) dialogVideoInfo.findViewById(R.id.tvRemove);
        imageViewClose.setOnClickListener(v -> dialogVideoInfo.dismiss());

        MediaController mediacontroller = new MediaController(getActivity());
        mediacontroller.setAnchorView(videoView);

        if (!onActivityPitchVideoCall) {
            Logger.e(TAG + " video Url : " + urlPatchVideo);
            videoView.setVideoPath(urlPatchVideo);
            tvRemove.setVisibility(View.VISIBLE);
        } else {
            Logger.e(TAG + " video Url : " + vidDecodeString);
            videoView.setVideoPath(vidDecodeString);
        }

        UtilsMethods.disableSSLCertificateChecking();

        /**play video while dialog appear first time*/
        hidePlayPauseController(0, imageViewPlay, imageViewPause);
        hideShowView(0, rlController);
        videoView.start();
        progressBar.setVisibility(View.VISIBLE);

        /** event for play video */
        imageViewPlay.setOnClickListener(v -> {
            if (!videoView.isPlaying()) {
                hidePlayPauseController(0, imageViewPlay, imageViewPause);
                hideShowView(0, rlController);
                videoView.start();
                //  progressBar.setVisibility(View.VISIBLE);
            }
        });

        /** event for pause video */
        imageViewPause.setOnClickListener(v -> {
            if (videoView.isPlaying()) {
                hideShowView(1, rlController);
                hidePlayPauseController(1, imageViewPlay, imageViewPause);
                progressBar.setVisibility(View.GONE);
                videoView.pause();
            }
        });
        /** event for full screen view  image*/
        imageViewFull.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PlayVideoFullSreenActivity.class);
            if (!onActivityPitchVideoCall) {
                intent.putExtra(Keys.PATCH_VIDEO, urlPatchVideo);
            } else {
                intent.putExtra(Keys.PATCH_VIDEO, vidDecodeString);
            }
            startActivity(intent);
        });

        /** media complete listener */
        videoView.setOnCompletionListener(mp -> {
            Logger.e(TAG + "  onCompletion ");
            progressBar.setVisibility(View.GONE);
            hideShowView(1, rlController);
            hidePlayPauseController(1, imageViewPlay, imageViewPause);
        });
        /** error listener while playing video */
        videoView.setOnErrorListener((mp, what, extra) -> {
            Logger.e(TAG + "  onError ");
            progressBar.setVisibility(View.GONE);
            hidePlayPauseController(1, imageViewPlay, imageViewPause);
            return true;
        });

        /** prepare listener  */
        videoView.setOnPreparedListener(mp -> {
            Logger.e(TAG + " onPrepared ");
            progressBar.setVisibility(View.GONE);
            mp.setOnBufferingUpdateListener((mp1, percent) -> {
                Logger.e(TAG + " onBufferingUpdate : " + percent);
                if (percent == 100) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        });

        /** hide/show custom controller on touch VideoView */
        videoView.setOnTouchListener((View v, MotionEvent event) -> {
            Logger.e(TAG + "onTouch ");
            if (videoView.isPlaying() && rlController.getVisibility() == View.VISIBLE) {
                hideShowView(0, rlController);
            } else {
                hideShowView(1, rlController);
            }
            return false;
        });

        /** event for remove video from dialog */
        tvRemove.setOnClickListener(v -> {
            UtilsMethods.removeConfirmation(getActivity(), () -> {
                dialogVideoInfo.dismiss();
                removePhotoVideo(Keys.PATCH_VIDEO);
            });
        });

        dialogVideoInfo.show();
    }

    /**
     * hide play pause controller
     * @param status 0-> hide play icon ,1->hide pause icon
     */
    void hidePlayPauseController(int status, ImageView imageViewPlay, ImageView imageViewPause) {
        if (status == 0) {
            imageViewPlay.setVisibility(View.GONE);
            imageViewPause.setVisibility(View.VISIBLE);
        } else {
            imageViewPlay.setVisibility(View.VISIBLE);
            imageViewPause.setVisibility(View.GONE);
        }
    }

    /** hide show controller on basis of status */
    private void hideShowView(int status, RelativeLayout rlController) {
        if (status == 0) {
            rlController.animate()
                    .alpha(0.0f)
                    .setDuration(1000)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            rlController.setVisibility(View.GONE);
                        }
                    });
        } else {
            rlController.animate()
                    .alpha(1.0f)
                    .setDuration(1000)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            rlController.setVisibility(View.VISIBLE);
                        }
                    });
        }
    }
}
