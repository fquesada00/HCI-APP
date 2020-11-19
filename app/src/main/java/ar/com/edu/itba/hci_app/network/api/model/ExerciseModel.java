package ar.com.edu.itba.hci_app.network.api.model;

import com.google.gson.annotations.SerializedName;

public class ExerciseModel {

	@SerializedName("duration")
	private int duration;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("detail")
	private String detail;

	@SerializedName("type")
	private String type;

	@SerializedName("repetitions")
	private int repetitions;

	@SerializedName("order")
	private int order;

	public int getDuration(){
		return duration;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public String getDetail(){
		return detail;
	}

	public String getType(){
		return type;
	}

	public int getRepetitions(){
		return repetitions;
	}

	public int getOrder(){
		return order;
	}
}