package NewsUI;

public class NewsBoxData {
    String title;
    String Description;
    String[] coverImgURL;
    String publisher;
    String detailUrl = "https://blog.csdn.net/YHyanghaoaixin/article/details/84772510?utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromMachineLearnPai2%7Edefault-4.essearch_pc_relevant&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromMachineLearnPai2%7Edefault-4.essearch_pc_relevant";
    String publishTime;
    boolean hasImg;

    public NewsBoxData(String t, String d, String[] c, String p, String _detailUrl, String _publishTime) {
        title = t;
        Description = d;
        coverImgURL = c;
        publisher = p;
        detailUrl = _detailUrl;
        hasImg = true;
        publishTime = _publishTime;
    }

    public NewsBoxData(String t, String d, String p, String _detailUrl, String _publishTIme) {
        title = t;
        Description = d;
        publisher = p;
        detailUrl = _detailUrl;
        hasImg = false;
        publishTime = _publishTIme;
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
}
