package com.infoicon.bonjob.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.infoicon.bonjob.R;
import com.infoicon.bonjob.logger.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;

import dmax.dialog.SpotsDialog;

/**
 * Created by infoicona on 7/7/17.
 */

public class CompressImage extends AsyncTask<String, Void, Boolean> {

    private  final String TAG = this.getClass().getSimpleName();
    SpotsDialog spotsDialog;

    Context context;

    public CompressImage(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // progressBar.setVisibility(View.VISIBLE);
        spotsDialog = new SpotsDialog(context, R.style.CustomCompress);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        Logger.e(TAG + " Start image compression");
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 2;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(params[0]);
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
            inputStream = new FileInputStream(params[0]);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            File dir = new File(params[1]);
            if (!dir.exists())
                dir.mkdir();
            // file = new File("/sdcard/ProjectTemplate/img" + Keys.SDF.format(new Date()) + Keys.count++ + ".jpg");
            File file = new File(Keys.PATH + Keys.SDF.format(new Date()) + Keys.count++ + ".jpg");

            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            boolean compress=selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            return compress;
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    protected void onPostExecute(Boolean compressed) {
        super.onPostExecute(compressed);
        spotsDialog.dismiss();
        if (compressed) {

            File file = new File(Keys.PATH + Keys.SDF.format(new Date()) + Keys.count++ + ".jpg");
            long length = file.length();
            length = length / 1024;
            Logger.e("Image File Path : " + file.getPath() + ", Image File size : " + length + " KB");
            Logger.e(TAG + " Compression successfully!");
        }else {

            Logger.e(TAG + " Problem in compressing video");
        }
    }
}
