package com.FYP.AdminController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.hibernate.query.criteria.internal.predicate.IsEmptyPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.FYP.DataTransferObject.CustomDTO;
import com.FYP.DataTransferObject.LimitedEditionDTO;
import com.FYP.DataTransferObject.ProductDTO;
import com.FYP.Entities.Category;
import com.FYP.Entities.Custom;
import com.FYP.Entities.LimitedEdition;
import com.FYP.Entities.Message;
import com.FYP.Entities.Orders;
import com.FYP.Entities.Products;
import com.FYP.Entities.User;
import com.FYP.repository.ProductRepository;
import com.FYP.repository.UserRepository;
import com.FYP.services.CategoryService;
import com.FYP.services.CustomServices;
import com.FYP.services.LimitedEditionServices;
import com.FYP.services.Orderservices;
import com.FYP.services.ProductServices;

@Controller
public class MainController {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProductServices productServices;
	@Autowired
	private LimitedEditionServices editionServices;
	@Autowired
	private CustomServices customServices;
	@Autowired
	private UserRepository repository;
	@Autowired
	private Orderservices orderservices;

	// Categories handler section.
	@GetMapping("/base")
	public String base() {
		return "adminBase";
	}

	@GetMapping("/admin")
	public String dashboard(Model model) {
		try {
			List<Orders> orders = orderservices.getAllOrders();
			model.addAttribute("orders",orders);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return "dashboard";
	}

	

	@GetMapping("/admin/category")
	public String getAllPages(Model model) {
		return getOnePage(1, model);
	}

	@GetMapping("/admin/category/{pageNumber}")
	public String getOnePage(@PathVariable("pageNumber") Integer pageNumber, Model model) {
		Page<Category> page = categoryService.getPage(pageNumber);
		int totalpages = page.getTotalPages();
		long totalItems = page.getTotalElements();
		List<Category> category = page.getContent();

		model.addAttribute("currentPage", pageNumber);
		model.addAttribute("totalPage", totalpages);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("categories", category);
		return "Category";
	}

	@GetMapping("/admin/category/addc")
	public String addCate(Model model) {
		try {
			model.addAttribute("category", new Category());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "AddCategory";
	}

	@PostMapping("/admin/category/addc")
	public String addCat(@ModelAttribute("category") Category category, HttpSession session) {
		try {
			categoryService.addCategory(category);
			session.setAttribute("message", new Message("Category has been added succesfully.", "success"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "redirect:/admin/category/addc";
	}

	@GetMapping("/admin/category/delete/{id}")
	public String deleteCategory(@PathVariable("id") int id, HttpSession session) {
		try {
			categoryService.removeCategoryById(id);
			session.setAttribute("message", new Message("Category has been deleted succesfully.", "success"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "redirect:/admin/category";
	}

	@GetMapping("/admin/category/update/{id}")
	public String updateCategory(@PathVariable int id, Model model, HttpSession session) {
		Optional<Category> optional = categoryService.getCategoryById(id);
		try {
			if (optional.isPresent()) {
				model.addAttribute("category", optional.get());
				return "AddCategory";
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "404";
	}

	// products handler section.

	@GetMapping("/admin/product")
	public String getproduct(Model model) {
		try {
			model.addAttribute("products", productServices.getAllProducts());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "product";
	}


	@GetMapping("/admin/product/add")
	public String products(Model model) {
		try {
			model.addAttribute("productDTO", new ProductDTO());
			model.addAttribute("categories", categoryService.getCategory());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "AddProduct";
	}

	@PostMapping("/admin/product/add")
	public String AddProduct(@ModelAttribute("productDTO") ProductDTO dto, @RequestParam("pImage") MultipartFile file,
			HttpSession session) throws IOException {
		Products products = new Products();

		try {
			products.setPId(dto.getPId());
			products.setName(dto.getName());
			products.setCategory(categoryService.getCategoryById(dto.getCategory_id()).get());
			products.setPrice(dto.getPrice());
			products.setSize(dto.getSize());
			products.setDescription(dto.getDescription());

			if (!file.isEmpty()) {
				products.setProductImage(file.getOriginalFilename());

				File filePath = new ClassPathResource("static/productImage").getFile();
				Path paths = Paths.get(filePath.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), paths, StandardCopyOption.REPLACE_EXISTING);
	
			}
			productServices.addProduct(products);

			session.setAttribute("message", new Message("Product has been added succesfully.", "success"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return "redirect:/admin/product/add";
	}

	@GetMapping("/admin/product/delete/{pId}")
	public String deleteProduct(@PathVariable("pId") long pId, HttpSession session) {
		try {
			productServices.removeProductById(pId);
			session.setAttribute("message", new Message("Product has been deleted succesfully.", "success"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "redirect:/admin/product";
	}

	@GetMapping("/admin/product/update/{pId}")
	public String updateProduct(@PathVariable("pId") long pId, Model model, HttpSession httpSession) {
		Products products = productServices.getProductById(pId).get();
		ProductDTO dto = new ProductDTO();

		try {
			dto.setPId(products.getPId());
			dto.setName(products.getName());
			dto.setCategory_id(products.getCategory().getId());
			dto.setPrice(products.getPrice());
			dto.setSize(products.getSize());
			dto.setDescription(products.getDescription());
			dto.setProductImage(products.getProductImage());
			model.addAttribute("categories", categoryService.getCategory());
			model.addAttribute("productDTO", dto);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return "AddProduct";
	}

	// LimitedEditon
	@GetMapping("/admin/limitedEdition")
	public String getAddLimitedEdition(Model model) {
		try {
			model.addAttribute("object", new LimitedEdition());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "addLimitedEdition";
	}

	@PostMapping("/admin/limitedEdition/add")
	public String AddLEditionProducts(@ModelAttribute("object") LimitedEditionDTO dto,
			@RequestParam("pImage") MultipartFile file, HttpSession session) throws IOException {
		System.out.println(dto);
		
		  LimitedEdition edition = new LimitedEdition();
		  
		  try {
			  edition.setEId(dto.getEId()); 
			  edition.setName(dto.getName());
			  edition.setPrice(dto.getPrice()); 
			  edition.setSize(dto.getSize());
			  edition.setDescription(dto.getDescription());
			  if(!file.isEmpty()) {
					edition.setProductImage(file.getOriginalFilename());
					
					File filePath = new ClassPathResource("static/productImage").getFile();
					Path paths = Paths.get(filePath.getAbsolutePath()+File.separator+file.getOriginalFilename());
					Files.copy(file.getInputStream(), paths, StandardCopyOption.REPLACE_EXISTING);
					
				}
				 editionServices.addLimitedEdition(edition); 
				 session.setAttribute("message", new Message("Product has been added succesfully", "success"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
			 
		return "redirect:/admin/limitedEdition";
	}
	
	@GetMapping("/admin/viewlimitedEdition")
	public String viewLimitedEdition(Model model) {
		try {
			List<LimitedEdition> all = editionServices.getAll();
			model.addAttribute("limited",all);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "viewLimitedEdition";
	}
	
	@GetMapping("/admin/limitedEdition/delete/{eId}")
	public String deleteEdition(@PathVariable long eId,HttpSession session) {
		try {
			editionServices.deleteLimitedProduct(eId);
			session.setAttribute("message", new Message("Product has been deleted succesfully", "success"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "redirect:/admin/viewlimitedEdition";
	}
	
	
	@GetMapping("/admin/limitedEdition/update/{eId}")
	public String updateEdition(@PathVariable long eId, Model model, HttpSession session) {
		LimitedEdition limitedEdition = editionServices.findById(eId).get();
		LimitedEditionDTO editionDTO = new LimitedEditionDTO();
		
		try {
			editionDTO.setEId(limitedEdition.getEId());
			editionDTO.setName(limitedEdition.getName());
			editionDTO.setPrice(limitedEdition.getPrice());
			editionDTO.setSize(limitedEdition.getSize());
			editionDTO.setDescription(limitedEdition.getDescription());			
			editionDTO.setProductImage(limitedEdition.getProductImage());

			model.addAttribute("object",editionDTO);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "addLimitedEdition";
	}
	
	/* Custom handlers */
	@GetMapping("/admin/custom")
	public String getCustom(Model model) {
		try {
			model.addAttribute("customDTO", new Custom());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "adminCustom";
	}
	
	@PostMapping("/admin/custom/add")
	public String addCustom(@ModelAttribute("customDTO")CustomDTO customDTO, @RequestParam("pImage") MultipartFile file, HttpSession session) throws IOException {
		
		  try {
			  Custom custom = new Custom(); 
				 
				 custom.setDId(customDTO.getDId());
				  custom.setName(customDTO.getName()); 
				  custom.setPrice(customDTO.getPrice());
				  custom.setSize(customDTO.getSize());
				  custom.setDuration(customDTO.getDuration()); 
				  if(!file.isEmpty()) {
				  custom.setImage(file.getOriginalFilename());
				  
				  File filePath = new ClassPathResource("static/productImage").getFile(); 
				  Path paths = Paths.get(filePath.getAbsolutePath()+File.separator+file.getOriginalFilename()); 
				  Files.copy(file.getInputStream(), paths,StandardCopyOption.REPLACE_EXISTING); 
				  } 
				  customServices.saveCustom(custom);
				  
				  session.setAttribute("message", new Message("Product has been added succesfully", "success"));
				 
				
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
			return "redirect:/admin/custom";
			
		
		
	}
	
	@GetMapping("/admin/ViewCustom")
	public String ViewCustom(Model model) {
		try {
			List<Custom> custom = customServices.getAllCustom();
			model.addAttribute("customs", custom);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "ViewCustom";
	}
	
	@GetMapping("/admin/custom/delete/{dId}")
	public String deleteCustom(@PathVariable Integer dId, HttpSession session) {
		try {
			customServices.removeCustom(dId);
			
			 session.setAttribute("message", new Message("Product has been deleted succesfully", "success"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "redirect:/admin/ViewCustom";
	}
	
	@GetMapping("/admin/custom/update/{dId}")
	public String updateCustom(@PathVariable int dId, Model model, HttpSession session) {
		Custom custom = customServices.findCustomById(dId).get();
		CustomDTO customDTO = new CustomDTO();
		try {
			customDTO.setDId(custom.getDId());
			customDTO.setName(custom.getName());
			customDTO.setPrice(custom.getPrice());
			customDTO.setSize(custom.getSize());
			customDTO.setDuration(custom.getDuration());
			customDTO.setImage(custom.getImage());
			model.addAttribute("customDTO",customDTO);
			
			return "adminCustom";
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "adminCustom";
	}
	
	@ModelAttribute
	public void Test(Principal principal, Model model) {
		try {
			String username = principal.getName();
			if(username==null) {
				model.addAttribute("user","Admin");
			}else{
				User user = repository.findUserByEmail(username);
				model.addAttribute("user",user.getFirstName());
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
}

