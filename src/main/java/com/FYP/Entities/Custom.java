package com.FYP.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="Custom")
public class Custom {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long dId;
	private String name;
	private long price;
	private long size;
	private String duration;
	private String image;
}
