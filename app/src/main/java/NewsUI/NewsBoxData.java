package NewsUI;

public class NewsBoxData {
    String title;
    String Description;
    String[] coverImgURL;
    String publisher;
    boolean hasImg;

    public NewsBoxData(String t, String d, String[] c, String p) {
        title = t;
        Description = d;
        coverImgURL = c;
        publisher = p;
        hasImg = true;
    }

    public NewsBoxData(String t, String d, String p) {
        title = t;
        Description = d;
        publisher = p;
        hasImg = false;
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

    public boolean hasImg() {
        return hasImg;
    }
}
