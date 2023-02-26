package com.FYP.AdminController;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.FYP.Entities.Custom;
import com.FYP.Entities.LimitedEdition;
import com.FYP.Entities.Products;
import com.FYP.Entities.User;
import com.FYP.GlobelData.GlobalData;
import com.FYP.repository.UserRepository;
import com.FYP.services.CustomServices;
import com.FYP.services.LimitedEditionServices;
import com.FYP.services.ProductServices;

@Controller
public class CartController {
	@Autowired
	ProductServices productServices;
	@Autowired
	LimitedEditionServices editionServices;
	@Autowired
	CustomServices customServices;
	@Autowired
	UserRepository userRepository; 
	
	//Products Cart//
	@GetMapping("/addToCart/{pId}")
	public String addToCart(@PathVariable int pId) {
		GlobalData.cart.add(productServices.getProductById(pId).get());
		return "redirect:/design";
	}
	
	@GetMapping("/cart")
	public String cartGet(Model model, Principal principal) {
		
		String username = principal.getName();
		User user = userRepository.findUserByEmail(username);
		
		if(user==null) {
			model.addAttribute("username","");
		}else {
			model.addAttribute("username",user.getFirstName());
		}
		
		int size = GlobalData.cart.size();
		int size2 = GlobalData.carts.size();
		int size3 = GlobalData.customCart.size();
		int cartCount = (size+size2+size3);
		
		try {
			model.addAttribute("cartCount", cartCount);
			model.addAttribute("total",GlobalData.cart.stream().mapToDouble(Products::getPrice).sum());
			model.addAttribute("carts",GlobalData.cart);
			
			
			model.addAttribute("totals",GlobalData.carts.stream().mapToDouble(LimitedEdition::getPrice).sum());
			model.addAttribute("cart",GlobalData.carts);
			
			model.addAttribute("customTotal",GlobalData.customCart.stream().mapToDouble(Custom::getPrice).sum());
			model.addAttribute("customs",GlobalData.customCart);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "cart";
	}
	
	@GetMapping("/Cart/remove/{pId}")
	public String removeCart(@PathVariable("pId") Integer pId) {
		try {
			int index=-1;
			for(int i=0;i<GlobalData.cart.size();i++)
			{
				
				if(GlobalData.cart.get(i).getPId()==pId)
				{
					
					index=i;
				}
			}
			GlobalData.cart.remove(index);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return "redirect:/cart";
	}
	
	
	//Limited Edition Cart//
	@GetMapping("/addToCarts/{eId}")
	public String addToCartLimitedEdition(@PathVariable long eId) {
		try {
			GlobalData.carts.add(editionServices.findById(eId).get());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "redirect:/limitedEdition";
	}
	
	
	  @GetMapping("/Carts/remove/{eId}") 
	  public String remove(@PathVariable Integer eId) { 
		  try {
			  int index2=-1; 
			  for(int i=0;i<GlobalData.carts.size();i++) {
			  
			  if(GlobalData.carts.get(i).getEId()==eId) {
				  
				  index2=i; 
			  	} 
			  } 
			  GlobalData.carts.remove(index2); 
		} catch (Exception e) {
			// TODO: handle exception
		}
	  return "redirect:/cart"; 
	  }
	  
	  
	//Custom Cart//
	  @GetMapping("/addToCartCustom/{dId}")
		public String addToCartCustom(@PathVariable long dId) {
			try {
				GlobalData.customCart.add(customServices.findCustomById(dId).get());
			} catch (Exception e) {
				// TODO: handle exception
			}
			return "redirect:/custom";
		}
	  
	  @GetMapping("/customCarts/remove/{dId}") 
	  public String removeCustom(@PathVariable long dId) { 
	  try {
		  int index3=-1; 
		  for(int i=0;i<GlobalData.customCart.size();i++) {
		  
		  if(GlobalData.customCart.get(i).getDId() == dId) {
			  
			  index3=i; 
		  	} 
		  } 
		  GlobalData.customCart.remove(index3); 
	} catch (Exception e) {
		// TODO: handle exception
	}
	  return "redirect:/cart"; 
	  }
	 

}
