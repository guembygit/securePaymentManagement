package com.example.securePaymentManagement.Dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;

public class UserEventDto {
	
	@NotEmpty(message="The user is required")
	private Integer idUser;
	
	@NotEmpty(message="The event is required")
	private int idEventUnique;
	
	@NotEmpty(message="The months is required")
	private int months;
	
	@NotEmpty(message="The amount is required")
	private String amountantTransaction;

	@NotEmpty(message="The about event is required")
	private String aboutEvent;

	

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public int getIdEventUnique() {
		return idEventUnique;
	}

	public void setIdEventUnique(int idEventUnique) {
		this.idEventUnique = idEventUnique;
	}

	public int getMonths() {
		return months;
	}

	public void setMonths(int months) {
		this.months = months;
	}

	public String getAmountantTransaction() {
		return amountantTransaction;
	}

	public void setAmountantTransaction(String amountantTransaction) {
		this.amountantTransaction = amountantTransaction;
	}

	public String getAboutEvent() {
		return aboutEvent;
	}

	public void setAboutEvent(String aboutEvent) {
		this.aboutEvent = aboutEvent;
	}
	
	
}
