package com.example.securePaymentManagement.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securePaymentManagement.Models.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
