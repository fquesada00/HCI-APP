package ar.com.edu.itba.hci_app.network.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class RoutineModel {

	@SerializedName("difficulty")
	private String difficulty;

	@SerializedName("creator")
	private CreatorModel creator;

	@SerializedName("dateCreated")
	private Date dateCreated;

	@SerializedName("averageRating")
	private Double averageRating;

	@SerializedName("name")
	private String name;

	@SerializedName("isPublic")
	private boolean isPublic;

	@SerializedName("id")
	private int id;

	@SerializedName("detail")
	private String detail;

	@SerializedName("category")
	private CategoryModel category;

	public String getDifficulty(){
		return difficulty;
	}

	public CreatorModel getCreator(){
		return creator;
	}

	public Date getDateCreated(){
		return dateCreated;
	}

	public Double getAverageRating(){
		return averageRating;
	}

	public String getName(){
		return name;
	}

	public boolean isIsPublic(){
		return isPublic;
	}

	public int getId(){
		return id;
	}

	public String getDetail(){
		return detail;
	}

	public CategoryModel getCategory(){
		return category;
	}
}