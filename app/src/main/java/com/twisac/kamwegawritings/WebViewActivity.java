package com.twisac.kamwegawritings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * Created by egafic on 3/5/2016.
 */
public class WebViewActivity extends AppCompatActivity {
WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webView = (WebView) findViewById(R.id.webView2);
        webView.getSettings().setJavaScriptEnabled(true);

/*
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.kamwegawritings.com/drink-up-son/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements element = doc.select("#comments");
        webView.loadData(element.html(), "text/html", "UTF-8");
        */
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url)
            {
                view.loadUrl("javascript:" +
                        "document.getElementsById('comments').style.display=none'; ");
            }
        });
        webView.loadUrl("http://www.kamwegawritings.com/drink-up-son/");
    }
}
