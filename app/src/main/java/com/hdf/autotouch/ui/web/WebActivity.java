package com.hdf.autotouch.ui.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.BaseActivity;

import butterknife.BindView;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2019/7/12
 *     desc  : WebActivity
 * </pre>
 */
public class WebActivity extends BaseActivity {

    @BindView(R.id.parent_view)
    FrameLayout parentView;

    private WebView mWebView;
    private String  mUrl;

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", url);
        ActivityUtils.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        if (bundle != null) {
            mUrl = bundle.getString("url");
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_web;
    }

    @Override
    public void initView() {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView = new WebView(getApplicationContext());
        mWebView.setLayoutParams(params);
        parentView.addView(mWebView, parentView.getChildCount());

        initWebSetting();
        initWebClient();

        mWebView.loadUrl(mUrl);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebSetting() {
        WebSettings mWebSettings = mWebView.getSettings();

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        mWebSettings.setJavaScriptEnabled(true);
        //若加载的html里有JS在执行动画等操作，会造成资源浪费（CPU、电量）
        //在onStop和onResume里分别把setJavaScriptEnabled()给设置成false和true即可

        //支持插件
        mWebSettings.setPluginState(WebSettings.PluginState.ON_DEMAND);

        //设置自适应屏幕，两者合用
        //将图片调整到适合webview的大小
        mWebSettings.setUseWideViewPort(true);
        //缩放至屏幕的大小
        mWebSettings.setLoadWithOverviewMode(true);

        //缩放操作
        //支持缩放，默认为true。是下面那个的前提
        mWebSettings.setSupportZoom(true);
        //设置内置的缩放控件。若为false，则该WebView不可缩放
        mWebSettings.setBuiltInZoomControls(true);
        //隐藏原生的缩放控件
        mWebSettings.setDisplayZoomControls(false);

        //其他细节操作
        //关闭webview中缓存
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //设置可以访问文件
        mWebSettings.setAllowFileAccess(true);
        //支持通过JS打开新窗口
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //支持自动加载图片
        mWebSettings.setLoadsImagesAutomatically(true);
        //设置编码格式
        mWebSettings.setDefaultTextEncodingName("utf-8");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    private void initWebClient() {
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                //获取网站标题
                setTitleText(title);
            }

            @Override
            public void onProgressChanged(WebView view, int progress) {
                //获取加载进度
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                //支持javascript的确认框
                //返回布尔值：判断点击时确认还是取消
                //true表示点击了确认；false表示点击了取消；
                return true;
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                //支持javascript输入框
                //点击确认返回输入框中的值，点击取消返回 null。
                return true;
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //设置不用系统浏览器打开,直接显示在当前Webview
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //设置加载前的函数
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //设置结束加载函数
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                //设定加载资源的操作
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                //该方法传回了错误码，根据错误类型可以进行不同的错误分类处理
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();//表示等待证书响应
                //handler.cancel();//表示挂起连接，为默认方式
                //handler.handleMessage(null);//可做其他处理
            }
        });
    }

    @Override
    public void doBusiness() {
    }

    @Override
    public void onViewClick(@NonNull View view) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
