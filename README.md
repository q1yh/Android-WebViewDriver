# Android-WebViewDriver
Here is an example of crawling JS generated content by Android's Chromium-based webview and some Javascript play.
Actually there are lots of utils for crawling interenet contents either from web service API or all sorts of web pages: 
- JSoup is pretty good, but cannot execute javascript. 
- Selenium Web Driver also looks good, but need a server to run.
- etc.
But sometimes Chromium-based webview is enough.


1. Setting WebView in an activity:
```
// Enable Javascript
WebSettings webSettings = mWebView.getSettings();
webSettings.setJavaScriptEnabled(true);
// Add Javascript Interface
mWebView.addJavascriptInterface(new mJavascriptInterface(this),"jsi");
```

2. Define Javascript Interface:
```
class mJavascriptInterface {
        private Context ctx;
        mJavascriptInterface(Context ctx) { this.ctx = ctx;}

        @JavascriptInterface
        public void showInLog(String jsrunresult) {
            Log.d("show content",jsrunresult);
        }
```

3. Play: retrive JS generated content through Javascript Interface:
```
public void retriveJSContent(String urlString) {
  mWebView.setWebViewClient(new WebViewClient(){
    @Override
    public void onPageFinished(WebView view, String url) {
      super.onPageFinished(view, url);
      //retrive content through JS Interface
      mWebView.loadUrl("javascript:window.jsi.showInLog(document.getElementById('watch').getAttribute('src'));");
    }
  }
  //Load web service or web page
  mWebView.loadUrl(urlString);
}
```

Under this circumstance, some more complex cases could be play. Enjoy!
