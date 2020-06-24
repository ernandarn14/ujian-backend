package com.cimb.ujianspring.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cimb.ujianspring.entity.Movie;

public interface MoviesRepo extends JpaRepository<Movie, Integer> {
	public Optional<Movie> findByMovieName(String movieName);
}
