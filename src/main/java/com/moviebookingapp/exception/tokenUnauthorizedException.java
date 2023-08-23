package com.moviebookingapp.exception;

public class tokenUnauthorizedException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public tokenUnauthorizedException(String msg) {
		super(msg);
	}

}
