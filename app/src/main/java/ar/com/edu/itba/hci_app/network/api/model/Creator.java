package ar.com.edu.itba.hci_app.network.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Creator{

	@SerializedName("dateLastActive")
	private long dateLastActive;

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

	public long getDateLastActive(){
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