package com.example.securePaymentManagement.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securePaymentManagement.Models.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

}
