package com.infoicon.bonjob.recruiters.profile;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomEditText;
import com.infoicon.bonjob.customview.CustomsButton;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.dialogs.CompanyCategoryDialogFragment;
import com.infoicon.bonjob.dialogs.SalaryEdProfileDialogFragment;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.multipart.MultipartWebServiceCall;
import com.infoicon.bonjob.permission.MarshmallowPermission;
import com.infoicon.bonjob.permission.PermissionKeys;
import com.infoicon.bonjob.picasso.ImageLoader;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.ServiceUrls;
import com.infoicon.bonjob.retrofit.response.GetPhotoVideoRemoveResponse;
import com.infoicon.bonjob.retrofit.response.GetRecruiterProfileResponse;
import com.infoicon.bonjob.seeker.profile.GetEmpProfileDropdownsResponce;
import com.infoicon.bonjob.seeker.profile.GetJobSoughtFragmentResponce;
import com.infoicon.bonjob.seeker.profile.PickLocationActivity;
import com.infoicon.bonjob.seeker.searchJob.PlayVideoFullSreenActivity;
import com.infoicon.bonjob.servicesConnection.INetworkResponse;
import com.infoicon.bonjob.servicesConnection.WebServiceCall;
import com.infoicon.bonjob.singletons.Singleton;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;

import static android.app.Activity.RESULT_OK;
import static com.infoicon.bonjob.utils.UtilsMethods.showErrorAlert;

@SuppressLint("ValidFragment")
public class EditProfileRecruiterFragment extends Fragment {
    GetEmpProfileDropdownsResponce getEmpProfileDropdownsResponce;
    private static final int RESULT_LOAD_IMG = 1;
    private static final int RESULT_LOAD_VID = 2;
    private static final int MB_SIZE = 1024;
    private static final int VIDEO_MAX_SIZE = 2048;
    View rootView;
    @BindView(R.id.edFName) CustomEditText edFName;
    @BindView(R.id.edName) CustomEditText edName;
    @BindView(R.id.edEnterprise) CustomEditText edEnterprise;
    @BindView(R.id.edAbout) CustomEditText edAbout;
    @BindView(R.id.edWebsite) CustomEditText edWebsite;
    @BindView(R.id.imageViewPhotoView) CircularImageView imageViewPhotoView;
    @BindView(R.id.imageViewPitchVideoView) CircularImageView imageViewPitchVideoView;
    @BindView(R.id.imageViewCompanyPhotoView) CircularImageView imageViewCompanyPhotoView;
    @BindView(R.id.tvLocation) CustomsTextView tvLocation;
    @BindView(R.id.textViewCategoryEnterprise) CustomsTextView textViewCategoryEnterprise;
    @BindView(R.id.textViewSalary) CustomsTextView textViewSalary;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    @BindView(R.id.tvRemoveUserPic) CustomsTextView tvRemoveUserPic;
    @BindView(R.id.tvRemovePatchVideo) CustomsTextView tvRemovePatchVideo;
    @BindView(R.id.tvRemoveEnterprisePhoto) CustomsTextView tvRemoveEnterprisePhoto;


    private String categoryEnterprisePosition = "",categoryEnterprisePositionId="",salaryPosition = "",salaryPositionId="";
    private MarshmallowPermission marshmallowPermission;
    private String imgDecodeStringPhoto;
    private String imgDecodeStringCompanyPhoto;
    private String vidDecodeString;
    private String mCurrentVideoPath;
    private String TAG = this.getClass().getSimpleName();
    private String USER = "user", COMPANY = "company";  //key for use same method to take video
    private String forWhich;
    private ProfileRecruiterFragment profileRecruiterFragment;

    private boolean onActivityUserPhotoCall;
    private boolean onActivityPitchVideoCall;
    private boolean onActivityEnterprisePhotoCall;
    private String companyLat = "", companyLong = "";
    private String pitchVideoThumbNail;

    private String urlPatchVideo;
    private Unbinder unbinder;

    @SuppressLint("ValidFragment")
    public EditProfileRecruiterFragment(ProfileRecruiterFragment
                                                    profileRecruiterFragment)
    {
        this.profileRecruiterFragment = profileRecruiterFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_edit_profile_recruiter, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        serviceForGetData();
        return rootView;
    }

    @Override
    public void onViewCreated(View view,
                              @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initialize();
        getProfileData();
    }

