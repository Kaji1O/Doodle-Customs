package com.FYP.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.FYP.Entities.Products;

public interface ProductRepository extends JpaRepository<Products, Long> {
	List<Products>findAllByCategory_id(int pId);
	
	//search
	public List <Products>findByNameContaining(String name);
	
	
}
