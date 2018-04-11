/*
 * Copyright (c) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.androidtv.webviewdriver.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.androidtv.webviewdriver.R;

public class FetchVideoURLActivity extends Activity {

    private WebView mWebView;
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetchvideourl);
        mUrl = getIntent().getStringExtra("urlString");
        mWebView = (WebView) findViewById(R.id.activity_fetchvideourl_webview);

        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Add Javascript Interface
        mWebView.addJavascriptInterface(new mJavascriptInterface(this),"jsi");

        retriveJSContent(mUrl,"watch1");
    }

    public void retriveJSContent(String urlString, String idString) {
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("pageFinished",url);
                switch(idString) {
                    case "watch1":
                        mWebView.loadUrl("javascript:window.jsi.retrive(document.getElementById('watch1').getAttribute('src'),'embed-container');");
                        break;
                    case "embed-container":
                        mWebView.loadUrl("javascript:window.jsi.retrive(document.getElementsByClassName('embed-container')[0].getElementsByTagName('iframe')[0].getAttribute('src'),'videooverlay');");
                        break;
                    case "videooverlay":
                        mWebView.loadUrl(
                                "javascript:(function() { " +
                                        "document.getElementById('videooverlay').click();" +
                                        "})();" +
                                        "window.jsi.retn(document.getElementById('olvideo_html5_api').getAttribute('src'));");
                }
            }
        });
        mWebView.loadUrl(urlString);
    }

    class mJavascriptInterface {

        private Context ctx;

        mJavascriptInterface(Context ctx) {
            this.ctx = ctx;
        }

        @JavascriptInterface
        public void showHTML(String videoUrl, String nextIdString) {
            Log.d("show url",videoUrl);
        }

        @JavascriptInterface
        public void retn(String videoUrl) {
            Intent data = new Intent();
            data.setData(Uri.parse(videoUrl));
            setResult(RESULT_OK, data);
            finish();
        }

        @JavascriptInterface
        public void retrive(String urlString, String nextIdString) {
            String rt = null;
            mWebView.post(new Runnable() {
                @Override
                public void run() {
                    retriveJSContent(urlString,nextIdString);
                }
            });
        }
    }

}
