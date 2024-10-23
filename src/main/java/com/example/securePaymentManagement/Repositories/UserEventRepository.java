package com.example.securePaymentManagement.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securePaymentManagement.Models.UserEvent;

public interface UserEventRepository extends JpaRepository<UserEvent, Long> {

}
