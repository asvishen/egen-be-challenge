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
 * The Class UnderWeightRuleTest.
 */
public class UnderWeightRuleTest {
	
	/** The rule. */
	UnderWeightRule rule;
	
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
		rule = new UnderWeightRule();
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
	 * Test under weight true.
	 */
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
	
	/**
	 * Test under weight false.
	 */
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
