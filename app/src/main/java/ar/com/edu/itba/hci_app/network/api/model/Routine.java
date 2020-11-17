package ar.com.edu.itba.hci_app.network.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Routine{

	@SerializedName("difficulty")
	private String difficulty;

	@SerializedName("creator")
	private Creator creator;

	@SerializedName("dateCreated")
	private Date dateCreated;

	@SerializedName("averageRating")
	private int averageRating;

	@SerializedName("name")
	private String name;

	@SerializedName("isPublic")
	private boolean isPublic;

	@SerializedName("id")
	private int id;

	@SerializedName("detail")
	private String detail;

	@SerializedName("category")
	private Category category;

	public String getDifficulty(){
		return difficulty;
	}

	public Creator getCreator(){
		return creator;
	}

	public Date getDateCreated(){
		return dateCreated;
	}

	public int getAverageRating(){
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

	public Category getCategory(){
		return category;
	}
}