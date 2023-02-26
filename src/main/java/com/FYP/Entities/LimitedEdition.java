package com.FYP.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "LimitedEdition")
public class LimitedEdition {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long eId;
	private String name;
	private double price;
	private double size;
	private String description;
	private String productImage;

}
