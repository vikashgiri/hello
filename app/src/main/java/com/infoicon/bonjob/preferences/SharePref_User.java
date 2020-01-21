package com.infoicon.bonjob.preferences;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.splash.GetAllDropDownResponce;
import com.infoicon.bonjob.utils.AddShortcutBadger;
import com.infoicon.bonjob.utils.Keys;

/**
 * Created by infoicona on 1/6/17.
 */

public class SharePref_User {

    private SharedPreferences sharedPreferences;
    private SharedPreferences sfPitchVideo;
    private SharedPreferences sfManageLoginPopUp;


    public SharePref_User(Context context) {
        sharedPreferences = context.getSharedPreferences(Keys.MY_PREF, Context.MODE_PRIVATE);
        sfPitchVideo = context.getSharedPreferences(Keys.PITCH_VIDEO_PREF, Context.MODE_PRIVATE);
        sfManageLoginPopUp = context.getSharedPreferences(Keys.PREF_MANAGE_LOGIN_POPUP, Context.MODE_PRIVATE);
    }


    public String getCompanyAddress() {
        return sharedPreferences.getString(Keys.COMPANY_ADDRESS, "");
    }

    public void setCompanyAddress(String companyAddress) {
        sharedPreferences.edit().putString(Keys.COMPANY_ADDRESS, companyAddress).apply();
    }

    public boolean isPitchInfoView() {
        return sfPitchVideo.getBoolean(Keys.IS_PITCH_VIDEO_VIEW, false);
    }

    public void setPitchInfoView(boolean isPitchInfoView) {
        sfPitchVideo.edit().putBoolean(Keys.IS_PITCH_VIDEO_VIEW, isPitchInfoView).apply();
    }

    public int getSeekerActivityCount() {
        return sharedPreferences.getInt(Keys.ACTIVITY_COUNT, 0);
    }

    public void setSeekerActivityCount(int seekerActivityCount) {
        sharedPreferences.edit().putInt(Keys.ACTIVITY_COUNT, seekerActivityCount).apply();

        //add badges
        /*int totalCount = seekerActivityCount + getChatCount() + getRecruiterOfferCount();
        //add badges
        if (totalCount > 0)
            AddShortcutBadger.addBadger(totalCount);
        else AddShortcutBadger.removeAllBadger();*/

    }

    public int getChatCount() {
        return sharedPreferences.getInt(Keys.CHAT_COUNT, 0);
    }

    public void setChatCount(int chatCount) {
        sharedPreferences.edit().putInt(Keys.CHAT_COUNT, chatCount).apply();

        /*int totalCount = chatCount + getSeekerActivityCount() + getRecruiterOfferCount();
        //add badges
        if (totalCount > 0)
            AddShortcutBadger.addBadger(totalCount);
        else AddShortcutBadger.removeAllBadger();*/

    }


    public int getRecruiterOfferCount() {
        return sharedPreferences.getInt(Keys.MY_OFFER_COUNT, 0);
    }

    public void setRecruiterOfferCount(int recruiterOfferCount) {
        sharedPreferences.edit().putInt(Keys.MY_OFFER_COUNT, recruiterOfferCount).apply();

       /* //add badges
        int totalCount = recruiterOfferCount + getChatCount() + getSeekerActivityCount();
        //add badges
        if (totalCount > 0)
            AddShortcutBadger.addBadger(totalCount);
        else AddShortcutBadger.removeAllBadger();*/
    }


    public String getMobile_number() {
        return sharedPreferences.getString(Keys.MOBILE_NUMBER, "");
    }

    public void setMobile_number(String mobile_number) {
        sharedPreferences.edit().putString(Keys.MOBILE_NUMBER, mobile_number).apply();
    }

    public String getEmail_notification() {
        return sharedPreferences.getString(Keys.EMAIL_NOTIFICATION, "");
    }

    public void setEmail_notification(String email_notification) {
        sharedPreferences.edit().putString(Keys.EMAIL_NOTIFICATION, email_notification).apply();
    }

    public String getMobile_notification() {
        return sharedPreferences.getString(Keys.MOBILE_NOTIFICATION, "");
    }

    public void setMobile_notification(String mobile_notification) {
        sharedPreferences.edit().putString(Keys.MOBILE_NOTIFICATION, mobile_notification).apply();
    }


    public String getChatId() {
        return sharedPreferences.getString(Keys.CHAT_ID, "");
    }

    public void setChatId(String chatId) {
        sharedPreferences.edit().putString(Keys.CHAT_ID, chatId).apply();
    }


