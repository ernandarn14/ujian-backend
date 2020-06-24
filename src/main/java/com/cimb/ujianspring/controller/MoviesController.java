package com.cimb.ujianspring.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cimb.ujianspring.dao.CategoriesRepo;
import com.cimb.ujianspring.dao.MoviesRepo;
import com.cimb.ujianspring.entity.Category;
import com.cimb.ujianspring.entity.Movie;

@RestController
@RequestMapping("/movies")
public class MoviesController {
	@Autowired
	private MoviesRepo moviesRepo;
	
	@Autowired
	private CategoriesRepo categoriesRepo;

	
	@GetMapping
	public Iterable<Movie> getAllMovie(){
		return moviesRepo.findAll();
	}
	
	@GetMapping("/{movieId}")
	public Optional<Movie> getMovieById(@PathVariable int movieId){
		return moviesRepo.findById(movieId);
	}
	
	@PostMapping
	public Movie addMovie(@RequestBody Movie movie) {
		movie.setId(0);
		return moviesRepo.save(movie);
	}
	
	@PutMapping("/update")
	public Movie updateMovie(@RequestBody Movie movie) {
		 Movie findMovie = moviesRepo.findById(movie.getId()).get();
		 if (findMovie == null)
			 throw new RuntimeException("Movie Not Found");
		 
		return moviesRepo.save(movie);
	}
	
	@DeleteMapping("/{movieId}")
	public void deleteMovie(@PathVariable int movieId) {
		Movie findMovie = moviesRepo.findById(movieId).get();
		 if (findMovie == null)
			 throw new RuntimeException("Movie Not Found");
		 
		findMovie.getCategory().forEach(category -> {
			List<Movie> movCat = category.getMovie();
			movCat.remove(findMovie);
			categoriesRepo.save(category);
		});
		moviesRepo.deleteById(movieId);
	}
	
	@DeleteMapping("/delete/{movieId}/categories/{categoryId}")
	public void deleteCategoryOnMovie(@PathVariable int movieId, @PathVariable int categoryId) {
		Category findCategory = categoriesRepo.findById(categoryId).get();
		 if (findCategory == null)
			 throw new RuntimeException("Category Not Found");
		Movie findMovie = moviesRepo.findById(movieId).get();
		 if (findMovie == null)
			 throw new RuntimeException("Movie Not Found");
		findCategory.getMovie().forEach(movie -> {
			if (movie.getId() == movieId) {
				List<Category> movieCategory = movie.getCategory();
				movieCategory.remove(findCategory);
				moviesRepo.save(findMovie);
			}
		});
	}
	
	
	@PostMapping("/{movieId}/categories/{categoryId}")
    public Movie addCategoryToMovie(@PathVariable int movieId, @PathVariable int categoryId) {
        Movie findMovie = moviesRepo.findById(movieId).get();
        if (findMovie == null)
			 throw new RuntimeException("Movie Not Found");
        Category findCategory = categoriesRepo.findById(categoryId).get();
        if (findCategory == null)
			 throw new RuntimeException("Category Not Found");
        findMovie.getCategory().add(findCategory);
        return moviesRepo.save(findMovie);
    }
}
