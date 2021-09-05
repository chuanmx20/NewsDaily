package jsonBean;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("image")
	private String image;

	@SerializedName("publishTime")
	private String publishTime;

	@SerializedName("keywords")
	private List<KeywordsItem> keywords;

	@SerializedName("language")
	private String language;

	@SerializedName("video")
	private String video;

	@SerializedName("title")
	private String title;

	@SerializedName("when")
	private List<WhenItem> when;

	@SerializedName("content")
	private String content;

	@SerializedName("url")
	private String url;

	@SerializedName("persons")
	private List<PersonsItem> persons;

	@SerializedName("newsID")
	private String newsID;

	@SerializedName("crawlTime")
	private String crawlTime;

	@SerializedName("organizations")
	private List<Object> organizations;

	@SerializedName("publisher")
	private String publisher;

	@SerializedName("locations")
	private List<LocationsItem> locations;

	@SerializedName("where")
	private List<Object> where;

	@SerializedName("scholars")
	private List<Object> scholars;

	@SerializedName("category")
	private String category;

	@SerializedName("who")
	private List<WhoItem> who;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public String[] getImageUrls() {
		if (image.equals("[]"))
			return null;
		//divide image string
		return image.substring(1, image.length() - 1).split(", ");
	}

	public void setPublishTime(String publishTime){
		this.publishTime = publishTime;
	}

	public String getPublishTime(){
		return publishTime;
	}

	public void setKeywords(List<KeywordsItem> keywords){
		this.keywords = keywords;
	}

	public List<KeywordsItem> getKeywords(){
		return keywords;
	}

	public void setLanguage(String language){
		this.language = language;
	}

	public String getLanguage(){
		return language;
	}

	public void setVideo(String video){
		this.video = video;
	}

	public String getVideo(){
		return video;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setWhen(List<WhenItem> when){
		this.when = when;
	}

	public List<WhenItem> getWhen(){
		return when;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		//去广告...
		if (content.substring(0, 3).equals("访问："))
			return content.substring(37);
		if (content.substring(0, 4).equals("用微信扫"))
			return content.substring(19);
		else return content;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setPersons(List<PersonsItem> persons){
		this.persons = persons;
	}

	public List<PersonsItem> getPersons(){
		return persons;
	}

	public void setNewsID(String newsID){
		this.newsID = newsID;
	}

	public String getNewsID(){
		return newsID;
	}

	public void setCrawlTime(String crawlTime){
		this.crawlTime = crawlTime;
	}

	public String getCrawlTime(){
		return crawlTime;
	}

	public void setOrganizations(List<Object> organizations){
		this.organizations = organizations;
	}

	public List<Object> getOrganizations(){
		return organizations;
	}

	public void setPublisher(String publisher){
		this.publisher = publisher;
	}

	public String getPublisher(){
		return publisher;
	}

	public void setLocations(List<LocationsItem> locations){
		this.locations = locations;
	}

	public List<LocationsItem> getLocations(){
		return locations;
	}

	public void setWhere(List<Object> where){
		this.where = where;
	}

	public List<Object> getWhere(){
		return where;
	}

	public void setScholars(List<Object> scholars){
		this.scholars = scholars;
	}

	public List<Object> getScholars(){
		return scholars;
	}

	public void setCategory(String category){
		this.category = category;
	}

	public String getCategory(){
		return category;
	}

	public void setWho(List<WhoItem> who){
		this.who = who;
	}

	public List<WhoItem> getWho(){
		return who;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"image = '" + image + '\'' + 
			",publishTime = '" + publishTime + '\'' + 
			",keywords = '" + keywords + '\'' + 
			",language = '" + language + '\'' + 
			",video = '" + video + '\'' + 
			",title = '" + title + '\'' + 
			",when = '" + when + '\'' + 
			",content = '" + content + '\'' + 
			",url = '" + url + '\'' + 
			",persons = '" + persons + '\'' + 
			",newsID = '" + newsID + '\'' + 
			",crawlTime = '" + crawlTime + '\'' + 
			",organizations = '" + organizations + '\'' + 
			",publisher = '" + publisher + '\'' + 
			",locations = '" + locations + '\'' + 
			",where = '" + where + '\'' + 
			",scholars = '" + scholars + '\'' + 
			",category = '" + category + '\'' + 
			",who = '" + who + '\'' + 
			"}";
		}
}