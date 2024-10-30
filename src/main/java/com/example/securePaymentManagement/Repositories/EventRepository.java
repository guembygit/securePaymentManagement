package com.example.securePaymentManagement.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.securePaymentManagement.Models.Event;

public interface EventRepository extends JpaRepository<Event, Long>{

		@Query(value = "SELECT * FROM users_events AS a JOIN events AS e ON a.id_event_unique = e.id_event "
				+ "JOIN users AS u ON a.id_user = u.id ", nativeQuery = true)
	    List<Event> findByLibelleEvent();
		
		@Query(value = "SELECT * FROM users_events AS a JOIN events AS e ON a.id_event_unique = e.id_event "
				+ "JOIN users AS u ON a.id_user = u.id WHERE a.cash_in=1", nativeQuery = true)
		    List<Object[]> findUsersWithEvents();
		    
	    @Query(value = "SELECT * FROM users_events AS a JOIN events AS e ON a.id_event_unique = e.id_event "
				+ "JOIN users AS u ON a.id_user = u.id WHERE a.disburse=1", nativeQuery = true)
		    List<Object[]> findByDisburse();
}
