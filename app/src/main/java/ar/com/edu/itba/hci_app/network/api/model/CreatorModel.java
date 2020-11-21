package ar.com.edu.itba.hci_app.network.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CreatorModel {
	public CreatorModel(Date dateLastActive, Date dateCreated, String gender, String avatarUrl, int id, String username) {
		this.dateLastActive = dateLastActive;
		this.dateCreated = dateCreated;
		this.gender = gender;
		this.avatarUrl = avatarUrl;
		this.id = id;
		this.username = username;
	}

	@SerializedName("dateLastActive")
	private Date dateLastActive;

	@SerializedName("dateCreated")
	private Date dateCreated;

	@SerializedName("gender")
	private String gender;

	@SerializedName("avatarUrl")
	private String avatarUrl;

	@SerializedName("id")
	private int id;

	@SerializedName("username")
	private String username;

	public Date getDateLastActive(){
		return dateLastActive;
	}

	public Date getDateCreated(){
		return dateCreated;
	}

	public String getGender(){
		return gender;
	}

	public String getAvatarUrl(){
		return avatarUrl;
	}

	public int getId(){
		return id;
	}

	public String getUsername(){
		return username;
	}
}