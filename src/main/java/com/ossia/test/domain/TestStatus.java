package com.ossia.test.domain;

public enum TestStatus {

	DRAFT ("DRAFT"),
    VALIDATED ("VALIDATED") ,
    ARCHIVED ("ARCHIVED") ;
    
    String value;
    
	private TestStatus(String value) {
		this.value = value;
	}
	
	public static TestStatus getByValue(String value) {		
		for(TestStatus status : TestStatus.values()) {
			if (status.getValue().equals(value)) {
				return status;
			}
		}
		return null;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	} 
}