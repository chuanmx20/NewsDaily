package com.NewsDaily.chuanmingxi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.chuanmingxi.R;

import static android.content.ContentValues.TAG;

public class DetailActivity extends AppCompatActivity {
    private static final String APP_CACHE_DIRNAME = "wedStorage";
    View backBtn;
    View collectBtn;
    boolean isCollected;
    String url;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        backBtn = findViewById(R.id.back_btn);
        collectBtn = findViewById(R.id.collect_btn);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        url = getIntent().getStringExtra("url");
        isCollected = getIntent().getBooleanExtra("collect", false);
        collectBtn.setBackgroundResource(isCollected ? R.drawable.ic_star_fill : R.drawable.ic_star);

        WebView webView = findViewById(R.id.news_web);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); // 设置缓存模式
        webView.getSettings().setDomStorageEnabled(true);       //DOM storage API
        webView.getSettings().setDatabaseEnabled(true);         //database storage
        String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACHE_DIRNAME;
        Log.i(TAG, "cachePath=" + cacheDirPath);
        webView.getSettings().setDatabasePath(cacheDirPath);
        webView.getSettings().setAppCachePath(cacheDirPath);
        webView.getSettings().setAppCacheEnabled(true);
                

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) { view.loadUrl(url); return super.shouldOverrideUrlLoading(view, url); } });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        collectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCollected = !isCollected;
                collectBtn.setBackgroundResource(isCollected ? R.drawable.ic_star_fill : R.drawable.ic_star);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("url", url);
        intent.putExtra("collect", isCollected);
        setResult(2, intent);
        super.finish();
    }
}
