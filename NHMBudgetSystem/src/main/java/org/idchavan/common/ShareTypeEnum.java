package org.idchavan.common;

/**
 * This enum hold share type.<BR>
 * 1. State
 * 2. Central.
 * 
 * @author Dnyaneshwar Chavan
 * @since 04-11-2017
 *
 */
public enum ShareTypeEnum {
	
	STATE("State"),
	CENTRAL("Central");
	
	private String name;
	
	private ShareTypeEnum(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
}