    /** set profile data to edit profile screen */
    private void getProfileData()
    {
        if (profileRecruiterFragment.dataBeanProfile != null)
        {
            GetRecruiterProfileResponse.DataBean dataBean =
                   profileRecruiterFragment.dataBeanProfile;
            edFName.setText(dataBean.getFirst_name());
            edName.setText(dataBean.getLast_name());
            edEnterprise.setText(dataBean.getEnterprise_name());
            tvLocation.setText(dataBean.getCity());
            textViewCategoryEnterprise.setText(dataBean.getCompany_category_name());
            categoryEnterprisePositionId = dataBean.getCompany_category();
            textViewSalary.setText(dataBean.getSalary_name());
            salaryPositionId=dataBean.getSalary();
            edAbout.setText(dataBean.getAbout());
            if (dataBean.getWebsite() != null && !dataBean.getWebsite().equals(""))
                edWebsite.setText(dataBean.getWebsite().toLowerCase());
            companyLat = dataBean.getCompany_lat();
            companyLong = dataBean.getCompany_long();

            /** set user pic */
            if (dataBean.getUser_pic() != null && !dataBean.getUser_pic().equals("")) {
                tvRemoveUserPic.setVisibility(View.VISIBLE);
                if (dataBean.isUserPicChange())
                    imageViewPhotoView.setImageBitmap(BitmapFactory.decodeFile(dataBean.getUser_pic()));
                else
                    ImageLoader.loadImage(getActivity(), dataBean.getUser_pic(), imageViewPhotoView);
            } else {
                tvRemoveUserPic.setVisibility(View.GONE);
            }
            /** set enterprise pic */
            if (dataBean.getEnterprise_pic() != null && !dataBean.getEnterprise_pic().equals("")) {
                tvRemoveEnterprisePhoto.setVisibility(View.VISIBLE);
                if (dataBean.isEnterPrisePicChange())
                    imageViewCompanyPhotoView.setImageBitmap(BitmapFactory.decodeFile(dataBean.getEnterprise_pic()));
                else
                    ImageLoader.loadImage(getActivity(), dataBean.getEnterprise_pic(), imageViewCompanyPhotoView);
            } else {
                tvRemoveEnterprisePhoto.setVisibility(View.GONE);
            }
            /** set thumbnail and get pitchVideo */
            if (dataBean.getPatch_video_thumbnail() != null && !dataBean.getPatch_video_thumbnail().equals("") &&
                    dataBean.getPatch_video() != null && !dataBean.getPatch_video().equals("")) {
                tvRemovePatchVideo.setVisibility(View.VISIBLE);
                urlPatchVideo = dataBean.getPatch_video();
                if (dataBean.isVideoChange())
                    imageViewPitchVideoView.setImageBitmap(BitmapFactory.decodeFile(dataBean.getPatch_video_thumbnail()));
                else
                    ImageLoader.loadImage(getActivity(), dataBean.getPatch_video_thumbnail(), imageViewPitchVideoView);
            } else {
                tvRemovePatchVideo.setVisibility(View.GONE);
            }

        }
    }

    private void initialize() {
        marshmallowPermission = new MarshmallowPermission(getActivity());
        edAbout.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);


