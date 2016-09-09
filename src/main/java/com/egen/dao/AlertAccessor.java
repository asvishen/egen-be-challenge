package com.egen.dao;

import java.util.List;

import org.mongodb.morphia.query.Query;

import com.egen.models.Alert;

/**
 * The Class AlertAccessor gives access to ALert datastore.
 */
public class AlertAccessor {
	
	/**
	 * Creates a new alert and stores to datastore
	 *
	 * @param alert the alert
	 */
	public void create(Alert alert) {
		DatabaseAccessor.getInstance().getDatastore().save(alert);
	}

	/**
	 * Reads all values from Alert Datastore
	 *
	 * @return the list
	 */
	public List<Alert> read() {
		final Query<Alert> query = DatabaseAccessor.getInstance().getDatastore().createQuery(Alert.class);
		return query.asList();

	}
	
	/**
	 * Read values between the start and end timestamp(exclusive)
	 *
	 * @param start the start timestamp
	 * @param end the end timestamp
	 * @return the list of matching alert objects
	 */
	public List<Alert> readByTimestamp(long start,long end)
	{
		final Query<Alert> query = DatabaseAccessor.getInstance().getDatastore().createQuery(Alert.class);
	    query.and(query.criteria("timeStamp").lessThan(end),query.criteria("timeStamp").greaterThan(start));
	    return query.asList();

	}

}
