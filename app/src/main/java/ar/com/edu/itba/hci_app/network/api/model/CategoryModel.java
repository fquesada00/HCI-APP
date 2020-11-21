package ar.com.edu.itba.hci_app.network.api.model;

import com.google.gson.annotations.SerializedName;

public class CategoryModel {
	public CategoryModel(String name, int id, String detail) {
		this.name = name;
		this.id = id;
		this.detail = detail;
	}

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