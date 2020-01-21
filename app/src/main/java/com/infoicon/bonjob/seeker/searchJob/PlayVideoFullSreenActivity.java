package com.infoicon.bonjob.seeker.searchJob;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.infoicon.bonjob.R;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayVideoFullSreenActivity extends AppCompatActivity {


    @BindView(R.id.videoView) VideoView videoView;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video_full_sreen);
        ButterKnife.bind(this);
        initialize();
    }


    private void initialize() {
        // Start the MediaController
        MediaController mediacontroller = new MediaController(this);
        mediacontroller.setAnchorView(videoView);
        // Get the URL from String VideoURL
        url = getIntent().getStringExtra(Keys.PATCH_VIDEO);
        videoView.setMediaController(mediacontroller);
        videoView.setVideoPath(url);
        UtilsMethods.disableSSLCertificateChecking();
        videoView.start();
        showProgress(true);
        videoView.setOnPreparedListener(mp -> showProgress(false));

        videoView.setOnCompletionListener(mp -> showProgress(false));

        videoView.setOnErrorListener((mp, what, extra) -> {
            showProgress(false);
            return true;
        });
    }


    /** show progressbar */
    private void showProgress(boolean status) {
        if (status) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
