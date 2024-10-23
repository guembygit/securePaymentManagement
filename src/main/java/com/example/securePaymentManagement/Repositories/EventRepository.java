package com.example.securePaymentManagement.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securePaymentManagement.Models.Event;

public interface EventRepository extends JpaRepository<Event, Long>{

}
