package com.egen.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.egen.dao.AlertAccessor;
import com.egen.models.Alert;

/**
 * The Class AlertController maps API requests related to Alerts.
 */
@RestController
@RequestMapping(value = "/alerts")
public class AlertController {

	/** The alert. */
	@Autowired
	AlertAccessor alert;
	
	/**
	 * Reads all Alerts
	 *
	 * @return the list
	 */
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public List<Alert> read()
	{
		return alert.read();
	}
	
	/**
	 * Returns the alerts within the time range.
	 *
	 * @param start the start timestamp
	 * @param end the end timestamp
	 * @return the list alerts matching the range
	 */
	@RequestMapping(value = "/readByTimeRange/{start}/{end}", method = RequestMethod.GET)
	public List<Alert> readByTimeRange(@PathVariable long start,@PathVariable long end)
	{
		return alert.readByTimestamp(start, end);
	}
	
}
