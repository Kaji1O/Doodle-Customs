package com.FYP.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FYP.Entities.Orders;
import com.FYP.repository.OrderRepository;

@Service
public class Orderservices {
	@Autowired
	OrderRepository orderRepository;
	
	//save orders to database.
	public void save(Orders orders) {
		orderRepository.save(orders);
	}
	
	public List<Orders> getAllOrders(){
		return orderRepository.findAll();
	}

}
