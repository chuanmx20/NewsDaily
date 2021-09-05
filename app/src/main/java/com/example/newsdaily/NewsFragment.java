package com.example.newsdaily;

import NewsUI.NewsBoxData;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.gson.Gson;
import jsonBean.DataItem;
import jsonBean.Response;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewsFragment extends Fragment {
    Handler mainHandler;
    ListView newsList;
    List<NewsBoxData> newsBoxDataArray;
    MyAdapter adapter;
    Response curPageData;
    String curPageJson = "";

    String url = "https://api2.newsminer.net/svc/news/queryNewsList?size=15&startDate=2021-09-01&endDate=2021-09-03&words=拜登&categories=科技";
    String api = "https://api2.newsminer.net/svc/news/queryNewsList?";
    String size = "15";
    String startDate = "2021-08-20";
    String endDate = "2021-09-03";
    String keyWords = "拜登";
    String categories = "科技";

    public NewsFragment(String category) {
        categories = category;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainHandler = new Handler();
        newsList = container.findViewById(R.id.newsList);
        newsBoxDataArray = new ArrayList<>();
        adapter = new MyAdapter(getContext(), R.layout.news_box, newsBoxDataArray);
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
                Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
            }

        });

        return inflater.inflate(R.layout.tag_bar, container, false);
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
