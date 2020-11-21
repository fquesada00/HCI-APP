package ar.com.edu.itba.hci_app.network.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class RatingModel {
	public RatingModel(Date date, Double score, RoutineModel routine, String review, int id) {
		this.date = date;
		this.score = score;
		this.routine = routine;
		this.review = review;
		this.id = id;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public void setRoutine(RoutineModel routine) {
		this.routine = routine;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public void setId(int id) {
		this.id = id;
	}

	@SerializedName("date")
	private Date date;

	@SerializedName("score")
	private Double score;

	@SerializedName("routine")
	private RoutineModel routine;

	@SerializedName("review")
	private String review;

	@SerializedName("id")
	private int id;

	public Date getDate(){
		return date;
	}

	public Double getScore(){
		return score;
	}

	public RoutineModel getRoutine(){
		return routine;
	}

	public String getReview(){
		return review;
	}

	public int getId(){
		return id;
	}
}