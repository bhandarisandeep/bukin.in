package com.bukin.in;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.view.KeyEvent;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "check1";
    @BindView(R.id.mainPage)WebView webView_Mainpage;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView_Mainpage.canGoBack()) {
            webView_Mainpage.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);


        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        webView_Mainpage.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // view.findViewById(R.id.p_bar).setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                findViewById(R.id.image_loading).setVisibility(View.GONE);
                findViewById(R.id.p_bar).setVisibility(View.GONE);

                //show webview
                webView_Mainpage.setVisibility(View.VISIBLE);
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if( URLUtil.isNetworkUrl(url) ) {
                    return false;
                }
                if (url.contains("whatsapp")){
                    String text = "Hi,%20I%20would%20like%20to%20have%20your%20services.";// Replace with your message.
                    String toNumber = " +919520948170";
                    Log.d(TAG, "inside whatsapp: *****************the uri is***********************"+url);
                    Intent sendIntent = new Intent(); sendIntent.setAction(Intent.ACTION_VIEW);
                    sendIntent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+toNumber +"&text="+text));
                    //sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
//                    sendIntent.setType("text/plain");
                    sendIntent.setPackage("com.whatsapp");
                    startActivity(sendIntent);
                }
                if (appInstalledOrNot(url)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity( intent );
                } else {
                    // do something if app is not installed
                    Toast.makeText(getApplicationContext(),"The Relevant application is not Installed", Toast.LENGTH_SHORT).show();
                }
                return true;
            }

        });

        // setting the webview client.
        //webView_Mainpage.canGoBack();


        WebSettings webSettings = webView_Mainpage.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView_Mainpage.loadUrl("https://bukin.in");





    }
    private boolean appInstalledOrNot(String uri) {
        Log.d(TAG, "appInstalledOrNot: *****************the uri is***********************"+uri);
        PackageManager pm = getPackageManager();
        try {
            Log.d(TAG, "appInstalledOrNot: *****************get package maner is ***********************"+ getPackageManager());
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            Log.d(TAG, "appInstalledOrNot: *****************the uri is ***********************"+uri);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }


}
