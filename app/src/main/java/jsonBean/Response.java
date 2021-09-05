package jsonBean;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Response{

	@SerializedName("total")
	private int total;

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("pageSize")
	private String pageSize;

	@SerializedName("currentPage")
	private String currentPage;

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	public void setPageSize(String pageSize){
		this.pageSize = pageSize;
	}

	public String getPageSize(){
		return pageSize;
	}

	public void setCurrentPage(String currentPage){
		this.currentPage = currentPage;
	}

	public String getCurrentPage(){
		return currentPage;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"total = '" + total + '\'' + 
			",data = '" + data + '\'' + 
			",pageSize = '" + pageSize + '\'' + 
			",currentPage = '" + currentPage + '\'' + 
			"}";
		}
}