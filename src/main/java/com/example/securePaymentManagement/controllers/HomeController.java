package com.example.securePaymentManagement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping("/")
	
	@GetMapping({"", "/"})
	public String index() {
		return "index";
	}
	
}
