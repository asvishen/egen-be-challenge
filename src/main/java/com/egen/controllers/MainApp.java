package com.egen.controllers;


import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine;

import org.easyrules.api.RulesEngine;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.egen.dao.AlertAccessor;
import com.egen.dao.MetricsAccessor;
import com.egen.rules.OverWeightRule;
import com.egen.rules.UnderWeightRule;
import com.mongodb.MongoClient;

@SpringBootApplication
public class MainApp {

	final static Morphia morphia = new Morphia();

	private static RulesEngine rulesEngine = null;
	private static Datastore datastore;

	public static Datastore getDatastore() {
		return datastore;
	}

	public static void main(String[] args) {
		morphia.mapPackage("com.egen.models");
	    datastore = morphia.createDatastore(new MongoClient(),
				"weight_service");
		datastore.ensureIndexes();
		rulesEngine = aNewRulesEngine().build();
		rulesEngine.registerRule(new OverWeightRule());
		rulesEngine.registerRule(new UnderWeightRule());
		SpringApplication.run(MainApp.class, args);

	}
	
	public static void runRules()
	{
		rulesEngine.fireRules();
	}

	@Bean
	public AlertAccessor alertAccessor()
	{
		return new AlertAccessor(getDatastore());
	}
	
	@Bean
	public MetricsAccessor metricsAccessor()
	{
		return new MetricsAccessor(getDatastore());
	}
}
