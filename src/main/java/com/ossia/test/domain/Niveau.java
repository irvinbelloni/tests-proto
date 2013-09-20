package com.ossia.test.domain;

public enum Niveau {

    JUNIOR ("JUNIOR"),
    INTERMEDIAIRE ("INTERMEDIAIRE") ,
    SENIOR ("SENIOR") ;
    
    String value ;
    
	private Niveau(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public static Niveau fromValue(String valueParam) {
		
		if (valueParam.matches(Niveau.JUNIOR.getValue())) {
			return Niveau.JUNIOR ; 
		} else if (valueParam.matches(Niveau.INTERMEDIAIRE.getValue())) {
			return Niveau.INTERMEDIAIRE ; 
		} else if (valueParam.matches(Niveau.SENIOR.getValue())) {
			return Niveau.SENIOR ; 
		} else {
			return null ; 
		}
	}

	public void setValue(String value) {
		this.value = value;
	} 
}
