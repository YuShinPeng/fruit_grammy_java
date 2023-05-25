package com.example.fruit_grammy_java.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dishlist")
public class DishList {

	// µæ¦W
	@Id
	@Column(name = "name")
	private String name;

	// ­¹§÷
	@Column(name = "needed")
	private String needed;

	// °µªk
	@Column(name = "cooking")
	private String cooking;

	public DishList() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNeeded() {
		return needed;
	}

	public void setNeeded(String needed) {
		this.needed = needed;
	}

	public String getCooking() {
		return cooking;
	}

	public void setCooking(String cooking) {
		this.cooking = cooking;
	}

}
