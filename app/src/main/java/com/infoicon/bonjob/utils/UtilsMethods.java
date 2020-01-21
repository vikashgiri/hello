package com.infoicon.bonjob.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.Network;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.chat.StaticConfig;
import com.infoicon.bonjob.customview.CustomsButton;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.dialogs.SubscribePlanDialogFragment;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.login.MainActivity;
import com.infoicon.bonjob.main.HomeRecruiterActivity;
import com.infoicon.bonjob.main.HomeSeekerActivity;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.response.GetLogoutResponse;
import com.infoicon.bonjob.retrofit.response.GetMyOfferSeekerResponse;
import com.infoicon.bonjob.retrofit.response.GetSubscriptionListResponse;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.splash.GetAllDropDownResponce;
import com.infoicon.bonjob.splash.SplashActivity;
import com.tapadoo.alerter.Alerter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import dmax.dialog.SpotsDialog;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Sumit Singh on 2/8/16.
 */
public class UtilsMethods {
    public static String postalCode = "";
    public static String country_name = "";
    public static String City_name = "";

    /**
     * check editText is empty on not
     *
     * @param editText for checking validation with editText
     */
    public static boolean isEmpty(EditText editText) {
        if (editText.getText().toString().trim().isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * check editText is empty on not
     *
     * @param editText for checking validation with editText
     */
    public static boolean isEmpty(CustomsTextView editText) {
        if (editText.getText().toString().trim().isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * get Random String
     */
    public static String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 5) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    /**
     * get Image path from url
     */
    public static String getImageFromUri(Context context, Uri selectedImage) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        // Get the cursor
        Cursor cursor = context.getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        // Move to first row
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String imagePath = cursor.getString(columnIndex);
            cursor.close();
            return imagePath;
        }
        return "";
    }

    /**
     * make text camel case
     */
    public static String capitalize(String capString) {
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()) {
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }

    /**
     * get Image path from url
     */
    public static String getVideoFromUri(Context context, Uri selectedVideo) {
        String[] filePathColumn = {MediaStore.Video.Media.DATA};
        // Get the cursor
        Cursor cursor = context.getContentResolver().query(selectedVideo, filePathColumn, null, null, null);
        // Move to first row
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String vidDecodeString = cursor.getString(columnIndex);
            cursor.close();
            return vidDecodeString;
        }
        return "";
    }

