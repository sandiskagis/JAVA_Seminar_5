package lv.venta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OtherController {
	
	@GetMapping("/error")//localhost:8080/error
	public String getError() {
		return "error-page";
	}
	
	
	@GetMapping("/access-denied")//localhost:8080/access-denied
	public String getAccesDenied(Model model ) {
		model.addAttribute("errormsg", "You don't have access to this page!!!");
		return "error-page";
	}
	
	
}