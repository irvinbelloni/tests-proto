package com.ossia.test.web.sort;

public class SortingInfo {
	
	public final static String SORT_ACTIVE = "dateActivation";
	public final static String SORT_NAME = "nom";
	public final static String SORT_FIRSTNAME = "prenom";
	public final static String SORT_CREATION_DATE = "id";
	
	public final static String SORT_INTITULE = "intitule";
	public final static String SORT_TYPE = "type";
	public final static String SORT_DUREE = "duree";
	public final static String SORT_STATUS = "status";
		
	public final static String ASC = "asc";
	public final static String DESC = "desc";

	private String sortingField;
	
	private String sortingDirection;

	public String getSortingField() {
		return sortingField;
	}

	public void setSortingField(String sortingField) {
		this.sortingField = sortingField;
	}

	public String getSortingDirection() {
		return sortingDirection;
	}

	public void setSortingDirection(String sortingDirection) {
		this.sortingDirection = sortingDirection;
	}
}