package com.FYP.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FYP.Entities.LimitedEdition;
import com.FYP.repository.LimitedEditionRepository;

@Service
public class LimitedEditionServices {
	@Autowired
	LimitedEditionRepository editionRepository;
	
	public void addLimitedEdition(LimitedEdition limitedEdition) {
		editionRepository.save(limitedEdition);
	}
	
	public List<LimitedEdition> getAll(){
		return editionRepository.findAll();
		
	}
	
	public void deleteLimitedProduct(long eId) {
		editionRepository.deleteById(eId);
	}
	
	public Optional<LimitedEdition>findById(long eId){
		return editionRepository.findById(eId);
		
	}

}
