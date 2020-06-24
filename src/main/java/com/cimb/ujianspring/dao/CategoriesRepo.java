package com.cimb.ujianspring.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cimb.ujianspring.entity.Category;

public interface CategoriesRepo extends JpaRepository<Category, Integer> {

}
