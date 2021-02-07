package com.example.permissions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class WebViews extends AppCompatActivity {

    WebView wv1;
    EditText etSearch;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_views);
        etSearch = (EditText)findViewById(R.id.etSearch);
        wv1 = (WebView)findViewById(R.id.wv1);
        wv1.setWebViewClient(new myWebView());
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv1.loadUrl("http://alruabye.net/");
    }

    public void buBack(View view) {
        wv1.goBack();
    }

    public void buForward(View view) {
        wv1.goForward();
    }

    public void buGo(View view) {
        LoadURL(etSearch.getText().toString());
    }
    void LoadURL(String url){
        this.url = url;
        wv1.loadUrl(url);
    }

    public void nextLayout(View view) {
        Intent intent = new Intent(this, mediaPlayer.class);
        startActivity(intent);
    }

    private class myWebView extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(url);
            return true;
        }
    }
}