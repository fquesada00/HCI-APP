package ar.com.edu.itba.hci_app.network.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class UserRegistrationModel {

	@SerializedName("password")
	private String password;

	@SerializedName("birthdate")
	private Date birthdate;

	@SerializedName("gender")
	private String gender;

	@SerializedName("phone")
	private String phone;

	@SerializedName("avatarUrl")
	private String avatarUrl;

	@SerializedName("fullName")
	private String fullName;

	@SerializedName("email")
	private String email;

	@SerializedName("username")
	private String username;

	public UserRegistrationModel(String password, Date birthdate, String gender, String phone, String avatarUrl, String fullName, String email, String username) {
		this.password = password;
		this.birthdate = birthdate;
		this.gender = gender;
		this.phone = phone;
		this.avatarUrl = avatarUrl;
		this.fullName = fullName;
		this.email = email;
		this.username = username;
	}

	public UserRegistrationModel(String password, Date birthdate, String gender, String fullName, String email, String username) {
		this.password = password;
		this.birthdate = birthdate;
		this.gender = gender;
		this.fullName = fullName;
		this.email = email;
		this.username = username;
	}

	public String getPassword(){
		return password;
	}

	public Date getBirthdate(){
		return birthdate;
	}

	public String getGender(){
		return gender;
	}

	public String getPhone(){
		return phone;
	}

	public String getAvatarUrl(){
		return avatarUrl;
	}

	public String getFullName(){
		return fullName;
	}

	public String getEmail(){
		return email;
	}

	public String getUsername(){
		return username;
	}
}