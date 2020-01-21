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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.chat.Message;
import com.infoicon.bonjob.customview.CustomCheckBox;
import com.infoicon.bonjob.customview.CustomRadioButton;
import com.infoicon.bonjob.customview.CustomsButton;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.dialogs.DiplomaDialogFragmentForRegister;
import com.infoicon.bonjob.dialogs.EductionLabelResponce;
import com.infoicon.bonjob.fcm.MyFcmTokenModel;
import com.infoicon.bonjob.fcm.RefreshTokenService;
import com.infoicon.bonjob.forgotPassword.ForgetPasswordActivity;
import com.infoicon.bonjob.interfaces.FragmentChanger;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.main.HomeRecruiterActivity;
import com.infoicon.bonjob.main.HomeSeekerActivity;
import com.infoicon.bonjob.multipart.INetworkResponse;
import com.infoicon.bonjob.multipart.MultipartWebServiceCall;
import com.infoicon.bonjob.permission.MarshmallowPermission;
import com.infoicon.bonjob.permission.PermissionKeys;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.ServiceUrls;
import com.infoicon.bonjob.retrofit.response.GetLoginSeekerResponse;
import com.infoicon.bonjob.retrofit.response.GetRegistrationSeekerResponse;
import com.infoicon.bonjob.retrofit.response.GetSeekerProfileResponse;
import com.infoicon.bonjob.retrofit.response.GetSocialLoginResponse;
import com.infoicon.bonjob.seeker.profile.ExperienceFragment;
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

public class LoginFacebookSignUpSeekerActivity extends AppCompatActivity implements FragmentChanger
{

    @BindView(R.id.edEmail) EditText edEmail;
    private MarshmallowPermission marshmallowPermission;
    @BindView(R.id.cbTermsConditionAccept) CustomCheckBox cbTermsConditionAccept;
    @BindView(R.id.buttonSignUp) CustomsButton buttonSignUp;
    @BindView(R.id.edExperiance)    EditText textViewExperience;
    @BindView(R.id.edtextViewTraninng) EditText textViewTraninng;
    @BindView(R.id.edLocation) EditText tvLocation;
    @BindView(R.id.edtextViewPhone) EditText edtextViewPhone;
    @BindView(R.id.edEnterprise) EditText edEnterprise;
    public JSONArray jArry;
    private String mLatitude;
    private String mLongitude;
    private String diplomaId;
    GetSocialLoginResponse getSocialLoginResponse;
    String from;
    List<GetSeekerProfileResponse.DataBean.ExperienceBean> experienceBeanList
            =new ArrayList<>();

    @OnClick(R.id.edLocation)
    void getLocation() {
        Intent intent = new Intent(this, PickLocationActivity.class);
        startActivityForResult(intent, Keys.LOCATION_CODE);
    }

