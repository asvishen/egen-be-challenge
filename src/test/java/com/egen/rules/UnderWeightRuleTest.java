package com.egen.rules;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.egen.dao.AlertAccessor;
import com.egen.dao.MetricsAccessor;
import com.egen.models.Metric;

public class UnderWeightRuleTest {
	UnderWeightRule rule;
	@Mock AlertAccessor alertAccess;
	@Mock MetricsAccessor metricAccess;
    Metric base;
	
	@Before
	public void setUpTest()
	{
		MockitoAnnotations.initMocks(this);
		rule = new UnderWeightRule();
		rule.setAlertAccessor(alertAccess);
		rule.setMetricsAccessor(metricAccess);
		base = new Metric();
		base.setTimeStamp(1);
		base.setValue(100);
	}
	@Test
	public void testNullVals()
	{
		Mockito.when(metricAccess.getBaseMetric()).thenReturn(base);
		Mockito.when(metricAccess.getLatestMetric()).thenReturn(base);
		Assert.assertFalse(rule.when());
	}
	
	@Test
	public void testUnderWeightTrue()
	{
		Metric metric = new Metric();
		metric.setTimeStamp(1234);
		metric.setValue(75);
		Mockito.when(metricAccess.getBaseMetric()).thenReturn(base);
		Mockito.when(metricAccess.getLatestMetric()).thenReturn(metric);
		Assert.assertTrue(rule.when());
		
	}
	
	@Test
	public void testUnderWeightFalse()
	{
		Metric metric = new Metric();
		metric.setTimeStamp(1234);
		metric.setValue(97);
		Mockito.when(metricAccess.getBaseMetric()).thenReturn(base);
		Mockito.when(metricAccess.getLatestMetric()).thenReturn(metric);
		Assert.assertFalse(rule.when());
	}
}
