package com.example.securePaymentManagement.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.securePaymentManagement.Dto.EventDto;
import com.example.securePaymentManagement.Dto.UserEventDto;
import com.example.securePaymentManagement.Models.Event;
import com.example.securePaymentManagement.Models.Invoice;
import com.example.securePaymentManagement.Models.User;
import com.example.securePaymentManagement.Models.UserEvent;
import com.example.securePaymentManagement.Repositories.EventRepository;
import com.example.securePaymentManagement.Repositories.UserEventRepository;
import com.example.securePaymentManagement.Repositories.UserRepository;
import com.example.securePaymentManagement.services.InvoiceService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/users_events")
public class UsersEventsController {
	
	@Autowired
	private UserEventRepository repo;
	@Autowired
	private EventRepository event_repo;
	@Autowired
	private UserRepository user_repo;
	@Autowired
    private InvoiceService invoiceService;

	@GetMapping({"", "/"})
	public String index(Model model){
		List<Object[]> usersEvents = event_repo.findUsersWithEvents();
		model.addAttribute("usersEvents", usersEvents);
		return "users_events/index";
	}
	
	@GetMapping("/outings")
	public String outings(Model model){
		List<Object[]> usersEvents = event_repo.findByDisburse();
		model.addAttribute("usersEvents", usersEvents);
		return "users_events/outings";
	}
	
	@GetMapping("/create")
	public String showCreatePage(Model model){
		UserEventDto userEventDto = new UserEventDto();
		UserEvent userEvent = new UserEvent();
		List<Event> events = event_repo.findAll(Sort.by(Sort.Direction.DESC, "idEvent"));
		
		List<User> users = user_repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
		model.addAttribute("events", events);
		model.addAttribute("userEventDto", userEvent);
		model.addAttribute("users", users);
		
		return "users_events/create";
	}
	
	@GetMapping("/create_outings")
	public String showCreateOutingsPage(Model model){
		UserEventDto userEventDto = new UserEventDto();
		UserEvent userEvent = new UserEvent();
		List<Event> events = event_repo.findAll(Sort.by(Sort.Direction.DESC, "idEvent"));
		
		List<User> users = user_repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
		model.addAttribute("events", events);
		model.addAttribute("userEventDto", userEvent);
		model.addAttribute("users", users);
		
		return "users_events/create_outings";
	}
	
	@PostMapping("/create")
	public String addUserEvent(@RequestParam int statutDeposit,
			@ModelAttribute UserEvent userEvent ){
		
		userEvent.setCreatedAtEvent(new Date());
		userEvent.setCashIn(1);
		userEvent.setStatutDeposit(statutDeposit);
		repo.save(userEvent);
		System.out.println(userEvent);
		return "redirect:/users_events";
	}
	
	@PostMapping("/create_outings")
	public String addUserEventOutings(@ModelAttribute UserEvent userEvent ){
		
		userEvent.setCreatedAtEvent(new Date());
		userEvent.setDisburse(1);
		repo.save(userEvent);
		System.out.println(userEvent);
		return "redirect:/users_events/outings";
	}
	
	@GetMapping("/edit")
	public String editshowEditPage(@RequestParam long id, Model model) {
		UserEvent userEvent = repo.findById(id).get();
		List<Event> events = event_repo.findAll(Sort.by(Sort.Direction.DESC, "idEvent"));
		
		List<User> users = user_repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
		model.addAttribute("events", events);
		model.addAttribute("users", users);
		model.addAttribute("userEvent", userEvent);
		return "users_events/edit";
	}
	
	@GetMapping("/edit_outings")
	public String editshowEditOutingsPage(@RequestParam long id, Model model) {
		UserEvent userEvent = repo.findById(id).get();
		List<Event> events = event_repo.findAll(Sort.by(Sort.Direction.DESC, "idEvent"));
		
		List<User> users = user_repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
		model.addAttribute("events", events);
		model.addAttribute("users", users);
		model.addAttribute("userEvent", userEvent);
		return "users_events/edit_outings";
	}
	
	@PostMapping("/edit")
	public String editUserEvent(@RequestParam long id, 
			@RequestParam int months,
			@RequestParam int idUser,
			@RequestParam int idEventUnique,
			@RequestParam int statutDeposit,
			@RequestParam String amountantTransaction,
			@Valid @ModelAttribute UserEvent userEvent,
			BindingResult result) {
		userEvent = repo.findById(id).get();
		userEvent.setMonths(months);
		userEvent.setIdUser(idUser);
		userEvent.setIdEventUnique(idEventUnique);
		userEvent.setStatutDeposit(statutDeposit);
		userEvent.setAmountantTransaction(amountantTransaction);
		repo.save(userEvent);
		return "redirect:/users_events";
	}
	
	@PostMapping("/edit_outings")
	public String editUserEventOutings(@RequestParam long id, 
			@RequestParam int months,
			@RequestParam int idUser,
			@RequestParam int idEventUnique,
			@RequestParam String amountantTransaction,
			@Valid @ModelAttribute UserEvent userEvent,
			BindingResult result) {
		userEvent = repo.findById(id).get();
		userEvent.setMonths(months);
		userEvent.setIdUser(idUser);
		userEvent.setIdEventUnique(idEventUnique);
		userEvent.setAmountantTransaction(amountantTransaction);
		repo.save(userEvent);
		return "redirect:/users_events/outings";
	}
	
	
	@GetMapping("/invoice")
	public ResponseEntity<FileSystemResource> generateInvoice() {
        try {
        	String pdfPath = invoiceService.generateInvoicePDF();
            FileSystemResource resource = new FileSystemResource(pdfPath);
            
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice_.pdf");
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
            
            //return ResponseEntity.ok("Facture générée avec succès!");
        } catch (Exception e) {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);        }
    }
	
}
