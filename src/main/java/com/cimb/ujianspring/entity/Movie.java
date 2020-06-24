package com.cimb.ujianspring.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;


@Entity
public class Movie {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String movieName;
	
	private int yearReleased;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@JoinTable(name = "movie_cat", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private List<Category> category;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public List<Category> getCategory() {
		return category;
	}

	public void setCategory(List<Category> category) {
		this.category = category;
	}

	public int getYearReleased() {
		return yearReleased;
	}

	public void setYearReleased(int yearReleased) {
		this.yearReleased = yearReleased;
	}
	
	

	
}
