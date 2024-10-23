package com.example.securePaymentManagement.Models;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name="invoices")
public class Invoice {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idInVoice;
	private int idUsersEvents;
	private String numInvoice;
	private Date invoiceDate;
	
	public int getIdInVoice() {
		return idInVoice;
	}
	public void setIdInVoice(int idInVoice) {
		this.idInVoice = idInVoice;
	}
	public int getIdUsersEvents() {
		return idUsersEvents;
	}
	public void setIdUsersEvents(int idUsersEvents) {
		this.idUsersEvents = idUsersEvents;
	}
	public String getNumInvoice() {
		return numInvoice;
	}
	public void setNumInvoice(String numInvoice) {
		this.numInvoice = numInvoice;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	
	
}
