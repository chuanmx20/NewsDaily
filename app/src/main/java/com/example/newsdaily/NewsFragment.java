package com.example.newsdaily;

import NewsUI.NewsBoxData;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.gson.Gson;
import JavaBean.DataItem;
import JavaBean.Response;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    Handler mainHandler = new Handler();
    SwipeRefreshLayout swipeRefreshLayout;
    ListView newsList;
    List<NewsBoxData> newsBoxDataArray;
    NewsBoxAdapter adapter;
    Response curPageData;
    String curPageJson = "";

    String url = "https://api2.newsminer.net/svc/news/queryNewsList?size=15&startDate=2021-09-01&endDate=2021-09-03&words=拜登&categories=科技";
    String api = "https://api2.newsminer.net/svc/news/queryNewsList?";
    String size = "15";
    String startDate = "2000-08-20";
    String endDate = MainActivity.getCurDate();
    String keyWords = "全部";
    String categories = "";

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getSize() {
        return size;
    }

    public void setCurPageJson(String _curPageJson) {
        curPageJson = _curPageJson;
    }

    public String getUrl() {
        return url;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getCategories() {
        return categories;
    }

    public NewsFragment(String category) {
        if (category.equals("全部"))
            return;
        categories = category;
    }

    public NewsFragment(String category, String start, String end, String key) {
        startDate = start;
        endDate = end;
        keyWords = key;
        if (category.equals("全部"))
            return;
        categories = category;
    }

    public NewsFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainHandler = new Handler();
        swipeRefreshLayout = getView().findViewById(R.id.swipeRefreshLayout);

        newsList = getView().findViewById(R.id.newsList);
        newsBoxDataArray = new ArrayList<>();
        adapter = new NewsBoxAdapter(getContext(), R.layout.news_box, newsBoxDataArray);
        newsList.setAdapter(adapter);
        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //页面跳转
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("url", newsBoxDataArray.get(position).getDetailUrl());
                startActivity(intent);
                newsBoxDataArray.get(position).save();
                NewsBoxAdapter.visit(view);
                view.invalidate();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                NewsBoxData.deleteAll(NewsBoxData.class);
                refreshData();
                System.out.println("Refreshing fragment of " + categories);
                System.out.println(startDate + "," + endDate + "," + keyWords + "," + size);
            }
        });
    }


    @Override
    public void onStart() {
        refreshData();
        super.onStart();
    }

    private void UrlCat() {
        url = api +
                "size=" + size + "&" +
                "startDate=" + startDate + "&" +
                "endDate=" + endDate + "&" +
                "words=" + keyWords + "&" +
                "categories=" + categories;

    }

    private void setListData() {
        if (curPageData == null) {
            return;
        }
        if (!newsBoxDataArray.isEmpty())
            newsBoxDataArray.clear();
        for (DataItem data : curPageData.getData()) {

            if (data.getImageUrls() != null)
                newsBoxDataArray.add(new NewsBoxData(data.getTitle(), data.getContent(), data.getImageUrls(), data.getPublisher(), data.getUrl(), data.getPublishTime()));
            else
                newsBoxDataArray.add(new NewsBoxData(data.getTitle(), data.getContent(), data.getPublisher(), data.getUrl(), data.getPublishTime()));

        }
        adapter.notifyDataSetChanged();
    }

    private void refreshJson() {
        curPageData = new Gson().fromJson(curPageJson, Response.class);
    }

    public void refreshData() {
        UrlCat();
        new fetchData(url).start();
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

                    refreshJson();
                    setListData();

                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }

    }
}

