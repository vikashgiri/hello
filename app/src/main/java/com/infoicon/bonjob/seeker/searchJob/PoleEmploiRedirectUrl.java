package com.infoicon.bonjob.seeker.searchJob;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.infoicon.bonjob.R;

import dmax.dialog.SpotsDialog;

public class PoleEmploiRedirectUrl extends AppCompatActivity {
     SpotsDialog spotsDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pole_emploi_redirect_url);
        WebView htmlWebView = (WebView) findViewById(R.id.webView);
        htmlWebView.setWebViewClient(new CustomWebViewClient());
        WebSettings webSetting = htmlWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDisplayZoomControls(true);
         spotsDialog = new SpotsDialog(this, R.style.Custom);
        spotsDialog.setCancelable(false);
            spotsDialog.show();
        htmlWebView.loadUrl(""+getIntent().getStringExtra("redirect_url"));
    }





    private class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
///media/info/InfoiconKeyStore.jks
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if(spotsDialog!=null){
                spotsDialog.dismiss();

            }
        }
    }
}

