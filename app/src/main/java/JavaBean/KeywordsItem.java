package JavaBean;

import com.google.gson.annotations.SerializedName;

public class KeywordsItem{

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
			"KeywordsItem{" + 
			"score = '" + score + '\'' + 
			",word = '" + word + '\'' + 
			"}";
		}
}