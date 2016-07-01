package com.cooksys.ftd.assessment.filesharing.model.api;

public class Response<T> {
	
	/**
	 * If true, an error was encountered and no data returned.
	 */
	private Boolean error;
	
	/**
	 * Optional message field.
	 */
	private String message;
	
	/**
	 * Data returned by the server. This will be missing if an error occurs.
	 */
	private T data;
	
	public Boolean getError() {
		return error;
	}
	
	public void setError(Boolean error) {
		this.error = error;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public T getData() {
		return data;
	}
	
	public void setData(T data) {
		this.data = data;
	}

}
