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
	public String index(Model model){
		List<Event> events = repo.findAll(Sort.by(Sort.Direction.DESC, "idEvent"));
		model.addAttribute("events", events);
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
	public String showEditPage(Model model, 
			@RequestParam long id){
		Event event = repo.findById(id).get();
		
		EventDto eventDto = new EventDto();
		eventDto.setLibelleEvent(event.getLibelleEvent());
		eventDto.setMountant(event.getMountant());
		
		model.addAttribute("eventDto", eventDto);
		
		return "events/edit";
	}
	
	@PostMapping("/edit")
	public String editEvent(Model model, 
			@RequestParam long id,
			@Valid @ModelAttribute EventDto eventDto, 
			BindingResult result) {
		
		if(result.hasErrors()) {
			return "events/edit";
		}
		
		Event event = repo.findById(id).get();
		event.setLibelleEvent(eventDto.getLibelleEvent());
		event.setMountant(eventDto.getMountant());
		
		repo.save(event);
		return "redirect:/events";
	}
	
	@GetMapping("/delete")
	public String deleteEvent(@RequestParam long id) {
		Event event = repo.findById(id).get();
		repo.delete(event);
		return "redirect:/events";
	}
}
