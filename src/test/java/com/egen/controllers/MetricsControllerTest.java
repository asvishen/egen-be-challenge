package com.egen.controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.egen.models.Metric;

public class MetricsControllerTest {
	
	@Autowired
	private MockMvc mvc;

	@Mock
	MetricsController metricsController = new MetricsController();

	@Before
	public void setup() {

		MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders.standaloneSetup(metricsController).build();

	}

	@Test
	public void testRead() throws Exception {
		List<Metric> list = new ArrayList<Metric>();

		Metric metric = new Metric();
		metric.setTimeStamp(0);
		metric.setValue(120);
		list.add(metric);
		metric = new Metric();
		metric.setTimeStamp(4);
		metric.setValue(140);
		list.add(metric);
		given(metricsController.read()).willReturn(list);
		this.mvc.perform(get("/metrics/read").accept("application/json"))
				.andExpect(status().isOk())
				.andExpect(
						content()
								.string("[{\"timeStamp\":0,\"value\":120},{\"timeStamp\":4,\"value\":140}]"));

	}

	@Test
	public void testReadWithRange() throws Exception {
		List<Metric> list = new ArrayList<Metric>();

		Metric metric = new Metric();
		metric.setTimeStamp(7);
		metric.setValue(150);
		list.add(metric);
		metric = new Metric();
		metric.setTimeStamp(4);
		metric.setValue(140);
		list.add(metric);

		given(metricsController.readByTimeRange(3, 10)).willReturn(list);
		this.mvc.perform(
				get("/metrics/readByTimeRange/3/10").accept("application/json"))
				.andExpect(status().isOk())
				.andExpect(
						content()
								.string("[{\"timeStamp\":7,\"value\":150},{\"timeStamp\":4,\"value\":140}]"));

	}

	@Test
	public void testReadWithRangeNoMatch() throws Exception {
		List<Metric> list = new ArrayList<Metric>();
		given(metricsController.readByTimeRange(40, 100)).willReturn(list);
		this.mvc.perform(
				get("/metrics/readByTimeRange/40/100")
						.accept("application/json")).andExpect(status().isOk())
				.andExpect(content().string("[]"));

	}
}
