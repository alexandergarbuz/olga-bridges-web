package com.ob.web.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmailResponse {
	
	public static final String SUCCESS = "Success";
	public static final String ERROR = "ERROR";
	
	private List<String> errors = new ArrayList<>();
	private String status = SUCCESS;
	
	public EmailResponse() {
	}
	public EmailResponse(List<String> errors, String status) {
		this.errors = errors;
		this.status = status;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public int hashCode() {
		return Objects.hash(errors, status);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmailResponse other = (EmailResponse) obj;
		return Objects.equals(errors, other.errors) && Objects.equals(status, other.status);
	}
	@Override
	public String toString() {
		return "EmailResponse [errors=" + errors + ", status=" + status + "]";
	}
	
}
