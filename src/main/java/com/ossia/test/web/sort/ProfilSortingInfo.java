package com.ossia.test.web.sort;

public class ProfilSortingInfo {
	
	public final static String SORT_ACTIVE = "dateActivation";
	public final static String SORT_NAME = "nom";
	public final static String SORT_FIRSTNAME = "prenom";
	public final static String SORT_CREATION_DATE = "id";
	
	public final static String ASC = "asc";
	public final static String DESC = "desc";

	private String sortingField = SORT_CREATION_DATE;
	
	private String sortingDirection = DESC;

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