    public void setExperiance()
    {
        textViewExperience.setText(R.string.no_company_added);
        JSONObject jsonobject= null;
        try {
            experienceBeanList.clear();
            for(int i=0;i<jArry.length();i++)
            {
                jsonobject = (JSONObject) jArry.get(i);
                GetSeekerProfileResponse.DataBean.ExperienceBean experienceBean=new
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
                jsonobject.optString("position_held_name")+"-"+
                        jsonobject.optString("company_name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facebook_seeker_signup);
        ButterKnife.bind(this);
        getIntents();
        initialize();
        setTypeFace();
        setEditTextSingleLine();
        jArry = new JSONArray();
    }



    @OnClick(R.id.edExperiance)
    void proceedToUserExperiencePage()
    {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Keys.EXPERIENCE,
                (ArrayList<GetSeekerProfileResponse.DataBean.ExperienceBean>)
                        experienceBeanList);
        bundle.putString(Keys.TYPE,"facebook_register");
        (LoginFacebookSignUpSeekerActivity.this).addFragment(new ExperienceFragment(),
                bundle, Keys.EXPERIENCE,
                false, true);
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
    public void addFragment(Fragment fragment, Bundle bundle, String tag, boolean isRemoveBackStack, boolean isAddToBackStack) {
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
    /** get index of the fragment by tag */
    private int getIndex(String tagname) {
        FragmentManager manager = getSupportFragmentManager();
        for (int i = 0; i < manager.getBackStackEntryCount(); i++) {
            if (manager.getBackStackEntryAt(i).getName().equalsIgnoreCase(tagname)) {
                return i;
            }
        }
        return -1;
    }

    /** events for terms of use */
    @OnClick(R.id.tvTermsOfUse)
    void termsOfUse() {
        UtilsMethods.openBrowser(this, Keys.TERM_OF_USE_URL);
    }


    /** initialize classes and perform listener */
    private void initialize() {

        marshmallowPermission = new MarshmallowPermission(this);
    }


    /** single line for editText */
    private void setEditTextSingleLine() {

        edEmail.setSingleLine(true);
           }

    /** set typeface to editText */
    private void setTypeFace() {
        Typeface font = Typeface.createFromAsset(this.getAssets(), "HelveticaNeueLTCom-LtEx.ttf");

        edEmail.setTypeface(font);

        buttonSignUp.setTextAppearance(this, R.style.boldText);
    }



    /** click event to get register as a new user. */
    @OnClick(R.id.buttonSignUp)
    void signUp() {
        UtilsMethods.hideSoftKeyboard(this);
        if (isRegistrationValidated()) {
                callWebServiceForRegWithoutFile();

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Keys.DIPLOMA_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null)
                {

                    textViewTraninng.setText(data.getStringExtra(Keys.DIPLOMA));
                    diplomaId = data.getStringExtra(Keys.DIPLOMA_ID);
                }
                break;
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
        }
    }


    /** validate registration field */
    private boolean isRegistrationValidated()
    {

        tvLocation.setError(null);
        textViewExperience.setError(null);
        textViewTraninng.setError(null);


        if (UtilsMethods.isEmpty(edEmail) && edEmail.isShown()) {
            showErrorAlert(this,getResources().getString(R.string.email_empty_message));
            requestFocus(edEmail);
            return false;
        }

        if (!UtilsMethods.isValidEmail(edEmail.getText().toString().trim())
                && edEmail.isShown()) {
            showErrorAlert(this,getResources().getString(R.string.email_invalid_message));
            requestFocus(edEmail);
            return false;
        }



        if (UtilsMethods.isEmpty(tvLocation) && tvLocation.isShown()) {

            showErrorAlert(this,getResources().getString(R.string.location_empty_message));
            requestFocus(tvLocation);
            return false;
        }

        if (UtilsMethods.isEmpty(textViewExperience) && textViewExperience.isShown()) {
            showErrorAlert(this,getResources().getString(R.string.select_experience));
            requestFocus(textViewExperience);
            return false;
        }
        if (UtilsMethods.isEmpty(textViewTraninng)&& textViewTraninng.isShown())
        {
            showErrorAlert(this,getResources().getString(R.string.tranning_empty_message));
            requestFocus(textViewTraninng);
            return false;
        }if (UtilsMethods.isEmpty(edEnterprise)&& edEnterprise.isShown())
    {
        showErrorAlert(this,getResources().getString(R.string.enterprise_empty_message));
        requestFocus(edEnterprise);
        return false;
    }

        if (UtilsMethods.isEmpty(edtextViewPhone)&& edtextViewPhone.isShown())
        {
            showErrorAlert(this,getResources().
                    getString(R.string.mobile_emp_msg));
            requestFocus(edtextViewPhone);
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


    private void getIntents()
    {
        if (getIntent() != null)
        {
            getSocialLoginResponse = getIntent().getParcelableExtra(Keys.TYPE);
            from = getIntent().getStringExtra(Keys.FROM);

           if(getSocialLoginResponse.getData().get(0).getEmail()!=null &&
                   !getSocialLoginResponse.getData().get(0).getEmail().isEmpty())
           {
               edEmail.setVisibility(View.GONE);
           }
           if(getSocialLoginResponse.getData().get(0).getCity()!=null &&
                   !getSocialLoginResponse.getData().get(0).getEmail().isEmpty())
           {
               tvLocation.setVisibility(View.GONE);

           }
           if(getSocialLoginResponse.getData().get(0).getSeeker_exp_count()
                   !=null &&
                   !getSocialLoginResponse.getData().get(0).getEmail().isEmpty())
           {
               textViewExperience.setVisibility(View.GONE);

           }
           if(getSocialLoginResponse.getData().get(0).getTraining()!=null &&
                   !getSocialLoginResponse.getData().get(0).getEmail().isEmpty())
           {
               textViewTraninng.setVisibility(View.GONE);
           }
           if(getSocialLoginResponse.getData().get(0).getMobile_number()!=null &&
                   !getSocialLoginResponse.getData().get(0).getMobile_number().isEmpty())
           {
               textViewTraninng.setVisibility(View.GONE);
           }if(getSocialLoginResponse.getData().get(0).getEnterprise_name()!=null &&
                   !getSocialLoginResponse.getData().get(0).getEnterprise_name().isEmpty())
           {
               textViewTraninng.setVisibility(View.GONE);
           }
            if (from.equals(Keys.FROM_FIND_JOB))
            {
                edEnterprise.setVisibility(View.GONE);
                edtextViewPhone.setVisibility(View.GONE);
            }
            else {
                textViewExperience.setVisibility(View.GONE);
                textViewTraninng.setVisibility(View.GONE);

            }

        }
    }
    /** call web service for registration without file upload */
    private void callWebServiceForRegWithoutFile()
    {
        final SpotsDialog spotsDialog = new SpotsDialog(this, R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        if(edEmail.isShown())
        {
           getSocialLoginResponse.getData().get(0)
                            .setEmail(edEmail.getText().toString());
        }

        jsonObject.addProperty(Keys.EMAIL,getSocialLoginResponse.getData().get(0)
                .getEmail());
        jsonObject.addProperty(Keys.FB_ID, getIntent().getStringExtra(Keys.FB_ID));

        if(tvLocation.isShown())
        {
            jsonObject.addProperty(Keys.LATTITUDE, mLatitude);
            jsonObject.addProperty(Keys.LONGITUDE, mLongitude);
            jsonObject.addProperty(Keys.CITY, tvLocation.getText().toString());
        }

        if(textViewExperience.isShown())
        {
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
               }

        if(textViewTraninng.isShown())
        {
            jsonObject.addProperty(Keys.EDUCATION_LEVEL, diplomaId);
        }
        if(edEnterprise.isShown())
        {
            jsonObject.addProperty(Keys.ENTERPRISE_NAME, edEnterprise.getText().toString().trim());
        }
 if(edtextViewPhone.isShown())
        {
            jsonObject.addProperty(Keys.MOBILE_NUMBER, edtextViewPhone.getText().toString().trim());
        }


        if(getSocialLoginResponse.getData().get(0).getUser_id()!=null) {
            jsonObject.addProperty(Keys.USER_ID, getSocialLoginResponse.getData().get(0)
                    .getUser_id());
        }
        if(getSocialLoginResponse.getData().get(0).getFull_name()!=null) {
            jsonObject.addProperty(Keys.FULLNAME, getSocialLoginResponse.getData().get(0)
                    .getFull_name());
        }

        if (from.equals(Keys.FROM_FIND_JOB))
            jsonObject.addProperty(Keys.USER_TYPE, Keys.SEEKER);
        else
            jsonObject.addProperty(Keys.USER_TYPE, Keys.EMPLOYER);


        retrofit.Call<GetSocialLoginResponse> call = AppRetrofit.
                getAppRetrofitInstance().getApiServices().
                getAddSocialLoginResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetSocialLoginResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetSocialLoginResponse> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                GetSocialLoginResponse getSocialLoginResponse = response.body();

                if (getSocialLoginResponse.isSuccess())
                {


                    if (from.equals(Keys.FROM_FIND_JOB)) {
                        addUserOnFireBaseOnRegistrations(getSocialLoginResponse, "seeker") ;
                        addUserOnFireBase(getSocialLoginResponse, "seeker") ;
                    }
                    else {
                        addUserOnFireBaseOnRegistrations(getSocialLoginResponse, "recruiter") ;
                        addUserOnFireBase(getSocialLoginResponse, "recruiter") ;
                    }

                    CommonUtils.showToast(LoginFacebookSignUpSeekerActivity.this, getSocialLoginResponse.getMsg());
                    Singleton.getUserInfoInstance().setFirst_name(getSocialLoginResponse.getData().get(0).getFull_name());
                    //Singleton.getUserInfoInstance().setLast_name(getSocialLoginResponse.getData().get(0).getLast_name());
                    //Singleton.getUserInfoInstance().setUsername(getSocialLoginResponse.getData().get(0).getUsername());
                    Singleton.getUserInfoInstance().setEmail(getSocialLoginResponse.getData().get(0).getEmail());
                    Singleton.getUserInfoInstance().setUser_id(getSocialLoginResponse.getData().get(0).getUser_id());
                    Singleton.getUserInfoInstance().setAuthKey(getSocialLoginResponse.getData().get(0).getAuthKey());
                    Singleton.getUserInfoInstance().setEmail_notification(getSocialLoginResponse.getData().get(0).getEmail_notification());
                    Singleton.getUserInfoInstance().setMobile_notification(getSocialLoginResponse.getData().get(0).getMobile_notification());
                    Singleton.getUserInfoInstance().setPassword(getSocialLoginResponse.getData().get(0).getChat_password());
                    Singleton.getUserInfoInstance().setMobile_number(getSocialLoginResponse.getData().get(0).getMobile_number());
                    Singleton.getUserInfoInstance().setEnterprise_name(getSocialLoginResponse.getData().get(0).getEnterprise_name());
                    Singleton.getUserInfoInstance().setChatId(Keys.BONJOB_ + getSocialLoginResponse.getData().get(0).getUser_id());
                    if(getSocialLoginResponse.getPrevLogined().equalsIgnoreCase("1"))
                    {
                        if (from.equals(Keys.FROM_FIND_JOB))
                            Singleton.getUserInfoInstance().setFirstTimeHomePopUpsSeeker(false);
                        else
                            Singleton.getUserInfoInstance().setFirstTimeHomePopUpRecruiter(false);
                    }else {
                        if (from.equals(Keys.FROM_FIND_JOB))
                            Singleton.getUserInfoInstance().setFirstTimeHomePopUpsSeeker(true);

                        else
                            Singleton.getUserInfoInstance().setFirstTimeHomePopUpRecruiter(true);
                    }
                    Singleton.getUserInfoInstance().setuser_pic(getSocialLoginResponse.getData().get(0).getUser_pic());

                    if (from.equals(Keys.FROM_FIND_JOB)) {
                        Singleton.getUserInfoInstance().setLogin(true);
                        Singleton.getUserInfoInstance().setLoginRecriter(false);
                        Intent intent = new Intent(LoginFacebookSignUpSeekerActivity
                                .this, HomeSeekerActivity.class);
                        intent.putExtra(Keys.FROM, "");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    } else {
                        Singleton.getUserInfoInstance().setLoginRecriter(true);
                        /*if (getSocialLoginResponse.getData().get(0).getMobile_number().equals("")) {
                            Singleton.getUserInfoInstance().setLogin(false);
                           *//* Intent intent = new Intent(LoginSignupOptionActivity.this, VerifyPhoneNumberActivity.class);
                            startActivity(intent);*//*
                        } else {*/
                        if(edEnterprise.isShown())
                        {
                            Singleton.getUserInfoInstance().setEnterprise_name(edEnterprise.getText().toString());
                        }

                        Singleton.getUserInfoInstance().setLogin(true);
                        Intent intent = new Intent(LoginFacebookSignUpSeekerActivity
                                .this,
                                HomeRecruiterActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra(Keys.FROM, "");
                        startActivity(intent);
                        // }
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }

                } else {
                    CommonUtils.showSimpleMessageBottom(
                            LoginFacebookSignUpSeekerActivity.this,
                            getSocialLoginResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(LoginFacebookSignUpSeekerActivity.this, t.getMessage());
            }
        });
    }


    /**
     * event for education level dialog
     */
    @OnClick(R.id.edtextViewTraninng)
    void educationLevel() {
        serviceForGetData();

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
                    Intent intent = new Intent(LoginFacebookSignUpSeekerActivity.this,
                            DiplomaDialogFragmentForRegister.class);
                    //Bundle bundle = new Bundle();
                    intent.putExtra("CompanyDetails",
                            getCompanyDetailsResponse);
                    startActivityForResult(intent, Keys.DIPLOMA_REQUEST_CODE);

                } else {
                    UtilsMethods.unAuthorizeAlert(LoginFacebookSignUpSeekerActivity.this,
                            getCompanyDetailsResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();

            }
        });
    }



    ;

    void addUserOnFireBaseOnRegistrations(GetSocialLoginResponse getRegistrationRecruiterResponse
            ,String type) {
        //send msg to user

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
        newMessage.setFcmToken(new MyFcmTokenModel(LoginFacebookSignUpSeekerActivity.this).getToken());
        newMessage.setTimeStamp(System.currentTimeMillis() / 1000);

        newMessage.setReadStatus("0");               //send msg
        Message.setUserType from = new Message.setUserType();
        from.setName("Assistant BonJob");
        from.setType("admin");
         from.setProfilePic((String) mapUserInfo.get("profilePic"));
        Message.setUserType to = new Message.setUserType();
        to.setName(getRegistrationRecruiterResponse.getData().get(0).getFull_name());
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


    void addUserOnFireBase(GetSocialLoginResponse  getLoginRecruiterResponse,String type)
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
        final DatabaseReference fireDatabaseReference = FirebaseDatabase.getInstance().getReference();
        fireDatabaseReference.
                child(type+"/" + getLoginRecruiterResponse.getData().get(0).getUser_id()).setValue(newUser);
    }
}
