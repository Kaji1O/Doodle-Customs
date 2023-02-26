package com.FYP.AdminController;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.FYP.Entities.Custom;
import com.FYP.Entities.LimitedEdition;
import com.FYP.Entities.Orders;
import com.FYP.Entities.Products;
import com.FYP.Entities.User;
import com.FYP.GlobelData.GlobalData;
import com.FYP.repository.UserRepository;
import com.FYP.services.CategoryService;
import com.FYP.services.CustomServices;
import com.FYP.services.EmailServices;
import com.FYP.services.LimitedEditionServices;
import com.FYP.services.Orderservices;
import com.FYP.services.ProductServices;

import net.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Optional;

@Controller
public class HomeController {
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductServices productServices;
	@Autowired
	LimitedEditionServices editionServices;
	@Autowired
	private CustomServices customServices;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private Orderservices orderservices;
	@Autowired
	private EmailServices emailServices;
	
	@GetMapping("/design")
	public String AllProducts(Model model, Principal principal) {
		  model.addAttribute("cartCount", GlobalData.cart.size()+GlobalData.carts.size()+GlobalData.customCart.size());
		  try {
			  
			  String username = principal.getName();
				User user = userRepository.findUserByEmail(username);
				
				if(user==null) {
					model.addAttribute("username","");
				}else {
					model.addAttribute("username",user.getFirstName());
				}
				
			  model.addAttribute("categories", categoryService.getCategory());
			  model.addAttribute("products", productServices.getAllProducts());
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "design";
	}
	
	@GetMapping("/design/category/{id}")
	public String getProductById(@PathVariable int id, Model model) {
		model.addAttribute("cartCount", GlobalData.cart.size()+GlobalData.carts.size());
		try {
			model.addAttribute("categories", categoryService.getCategory());
			model.addAttribute("products", productServices.getAllProductsByCategoryId(id));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return "design";
	}
	
	@RequestMapping("/design/details/{pId}")
	public String viewDetail(@PathVariable("pId") Integer pId, Model model, Principal principal) {
		
		try {
			
			String username = principal.getName();
			User user = userRepository.findUserByEmail(username);
			
			if(user==null) {
				model.addAttribute("username","");
			}else {
				model.addAttribute("username",user.getFirstName());
			}
			
			java.util.Optional<Products> optional = productServices.getProductById(pId);
			Products products = optional.get();
			model.addAttribute("products",products);
			model.addAttribute("cartCount", GlobalData.cart.size()+GlobalData.carts.size()+GlobalData.customCart.size());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		  
		return "details";
	}
	
	
	
	
	
	
	
	//limitedEdition Handlers
	@GetMapping("/limitedEdition")
	public String viewLimitedEdition(Model model, Principal principal) {
		model.addAttribute("cartCount", GlobalData.cart.size()+GlobalData.carts.size()+GlobalData.customCart.size());
		try {
			
			String username = principal.getName();
			User user = userRepository.findUserByEmail(username);
			
			if(user==null) {
				model.addAttribute("username","");
			}else {
				model.addAttribute("username",user.getFirstName());
			}
			
			model.addAttribute("limitedEditon",editionServices.getAll());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return "limitedEdition";
	}
	
	@RequestMapping("/limitedEditionDetails/{eId}")
	public String limitedEdition(@PathVariable long eId, Model model, Principal principal) {
		model.addAttribute("cartCount", GlobalData.cart.size()+GlobalData.carts.size()+GlobalData.customCart.size());
		try {
			
			String username = principal.getName();
			User user = userRepository.findUserByEmail(username);
			
			if(user==null) {
				model.addAttribute("username","");
			}else {
				model.addAttribute("username",user.getFirstName());
			}
			
			java.util.Optional<LimitedEdition> findById = editionServices.findById(eId);
			LimitedEdition limitedEdition = findById.get();
			model.addAttribute("limitedEdition", limitedEdition);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "limitedEditionDetails";
	}
	
	
	
	
	
	@GetMapping("/home")
	public String homePage(Model model,Principal principal) {
		model.addAttribute("cartCount", GlobalData.cart.size()+GlobalData.carts.size()+GlobalData.customCart.size());
		try {
			String username = principal.getName();
			User user = userRepository.findUserByEmail(username);
			
			if(user==null) {
				model.addAttribute("username","");
			}else {
				model.addAttribute("username",user.getFirstName());
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
		return "home";
	}
	
	@GetMapping("/founder")
	public String founderPage(Model model, Principal principal)
	{
		try {
			String username = principal.getName();
			User user = userRepository.findUserByEmail(username);
			
			if(user==null) {
				model.addAttribute("username","");
			}else {
				model.addAttribute("username",user.getFirstName());
			}
			
			model.addAttribute("cartCount", GlobalData.cart.size()+GlobalData.carts.size()+GlobalData.customCart.size());
			return "founder";
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "founder";
		}
	}
	
	@GetMapping("/artist")
	public String artistPage(Model model, Principal principal) {
		
		String username = principal.getName();
		User user = userRepository.findUserByEmail(username);
		
		if(user==null) {
			model.addAttribute("username","");
		}else {
			model.addAttribute("username",user.getFirstName());
		}
		
		model.addAttribute("cartCount", GlobalData.cart.size()+GlobalData.carts.size()+GlobalData.customCart.size());
		return "artist";
	}
	
	@GetMapping("/privacypolicy")
	public String privacypolicyPage(Model model, Principal principal) {
		
		String username = principal.getName();
		User user = userRepository.findUserByEmail(username);
		
		if(user==null) {
			model.addAttribute("username","");
		}else {
			model.addAttribute("username",user.getFirstName());
		}
		
		model.addAttribute("cartCount", GlobalData.cart.size()+GlobalData.carts.size()+GlobalData.customCart.size());
		return "privacypolicy";
	}
	
	@GetMapping("/termsnconditions")
	public String termsnconditionsPage(Model model, Principal principal) {
		
		String username = principal.getName();
		User user = userRepository.findUserByEmail(username);
		
		if(user==null) {
			model.addAttribute("username","");
		}else {
			model.addAttribute("username",user.getFirstName());
		}
		
		model.addAttribute("cartCount", GlobalData.cart.size()+GlobalData.carts.size()+GlobalData.customCart.size());
		return "termsnconditions";
	}
	
	@GetMapping("/faq")
	public String faqPage(Model model, Principal principal) {
		
		String username = principal.getName();
		User user = userRepository.findUserByEmail(username);
		
		if(user==null) {
			model.addAttribute("username","");
		}else {
			model.addAttribute("username",user.getFirstName());
		}
		
		model.addAttribute("cartCount", GlobalData.cart.size()+GlobalData.carts.size()+GlobalData.customCart.size());
		return "faq";
	}
	
	@GetMapping("/custom")
	public String customPage(Model model, Principal principal) {
		try {	
		String username = principal.getName();
		User user = userRepository.findUserByEmail(username);
		
		if(user==null) {
			model.addAttribute("username","");
		}else {
			model.addAttribute("username",user.getFirstName());
		}
		
		model.addAttribute("cartCount", GlobalData.cart.size()+GlobalData.carts.size()+GlobalData.customCart.size());
		
			List<Custom> custom = customServices.getAllCustom();
			model.addAttribute("customs",custom);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "custom";
	}
	
	
	//orders handler
	@PostMapping("/orders")
	public String order(@ModelAttribute("orders") Orders orders, Principal principal){
		Orders order = new Orders();
		try {
			String email = principal.getName();
			order.setName(orders.getName());
			order.setAddress(orders.getAddress());
			order.setPhoneNumber(orders.getPhoneNumber());
			order.setStatus("Unpaid");
			order.setOrderDate(LocalDate.now().toString());
			order.setEmail(email);
			double sum = GlobalData.customCart.stream().mapToDouble(Custom::getPrice).sum();
			double sum2 = GlobalData.carts.stream().mapToDouble(LimitedEdition::getPrice).sum();
			double sum1 = GlobalData.cart.stream().mapToDouble(Products::getPrice).sum();
			long total1 = (new Double(sum1)).longValue();
			long total2 = (new Double(sum2)).longValue();
			long total3 = (new Double(sum)).longValue();
			long totalAmount = (total1 + total2+total3);
			order.setTotalAmount(totalAmount);
			for(int i = 0; i<GlobalData.cart.size(); i++) {
				long pId = GlobalData.cart.get(i).getPId();
				int PID =(int) pId;
				order.setProductId(PID);
				order.setPName(GlobalData.cart.get(i).getName());
				order.setImage(GlobalData.cart.get(i).getProductImage());
				double size = GlobalData.cart.get(i).getSize();
				long s = (new Double(size).longValue());
				order.setSize(s);
				
				//Order confirmation mail sending 
				String productName = GlobalData.cart.get(i).getName();
	            String subject = "Order Confirmation";
	            String message = ""
	                           + "<div style='border: 1px solid #e2e2e2; padding: 20px;'>"
	                           + "<h1>"
	                           + "Your product is:"
	                           + "<b>" +productName
	                           + "</n>"
	                           + "</h1>"
	                           + "<p>"
	                           + "Total Amount:"
	                           + "<b>"+sum1
	                           + "</p>"
	                           + "<p>"
	                           + "Your order has been confirmed. For any query Please contact at 9812345678."
	                           + "</P>"
	                           + "</div>";
	            String to = email;
	            System.out.println(to);
	            emailServices.sendEmail(subject, message, to);
	            
	            
			}
			for(int i = 0; i<GlobalData.carts.size(); i++) {
				long eId = GlobalData.carts.get(i).getEId();
				int EID =(int) eId;
				order.setProductId(EID); 
				order.setPName(GlobalData.carts.get(i).getName());
				order.setImage(GlobalData.carts.get(i).getProductImage());
				double size = GlobalData.carts.get(i).getSize();
				long s = (new Double(size).longValue());
				order.setSize(s);
				
				
				//Order confirmation mail sending 
				String productName = GlobalData.carts.get(i).getName();
	            String subject = "Order Confirmation";
	            String message = ""
	                           + "<div style='border: 1px solid #e2e2e2; padding: 20px;'>"
	                           + "<h1>"
	                           + "Your product is:"
	                           + "<b>" +productName
	                           + "</n>"
	                           + "</h1>"
	                           + "<p>"
	                           + "Total Amount:"
	                           + "<b>"+sum2
	                           + "</p>"
	                           + "<p>"
	                           + "Your order has been confirmed. For any query Please contact at 9812345678."
	                           + "</P>"
	                           + "</div>";
	            String to = email;
	            emailServices.sendEmail(subject, message, to);
			}
			for(int i = 0; i<GlobalData.customCart.size(); i++) {
				long dId = GlobalData.customCart.get(i).getDId();
				int DID =(int) dId;
				order.setProductId(DID); 
				order.setPName(GlobalData.customCart.get(i).getName());
				order.setImage(GlobalData.customCart.get(i).getImage());
				double size = GlobalData.customCart.get(i).getSize();
				long s = (new Double(size).longValue());
				order.setSize(s);
				//Order confirmation mail sending
				String productName = GlobalData.customCart.get(i).getName();            
	            String subject = "Order Confirmation";
	            String message = ""
	                           + "<div style='border: 1px solid #e2e2e2; padding: 20px;'>"
	                           + "<h1>"
	                           + "Your product is:"
	                           + "<b>" +productName
	                           + "</n>"
	                           + "</h1>"
	                           + "<p>"
	                           + "Total Amount:"
	                           + "<b>"+sum
	                           + "</p>"
	                           + "<p>"
	                           + "Your order has been confirmed. For any query Please contact at 9812345678."
	                           + "</P>"
	                           + "</div>";
	            String to = email;
	            emailServices.sendEmail(subject, message, to);
			}
			orderservices.save(order);
			GlobalData.cart.clear();
			GlobalData.carts.clear();
			GlobalData.customCart.clear();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "redirect:/cart";
	}
	
	@GetMapping("/khalti")
	public String khaltiRequest(@ModelAttribute("orders") Orders orders, Principal principal) {
		Orders order = new Orders();
		String email = principal.getName();
		User user = userRepository.findUserByEmail(email);
		try {
			
			order.setName(user.getFirstName());
			order.setAddress(orders.getAddress());
			order.setPhoneNumber(user.getPhoneNumber());
			order.setStatus("Paid");
			order.setOrderDate(LocalDate.now().toString());
			order.setEmail(email);
			double sum = GlobalData.customCart.stream().mapToDouble(Custom::getPrice).sum();
			double sum2 = GlobalData.carts.stream().mapToDouble(LimitedEdition::getPrice).sum();
			double sum1 = GlobalData.cart.stream().mapToDouble(Products::getPrice).sum();
			long total1 = (new Double(sum1)).longValue();
			long total2 = (new Double(sum2)).longValue();
			long total3 = (new Double(sum)).longValue();
			long totalAmount = (total1 + total2+ total3);
			order.setTotalAmount(totalAmount);
			for(int i = 0; i<GlobalData.cart.size(); i++) {
				long pId = GlobalData.cart.get(i).getPId();
				int PID =(int) pId;
				order.setProductId(PID);
				order.setPName(GlobalData.cart.get(i).getName());
				order.setImage(GlobalData.cart.get(i).getProductImage());
				double size = GlobalData.cart.get(i).getSize();
				long s = (new Double(size).longValue());
				order.setSize(s);
				
				//order confirmation mail sending
				String productName = GlobalData.cart.get(i).getName();
	            String email2 = orders.getEmail();
	            
	            String subject = "Order Confirmation";
	            String message = ""
	                           + "<div style='border: 1px solid #e2e2e2; padding: 20px;'>"
	                           + "<h1>"
	                           + "Your product is:"
	                           + "<b>" +productName
	                           + "</n>"
	                           + "</h1>"
	                           + "<p>"
	                           + "Total Amount:"
	                           + "<b>"+sum
	                           + "</p>"
	                           + "<p>"
	                           + "Your order has been confirmed. For any query Please contact at 9812345678."
	                           + "</P>"
	                           + "</div>";
	            String to = email2;
	            emailServices.sendEmail(subject, message, to);
			}
			for(int i = 0; i<GlobalData.carts.size(); i++) {
				long eId = GlobalData.carts.get(i).getEId();
				int EID =(int) eId;
				order.setProductId(EID); 
				order.setPName(GlobalData.carts.get(i).getName());
				order.setImage(GlobalData.carts.get(i).getProductImage());
				double size = GlobalData.carts.get(i).getSize();
				long s = (new Double(size).longValue());
				order.setSize(s);
				
				//Order confirmation mail sending 
				String productName = GlobalData.carts.get(i).getName();
	            String email2 = orders.getEmail();
	            
	            String subject = "Order Confirmation";
	            String message = ""
	                           + "<div style='border: 1px solid #e2e2e2; padding: 20px;'>"
	                           + "<h1>"
	                           + "Your product is:"
	                           + "<b>" +productName
	                           + "</n>"
	                           + "</h1>"
	                           + "<p>"
	                           + "Total Amount:"
	                           + "<b>"+sum
	                           + "</p>"
	                           + "<p>"
	                           + "Your order has been confirmed. For any query Please contact at 9812345678."
	                           + "</P>"
	                           + "</div>";
	            String to = email2;
	            emailServices.sendEmail(subject, message, to);
			}
			for(int i = 0; i<GlobalData.customCart.size(); i++) {
				long dId = GlobalData.customCart.get(i).getDId();
				int DID =(int) dId;
				order.setProductId(DID); 
				order.setPName(GlobalData.customCart.get(i).getName());
				order.setImage(GlobalData.customCart.get(i).getImage());
				double size = GlobalData.customCart.get(i).getSize();
				long s = (new Double(size).longValue());
				order.setSize(s);
				
				String productName = GlobalData.customCart.get(i).getName();
	            String email2 = orders.getEmail();
	            
	            //Order confirmation mail sending 
	            String subject = "Order Confirmation";
	            String message = ""
	                           + "<div style='border: 1px solid #e2e2e2; padding: 20px;'>"
	                           + "<h1>"
	                           + "Your product is:"
	                           + "<b>" +productName
	                           + "</n>"
	                           + "</h1>"
	                           + "<p>"
	                           + "Total Amount:"
	                           + "<b>"+sum
	                           + "</p>"
	                           + "<p>"
	                           + "Your order has been confirmed. For any query Please contact at 9812345678."
	                           + "</P>"
	                           + "</div>";
	            String to = email2;
	            emailServices.sendEmail(subject, message, to);
			}
			
			orderservices.save(order);
			GlobalData.cart.clear();
			GlobalData.carts.clear();
			GlobalData.customCart.clear();
			return "redirect:/cart";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "redirect:/cart";
			
		
		
	}
	
	
	
}

