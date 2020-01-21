package com.infoicon.bonjob.chat;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.infoicon.bonjob.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ShowUrl extends Activity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_video_image);
        ProgressBar progress_bar = (ProgressBar) findViewById(R.id.progress_bar);

        ImageView image = (ImageView) findViewById(R.id.image);
        WebView pdf = (WebView) findViewById(R.id.pdf);
        VideoView videoView = (VideoView)findViewById(R.id.videoView2);
        TextView close = (TextView) findViewById(R.id.close);
        String type=getIntent().getStringExtra("type");
        String url=getIntent().getStringExtra("url");
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        if(type.equalsIgnoreCase("video"))
        {


            MediaController mc= new MediaController(this);
            videoView.setMediaController(mc);
            videoView.setVisibility(View.VISIBLE);
            videoView.setVideoPath(""+url);
            videoView.start();

    videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            progress_bar.setVisibility(View.GONE);
        }
    });


        }

        if(type.equalsIgnoreCase("file"))
        {
            pdf.setVisibility(View.VISIBLE);
            try {
                pdf.getSettings().setJavaScriptEnabled(true);
                url= URLEncoder.encode(url,"UTF-8");
                pdf.loadUrl("https://docs.google.com/gview?embedded=true&url="+url);
                progress_bar.setVisibility(View.GONE);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        pdf.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                progress_bar.setVisibility(View.GONE);
            }
        });
        if(type.equalsIgnoreCase("image"))
        {
            image.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(url)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            progress_bar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(image);
           /* Glide.with(this)
                    .load(url)
                    .placeholder(R.drawable.default_icon)
                    .into(image).se;
            progress_bar.setVisibility(View.GONE);*/
    }
    }
}
