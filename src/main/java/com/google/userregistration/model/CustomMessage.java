package com.google.userregistration.model;

import java.io.Serializable;

public class CustomMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	private String message;
	private int statusCode;
	private String statusMessage;

	public CustomMessage(String message, int statusCode, String statusMessage) {
		this.message = message;
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
	}

	public String getMessage() {
		return message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

}
