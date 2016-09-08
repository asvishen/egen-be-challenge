package com.egen.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.egen.dao.MetricsAccessor;
import com.egen.models.Metric;

@RestController
@RequestMapping(value = "/metrics")
public class MetricsController {

	@Autowired
	private MetricsAccessor metricsAccessor;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public void create(@RequestBody Metric metric) {
		metricsAccessor.create(metric);
		MainApp.runRules();

	}

	@ExceptionHandler
	void handleIllegalArgumentException(IllegalArgumentException e,
			HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}

	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public List<Metric> read() {
		return metricsAccessor.read();
	}

	@RequestMapping(value = "/readByTimeRange/{start}/{end}", method = RequestMethod.GET)
	public List<Metric> readByTimeRange(@PathVariable long start,
			@PathVariable long end) {
		return metricsAccessor.readByTimestamp(start, end);
	}

}
