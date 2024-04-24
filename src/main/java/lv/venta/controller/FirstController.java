package lv.venta.controller;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lv.venta.model.Product;
import lv.venta.service.ICRUDProductService;
import lv.venta.service.IFilterProductService;

@Controller
public class FirstController {

	@Autowired
	private ICRUDProductService crudService;

	@Autowired
	private IFilterProductService filterService;

	@GetMapping("/hello") // localhost:8080/hello
	public String getHello() {
		System.out.println("First Controller!!!");
		return "hello-page"; // tiek parādīta hello-page.html lapa

	}

	@GetMapping("/hello/msg") // localhost:8080/hello/msg
	public String getHelloMsg(Model model) {
		System.out.println("Msg controller is called");
		model.addAttribute("mydata", "Ziņa no JAVA Spring!!!!");
		return "hello-msg-page";// tiek parādīta hello-msg-page.html lapa
	}

	@GetMapping("/product/test") // localhost:8080/product/test
	public String getProductTest(Model model) {
		try {
			model.addAttribute("mydata", crudService.retrieveById(1));
			return "product-one-show-page";// tiek parādīta product-one-show-page.html lapa
		} catch (Exception e) {
			model.addAttribute("errormsg", e.getMessage());
			return "error-page";// tiek parādīta error-page.html lapa
		}
	}

	@GetMapping("/product/all") // localhost:8080/product/all
	public String getProductAll(Model model) {

		try {
			model.addAttribute("mydata", crudService.retrieveAll());
			model.addAttribute("msg", "All products");
			return "product-all-show-page";// tiek parādīta product-all-show-page.html lapa
		}
		catch (Exception e) {
			model.addAttribute("errormsg", e.getMessage());
			return "error-page";// tiek parādīta error-page.html lapa
		}
	}

	@GetMapping("/product/one") // localhost:8080/product/one?id=2
	public String getProductOneId(@RequestParam("id") int id, Model model) {
		try
		{
			model.addAttribute("mydata", crudService.retrieveById(id));
			return "product-one-show-page";// tiek parādīta product-one-show-page.html lapa
		}
		catch (Exception e) {
			model.addAttribute("errormsg", e.getMessage());
			return "error-page";// tiek parādīta error-page.html lapa
		}

	}

	@GetMapping("/product/all/{id}") // localhost:8080/product/all/2
	public String getProductAllId(@PathVariable("id") int id, Model model) {
		try
		{
			model.addAttribute("mydata", crudService.retrieveById(id));
			return "product-one-show-page";// tiek parādīta product-one-show-page.html lapa
		}
		catch (Exception e) {
			model.addAttribute("errormsg", e.getMessage());
			return "error-page";// tiek parādīta error-page.html lapa
		}
	}
	
	
	@GetMapping("/product/insert") //localhost:8080/product/insert
	public String getProductInsert(Model model) {
		model.addAttribute("product", new Product());//noklusējuma produkts tiks padots uz lapu
		return "product-insert-page";//tiek parādīta product-insert-page.html lapa
	}
	
	//TODO izveidosim html lapu
	
	@PostMapping("/product/insert")
	public String postProductInsert(@Valid Product product, BindingResult result) {//iegūstam aju aizpildītu produktu
		//sajā gadījumā ir validāciju pāŗkāpumi Product objektam
		if(result.hasErrors()) {
			return "product-insert-page";//paliekam šajā pašā lapā
		}
		else
		{
			try {
				crudService.create(product.getTitle(), product.getDescription(), 
					product.getPrice(), product.getQuantity());
				return "redirect:/product/all";//tiks pārvirzīts jeb izsaukts localhost:8080/product/all
			} catch (Exception e) {
			
				return "redirect:/error";//tiks pārvirzīts jeb izsaukt loclahost:8080/error
			}
		}
	
	}
	
	@GetMapping("/error")//localhost:8080/error
	public String getError() {
		return "error-page";
	}
	
	@GetMapping("/product/update")//localhost:8080/product/update?id=2
	public String getProductUpdateById(@RequestParam("id") int id, Model model) {
		
		try {
			Product updatedProduct = crudService.retrieveById(id);
			model.addAttribute("product", updatedProduct);
			model.addAttribute("id", id);
			return "product-update-page";//tiks parādīta product-update-page.html lapa ar atrasto produktu
			
			
		} catch (Exception e) {
			model.addAttribute("errormsg", e.getMessage());
			return "error-page";// tiek parādīta error-page.html lapa
		}
		
	}
	
	
	
