package com.FYP.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.FYP.Entities.Category;
import com.FYP.Entities.Products;
import com.FYP.repository.ProductRepository;

@Service
public class ProductServices {
	@Autowired
	private ProductRepository productRepository;
	
	
	
	//adding product
	public void addProduct(Products products) {
		productRepository.save(products);
	}
	
	//
	public List<Products> getAllProducts(){
		return productRepository.findAll();
	}
	
	public Page<Products>getPage(int pNumber){
		Pageable pageable = PageRequest.of(pNumber-1, 5);
		return productRepository.findAll(pageable);
	}
	
	public void removeProductById(long pId) {
		productRepository.deleteById(pId);
	}
	
	public Optional<Products> getProductById(long pId){
		return productRepository.findById(pId);
	}
	public List<Products> getAllProductsByCategoryId(int pId){
		return productRepository.findAllByCategory_id(pId);
	}
	
	//search
	public List<Products> search(String name){
		return productRepository.findByNameContaining(name);
	}
}
