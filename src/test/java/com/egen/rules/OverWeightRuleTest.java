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

// TODO: Auto-generated Javadoc
/**
 * The Class OverWeightRuleTest.
 */
public class OverWeightRuleTest {
	
	/** The rule. */
	OverWeightRule rule;
	
	/** The alert access. */
	@Mock AlertAccessor alertAccess;
	
	/** The metric access. */
	@Mock MetricsAccessor metricAccess;
    
    /** The base. */
    Metric base;
	
	/**
	 * Sets the up test.
	 */
	@Before
	public void setUpTest()
	{
		MockitoAnnotations.initMocks(this);
		rule = new OverWeightRule();
		rule.setAlertAccessor(alertAccess);
		rule.setMetricsAccessor(metricAccess);
		base = new Metric();
		base.setTimeStamp(1);
		base.setValue(100);
	}
	
	/**
	 * Test null vals.
	 */
	@Test
	public void testNullVals()
	{
		Mockito.when(metricAccess.getBaseMetric()).thenReturn(base);
		Mockito.when(metricAccess.getLatestMetric()).thenReturn(base);
		Assert.assertFalse(rule.when());
	}
	
	/**
	 * Test over weight true.
	 */
	@Test
	public void testOverWeightTrue()
	{
		Metric metric = new Metric();
		metric.setTimeStamp(1234);
		metric.setValue(120);
		Mockito.when(metricAccess.getBaseMetric()).thenReturn(base);
		Mockito.when(metricAccess.getLatestMetric()).thenReturn(metric);
		Assert.assertTrue(rule.when());
		
	}
	
	/**
	 * Test over weight false.
	 */
	@Test
	public void testOverWeightFalse()
	{
		Metric metric = new Metric();
		metric.setTimeStamp(1234);
		metric.setValue(80);
		Mockito.when(metricAccess.getBaseMetric()).thenReturn(base);
		Mockito.when(metricAccess.getLatestMetric()).thenReturn(metric);
		Assert.assertFalse(rule.when());
	}
}
