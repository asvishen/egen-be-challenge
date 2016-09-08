package com.egen.dao;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.egen.models.Alert;

public class AlertAccessor {
	private Datastore datastore;

	public AlertAccessor(Datastore store) {
		this.datastore = store;
	}

	public void create(Alert alert) {
		datastore.save(alert);
	}

	public List<Alert> read() {
		final Query<Alert> query = datastore.createQuery(Alert.class);
		return query.asList();

	}
	
	public List<Alert> readByTimestamp(long start,long end)
	{
		final Query<Alert> query = datastore.createQuery(Alert.class);
	    query.and(query.criteria("timeStamp").lessThan(end),query.criteria("timeStamp").greaterThan(start));
	    return query.asList();

	}

}
