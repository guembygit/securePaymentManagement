package com.example.securePaymentManagement.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.securePaymentManagement.Dto.UserDto;
import com.example.securePaymentManagement.Models.User;
import com.example.securePaymentManagement.Repositories.UserRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UsersController {
	
	@Autowired
	private UserRepository repo;
	@GetMapping({"", "/"})
	public String index(Model model) {
		List<User> users = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
		model.addAttribute("users", users);
		return "users/index";
	}
	
	@GetMapping("/create")
	public String showCreatePage(Model model) {
		UserDto userDto = new UserDto();
		model.addAttribute("userDto", userDto);
		return "users/create";
	}
	
	@PostMapping("/create")
	public String createUser(@Valid @ModelAttribute UserDto userDto, 
			BindingResult result) {
		if(result.hasErrors()) {
			return "users/create";
		}
		
		try {
			
		}catch(Exception ex) {
			System.out.println("Exception: "+ ex.getMessage());
		}
		
		User user = new User();
		user.setLastName(userDto.getLastName());
		user.setFirstName(userDto.getFirstName());
		user.setEmail(userDto.getEmail());
		user.setNumber(userDto.getNumber());
		user.setAddress(userDto.getAddress());
		user.setPasswords(userDto.getPasswords());
		user.setCreatedAt(new Date());
		
		repo.save(user);
		
		return "redirect:/users";
	}
	
	@GetMapping("/edit")
	public String showEditpage(Model model, @RequestParam  long id) {
		
		User user = repo.findById(id).get();
		
		UserDto userDto = new UserDto();
		userDto.setLastName(user.getLastName());
		userDto.setFirstName(user.getFirstName());
		userDto.setEmail(user.getEmail());
		userDto.setAddress(user.getAddress());
		userDto.setNumber(user.getNumber());
		userDto.setPasswords(user.getPasswords());
		
		model.addAttribute("userDto", userDto);
		
		return"users/edit";
	}
	
	@PostMapping("/edit")
	public String updateUser(Model model,
			@RequestParam long id,
			@Valid @ModelAttribute UserDto userDto, 
			BindingResult result){
		if(result.hasErrors()) {
			return "users/edit";
		}
		
		User user = repo.findById(id).get();
		
		user.setLastName(userDto.getLastName());
		user.setFirstName(userDto.getFirstName());
		user.setEmail(userDto.getEmail());
		user.setAddress(userDto.getAddress());
		user.setNumber(userDto.getNumber());
		user.setPasswords(userDto.getPasswords());
		
		repo.save(user);
		return "redirect:/users";
	}
	
	@GetMapping("/delete")
	public String deleteUser(@RequestParam long id){
		
		User user = repo.findById(id).get();
		
		repo.delete(user);
		return "redirect:/users";
	}
}
