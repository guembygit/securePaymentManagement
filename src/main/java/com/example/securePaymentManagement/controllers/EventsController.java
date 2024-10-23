package com.example.securePaymentManagement.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.securePaymentManagement.Dto.EventDto;
import com.example.securePaymentManagement.Models.Event;
import com.example.securePaymentManagement.Repositories.EventRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/events")
public class EventsController {
	
	@Autowired
	private EventRepository repo;
	
	@GetMapping({"", "/"})
	public String index(){
		return "events/index";
	}
	
	@GetMapping("/create")
	public String showCreatePage(Model model){
		EventDto eventDto = new EventDto();
		model.addAttribute("eventDto", eventDto);
		return "events/create";
	}
	
	@PostMapping("/create")
	public String showCreatePages(Model model, 
			@Valid @ModelAttribute EventDto eventDto,
			BindingResult result){
		if(result.hasErrors()) {
			return "events/create";
		}
		
		Event event = new Event();
		event.setLibelleEvent(eventDto.getLibelleEvent());
		event.setMountant(eventDto.getMountant());
		event.setCreatedAt(new Date());
		
		repo.save(event);
		
		return "redirect:/events/";
	}
	
	@GetMapping("/edit")
	public String showEditPage(){
		return "events/edit";
	}
}
