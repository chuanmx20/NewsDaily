package NewsUI;

import com.orm.SugarRecord;

import java.util.List;

public class NewsBoxData extends SugarRecord {
    String title;
    String Description;
    String[] coverImgURL;
    String publisher = "";
    String detailUrl = "";
    String publishTime = "";
    boolean hasImg;
    String date = "";

    public NewsBoxData(String t, String d, String[] c, String p, String _detailUrl, String _publishTime, String date) {
        title = t;
        Description = d;
        coverImgURL = c;
        publisher = p;
        detailUrl = _detailUrl;
        hasImg = true;
        publishTime = _publishTime;
        this.date = date;
    }

    public NewsBoxData() {}

    public NewsBoxData(String t, String d, String p, String _detailUrl, String _publishTIme, String date) {
        title = t;
        Description = d;
        publisher = p;
        detailUrl = _detailUrl;
        hasImg = false;
        publishTime = _publishTIme;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return Description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        Description = description;
    }


    public void setCoverImgURL(String[] coverImgURL) {
        this.coverImgURL = coverImgURL;
    }

    public String[] getCoverImgURL() {
        return coverImgURL;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public boolean hasImg() {
        return hasImg;
    }

    public String getDetailUrl() {
        return detailUrl;
    }


    public static boolean isVisited(NewsBoxData newsBoxData) {
        List<NewsBoxData> list = NewsBoxData.listAll(NewsBoxData.class);
        if (list.isEmpty()) return false;
        for (NewsBoxData news : list) {
            if (news.getTitle().equals(newsBoxData.getTitle())) {
                return true;
            }
        }
        return false;
    }

}
