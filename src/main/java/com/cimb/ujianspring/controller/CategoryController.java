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
@RequestMapping("/categories")
public class CategoryController {
	@Autowired
	private CategoriesRepo categoriesRepo;
	
	@Autowired
	private MoviesRepo moviesRepo;
	
	@GetMapping
	public Iterable<Category> getAllCategory(){
		return categoriesRepo.findAll();
	}
	
	@GetMapping("/{categoryId}")
	public Optional<Category> getCategoryById(@PathVariable int categoryId){
		return categoriesRepo.findById(categoryId);
	}
	
	@PostMapping
	public Category addCategory(@RequestBody Category category) {
		category.setId(0);
		return categoriesRepo.save(category);
	}
	
	@PutMapping
	public Category updateCategory(@RequestBody Category category) {
		Category findCategory = categoriesRepo.findById(category.getId()).get();
		 if (findCategory == null)
			 throw new RuntimeException("Category Not Found");
		
		return categoriesRepo.save(category);
	}
	
	@DeleteMapping("/{categoryId}")
	public void deleteCategory(@PathVariable int categoryId) {
		Category findCategory = categoriesRepo.findById(categoryId).get();
		 if (findCategory == null)
			 throw new RuntimeException("Category Not Found");
		
		findCategory.getMovie().forEach(movie -> {
			List<Category> movieCategory = movie.getCategory();
			movieCategory.remove(findCategory);
			moviesRepo.save(movie);
		});
		findCategory.setMovie(null);
		categoriesRepo.deleteById(categoryId);
	}
	
	@GetMapping("/{categoryId}/movies")
	public List<Movie> getMovieCategory(@PathVariable int categoryId){
		Category findCategory = categoriesRepo.findById(categoryId).get();
		 if (findCategory == null)
			 throw new RuntimeException("Category Not Found");
		return findCategory.getMovie();
	}

}
