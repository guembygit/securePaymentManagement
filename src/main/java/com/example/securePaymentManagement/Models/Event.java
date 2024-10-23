package com.example.securePaymentManagement.Models;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name="events")
public class Event {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idEvent;
	private String libelleEvent;
	private String mountant;
	private Date createdAt;
	
	public int getIdEvent() {
		return idEvent;
	}
	public void setIdEvent(int idEvent) {
		this.idEvent = idEvent;
	}
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
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	
}
