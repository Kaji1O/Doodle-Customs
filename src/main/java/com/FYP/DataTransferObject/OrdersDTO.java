package com.FYP.DataTransferObject;

import lombok.Data;

@Data
public class OrdersDTO {
	private int orderId;
	private String name;
	private String address;
	private String phoneNumber;
	private String email;
	private String orderDate;
	private int productId;
	private String image;
	private String pName;
	private long size;
	private long totalAmount;
	private String status;

}
