package ar.com.edu.itba.hci_app.network.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Rating{

	@SerializedName("date")
	private Date date;

	@SerializedName("score")
	private int score;

	@SerializedName("routine")
	private Routine routine;

	@SerializedName("review")
	private String review;

	@SerializedName("id")
	private int id;

	public Date getDate(){
		return date;
	}

	public int getScore(){
		return score;
	}

	public Routine getRoutine(){
		return routine;
	}

	public String getReview(){
		return review;
	}

	public int getId(){
		return id;
	}
}