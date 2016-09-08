package com.egen.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.egen.dao.AlertAccessor;
import com.egen.models.Alert;

@RestController
@RequestMapping(value = "/alerts")
public class AlertController {

	@Autowired
	AlertAccessor alert;
	
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public List<Alert> read()
	{
		return alert.read();
	}
	@RequestMapping(value = "/readByTimeRange/{start}/{end}", method = RequestMethod.GET)
	public List<Alert> readByTimeRange(@PathVariable long start,@PathVariable long end)
	{
		return alert.readByTimestamp(start, end);
	}
	
}
