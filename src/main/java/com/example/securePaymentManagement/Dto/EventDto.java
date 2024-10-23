package com.example.securePaymentManagement.Dto;

import java.util.Date;

import jakarta.validation.constraints.NotEmpty;

public class EventDto {
	
	@NotEmpty(message="The title is required")
	private String libelleEvent;
	
	@NotEmpty(message="The mountant is required")
	private String mountant;

	public String getLibelleEvent() {
		return libelleEvent;
	}

	public void setLibelleEvent(String libelleEvent) {
		this.libelleEvent = libelleEvent;
	}

	public String getMountant() {
		return mountant;
	}

	public void setMountant(String mountant) {
		this.mountant = mountant;
	}
	
	
}
