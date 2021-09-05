package jsonBean;

import com.google.gson.annotations.SerializedName;

public class WhoItem{

	@SerializedName("score")
	private double score;

	@SerializedName("word")
	private String word;

	public void setScore(double score){
		this.score = score;
	}

	public double getScore(){
		return score;
	}

	public void setWord(String word){
		this.word = word;
	}

	public String getWord(){
		return word;
	}

	@Override
 	public String toString(){
		return 
			"WhoItem{" + 
			"score = '" + score + '\'' + 
			",word = '" + word + '\'' + 
			"}";
		}
}