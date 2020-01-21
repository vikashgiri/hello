package com.infoicon.bonjob.picasso;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.Rounded;
import com.infoicon.bonjob.logger.Logger;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Callback;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.Executors;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


/**
 * Created by android1 on 8/12/16.
 */

public class ImageLoader {

    private static Picasso instance;

    /**
     * create shared instance for picasso.
     */
    public static Picasso getSharedInstance(Context context) {
        if (instance == null) {

            OkHttpClient okHttpClient = new OkHttpClient();
            SSLContext sslContext;
            try {
                sslContext = SSLContext.getInstance("TLS");

                final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] chain,
                            String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] chain,
                            String authType) throws CertificateException {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[0];
                    }
                }};
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                okHttpClient.setSslSocketFactory(sslContext.getSocketFactory());

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }

            instance = new Picasso.Builder(context)
                    .downloader(new OkHttpDownloader(okHttpClient))
                    .executor(Executors.newSingleThreadExecutor())
                    .indicatorsEnabled(false).listener(new Picasso.Listener() {
                        @Override
                        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                            Logger.e("Picasso Exception : " + exception.getMessage());
                            Logger.e("Picasso Uri : " + uri.toString());
                        }
                    })
                    .build();
        }
        return instance;
    }

    /**
     * load simple image to ImageView.
     * @param context   current class context.
     * @param imageUrl  url of the image.
     * @param imageView view where image will load
     */
    public static void loadImage(Context context, String imageUrl, ImageView imageView, int width, int height) {
        getSharedInstance(context)
                .load(imageUrl)
                .resize(width, height)
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(imageView);
    }

    /**
     * load simple image to ImageView.
     * @param context   current class context.
     * @param imageUrl  url of the image.
     * @param imageView view where image will load
     */
    public static void loadImage2(Context context, String imageUrl, ImageView imageView) {
        getSharedInstance(context)
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(imageView);
    }

    /** load image normally */
    public static void loadImageWithCircle(Context context, String imageUrl, ImageView imageView) {
        getSharedInstance(context)
                .load(imageUrl)
                //  .fit()
                .placeholder(R.drawable.default_photo_deactive)
                .error(R.drawable.default_photo_deactive)
                .into(imageView);
    }

    /** load image normally */
    public static void loadImageWithCircleProgress(Context context, String imageUrl, ImageView imageView, ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        getSharedInstance(context)
                .load(imageUrl)
                .fit()
                .placeholder(R.drawable.default_photo_deactive)
                .error(R.drawable.default_photo_deactive)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }


    /** load image normally */
    public static void loadImage(Context context, String imageUrl, ImageView imageView) {
        getSharedInstance(context)
                .load(imageUrl)
                .fit()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(imageView);
    }

    /** load image normally */
    public static void loadJobImage(Context context, String imageUrl, ImageView imageView) {
        getSharedInstance(context)
                .load(imageUrl)
                .fit()
               // .placeholder(R.drawable.default_job)
                .error(R.drawable.default_job)
                .into(imageView);
    }

    /** load image with round corner */
    public static void loadImageRound(Context context, String imageUrl, ImageView imageView) {
        getSharedInstance(context)
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .transform(new Rounded(20, Rounded.Corners.ALL)).into(imageView);
        // .into(imageView);
    }


    public static void loadImageRoundCallBack(Context context, String imageUrl, ImageView imageView, CallBack callBack) {
        getSharedInstance(context)
                .load(imageUrl)
                //    .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .transform(new Rounded(20, Rounded.Corners.ALL)).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                callBack.onSuccess();
            }

            @Override
            public void onError() {
                callBack.onError();
            }
        });
    }

    public interface CallBack {
        void onSuccess();

        void onError();
    }

    /** load image with round top corner */
    public static void loadImageRoundTop(Context context, String imageUrl, ImageView imageView) {
        getSharedInstance(context)
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .transform(new Rounded(30, Rounded.Corners.TOP)).into(imageView);
        // .placeholder(R.mipmap.ic_launcher)
        //  .error(R.mipmap.ic_launcher)
        // .into(imageView);
    }

    /**
     * load simple image to ImageView.
     * @param context     current class context.
     * @param imageUrl    url of the image.
     * @param imageView   view where image will load
     * @param progressBar showing loader on ImageView.
     */
    public static void loadImageCallback(Context context, String imageUrl, ImageView imageView, final ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        getSharedInstance(context)
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }


    /**
     * load simple image to ImageView.
     * @param context     current class context.
     * @param imageUrl    url of the image.
     * @param imageView   view where image will load
     * @param progressBar showing loader on ImageView.
     */
    public static void loadJobImageCallback(Context context,
                                            String imageUrl,
                                            ImageView imageView,
                                            final ProgressBar progressBar)
    {
       // imageUrl="http://i.imgur.com/1ALnB2s.gif";
        progressBar.setVisibility(View.VISIBLE);
    /*    getSharedInstance(context)
                .load(imageUrl)
              //  .placeholder(R.drawable.default_job)
                .error(R.drawable.default_job)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        progressBar.setVisibility(View.GONE);
                    }
                });*/
       /* Glide.with(context)
                .load("https://github.com/bumptech/glide")
                .placeholder(R.drawable.default_job)
                .into(imageView);*/
        Glide.with(context)
                .load(imageUrl)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView)
        ;
    }

    /** load image with round corner with callback */
    public static void loadImageRoundCallback(Context context, String imageUrl, ImageView imageView, final ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        getSharedInstance(context)
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .transform(new Rounded(20, Rounded.Corners.ALL))
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }


    /** load image normally */
    public static void loadStorageImage(Context context, String imageUrl, ImageView imageView) {
        getSharedInstance(context)
                .load(new File(imageUrl))
                .fit()
                .placeholder(R.drawable.default_photo_deactive)
                .error(R.drawable.default_photo_deactive)
                .into(imageView);
    }

}
