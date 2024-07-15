package com.ob.web.model;

import java.util.Objects;

public class Welcome {
	private String firstName;
	private String lastName;
	
	public Welcome() {
	}
	public Welcome(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Override
	public int hashCode() {
		return Objects.hash(firstName, lastName);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Welcome other = (Welcome) obj;
		return Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName);
	}
	@Override
	public String toString() {
		return "Welcome [firstName=" + firstName + ", lastName=" + lastName + "]";
	}

}
