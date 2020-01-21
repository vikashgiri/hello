package com.infoicon.bonjob.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.chat.Message;
import com.infoicon.bonjob.customview.CustomsButton;
import com.infoicon.bonjob.customview.CustomsItalicTextView;
import com.infoicon.bonjob.fb_login.FbLoginActivity;
import com.infoicon.bonjob.fcm.MyFcmTokenModel;
import com.infoicon.bonjob.main.HomeRecruiterActivity;
import com.infoicon.bonjob.main.HomeSeekerActivity;
import com.infoicon.bonjob.recruiters.verify_phone.VerifyPhoneNumberActivity;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.response.GetRegistrationRecruiterResponse;
import com.infoicon.bonjob.retrofit.response.GetSocialLoginResponse;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.CommonUtils;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

public class LoginSignupOptionActivity extends AppCompatActivity {

    private String from;
    @BindView(R.id.tvMessage) CustomsItalicTextView tvMessage;
    @BindView(R.id.imageView) ImageView imageView;
    @BindView(R.id.buttonFbLogin) CustomsButton buttonFbLogin;
    @BindView(R.id.buttonSignUp) CustomsButton buttonSignUp;
    @BindView(R.id.buttonLogin) CustomsButton buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_signup_option);
        ButterKnife.bind(this);
        getIntents();

    }

    /** get intents data */
    private void getIntents()
    {
        if (getIntent() != null)
        {
            from = getIntent().getStringExtra(Keys.FROM);
            if (from.equals(Keys.FROM_FIND_JOB))
            {
                tvMessage.setText(getResources().getString(R.string.message_find_job_in_hotel_catering));
            }
            else
                {
                tvMessage.setText(getResources().getString(R.string.publish_your_job_offer));
                imageView.setImageResource(R.drawable.recruiter);
            }
        }
    }

    /** click event for login with facebook */
    @OnClick(R.id.buttonFbLogin)
    void facebookLogin()
    {
        startActivityForResult(new Intent(LoginSignupOptionActivity.this,
                FbLoginActivity.class), Keys.FACEBOOK_CODE);
    }


    /** event for proceed to sign up page */

    @OnClick(R.id.buttonSignUp)
    void signUp()
    {
        if (from.equals(Keys.FROM_FIND_JOB)) {
            Intent intent = new Intent(this, LoginSignUpSeekerActivity.class);
            intent.putExtra(Keys.CLICKED_EVENT, Keys.CLICKED_EVENT_SIGNUP);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            Intent intent = new Intent(this, LoginSignUpRecruiterActivity.class);
            intent.putExtra(Keys.CLICKED_EVENT, Keys.CLICKED_EVENT_SIGNUP);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    /** event for proceed to login page */
    @OnClick(R.id.buttonLogin)
    void login()
    {
        UtilsMethods.hideSoftKeyboard(this);
        if (from.equals(Keys.FROM_FIND_JOB))
        {

           Intent intent = new Intent(this, LoginSignUpSeekerActivity.class);
            intent.putExtra(Keys.CLICKED_EVENT, Keys.CLICKED_EVENT_LOGIN);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
      }
        else
            {
            Intent intent = new Intent(this, LoginSignUpRecruiterActivity.class);
            intent.putExtra(Keys.CLICKED_EVENT, Keys.CLICKED_EVENT_LOGIN);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
     }
    }

    /** event for terms of use */
    @OnClick(R.id.tvTermsOfUse)
    void termsOfUse()
    {
        UtilsMethods.openBrowser(this, Keys.TERM_OF_USE_URL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        //callback for facebook
        if (requestCode == Keys.FACEBOOK_CODE)
        {
            if (resultCode == RESULT_OK)
            {
                String fb_id = data.getStringExtra(Keys.FB_ID);
                String name = data.getStringExtra(Keys.NAME);
                String email = data.getStringExtra(Keys.EMAIL);
               /*fb_id="kjk]ujo";
                name="kjkoooook ooooooo";
                email="evcd@sdoofv.com";*/
                callWebServiceForSocialLogin(fb_id, name,
                        email);
            }
        }
    }

    /** call web service for get login */
    private void callWebServiceForSocialLogin(String fb_id, String name, String email)
    {
        final SpotsDialog spotsDialog = new SpotsDialog(this, R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.FULLNAME, name);
        if (from.equals(Keys.FROM_FIND_JOB))
            jsonObject.addProperty(Keys.USER_TYPE, Keys.SEEKER);
        else
            jsonObject.addProperty(Keys.USER_TYPE, Keys.EMPLOYER);
        jsonObject.addProperty(Keys.EMAIL, email);
        jsonObject.addProperty(Keys.FB_ID, fb_id);
        jsonObject.addProperty(Keys.DEVICE_ID, new MyFcmTokenModel(this).getToken());
        retrofit.Call<GetSocialLoginResponse> call = AppRetrofit.
                getAppRetrofitInstance().getApiServices().
                getSocialLoginResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetSocialLoginResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetSocialLoginResponse> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                GetSocialLoginResponse getSocialLoginResponse = response.body();

                if (getSocialLoginResponse.isSuccess())
                {


                    if(getSocialLoginResponse.getRegister().
                            equalsIgnoreCase("0")||
                            getSocialLoginResponse.getRegister().
                            equalsIgnoreCase("1"))
                    {

                        Intent intents = new Intent(LoginSignupOptionActivity.this,
                                LoginFacebookSignUpSeekerActivity.class);
                        intents.putExtra(Keys.TYPE, getSocialLoginResponse);
                        intents.putExtra(Keys.FROM, from);
                        intents.putExtra(Keys.NAME, name);
                        intents.putExtra(Keys.FB_ID, fb_id);
                        startActivity(intents);
                     return;
                    }
                    if (from.equals(Keys.FROM_FIND_JOB))
                    {
                        addUserOnFireBase(getSocialLoginResponse, "seeker");
                        addUserOnFireBaseOnRegistrations(getSocialLoginResponse,"seeker");
                    }
                    else {
                        addUserOnFireBase(getSocialLoginResponse, "recruiter");
                        addUserOnFireBaseOnRegistrations(getSocialLoginResponse,"recruiter");

                    }
                    CommonUtils.showToast(LoginSignupOptionActivity.this, getSocialLoginResponse.getMsg());
                    Singleton.getUserInfoInstance().setFirst_name(getSocialLoginResponse.getData().get(0).getFull_name());
                   // Singleton.getUserInfoInstance().setLast_name(getSocialLoginResponse.getData().get(0).getLast_name());
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

                    if (from.equals(Keys.FROM_FIND_JOB)) {
                        Singleton.getUserInfoInstance().setLogin(true);
                        Singleton.getUserInfoInstance().setLoginRecriter(false);
                        Intent intent = new Intent(LoginSignupOptionActivity.this, HomeSeekerActivity.class);
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
                            Singleton.getUserInfoInstance().setLogin(true);
                            Intent intent = new Intent(LoginSignupOptionActivity.this,
                                    HomeRecruiterActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra(Keys.FROM, "");
                            startActivity(intent);
                       // }
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }

                } else {
                    CommonUtils.showSimpleMessageBottom(LoginSignupOptionActivity.this, getSocialLoginResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(LoginSignupOptionActivity.this, t.getMessage());
            }
        });
    }


    void addUserOnFireBase(GetSocialLoginResponse  getLoginRecruiterResponse,String type) {
        //register user
        Users newUser = new Users();
        newUser.email = getLoginRecruiterResponse.getData().get(0).getEmail();
        newUser.first_name = getLoginRecruiterResponse.getData().get(0).getFirst_name();
        newUser.fcmToken = new MyFcmTokenModel(this).getToken();
        newUser.last_name =getLoginRecruiterResponse.getData().get(0).getLast_name();
        newUser.online_status = "0";
        newUser.lastOnlineTime = System.currentTimeMillis() / 1000;
        newUser.totalUnreadCount = 0;
        newUser.user_id = getLoginRecruiterResponse.getData().get(0).getUser_id();
        newUser.logOutStatus = 0;
        final DatabaseReference fireDatabaseReference = FirebaseDatabase.getInstance().getReference();
        fireDatabaseReference.
                child(type+"/" + getLoginRecruiterResponse.getData().get(0).getUser_id()).setValue(newUser);

    }



    void addUserOnFireBaseOnRegistrations(GetSocialLoginResponse getRegistrationRecruiterResponse,String type) {
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
                                newMessage.setFcmToken(new MyFcmTokenModel(LoginSignupOptionActivity.this).getToken());
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

}
