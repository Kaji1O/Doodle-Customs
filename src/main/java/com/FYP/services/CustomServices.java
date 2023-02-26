package com.FYP.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FYP.Entities.Custom;
import com.FYP.repository.CustomRepository;

@Service
public class CustomServices {
	@Autowired
	private CustomRepository customRepository;
	
	public void saveCustom(Custom custom) {
		customRepository.save(custom);
	}
	
	public List<Custom>getAllCustom(){
		return customRepository.findAll();	
	}
	
	public void removeCustom(long dId) {
		customRepository.deleteById(dId);
	}
	
	public Optional<Custom>findCustomById(long dId){
		return customRepository.findById(dId);
	}
	


}
