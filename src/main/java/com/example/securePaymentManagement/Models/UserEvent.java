package com.example.securePaymentManagement.Models;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name="users_events")
public class UserEvent {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idUsersEvents;
	private int idUser;
	private int idEvent;
	private int months;
	private Date createdAt;
	public int getIdUsersEvents() {
		return idUsersEvents;
	}
	public void setIdUsersEvents(int idUsersEvents) {
		this.idUsersEvents = idUsersEvents;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public int getIdEvent() {
		return idEvent;
	}
	public void setIdEvent(int idEvent) {
		this.idEvent = idEvent;
	}
	public int getMonths() {
		return months;
	}
	public void setMonths(int months) {
		this.months = months;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	
}
