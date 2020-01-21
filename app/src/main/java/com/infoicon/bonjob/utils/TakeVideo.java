package com.infoicon.bonjob.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.FileProvider;
import android.view.Window;
import android.widget.LinearLayout;

import com.infoicon.bonjob.BuildConfig;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.permission.MarshmallowPermission;
import com.infoicon.bonjob.permission.PermissionKeys;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by infoicona on 30/6/17.
 */

public class TakeVideo {

    Activity context;
    MarshmallowPermission marshmallowPermission;
    private String mCurrentVideoPath;
    private String vidDecodableString;
    private CallbackTakeVideo callbackTakeVideo;

    public TakeVideo(Activity context, CallbackTakeVideo callbackTakeVideo) {
        this.context = context;
        this.callbackTakeVideo = callbackTakeVideo;
        marshmallowPermission = new MarshmallowPermission(context);
    }

    /**
     * method for open dialog for ask to take a video from camera and from gallery
     */
    public void dialogTakeVideo() {
        final BottomSheetDialog dialog = new BottomSheetDialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_pic_video);
        LinearLayout linearCancel = (LinearLayout) dialog.findViewById(R.id.linearCancel);
        CustomsTextView textViewTakeVideo = (CustomsTextView) dialog.findViewById(R.id.textViewTakeVideo);
        CustomsTextView textViewFromGallery = (CustomsTextView) dialog.findViewById(R.id.textViewFromFallery);

        assert textViewTakeVideo != null;
        textViewTakeVideo.setOnClickListener(v -> {
            dialog.dismiss();
            takeVideoFromCamera();
        });

        assert textViewFromGallery != null;
        textViewFromGallery.setOnClickListener(v -> {
            dialog.dismiss();
            loadVideoFromGallery();
        });

        assert linearCancel != null;
        linearCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }


    /** method for take video from camera */
    private void takeVideoFromCamera() {
        String[] permissions = {PermissionKeys.PERMISSION_CAMERA, PermissionKeys.PERMISSION_WRITE_EXTERNAL_STORAGE};
        if (marshmallowPermission.isPermissionGrantedAll(PermissionKeys.REQUEST_CODE_PERMISSION_ALL, permissions)) {
            try {
                dispatchTakeVideoIntent();
            } catch (IOException e) {
            }
        }
    }


    /** handle intent to take video from camera */
    private void dispatchTakeVideoIntent() throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        // takePictureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createVideoFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                return;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context,
                        BuildConfig.APPLICATION_ID + ".provider",
                        createVideoFile());
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                takePictureIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 60);
                context.startActivityForResult(takePictureIntent, Keys.TAKE_VIDEO_CODE);
                callbackTakeVideo.getPath(vidDecodableString, mCurrentVideoPath);
            }
        }
    }

    /**
     * load video from gallery
     * Create intent to Open video applications like Gallery, Google Photos
     */
    private void loadVideoFromGallery() {
        if (marshmallowPermission.isPermissionGranted(PermissionKeys.PERMISSION_WRITE_EXTERNAL_STORAGE, PermissionKeys.REQUEST_CODE_WRITE_EXTERNAL_STORAGE_2)) {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
            context.startActivityForResult(galleryIntent, Keys.RESULT_LOAD_VID);
        }
    }


    /** create file for video */
    private File createVideoFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "MP4_" + timeStamp + "_";

        File storageDir = new File(Environment.getExternalStorageDirectory().getPath(), "Bonjob/Images");

        //File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".mp4",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentVideoPath = "file:" + image.getAbsolutePath();
        vidDecodableString = image.getAbsolutePath();
        return image;
    }

    public interface CallbackTakeVideo {
        void getPath(String vidDecodableString, String mCurrentVideoPath);
    }

}
