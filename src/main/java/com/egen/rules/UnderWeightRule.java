package com.egen.rules;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

import com.egen.dao.AlertAccessor;
import com.egen.dao.MetricsAccessor;
import com.egen.models.Alert;
import com.egen.models.Metric;


/**
 * The Class UnderWeightRule checks the condition for underweight and 
 * creates an alert.
 */
@Rule (name = "underweightrule" )
public class UnderWeightRule {
	
	private static final String TYPE = "Under Weight";
	
	private AlertAccessor alertAccessor;
	
	private MetricsAccessor metricsAccessor;
	
	private Metric currentMetric;
	
	/**
	 * Instantiates a new under weight rule.
	 */
	public UnderWeightRule() 
	{
		alertAccessor = new AlertAccessor();
		metricsAccessor = new MetricsAccessor();
	}

	/**
	 * checks the condition for Undereight rule
	 *
	 * @return true, if current wt is more than 10% than base wt
	 */
	@Condition
    public boolean when() {
		
		currentMetric = metricsAccessor.getLatestMetric();
		if(metricsAccessor.getBaseMetric()==metricsAccessor.getLatestMetric()) return false;
		if((double) currentMetric.getValue() / metricsAccessor.getBaseMetric().getValue() < 0.9)
			return true;
		return false;
		
    }
    
    /**
     * Creates a new Underweight alert if when is true
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
