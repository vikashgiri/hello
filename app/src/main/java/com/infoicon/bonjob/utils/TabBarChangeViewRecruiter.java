package com.infoicon.bonjob.utils;

import android.app.Activity;
import android.support.v4.content.ContextCompat;

import com.infoicon.bonjob.R;
import com.infoicon.bonjob.main.HomeRecruiterActivity;

/**
 * Created by infoicona on 15/6/17.
 * class for change the bottom view when fragment change or event on bottom view
 */

public class TabBarChangeViewRecruiter {

    public static void changeView(Activity activity,String tab){
        switch (tab) {
            case Keys.LOOK_FOR:
                ((HomeRecruiterActivity)activity).rbLookFor.setTextColor(ContextCompat.getColor(activity, R.color.pink_color));
                ((HomeRecruiterActivity)activity).rbLookFor.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity,R.drawable.footer_search_active),null,null);
                ((HomeRecruiterActivity)activity).rbFolder.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeRecruiterActivity)activity).rbFolder.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity,R.drawable.footer_folder),null,null);
                ((HomeRecruiterActivity)activity).rbChat.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeRecruiterActivity)activity).rbChat.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity,R.drawable.footer_chat_inactive),null,null);
                ((HomeRecruiterActivity)activity).rbActivity.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeRecruiterActivity)activity).rbActivity.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity,R.drawable.plus),null,null);
                ((HomeRecruiterActivity)activity).rbProfile.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeRecruiterActivity)activity).rbProfile.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity,R.drawable.profile_grey),null,null);

                break;
            case Keys.MY_OFFERS:

                ((HomeRecruiterActivity)activity).rbLookFor.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeRecruiterActivity)activity).rbLookFor.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity,R.drawable.footer_search_deactive),null,null);
                ((HomeRecruiterActivity)activity).rbFolder.setTextColor(ContextCompat.getColor(activity, R.color.pink_color));
                ((HomeRecruiterActivity)activity).rbFolder.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity,R.drawable.footer_folder_active),null,null);
                ((HomeRecruiterActivity)activity).rbChat.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeRecruiterActivity)activity).rbChat.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity,R.drawable.footer_chat_inactive),null,null);
                ((HomeRecruiterActivity)activity).rbActivity.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeRecruiterActivity)activity).rbActivity.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity,R.drawable.plus),null,null);
                ((HomeRecruiterActivity)activity).rbProfile.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeRecruiterActivity)activity).rbProfile.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity,R.drawable.profile_grey),null,null);


                break;
            case Keys.CHAT:

                ((HomeRecruiterActivity)activity).rbLookFor.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeRecruiterActivity)activity).rbLookFor.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity,R.drawable.footer_search_deactive),null,null);
                ((HomeRecruiterActivity)activity).rbFolder.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeRecruiterActivity)activity).rbFolder.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity,R.drawable.footer_folder),null,null);
                ((HomeRecruiterActivity)activity).rbChat.setTextColor(ContextCompat.getColor(activity, R.color.pink_color));
                ((HomeRecruiterActivity)activity).rbChat.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity,R.drawable.footer_chat_active),null,null);
                ((HomeRecruiterActivity)activity).rbActivity.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeRecruiterActivity)activity).rbActivity.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity,R.drawable.plus),null,null);
                ((HomeRecruiterActivity)activity).rbProfile.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeRecruiterActivity)activity).rbProfile.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity,R.drawable.profile_grey),null,null);

                break;
            case Keys.POST_JOB:

                ((HomeRecruiterActivity)activity).rbLookFor.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeRecruiterActivity)activity).rbLookFor.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity,R.drawable.footer_search_deactive),null,null);
                ((HomeRecruiterActivity)activity).rbFolder.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeRecruiterActivity)activity).rbFolder.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity,R.drawable.footer_folder),null,null);
                ((HomeRecruiterActivity)activity).rbChat.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeRecruiterActivity)activity).rbChat.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity,R.drawable.footer_chat_inactive),null,null);
                ((HomeRecruiterActivity)activity).rbActivity.setTextColor(ContextCompat.getColor(activity, R.color.pink_color));
                ((HomeRecruiterActivity)activity).rbActivity.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity,R.drawable.plus_pink),null,null);
                ((HomeRecruiterActivity)activity).rbProfile.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeRecruiterActivity)activity).rbProfile.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity,R.drawable.profile_grey),null,null);


                break;
            case Keys.PROFILE:
                ((HomeRecruiterActivity)activity).rbLookFor.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeRecruiterActivity)activity).rbLookFor.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity,R.drawable.footer_search_deactive),null,null);
                ((HomeRecruiterActivity)activity).rbFolder.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeRecruiterActivity)activity).rbFolder.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity,R.drawable.footer_folder),null,null);
                ((HomeRecruiterActivity)activity).rbChat.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeRecruiterActivity)activity).rbChat.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity,R.drawable.footer_chat_inactive),null,null);
                ((HomeRecruiterActivity)activity).rbActivity.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeRecruiterActivity)activity).rbActivity.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity,R.drawable.plus),null,null);
                ((HomeRecruiterActivity)activity).rbProfile.setTextColor(ContextCompat.getColor(activity, R.color.pink_color));
                ((HomeRecruiterActivity)activity).rbProfile.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity,R.drawable.profile_pink),null,null);


                break;
            default:
                break;
        }
    }

}
