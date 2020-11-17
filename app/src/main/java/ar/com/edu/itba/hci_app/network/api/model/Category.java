package ar.com.edu.itba.hci_app.network.api.model;

import com.google.gson.annotations.SerializedName;

public class Category{

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("detail")
	private String detail;

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public String getDetail(){
		return detail;
	}
}