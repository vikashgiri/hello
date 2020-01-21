package com.infoicon.bonjob.utils;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import com.infoicon.bonjob.logger.Logger;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.HashMap;

/**
 * Created by infoicona on 1/7/17.
 * this class for getting thumbnail from url.
 * passing view and url to show the thumbnail.
 */

public class GetThumbNail extends AsyncTask<Void, Void, Bitmap> {
    public static final String TAG = PackageManager.class.getName();
    private CircularImageView circularImageView;
    private String patch_video;


    public GetThumbNail(CircularImageView circularImageView, String patch_video) {
        this.circularImageView = circularImageView;
        this.patch_video = patch_video;
        Logger.e(TAG + " : " + patch_video);
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        Bitmap bitmap = null;
        String videoPath = patch_video;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
          //  if (Build.VERSION.SDK_INT >= 14)
                // no headers included
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<>());
           // else
           //     mediaMetadataRetriever.setDataSource(videoPath);

            bitmap = mediaMetadataRetriever.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (mediaMetadataRetriever != null)
                mediaMetadataRetriever.release();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (bitmap != null) {
            circularImageView.setImageBitmap(bitmap);
        }
    }
}

