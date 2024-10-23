package com.example.securePaymentManagement.Dto;

import java.util.Date;

import jakarta.validation.constraints.NotEmpty;

public class UserDto {
	@NotEmpty(message="The lastname is required")
	private String lastName;
	
	@NotEmpty(message="The firstname is required")
	private String firstName;
	
	@NotEmpty(message="The email is required")
	private String email;
	
	@NotEmpty(message="The number is required")
	private String number;
	
	@NotEmpty(message="The address is required")
	private String address;
	
	@NotEmpty(message="The password is required")
	private String passwords;
	
	private Date createdAt;

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPasswords() {
		return passwords;
	}

	public void setPasswords(String passwords) {
		this.passwords = passwords;
	}
	
	
}
