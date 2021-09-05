package jsonBean;

import com.google.gson.annotations.SerializedName;

public class OrganizationsItem{

	@SerializedName("count")
	private int count;

	@SerializedName("linkedURL")
	private String linkedURL;

	@SerializedName("mention")
	private String mention;

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

	public void setMention(String mention){
		this.mention = mention;
	}

	public String getMention(){
		return mention;
	}

	@Override
 	public String toString(){
		return 
			"OrganizationsItem{" + 
			"count = '" + count + '\'' + 
			",linkedURL = '" + linkedURL + '\'' + 
			",mention = '" + mention + '\'' + 
			"}";
		}
}