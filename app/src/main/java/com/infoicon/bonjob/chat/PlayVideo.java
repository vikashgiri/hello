package com.infoicon.bonjob.chat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.utils.Keys;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PlayVideo extends Fragment {
    private View dialog;
    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dialog = inflater.inflate(R.layout.chat_video_image, container, false);
        unbinder = ButterKnife.bind(this, dialog);


        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        WebView pdf = (WebView) dialog.findViewById(R.id.pdf);
        VideoView videoView = (VideoView) dialog.findViewById(R.id.videoView2);
        TextView close = (TextView) dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().
            }
        });

        /*if(type.equalsIgnoreCase("video"))
        {*/

            Uri uri = Uri.parse((StaticConfig.BID_TOKEN));



          //  Read more: http://mrbool.com/how-to-play-video-formats-in-android-using-videoview/28299#ixzz5zOdEgBrJ


        return dialog;
    }
}

   /*     }

        if(type.equalsIgnoreCase("file"))
        {
            pdf.setVisibility(View.VISIBLE);
            try {
                pdf.getSettings().setJavaScriptEnabled(true);
                url= URLEncoder.encode(url,"UTF-8");
                pdf.loadUrl("https://docs.google.com/gview?embedded=true&url="+url);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if(type.equalsIgnoreCase("image"))
        {
            image.setVisibility(View.VISIBLE);
            Glide.with(ChatActivity.this.getActivity())
                    .load(url)
                    .placeholder(R.drawable.default_icon)
                    .into(image);
        }

        dialog.show();
    }
        return rootView;
    }*/
