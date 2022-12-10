package com.reportcard.project.exceptions;

public class NotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7769735604949418285L;

	public NotFoundException(String entityName, String propertyName, String propertyValue) {
		super(String.format("%s cu %s = %s nu exista in baza de date", 
							entityName, 
							propertyName, 
							propertyValue));
	}

}

