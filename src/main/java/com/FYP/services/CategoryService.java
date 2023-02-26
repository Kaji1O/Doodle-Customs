package com.FYP.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.FYP.Entities.Category;
import com.FYP.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository categoryRepository;
	
	public void addCategory(Category category) {
		categoryRepository.save(category);
	}
	public List<Category> getCategory(){
		return categoryRepository.findAll();
		
	}
	
	public Page<Category>getPage(int pageNumber){
		Pageable pageable = PageRequest.of(pageNumber-1, 5);
		return categoryRepository.findAll(pageable);
	}
	
	public void removeCategoryById(int id) {
		categoryRepository.deleteById(id);
	}
	public Optional<Category> getCategoryById(int id){
		Optional<Category> optional = categoryRepository.findById(id);
		return optional;
	}
	
	

}
