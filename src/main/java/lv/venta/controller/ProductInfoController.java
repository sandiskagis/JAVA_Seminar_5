package lv.venta.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lv.venta.model.Product;
import lv.venta.service.IFilterProductService;

@Controller
@RequestMapping("/product/filter")
public class ProductInfoController {


	@Autowired
	private IFilterProductService filterService;
	
	
	//TODO pameģinam ielikt atsevisķu nosaukumu html- kas tie ir pa produktiem
	//un caur model nosūtīt so nosaukumu
	//izveidojam 4 get kontrolierus prieks filtrācijas funkcijām
	@GetMapping("/price/{threshold}")//localhost:8080/product/filter/price/1.5
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
	@GetMapping("/quantity/{threshold}")//localhost:8080/product/filter/quantity/20
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
	
	@GetMapping("/text/{text}")//localhost:8080/product/filter/text/Sarkans
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
	
	
	@GetMapping("/calculate/total")//localhost:8080/product/filter/calculate/total
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