	@PostMapping("/product/update")
	public String postProductUpdateById(@Valid Product product, 
			BindingResult result, @RequestParam("id") int id, Model model) {
		
	if(result.hasErrors()) {
		model.addAttribute("id", id);
		return "product-update-page";//tiks parādīta product-update-page.html lapa ar atrasto produktu	
	}
	else
	{
		try
		{
			crudService.updateById(id,product.getTitle(), product.getDescription(), 
				product.getPrice(), product.getQuantity());
			return "redirect:/product/all"; //var arī "redirect:/product/all/" + id; vai arī "redirect:/product/one?id=" + id;
		}
		catch (Exception e) {
			model.addAttribute("errormsg", e.getMessage());
			return "error-page";// tiek parādīta error-page.html lapa
		}
		
	}
		
		
	}
	
	
	@GetMapping("/product/delete/{id}")//localhost:8080/product/delete/2
	public String getProductDeleteById(@PathVariable("id") int id, Model model) {
		
		try {
			crudService.deleteById(id);
			model.addAttribute("mydata", crudService.retrieveAll());
			model.addAttribute("msg", "All products");
			return "product-all-show-page";// tiek parādīta product-all-show-page.html lapa
		} catch (Exception e) {
			model.addAttribute("errormsg", e.getMessage());
			return "error-page";// tiek parādīta error-page.html lapa
		}
		
		
	}
	
		
	//TODO pameģinam ielikt atsevisķu nosaukumu html- kas tie ir pa produktiem
	//un caur model nosūtīt so nosaukumu
	//izveidojam 4 get kontrolierus prieks filtrācijas funkcijām
	@GetMapping("/product/filter/price/{threshold}")//localhost:8080/product/filter/price/1.5
	public String getProductFilterByPrice(@PathVariable("threshold") float threshold,
			Model model) {
		
		try
		{
			ArrayList<Product> filterProducts 
			= filterService.filterByPriceLessThanThreshold(threshold);
			model.addAttribute("mydata", filterProducts);
			model.addAttribute("msg", "Products filtered by price: " + threshold + " eur");
			return "product-all-show-page";// tiek parādīta product-all-show-page.html lapa

		}
		catch (Exception e) {
			model.addAttribute("errormsg", e.getMessage());
			return "error-page";// tiek parādīta error-page.html lapa
		}
	
	}
	
	
	
	//TODO uztaisām kontrolierus visām pārējām filterService funkcijām un notetsējam
	@GetMapping("/product/filter/quantity/{threshold}")//localhost:8080/product/filter/quantity/20
	public String getProductFilterByQuantity(@PathVariable("threshold") int threshold,
			Model model) {
		
		try
		{
			ArrayList<Product> filterProducts 
			= filterService.filterByQuantityLessThanThreshold(threshold);
			model.addAttribute("mydata", filterProducts);
			model.addAttribute("msg", "Products filtered by quantity: " + threshold );
			return "product-all-show-page";// tiek parādīta product-all-show-page.html lapa

		}
		catch (Exception e) {
			model.addAttribute("errormsg", e.getMessage());
			return "error-page";// tiek parādīta error-page.html lapa
		}
	
	}
	
	@GetMapping("/product/filter/text/{text}")//localhost:8080/product/filter/text/Sarkans
	public String getProductFilterByText(@PathVariable("text") String text,
			Model model) {
		
		try
		{
			ArrayList<Product> filterProducts 
			= filterService.filterByTitleOrDescription(text);
			model.addAttribute("mydata", filterProducts);
			model.addAttribute("msg", "Products filtered by text: " + text );
			return "product-all-show-page";// tiek parādīta product-all-show-page.html lapa

		}
		catch (Exception e) {
			model.addAttribute("errormsg", e.getMessage());
			return "error-page";// tiek parādīta error-page.html lapa
		}
	
	}
	
	
	@GetMapping("/product/calculate/total")//localhost:8080/product/calculate/total
	public String getProductCaluclateTotal(Model model) {
		try {
			float result = filterService.calculateProductsTotalValue();
			model.addAttribute("mydata", "Total value of all products is " + result + " eur");
			return "hello-msg-page";// tiek parādīta hello-msg-page.html lapa
		} catch (Exception e) {
			model.addAttribute("errormsg", e.getMessage());
			return "error-page";// tiek parādīta error-page.html lapa
		}
	}
	
	
	
}