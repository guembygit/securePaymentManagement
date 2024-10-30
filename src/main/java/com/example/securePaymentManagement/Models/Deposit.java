package com.example.securePaymentManagement.Models;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="deposit")

public class Deposit {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idUsersEventsDeposit;
	private int idUserDeposit;
	private int idEventUniqueDeposit;
	private int monthsDeposit;
	private int depositHistory;
	private double depositMountant;
	private Date createdAtDeposit;
	public int getIdUsersEventsDeposit() {
		return idUsersEventsDeposit;
	}
	public void setIdUsersEventsDeposit(int idUsersEventsDeposit) {
		this.idUsersEventsDeposit = idUsersEventsDeposit;
	}
	public int getIdUserDeposit() {
		return idUserDeposit;
	}
	public void setIdUserDeposit(int idUserDeposit) {
		this.idUserDeposit = idUserDeposit;
	}
	public int getIdEventUniqueDeposit() {
		return idEventUniqueDeposit;
	}
	public void setIdEventUniqueDeposit(int idEventUniqueDeposit) {
		this.idEventUniqueDeposit = idEventUniqueDeposit;
	}
	public int getMonthsDeposit() {
		return monthsDeposit;
	}
	public void setMonthsDeposit(int monthsDeposit) {
		this.monthsDeposit = monthsDeposit;
	}
	public int getDepositHistory() {
		return depositHistory;
	}
	public void setDepositHistory(int depositHistory) {
		this.depositHistory = depositHistory;
	}
	public double getDepositMountant() {
		return depositMountant;
	}
	public void setDepositMountant(double depositMountant) {
		this.depositMountant = depositMountant;
	}
	public Date getCreatedAtDeposit() {
		return createdAtDeposit;
	}
	public void setCreatedAtDeposit(Date createdAtDeposit) {
		this.createdAtDeposit = createdAtDeposit;
	}
	
	
}
