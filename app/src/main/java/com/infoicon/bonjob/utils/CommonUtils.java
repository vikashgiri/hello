package com.infoicon.bonjob.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.infoicon.bonjob.R;


/**
 * Created by infoicon on 16/3/16.
 */
public class CommonUtils {

    /** interface for call back to retry */
    public interface RetryInterface{
        public void onClickRetry();
    }

    /** show simple message to bottom bar in app  */
    public static void showSimpleMessageBottom(Activity activity, String message){
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        sbView.setBackgroundColor(ContextCompat.getColor(activity, R.color.pink_color));
        textView.setMaxLines(2);
        snackbar.show();
    }

    /** show simple message to bottom bar in app  */
    public static void showSimpleMessageTop(Activity activity, String message){
        Snackbar snackbar = Snackbar.make(activity.
                findViewById(android.R.id.content),
                message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
   //     sbView.animate().rotationYBy(360);
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        sbView.setBackgroundColor(ContextCompat.getColor(activity, R.color.pink_color));
        textView.setMaxLines(2);

        // Calculate ActionBar height
        int  actionBarHeight=0;
        TypedValue tv = new TypedValue();
        if (activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,activity.getResources().getDisplayMetrics());

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)sbView.getLayoutParams();
        params.gravity = Gravity.TOP;
        params.topMargin=actionBarHeight;
        sbView.setLayoutParams(params);
        snackbar.show();
    }

    /** show message for no internet connect  and call back to retry*/
    public static void showInternetConnectionMessage(Activity activity, final RetryInterface retryInterface){
        Snackbar snackbar = Snackbar
                .make(activity.findViewById(android.R.id.content), activity.getResources().getString(R.string.network_error_message), Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        retryInterface.onClickRetry();
                    }
                });
        // Changing message text color
        snackbar.setActionTextColor(ContextCompat.getColor(activity, R.color.pink_color));
        // Changing action button_red text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        sbView.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        textView.setMaxLines(2);
        snackbar.show();

    }

    /** show error message for length long and call back to retry*/
    public static void showErrorMessage(Activity activity, String message, final RetryInterface retryInterface){
        Snackbar snackbar = Snackbar
                .make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        retryInterface.onClickRetry();
                    }
                });
        // Changing message text color
        snackbar.setActionTextColor(ContextCompat.getColor(activity, R.color.pink_color));

        // Changing action button_red text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        sbView.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        textView.setMaxLines(1);
        snackbar.show();
    }

    /** show error message for length infinite and call back to retry*/
    public static void showErrorMessageIndefinite(Activity activity, String message, final RetryInterface retryInterface){
        Snackbar snackbar = Snackbar
                .make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        retryInterface.onClickRetry();
                    }
                });
        // Changing message text color
        snackbar.setActionTextColor(ContextCompat.getColor(activity, R.color.pink_color));

        // Changing action button_red text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        sbView.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        textView.setMaxLines(2);
        snackbar.show();
    }

    public static void showLoginFirst(Activity activity, final RetryInterface retryInterface){
        Snackbar snackbar = Snackbar
                .make(activity.findViewById(android.R.id.content), "Please Login First!", Snackbar.LENGTH_LONG)
                .setAction("LOGIN_SEEKER", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        retryInterface.onClickRetry();
                    }
                });
        // Changing message text color
        snackbar.setActionTextColor(ContextCompat.getColor(activity, R.color.colorPrimary));

        // Changing action button_red text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        sbView.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        snackbar.show();
    }

    public static Typeface getTypeFaceHalvetical(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "HelveticaNeueLTCom-Lt.ttf");
    }

    public static Typeface getTypeFaceHalveticalBold(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeueLTCom-Md.ttf");
    }

    public static Typeface getTypeFaceLobsterTwo(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "LobsterTwo-Regular.otf");
    }

    /** showing message in toast  */
    public static void showToast(Context context, String message){
        Toast toast= Toast.makeText(context,message, Toast.LENGTH_SHORT);
        toast.show();
    }




}
