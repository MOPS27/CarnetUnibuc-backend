package com.reportcard.project.exceptions;

public class DuplicateItemException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7769735604949418285L;

	public DuplicateItemException(String entityName, String propertyName, String propertyValue) {
		super(String.format("%s cu %s = %s deja exista", 
							entityName, 
							propertyName, 
							propertyValue));
	}

}