        edWebsite.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                if (!s.equals(s.toLowerCase())) {
                    s = s.toLowerCase();
                    edWebsite.setText(s);
                    edWebsite.setSelection(edWebsite.getText().toString().length());
                }
            }
        });

        /*edWebsite.setFilters(new InputFilter[]{
                new InputFilter.AllCaps() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        return String.valueOf(source).toLowerCase();
                    }
                }
        });*/

    }


    @OnClick(R.id.imgViewSalary)
    void getSalary() {
        FragmentManager fm = getFragmentManager();
        SalaryEdProfileDialogFragment salaryDialogFragment = new SalaryEdProfileDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Keys.DATA, getEmpProfileDropdownsResponce);

        bundle.putString(Keys.SELECTED_POSITION, salaryPosition);
        salaryDialogFragment.setArguments(bundle);
        salaryDialogFragment.setTargetFragment(this, Keys.SALARY_REQUEST_CODE);
        salaryDialogFragment.show(fm, Keys.TAG_SALARY);
    }

    @OnClick(R.id.imgViewCategoryEnterprise)
    void categoryEnterprise() {
        FragmentManager fm = getFragmentManager();
        CompanyCategoryDialogFragment companyCategoryDialogFragment = new CompanyCategoryDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Keys.DATA, getEmpProfileDropdownsResponce);

        bundle.putString(Keys.SELECTED_POSITION, categoryEnterprisePosition);
        companyCategoryDialogFragment.setArguments(bundle);
        companyCategoryDialogFragment.setTargetFragment(this, Keys.COMPANY_CATEGORY_REQUEST_CODE);
        companyCategoryDialogFragment.show(fm, Keys.TAG_COMPANY_CATEGORY);
    }

    /** event for pick location */
    @OnClick(R.id.tvLocation)
    void getLocation() {
        Intent intent = new Intent(getActivity(), PickLocationActivity.class);
        startActivityForResult(intent, Keys.LOCATION_CODE);
    }

    /** event for take photo for user pic */
    @OnClick(R.id.imageViewPhoto)
    void takeImage() {
        takePhotoOption(USER);
    }

    /** event for take video for pitch video */
    @OnClick(R.id.imageViewPitchVideo)
    void takeVideo() {
        takeVideoOption();
    }

    /** event for take photo for enterprise pic */
    @OnClick(R.id.imageViewCompanyPhoto)
    void takeImageCompany() {
        takePhotoOption(COMPANY);
    }

    /** event for call edit profile service */
    @OnClick(R.id.tvSave)
    void updateProfile() {
        if (validateField()) {
            callEditProfileService();
        }
    }

    /** event to remove user photo */
    @OnClick(R.id.tvRemoveUserPic)
    void removeUserPhoto() {
        UtilsMethods.removeConfirmation(getActivity(), () -> removePhotoVideo(Keys.USER_PIC));

    }

    /** event to remove enterprise photo */
    @OnClick(R.id.tvRemoveEnterprisePhoto)
    void removeEnterPrisePhoto() {
        UtilsMethods.removeConfirmation(getActivity(), () -> removePhotoVideo(Keys.ENTERPRISE_PIC));

    }

    /** event to remove patch Video */
    @OnClick(R.id.tvRemovePatchVideo)
    void removePatchVideo() {
        UtilsMethods.removeConfirmation(getActivity(), () -> removePhotoVideo(Keys.PATCH_VIDEO));

    }

    @OnClick(R.id.imageViewPhotoView)
    void viewUserPhoto() {
        if (onActivityUserPhotoCall || !profileRecruiterFragment.dataBeanProfile.getUser_pic().equals("")) {
            dialogViewPhoto(Keys.USER_PIC);
        }

    }

    @OnClick(R.id.imageViewCompanyPhotoView)
    void viewEnterprisePhoto() {
        if (onActivityPitchVideoCall || !profileRecruiterFragment.dataBeanProfile.getEnterprise_pic().equals("")) {
            dialogViewPhoto(Keys.ENTERPRISE_PIC);
        }
    }

    @OnClick(R.id.imageViewPitchVideoView)
    void viewPatchVideo() {
        if (onActivityPitchVideoCall || !profileRecruiterFragment.dataBeanProfile.getPatch_video().equals("")) {
            dialogViewVideo();
        }
    }

    /** back */
    @OnClick(R.id.textViewBack)
    void goBack() {
        (getActivity()).onBackPressed();
    }

    /** method for taking photo */

    public void takePhotoOption(String forWhich) {
        this.forWhich = forWhich;
        String[] permissions = {PermissionKeys.PERMISSION_CAMERA, PermissionKeys.PERMISSION_WRITE_EXTERNAL_STORAGE};
        if (marshmallowPermission.isPermissionGrantedAll(PermissionKeys.REQUEST_CODE_PERMISSION_ALL_PHOTO, permissions)) {
            new TakePhoto(getActivity(), (imgDecodeStrings, mCurrentPhotoPaths) -> {
                if (forWhich.equals(USER)) {
                    imgDecodeStringPhoto = imgDecodeStrings;
                    onActivityUserPhotoCall = false;
                } else {
                    imgDecodeStringCompanyPhoto = imgDecodeStrings;
                    onActivityEnterprisePhotoCall = false;
                }
            }).dialogTakePic();
        }
    }

    /** method for get video from camera/gallery */
    private void takeVideoOption() {
        String[] permissions = {PermissionKeys.PERMISSION_CAMERA, PermissionKeys.PERMISSION_WRITE_EXTERNAL_STORAGE};
        if (marshmallowPermission.isPermissionGrantedAll(PermissionKeys.REQUEST_CODE_PERMISSION_ALL_VIDEO, permissions)) {
            new TakeVideo(getActivity(), (vidDecodeStrings, mCurrentVideoPaths) -> {
                vidDecodeString = vidDecodeStrings;
                mCurrentVideoPath = mCurrentVideoPaths;
                onActivityPitchVideoCall = false;
            }).dialogTakeVideo();
        }
    }


    /**
     * load image from gallery
     * Create intent to Open Image applications like Gallery, Google Photos
     */
    private void loadImagefromGallery() {
        if (marshmallowPermission.isPermissionGranted(PermissionKeys.PERMISSION_WRITE_EXTERNAL_STORAGE, PermissionKeys.REQUEST_CODE_WRITE_EXTERNAL_STORAGE_1)) {
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

    /**
     * getting data for edit profile from field
     */
    private void callEditProfileService() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Keys.USER_ID, Singleton.getUserInfoInstance().getUser_id());
            jsonObject.put(Keys.FIRST_NAME, edFName.getText().toString().trim());
            jsonObject.put(Keys.LAST_NAME, edName.getText().toString().trim());
            jsonObject.put(Keys.ENTERPRISE_NAME, edEnterprise.getText().toString().trim());
            jsonObject.put(Keys.CITY, tvLocation.getText().toString().trim());
            jsonObject.put(Keys.COMPANY_CAEGORY, categoryEnterprisePositionId);
            jsonObject.put(Keys.SALARY, salaryPositionId);
            jsonObject.put(Keys.ABOUT, edAbout.getText().toString().trim());
            jsonObject.put(Keys.WEBSITE, edWebsite.getText().toString().trim().toLowerCase());
            jsonObject.put(Keys.USER_TYPE, Keys.EMPLOYER);
            jsonObject.put(Keys.COMPANY_LAT, companyLat);
            jsonObject.put(Keys.COMPANY_LONG, companyLong);
            jsonObject.put(Keys.DEVICE_TOKEN, "");
            jsonObject.put(Keys.DEVICE_ID, "");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        try {
            if (imgDecodeStringPhoto != null && !imgDecodeStringPhoto.equals("") && onActivityUserPhotoCall) {
                FileBody fileBodyImage = new FileBody(new File(imgDecodeStringPhoto));
                multipartEntityBuilder.addPart(Keys.USER_PIC, fileBodyImage);
            }
            if (imgDecodeStringCompanyPhoto != null && !imgDecodeStringCompanyPhoto.equals("") && onActivityEnterprisePhotoCall) {
                FileBody fileBodyImage = new FileBody(new File(imgDecodeStringCompanyPhoto));
                multipartEntityBuilder.addPart(Keys.ENTERPRISE_PIC, fileBodyImage);
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

          //  if (!onActivityUserPhotoCall && !onActivityEnterprisePhotoCall && !onActivityPitchVideoCall) {
                callEditProfileServiceWithoutFile(jsonObject);
          //  }
            /*else {
                multipartEntityBuilder.addPart(Keys.DATA, new StringBody(jsonObject.toString(), ContentType.TEXT_PLAIN.withCharset("UTF-8")));
                callEditProfileServiceWithFile(multipartEntityBuilder);
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    private void callServiceDataForEditProfileImages(String type) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Keys.USER_ID, Singleton.getUserInfoInstance().getUser_id());
            jsonObject.put(Keys.USER_TYPE, Keys.EMPLOYER);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        try {
            if (type.equalsIgnoreCase("image") ){
                FileBody fileBodyImage = new FileBody(new File(imgDecodeStringPhoto));
                multipartEntityBuilder.addPart(Keys.USER_PIC, fileBodyImage);
            }
            if (type.equalsIgnoreCase("company_image")) {
                FileBody fileBodyImage = new FileBody(new File(imgDecodeStringCompanyPhoto));
                multipartEntityBuilder.addPart(Keys.ENTERPRISE_PIC, fileBodyImage);
            }
            if (type.equalsIgnoreCase("video")) {
                FileBody fileBodyVideo = new FileBody(new File(vidDecodeString));
                multipartEntityBuilder.addPart(Keys.PATCH_VIDEO, fileBodyVideo);
                try {
                    FileBody fileBodyVideoThumbNail = new FileBody(new File(pitchVideoThumbNail));
                    multipartEntityBuilder.addPart(Keys.PATCH_VIDEO_THUMBNAIL, fileBodyVideoThumbNail);
                } catch (Exception e) {
                }
            }

                multipartEntityBuilder.addPart(Keys.DATA, new StringBody(jsonObject.toString(), ContentType.TEXT_PLAIN.withCharset("UTF-8")));
            callEditProfileServiceWithFile(multipartEntityBuilder,type);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }






    /** call service for update profile without images */
    private void callEditProfileServiceWithoutFile(JSONObject jsonObject) {
        WebServiceCall webServiceCall = new WebServiceCall(getActivity(), new INetworkResponse() {
            @Override
            public void onSuccess(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean(Keys.SUCCESS);
                    String message = jsonObject.getString(Keys.MESSAGE);
                    if (success) {
                        changeProfileData(false);
                        openAlert(message);
                    } else {
                        if (jsonObject.getString(Keys.ACTIVE_USER).equals(Keys.AUTH_CODE)) {
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
        webServiceCall.execute(jsonObject, ServiceUrls.RECRUITER_EDIT_PROFILE);
    }

    /** call service for update profile  with file */
    private void callEditProfileServiceWithFile(MultipartEntityBuilder multipartEntityBuilder,
                                                String type) {
        final SpotsDialog spotsDialog = new SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();


        MultipartWebServiceCall multipartWebServiceCall = new MultipartWebServiceCall(getActivity(), new com.infoicon.bonjob.multipart.INetworkResponse() {
            @Override
            public void onSuccess(String response) {
                JSONObject jsonObject;
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
                                child("recruiter/" + Singleton.getUserInfoInstance().getUser_id()+"/profilePic")
                                .setValue(user_pic_json.getString(Keys.USER_PIC));
                        Singleton.getUserInfoInstance().setuser_pic(user_pic_json.getString(Keys.USER_PIC));

                        changeProfileData(true);
                        //openAlert(msg);
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
        }  if (type.equalsIgnoreCase("company_image")) {
            multipartWebServiceCall.execute(multipartEntityBuilder, ServiceUrls.uploadEnterprisePic);
        }
        if (type.equalsIgnoreCase("video")) {
            multipartWebServiceCall.execute(multipartEntityBuilder, ServiceUrls.uploadPatchVideo);
        }    }

    private void serviceForGetData()
    {
        final SpotsDialog spotsDialog = new
                SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();

        retrofit.Call<GetEmpProfileDropdownsResponce> call =
                AppRetrofit.getAppRetrofitInstance().
                getApiServices().getGetEmpProfileDropdownsResponce
                (jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetEmpProfileDropdownsResponce>() {
            @Override
            public void onResponse(retrofit.Response<GetEmpProfileDropdownsResponce> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                getEmpProfileDropdownsResponce = response.body();

                if (getEmpProfileDropdownsResponce.isSuccess())
                {


                } else {
                    if (getEmpProfileDropdownsResponce.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(getActivity(), getEmpProfileDropdownsResponce.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();

            }
        });
    }
    /** change profile data after updated profile */
    private void changeProfileData(boolean isUploadFile) {
        Singleton.getUserInfoInstance().setCompanyAddress(tvLocation.getText().toString().trim());
        Singleton.getUserInfoInstance().setEnterprise_name(edEnterprise.getText().toString().trim());
        if (profileRecruiterFragment.dataBeanProfile != null) {
            GetRecruiterProfileResponse.DataBean dataBean = profileRecruiterFragment.dataBeanProfile;
            dataBean.setFirst_name(edFName.getText().toString().trim());
            dataBean.setLast_name(edName.getText().toString().trim());
            dataBean.setEnterprise_name(edEnterprise.getText().toString().trim());
            dataBean.setCity(tvLocation.getText().toString().trim());
            dataBean.setCompany_category_name(textViewCategoryEnterprise.getText().toString());
            dataBean.setCompany_category(categoryEnterprisePositionId);
            dataBean.setSalary_name(textViewSalary.getText().toString().trim());
            dataBean.setSalary(salaryPositionId);
            dataBean.setAbout(edAbout.getText().toString().trim());
            dataBean.setWebsite(edWebsite.getText().toString().trim().toLowerCase());
            if (isUploadFile) {
                if (imgDecodeStringPhoto != null && !imgDecodeStringPhoto.equals("")) {
                    dataBean.setUserPicChange(true);
                    dataBean.setUser_pic(imgDecodeStringPhoto);
                }
                if (imgDecodeStringCompanyPhoto != null && !imgDecodeStringCompanyPhoto.equals("")) {
                    dataBean.setEnterPrisePicChange(true);
                    dataBean.setEnterprise_pic(imgDecodeStringCompanyPhoto);
                }
                if (vidDecodeString != null && !vidDecodeString.equals("")) {
                    dataBean.setVideoChange(true);
                    dataBean.setPatch_video(vidDecodeString);
                    dataBean.setPatch_video_thumbnail(pitchVideoThumbNail);
                }
            }
        }
    }


    /**
     * validate the field
     */
    private boolean validateField() {
        if (UtilsMethods.isEmpty(edFName)) {
            showErrorAlert(getActivity(),getResources().getString(R.string.firstName_empty_message));
            requestFocus(edFName);
            return false;
        }
        if (UtilsMethods.isEmpty(edName)) {
            showErrorAlert(getActivity(),getResources().getString(R.string.name_empty_message));
            return false;
        }
       /*if (UtilsMethods.isEmpty(tvLocation)) {
            tvLocation.setHint(getResources().getString(R.string.required));
            tvLocation.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.red_color));
            requestFocus(tvLocation);
            return false;
        }

        if (!UtilsMethods.isEmpty(edWebsite) && !Patterns.WEB_URL.matcher(edWebsite.getText().toString().trim()).matches()) {
            edWebsite.setError(getResources().getString(R.string.invalid_url));
            requestFocus(edWebsite);
            return false;
        }
*/
        return true;
    }

    /**
     * focus to view which view have invalid.
     * @param view focus here
     */
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    /** get result after taking photo/video from gallery/camera */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_LOAD_IMG:
                if (resultCode == RESULT_OK && data != null) {
                    // Get the Image from data
                    Uri uri = data.getData();
                    Uri destination = Uri.fromFile(new File(UtilsMethods.getCropedPath()));

                    if (forWhich.equals(USER)) {
                        imgDecodeStringPhoto = UtilsMethods.getImageFromUri(getActivity(), uri);
                        imgDecodeStringPhoto = UtilsMethods.compressImage(getActivity(), imgDecodeStringPhoto);
                        Crop.of(Uri.fromFile(new File(imgDecodeStringPhoto)), destination).asSquare().start(getActivity());
                    } else {
                        imgDecodeStringCompanyPhoto = UtilsMethods.getImageFromUri(getActivity(), uri);
                        imgDecodeStringCompanyPhoto = UtilsMethods.compressImage(getActivity(), imgDecodeStringCompanyPhoto);
                        Crop.of(Uri.fromFile(new File(imgDecodeStringCompanyPhoto)), destination).withAspect(AppConstants.ASPECT_RATIO_X, AppConstants.ASPECT_RATIO_Y).start(getActivity());
                    }


                }
                break;
            case RESULT_LOAD_VID:
                if (resultCode == RESULT_OK && data != null) {
                    // Get the Image from data
                    Uri selectedImage = data.getData();
                    vidDecodeString = UtilsMethods.getVideoFromUri(getActivity(), selectedImage);
                    pitchVideoThumbNail = UtilsMethods.getThumbnailPathForLocalFile(getActivity(), selectedImage);
                    Logger.e("Thumbnail Path : " + pitchVideoThumbNail);
                    long dur = getVideoDuration(vidDecodeString);
                    if (dur > 60) {
                        vidDecodeString = null;
                        UtilsMethods.openCustumAlert(getActivity(), getResources().getString(R.string.video_length_message));
                    } else {
                        Bitmap bmThumbnail = ThumbnailUtils.createVideoThumbnail(vidDecodeString, MediaStore.Video.Thumbnails.MICRO_KIND);
                        imageViewPitchVideoView.setImageBitmap(bmThumbnail);
                        callServiceDataForEditProfileImages("video");

                       // compressingVideo();
                    }
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
            case Keys.TAKE_PHOTO_CODE:
                if (resultCode == RESULT_OK) {
                    Uri destination = Uri.fromFile(new File(UtilsMethods.getCropedPath()));

                    if (forWhich.equals(USER)) {
                        imgDecodeStringPhoto = UtilsMethods.compressImage(getActivity(), imgDecodeStringPhoto);
                        Crop.of(Uri.fromFile(new File(imgDecodeStringPhoto)), destination).asSquare().start(getActivity());
                    } else {
                        imgDecodeStringCompanyPhoto = UtilsMethods.compressImage(getActivity(), imgDecodeStringCompanyPhoto);
                        Crop.of(Uri.fromFile(new File(imgDecodeStringCompanyPhoto)), destination).withAspect(AppConstants.ASPECT_RATIO_X, AppConstants.ASPECT_RATIO_Y).start(getActivity());
                    }
                }

                break;
            case Keys.TAKE_VIDEO_CODE:
                if (resultCode == RESULT_OK) {
                    File file = new File(vidDecodeString);
                    long length = file.length();
                    length = length / MB_SIZE;
                    Logger.e(TAG + " :   File Path : " + file.getPath() + ",check File size : " + length + " KB");

                    //Uri selectedImage = data.getData();
                    //   pitchVideoThumbNail = UtilsMethods.getThumbnailPathForLocalFile(getActivity(), selectedImage);

                    Uri videoUri = Uri.parse(mCurrentVideoPath);
                    Bitmap bmThumbnail = ThumbnailUtils.createVideoThumbnail(videoUri.getPath(), MediaStore.Video.Thumbnails.MICRO_KIND);
                    pitchVideoThumbNail = UtilsMethods.saveImageToInternalStorage(getActivity(), bmThumbnail);
                    Logger.e("Thumbnail take video2 : " + pitchVideoThumbNail);
                    Bitmap bmThumbnails = ThumbnailUtils.createVideoThumbnail(vidDecodeString, MediaStore.Video.Thumbnails.MICRO_KIND);
                    imageViewPitchVideoView.setImageBitmap(bmThumbnails);
                   // compressingVideo();
                    /*if (length > VIDEO_MAX_SIZE) {
                        compressingVideo();
                    } else {
                        // Uri videoUri = Uri.parse(mCurrentVideoPath);
                        // Bitmap bmThumbnail = ThumbnailUtils.createVideoThumbnail(videoUri.getPath(), MediaStore.Video.Thumbnails.MICRO_KIND);
                        imageViewPitchVideoView.setImageBitmap(bmThumbnail);
                        onActivityPitchVideoCall = true;
                        //   pitchVideoThumbNail= UtilsMethods.saveImageToInternalStorage(getActivity(),bmThumbnail);
                        //   Logger.e("Thumbnail take video2 : " + pitchVideoThumbNail);
                    }*/
                    callServiceDataForEditProfileImages("video");
                }
                break;
            case Keys.SALARY_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    salaryPosition = data.getStringExtra(Keys.POSITION);
                    salaryPositionId = data.getStringExtra(Keys.SALARY_ID);
                    textViewSalary.setText(data.getStringExtra(Keys.SALARY));
                }
                break;
            case Keys.COMPANY_CATEGORY_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    categoryEnterprisePosition =
                            data.getStringExtra(Keys.POSITION);
                    categoryEnterprisePositionId =
                            data.getStringExtra(Keys.COMPANY_CAEGORY_Id);
                    textViewCategoryEnterprise.setText(data.getStringExtra(Keys.COMPANY_CAEGORY));
                }
                break;
            case Crop.REQUEST_CROP:
                if (resultCode == RESULT_OK) {
                    if (forWhich.equals(USER)) {
                        onActivityUserPhotoCall = true;
                        imgDecodeStringPhoto = Crop.getOutput(data).getPath();
                        //compress image
                        imgDecodeStringPhoto = UtilsMethods.compressImage(getActivity(), imgDecodeStringPhoto);
                        imageViewPhotoView.setImageURI(Crop.getOutput(data));
                        callServiceDataForEditProfileImages("image");
                    } else {
                        onActivityEnterprisePhotoCall = true;
                        imgDecodeStringCompanyPhoto = Crop.getOutput(data).getPath();
                        //compress image
                        imgDecodeStringCompanyPhoto = UtilsMethods.compressImage(getActivity(), imgDecodeStringCompanyPhoto);
                        imageViewCompanyPhotoView.setImageURI(Crop.getOutput(data));
                        callServiceDataForEditProfileImages("company_image");
                    }

                } else if (resultCode == Crop.RESULT_ERROR) {
                    CommonUtils.showToast(getActivity(), Crop.getError(data).getMessage());
                }
                break;

            default:
                break;
        }
    }


    /** get result back if permission is granted or not. */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
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
                    takePhotoOption(forWhich);
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
                Uri videoUri = Uri.parse(vidDecodeString);
                CommonUtils.showToast(getActivity(), getResources().getString(R.string.compression_success));
                Bitmap bmThumbnail = ThumbnailUtils.createVideoThumbnail(videoUri.getPath(), MediaStore.Video.Thumbnails.MICRO_KIND);
                imageViewPitchVideoView.setImageBitmap(bmThumbnail);
            }
        }).compress();
    }

    /** get duration form video path */
    private long getVideoDuration(String vidDecodeString) {
        Uri uri = Uri.parse(vidDecodeString);
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(getActivity(), uri);
        String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        long timeInMilliSec = Long.parseLong(time);
        return timeInMilliSec / 1000;
    }

    /**
     * Method  to Open Alert/Dialog
     * @param requestCode code to identify the permission request code
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
                    takePhotoOption(forWhich);
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


    /** service for remove photo and video */
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
                        profileRecruiterFragment.dataBeanProfile.setUser_pic("");
                        tvRemoveUserPic.setVisibility(View.GONE);
                        final DatabaseReference fireDatabaseReference = FirebaseDatabase.getInstance().getReference();

                        fireDatabaseReference.
                                child("recruiter/" + Singleton.getUserInfoInstance().getUser_id()+"/profilePic")
                                .setValue("");

                    } else if (fieldName.equals(Keys.ENTERPRISE_PIC)) {
                        imageViewCompanyPhotoView.setImageResource(R.drawable.home_grey);
                        onActivityEnterprisePhotoCall = false;
                        profileRecruiterFragment.dataBeanProfile.setEnterprise_pic("");
                        tvRemoveEnterprisePhoto.setVisibility(View.GONE);
                    } else if (fieldName.equals(Keys.PATCH_VIDEO)) {
                        imageViewPitchVideoView.setImageResource(R.drawable.play_icon_deactive);
                        onActivityPitchVideoCall = false;
                        profileRecruiterFragment.dataBeanProfile.setPatch_video("");
                        profileRecruiterFragment.dataBeanProfile.setPatch_video_thumbnail("");
                        tvRemovePatchVideo.setVisibility(View.GONE);
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
    public void dialogViewPhoto(String eventFrom) {
        final Dialog dialogVideoInfo = new Dialog(getActivity());
        dialogVideoInfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialogVideoInfo.getWindow() != null)
            dialogVideoInfo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (eventFrom.equals(Keys.USER_PIC)) {
            dialogVideoInfo.setContentView(R.layout.dialog_photo_view_square);
        } else {
            dialogVideoInfo.setContentView(R.layout.dialog_photo_view);
        }
        ImageView imageViewClose = (ImageView) dialogVideoInfo.findViewById(R.id.imageViewClose);
        ImageView imageViewPhoto = (ImageView) dialogVideoInfo.findViewById(R.id.imageViewPhoto);
        CustomsTextView tvRemove = (CustomsTextView) dialogVideoInfo.findViewById(R.id.tvRemove);

        if (eventFrom.equals(Keys.USER_PIC)) {
            if (!onActivityUserPhotoCall) {
                tvRemove.setVisibility(View.VISIBLE);
                //check if profile change with new image which is local path
                if (profileRecruiterFragment.dataBeanProfile.isUserPicChange())
                    imageViewPhoto.setImageBitmap(BitmapFactory.decodeFile(profileRecruiterFragment.dataBeanProfile.getUser_pic()));
                else {
                    showProgress(true);
                    ImageLoader.loadImageRoundCallBack(getActivity(), profileRecruiterFragment.dataBeanProfile.getUser_pic(), imageViewPhoto, new ImageLoader.CallBack() {
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
                }
            } else {
                imageViewPhoto.setImageBitmap(BitmapFactory.decodeFile(imgDecodeStringPhoto));
            }

        } else {
            if (!onActivityEnterprisePhotoCall) {
                tvRemove.setVisibility(View.VISIBLE);
                if (profileRecruiterFragment.dataBeanProfile.isEnterPrisePicChange()) {
                    imageViewPhoto.setImageBitmap(BitmapFactory.decodeFile(profileRecruiterFragment.dataBeanProfile.getEnterprise_pic()));
                } else {
                    showProgress(true);
                    ImageLoader.loadImageRoundCallBack(getActivity(), profileRecruiterFragment.dataBeanProfile.getEnterprise_pic(), imageViewPhoto, new ImageLoader.CallBack() {
                        @Override
                        public void onSuccess() {
                            showProgress(false);
                            new Handler().postDelayed(dialogVideoInfo::show, 300);
                        }

                        @Override
                        public void onError() {
                            showProgress(false);
                        }
                    });
                }
            } else {
                imageViewPhoto.setImageBitmap(BitmapFactory.decodeFile(imgDecodeStringCompanyPhoto));
            }
        }

        imageViewClose.setOnClickListener(v -> dialogVideoInfo.dismiss());
        //remove photo
        tvRemove.setOnClickListener(v -> {
            UtilsMethods.removeConfirmation(getActivity(), () -> {
                dialogVideoInfo.dismiss();
                if (eventFrom.equals(Keys.USER_PIC)) {
                    removePhotoVideo(Keys.USER_PIC);
                } else {
                    removePhotoVideo(Keys.ENTERPRISE_PIC);
                }

            });
        });
        dialogVideoInfo.show();
    }

    /** show progressbar */
    private void showProgress(boolean status) {
        if (status) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
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
                hidePlayPauseController(1, imageViewPlay, imageViewPause);
                progressBar.setVisibility(View.GONE);
                hideShowView(1, rlController);
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
        videoView.setOnTouchListener((v, event) -> {
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
                            Logger.e("Controller hide");
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
                            Logger.e("Controller show");
                            rlController.setVisibility(View.VISIBLE);
                        }
                    });
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
