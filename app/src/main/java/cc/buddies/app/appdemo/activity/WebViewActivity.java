package cc.buddies.app.appdemo.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;

import cc.buddies.app.appdemo.BaseActivity;
import cc.buddies.app.appdemo.R;

/**
 * WebView的使用
 * https://blog.csdn.net/yllp_1230/article/details/80655350
 */
public class WebViewActivity extends BaseActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        addWebView();
        init();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onResume() {
        super.onResume();
        webView.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        webView.getSettings().setJavaScriptEnabled(false);
    }

    private void addWebView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        webView = new WebView(getApplicationContext());
        webView.setLayoutParams(params);
        ViewGroup rootLayout = findViewById(android.R.id.content);
        if (rootLayout != null) {
            rootLayout.addView(webView);
        }
    }

    /**
     * WebView销毁的时候，先让 WebView 加载null内容，然后移除 WebView，再销毁 WebView，最后置空。
     */
    private void removeWebView() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        WebSettings webSettings = webView.getSettings();

        // 支持JavaScript交互
        webSettings.setJavaScriptEnabled(true);

        // 自适应屏幕
        webSettings.setUseWideViewPort(true);       // 将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true);  // 缩放至屏幕大小

        // 缩放操作
        webSettings.setSupportZoom(true);           // 支持缩放，默认为true，是下面缩放控件的前提。
        webSettings.setBuiltInZoomControls(true);   // 使用内置缩放控件，若为false，则不可缩放。
        webSettings.setDisplayZoomControls(false);  // 隐藏原生的缩放控件

        // 其他
        webSettings.setAllowFileAccess(true);                           // 设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);     // 支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true);                  // 支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");                // 设置编码格式

        /*
            当加载 html 页面时，WebView会在/data/data/包名目录下生成 database 与 cache 两个文件夹。
            请求的 URL记录保存在 WebViewCache.db，而 URL的内容是保存在 WebViewCache 文件夹下。

            缓存模式如下：
            LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
            LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
            LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
            LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
         */
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        // 特别注意：5.1以上默认禁止了https和http混用，以下方式是开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }



        // 加载方式
        // 1. 加载网页链接
//        webView.loadUrl("http://www.baidu.com");
        // 2. 加载apk包中本地html文件(asset目录资源)
//        webView.loadUrl("file://android_asset/test.html");
        // 3. 加载手机本地html文件(FileProvider)
//        webView.loadUrl("content://com.android.htmlfileprovider/sdcard/test.html");


        // 重写shouldOverrideUrlLoading()方法，使打开网页时不调用系统浏览器，而在WebView中。
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                // 设定开始加载操作
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // 设定加载结束操作
            }

            // 在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次。
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                // 设定加载资源操作
            }

            // 加载页面的服务器出现错误时（如404）调用。
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                // 加载页面出错调用，可在此加载本地错误提示页面
                switch (errorCode) {
                    case 404:
                        view.loadUrl("file://android_assets/error_handle.html");
                        break;
                }
            }

            // webView默认是不处理https请求的，页面显示空白，在此设置处理https请求。
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();      // 表示等待证书响应
                // handler.cancel();       // 表示挂起连接，默认方式
                // handler.handleMessage(null);     // 可做其他处理
            }
        });


        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                // 网页加载进度
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                // 获取网页标题
            }

            // 支持javascript的警告框
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                new AlertDialog.Builder(WebViewActivity.this)
                        .setTitle("JsAlert")
                        .setMessage(message)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        })
                        .setCancelable(false)
                        .show();
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                new AlertDialog.Builder(WebViewActivity.this)
                        .setTitle("JsConfirm")
                        .setMessage(message)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.cancel();
                            }
                        })
                        .setCancelable(false)
                        .show();
                // 返回布尔值：判断点击时确认还是取消
                // true表示点击了确认；false表示点击了取消；
                return true;
            }

            // 支持javascript输入框
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
                final EditText et = new EditText(WebViewActivity.this);
                et.setText(defaultValue);
                new AlertDialog.Builder(WebViewActivity.this)
                        .setTitle(message)
                        .setView(et)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm(et.getText().toString());
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.cancel();
                            }
                        })
                        .setCancelable(false)
                        .show();
                return true;
            }
        });
    }


    @Override
    protected void onDestroy() {
        removeWebView();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
