package com.egen.controllers;


import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine;

import org.easyrules.api.RulesEngine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.egen.dao.AlertAccessor;
import com.egen.dao.DatabaseAccessor;
import com.egen.dao.MetricsAccessor;
import com.egen.rules.OverWeightRule;
import com.egen.rules.UnderWeightRule;

@SpringBootApplication
public class MainApp {


	private static RulesEngine rulesEngine = null;

	public static void main(String[] args) {

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
		return new AlertAccessor(DatabaseAccessor.getInstance().getDatastore());
	}
	
	@Bean
	public MetricsAccessor metricsAccessor()
	{
		return new MetricsAccessor(DatabaseAccessor.getInstance().getDatastore());
	}
}
