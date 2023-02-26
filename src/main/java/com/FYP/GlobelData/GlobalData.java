package com.FYP.GlobelData;

import java.util.ArrayList;
import java.util.List;

import com.FYP.Entities.Custom;
import com.FYP.Entities.LimitedEdition;
import com.FYP.Entities.Products;

public class GlobalData {
	public static List<Products> cart;
	  static {
	 
		cart = new ArrayList<Products>();
		
	 
	  }
	  
	  public static List<LimitedEdition>carts;
	  static {
		  
		  carts=new ArrayList<LimitedEdition>();
		  
	  }
	  
	  public static List<Custom> customCart;
	  static {
		  customCart = new ArrayList<Custom>();
	  }

}
