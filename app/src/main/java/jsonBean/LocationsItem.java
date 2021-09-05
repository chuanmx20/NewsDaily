package jsonBean;

import com.google.gson.annotations.SerializedName;

public class LocationsItem{

	@SerializedName("lng")
	private double lng;

	@SerializedName("count")
	private int count;

	@SerializedName("linkedURL")
	private String linkedURL;

	@SerializedName("lat")
	private double lat;

	@SerializedName("mention")
	private String mention;

	public void setLng(double lng){
		this.lng = lng;
	}

	public double getLng(){
		return lng;
	}

	public void setCount(int count){
		this.count = count;
	}

	public int getCount(){
		return count;
	}

	public void setLinkedURL(String linkedURL){
		this.linkedURL = linkedURL;
	}

	public String getLinkedURL(){
		return linkedURL;
	}

	public void setLat(double lat){
		this.lat = lat;
	}

	public double getLat(){
		return lat;
	}

	public void setMention(String mention){
		this.mention = mention;
	}

	public String getMention(){
		return mention;
	}

	@Override
 	public String toString(){
		return 
			"LocationsItem{" + 
			"lng = '" + lng + '\'' + 
			",count = '" + count + '\'' + 
			",linkedURL = '" + linkedURL + '\'' + 
			",lat = '" + lat + '\'' + 
			",mention = '" + mention + '\'' + 
			"}";
		}
}