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
 * Created by infoicona on 1/7/17.
 */

public class TakePhoto {


    Activity context;
    MarshmallowPermission marshmallowPermission;
    private String mCurrentPhotoPath;
    private String imgDecodableString;
    private CallbackTakePhoto callbackTakePhoto;
    public TakePhoto(Activity context,CallbackTakePhoto callbackTakeVideo){
        this.context=context;
        this.callbackTakePhoto=callbackTakeVideo;
        marshmallowPermission=new MarshmallowPermission(context);
    }


    /** method for open dialog for ask to take a photo from camera and from gallery */
    public void dialogTakePic() {
        final BottomSheetDialog dialog = new BottomSheetDialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_pic_photo);
        LinearLayout linearCancel = (LinearLayout) dialog.findViewById(R.id.linearCancel);
        CustomsTextView textViewTakePicture = (CustomsTextView) dialog.findViewById(R.id.textViewTakePicture);
        CustomsTextView textViewFromFallery = (CustomsTextView) dialog.findViewById(R.id.textViewFromFallery);

        assert textViewTakePicture != null;
        textViewTakePicture.setOnClickListener(v -> {
            dialog.dismiss();
            takePhotoFromCamera();
        });

        assert textViewFromFallery != null;
        textViewFromFallery.setOnClickListener(v -> {
            dialog.dismiss();
            loadImageFromGallery();
        });

        assert linearCancel != null;
        linearCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    /** method for take photo from camera */
    private void takePhotoFromCamera() {
        String[] permissions = {PermissionKeys.PERMISSION_CAMERA, PermissionKeys.PERMISSION_WRITE_EXTERNAL_STORAGE};
        if (marshmallowPermission.isPermissionGrantedAll(PermissionKeys.REQUEST_CODE_PERMISSION_ALL, permissions)) {
            try {
                dispatchTakePictureIntent();
            } catch (IOException e) {
            }
        }
    }

    /** handle intent to take picture from camera */
    private void dispatchTakePictureIntent() throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                return;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context,
                        BuildConfig.APPLICATION_ID + ".provider",
                        createImageFile());
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                context.startActivityForResult(takePictureIntent, Keys.TAKE_PHOTO_CODE);
                callbackTakePhoto.getPath(imgDecodableString,mCurrentPhotoPath);
            }
        }
    }


    /** create file for images */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = new File(Environment.getExternalStorageDirectory().getPath(), "Bonjob/Images");

        //File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        imgDecodableString = image.getAbsolutePath();

        return image;
    }


    /**
     * load image from gallery
     * Create intent to Open Image applications like Gallery, Google Photos
     */
    private void loadImageFromGallery() {
        if (marshmallowPermission.isPermissionGranted(PermissionKeys.PERMISSION_WRITE_EXTERNAL_STORAGE, PermissionKeys.REQUEST_CODE_WRITE_EXTERNAL_STORAGE_1)) {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            context.startActivityForResult(galleryIntent, Keys.RESULT_LOAD_IMG);
        }
    }

    public interface CallbackTakePhoto{
        void getPath(String imgDecodableString,String mCurrentPhotoPath);
    }



    /** method for open dialog for ask to take a photo from camera and from gallery */
    public void dialogMultimedia() {
        final BottomSheetDialog dialog = new BottomSheetDialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_pic_photo);
        LinearLayout linearCancel = (LinearLayout) dialog.findViewById(R.id.linearCancel);
        CustomsTextView textViewTakePicture = (CustomsTextView) dialog.findViewById(R.id.textViewTakePicture);
        CustomsTextView textViewFromFallery = (CustomsTextView) dialog.findViewById(R.id.textViewFromFallery);

        assert textViewTakePicture != null;
        textViewTakePicture.setOnClickListener(v -> {
            dialog.dismiss();
            takePhotoFromCamera();
        });

        assert textViewFromFallery != null;
        textViewFromFallery.setOnClickListener(v -> {
            dialog.dismiss();
            loadImageFromGallery();
        });

        assert linearCancel != null;
        linearCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

}