    public String getFirst_name() {
        return sharedPreferences.getString(Keys.FIRST_NAME, "");
    }

    public void setFirst_name(String first_name) {
        sharedPreferences.edit().putString(Keys.FIRST_NAME, first_name).apply();
    }

    public String getLast_name() {
        return sharedPreferences.getString(Keys.LAST_NAME, "");
    }

    public void setLast_name(String last_name) {
        sharedPreferences.edit().putString(Keys.LAST_NAME, last_name).apply();
    }

    public String getUsername() {
        return sharedPreferences.getString(Keys.USERNAME, "");
    }

    public void setUsername(String username) {
        sharedPreferences.edit().putString(Keys.USERNAME, username).apply();
    }

    public String getEmail() {
        return sharedPreferences.getString(Keys.EMAIL, "");
    }

    public void setEmail(String email) {
        sharedPreferences.edit().putString(Keys.EMAIL, email).apply();
    }

    public String getUser_id() {
        return sharedPreferences.getString(Keys.USER_ID, "");
    }

    public void setUser_id(String user_id) {
        sharedPreferences.edit().putString(Keys.USER_ID, user_id).apply();
        //  sharedPreferences.edit().putString(Keys.USER_ID, "kollyde1231").apply();
    }

    public String getAuthKey() {
        return sharedPreferences.getString(Keys.AUTH_KEY, "");
    }

    public void setAuthKey(String authKey) {
        sharedPreferences.edit().putString(Keys.AUTH_KEY, authKey).apply();
    }

    public boolean isLogin() {
        return sharedPreferences.getBoolean(Keys.IS_LOGIN, false);
    }

    public void setLogin(boolean status) {
        sharedPreferences.edit().putBoolean(Keys.IS_LOGIN, status).apply();
    }

    public boolean isLoginRecriter() {
        return sharedPreferences.getBoolean(Keys.IS_LOGIN_RECRUITER, false);
    }

    public void setLoginRecriter(boolean status) {
        sharedPreferences.edit().putBoolean(Keys.IS_LOGIN_RECRUITER, status).apply();
    }


    public void setLoginAdmin(boolean status) {
        sharedPreferences.edit().putBoolean(Keys.IS_LOGIN_ADMIN, status).apply();
    }
    public boolean getLoginAdmin() {
       return sharedPreferences.getBoolean(Keys.IS_LOGIN_ADMIN, false);
    }


    public boolean isFirstTimeHomePopUpSeeker() {
        return sfManageLoginPopUp.getBoolean(Keys.FIRST_TIME_SEEKER, false);
    }

    public void setFirstTimeHomePopUpsSeeker(boolean firstTimeHomePopUp) {
        sfManageLoginPopUp.edit().putBoolean(Keys.FIRST_TIME_SEEKER, firstTimeHomePopUp).apply();
    }

    public boolean isFirstTimeHomePopUpRecruiter() {
        return sfManageLoginPopUp.getBoolean(Keys.FIRST_TIME_RECRUITER, false);
    }

    public void setFirstTimeHomePopUpRecruiter(boolean firstTimeHomePopUp) {
        sfManageLoginPopUp.edit().putBoolean(Keys.FIRST_TIME_RECRUITER, firstTimeHomePopUp).apply();
    }


    public String getPassword() {
        return sharedPreferences.getString(Keys.PASSWORD, "");
    }

    public void setPassword(String password) {
        sharedPreferences.edit().putString(Keys.PASSWORD, password).apply();
    }

    public String getuser_pic() {
        return sharedPreferences.getString(Keys.user_pic, "");
    }

    public void setuser_pic(String user_pic) {
        sharedPreferences.edit().putString(Keys.user_pic, user_pic).apply();
    }

    public String getEnterprise_name() {
        return sharedPreferences.getString(Keys.ENTERPRISE_NAME, "");
    }

    public void setEnterprise_name(String enterprise_name) {
        sharedPreferences.edit().putString(Keys.ENTERPRISE_NAME, enterprise_name).apply();
    }

    public void clear() {
        sharedPreferences.edit().clear().apply();
    }


    public GetAllDropDownResponce getAllDropdowns() {
        Gson gson = new Gson();
        GetAllDropDownResponce obj = gson.fromJson(sharedPreferences.getString(Keys.DROP_DOWN, "")
                , GetAllDropDownResponce.class);
        return obj;
    }

    public void setAllDropdowns(String enterprise_name) {
        sharedPreferences.edit().putString(Keys.DROP_DOWN, enterprise_name).apply();
    }

}
