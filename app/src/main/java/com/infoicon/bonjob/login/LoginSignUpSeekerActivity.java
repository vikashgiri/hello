package com.infoicon.bonjob.login;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.chat.Message;
import com.infoicon.bonjob.chat.StaticConfig;
import com.infoicon.bonjob.customview.CustomCheckBox;
import com.infoicon.bonjob.customview.CustomEditText;
import com.infoicon.bonjob.customview.CustomRadioButton;
import com.infoicon.bonjob.customview.CustomsButton;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.dialogs.DiplomaDialogFragment;
import com.infoicon.bonjob.dialogs.DiplomaDialogFragmentForRegister;
import com.infoicon.bonjob.dialogs.EductionLabelResponce;
import com.infoicon.bonjob.fcm.MyFcmTokenModel;
import com.infoicon.bonjob.fcm.RefreshTokenService;
import com.infoicon.bonjob.forgotPassword.ForgetPasswordActivity;
import com.infoicon.bonjob.interfaces.FragmentChanger;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.main.HomeSeekerActivity;
import com.infoicon.bonjob.multipart.INetworkResponse;
import com.infoicon.bonjob.multipart.MultipartWebServiceCall;
import com.infoicon.bonjob.permission.MarshmallowPermission;
import com.infoicon.bonjob.permission.PermissionKeys;
import com.infoicon.bonjob.recruiters.candidateProfile.CandidateProfileActivity;
import com.infoicon.bonjob.recruiters.profile.ProfileRecruiterFragment;
import com.infoicon.bonjob.recruiters.search.GetTargetSearchResponce;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.ServiceUrls;
import com.infoicon.bonjob.retrofit.response.GetLoginRecruiterResponse;
import com.infoicon.bonjob.retrofit.response.GetLoginSeekerResponse;
import com.infoicon.bonjob.retrofit.response.GetRegistrationRecruiterResponse;
import com.infoicon.bonjob.retrofit.response.GetRegistrationSeekerResponse;
import com.infoicon.bonjob.retrofit.response.GetSeekerProfileResponse;
import com.infoicon.bonjob.retrofit.response.GetSocialLoginResponse;
import com.infoicon.bonjob.seeker.profile.ExperienceFragment;
import com.infoicon.bonjob.seeker.profile.GetEducationDataResponce;
import com.infoicon.bonjob.seeker.profile.PickLocationActivity;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.AppConstants;
import com.infoicon.bonjob.utils.CommonUtils;
import com.infoicon.bonjob.utils.CompressVideo;
import com.infoicon.bonjob.utils.JSONUtils;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

import static com.infoicon.bonjob.utils.UtilsMethods.showErrorAlert;

