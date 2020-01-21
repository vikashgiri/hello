package com.infoicon.bonjob.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import com.infoicon.bonjob.R;
import com.infoicon.bonjob.logger.Logger;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import dmax.dialog.SpotsDialog;

/**
 * Created by infoicona on 30/6/17.
 * class for compressing video.
 * create a new file after compressed video.
 */

public class CompressVideo {

    private String TAG=CompressVideo.class.getName();

    private static final String APP_DIR = "VideoCompressor";

    private static final String COMPRESSED_VIDEOS_DIR = "/Compressed Videos/";

    private static final String TEMP_DIR = "/Temp/";

    private Context context;
    private String vidDecodableString;
    private CallbackCompressVid callbackCompressVid;
    private String outPath;

    public CompressVideo(Context context, String vidDecodableString,CallbackCompressVid callbackCompressVid){
        this.context= context;
        this.vidDecodableString=vidDecodableString;
        this.callbackCompressVid=callbackCompressVid;

    }


    /** create directory for compressed file */
    public static void try2CreateCompressDir() {
        File f = new File(Environment.getExternalStorageDirectory(), File.separator + APP_DIR);
        f.mkdirs();
        f = new File(Environment.getExternalStorageDirectory(), File.separator + APP_DIR + COMPRESSED_VIDEOS_DIR);
        f.mkdirs();
        f = new File(Environment.getExternalStorageDirectory(), File.separator + APP_DIR + TEMP_DIR);
        f.mkdirs();
    }

    /** method for creating new compressed file */
    public void compress() {
        try2CreateCompressDir();
        outPath = Environment.getExternalStorageDirectory()
                + File.separator
                + APP_DIR
                + COMPRESSED_VIDEOS_DIR
                + "VIDEO_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date()) + ".mp4";
        this.context=context;
        new VideoCompressor().execute(vidDecodableString, outPath);

    }
    /** method for creating new compressed file */
    public void compress(Context context) {
        try2CreateCompressDir();
        outPath = Environment.getExternalStorageDirectory()
                + File.separator
                + APP_DIR
                + COMPRESSED_VIDEOS_DIR
                + "VIDEO_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date()) + ".mp4";
        this.context=context;
        new VideoCompressor().execute(vidDecodableString, outPath);

    }

    /** class for compressing the video */
    private class VideoCompressor extends AsyncTask<String, Void, Boolean> {
        SpotsDialog spotsDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // progressBar.setVisibility(View.VISIBLE);
            spotsDialog = new SpotsDialog(context, R.style.CustomCompress);
            spotsDialog.setCancelable(false);
            spotsDialog.show();
            Logger.e(TAG + " Start video compression");
        }

        @Override
        protected Boolean doInBackground(String... params) {
           // return MediaController.getInstance().convertVideo(params[0], params[1]);
            return null;
        }

        @Override
        protected void onPostExecute(Boolean compressed) {
            super.onPostExecute(compressed);
            // progressBar.setVisibility(View.GONE);
            spotsDialog.dismiss();
            if (compressed) {
                videoSize(true);
                Logger.e(TAG + " Compression successfully!");
            }else {
                videoSize(false);
                Logger.e(TAG + " Problem in compressing video");
            }
        }
    }

    /** method for get size of the video */
    private void videoSize(boolean haveCompressed) {

        File file2 = new File(vidDecodableString);
        long length2 = file2.length();
        length2 = length2 / 1024;
        Logger.e("File Path : " + file2.getPath() + ", File size Before Compression  : " + length2 + " KB");


        try {
            File file;
            if (haveCompressed){
                file = new File(outPath);
            }else {
                file = new File(vidDecodableString);
            }

            long length = file.length();
            length = length / 1024;

            Logger.e("File Path : " + file.getPath() + ", File size : " + length + " KB");
            if (haveCompressed){
                callbackCompressVid.getSize(length,outPath);
            }else {
                callbackCompressVid.getSize(length,vidDecodableString);
            }


        } catch (Exception e) {
            Logger.e("File not found : " + e.getMessage() + e);
        }
    }

    /** interface for callback the duration of the video, new compressed video path */
    public interface CallbackCompressVid{
        void getSize(long length,String outpath);
    }

}