    /**
     * convert to date time
     */
    public static String convertDateToYYMMDD(String dates) {
        // SimpleDateFormat spf=new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aaa");
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);
        Date newDate = null;
        String date = "";
        try {
            newDate = spf.parse(dates);
            spf = new SimpleDateFormat("dd-MM-yyyy", Locale.FRENCH);
            date = spf.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * convert to date time
     */
    public static String convertDateTimeFormat(String dates) {
        // SimpleDateFormat spf=new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aaa");
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRENCH);
        Date newDate = null;
        String date = "";
        try {
            newDate = spf.parse(dates);
            spf = new SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.FRENCH);
            date = spf.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * convert to date
     */
    public static String convertDateFormat(String dates) {
        // SimpleDateFormat spf=new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aaa");
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRENCH);
        Date newDate = null;
        String date = "";
        try {
            newDate = spf.parse(dates);
            spf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
            date = spf.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String convertDateFormat2(String dates) {
        // SimpleDateFormat spf=new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aaa");
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);
        Date newDate = null;
        String date = "";
        try {
            newDate = spf.parse(dates);
            spf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
            date = spf.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * disable ssl certificate
     */
    public static void disableSSLCertificateChecking() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                // Not implemented
            }

            @Override
            public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                // Not implemented
            }
        }};

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * convert to time format
     *
     * @param diffInSeconds seconds to convert in time
     */
    public static String convertTimeFormat(int diffInSeconds) {
        return DateUtils.formatElapsedTime((long) diffInSeconds);
    }

    /**
     * get the time left in seconds
     *
     * @param paramDate date
     */
    public static int getSecondLeft(String paramDate, String currDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRENCH);
        try {
            Date currentDate = df.parse(currDate);
            Date createdOnDate = df.parse(paramDate);
            // formattedDate have current date/time
            Logger.e(" Current Date Format  => " + currDate);
            Logger.e(" Created on Date Format  => " + paramDate);

            long duration = currentDate.getTime() - createdOnDate.getTime();
            long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
            diffInSeconds = 172800 - diffInSeconds;// 24 hours=86400 seconds
            Logger.e(" Time difference : " + diffInSeconds);
            return (int) diffInSeconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * get the time left in minutes
     *
     * @param paramDate date
     */
    public static int getMinutesLeft(String paramDate, String currDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRENCH);
        try {
            Date currentDate = df.parse(currDate);
            Date createdOnDate = df.parse(paramDate);
            // formattedDate have current date/time
            Logger.e(" Current Date Format  => " + currDate);
            Logger.e(" Created on Date Format  => " + paramDate);

            long duration = currentDate.getTime() - createdOnDate.getTime();
            long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
            diffInMinutes = 1440 - diffInMinutes;// 24 hours=1440 minutes
            Logger.e(" Time difference : " + diffInMinutes);
            return (int) diffInMinutes;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * get the time defference in seconds
     *
     * @param paramDate date
     */
    public static int getSecondDiff(String paramDate, String currDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRENCH);
        try {
            Date currentDate = df.parse(currDate);
            Date createdOnDate = df.parse(paramDate);
            // formattedDate have current date/time
            Logger.e(" Current Date Format  => " + currDate);
            Logger.e(" Created on Date Format  => " + paramDate);

            long duration = currentDate.getTime() - createdOnDate.getTime();
            long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
            Logger.e(" Time difference : " + diffInSeconds);
            return (int) diffInSeconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * get the time defference in seconds
     *
     * @param paramDate date
     */
    public static int getMinuteDiff(String paramDate, String currDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRENCH);
        try {
            Date currentDate = df.parse(currDate);
            Date createdOnDate = df.parse(paramDate);
            // formattedDate have current date/time
            Logger.e(" Current Date Format  => " + currDate);
            Logger.e(" Created on Date Format  => " + paramDate);

            long duration = currentDate.getTime() - createdOnDate.getTime();
            long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
            Logger.e(" Time difference : " + diffInMinutes);
            return (int) diffInMinutes;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * delete file from local storage
     */
    public static void deleteFile(String path) {
        if (path == null || path.equals("")) {
            return;
        }
        try {
            File file = new File(path);
            boolean deleted = file.delete();
            Logger.e("Deleted pitchVideoThumbNail : " + deleted + ", Path" + path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * delete all files from storage related to bonjob app
     */
    public static void deleteAllBonjobCreatedFile() {
        try {
            String APP_DIR = "VideoCompressor";
            String COMPRESSED_VIDEOS_DIR = "/Compressed Videos/";
            String directory = Environment.getExternalStorageDirectory()
                    + File.separator
                    + APP_DIR
                    + COMPRESSED_VIDEOS_DIR;
            File dir = new File(directory);
            if (dir.isDirectory()) {
                String[] children = dir.list();
                if (children.length > 0) {
                    for (int i = 0; i < children.length; i++) {
                        new File(dir, children[i]).delete();
                    }
                }
            }

            /*File dir2 = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM), "Camera");

            if (dir2.isDirectory()) {
                String[] children = dir2.list();
                if (children.length > 0) {
                    for (int i = 0; i < children.length; i++) {
                        new File(dir2, children[i]).delete();
                    }
                }
            }*/

            File dir3 = new File(Environment.getExternalStorageDirectory().getPath(), "Bonjob/Images");
            if (dir3.isDirectory()) {
                String[] children = dir3.list();
                if (children.length > 0) {
                    for (int i = 0; i < children.length; i++) {
                        new File(dir3, children[i]).delete();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * get full text for description
     */
    public static String getDescription(Context activity, String jobTitle, String num_of_hour) {
        if (num_of_hour.contains(activity.getResources().getString(R.string.week))) {
            String desc = jobTitle + " " +
                    //activity.getResources().getString(R.string.for_partTime) + " " +
                    //  num_of_hour+", "+
                    //  activity.getResources().getString(R.string.experience_in)+" "+
                    // jobTitle+" "+
                    activity.getResources().getString(R.string.appreciated);
            return desc;
        } else {
            String desc = jobTitle + " " +
                    //  activity.getResources().getString(R.string.for_fullTime) + ", " +
                    //  activity.getResources().getString(R.string.experience_in)+" "+
                    //  jobTitle+" "+
                    activity.getResources().getString(R.string.appreciated);
            return desc;
        }
    }


    /**
     * get experience string from number
     */
    public static String getExperience(Context context, String ex) {
        switch (ex) {
            case "0":
                return context.getResources().getString(R.string.exp1);
            case "1":
                return context.getResources().getString(R.string.exp2) + " " +
                        context.getResources().getString(R.string.of_experience);
            case "2":
                return context.getResources().getString(R.string.exp3) + " " +
                        context.getResources().getString(R.string.of_experience);
            case "3":
                return context.getResources().getString(R.string.exp4) + " " +
                        context.getResources().getString(R.string.of_experience);
            case "4":
                return context.getResources().getString(R.string.exp5) + " " +
                        context.getResources().getString(R.string.of_experience);
            default:
                return context.getResources().getString(R.string.exp1);

        }
    }


    /**
     * calculate distance between two latlong
     */
    public static String calculateDistanceInKm(String companyLat, String companyLng, String candidateLat, String candidateLng) {
        Location mylocation = new Location("");
        Location dest_location = new Location("");
        dest_location.setLatitude(Double.parseDouble(candidateLat));
        dest_location.setLongitude(Double.parseDouble(candidateLng));
        mylocation.setLatitude(Double.parseDouble(companyLat));
        mylocation.setLongitude(Double.parseDouble(companyLng));
        float distance = mylocation.distanceTo(dest_location);//in meters
        float km = distance / 1000;
        DecimalFormat df = new DecimalFormat("#0.00");
        String kms = df.format(km);
        if (kms.contains(",")) {
            kms = kms.replace(",", ".");
        }
        return kms;
    }

    /**
     * check internet is connected
     */
    public static boolean isInternetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }

    /**
     * clear notification tray
     */
    public static void clearNotification(Activity activity) {
        NotificationManager notificationManager = (NotificationManager) activity.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    /**
     * get default selected language in your device
     */
    public static String getDefaultLanguage() {
        String language = Locale.getDefault().getLanguage();
        Logger.e("Language : " + language);
        if (language.equals("fr")) {
            return "2";
        } else {
            //return "1";
            return "2";
        }
    }


    /**
     * get thumbnail from local video file
     */
    public static String getThumbnailPathForLocalFile(Activity context, Uri fileUri) {
        String[] thumbColumns = {MediaStore.Video.Thumbnails.DATA};
        long fileId = getFileId(context, fileUri);

        MediaStore.Video.Thumbnails.getThumbnail(context.getContentResolver(),
                fileId, MediaStore.Video.Thumbnails.MICRO_KIND, null);

        Cursor thumbCursor = null;
        try {

            thumbCursor = context.getContentResolver().query(
                    MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
                    thumbColumns, MediaStore.Video.Thumbnails.VIDEO_ID + " = "
                            + fileId, null, null);

            assert thumbCursor != null;
            if (thumbCursor.moveToFirst()) {
                String thumbPath = thumbCursor.getString(thumbCursor.getColumnIndex(MediaStore.Video.Thumbnails.DATA));
                Logger.e("Thumbnail :" + thumbPath);
                return thumbPath;
            }

        } finally {
            if (thumbCursor != null)
                thumbCursor.close();
        }

        return null;
    }

    public static long getFileId(Activity context, Uri fileUri) {
        String[] mediaColumns = {MediaStore.Video.Media._ID};
        Cursor cursor = context.getContentResolver().query(fileUri, mediaColumns, null, null, null);

        if (cursor != null) {

            if (cursor.moveToFirst()) {
                int columnIndex = cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media._ID);
                int id = cursor.getInt(columnIndex);

                return id;
            }
            cursor.close();
        }


        return 0;
    }

    /**
     * save bitmap image to internal storage
     */
    public static String saveImageToInternalStorage(Context context, Bitmap image) {

        try {
            // Use the compress method on the Bitmap object to write image to
            // the OutputStream
            String createPath = createThumbFile();
            //FileOutputStream fos = context.openFileOutput(createPath, Context.MODE_PRIVATE);
            FileOutputStream fos = new FileOutputStream(new File(createPath), true); // true will be same as Context.MODE_APPEND

            // Writing the bitmap to the output stream
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

            return createPath;
        } catch (Exception e) {
            Log.e("saveToInternalStorage()", e.getMessage());
            return "";
        }
    }

    /**
     * create file for video
     */
    private static String createThumbFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.FRENCH).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = new File(Environment.getExternalStorageDirectory().getPath(), "Bonjob/Images");

        //File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        return image.getAbsolutePath();
    }


    public static void showAlertDialog(Context context) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(context.getResources().getString(R.string.message_feature_not_implemented));
        //    builder.setMessage("Yet to implement.");
        builder.setPositiveButton(context.getResources().getString(R.string.message_ok), null);
        builder.setNegativeButton(context.getResources().getString(R.string.cancel), null);
        builder.show();
    }

    /**
     * validate email id
     */
    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * check phone Number is correct or not.
     *
     * @param phone for checking validation of phone
     */
    public static boolean isValidPhoneNumber(String phone) {
        if (android.util.Patterns.PHONE.matcher(phone).matches()) {
            return false;
        }
        return true;
    }

    /**
     * check length of editText
     *
     * @param text for checking validation with editText
     */
    public static boolean isValidLength(String text, int length) {
        if (text.length() >= length) {
            return false;
        }
        return true;
    }

    /**
     * check network connection
     */
    public static boolean isConnectingToInternet(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        } else {
            if (connectivityManager != null) {
                //noinspection deprecation
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            Log.d("Network", "NETWORKNAME: " + anInfo.getTypeName());
                            return true;
                        }
                    }
                }
            }
        }
        //Toast.makeText(mContext, "Your are not connected to internet", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * show short length toast
     */
    public static void showToastS(Context context, String message) {
        if (context != null) {
            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            //toast.setGravity(Gravity.BOTTOM, 0,0);
            toast.show();
        }
    }


    /**
     * show long length toast
     */
    public static void showToastL(Context context, String message) {
        if (context != null) {
            Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            //toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0,0);
            toast.show();
        }
    }

    /**
     * get the size of listview
     */
    public static void getListViewSize(ListView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {

            return;
        }

        int totalHeight = 0;
        for (int size = 0; size < myListAdapter.getCount(); size++) {
            View listItem = myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
        myListView.setLayoutParams(params);

        Log.i("height of listItem:", String.valueOf(totalHeight));
    }

    /**
     * Method  to Open Alert/Dialog
     *
     * @param message is used as a Dialog Message
     */
    public static void openAlert(String message, final Activity activity) {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle(activity.getResources().getString(R.string.app_name));
        alertDialogBuilder.setMessage(message);
        // set positive button: Yes message
        alertDialogBuilder.setPositiveButton(activity.getResources().getString(R.string.message_ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
    }


    /**
     * show message on alert
     */
    public static void openCustumAlert(Activity activity, String message) {
        final Dialog dialog = new Dialog(activity);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_alert_ok);
        dialog.setCancelable(false);
        CustomsTextView textViewTitle = (CustomsTextView) dialog.findViewById(R.id.textViewTitle);
        CustomsTextView textViewMessage = (CustomsTextView) dialog.findViewById(R.id.textViewMessage);
        CustomsButton buttonOk = (CustomsButton) dialog.findViewById(R.id.buttonOk);
        textViewTitle.setText(activity.getResources().getString(R.string.app_name));
        textViewMessage.setText(message);
        buttonOk.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }


    /**
     * method for open dialog to show video info for recording
     */
    public static void dialogPitchVideInfo(Activity mActivity) {
        final Dialog dialogVideoInfo = new Dialog(mActivity);
        dialogVideoInfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialogVideoInfo.getWindow() != null)
            dialogVideoInfo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogVideoInfo.setContentView(R.layout.dialog_video_record_tutorial);
        ImageView imageView = (ImageView) dialogVideoInfo.findViewById(R.id.imageViewClose);
        imageView.setOnClickListener(v -> dialogVideoInfo.dismiss());
        dialogVideoInfo.show();
    }

    /**
     * Method  to open Alert/Dialog if Server Request is failed
     */
    public static void serverRequestError(Context context) {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(context.getResources().getString(R.string.app_name));
        alertDialogBuilder.setMessage(context.getResources().getString(R.string.message_server_alert));
        // set positive button: Yes message
        alertDialogBuilder.setPositiveButton(context.getResources().getString(R.string.message_ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
    }

    /**
     * Method  to open Alert/Dialog when Server Request is TimeOut
     */
    public static void serverRequestTimeout(Context context) {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(context.getResources().getString(R.string.app_name));
        alertDialogBuilder.setMessage(context.getResources().getString(R.string.message_server_alert));
        // set positive button: Yes message
        alertDialogBuilder.setPositiveButton(context.getResources().getString(R.string.message_ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
    }


    /**
     * Method to hide system default UI i.e Status bar, Title bar
     */
    public static void hideSystemUiVisibility(Activity mActivity) {
        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        final View decorView = mActivity.getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                decorView.setSystemUiVisibility(flags);

            }
        });
    }

    /**
     * Method for hiding soft keyboard
     */
    public static void hideSoftKeyboard(Activity mActivity) {
        try {
            InputMethodManager inputManager = (InputMethodManager) mActivity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (mActivity.getCurrentFocus().getWindowToken() != null) {
                inputManager.hideSoftInputFromWindow(mActivity.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * set dynamic height listview
     */
    public static void setDynamicHeight(ListView mListView) {
        ListAdapter mListAdapter = mListView.getAdapter();
        if (mListAdapter == null) {
            // when adapter is null
            return;
        }
        int height = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        for (int i = 0; i < mListAdapter.getCount(); i++) {
            View listItem = mListAdapter.getView(i, null, mListView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            height += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = mListView.getLayoutParams();
        params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
        mListView.setLayoutParams(params);
        mListView.requestLayout();
    }

    public static String compressFile(File file, String originalImageDir) {
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 2;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 90;

            // Find the correct scale value. It should be the power of 2.

            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            File dir = new File(originalImageDir);
            if (!dir.exists())
                dir.mkdir();
            // file = new File("/sdcard/ProjectTemplate/img" + Keys.SDF.format(new Date()) + Keys.count++ + ".jpg");
            String APP_DIR = "VideoCompressor";
            String COMPRESSED_IMAGE_DIR = "/CompressedImages/";
            String outPath = Environment.getExternalStorageDirectory()
                    + File.separator
                    + APP_DIR
                    + COMPRESSED_IMAGE_DIR
                    + "PHOTO_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date()) + ".jpg";
            file = new File(outPath);

            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            return file.getAbsolutePath();
        } catch (Exception e) {
            return null;
        }
    }


    public static String getRealPathFromURI(Uri contentUri) {
        String path = contentUri.getPath(); // "file:///mnt/sdcard/FileName.mp3"
        String cropedPath = "";
        try {
            File file = new File(new URI(path));
            cropedPath = file.getAbsolutePath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return cropedPath;
    }



    /*public static void showCallDialog(final Context context,final String phoneNo) {
        MyDialog.MyDialogcall dialogcall = new MyDialog.MyDialogcall() {
            @Override
            public void ok() {
                makeCallToPhone(context,phoneNo);
            }

            @Override
            public void cancel() {
            }
        };
        String str = phoneNo.replace("-", " ");
        MyDialog myDialog = new MyDialog(context, dialogcall);
        myDialog.setTitle("Call");
        myDialog.setMessage(str);
        myDialog.setIscancel(false);
        myDialog.setTitle_ok_btn("Call");
        myDialog.setTitle_cancel_btn("Cancel");
        myDialog.setCancelButtonVisibility(true);
        myDialog.setOkButtonVisibility(true);

        myDialog.showMyCustomDialog();
    }*/

    /**
     * Method for logout from app.
     */
    public static void logoutAlert(final Activity mActivity) {
        final Dialog dialog = new Dialog(mActivity);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_alert);
        dialog.setCancelable(false);
        CustomsTextView textViewTitle = (CustomsTextView) dialog.findViewById(R.id.textViewTitle);
        CustomsTextView textViewMessage = (CustomsTextView) dialog.findViewById(R.id.textViewMessage);
        CustomsButton buttonYes = (CustomsButton) dialog.findViewById(R.id.buttonYes);
        CustomsButton buttonNo = (CustomsButton) dialog.findViewById(R.id.buttonNo);
        buttonYes.setText(mActivity.getResources().getString(R.string.message_logout));
        buttonNo.setText(mActivity.getResources().getString(R.string.cancel));
        textViewTitle.setText(mActivity.getResources().getString(R.string.app_name));
        textViewMessage.setText(mActivity.getResources().getString(R.string.message_signout));
        buttonYes.setOnClickListener(view -> {
                    dialog.dismiss();
                    if (Singleton.getUserInfoInstance().isLoginRecriter()
                            || Singleton.getUserInfoInstance().getLoginAdmin()) {

                        if (Singleton.getUserInfoInstance().getLoginAdmin()) {

                            FirebaseDatabase.getInstance().getReference()
                                    .child("admin/" + Singleton.getUserInfoInstance().getUser_id())
                                    .child("fcmToken")
                                    .setValue("");
                            FirebaseDatabase.getInstance().getReference()
                                    .child("admin/" + Singleton.getUserInfoInstance().getUser_id())
                                    .child("logOutStatus")
                                    .setValue(1);
                            FirebaseDatabase.getInstance().getReference()
                                    .child("admin/" + Singleton.getUserInfoInstance().getUser_id())
                                    .child("lastOnlineTime")
                                    .setValue(System.currentTimeMillis() / 1000);
                            FirebaseDatabase.getInstance().getReference().
                                    child("admin/"+Singleton.getUserInfoInstance().getUser_id()).child("online_status")
                                    .setValue("0");
                        } else {
                            FirebaseDatabase.getInstance().getReference()
                                    .child("recruiter/" +Singleton.getUserInfoInstance().getUser_id())
                                    .child("fcmToken")
                                    .setValue("");
                            FirebaseDatabase.getInstance().getReference()
                                    .child("recruiter/" + Singleton.getUserInfoInstance().getUser_id())
                                    .child("logOutStatus")
                                    .setValue(1);    FirebaseDatabase.getInstance().getReference()
                                    .child("recruiter/" + Singleton.getUserInfoInstance().getUser_id())
                                    .child("lastOnlineTime")
                                    .setValue(System.currentTimeMillis() / 1000);
                            FirebaseDatabase.getInstance().getReference().
                                    child("recruiter/"+Singleton.getUserInfoInstance().getUser_id()).child("online_status")
                                    .setValue("0");
                        }
                    } else {
                        FirebaseDatabase.getInstance().getReference()
                                .child("seeker/" +Singleton.getUserInfoInstance().getUser_id())
                                .child("fcmToken")
                                .setValue("");
                        FirebaseDatabase.getInstance().getReference()
                                .child("seeker/" +Singleton.getUserInfoInstance().getUser_id())
                                .child("logOutStatus")
                                .setValue(1);

                        FirebaseDatabase.getInstance().getReference()
                                .child("seeker/" +Singleton.getUserInfoInstance().getUser_id())
                                .child("lastOnlineTime")
                                .setValue(System.currentTimeMillis() / 1000);
                        FirebaseDatabase.getInstance().getReference().
                                child("seeker/"+Singleton.getUserInfoInstance().getUser_id()).child("online_status")
                                .setValue("0");
                    }
            signoutService(mActivity);
                });



        buttonNo.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    /**
     * Method for confirmation on remove.
     */
    public static void removeConfirmation(final Activity mActivity, Callback callback) {
        final Dialog dialog = new Dialog(mActivity);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_alert);
        dialog.setCancelable(false);
        CustomsTextView textViewTitle = (CustomsTextView) dialog.findViewById(R.id.textViewTitle);
        CustomsTextView textViewMessage = (CustomsTextView) dialog.findViewById(R.id.textViewMessage);
        CustomsButton buttonYes = (CustomsButton) dialog.findViewById(R.id.buttonYes);
        CustomsButton buttonNo = (CustomsButton) dialog.findViewById(R.id.buttonNo);
        textViewTitle.setText(mActivity.getResources().getString(R.string.app_name));
        textViewMessage.setText(mActivity.getResources().getString(R.string.remove_confirm));
        buttonYes.setOnClickListener(view -> {
            dialog.dismiss();
            callback.onYes();
        });
        buttonNo.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    /**
     * Method for confirmation on remove.
     */
    public static void postCallback(final Activity mActivity, Callback callback) {
        final Dialog dialog = new Dialog(mActivity);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_alert);
        dialog.setCancelable(false);
        CustomsTextView textViewTitle = (CustomsTextView) dialog.findViewById(R.id.textViewTitle);
        CustomsTextView textViewMessage = (CustomsTextView) dialog.findViewById(R.id.textViewMessage);
        CustomsButton buttonYes = (CustomsButton) dialog.findViewById(R.id.buttonYes);
        CustomsButton buttonNo = (CustomsButton) dialog.findViewById(R.id.buttonNo);
        textViewTitle.setText(mActivity.getResources().getString(R.string.app_name));
        textViewMessage.setText(mActivity.getResources().getString(R.string.fill_profile_msg));
        buttonYes.setOnClickListener(view -> {
            dialog.dismiss();
            callback.onYes();
        });
        buttonNo.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    public static interface Callback {
        public void onYes();
    }


    /**
     * Method for logout from app.
     */
    public static void unAuthorizeAlert(final Activity mActivity, String message)
    {
        final Dialog dialog = new Dialog(mActivity);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_alert_ok);
        dialog.setCancelable(false);
        CustomsTextView textViewTitle = (CustomsTextView) dialog.findViewById(R.id.textViewTitle);
        CustomsTextView textViewMessage = (CustomsTextView) dialog.findViewById(R.id.textViewMessage);
        CustomsButton buttonOk = (CustomsButton) dialog.findViewById(R.id.buttonOk);
        textViewTitle.setText(mActivity.getResources().getString(R.string.app_name));
        textViewMessage.setText(message);
        buttonOk.setOnClickListener(view -> {
            dialog.dismiss();
            Singleton.getOnlineList().clear();
            Singleton.getUserInfoInstance().clear();
            mActivity.finish();
            mActivity.startActivity(new Intent(mActivity, SplashActivity.class));
            mActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        dialog.show();
    }

    /**
     * service for logout from app.
     */
    private static void signoutService(Activity mActivity) {
        final SpotsDialog spotsDialog = new SpotsDialog(mActivity, R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.USER_ID, Singleton.getUserInfoInstance().getUser_id());

        retrofit.Call<GetLogoutResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getLogoutResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetLogoutResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetLogoutResponse> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                GetLogoutResponse getApplyJobResponse = response.body();
                if (getApplyJobResponse.isSuccess()) {
                    Singleton.getOnlineList().clear();
                    Singleton.getUserInfoInstance().clear();
                    mActivity.finish();
                    mActivity.startActivity(new Intent(mActivity, SplashActivity.class));
                    mActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {
                    CommonUtils.showSimpleMessageBottom(mActivity, getApplyJobResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(mActivity, t.getMessage());
            }
        });
    }

    /**
     * Method for diverting usert to setting screen of the app
     */
    public static void promptSettings(final Activity mActivity, @Nullable String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(mActivity.getResources().getString(R.string.app_name));
        builder.setMessage(message);
        builder.setPositiveButton(mActivity.getResources().getString(R.string.message_goto_setting), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                goToSettings(mActivity);
            }
        });
        builder.setNegativeButton(mActivity.getResources().getString(R.string.cancel), null);
        builder.show();
    }

    /**
     * go to setting
     */
    public static void goToSettings(Activity mActivity) {
        Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + mActivity.getPackageName()));
        myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
        myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mActivity.startActivity(myAppSettings);
    }


    /**
     * Method for opening browser
     */
    public static void openBrowser(Activity activity, String website) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
        activity.startActivity(browserIntent);
    }


    /**
     * method for check locai
     */
    public static boolean isLocationEnabledNoCallback(Activity mActivity) {
        LocationManager lm = (LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }


        if (!gps_enabled && !network_enabled) {
            return false;
        } else {
            return true;
        }
        //  return false;
    }

    /**
     * check location availability
     */
    public static boolean isLocationEnabled(Activity mActivity) {
        LocationManager lm = (LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }


        if (!gps_enabled && !network_enabled) {
            enableLocationSettingDialog(mActivity, mActivity.getResources().getString(R.string.message_location_not_enable));
        } else {
            return true;
        }
        return false;
    }

    /**
     * method for write content into file
     */
    public static void writeToFile(String fname, String fcontent) {
        try {
            String fpath = "/sdcard/" + fname + ".txt";

            File file = new File(fpath);

            // If file does not exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(fcontent);
            bw.close();

            Log.d("Suceess", "Sucess");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Notifying user to enable location settings in
     * his/her device. So that we can extract location.
     */
    public static void enableLocationSettingDialog(final Activity mActivity, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(mActivity.getResources().getString(R.string.app_name));

        builder.setMessage(message);
        builder.setPositiveButton(mActivity.getResources().getString(R.string.message_change_setting), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mActivity.startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), Keys.LOCATION_ENABLE_CODE);
            }
        });
        builder.setNegativeButton(mActivity.getResources().getString(R.string.cancel), null);
        builder.show();
    }

    /**
     * compress image
     */
    public static String compressImage(Activity activity, String imageUri) {

        String filePath = getRealPathFromURI(activity, imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        /*float maxHeight = 816.0f;
        float maxWidth = 612.0f;*/
        float maxHeight = 600.0f;
        float maxWidth = 900.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    /**
     * get file name from storage
     */
    public static String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "Bonjob/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;
    }

    /**
     * get path from uri
     */
    private static String getRealPathFromURI(Activity activity, String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = activity.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            String abc = cursor.getString(index);
            cursor.close();
            return abc;
        }

    }

    /**
     * calculate size of image
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }


    /**
     * This method is used to get days,hours,minutes,seconds from a date given in yyyy-MM-dd HH:mm:ss format
     */

    public static String[] getCounterTime(String startTime, String endtime) {
        String[] counttimearray = null;
        if (!endtime.equals("")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            try {
                Date startDate = sdf.parse(startTime);
                Date enddate = sdf.parse(endtime);
                counttimearray = printDifference(startDate, enddate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            counttimearray = new String[]{"0", "0", "0", "0"};
        }
        return counttimearray;

    }

    /**
     * This method print the diffrence betwwen given time and betwwen current time
     */
    public static String[] printDifference(Date startDate, Date endDate) {
        String[] counttime = new String[4];

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

      /*  System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays,
                elapsedHours, elapsedMinutes, elapsedSeconds);*/
        counttime[0] = String.valueOf(elapsedDays);
        counttime[1] = String.valueOf(elapsedHours);
        counttime[2] = String.valueOf(elapsedMinutes);
        counttime[3] = String.valueOf(elapsedSeconds);

        return counttime;
    }


    public static String getCropedPath() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = new File(Environment.getExternalStorageDirectory().getPath(), "Bonjob/Images");

        //File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");
        String imgDecodableString = "";
        try {
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
            imgDecodableString = image.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //   mCurrentPhotoPath = "file:" + image.getAbsolutePath();

        return imgDecodableString;
    }


    /**
     * check the search is valid
     */
    public static boolean isValidForSearch(FragmentActivity activity) {
        GetSubscriptionListResponse.CurrentPlanBean currentPlanBean = Singleton.getSubscriptionListAndMyPlan().getCurrentPlan();

        if (currentPlanBean == null) {
            Logger.e("CURRENT PLAN IS NULL");
            if (!UtilsMethods.isInternetConnected(activity)) {
                openCustumAlert(activity, activity.getString(R.string.internet_connection));
            } else {
                callServiceForSubscriptioPlan(activity);
            }
            return false;
        }

        if (currentPlanBean.getExpiredOn().equalsIgnoreCase("")) {
            if (currentPlanBean.getSubscription_id().equalsIgnoreCase("")) {
                //for new recruiter
                if (Integer.parseInt(currentPlanBean.getSearch_candidate_count()) >= 1) {
                    openPaymentPlanDialog(activity);
                    return false;
                } else {
                    return true;
                }
            } else {
                //for already purchased plan, but now plan have expired
                openPaymentPlanDialog(activity);
                return false;
            }
        } else {
            // now plan is running
            return true;
        }
    }


    /**
     * check the select candidate is valid
     */


    public static void showErrorAlert(Activity context, String message) {
        Alerter.create(context)
                .setTitle(context.getResources().getString(R.string.app_name))
                .setText(message)
                .setBackgroundColorRes(R.color.red_color).
                show();
    }

    public static boolean isValidForSlectCandidate(FragmentActivity activity) {
       GetSubscriptionListResponse.CurrentPlanBean currentPlanBean = Singleton.getSubscriptionListAndMyPlan().getCurrentPlan();

        if (currentPlanBean == null) {
            Logger.e("CURRENT PLAN IS NULL");
            if (!UtilsMethods.isInternetConnected(activity))
            {
                openCustumAlert(activity, activity.getString(R.string.internet_connection));
            } else {
                callServiceForSubscriptioPlan(activity);
            }
            return false;
        }

        if (currentPlanBean.getExpiredOn().equalsIgnoreCase("")) {
            if (currentPlanBean.getSubscription_id().equalsIgnoreCase("")) {
                //for new recruiter
                if (Integer.parseInt(currentPlanBean.getSelect_candidate_count())
                        >= 1) {
                    openPaymentPlanDialog(activity);
                    return false;
                } else {
                    return true;
                }
            } else {
                //for already purchased plan, but now plan have expired
                openPaymentPlanDialog(activity);
                return false;
            }
        } else {
            // now plan is running
            return true;
        }
    }




    /** check the current plan is active */
    public static boolean isPlanActive(FragmentActivity activity) {
        GetSubscriptionListResponse.CurrentPlanBean currentPlanBean = Singleton.getSubscriptionListAndMyPlan().getCurrentPlan();
        if (currentPlanBean == null) {
            Logger.e("CURRENT PLAN IS NULL");
            if (!UtilsMethods.isInternetConnected(activity)) {
                openCustumAlert(activity, activity.getString(R.string.internet_connection));
            } else {
                callServiceForSubscriptioPlan(activity);
            }
            return false;
        }

        if (currentPlanBean.getExpiredOn().equalsIgnoreCase("")) {
            if (currentPlanBean.getSubscription_id().equalsIgnoreCase("")) {
                return true;
            } else {
                openPaymentPlanDialog(activity);
                return false;
            }
        } else {
            return true;
        }
    }


    /** check the post is valid */
    public static boolean isValidForPostJob(FragmentActivity activity) {
        GetSubscriptionListResponse.CurrentPlanBean currentPlanBean =
                Singleton.getSubscriptionListAndMyPlan().getCurrentPlan();
        if (currentPlanBean == null) {
            Logger.e("CURRENT PLAN IS NULL");
            if (!UtilsMethods.isInternetConnected(activity)) {
                openCustumAlert(activity, activity.getString(R.string.internet_connection));
            } else {
                callServiceForSubscriptioPlan(activity);
            }
            return false;
        }

        if (currentPlanBean.getExpiredOn().equalsIgnoreCase("")) {
            if (currentPlanBean.getSubscription_id().equalsIgnoreCase("")) {
                //for new recruiter
                if (currentPlanBean.getJob_post_count() >= 1) {
                    openPaymentPlanDialog(activity);
                    return false;
                } else {
                    return true;
                }
            } else {
                //for already purchased plan, but now plan have expired
                openPaymentPlanDialog(activity);
                return false;
            }
        } else {
            // now plan is running
            return true;
        }
    }

    /** open dialog fragment for plan list */
    private static void openPaymentPlanDialog(FragmentActivity activity) {
        FragmentManager fm = activity.getSupportFragmentManager();
        SubscribePlanDialogFragment experienceDialogFragment = new SubscribePlanDialogFragment();
        experienceDialogFragment.show(fm, Keys.TAG_SUBSCRIBE);
    }


    /** get the subscription list and your current plan */
    private static void callServiceForSubscriptioPlan(FragmentActivity fragmentActivity) {
        final SpotsDialog spotsDialog = new SpotsDialog(fragmentActivity, R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        retrofit.Call<GetSubscriptionListResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getSubscriptionListResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetSubscriptionListResponse>()
        {
            @Override
            public void onResponse(retrofit.Response<GetSubscriptionListResponse> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                GetSubscriptionListResponse getSubscriptionListResponse = response.body();
                if (getSubscriptionListResponse.isSuccess()) {
                    Singleton.getSubscriptionListAndMyPlan().setSubscriptionList(getSubscriptionListResponse.getData());
                    Singleton.getSubscriptionListAndMyPlan().setCurrentPlan(getSubscriptionListResponse.getCurrentPlan());
                } else {
                    if (getSubscriptionListResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(fragmentActivity, getSubscriptionListResponse.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(fragmentActivity, t.getMessage());
            }
        });
    }



    /** call service for get my offer */
    public static void serviceGetCandidateProfile(Context context) {

        JsonObject jsonObject = new JsonObject();
        Call<GetAllDropDownResponce> call = AppRetrofit.getAppRetrofitInstance().getApiServices().
                getAllDropdowns(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetAllDropDownResponce>() {
            @Override
            public void onResponse(Response<GetAllDropDownResponce> response, Retrofit retrofit) {

                GetAllDropDownResponce getMyOfferSeekerResponse = response.body();
                if (getMyOfferSeekerResponse.isSuccess()) {
                    Gson gson = new Gson();
                    String json = gson.toJson(getMyOfferSeekerResponse);
                    Singleton.getUserInfoInstance().setAllDropdowns(json);
           /*         GetAllDropDownResponce obj = gson.fromJson
           ( Singleton.getUserInfoInstance().getAllDropdowns()
                            , GetAllDropDownResponce.class);*/
                 //  System.out.println(obj.getCandidateSeeks());

                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
