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

//TODO izveidot divas jaunas kontrolieru klases
//un visus, ka saistīts ar CRUD kontrolieru funkcijām, ielikt vienā klase
//un viu, aks saistīts ar Filter kontrolieru funkcija, ieliekt otrajā klasē

@Controller
public class FirstController {



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

	/*
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
*/
	
	
	
	@GetMapping("/error")//localhost:8080/error
	public String getError() {
		return "error-page";
	}
	

	
	
}