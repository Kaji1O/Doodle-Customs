package com.FYP.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FYP.Entities.Orders;

public interface OrderRepository extends JpaRepository<Orders, Integer> {

}
