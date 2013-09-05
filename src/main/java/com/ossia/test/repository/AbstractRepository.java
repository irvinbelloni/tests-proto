package com.ossia.test.repository;
import java.util.List;

public interface AbstractRepository<T, PK> {

	/**
	 * Creates a new instance of a T object in the database
	 * @param newInstance
	 * @return The primary key of the newly created object
	 */
	PK create(T newInstance);
	
	/**
	 * Fetches in the database the object specified by the given id
	 * @param id Id of the object we are looking for
	 * @return The fetched object, null if none have been found
	 */
    T read(PK instanceId);
 
    /**
     * Fetches all the objects in the given table
     * @return List of fetched objects
     */
    List<T> readAll();
 
    /**
     * Updates an object in the database
     * @param transientObject Object to update
     */
    void update(T transientObject);
 
    /** Deletes the object from the database 
     * @param persistentObject Object to delete
     */
    void delete(T persistentObject);
}