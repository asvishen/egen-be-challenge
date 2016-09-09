package com.egen.dao;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

/**
 * The Class DatabaseAccessor Singleton
 */
public class DatabaseAccessor {
	
	private static DatabaseAccessor instance = null;
	
	final static Morphia morphia = new Morphia();

	/**
	 * Instantiates a new database accessor.
	 */
	private DatabaseAccessor(){
		morphia.mapPackage("com.egen.models");

	    datastore = morphia.createDatastore(new MongoClient(),
				"weight_service");
		datastore.ensureIndexes();
	}
	
	private Datastore datastore;
	
	/**
	 * Gets the single instance of DatabaseAccessor.
	 *
	 * @return single instance of DatabaseAccessor
	 */
	public static DatabaseAccessor getInstance()
	{
		if(instance==null) instance = new DatabaseAccessor();
		return instance;
	}

	/**
	 * Gets the datastore.
	 *
	 * @return the datastore
	 */
	public Datastore getDatastore() {
		return datastore;
	}
}
