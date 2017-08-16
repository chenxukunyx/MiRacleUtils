package com.miracle.libs.ui.jianshu;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.miracle.libs.R;

/**
 * Create with Android studio
 *
 * @fuction: 根据传入的链接，跳转不同的H5页面
 * @author: chenxukun
 * @date: 2017-08-16
 * @time: 16:25
 * @age: 24
 */
public class DetailActivity  extends Activity{

    private String url;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        webView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(webView);

        url = getIntent().getStringExtra("link");
        initWebview(url);

        initWebsetting();

        initWebClient();

        initWebChromeClient();
    }

    private void initWebview(String url) {
        webView.loadUrl(url);
    }

    private void initWebsetting() {
        WebSettings settings = webView.getSettings();

        //支持获取手势焦点
        webView.requestFocusFromTouch();
        //支持JS
        settings.setJavaScriptEnabled(true);
        //支持插件
        settings.setPluginState(WebSettings.PluginState.ON);
        //设置适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //支持缩放
        settings.setSupportZoom(true);
        //隐藏原生得缩放控件
        settings.setDisplayZoomControls(false);
        //支持内容重新布局
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.supportMultipleWindows();
        settings.setSupportMultipleWindows(true);
        //设置缓存模式
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(webView.getContext().getCacheDir().getAbsolutePath());

        //设置可访问文件
        settings.setAllowFileAccess(true);
        //当webview调用requestFocus时为webview设置节点
        settings.setNeedInitialFocus(true);
        //支持自动加载图片
        if (Build.VERSION.SDK_INT >= 19){
            settings.setLoadsImagesAutomatically(true);
        } else {
            settings.setLoadsImagesAutomatically(false);
        }
        settings.setNeedInitialFocus(true);
        //设置编码格式
        settings.setDefaultTextEncodingName("UTF-8");
    }

    private void initWebClient() {
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.toString());
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);

            }
        });
    }

    private void initWebChromeClient() {
        webView.setWebChromeClient(new WebChromeClient(){
            private Bitmap mDefaultVideoPoster;//默认得视频展示图

            @Override
            public void onReceivedTitle(WebView view, final String title) {
                super.onReceivedTitle(view, title);
//                if (mTbShow != null){
//                    mTbShow.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            mTbShow.setTitle(TextUtils.isEmpty(title) ? "加载中..." : title);
//                        }
//                    });
//                }
            }

            @Override
            public Bitmap getDefaultVideoPoster() {
                if (mDefaultVideoPoster == null){
                    mDefaultVideoPoster = BitmapFactory.decodeResource(getResources(), R.drawable.like);
                    return mDefaultVideoPoster;
                }
                return super.getDefaultVideoPoster();
            }
        });
    }
}
