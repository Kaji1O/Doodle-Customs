package com.FYP.DataTransferObject;

import lombok.Data;

@Data
public class LimitedEditionDTO {
	private long eId;
	private String name;
	private double price;
	private double size;
	private String description;
	private String productImage;

}
