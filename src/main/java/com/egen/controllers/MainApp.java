package com.egen.controllers;


import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine;

import org.easyrules.api.RulesEngine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.egen.dao.AlertAccessor;
import com.egen.dao.MetricsAccessor;
import com.egen.rules.OverWeightRule;
import com.egen.rules.UnderWeightRule;

/**
 * The Class MainApp which launches the Application
 */
@SpringBootApplication
public class MainApp {


	private static RulesEngine rulesEngine = null;

	public static void main(String[] args) {

		rulesEngine = aNewRulesEngine().build();
		rulesEngine.registerRule(new OverWeightRule());
		rulesEngine.registerRule(new UnderWeightRule());
		SpringApplication.run(MainApp.class, args);

	}
	
	/**
	 * Fires all the rules registered.
	 */
	public static void runRules()
	{
		rulesEngine.fireRules();
	}

	/**
	 * Alert accessor bean autowired into Controllers
	 *
	 * @return the alert accessor
	 */
	@Bean
	public AlertAccessor alertAccessor()
	{
		return new AlertAccessor();
	}
	
	/**
	 * Metrics accessor bean.
	 *
	 * @return the metrics accessor
	 */
	@Bean
	public MetricsAccessor metricsAccessor()
	{
		return new MetricsAccessor();
	}
}
