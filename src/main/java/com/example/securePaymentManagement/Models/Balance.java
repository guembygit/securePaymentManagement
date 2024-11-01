package com.example.securePaymentManagement.Models;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="balances")
public class Balance {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idBalance;
	private String initialBalance;
	private String currentAmount;
	private int operationType;
	private int idUsersEventsBalance;
	private int cancelBalance;
	private int idReferenceUserEventBalance;
	private Date canceledAtBalance;
	private Date createdAtBalance;
	
	
	public int getIdBalance() {
		return idBalance;
	}
	public void setIdBalance(int idBalance) {
		this.idBalance = idBalance;
	}
	public String getInitialBalance() {
		return initialBalance;
	}
	public void setInitialBalance(String initialBalance) {
		this.initialBalance = initialBalance;
	}
	public String getCurrentAmount() {
		return currentAmount;
	}
	public void setCurrentAmount(String currentAmount) {
		this.currentAmount = currentAmount;
	}
	public int getOperationType() {
		return operationType;
	}
	public void setOperationType(int operationType) {
		this.operationType = operationType;
	}
	
	public int getIdUsersEventsBalance() {
		return idUsersEventsBalance;
	}
	public void setIdUsersEventsBalance(int idUsersEventsBalance) {
		this.idUsersEventsBalance = idUsersEventsBalance;
	}
	
	public int getCancelBalance() {
		return cancelBalance;
	}
	public void setCancelBalance(int cancelBalance) {
		this.cancelBalance = cancelBalance;
	}
	public Date getCreatedAtBalance() {
		return createdAtBalance;
	}
	public void setCreatedAtBalance(Date createdAtBalance) {
		this.createdAtBalance = createdAtBalance;
	}
	public int getIdReferenceUserEventBalance() {
		return idReferenceUserEventBalance;
	}
	public void setIdReferenceUserEventBalance(int idReferenceUserEventBalance) {
		this.idReferenceUserEventBalance = idReferenceUserEventBalance;
	}
	public Date getCanceledAtBalance() {
		return canceledAtBalance;
	}
	public void setCanceledAtBalance(Date canceledAtBalance) {
		this.canceledAtBalance = canceledAtBalance;
	}
	
	
	
}
