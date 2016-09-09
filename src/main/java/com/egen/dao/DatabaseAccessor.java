package com.egen.dao;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

public class DatabaseAccessor {
	private static DatabaseAccessor instance = null;
	final static Morphia morphia = new Morphia();

	private DatabaseAccessor(){
		morphia.mapPackage("com.egen.models");

	    datastore = morphia.createDatastore(new MongoClient(),
				"weight_service");
		datastore.ensureIndexes();
	}
	
	private Datastore datastore;
	
	public static DatabaseAccessor getInstance()
	{
		if(instance==null) instance = new DatabaseAccessor();
		return instance;
	}

	public Datastore getDatastore() {
		return datastore;
	}
}
