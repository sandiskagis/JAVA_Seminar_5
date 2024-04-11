package lv.venta.controller;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import lv.venta.model.Product;
import lv.venta.service.ICRUDProductService;
import lv.venta.service.IFilterProductService;

@Controller
public class FirstController {
	
	
	/*
	
	private Product tempProduct1 = new Product("Abols", "Sarkans", 0.99f, 5);
	private Product tempProduct2 = new Product("Zemene", "Salda", 1.99f, 50);
	private Product tempProduct3 = new Product("Burkans", "Oranžš", 0.39f, 500);
		
	ArrayList<Product> allProducts = new ArrayList<>(
			Arrays.asList(tempProduct1, tempProduct2, tempProduct3));
	
	*/
	
	
	
	@Autowired
	private ICRUDProductService crudService;
	
	@Autowired
	private IFilterProductService filterService;
	
	
	
	

	@GetMapping("/hello")//localhost:8080/hello
	public String getHello() {
		System.out.println("First Controller!!!");
		return "hello-page"; //tiek parādīta hello-page.html lapa
		
	}
	
	@GetMapping("/hello/msg")//localhost:8080/hello/msg
	public String getHelloMsg(Model model) {
		System.out.println("Msg controller is called");
		model.addAttribute("mydata", "Ziņa no JAVA Spring!!!!");
		return "hello-msg-page";//tiek parādīta hello-msg-page.html lapa
	}
	
	@GetMapping("/product/test")//localhost:8080/product/test
	public String getProductTest(Model model) {
		try {
		model.addAttribute("mydata", crudService.retrieveById(1));
		return "product-one-show-page";//tiek parādīta product-one-show-page.html lapa
		}
		catch(Exception e) {
			model.addAttribute("errormsg", e.getMessage());
			return "error-page";//tiek paradita error page
		}
	}
	

	@GetMapping("/product/all") //localhost:8080/product/all
	public String getProductAll(Model model) {
		try {
		model.addAttribute("mydata", crudService.retrieveAll());
		return "product-all-show-page";//tiek parādīta product-all-show-page.html lapa
		}
		catch(Exception e){
			model.addAttribute("errormsg", e.getMessage());
			return "error-page";//tiek paradita error page
		}
	}
	
	
	
	@GetMapping("/product/one") //localhost:8080/product/one?id=5
	public String getProductOneId(@RequestParam("id")int id, Model model) {
		try {
		model.addAttribute("mydata", crudService.retrieveById(id));
		return "product-one-show-page";//tiek paradita html lapa
		}
		catch (Exception e) {
			model.addAttribute("errormsg", e.getMessage());
			return "error-page";//tiek paradita error page
		}
	}
	
	
	
	
	@GetMapping("/product/all/{id}") //localhost:8080/product/all/2
	public String getProductAllId(@PathVariable("id")int id, Model model) {
		try {
			model.addAttribute("mydata", crudService.retrieveById(id));
			return "product-one-show-page";//tiek paradita html lapa
			}
			catch (Exception e) {
				model.addAttribute("errormsg", e.getMessage());
				return "error-page";//tiek paradita error page
			}
	}
	
	
	
	
	
	
	
	
}