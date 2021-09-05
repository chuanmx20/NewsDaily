package com.example.newsdaily;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.gson.Gson;
import NewsUI.NewsBoxData;

import java.net.*;
import java.io.*;

import jsonBean.DataItem;
import jsonBean.Response;
import NewsUI.*;
import java.lang.Object;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    Handler mainHandler = new Handler();
    SwipeRefreshLayout swipeRefreshLayout;
    String curPageJson = "";
    Response curPageData;
    ListView newsList;
    List<NewsBoxData> newsBoxDataArray;
    MyAdapter adapter;

    SearchBar searchBar;
    String url = "https://api2.newsminer.net/svc/news/queryNewsList?size=15&startDate=2021-09-01&endDate=2021-09-03&words=拜登&categories=科技";
    String api = "https://api2.newsminer.net/svc/news/queryNewsList?";
    String size = "15";
    String startDate = "2021-08-20";
    String endDate = "2021-09-03";
    String keyWords = "拜登";
    String categories = "科技";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initview();
        //get initial news list
        refreshData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN && !searchBar.getText().toString().isEmpty())
                {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow((IBinder) getWindow().getDecorView().getWindowToken(), 0);
                    keyWords = searchBar.getText().toString();
                    searchBar.clearFocus();
                    refreshData();
                }
                return false;
            }
        });
    }

    private void setListData() {
        if (curPageData == null) {
            System.out.println("Null data");
            return;
        }
        newsBoxDataArray.clear();
        for (DataItem data : curPageData.getData()) {

            if (data.getImageUrls() != null)
                newsBoxDataArray.add(new NewsBoxData(data.getTitle(), data.getContent(), data.getImageUrls(), data.getPublisher()));
            else
                newsBoxDataArray.add(new NewsBoxData(data.getTitle(), data.getContent(), data.getPublisher()));

        }
        adapter.notifyDataSetChanged();
    }

    private void UrlCat() {
        url = api +
                "size=" + size + "&" +
                "startDate=" + startDate + "&" +
                "endDate=" + endDate + "&" +
                "words=" + keyWords + "&" +
                "categories=" + categories;

    }

    private void updateEndTime() {
        Date now = new Date(System.currentTimeMillis());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String timeNow = dataFormat.format(now);
    }
    private void refreshJson() {
        curPageData = new Gson().fromJson(curPageJson, Response.class);
    }

    private void refreshData() {
        updateEndTime();
        UrlCat();
        new fetchData(url).start();
        refreshJson();
        setListData();
    }

    @SuppressLint("ResourceType")
    private void initview()  {
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(132);
        searchBar = findViewById(R.id.searchBar);
        newsList = findViewById(R.id.newsList);
        newsBoxDataArray = new ArrayList<>();
        adapter = new MyAdapter(this, R.layout.news_box, newsBoxDataArray);
        newsList.setAdapter(adapter);
        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            /**
             * Callback method to be invoked when an item in this AdapterView has
             * been clicked.
             * <p>
             * Implementers can call getItemAtPosition(position) if they need
             * to access the data associated with the selected item.
             *
             * @param parent   The AdapterView where the click happened.
             * @param view     The view within the AdapterView that was clicked (this
             *                 will be a view provided by the adapter)
             * @param position The position of the view in the adapter.
             * @param id       The row id of the item that was clicked.
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
            }

        });
    }

    class fetchData extends Thread {
        String data = "";
        String mUrl = "";
        public fetchData(String _url) {
            mUrl = _url;
        }
        @Override
        public void run() {

            try {
                URL url = new URL(mUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream input = conn.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    data += line;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            mainHandler.post(new Runnable() {
                @Override
                public void run() {

                    curPageJson = data;
                }
            });

        }
    }

}