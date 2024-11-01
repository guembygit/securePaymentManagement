package com.example.securePaymentManagement.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.securePaymentManagement.Models.Event;
import com.example.securePaymentManagement.Models.UserEvent;

public interface EventRepository extends JpaRepository<Event, Long>{

		@Query(value = "SELECT * FROM users_events AS a JOIN events AS e ON a.id_event_unique = e.id_event "
				+ "JOIN users AS u ON a.id_user = u.id ", nativeQuery = true)
	    List<Event> findByLibelleEvent();
		
		@Query(value = "SELECT a.id_users_events, u.last_name, u.first_name, e.libelle_event, "
	    		+ "a.amountant_transaction, a.months, a.statut_deposit, a.created_at_event, a.cash_in,"
	    		+ " a.disburse, a.id_event_unique, a.id_user, a.about_event,"
	    		+ "a.cancel_users_events, a.canceled_at_user_event, a.id_reference_user_event "
	    		+ "FROM users_events AS a JOIN events AS e ON a.id_event_unique = e.id_event "
				+ "JOIN users AS u ON a.id_user = u.id WHERE a.cash_in=1", nativeQuery = true)
		    List<Object[]> findUsersWithEvents();
		    
	    @Query(value = "SELECT a.id_users_events, u.last_name, u.first_name, e.libelle_event, "
	    		+ "a.amountant_transaction, a.months, a.statut_deposit, a.created_at_event, a.cash_in,"
	    		+ " a.disburse, a.id_event_unique, a.id_user, a.about_event,"
	    		+ "a.cancel_users_events, a.canceled_at_user_event, a.id_reference_user_event "
	    		+ "FROM users_events AS a JOIN events AS e ON a.id_event_unique = e.id_event "
				+ "JOIN users AS u ON a.id_user = u.id WHERE a.disburse=1", nativeQuery = true)
		    List<Object[]> findByDisburse();
		    
		    @Query(value = "SELECT a.id_users_events, u.last_name, u.first_name, u.address, e.libelle_event, "
		    		+ "a.amountant_transaction, a.months, a.statut_deposit, a.invoice_number, a.created_at_event, a.cash_in,"
		    		+ " a.disburse, a.id_event_unique, a.id_user, a.about_event,"
		    		+ "a.cancel_users_events, a.canceled_at_user_event, a.id_reference_user_event "
		    		+ "FROM users_events AS a JOIN events AS e ON a.id_event_unique = e.id_event "
					+ "JOIN users AS u ON a.id_user = u.id WHERE a.id_users_events = :id", nativeQuery = true)
			    List<Object[]> getInvoiceData(@Param("id") int id);
}
