package com.infoicon.bonjob.utils;

import android.app.Activity;
import android.support.v4.content.ContextCompat;

import com.infoicon.bonjob.R;
import com.infoicon.bonjob.main.HomeSeekerActivity;

/**
 * Created by infoicona on 15/6/17.
 * class for change the bottom view when fragment change or event on bottom view
 */

public class TabBarChangeViewSeeker {




    public static void changeView(Activity activity,String tab) {
        switch (tab) {
            case Keys.LOOK_FOR:
                ((HomeSeekerActivity) activity).rbLookFor.setTextColor(ContextCompat.getColor(activity, R.color.pink_color));
                ((HomeSeekerActivity) activity).rbLookFor.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity, R.drawable.footer_search_active), null, null);
                ((HomeSeekerActivity) activity).rbFolder.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeSeekerActivity) activity).rbFolder.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity, R.drawable.footer_folder), null, null);
                ((HomeSeekerActivity) activity).rbChat.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeSeekerActivity) activity).rbChat.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity, R.drawable.footer_chat_inactive), null, null);
                ((HomeSeekerActivity) activity).rbActivity.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeSeekerActivity) activity).rbActivity.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity, R.drawable.footer_activity_deactive), null, null);
                ((HomeSeekerActivity) activity).rbProfile.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeSeekerActivity) activity).rbProfile.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity, R.drawable.profile_grey), null, null);

                break;
            case Keys.MY_OFFERS:

                ((HomeSeekerActivity) activity).rbLookFor.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeSeekerActivity) activity).rbLookFor.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity, R.drawable.footer_search_deactive), null, null);
                ((HomeSeekerActivity) activity).rbFolder.setTextColor(ContextCompat.getColor(activity, R.color.pink_color));
                ((HomeSeekerActivity) activity).rbFolder.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity, R.drawable.footer_folder_active), null, null);
                ((HomeSeekerActivity) activity).rbChat.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeSeekerActivity) activity).rbChat.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity, R.drawable.footer_chat_inactive), null, null);
                ((HomeSeekerActivity) activity).rbActivity.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeSeekerActivity) activity).rbActivity.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity, R.drawable.footer_activity_deactive), null, null);
                ((HomeSeekerActivity) activity).rbProfile.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeSeekerActivity) activity).rbProfile.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity, R.drawable.profile_grey), null, null);


                break;
            case Keys.CHAT:

                ((HomeSeekerActivity) activity).rbLookFor.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeSeekerActivity) activity).rbLookFor.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity, R.drawable.footer_search_deactive), null, null);
                ((HomeSeekerActivity) activity).rbFolder.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeSeekerActivity) activity).rbFolder.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity, R.drawable.footer_folder), null, null);
                ((HomeSeekerActivity) activity).rbChat.setTextColor(ContextCompat.getColor(activity, R.color.pink_color));
                ((HomeSeekerActivity) activity).rbChat.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity, R.drawable.footer_chat_active), null, null);
                ((HomeSeekerActivity) activity).rbActivity.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeSeekerActivity) activity).rbActivity.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity, R.drawable.footer_activity_deactive), null, null);
                ((HomeSeekerActivity) activity).rbProfile.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeSeekerActivity) activity).rbProfile.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity, R.drawable.profile_grey), null, null);

                break;
            case Keys.ACTIVITY:

                ((HomeSeekerActivity) activity).rbLookFor.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeSeekerActivity) activity).rbLookFor.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity, R.drawable.footer_search_deactive), null, null);
                ((HomeSeekerActivity) activity).rbFolder.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeSeekerActivity) activity).rbFolder.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity, R.drawable.footer_folder), null, null);
                ((HomeSeekerActivity) activity).rbChat.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeSeekerActivity) activity).rbChat.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity, R.drawable.footer_chat_inactive), null, null);
                ((HomeSeekerActivity) activity).rbActivity.setTextColor(ContextCompat.getColor(activity, R.color.pink_color));
                ((HomeSeekerActivity) activity).rbActivity.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity, R.drawable.footer_activity_active), null, null);
                ((HomeSeekerActivity) activity).rbProfile.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeSeekerActivity) activity).rbProfile.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity, R.drawable.profile_grey), null, null);


                break;
            case Keys.PROFILE:
                ((HomeSeekerActivity) activity).rbLookFor.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeSeekerActivity) activity).rbLookFor.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity, R.drawable.footer_search_deactive), null, null);
                ((HomeSeekerActivity) activity).rbFolder.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeSeekerActivity) activity).rbFolder.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity, R.drawable.footer_folder), null, null);
                ((HomeSeekerActivity) activity).rbChat.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeSeekerActivity) activity).rbChat.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity, R.drawable.footer_chat_inactive), null, null);
                ((HomeSeekerActivity) activity).rbActivity.setTextColor(ContextCompat.getColor(activity, R.color.text_color));
                ((HomeSeekerActivity) activity).rbActivity.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity, R.drawable.footer_activity_deactive), null, null);
                ((HomeSeekerActivity) activity).rbProfile.setTextColor(ContextCompat.getColor(activity, R.color.pink_color));
                ((HomeSeekerActivity) activity).rbProfile.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity, R.drawable.profile_pink), null, null);


                break;
            default:
                break;
        }
    }

}
