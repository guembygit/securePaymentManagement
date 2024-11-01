package com.example.securePaymentManagement.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.securePaymentManagement.Models.Balance;
import com.example.securePaymentManagement.Models.Event;
import com.example.securePaymentManagement.Models.User;
import com.example.securePaymentManagement.Models.UserEvent;

public interface UserEventRepository extends JpaRepository<UserEvent, Long> {
	
	@Query(value = "SELECT * FROM users_events WHERE idUsersEvents = :id AND cancelUsersEvents = 0 ", nativeQuery = true)
    List<Balance> getUserEventByLastId(@Param("id") int id);
}
