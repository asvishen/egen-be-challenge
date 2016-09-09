package com.egen.rules;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

import com.egen.dao.AlertAccessor;
import com.egen.dao.MetricsAccessor;
import com.egen.models.Alert;
import com.egen.models.Metric;

/**
 * The Class OverWeightRule.
 */
@Rule (name = "overweightrule" )
public class OverWeightRule { 
		
	public static String TYPE = "Over Weight";
    
    private AlertAccessor alertAccessor;
	
	private MetricsAccessor metricsAccessor;
	
	private Metric currentMetric;
	
	/**
	 * Instantiates a new over weight rule.
	 */
	public OverWeightRule()
	{
		alertAccessor = new AlertAccessor();
		metricsAccessor = new MetricsAccessor();
	}

	/**
	 * Checks if current wt is 10% less than base wt.
	 *
	 * @return true, if condition meets
	 */
	@Condition
    public boolean when() {
		currentMetric = metricsAccessor.getLatestMetric();
		if(metricsAccessor.getBaseMetric()==metricsAccessor.getLatestMetric()) return false;
		double ratio = (double)currentMetric.getValue()/metricsAccessor.getBaseMetric().getValue();
		if(ratio > 1.1)
		{
			return true;
		}

		return false;
		
    }


	/**
	 * Creates a new Underweight alert and saves to datastore
	 */
	@Action
    public void then() {
    	Alert alert = new Alert();
    	alert.setTimeStamp(currentMetric.getTimeStamp());
    	alert.setType(TYPE);
    	alert.setValue(currentMetric.getValue());
    	alertAccessor.create(alert);
    }
	

	/**
	 * Sets the alert accessor.
	 *
	 * @param alertAccessor the new alert accessor
	 */
	public void setAlertAccessor(AlertAccessor alertAccessor) {
		this.alertAccessor = alertAccessor;
	}

	/**
	 * Sets the metrics accessor.
	 *
	 * @param metricsAccessor the new metrics accessor
	 */
	public void setMetricsAccessor(MetricsAccessor metricsAccessor) {
		this.metricsAccessor = metricsAccessor;
	}
	
}