public class LoginSignUpSeekerActivity extends AppCompatActivity implements
        CompoundButton.OnCheckedChangeListener,FragmentChanger {


    @BindView(R.id.rbLogin)
    CustomRadioButton rbLogin;
    @BindView(R.id.rbSignUp)
    CustomRadioButton rbSignUp;
    @BindView(R.id.signup)
    ScrollView signup;
    @BindView(R.id.login)
    ScrollView login;
    @BindView(R.id.edFirstName)
    EditText edFirstName;
    @BindView(R.id.edName)
    EditText edName;
    @BindView(R.id.edEmail)
    EditText edEmail;
    @BindView(R.id.edPassword)
    EditText edPassword;
    @BindView(R.id.edEmailLogin)
    EditText edEmailLogin;
    @BindView(R.id.edPasswordLogin)
    EditText edPasswordLogin;
    @BindView(R.id.imageViewPhoto)
    CircularImageView imageViewPhoto;
    @BindView(R.id.imageViewVideo)
    CircularImageView imageViewVideo;
    @BindView(R.id.cbTermsConditionAccept)
    CustomCheckBox cbTermsConditionAccept;
    @BindView(R.id.buttonSignUp)
    CustomsButton buttonSignUp;
    @BindView(R.id.buttonLogin)
    CustomsButton buttonLogin;
    @BindView(R.id.edExperiance)
    EditText textViewExperience;
    @BindView(R.id.edtextViewTraninng)
    EditText textViewDiploma;
    @BindView(R.id.edLocation)
    EditText tvLocation;
    public JSONArray jArry;

    private MarshmallowPermission marshmallowPermission;
    private MultipartEntityBuilder multipartEntityBuilder;
    private FileBody fileBodyImage, fileBodyVideo;
    private String imgDecodableString;
    private String vidDecodableString;
    private String mLatitude;
    private String mLongitude;
    private String mCurrentVideoPath;
    private boolean onActivityUserPhotoCall;
    private boolean onActivityPitchVideoCall;
    private String pitchVideoThumbNail;
    private String diplomaId;

    List<GetSeekerProfileResponse.DataBean.ExperienceBean> experienceBeanList
            = new ArrayList<>();

    @OnClick(R.id.edLocation)
    void getLocation() {
        Intent intent = new Intent(this, PickLocationActivity.class);
        startActivityForResult(intent, Keys.LOCATION_CODE);
    }

    public void setExperiance() {

        textViewExperience.setText(R.string.no_company_added);
        JSONObject jsonobject = null;
        try {
            experienceBeanList.clear();

            for (int i = 0; i < jArry.length(); i++) {
                jsonobject = (JSONObject) jArry.get(i);
                GetSeekerProfileResponse.DataBean.ExperienceBean experienceBean = new
                        GetSeekerProfileResponse.DataBean.ExperienceBean();
                experienceBean.setPosition_held(jsonobject.optString("position_held"));
                experienceBean.setIndustry_type(jsonobject.optString("industry_type"));
                experienceBean.setCompany_name(jsonobject.optString("company_name"));
                experienceBean.setDescription(jsonobject.optString("description"));
                experienceBean.setExperience(jsonobject.optString("experience"));
                experienceBean.setPosition_held_name(jsonobject.optString("position_held_name"));
                experienceBean.setIndustry_type_name(jsonobject.optString("industry_type_name"));
                experienceBeanList.add(experienceBean);
            }
            textViewExperience.setText(
                    jsonobject.optString("position_held_name") + "-" +
                            jsonobject.optString("company_name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        ButterKnife.bind(this);
        initialize();
        setTypeFace();
        setEditTextSingleLine();
        jArry = new JSONArray();
        getIntents();
    }


    /**
     * get intents data
     */
    private void getIntents() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            String event = intent.getStringExtra(Keys.CLICKED_EVENT);
            if (event.equalsIgnoreCase(Keys.CLICKED_EVENT_LOGIN)) {
                rbSignUp.setChecked(true);
                rbLogin.setChecked(false);
                rbSignUp.setTextColor(ContextCompat.getColor(this, R.color.white));
                rbLogin.setTextColor(ContextCompat.getColor(this, R.color.txt_Color_blue));
                visibleLogin();

            } else {
                rbLogin.setChecked(true);
                rbSignUp.setChecked(false);
                rbLogin.setTextColor(ContextCompat.getColor(this, R.color.white));
                rbSignUp.setTextColor(ContextCompat.getColor(this, R.color.txt_Color_blue));
                visibleSinUp();
            }
        }
    }

    /**
     * event for education level dialog
     */
    @OnClick(R.id.edtextViewTraninng)
    void educationLevel() {
        //serviceForGetData();
        Intent intent = new Intent(LoginSignUpSeekerActivity.this,
                DiplomaDialogFragmentForRegister.class);

        startActivityForResult(intent, Keys.DIPLOMA_REQUEST_CODE);
}
// close existing dialog fragments




    @OnClick(R.id.edExperiance)
    void proceedToUserExperiencePage()
    {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Keys.EXPERIENCE,
                (ArrayList<GetSeekerProfileResponse.DataBean.ExperienceBean>)
                        experienceBeanList);

        bundle.putString(Keys.TYPE,"register");
        (LoginSignUpSeekerActivity.this).addFragment(new ExperienceFragment(),
                bundle, Keys.EXPERIENCE,
                false, true);
    }
    /** get index of the fragment by tag */
    private int getIndex(String tagname)
    {
        FragmentManager manager = getSupportFragmentManager();
        for (int i = 0; i < manager.getBackStackEntryCount(); i++) {
            if (manager.getBackStackEntryAt(i).getName().equalsIgnoreCase(tagname)) {
                return i;
            }
        }
        return -1;
    }
    @Override
    public void addInnerFragment(Fragment fragment, Bundle bundle, String tag, boolean isRemoveBackStack, boolean isAddToBackStack)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        if (isRemoveBackStack) {
            for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                fm.popBackStack();
            }
        }

        fragment.setArguments(bundle);
        //  fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, 0, 0, R.anim.slide_out_left);
        if (isAddToBackStack) {
            fragmentTransaction.add(R.id.frame_container, fragment, tag).addToBackStack(null).commit();
        } else {
            fragmentTransaction.add(R.id.frame_container, fragment, tag).commit();
        }
    }

    /**
     * remove fragment from backStack
     */
    @Override
    public void removeFragment()
    {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();
    }

    @Override
    public void addFragment(Fragment fragment, Bundle bundle, String tag,
                            boolean isRemoveBackStack, boolean isAddToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        if (isRemoveBackStack) {
            //fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                fm.popBackStack();
            }
        }
        fragment.setArguments(bundle);

        if (getIndex(tag) >= 0) {
            if (isAddToBackStack) {
                fm.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentTransaction.replace(R.id.frame_container, fragment, tag).addToBackStack(tag).commit();
                // fragmentTransaction.replace(R.id.frame_container, fragment, tag).commit();
            } else {
                fragmentTransaction.replace(R.id.frame_container, fragment, tag).commit();
            }
        } else {
            if (isAddToBackStack)
            {
                fragmentTransaction.add(R.id.frame_container, fragment, tag).addToBackStack(tag).commit();
                // fragmentTransaction.add(R.id.frame_container, fragment, tag).commit();
            } else {
                fragmentTransaction.add(R.id.frame_container, fragment, tag).commit();
            }
        }
    }
    /** events for terms of use */
    @OnClick(R.id.tvTermsOfUse)
    void termsOfUse() {
        UtilsMethods.openBrowser(this, Keys.TERM_OF_USE_URL);
    }


    /** initialize classes and perform listener */
    private void initialize() {

        marshmallowPermission = new MarshmallowPermission(this);
        rbLogin.setOnCheckedChangeListener(this);
        rbSignUp.setOnCheckedChangeListener(this);
        edPassword.setFilters(new InputFilter[]{filter});
        // edEmail.setFilters(new InputFilter[]{filter});
        // edEmailLogin.setText("pramod@infoicon.co.in");
        // edPasswordLogin.setText("OX03O1");
    }

    /** single line for editText */
    private void setEditTextSingleLine() {
        edFirstName.setSingleLine(true);
        edName.setSingleLine(true);
        edEmail.setSingleLine(true);
        edPassword.setSingleLine(true);
        edEmailLogin.setSingleLine(true);
        edPasswordLogin.setSingleLine(true);
        edPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        edPasswordLogin.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    /** set typeface to editText */
    private void setTypeFace() {
        Typeface font = Typeface.createFromAsset(this.getAssets(), "HelveticaNeueLTCom-LtEx.ttf");
        edFirstName.setTypeface(font);
        edName.setTypeface(font);
        edEmail.setTypeface(font);
        edPassword.setTypeface(font);
        edEmailLogin.setTypeface(font);
        edPasswordLogin.setTypeface(font);
        buttonSignUp.setTextAppearance(this, R.style.boldText);
        buttonLogin.setTextAppearance(this, R.style.boldText);
    }

    /** click event to perform login */
    @OnClick(R.id.buttonLogin)
    void login() {

        if (new MyFcmTokenModel(this).getToken().equals("")) {
            startService(new Intent(this, RefreshTokenService.class));
            CommonUtils.showSimpleMessageBottom(this, getString(R.string.error_network_unavailable));
            return;
        }

        UtilsMethods.hideSoftKeyboard(this);
        if (isLoginValidated()) {
            callWebServiceForLogin();
        }
    }

    /** click event to perform signup */
    @OnClick(R.id.tvForgotPassword)
    void forgotPassword() {
       Intent intent=new Intent(LoginSignUpSeekerActivity.this,
                ForgetPasswordActivity.class);
  intent.putExtra("type","Seeker");
  startActivity(intent);
    }

    /** click event to get register as a new user. */
    @OnClick(R.id.buttonSignUp)
    void signUp() {
        UtilsMethods.hideSoftKeyboard(this);
        if (isRegistrationValidated()) {
            if (imgDecodableString == null && vidDecodableString == null) {
                callWebServiceForRegWithoutFile();
            } else {
                getRegister();
            }
        }
    }

    /** perform click event to take photo from gallery/camera to upload on server. */
    @OnClick(R.id.imageViewPhoto)
    void openGalleryPhoto() {
        takePhotoOption();

    }

    /** perform click event to take video from gallery/camera to upload on server. */
    @OnClick(R.id.imageViewVideo)
    void openGalleryVideo() {
        if (!Singleton.getUserInfoInstance().isPitchInfoView()) {
            Singleton.getUserInfoInstance().setPitchInfoView(true);
            UtilsMethods.dialogPitchVideInfo(this);
        } else {
            takeVideoOption();
        }
    }


    /** event for go back from current activity */
    @OnClick(R.id.imageViewBack)
    void backGo() {
        finish();
    }

    /** taking photo dialog */
    private void takePhotoOption() {
        String[] permissions = {PermissionKeys.PERMISSION_CAMERA, PermissionKeys.PERMISSION_WRITE_EXTERNAL_STORAGE};
        if (marshmallowPermission.isPermissionGrantedAll(PermissionKeys.REQUEST_CODE_PERMISSION_ALL_PHOTO, permissions)) {
            new TakePhoto(this, new TakePhoto.CallbackTakePhoto() {
                @Override
                public void getPath(String imgDecodableStrings, String mCurrentVideoPaths) {
                    imgDecodableString = imgDecodableStrings;
                    mCurrentVideoPath = mCurrentVideoPaths;
                    onActivityUserPhotoCall = false;

                }
            }).dialogTakePic();
        }
    }

    /** taking video dialog */
    private void takeVideoOption() {
        String[] permissions = {PermissionKeys.PERMISSION_CAMERA, PermissionKeys.PERMISSION_WRITE_EXTERNAL_STORAGE};
        if (marshmallowPermission.isPermissionGrantedAll(PermissionKeys.REQUEST_CODE_PERMISSION_ALL_VIDEO, permissions)) {
            new TakeVideo(this, new TakeVideo.CallbackTakeVideo() {
                @Override
                public void getPath(String vidDecodableStrings, String mCurrentVideoPaths) {
                    vidDecodableString = vidDecodableStrings;
                    mCurrentVideoPath = mCurrentVideoPaths;
                    onActivityPitchVideoCall = false;
                }
            }).dialogTakeVideo();
        }
    }


    /** event for switch from login to sign up/sign up to login */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.rbLogin:
                visibleLogin();
                rbLogin.setTextColor(ContextCompat.getColor(this, R.color.txt_Color_blue));
                rbSignUp.setTextColor(ContextCompat.getColor(this, R.color.white));
                break;
            case R.id.rbSignUp:
                visibleSinUp();
                rbSignUp.setTextColor(ContextCompat.getColor(this, R.color.txt_Color_blue));
                rbLogin.setTextColor(ContextCompat.getColor(this, R.color.white));
                break;
            default:
                break;
        }
    }


    /**
     * load image from gallery
     * Create intent to Open Image applications like Gallery, Google Photos
     */
    private void loadImageFromGallery() {
        if (marshmallowPermission.isPermissionGranted(PermissionKeys.PERMISSION_WRITE_EXTERNAL_STORAGE, PermissionKeys.REQUEST_CODE_WRITE_EXTERNAL_STORAGE_1)) {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, Keys.RESULT_LOAD_IMG);
        }
    }

    /**
     * load video from gallery
     * Create intent to Open video applications like Gallery, Google Photos
     */
    private void loadVideoFromGallery() {
        if (marshmallowPermission.isPermissionGranted(PermissionKeys.PERMISSION_WRITE_EXTERNAL_STORAGE, PermissionKeys.REQUEST_CODE_WRITE_EXTERNAL_STORAGE_2)) {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, Keys.RESULT_LOAD_VID);
        }
    }


    /** get result back if permission is granted or not. */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionKeys.REQUEST_CODE_WRITE_EXTERNAL_STORAGE_1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadImageFromGallery();
                } else {
                    openAlert(requestCode, getResources().getString(R.string.external_storage_perm_mess));
                }
                break;
            case PermissionKeys.REQUEST_CODE_WRITE_EXTERNAL_STORAGE_2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadVideoFromGallery();
                } else {
                    openAlert(requestCode, getResources().getString(R.string.external_storage_perm_mess));
                }
                break;
            case PermissionKeys.REQUEST_CODE_PERMISSION_ALL_PHOTO:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    takePhotoOption();
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

    /** get intent data from other application like camera,gallery. */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Keys.LOCATION_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    String locationName = data.getStringExtra(Keys.LOCATION_NAME);
                    mLatitude = String.valueOf(data.getDoubleExtra
                            (Keys.LOCATION_LAT, 0.0));
                    mLongitude = String.valueOf(data.getDoubleExtra
                            (Keys.LOCATION_LONG, 0.0));
                    tvLocation.setText(locationName);
                }
                break;
            case Keys.DIPLOMA_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null)
                {

                    textViewDiploma.setText(data.getStringExtra(Keys.DIPLOMA));
                    diplomaId = data.getStringExtra(Keys.DIPLOMA_ID);
                }
                break;
            case Keys.RESULT_LOAD_IMG:
                if (resultCode == RESULT_OK && data != null) {
                    // Get the Image from data
                    Uri uri = data.getData();
                    imgDecodableString = UtilsMethods.getImageFromUri(this, uri);
                    imgDecodableString = UtilsMethods.compressImage(this, imgDecodableString);
                    Uri destination = Uri.fromFile(new File(UtilsMethods.getCropedPath()));
                    Crop.of(Uri.fromFile(new File(imgDecodableString)), destination).asSquare().start(this);
                }
                break;
            case Keys.RESULT_LOAD_VID:
                if (resultCode == RESULT_OK && data != null) {
                    // Get the Image from data
                    Uri uri = data.getData();
                    vidDecodableString = UtilsMethods.getVideoFromUri(this, uri);
                    long dur = getVideoDuration(vidDecodableString);

                    pitchVideoThumbNail = UtilsMethods.getThumbnailPathForLocalFile(LoginSignUpSeekerActivity.this, uri);
                    Logger.e("Thumbnail Path : " + pitchVideoThumbNail);

                    if (dur > 60) {
                        vidDecodableString = null;
                        openAlert(getResources().getString(R.string.video_length_message));
                    } else {
                        compressingVideo();
                    }
                }
                break;
            case Keys.TAKE_PHOTO_CODE:
                if (resultCode == RESULT_OK) {
                    imgDecodableString = UtilsMethods.compressImage(this, imgDecodableString);
                    Uri destination = Uri.fromFile(new File(UtilsMethods.getCropedPath()));
                    Crop.of(Uri.fromFile(new File(imgDecodableString)), destination).asSquare().start(this);
                }
                break;
            case Keys.TAKE_VIDEO_CODE:
                if (resultCode == RESULT_OK)
                {
                    Uri videoUri = Uri.parse(mCurrentVideoPath);
                    Bitmap bmThumbnail = ThumbnailUtils.createVideoThumbnail(videoUri.getPath(), MediaStore.Video.Thumbnails.MICRO_KIND);
                    pitchVideoThumbNail = UtilsMethods.saveImageToInternalStorage(LoginSignUpSeekerActivity.this, bmThumbnail);
                    Logger.e("Thumbnail take video2 : " + pitchVideoThumbNail);
                    compressingVideo();
                }
                break;

            case Crop.REQUEST_CROP:
                if (resultCode == RESULT_OK) {
                    onActivityUserPhotoCall = true;
                    imgDecodableString = Crop.getOutput(data).getPath();
                    imageViewPhoto.setImageURI(Crop.getOutput(data));
                } else if (resultCode == Crop.RESULT_ERROR) {
                    CommonUtils.showToast(this, Crop.getError(data).getMessage());
                }
                break;
            default:
                break;
        }
    }

    /** compressing video */
    private void compressingVideo() {
        new CompressVideo(this, vidDecodableString, (length, outpath) -> {
            if (length > AppConstants.VIDEO_MAX_SIZE) {
                vidDecodableString = null;
                imageViewVideo.setImageResource(R.drawable.play_icon);
                openAlert(getResources().getString(R.string.video_size_message));
            } else {
                vidDecodableString = outpath;
                onActivityPitchVideoCall = true;
                Uri videoUri = Uri.parse(vidDecodableString);
                // CommonUtils.showToast(LoginSignUpSeekerActivity.this, getResources().getString(R.string.compression_success));
                Bitmap bmThumbnail = ThumbnailUtils.createVideoThumbnail(videoUri.getPath(), MediaStore.Video.Thumbnails.MICRO_KIND);
                imageViewVideo.setImageBitmap(bmThumbnail);
            }
        }).compress();
    }

    /** call web service for get login */
    private void callWebServiceForLogin() {
        final SpotsDialog spotsDialog = new SpotsDialog(this, R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty(Keys.EMAIL, edEmailLogin.getText().toString().trim());
        jsonObject.addProperty(Keys.PASSWORD, edPasswordLogin.getText().toString().trim());
        jsonObject.addProperty(Keys.DEVICE_TOKEN, "");
        String token=new MyFcmTokenModel(this).getToken();
        jsonObject.addProperty(Keys.DEVICE_ID, token);

        retrofit.Call<GetLoginSeekerResponse> call = AppRetrofit.getAppRetrofitInstance()
                .getApiServices().getLoginSeekerResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetLoginSeekerResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetLoginSeekerResponse> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                GetLoginSeekerResponse getLoginSeekerResponse = response.body();
                if (getLoginSeekerResponse.isSuccess()) {

                    CommonUtils.showToast(LoginSignUpSeekerActivity.this, getLoginSeekerResponse.getMsg());
                    Singleton.getUserInfoInstance().setFirst_name(getLoginSeekerResponse.getData().get(0).getFirst_name());
                    Singleton.getUserInfoInstance().setLast_name(getLoginSeekerResponse.getData().get(0).getLast_name());
                    Singleton.getUserInfoInstance().setUsername(getLoginSeekerResponse.getData().get(0).getUsername());
                    Singleton.getUserInfoInstance().setEmail(getLoginSeekerResponse.getData().get(0).getEmail());
                    Singleton.getUserInfoInstance().setUser_id(getLoginSeekerResponse.getData().get(0).getUser_id());
                    Singleton.getUserInfoInstance().setAuthKey(getLoginSeekerResponse.getData().get(0).getAuthKey());
                    Singleton.getUserInfoInstance().setEmail_notification(getLoginSeekerResponse.getData().get(0).getEmail_notification());
                    Singleton.getUserInfoInstance().setMobile_notification(getLoginSeekerResponse.getData().get(0).getMobile_notification());
                    Singleton.getUserInfoInstance().setPassword(getLoginSeekerResponse.getData().get(0).getChat_password());
                    Singleton.getUserInfoInstance().setLogin(true);
                    Singleton.getUserInfoInstance().setLoginRecriter(false);
                    Singleton.getUserInfoInstance().setChatId(Keys.BONJOB_ + getLoginSeekerResponse.getData().get(0).getUser_id());
                    addUserOnFireBase(getLoginSeekerResponse);

                    if(getLoginSeekerResponse.getPrevLogined().equalsIgnoreCase("1"))
                    {
                        Singleton.getUserInfoInstance().setFirstTimeHomePopUpsSeeker(false);

                    }
                    else
                        {
                            Singleton.getUserInfoInstance().setFirstTimeHomePopUpsSeeker(true);
                    }
                    Singleton.getUserInfoInstance().setuser_pic(getLoginSeekerResponse.getData().get(0).getUser_pic());
                    Intent intent = new Intent(LoginSignUpSeekerActivity.this, HomeSeekerActivity.class);
                    intent.putExtra(Keys.FROM, "");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


                } else {
                    CommonUtils.showSimpleMessageBottom(LoginSignUpSeekerActivity.this, getLoginSeekerResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(LoginSignUpSeekerActivity.this, t.getMessage());
            }
        });
    }

    /**
     * Method  to Open Alert/Dialog
     * @param requestCode request code for identify the permission request code.
     * @param message     message will show on dialog.
     */
    public void openAlert(int requestCode, String message)
    {
        final Dialog dialog = new Dialog(this);
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
                    loadImageFromGallery();
                    break;
                case PermissionKeys.REQUEST_CODE_WRITE_EXTERNAL_STORAGE_2:
                    loadVideoFromGallery();
                    break;
                case PermissionKeys.REQUEST_CODE_PERMISSION_ALL_PHOTO:
                    takePhotoOption();
                    break;
                case PermissionKeys.REQUEST_CODE_PERMISSION_ALL_VIDEO:
                    takeVideoOption();
                    break;
                default:
                    break;
            }

        });
        buttonNo.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    /** show message on alert */
    public void openAlert(String message)
    {
        final Dialog dialog = new Dialog(this);
        if (dialog.getWindow() != null)
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_alert_ok);
        dialog.setCancelable(false);
        CustomsTextView textViewTitle = (CustomsTextView) dialog.findViewById(R.id.textViewTitle);
        CustomsTextView textViewMessage = (CustomsTextView) dialog.findViewById(R.id.textViewMessage);
        CustomsButton buttonOk = (CustomsButton) dialog.findViewById(R.id.buttonOk);
        textViewTitle.setText(getResources().getString(R.string.app_name));
        textViewMessage.setText(message);
        buttonOk.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

    /** validate registration field */
    private boolean isRegistrationValidated() {
        edFirstName.setError(null);
        edName.setError(null);
        tvLocation.setError(null);
        textViewExperience.setError(null);
        textViewDiploma.setError(null);
        edPassword.setError(null);



        if (UtilsMethods.isEmpty(edFirstName)) {
            showErrorAlert(this,getResources().getString(R.string.firstName_empty_message));
            requestFocus(edFirstName);
            return false;
        }
        if (UtilsMethods.isEmpty(edName)) {
            showErrorAlert(this,getResources().getString(R.string.name_empty_message));
            requestFocus(edName);
            return false;
        }
        if (UtilsMethods.isEmpty(edEmail)) {
            showErrorAlert(this,getResources().getString(R.string.email_empty_message));
            requestFocus(edEmail);
            return false;
        }
        if (!UtilsMethods.isValidEmail(edEmail.getText().toString().trim())) {
            showErrorAlert(this,getResources().getString(R.string.email_invalid_message));
            requestFocus(edEmail);
            return false;
        }



        if (UtilsMethods.isEmpty(tvLocation)) {

            showErrorAlert(this,getResources().getString(R.string.location_empty_message));
            requestFocus(tvLocation);
            return false;
        }

        if (UtilsMethods.isEmpty(textViewExperience)) {
            showErrorAlert(this,getResources().getString(R.string.select_experience));
            requestFocus(textViewExperience);
            return false;
        }
        if (UtilsMethods.isEmpty(textViewDiploma))
        {
            showErrorAlert(this,getResources().getString(R.string.tranning_empty_message));
            requestFocus(textViewDiploma);
            return false;
        }

        if (UtilsMethods.isEmpty(edPassword)) {
            showErrorAlert(this,getResources().getString(R.string.password_empty_message));
            requestFocus(edPassword);
            return false;
        }
        if (edPassword.getText().toString().trim().length() < 6) {
            showErrorAlert(this,getResources().getString(R.string.password_length));
            edPassword.requestFocus();
            return false;
        }
        if (!cbTermsConditionAccept.isChecked())
        {
            CommonUtils.showSimpleMessageBottom(this, getResources().getString(R.string.terms_condition_msg));
            return false;
        }
        return true;
    }

    /** filter to remove whitespace from editext */
    InputFilter filter = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            String filtered = "";
            for (int i = start; i < end; i++) {
                char character = source.charAt(i);
                if (!Character.isWhitespace(character)) {
                    filtered += character;
                }
            }
            return filtered;
        }
    };

    /** validate login field */
    private boolean isLoginValidated() {
        if (UtilsMethods.isEmpty(edEmailLogin)) {
            showErrorAlert(this,getResources().getString(R.string.username_empty_message));
            requestFocus(edEmailLogin);
            return false;
        }
        if (UtilsMethods.isEmpty(edPasswordLogin)) {
            showErrorAlert(this,getResources().getString(R.string.password_empty_message));
            requestFocus(edPasswordLogin);
            return false;
        }
        return true;
    }

    /**
     * focus to view which view have invalid.
     * @param view
     */
    private void requestFocus(View view)
    {
        if (view.requestFocus())
        {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    /** make visibility for sign up page */
    private void visibleSinUp() {
        experienceBeanList.clear();
        experienceBeanList.clear();
        edFirstName.setText("");
        edName.setText("");
        edEmail.setText("");
        edPassword.setText("");
        textViewDiploma.setText("");
        textViewExperience.setText("");
tvLocation.setText("");
        signup.setVisibility(View.VISIBLE);
        login.setVisibility(View.GONE);
    }

    /** make visibility for login up page */
    private void visibleLogin() {
        experienceBeanList.clear();
        edFirstName.setText("");
       edName.setText("");
        edEmail.setText("");
        edPassword.setText("");
        textViewDiploma.setText("");
        textViewExperience.setText("");
        tvLocation.setText("");
        signup.setVisibility(View.GONE);
        login.setVisibility(View.VISIBLE);
    }

    /** get duration form video path */
    private long getVideoDuration(String vidDecodableString)
    {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(vidDecodableString);
        String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        long timeInmillisec = Long.parseLong(time);
        long duration = timeInmillisec / 1000;
        return duration;

    }

    /** handle json object and multipart */
    private void getRegister() {

        JsonObject jsonObject = new JsonObject();
        try {
            jsonObject.addProperty(Keys.FIRST_NAME, edFirstName.getText().toString().trim());
            jsonObject.addProperty(Keys.LAST_NAME, edName.getText().toString().trim());
            jsonObject.addProperty(Keys.USERNAME, edEmail.getText().toString().trim());
            jsonObject.addProperty(Keys.EMAIL, edEmail.getText().toString().trim());
            jsonObject.addProperty(Keys.PASSWORD, edPassword.getText().toString().trim());
            jsonObject.addProperty(Keys.DEVICE_TOKEN, "");
            jsonObject.addProperty(Keys.DEVICE_ID, new MyFcmTokenModel(this).getToken());
            jsonObject.addProperty(Keys.USER_TYPE, Keys.SEEKER);
            jsonObject.addProperty(Keys.CITY,tvLocation.getText().toString());
            jsonObject.addProperty(Keys.LATTITUDE, mLatitude);
            jsonObject.addProperty(Keys.LONGITUDE,mLongitude);
            Gson gson = new Gson();
            JsonElement element = gson.fromJson(jArry.toString(), JsonElement.class);

            jsonObject.add(Keys.EXPERIENCE,element);
            jsonObject.addProperty(Keys.EDUCATION_LEVEL, diplomaId);
            jsonObject.addProperty(Keys.GENDER, "");

        } catch (Exception e) {
            e.printStackTrace();
        }
        multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        try {

            if (imgDecodableString != null && !imgDecodableString.equals("") && onActivityUserPhotoCall) {
                fileBodyImage = new FileBody(new File(imgDecodableString));
                multipartEntityBuilder.addPart(Keys.USER_PIC, fileBodyImage);
            }
            if (vidDecodableString != null && !vidDecodableString.equals("") && onActivityPitchVideoCall) {
                fileBodyVideo = new FileBody(new File(vidDecodableString));
                multipartEntityBuilder.addPart(Keys.PATCH_VIDEO, fileBodyVideo);
                try {
                    FileBody fileBodyVideoThumbNail = new FileBody(new File(pitchVideoThumbNail));
                    multipartEntityBuilder.addPart(Keys.PATCH_VIDEO_THUMBNAIL, fileBodyVideoThumbNail);
                } catch (Exception e) {
                }
            }

            multipartEntityBuilder.addPart(Keys.DATA, new StringBody(jsonObject.toString(), ContentType.TEXT_PLAIN.withCharset("UTF-8")));

            callServiceForReg(multipartEntityBuilder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** call service for registration with images */
    private void callServiceForReg(MultipartEntityBuilder multipartEntityBuilder) {
        final SpotsDialog spotsDialog = new SpotsDialog(this, R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        MultipartWebServiceCall multipartWebServiceCall = new MultipartWebServiceCall(LoginSignUpSeekerActivity.this, new INetworkResponse() {
            @Override
            public void onSuccess(String response) {

                JSONObject jsonObject = null;
                spotsDialog.dismiss();
                try {
                    jsonObject = new JSONObject(response);
                    String msg = jsonObject.getString(Keys.MESSAGE);
                    boolean success = jsonObject.getBoolean(Keys.SUCCESS);
                    if (success) {
                        CommonUtils.showToast(LoginSignUpSeekerActivity.this, msg);
                        emptyViewAfterReg();
                    } else {
                        JSONArray arrayData = JSONUtils.getJSONArrayFromJSON(jsonObject, Keys.DATA);
                        String email = JSONUtils.getStringFromJSON(arrayData.getJSONObject(0), Keys.EMAIL);
                        if (!email.equals(""))
                            CommonUtils.showSimpleMessageBottom(LoginSignUpSeekerActivity.this, email);
                        else
                            CommonUtils.showSimpleMessageBottom(LoginSignUpSeekerActivity.this, msg);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {
                spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(LoginSignUpSeekerActivity.this, error);
            }
        });
        multipartWebServiceCall.execute(multipartEntityBuilder, ServiceUrls.REGISTRATION_API_SEEKER);
    }


    /** call web service for registration without file upload */
    private void callWebServiceForRegWithoutFile()
    {
        final SpotsDialog spotsDialog = new SpotsDialog(this, R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.FIRST_NAME, edFirstName.getText().toString().trim());
        jsonObject.addProperty(Keys.LAST_NAME, edName.getText().toString().trim());
        jsonObject.addProperty(Keys.USERNAME, edEmail.getText().toString().trim());
        jsonObject.addProperty(Keys.EMAIL, edEmail.getText().toString().trim());
        jsonObject.addProperty(Keys.PASSWORD, edPassword.getText().toString().trim());
        jsonObject.addProperty(Keys.DEVICE_TOKEN, "");
        jsonObject.addProperty(Keys.DEVICE_ID,  new MyFcmTokenModel(this).getToken());
        jsonObject.addProperty(Keys.USER_TYPE, Keys.SEEKER);
        jsonObject.addProperty(Keys.GENDER, "");
        jsonObject.addProperty(Keys.CITY,tvLocation.getText().toString());
        jsonObject.addProperty(Keys.LATTITUDE, mLatitude);
        jsonObject.addProperty(Keys.LONGITUDE,mLongitude);
       /* Gson gson = new Gson();
        JsonElement element = gson.fromJson(jArry.toString(), JsonElement.class);
        jsonObject.add(Keys.EXPERIENCE,element);*/
        if(jArry.length()<=0)
        {
            try {
                JSONObject jsonObjects=new JSONObject();
                jsonObjects.put("position_held", "");
                jsonObjects.put("industry_type", "");
                jsonObjects.put("company_name", "");
                jsonObjects.put("description", "");
                jsonObjects.put("experience","1");
                jsonObjects.put("position_held_name", "");
                jsonObjects.put("industry_type_name","");
                jArry.put(jsonObjects);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        Gson gson = new Gson();
        JsonElement element = gson.fromJson(jArry.toString(), JsonElement.class);
        jsonObject.add(Keys.EXPERIENCE, element);

        jsonObject.addProperty(Keys.EDUCATION_LEVEL, diplomaId);

        retrofit.Call<GetRegistrationSeekerResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getRegistrationSeekerResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetRegistrationSeekerResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetRegistrationSeekerResponse> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                GetRegistrationSeekerResponse getRegistrationSeekerResponse = response.body();
                if (getRegistrationSeekerResponse.isSuccess()) {
                    CommonUtils.showToast(LoginSignUpSeekerActivity.this, getRegistrationSeekerResponse.getMsg());
                    addUserOnFireBaseOnRegistrations(getRegistrationSeekerResponse);
                    visibleLogin();
                    rbLogin.setTextColor(ContextCompat.getColor(LoginSignUpSeekerActivity.this, R.color.white));
                    rbSignUp.setTextColor(ContextCompat.getColor(LoginSignUpSeekerActivity.this, R.color.txt_Color_blue));
                    rbSignUp.setChecked(true);
                    rbLogin.setChecked(false);
                } else {
                    CommonUtils.showSimpleMessageBottom(LoginSignUpSeekerActivity.this, getRegistrationSeekerResponse.getData().get(0).getEmail());
                }
            }

            @Override
            public void onFailure(Throwable t)
            {
                spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(LoginSignUpSeekerActivity.this, t.getMessage());
            }
        });
    }

    /** make text and images empty after success registration */
    private void emptyViewAfterReg() {

        rbSignUp.setChecked(true);
        rbLogin.setChecked(false);
        visibleLogin();
        rbLogin.setTextColor(ContextCompat.getColor(LoginSignUpSeekerActivity.this, R.color.txt_Color_blue));
        rbSignUp.setTextColor(ContextCompat.getColor(LoginSignUpSeekerActivity.this, R.color.white));

        imgDecodableString = null;
        vidDecodableString = null;

        edFirstName.setText("");
        edName.setText("");
        edEmail.setText("");
        edPassword.setText("");

        imageViewPhoto.setImageResource(R.drawable.default_photo_deactive);
        imageViewVideo.setImageResource(R.drawable.play_icon);
        cbTermsConditionAccept.setChecked(false);
    }


    private void serviceForGetData()
    {
        final SpotsDialog spotsDialog = new
                SpotsDialog(this, R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();

        retrofit.Call<EductionLabelResponce> call = AppRetrofit.
                getAppRetrofitInstance().getApiServices().getGetEductionResponce
                (jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<EductionLabelResponce>() {
            @Override
            public void onResponse(retrofit.Response<EductionLabelResponce> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                EductionLabelResponce
                        getCompanyDetailsResponse = response.body();

                if (getCompanyDetailsResponse.isSuccess())
                {
                    Intent intent = new Intent(LoginSignUpSeekerActivity.this,
                            DiplomaDialogFragmentForRegister.class);
                    //Bundle bundle = new Bundle();
                    intent.putExtra("CompanyDetails",
                            getCompanyDetailsResponse);
                    startActivityForResult(intent, Keys.DIPLOMA_REQUEST_CODE);

                } else {
                        UtilsMethods.unAuthorizeAlert(LoginSignUpSeekerActivity.this,
                                getCompanyDetailsResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();

            }
        });
    }


    void addUserOnFireBase(GetLoginSeekerResponse getLoginRecruiterResponse) {
        //register user
        Users newUser = new Users();
        newUser.email = getLoginRecruiterResponse.getData().get(0).getEmail();
        newUser.first_name = getLoginRecruiterResponse.getData().get(0).getFirst_name();
        newUser.fcmToken = new MyFcmTokenModel(this).getToken();
        newUser.last_name = getLoginRecruiterResponse.getData().get(0).getLast_name();
        newUser.online_status = "0";
        newUser.lastOnlineTime = System.currentTimeMillis() / 1000;
        newUser.totalUnreadCount = 0;
        newUser.user_id = getLoginRecruiterResponse.getData().get(0).getUser_id();
        newUser.logOutStatus = 0;
        newUser.profilePic =getLoginRecruiterResponse.getData().get(0).getUser_pic();
        final DatabaseReference fireDatabaseReference = FirebaseDatabase.getInstance().getReference();
        fireDatabaseReference.
                child("seeker/" + getLoginRecruiterResponse.getData().get(0).getUser_id()).setValue(newUser);


        addUserOnFireBaseOnLogin(getLoginRecruiterResponse);


    }


    void addUserOnFireBaseOnRegistrations(GetRegistrationSeekerResponse getRegistrationRecruiterResponse) {
        FirebaseDatabase.
                getInstance().
                getReference()
                .child("admin/1")
                .
                        addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                HashMap mapUserInfo = (HashMap) dataSnapshot.getValue();
                                Message newMessage = new Message();
                                newMessage.setText(getString(R.string.msg_welcome_back));
                                newMessage.setFromId("1");
                                newMessage.setToId(getRegistrationRecruiterResponse.getData().get(0).getUser_id());
                                newMessage.setFcmToken(new MyFcmTokenModel(LoginSignUpSeekerActivity.this).getToken());
                                newMessage.setTimeStamp(System.currentTimeMillis() / 1000);

                                newMessage.setReadStatus("0");               //send msg
                                Message.setUserType from = new Message.setUserType();
                                from.setName("Assistant BonJob");
                                from.setType("admin");
                                from.setProfilePic((String) mapUserInfo.get("profilePic"));
                                Message.setUserType to = new Message.setUserType();
                                to.setName(getRegistrationRecruiterResponse.getData().get(0).getFirst_name() + "" + getRegistrationRecruiterResponse.getData().get(0).getLast_name());
                                to.setType("seeker");
                                to.setProfilePic("");

                                newMessage.setFrom(from);
                                newMessage.setTo(to);

                                final DatabaseReference mss = FirebaseDatabase.getInstance().getReference();
                                final String key = mss.child("Messages").push().getKey();
                                newMessage.setMessageID(key);
                                mss.child("Messages/" + key).setValue(newMessage).
                                        addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                                FirebaseDatabase.getInstance().getReference().child("read-Messages").
                                                        child(getRegistrationRecruiterResponse.getData().get(0).getUser_id()).child("1/unreadMessages")
                                                        .child(key).setValue(1);

                                                FirebaseDatabase.getInstance().getReference().
                                                        child("User-List/1/" + getRegistrationRecruiterResponse
                                                                .getData().get(0).getUser_id() + "/" + key).setValue(1);

                                                FirebaseDatabase.getInstance().getReference().
                                                        child("User-List/" + getRegistrationRecruiterResponse.getData()
                                                                .get(0).getUser_id() + "/1/" + key).setValue(1);

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Write failed
                                                // ...
                                            }
                                        });
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value

                            }
                        });
    }







    void addUserOnFireBaseOnLogin(GetLoginSeekerResponse getRegistrationRecruiterResponse) {
        FirebaseDatabase.
                getInstance().
                getReference()
                .child("admin/1")
                .
                        addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                HashMap mapUserInfo = (HashMap) dataSnapshot.getValue();
                                Message newMessage = new Message();
                                newMessage.setText(getString(R.string.msg_welcome_back));
                                newMessage.setFromId("1");
                                newMessage.setToId(getRegistrationRecruiterResponse.getData().get(0).getUser_id());
                                newMessage.setFcmToken(new MyFcmTokenModel(LoginSignUpSeekerActivity.this)
                                        .getToken());
                                newMessage.setTimeStamp(System.currentTimeMillis() / 1000);

                                newMessage.setReadStatus("0");               //send msg
                                Message.setUserType from = new Message.setUserType();
                                from.setName("Assistant BonJob");
                                from.setType("admin");
                                from.setProfilePic((String) mapUserInfo.get("profilePic"));
                                Message.setUserType to = new Message.setUserType();
                                to.setName(getRegistrationRecruiterResponse.getData().get(0).getFirst_name() + "" + getRegistrationRecruiterResponse.getData().get(0).getLast_name());
                                to.setType("seeker");
                                to.setProfilePic("");
                                newMessage.setFrom(from);
                                newMessage.setTo(to);

                                final DatabaseReference mss = FirebaseDatabase.getInstance().getReference();
                                final String key = mss.child("Messages").push().getKey();
                                newMessage.setMessageID(key);
                                mss.child("Messages/" + key).setValue(newMessage).
                                        addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                                FirebaseDatabase.getInstance().getReference().child("read-Messages").
                                                        child(getRegistrationRecruiterResponse.getData().get(0).getUser_id()).child("1/unreadMessages")
                                                        .child(key).setValue(1);

                                                FirebaseDatabase.getInstance().getReference().
                                                        child("User-List/1/" + getRegistrationRecruiterResponse
                                                                .getData().get(0).getUser_id() + "/" + key).setValue(1);

                                                FirebaseDatabase.getInstance().getReference().
                                                        child("User-List/" + getRegistrationRecruiterResponse.getData()
                                                                .get(0).getUser_id() + "/1/" + key).setValue(1);

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Write failed
                                                // ...
                                            }
                                        });
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value

                            }
                        });


    }
}
