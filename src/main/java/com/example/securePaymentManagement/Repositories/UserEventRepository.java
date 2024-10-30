package com.example.securePaymentManagement.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.securePaymentManagement.Models.Event;
import com.example.securePaymentManagement.Models.User;
import com.example.securePaymentManagement.Models.UserEvent;

public interface UserEventRepository extends JpaRepository<UserEvent, Long> {
	
}
