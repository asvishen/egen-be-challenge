package com.egen.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.egen.dao.MetricsAccessor;
import com.egen.models.Metric;

/**
 * The Class MetricsController handles Metrics API calls
 */
@RestController
@RequestMapping(value = "/metrics")
public class MetricsController {

	@Autowired
	private MetricsAccessor metricsAccessor;

	/**
	 * Creates a new metric and puts in the datastore.
	 * Runs the rules and creates an alert if applicable
	 *
	 * @param metric the metric from the request
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public void create(@RequestBody Metric metric) {
		metricsAccessor.create(metric);
		MainApp.runRules();

	}

	/**
	 * Read all values from the Metrics Datastore
	 *
	 * @return the list
	 */
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public List<Metric> read() {
		return metricsAccessor.read();
	}

	/**
	 * Read values within timerange(exclusive)
	 *
	 * @param start the start timestamp
	 * @param end the end timestamp
	 * @return the list of metric objects
	 */
	@RequestMapping(value = "/readByTimeRange/{start}/{end}", method = RequestMethod.GET)
	public List<Metric> readByTimeRange(@PathVariable long start,
			@PathVariable long end) {
		return metricsAccessor.readByTimestamp(start, end);
	}

}
