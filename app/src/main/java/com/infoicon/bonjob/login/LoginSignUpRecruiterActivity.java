package com.infoicon.bonjob.login;

import android.app.Activity;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.chat.Message;
import com.infoicon.bonjob.customview.CircleImageView;
import com.infoicon.bonjob.customview.CustomCheckBox;
import com.infoicon.bonjob.customview.CustomRadioButton;
import com.infoicon.bonjob.customview.CustomsButton;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.fcm.MyFcmTokenModel;
import com.infoicon.bonjob.fcm.RefreshTokenService;
import com.infoicon.bonjob.forgotPassword.ForgetPasswordActivity;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.main.HomeRecruiterActivity;
import com.infoicon.bonjob.multipart.INetworkResponse;
import com.infoicon.bonjob.multipart.MultipartWebServiceCall;
import com.infoicon.bonjob.permission.MarshmallowPermission;
import com.infoicon.bonjob.permission.PermissionKeys;
import com.infoicon.bonjob.recruiters.verify_phone.VerifiedPhoneNumberActivity;
import com.infoicon.bonjob.recruiters.verify_phone.VerifyPhoneNumberActivity;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.ServiceUrls;
import com.infoicon.bonjob.retrofit.response.GetLoginRecruiterResponse;
import com.infoicon.bonjob.retrofit.response.GetRegistrationRecruiterResponse;
import com.infoicon.bonjob.retrofit.response.GetSocialLoginResponse;
import com.infoicon.bonjob.seeker.profile.PickLocationActivity;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.AppConstants;
import com.infoicon.bonjob.utils.CommonUtils;
import com.infoicon.bonjob.utils.CompressVideo;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.TakePhoto;
import com.infoicon.bonjob.utils.TakeVideo;
import com.infoicon.bonjob.utils.UtilsMethods;
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
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

import static com.infoicon.bonjob.utils.UtilsMethods.showErrorAlert;

public class LoginSignUpRecruiterActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private static final int MB_SIZE = 1024;
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
    @BindView(R.id.edCompanyName)
    EditText edCompanyName;
    @BindView(R.id.edEmailLogin)
    EditText edEmailLogin;
    @BindView(R.id.tvLocation)
    CustomsTextView tvLocation;
    @BindView(R.id.edMobile)
    EditText etMobile;
    @BindView(R.id.edPasswordLogin)
    EditText edPasswordLogin;
    @BindView(R.id.imageViewPhoto)
    CircleImageView imageViewPhoto;
    @BindView(R.id.imageViewPhotoEnterPrise)
    CircleImageView imageViewPhotoEnterPrise;
    @BindView(R.id.imageViewVideo)
    CircleImageView imageViewVideo;
    @BindView(R.id.cbTermsConditionAccept)
    CustomCheckBox cbTermsConditionAccept;
    @BindView(R.id.buttonSignUp)
    CustomsButton buttonSignUp;
    @BindView(R.id.buttonLogin)
    CustomsButton buttonLogin;

    private MarshmallowPermission marshmallowPermission;
    private String imgDecodableString;
    private String vidDecodableString;
    private String imgDecodableStringForCompany;
    private String mCurrentVideoPath;
    private String forPhotos;
    private String userphoto = "user_photo";
    private String company_photo = "company_photo";
    private String companyLat = "", companyLong = "";
    private boolean onActivityUserPhotoCall;
    private boolean onActivityPitchVideoCall;
    private boolean onActivityEnterprisePhotoCall;
    private String pitchVideoThumbNail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup_recruiter);
        ButterKnife.bind(this);
        initialize();
        setTypeFace();
        setEditTextSingleLine();
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
     * event for get terms of use
     */
    @OnClick(R.id.tvTermsOfUse)
    void termsOfUse() {
        UtilsMethods.openBrowser(this, Keys.TERM_OF_USE_URL);
    }

    /**
     * initialize classes and perform listener
     */
    private void initialize() {
        marshmallowPermission = new MarshmallowPermission(this);
        rbLogin.setOnCheckedChangeListener(this);
        rbSignUp.setOnCheckedChangeListener(this);
        edPassword.setFilters(new InputFilter[]{filter});
        //   edEmail.setFilters(new InputFilter[]{filter});
        //edEmailLogin.setText("pramod12@infoicon.co.in");
        // edPasswordLogin.setText("123456");
    }

    /**
     * single line for editText
     */
    private void setEditTextSingleLine() {
        edFirstName.setSingleLine(true);
        edName.setSingleLine(true);
        edEmail.setSingleLine(true);
        edCompanyName.setSingleLine(true);
        edPassword.setSingleLine(true);
        edEmailLogin.setSingleLine(true);
        edPasswordLogin.setSingleLine(true);
        edPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        edPasswordLogin.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    /**
     * set typeface to editText
     */
    private void setTypeFace() {
        Typeface font = Typeface.createFromAsset(this.getAssets(), "HelveticaNeueLTCom-LtEx.ttf");
        edFirstName.setTypeface(font);
        edName.setTypeface(font);
        edEmail.setTypeface(font);
        edPassword.setTypeface(font);
        edEmailLogin.setTypeface(font);
        edPasswordLogin.setTypeface(font);
        edCompanyName.setTypeface(font);
    }

    /**
     * filter to remove whitespace from editext
     */
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

    /**
     * click event to perform login
     */
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

    /**
     * click event to perform signup
     */
    @OnClick(R.id.tvForgotPassword)
    void forgotPassword() {
        Intent intent = new Intent(LoginSignUpRecruiterActivity.this,
                ForgetPasswordActivity.class);
        intent.putExtra("type", "recruiter");
        startActivity(intent);
    }

    /**
     * click event to get register as a new user.
     */
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

    /**
     * perform click event to take photo from gallery/camera to upload on server.
     */
    @OnClick(R.id.imageViewPhoto)
    void openGalleryPhoto() {
        forPhotos = userphoto;
        takePhotoOption();

    }

    /**
     * perform click event to take photo from gallery/camera to upload on server.
     */
    @OnClick(R.id.imageViewPhotoEnterPrise)
    void openGalleryForEnterprise() {
        forPhotos = company_photo;
        takePhotoOption();
    }

    /**
     * perform click event to take video from gallery/camera to upload on server.
     */
    @OnClick(R.id.imageViewVideo)
    void openGalleryVideo() {
        takeVideoOption();
    }

    /**
     * event for go back from current activity
     */
    @OnClick(R.id.imageViewBack)
    void backGo() {
        finish();
    }

    @OnClick(R.id.tvLocation)
    void getLocation() {
        Intent intent = new Intent(this, PickLocationActivity.class);
        startActivityForResult(intent, Keys.LOCATION_CODE);
    }

    private void takePhotoOption() {
        String[] permissions = {PermissionKeys.PERMISSION_CAMERA, PermissionKeys.PERMISSION_WRITE_EXTERNAL_STORAGE};
        if (marshmallowPermission.isPermissionGrantedAll(PermissionKeys.REQUEST_CODE_PERMISSION_ALL_PHOTO, permissions)) {
            new TakePhoto(this, new TakePhoto.CallbackTakePhoto() {
                @Override
                public void getPath(String imgDecodableStrings, String mCurrentVideoPaths) {
                    if (forPhotos.equals(userphoto)) {
                        onActivityUserPhotoCall = false;
                        imgDecodableString = imgDecodableStrings;
                        // mCurrentPhotoPath = mCurrentVideoPaths;
                    } else {
                        onActivityEnterprisePhotoCall = false;
                        imgDecodableStringForCompany = imgDecodableStrings;
                    }

                }
            }).dialogTakePic();
        }
    }

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

    /**
     * event for switch from login to sign up/sign up to login
     */
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

    /**
     * get result back if permission is granted or not.
     */
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

    /**
     * get intent data from other application like camera,gallery.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Keys.RESULT_LOAD_IMG:
                if (resultCode == RESULT_OK && data != null) {
                    // Get the Image from data
                    Uri uri = data.getData();
                    Uri destination = Uri.fromFile(new File(UtilsMethods.getCropedPath()));
                    //    Crop.of(uri, destination).asSquare().start(this);
                    if (forPhotos.equals(userphoto)) {
                        imgDecodableString = UtilsMethods.getImageFromUri(this, uri);
                        imgDecodableString = UtilsMethods.compressImage(this, imgDecodableString);
                        Crop.of(Uri.fromFile(new File(imgDecodableString)), destination).asSquare().start(this);
                    } else {
                        imgDecodableStringForCompany = UtilsMethods.getImageFromUri(this, uri);
                        imgDecodableStringForCompany = UtilsMethods.compressImage(this, imgDecodableStringForCompany);
                        Crop.of(Uri.fromFile(new File(imgDecodableStringForCompany)), destination).withAspect(AppConstants.ASPECT_RATIO_X, AppConstants.ASPECT_RATIO_Y).start(this);
                    }
                }

                break;
            case Keys.RESULT_LOAD_VID:
                if (resultCode == RESULT_OK && data != null) {
                    // Get the Image from data
                    Uri uri = data.getData();
                    vidDecodableString = UtilsMethods.getVideoFromUri(this, uri);
                    pitchVideoThumbNail = UtilsMethods.getThumbnailPathForLocalFile(LoginSignUpRecruiterActivity.this, uri);
                    Logger.e("Thumbnail Path : " + pitchVideoThumbNail);
                    long dur = getVideoDuration(vidDecodableString);
                    if (dur > 60) {
                        vidDecodableString = null;
                        openAlert(getResources().getString(R.string.video_length_message));
                    } else {
                        // Bitmap bmThumbnail = ThumbnailUtils.createVideoThumbnail(vidDecodableString, MediaStore.Video.Thumbnails.MICRO_KIND);
                        // imageViewVideo.setImageBitmap(bmThumbnail);
                        compressingVideo();
                    }
                }
                break;
            case Keys.TAKE_PHOTO_CODE:
                if (resultCode == RESULT_OK) {
                    Uri destination = Uri.fromFile(new File(UtilsMethods.getCropedPath()));

                    if (forPhotos.equals(userphoto)) {
                        imgDecodableString = UtilsMethods.compressImage(this, imgDecodableString);
                        Crop.of(Uri.fromFile(new File(imgDecodableString)), destination).asSquare().start(this);
                    } else {
                        imgDecodableStringForCompany = UtilsMethods.compressImage(this, imgDecodableStringForCompany);
                        Crop.of(Uri.fromFile(new File(imgDecodableStringForCompany)), destination).withAspect(AppConstants.ASPECT_RATIO_X, AppConstants.ASPECT_RATIO_Y).start(this);
                    }
                }
                break;
            case Keys.TAKE_VIDEO_CODE:
                if (resultCode == RESULT_OK) {
                    File file = new File(vidDecodableString);
                    long length = file.length();
                    length = length / MB_SIZE;

                    Uri videoUri = Uri.parse(mCurrentVideoPath);
                    Bitmap bmThumbnail = ThumbnailUtils.createVideoThumbnail(videoUri.getPath(), MediaStore.Video.Thumbnails.MICRO_KIND);
                    pitchVideoThumbNail = UtilsMethods.saveImageToInternalStorage(LoginSignUpRecruiterActivity.this, bmThumbnail);
                    Logger.e("Thumbnail take video2 : " + pitchVideoThumbNail);
                    compressingVideo();

                }
                break;
            case Keys.LOCATION_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    String locationName = data.getStringExtra(Keys.LOCATION_NAME);
                    companyLat = String.valueOf(data.getDoubleExtra(Keys.LOCATION_LAT, 0.0));
                    companyLong = String.valueOf(data.getDoubleExtra(Keys.LOCATION_LONG, 0.0));
                    tvLocation.setText(locationName);
                }
                break;
            case Crop.REQUEST_CROP:
                if (resultCode == RESULT_OK) {
                    if (forPhotos.equals(userphoto)) {
                        onActivityUserPhotoCall = true;
                        imgDecodableString = Crop.getOutput(data).getPath();
                        //compress image
                        //imgDecodableString = UtilsMethods.compressImage(this, imgDecodableString);
                        imageViewPhoto.setImageURI(Crop.getOutput(data));
                    } else {
                        onActivityEnterprisePhotoCall = true;
                        imgDecodableStringForCompany = Crop.getOutput(data).getPath();
                        //compress image
                        //imgDecodableStringForCompany = UtilsMethods.compressImage(this, imgDecodableStringForCompany);
                        imageViewPhotoEnterPrise.setImageURI(Crop.getOutput(data));
                    }
                } else if (resultCode == Crop.RESULT_ERROR) {
                    CommonUtils.showToast(this, Crop.getError(data).getMessage());
                }
                break;
            default:
                break;
        }
    }

    /**
     * compressing video
     */
    private void compressingVideo() {
        new CompressVideo(this, vidDecodableString, (length, outpath) -> {
            if (length > AppConstants.VIDEO_MAX_SIZE) {
                vidDecodableString = null;
                imageViewVideo.setImageResource(R.drawable.play_icon);
                openAlert(getResources().getString(R.string.video_size_message));
            } else {
                onActivityPitchVideoCall = true;
                vidDecodableString = outpath;
                Uri videoUri = Uri.parse(vidDecodableString);
                CommonUtils.showToast(LoginSignUpRecruiterActivity.this, getResources().getString(R.string.compression_success));
                Bitmap bmThumbnail = ThumbnailUtils.createVideoThumbnail(videoUri.getPath(), MediaStore.Video.Thumbnails.MICRO_KIND);
                imageViewVideo.setImageBitmap(bmThumbnail);
            }
        }).compress();
    }

    /**
     * call web service for get login
     */
    private void callWebServiceForLogin() {
        final SpotsDialog spotsDialog = new SpotsDialog(this, R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.EMAIL, edEmailLogin.getText().toString().trim());
        jsonObject.addProperty(Keys.PASSWORD, edPasswordLogin.getText().toString().trim());
        jsonObject.addProperty(Keys.DEVICE_TOKEN, "");
        String token = new MyFcmTokenModel(this).getToken();
        jsonObject.addProperty(Keys.DEVICE_ID, token);
        retrofit.Call<GetLoginRecruiterResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getLoginRecruiterResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetLoginRecruiterResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetLoginRecruiterResponse> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                GetLoginRecruiterResponse getLoginRecruiterResponse = response.body();
                if (getLoginRecruiterResponse.isSuccess()) {
                    Singleton.getUserInfoInstance().setFirst_name(getLoginRecruiterResponse.getData().get(0).getFirst_name());
                    Singleton.getUserInfoInstance().setLast_name(getLoginRecruiterResponse.getData().get(0).getLast_name());
                    Singleton.getUserInfoInstance().setEmail(getLoginRecruiterResponse.getData().get(0).getEmail());
                    Singleton.getUserInfoInstance().setUser_id(getLoginRecruiterResponse.getData().get(0).getUser_id());
                    Singleton.getUserInfoInstance().setAuthKey(getLoginRecruiterResponse.getData().get(0).getAuthKey());
                    Singleton.getUserInfoInstance().setEmail_notification(getLoginRecruiterResponse.getData().get(0).getEmail_notification());
                    Singleton.getUserInfoInstance().setMobile_notification(getLoginRecruiterResponse.getData().get(0).getMobile_notification());
                    Singleton.getUserInfoInstance().setMobile_number(getLoginRecruiterResponse.getData().get(0).getMobile_number());
                    Singleton.getUserInfoInstance().setPassword(getLoginRecruiterResponse.getData().get(0).getChat_password());
                    Singleton.getUserInfoInstance().setCompanyAddress(getLoginRecruiterResponse.getData().get(0).getAddress());
                    Singleton.getUserInfoInstance().setuser_pic(getLoginRecruiterResponse.getData().get(0).getUser_pic());
                    Singleton.getUserInfoInstance().setEnterprise_name(getLoginRecruiterResponse
                            .getData().get(0)
                            .getEnterprise_name());
                    if (getLoginRecruiterResponse.getData().get(0).getIs_admin().equalsIgnoreCase("1")) {
                        addUserOnFireBase(getLoginRecruiterResponse, "admin");
                        Singleton.getUserInfoInstance().setLoginAdmin(true);
                    } else {
                        addUserOnFireBase(getLoginRecruiterResponse, "recruiter");
                        Singleton.getUserInfoInstance().setLoginRecriter(true);
                    }

                    Singleton.getUserInfoInstance().
                            setChatId(Keys.BONJOB_ + getLoginRecruiterResponse.getData().get(0).getUser_id());
                    if (getLoginRecruiterResponse.getPrevLogined().equalsIgnoreCase("1")) {

                        Singleton.getUserInfoInstance().setFirstTimeHomePopUpRecruiter(false);

                    } else {
                        Singleton.getUserInfoInstance().setFirstTimeHomePopUpRecruiter(true);
                    }

                    /*if (getLoginRecruiterResponse.getData().get(0).getMobile_number().equals("")) {
                        Singleton.getUserInfoInstance().setLogin(false);
                        Intent intent = new Intent(LoginSignUpRecruiterActivity.this, VerifyPhoneNumberActivity.class);
                        startActivity(intent);
                    } else {*/
                    //  CommonUtils.showToast(LoginSignUpRecruiterActivity.this, getLoginRecruiterResponse.getMsg());
                    Singleton.getUserInfoInstance().setLogin(true);
                    Intent intent = new Intent(LoginSignUpRecruiterActivity.this,
                            HomeRecruiterActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra(Keys.FROM, "");
                    startActivity(intent);
                    // }
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {
                    CommonUtils.showSimpleMessageBottom(LoginSignUpRecruiterActivity.this, getLoginRecruiterResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(LoginSignUpRecruiterActivity.this, t.getMessage());
            }
        });
    }

    /**
     * Method  to Open Alert/Dialog
     *
     * @param requestCode request code for identify the permission request code.
     * @param message     message will show on dialog.
     */
    public void openAlert(int requestCode, String message) {
        final Dialog dialog = new Dialog(this);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_alert);
        dialog.setCancelable(false);
        CustomsTextView textViewTitle = dialog.findViewById(R.id.textViewTitle);
        CustomsTextView textViewMessage = dialog.findViewById(R.id.textViewMessage);
        CustomsButton buttonYes = dialog.findViewById(R.id.buttonYes);
        CustomsButton buttonNo = dialog.findViewById(R.id.buttonNo);
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

    /**
     * show message on alert
     */
    public void openAlert(String message) {
        final Dialog dialog = new Dialog(this);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_alert_ok);
        dialog.setCancelable(false);
        CustomsTextView textViewTitle = dialog.findViewById(R.id.textViewTitle);
        CustomsTextView textViewMessage = dialog.findViewById(R.id.textViewMessage);
        CustomsButton buttonOk = dialog.findViewById(R.id.buttonOk);
        textViewTitle.setText(getResources().getString(R.string.app_name));
        textViewMessage.setText(message);
        buttonOk.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

    /**
     * validate registration field
     */
    private boolean isRegistrationValidated() {
        if (UtilsMethods.isEmpty(edFirstName)) {
            showErrorAlert(LoginSignUpRecruiterActivity.this,
                    getResources().getString(R.string.firstName_empty_message));
            //  edFirstName.setError(getResources().getString(R.string.firstName_empty_message));
            requestFocus(edFirstName);
            return false;
        }
        if (UtilsMethods.isEmpty(edName)) {
            showErrorAlert(this, getResources().getString(R.string.name_empty_message));
            requestFocus(edName);
            return false;
        }

        if (UtilsMethods.isEmpty(edCompanyName)) {
            showErrorAlert(this, getResources().getString(R.string.enterprise_empty_message));
            requestFocus(edCompanyName);
            return false;
        }

        if (UtilsMethods.isEmpty(tvLocation)) {
            tvLocation.setHint(getResources().getString(R.string.location_empty_message));
            requestFocus(tvLocation);
            return false;
        }

        if (UtilsMethods.isEmpty(edEmail)) {
            showErrorAlert(this, getResources().getString(R.string.email_empty_message));
            requestFocus(edEmail);
            return false;
        }
        if (UtilsMethods.isEmpty(etMobile)) {
            showErrorAlert(this, getResources().getString(R.string.enter_mobile_number));
            requestFocus(etMobile);
            return false;
        }
        if (!UtilsMethods.isValidEmail(edEmail.getText().toString().trim())) {
            showErrorAlert(this, getResources().getString(R.string.email_invalid_message));
            requestFocus(edEmail);
            return false;
        }
        if (UtilsMethods.isEmpty(edPassword)) {
            showErrorAlert(this, getResources().getString(R.string.password_empty_message));
            requestFocus(edPassword);
            return false;
        }

        if (edPassword.getText().toString().trim().length() < 6) {
            showErrorAlert(this, getResources().getString(R.string.password_length));
            edPassword.requestFocus();
            return false;
        }

        if (!cbTermsConditionAccept.isChecked()) {
            CommonUtils.showSimpleMessageBottom(this, getResources().getString(R.string.terms_condition_msg));
            return false;
        }
        return true;
    }

    /**
     * validate login field
     */
    private boolean isLoginValidated() {
        //  edEmailLogin.setText("sushant2@infoicon.co.in");
        //edPasswordLogin.setText("123");
        //edEmailLogin.setText("bonjobrecruiter@gmail.com");
        //   edPasswordLogin.setText("123456");
        if (UtilsMethods.isEmpty(edEmailLogin)) {
            showErrorAlert(this, getResources().getString(R.string.username_empty_message));
            requestFocus(edEmailLogin);
            return false;
        }
        if (UtilsMethods.isEmpty(edPasswordLogin)) {
            showErrorAlert(this, getResources().getString(R.string.password_empty_message));
            requestFocus(edPasswordLogin);
            return false;
        }
        return true;
    }

    /**
     * focus to view which view have invalid.
     *
     * @param view
     */
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    /**
     * make visibility for sign up page
     */
    private void visibleSinUp() {
        signup.setVisibility(View.VISIBLE);
        login.setVisibility(View.GONE);
    }

    /**
     * make visibility for login up page
     */
    private void visibleLogin() {
        signup.setVisibility(View.GONE);
        login.setVisibility(View.VISIBLE);
    }

    /**
     * get duration form video path
     */
    private long getVideoDuration(String vidDecodableString) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(vidDecodableString);
        String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        long timeInmillisec = Long.parseLong(time);
        long duration = timeInmillisec / 1000;
        return duration;
    }

    /**
     * handle json onject and multipart
     */
    private void getRegister() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Keys.FIRST_NAME, edFirstName.getText().toString().trim());
            jsonObject.put(Keys.LAST_NAME, edName.getText().toString().trim());
            jsonObject.put(Keys.ENTERPRISE_NAME, edCompanyName.getText().toString().trim());
            jsonObject.put(Keys.ADDRESS, tvLocation.getText().toString().trim());
            jsonObject.put(Keys.MOBILE, etMobile.getText().toString().trim());
            //   jsonObject.put(Keys.USERNAME, edEmail.getText().toString().trim());
            jsonObject.put(Keys.EMAIL, edEmail.getText().toString().trim());
            jsonObject.put(Keys.PASSWORD, edPassword.getText().toString().trim());
            jsonObject.put(Keys.DEVICE_TOKEN, "");
            jsonObject.put(Keys.DEVICE_ID, new MyFcmTokenModel(this).getToken());
            jsonObject.put(Keys.USER_TYPE, Keys.EMPLOYER);
            jsonObject.put(Keys.COMPANY_LAT, companyLat);
            jsonObject.put(Keys.COMPANY_LONG, companyLong);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        try {
            if (imgDecodableString != null && !imgDecodableString.equals("") && onActivityUserPhotoCall) {
                FileBody fileBodyImage = new FileBody(new File(imgDecodableString));
                multipartEntityBuilder.addPart(Keys.USER_PIC, fileBodyImage);
            }
            if (imgDecodableStringForCompany != null && !imgDecodableStringForCompany.equals("") && onActivityEnterprisePhotoCall) {
                FileBody fileBodyImage = new FileBody(new File(imgDecodableStringForCompany));
                multipartEntityBuilder.addPart(Keys.ENTERPRISE_PIC, fileBodyImage);
            }
            if (vidDecodableString != null && !vidDecodableString.equals("") && onActivityPitchVideoCall) {
                FileBody fileBodyVideo = new FileBody(new File(vidDecodableString));
                multipartEntityBuilder.addPart(Keys.PATCH_VIDEO, fileBodyVideo);
                try {
                    FileBody fileBodyVideoThumbNail = new FileBody(new File(pitchVideoThumbNail));
                    multipartEntityBuilder.addPart(Keys.PATCH_VIDEO_THUMBNAIL, fileBodyVideoThumbNail);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            multipartEntityBuilder.addPart(Keys.DATA, new StringBody(jsonObject.toString(), ContentType.TEXT_PLAIN.withCharset("UTF-8")));
            callServiceForReg(multipartEntityBuilder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * call service for registration with images
     */
    private void callServiceForReg(MultipartEntityBuilder multipartEntityBuilder) {
        final SpotsDialog spotsDialog = new SpotsDialog(this, R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        MultipartWebServiceCall multipartWebServiceCall = new MultipartWebServiceCall(LoginSignUpRecruiterActivity.this, new INetworkResponse() {
            @Override
            public void onSuccess(String response) {
                spotsDialog.dismiss();
                GetRegistrationRecruiterResponse getRegistrationRecruiterResponse =
                        new Gson().fromJson(response,
                                GetRegistrationRecruiterResponse.class);

                if (getRegistrationRecruiterResponse.isSuccess()) {
                    CommonUtils.showToast(LoginSignUpRecruiterActivity.this, getRegistrationRecruiterResponse.getMsg());

                    CommonUtils.showToast(LoginSignUpRecruiterActivity.this, getRegistrationRecruiterResponse.getMsg());
                    Singleton.getUserInfoInstance().setFirst_name(getRegistrationRecruiterResponse.getData().get(0).getFirst_name());
                    Singleton.getUserInfoInstance().setLast_name(getRegistrationRecruiterResponse.getData().get(0).getLast_name());
                    Singleton.getUserInfoInstance().setEmail(getRegistrationRecruiterResponse.getData().get(0).getEmail());
                    Singleton.getUserInfoInstance().setUser_id(getRegistrationRecruiterResponse.getData().get(0).getUser_id());
                    Singleton.getUserInfoInstance().setAuthKey(getRegistrationRecruiterResponse.getData().get(0).getAuthKey());
                    Singleton.getUserInfoInstance().setEmail_notification(getRegistrationRecruiterResponse.getData().get(0).getEmail_notification());
                    Singleton.getUserInfoInstance().setMobile_notification(getRegistrationRecruiterResponse.getData().get(0).getMobile_notification());
                    Singleton.getUserInfoInstance().setMobile_number(getRegistrationRecruiterResponse.getData().get(0).getMobile_number());
                    Singleton.getUserInfoInstance().setPassword(getRegistrationRecruiterResponse.getData().get(0).getChat_password());
                    Singleton.getUserInfoInstance().setCompanyAddress(getRegistrationRecruiterResponse.getData().get(0).getAddress());
                    Singleton.getUserInfoInstance().setEnterprise_name(getRegistrationRecruiterResponse.getData().get(0).getEnterprise_name());
                    Singleton.getUserInfoInstance().setLogin(false);
                    Singleton.getUserInfoInstance().setLoginRecriter(true);
                    Singleton.getUserInfoInstance().setChatId(Keys.BONJOB_ + getRegistrationRecruiterResponse.getData().get(0).getUser_id());
                   /* Intent intent = new Intent(LoginSignUpRecruiterActivity.this, VerifyPhoneNumberActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                  */
                    visibleLogin();
                    rbLogin.setTextColor(ContextCompat.getColor(LoginSignUpRecruiterActivity.this, R.color.txt_Color_blue));
                    rbSignUp.setTextColor(ContextCompat.getColor(LoginSignUpRecruiterActivity.this, R.color.white));
                } else {
                    CommonUtils.showSimpleMessageBottom(LoginSignUpRecruiterActivity.this, getRegistrationRecruiterResponse.getData().get(0).getEmail());
                }
            }

            @Override
            public void onError(String error) {
                spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(LoginSignUpRecruiterActivity.this, error);
            }
        });
        multipartWebServiceCall.execute(multipartEntityBuilder, ServiceUrls.REGISTRATION_API_RECRUITER);
    }

    /**
     * call web service for registration without file upload
     */
    private void callWebServiceForRegWithoutFile() {
        final SpotsDialog spotsDialog = new SpotsDialog(this, R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.FIRST_NAME, edFirstName.getText().toString().trim());
        jsonObject.addProperty(Keys.LAST_NAME, edName.getText().toString().trim());
        jsonObject.addProperty(Keys.ENTERPRISE_NAME, edCompanyName.getText().toString().trim());
        jsonObject.addProperty(Keys.ADDRESS, tvLocation.getText().toString().trim());
        // jsonObject.addProperty(Keys.USERNAME, edEmail.getText().toString().trim());
        jsonObject.addProperty(Keys.EMAIL, edEmail.getText().toString().trim());
        jsonObject.addProperty(Keys.MOBILE, etMobile.getText().toString().trim());

        jsonObject.addProperty(Keys.PASSWORD, edPassword.getText().toString().trim());
        jsonObject.addProperty(Keys.DEVICE_TOKEN, "");
        jsonObject.addProperty(Keys.DEVICE_ID, new MyFcmTokenModel(this).getToken());
        jsonObject.addProperty(Keys.USER_TYPE, Keys.EMPLOYER);
        jsonObject.addProperty(Keys.COMPANY_LAT, companyLat);
        jsonObject.addProperty(Keys.COMPANY_LONG, companyLong);

        retrofit.Call<GetRegistrationRecruiterResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getRegistrationRecruiterResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetRegistrationRecruiterResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetRegistrationRecruiterResponse> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                GetRegistrationRecruiterResponse getRegistrationRecruiterResponse = response.body();
                if (getRegistrationRecruiterResponse.isSuccess()) {
                    CommonUtils.showToast(LoginSignUpRecruiterActivity.this, getRegistrationRecruiterResponse.getMsg());
                    CommonUtils.showToast(LoginSignUpRecruiterActivity.this, getRegistrationRecruiterResponse.getMsg());
                    Singleton.getUserInfoInstance().setFirst_name(getRegistrationRecruiterResponse.getData().get(0).getFirst_name());
                    Singleton.getUserInfoInstance().setLast_name(getRegistrationRecruiterResponse.getData().get(0).getLast_name());
                    Singleton.getUserInfoInstance().setEmail(getRegistrationRecruiterResponse.getData().get(0).getEmail());
                    Singleton.getUserInfoInstance().setUser_id(getRegistrationRecruiterResponse.getData().get(0).getUser_id());
                    Singleton.getUserInfoInstance().setAuthKey(getRegistrationRecruiterResponse.getData().get(0).getAuthKey());
                    Singleton.getUserInfoInstance().setEmail_notification(getRegistrationRecruiterResponse.getData().get(0).getEmail_notification());
                    Singleton.getUserInfoInstance().setMobile_notification(getRegistrationRecruiterResponse.getData().get(0).getMobile_notification());
                    Singleton.getUserInfoInstance().setMobile_number(getRegistrationRecruiterResponse.getData().get(0).getMobile_number());
                    Singleton.getUserInfoInstance().setPassword(getRegistrationRecruiterResponse.getData().get(0).getChat_password());
                    Singleton.getUserInfoInstance().setCompanyAddress(getRegistrationRecruiterResponse.getData().get(0).getAddress());
                    Singleton.getUserInfoInstance().setEnterprise_name(getRegistrationRecruiterResponse.getData().get(0).getEnterprise_name());
                    Singleton.getUserInfoInstance().setuser_pic(getRegistrationRecruiterResponse.getData().get(0).getUser_pic());
                    Singleton.getUserInfoInstance().setLogin(false);
                    Singleton.getUserInfoInstance().setLoginRecriter(true);
                    Singleton.getUserInfoInstance().setChatId(Keys.BONJOB_ + getRegistrationRecruiterResponse.getData().get(0).getUser_id());
                    addUserOnFireBaseOnRegistrations(getRegistrationRecruiterResponse);
                /* Intent intent = new Intent(LoginSignUpRecruiterActivity.this, VerifyPhoneNumberActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
      */
                      } else {
                    CommonUtils.showSimpleMessageBottom(LoginSignUpRecruiterActivity.this, getRegistrationRecruiterResponse.getData().get(0).getEmail());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(LoginSignUpRecruiterActivity.this, t.getMessage());
            }
        });
    }

    //make text and images empty after success registration
    private void emptyViewAfterReg() {

        rbSignUp.setChecked(true);
        rbLogin.setChecked(false);
        visibleLogin();
        rbLogin.setTextColor(ContextCompat.getColor(LoginSignUpRecruiterActivity.this, R.color.txt_Color_blue));
        rbSignUp.setTextColor(ContextCompat.getColor(LoginSignUpRecruiterActivity.this, R.color.white));

        imgDecodableString = null;
        vidDecodableString = null;
        imgDecodableStringForCompany = null;

        edFirstName.setText("");
        edName.setText("");
        edCompanyName.setText("");
        tvLocation.setText("");
        edEmail.setText("");
        edPassword.setText("");

        imageViewPhoto.setImageResource(R.drawable.default_photo_deactive);
        imageViewPhotoEnterPrise.setImageResource(R.drawable.blue_home);
        imageViewVideo.setImageResource(R.drawable.play_icon);
        cbTermsConditionAccept.setChecked(false);
    }


    void addUserOnFireBase(GetLoginRecruiterResponse getLoginRecruiterResponse, String type)
    {
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
                child(type + "/" + getLoginRecruiterResponse.getData().get(0).getUser_id()).setValue(newUser);
        addUserOnFireBaseOnLogin(getLoginRecruiterResponse, type);
    }


    void addUserOnFireBaseOnRegistrations(GetRegistrationRecruiterResponse getRegistrationRecruiterResponse) {
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
                                newMessage.setFcmToken(new MyFcmTokenModel(LoginSignUpRecruiterActivity.this).getToken());
                                newMessage.setTimeStamp(System.currentTimeMillis() / 1000);

                                newMessage.setReadStatus("0");               //send msg
                                Message.setUserType from = new Message.setUserType();
                                from.setName("Assistant BonJob");
                                from.setType("admin");
                                from.setProfilePic((String) mapUserInfo.get("profilePic"));
                                Message.setUserType to = new Message.setUserType();
                                to.setName(getRegistrationRecruiterResponse.getData().get(0).getFirst_name() + "" + getRegistrationRecruiterResponse.getData().get(0).getLast_name());
                                to.setType("recruiter");
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
        visibleLogin();
        rbLogin.setTextColor(ContextCompat.getColor(LoginSignUpRecruiterActivity.this, R.color.white));
        rbSignUp.setTextColor(ContextCompat.getColor(LoginSignUpRecruiterActivity.this, R.color.txt_Color_blue));
        rbSignUp.setChecked(true);
        rbLogin.setChecked(false);

    }




    void addUserOnFireBaseOnLogin(GetLoginRecruiterResponse getRegistrationRecruiterResponse, String type) {
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
                                newMessage.setFcmToken(new MyFcmTokenModel(LoginSignUpRecruiterActivity.this)
                                        .getToken());
                                newMessage.setTimeStamp(System.currentTimeMillis() / 1000);

                                newMessage.setReadStatus("0");               //send msg
                                Message.setUserType from = new Message.setUserType();
                                from.setName("Assistant BonJob");
                                from.setType("admin");
                                from.setProfilePic((String) mapUserInfo.get("profilePic"));
                                Message.setUserType to = new Message.setUserType();
                                to.setName(getRegistrationRecruiterResponse.getData().get(0).getFirst_name() + "" + getRegistrationRecruiterResponse.getData().get(0).getLast_name());
                                to.setType(""+type);
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