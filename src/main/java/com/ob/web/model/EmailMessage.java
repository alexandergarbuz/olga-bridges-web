package com.ob.web.model;

import java.util.Objects;

public class EmailMessage {

	private String name;
	private String email;
	private String phoneNumber;
	private String message;
	
	public EmailMessage() {
	}
	public EmailMessage(String name, String email, String phoneNumber, String message) {
		super();
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.message = message;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public int hashCode() {
		return Objects.hash(email, message, name, phoneNumber);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmailMessage other = (EmailMessage) obj;
		return Objects.equals(email, other.email) && Objects.equals(message, other.message)
				&& Objects.equals(name, other.name) && Objects.equals(phoneNumber, other.phoneNumber);
	}
	@Override
	public String toString() {
		return "EmailMessage [name=" + name + ", email=" + email + ", phoneNumber=" + phoneNumber + ", message="
				+ message + "]";
	}
}
