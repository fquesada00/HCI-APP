package ar.com.edu.itba.hci_app.domain;

import java.util.Objects;

public class Category {


	private String name;

	private int id;

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

	public Category(String name, int id, String detail) {
		this.name = name;
		this.id = id;
		this.detail = detail;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Category category = (Category) o;
		return id == category.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}