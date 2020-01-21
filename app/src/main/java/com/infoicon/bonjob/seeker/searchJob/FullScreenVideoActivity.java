package com.infoicon.bonjob.seeker.searchJob;

/**
 * Created by info on 7/4/18.
 */
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.infoicon.bonjob.R;
import com.infoicon.bonjob.utils.Keys;

public class FullScreenVideoActivity  extends AppCompatActivity {

    private VideoView videoView;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullscreenvideo);

        videoView = findViewById(R.id.videoView);
        Uri videoUri = Uri.parse(getIntent().getStringExtra(Keys.URL));
        videoView.setVideoURI(videoUri);

        ProgressBar progress_bar=(ProgressBar)findViewById(R.id.progress_bar);

       videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
                        if (i == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START){
                            //first frame was bufered - do your stuff here
                            progress_bar.setVisibility(View.GONE);
                        }
                        return false;
                    }
                });

            }
        });



        mediaController= new MediaController(FullScreenVideoActivity.this);




        mediaController.setAnchorView(videoView);
       videoView.setMediaController(mediaController);



        videoView.start();
    }
}
