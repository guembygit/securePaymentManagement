package com.example.securePaymentManagement.Models;

import java.util.Date;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

@Entity
@Table(name="users_events")
public class UserEvent {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idUsersEvents;
	
	@Nullable
	private int idUser;
	
	@Nullable
	private int idEventUnique;
	
	@Nullable
	private int months;
	
	@Nullable
	private int statutDeposit;
	
	@Nullable
	private String amountantTransaction;
	
	@Nullable
	private int cashIn;
	
	@Nullable
	private int disburse;
	
	@Nullable
	@Column(columnDefinition = "TEXT")
	private String aboutEvent;
	
	@Nullable
	private Date createdAtEvent;
	
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
	public Date getCreatedAtEvent() {
		return createdAtEvent;
	}
	public void setCreatedAtEvent(Date createdAtEvent) {
		this.createdAtEvent = createdAtEvent;
	}
	public int getStatutDeposit() {
		return statutDeposit;
	}
	public void setStatutDeposit(int statutDeposit) {
		this.statutDeposit = statutDeposit;
	}
	public String getAmountantTransaction() {
		return amountantTransaction;
	}
	public void setAmountantTransaction(String amountantTransaction) {
		this.amountantTransaction = amountantTransaction;
	}
	public int getCashIn() {
		return cashIn;
	}
	public void setCashIn(int cashIn) {
		this.cashIn = cashIn;
	}
	public int getDisburse() {
		return disburse;
	}
	public void setDisburse(int disburse) {
		this.disburse = disburse;
	}
	public String getAboutEvent() {
		return aboutEvent;
	}
	public void setAboutEvent(String aboutEvent) {
		this.aboutEvent = aboutEvent;
	}
	
	
	
	
	
}
