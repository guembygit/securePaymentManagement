package com.example.securePaymentManagement.controllers;

import java.util.Date;
import java.util.List;
import java.util.Random;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.securePaymentManagement.Dto.EventDto;
import com.example.securePaymentManagement.Dto.UserEventDto;
import com.example.securePaymentManagement.Models.Balance;
import com.example.securePaymentManagement.Models.Event;
import com.example.securePaymentManagement.Models.Invoice;
import com.example.securePaymentManagement.Models.User;
import com.example.securePaymentManagement.Models.UserEvent;
import com.example.securePaymentManagement.Repositories.BalanceRepository;
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
	@Autowired
	private BalanceRepository balance_repo;

	@GetMapping({"", "/"})
	public String index(Model model){
		List<Object[]> usersEvents = event_repo.findUsersWithEvents();
		model.addAttribute("usersEvents", usersEvents);
		model.addAttribute("balance", String.valueOf(balance_repo.findByInitialBalance()));
		return "users_events/index";
	}
	
	@GetMapping("/outings")
	public String outings(Model model){
		List<Object[]> usersEvents = event_repo.findByDisburse();
		model.addAttribute("usersEvents", usersEvents);
		model.addAttribute("balance", String.valueOf(balance_repo.findByInitialBalance()));
		
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
	public String addUserEvent(
			@RequestParam int idUser,
			@RequestParam int idEventUnique,
			@RequestParam int statutDeposit,
			@RequestParam String amountantTransaction,
			@ModelAttribute UserEvent userEvent ){
		
		Random random = new Random();
        int randomNumber = random.nextInt(1000000);
        String invoiceNumber = "LF-" + String.format("%06d", randomNumber);
        
		Balance balance = new Balance();
		
		int currentStatutDeposit = 0;
		if(statutDeposit == 0) {
			currentStatutDeposit = 0;
		}else if(statutDeposit == 1) {
			currentStatutDeposit = 1;
		}
		
		userEvent.setCreatedAtEvent(new Date());
		userEvent.setCashIn(1);
		userEvent.setStatutDeposit(currentStatutDeposit);
		userEvent.setInvoiceNumber(invoiceNumber);
		repo.save(userEvent);
		
		
		if(balance_repo.count() == 0) {
			balance.setInitialBalance(amountantTransaction);
			balance.setCurrentAmount(amountantTransaction);
			balance.setOperationType(1);
			balance.setIdUsersEventsBalance(userEvent.getIdUsersEvents());
			balance.setCreatedAtBalance(new Date());
			balance_repo.save(balance);
		}else {
			Balance getLastData = balance_repo.findTopByOrderByIdBalanceDesc();
			//Balance currentBalance = balance_repo.findById(Long.parseLong(String.valueOf(getLastData.getIdBalance()))).get();
			Balance currentBalance = new Balance();
			int updateBalance = Integer.parseInt(getLastData.getInitialBalance()) + Integer.parseInt(amountantTransaction);
			
			currentBalance.setInitialBalance(String.valueOf(updateBalance));
			currentBalance.setCurrentAmount(amountantTransaction);
			currentBalance.setOperationType(1);
			currentBalance.setIdUsersEventsBalance(userEvent.getIdUsersEvents());
			currentBalance.setCreatedAtBalance(new Date());
			
			balance_repo.save(currentBalance);
			
		}
		
		
		System.out.println("===========DATA ROW ============== :::"+statutDeposit+"::::=======ROW ================");
		return "redirect:/users_events";
	}
	
	@PostMapping("/create_outings")
	public String addUserEventOutings(
		@RequestParam int idUser,
		@RequestParam int idEventUnique,
		@RequestParam String amountantTransaction,
		@ModelAttribute UserEvent userEvent, Model model ){
		
		Random random = new Random();
        int randomNumber = random.nextInt(1000000);
        String invoiceNumber = "LF-" + String.format("%06d", randomNumber);
        
		Balance balance = new Balance();
		
		if(balance_repo.count() == 0) {
			model.addAttribute("success", false);
		}else {
			
			Balance getLastData = balance_repo.findTopByOrderByIdBalanceDesc();
			//Balance currentBalance = balance_repo.findById(Long.parseLong(String.valueOf(getLastData.getIdBalance()))).get();
			Balance currentBalance = new Balance();
			int updateBalance = Integer.parseInt(getLastData.getInitialBalance()) - Integer.parseInt(amountantTransaction);
			
			if(Integer.parseInt(getLastData.getInitialBalance()) >= Integer.parseInt(amountantTransaction)) {
				
				userEvent.setCreatedAtEvent(new Date());
				userEvent.setDisburse(1);
				userEvent.setInvoiceNumber(invoiceNumber);
				repo.save(userEvent);
				
				currentBalance.setInitialBalance(String.valueOf(updateBalance));
				currentBalance.setCurrentAmount(amountantTransaction);
				currentBalance.setOperationType(0);
				currentBalance.setIdUsersEventsBalance(userEvent.getIdUsersEvents());
				currentBalance.setCreatedAtBalance(new Date());
				
				balance_repo.save(currentBalance);
				
				return "redirect:/users_events/outings";
			}
			
		}
		
		model.addAttribute("success", true);
		return "users_events/create_outings";
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
		
		int currentStatutDeposit = 0;
		if(statutDeposit == 0) {
			currentStatutDeposit = 0;
		}else if(statutDeposit == 1) {
			currentStatutDeposit = 1;
		}
		
		userEvent = repo.findById(id).get();
		userEvent.setMonths(months);
		userEvent.setIdUser(idUser);
		userEvent.setIdEventUnique(idEventUnique);
		userEvent.setStatutDeposit(currentStatutDeposit);
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
	public ResponseEntity<FileSystemResource> generateInvoice(@RequestParam int id) {
        try {
        	String pdfPath = invoiceService.generateInvoicePDF(id);
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
	
	@GetMapping("/cancel_entrances")
	public String cancelEntrances(@RequestParam long id) {
		
		UserEvent currentUserEvent = new UserEvent();
		
		UserEvent userEvent = repo.findById(id).get();
		userEvent.setCancelUsersEvents(1);
		userEvent.setCanceledAtUserEvent(new Date());
		repo.save(userEvent);
		
		Balance balance = balance_repo.findById(id).get();
		balance.setCancelBalance(1);
		balance.setCanceledAtBalance(new Date());
		balance_repo.save(balance);
		
		Balance getLastData = balance_repo.getBalanceByLastId();
		
		//Balance currentBalance = balance_repo.findById(Long.parseLong(String.valueOf(getLastData.getIdBalance()))).get();
		Balance currentBalance = new Balance();
		int updateBalance = Integer.parseInt(getLastData.getInitialBalance()) - Integer.parseInt(balance.getCurrentAmount());
		
		if(Integer.parseInt(getLastData.getInitialBalance()) >= Integer.parseInt(balance.getCurrentAmount())) {
			
			Random random = new Random();
	        int randomNumber = random.nextInt(1000000);
	        String invoiceNumber = "LF-" + String.format("%06d", randomNumber);
	        
			currentUserEvent.setCreatedAtEvent(new Date());
			currentUserEvent.setDisburse(1);
			currentUserEvent.setAmountantTransaction(balance.getCurrentAmount());
			currentUserEvent.setIdEventUnique(userEvent.getIdEventUnique());
			currentUserEvent.setIdUser(userEvent.getIdUser());
			currentUserEvent.setMonths(userEvent.getMonths());
			currentUserEvent.setStatutDeposit(userEvent.getStatutDeposit());
			currentUserEvent.setIdReferenceUserEvent(userEvent.getIdUsersEvents());
			currentUserEvent.setInvoiceNumber(invoiceNumber);
			repo.save(currentUserEvent);
			
			currentBalance.setInitialBalance(String.valueOf(updateBalance));
			currentBalance.setCurrentAmount(balance.getCurrentAmount());
			currentBalance.setOperationType(0);
			currentBalance.setIdUsersEventsBalance(userEvent.getIdUsersEvents());
			currentBalance.setCreatedAtBalance(new Date());
			currentBalance.setIdReferenceUserEventBalance(userEvent.getIdUsersEvents());
			
			balance_repo.save(currentBalance);
		}
		
		return "redirect:/users_events";
	}
	
	@GetMapping("/cancel_outings")
	public String cancelOutings(@RequestParam long id) {
		
		UserEvent currentUserEvent = new UserEvent();
		
		UserEvent userEvent = repo.findById(id).get();
		userEvent.setCancelUsersEvents(1);
		userEvent.setCanceledAtUserEvent(new Date());
		repo.save(userEvent);
		
		Balance balance = balance_repo.findById(id).get();
		balance.setCancelBalance(1);
		balance.setCanceledAtBalance(new Date());
		balance_repo.save(balance);
		
		Balance getLastData = balance_repo.getBalanceByLastId();
		
		//Balance currentBalance = balance_repo.findById(Long.parseLong(String.valueOf(getLastData.getIdBalance()))).get();
		Balance currentBalance = new Balance();
		int updateBalance = Integer.parseInt(getLastData.getInitialBalance()) + Integer.parseInt(balance.getCurrentAmount());
		
		if(Integer.parseInt(getLastData.getInitialBalance()) >= 0) {
			
			Random random = new Random();
	        int randomNumber = random.nextInt(1000000);
	        String invoiceNumber = "LF-" + String.format("%06d", randomNumber);
	        
			currentUserEvent.setCreatedAtEvent(new Date());
			currentUserEvent.setCashIn(1);
			currentUserEvent.setAmountantTransaction(balance.getCurrentAmount());
			currentUserEvent.setIdEventUnique(userEvent.getIdEventUnique());
			currentUserEvent.setIdUser(userEvent.getIdUser());
			currentUserEvent.setMonths(userEvent.getMonths());
			currentUserEvent.setStatutDeposit(userEvent.getStatutDeposit());
			currentUserEvent.setIdReferenceUserEvent(userEvent.getIdUsersEvents());
			currentUserEvent.setInvoiceNumber(invoiceNumber);
			
			repo.save(currentUserEvent);
			
			currentBalance.setInitialBalance(String.valueOf(updateBalance));
			currentBalance.setCurrentAmount(balance.getCurrentAmount());
			currentBalance.setOperationType(1);
			currentBalance.setIdUsersEventsBalance(userEvent.getIdUsersEvents());
			currentBalance.setCreatedAtBalance(new Date());
			currentBalance.setIdReferenceUserEventBalance(userEvent.getIdUsersEvents());
			
			balance_repo.save(currentBalance);
		}
		
		return "redirect:/users_events/outings";
	}
	
}
