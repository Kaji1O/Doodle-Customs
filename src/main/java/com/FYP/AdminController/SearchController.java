package com.FYP.AdminController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.FYP.Entities.Products;
import com.FYP.services.ProductServices;

@Controller
public class SearchController {
	@Autowired
	ProductServices productServices;
	
	@GetMapping("/search/{word}")
	public ResponseEntity<?> searchProduct(@PathVariable("word") String word){
		List<Products> searchList = productServices.search(word);
		return ResponseEntity.ok(searchList);
	}

}
