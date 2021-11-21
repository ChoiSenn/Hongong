package com.example.hongong;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class search extends MainActivity {
    EditText url;
    Button search, back;
    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        url = (EditText) findViewById(R.id.url);
        search = (Button) findViewById(R.id.search);
        back = (Button) findViewById(R.id.back);
        web = (WebView) findViewById(R.id.web);

        web.setWebViewClient(new CookWebViewClient());

        WebSettings webSet = web.getSettings();
        webSet.setBuiltInZoomControls(true);
        webSet.setJavaScriptEnabled(true);

        search.setOnClickListener(new View.OnClickListener(){  // 입력받은 텍스트로 검색
            @Override
            public void onClick(View view) {
                web.loadUrl("https://www.google.co.kr/search?q=" + url.getText().toString());
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                web.goBack();
            }
        });
    }

    class CookWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }
}
