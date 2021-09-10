package com.example.newsdaily;

import NewsUI.NewsBoxData;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class HistoryListActivity extends AppCompatActivity {
    ArrayList<NewsBoxData> historyData;
    ListView historyList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        historyList = findViewById(R.id.history_box_list);
        historyData = new ArrayList<>();
        System.out.println(NewsBoxData.count(NewsBoxData.class));
        historyData.addAll(NewsBoxData.listAll(NewsBoxData.class));
        HistoryBoxAdapter adapter = new HistoryBoxAdapter(this, R.layout.news_box, historyData);
        historyList.setAdapter(adapter);
        historyList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //页面跳转
                Intent intent = new Intent(HistoryListActivity.this, DetailActivity.class);
                intent.putExtra("url", historyData.get(position).getDetailUrl());
                startActivity(intent);
            }
        });
    }

    class HistoryBoxAdapter extends ArrayAdapter<NewsBoxData> {
        private Context mContext;
        private int mResource;

        public HistoryBoxAdapter(@NonNull Context context, int resource, @NonNull List<NewsBoxData> objects) {
            super(context, resource, objects);
            this.mContext = context;
            this.mResource = resource;
        }

        @SuppressLint("ViewHolder")
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater  = LayoutInflater.from(mContext);

            convertView = layoutInflater.inflate(mResource, parent, false);

            if (getItem(position).hasImg()) {
                ImageView imageView = convertView.findViewById(R.id.coverImg);
                TextView title = convertView.findViewById(R.id.Title);
                TextView des = convertView.findViewById(R.id.Description);
                TextView pub = convertView.findViewById(R.id.publisher);
                TextView pubTime = convertView.findViewById(R.id.publishTime);

                LoadImage loadImage = new LoadImage(imageView);
                loadImage.execute(getItem(position).getCoverImgURL()[0]);
                title.setText(getItem(position).getTitle());
                des.setText(getItem(position).getDescription());
                pub.setText(getItem(position).getPublisher());
                pubTime.setText(getItem(position).getPublishTime());
            } else {
                TextView title = convertView.findViewById(R.id.Title);
                TextView des = convertView.findViewById(R.id.Description);
                TextView pub = convertView.findViewById(R.id.publisher);
                convertView.findViewById(R.id.coverImg).setVisibility(View.GONE);
                TextView pubTime = convertView.findViewById(R.id.publishTime);

                title.setWidth(parent.getWidth());
                des.setWidth(parent.getWidth());
                pub.setWidth(parent.getWidth());

                title.setText(getItem(position).getTitle());
                des.setText(getItem(position).getDescription());
                pub.setText(getItem(position).getPublisher());
                pubTime.setText(getItem(position).getPublishTime());
            }

            return convertView;
        }
    }

}
