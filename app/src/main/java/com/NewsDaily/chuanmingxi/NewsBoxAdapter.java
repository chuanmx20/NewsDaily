package com.NewsDaily.chuanmingxi;

import NewsUI.NewsBoxData;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.chuanmingxi.R;

import java.util.List;

public class NewsBoxAdapter extends ArrayAdapter<NewsBoxData> {
    private Context mContext;
    private int mResource;

    public NewsBoxAdapter(@NonNull Context context, int resource, @NonNull List<NewsBoxData> objects) {
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

        if (NewsBoxData.isVisited(getItem(position))) {
            visit(convertView);
        }

        return convertView;
    }

    public static void visit(View view) {
        TextView title = view.findViewById(R.id.Title);
        TextView des = view.findViewById(R.id.Description);
        title.setTextColor(Color.rgb(133, 116, 116));
        des.setTextColor(Color.rgb(133, 116, 116));
    }
}
