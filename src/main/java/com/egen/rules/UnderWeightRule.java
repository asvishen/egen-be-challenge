package com.egen.rules;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

import com.egen.dao.AlertAccessor;
import com.egen.dao.DatabaseAccessor;
import com.egen.dao.MetricsAccessor;
import com.egen.models.Alert;
import com.egen.models.Metric;


@Rule (name = "underweightrule" )
public class UnderWeightRule {
	
	private static final String TYPE = "Under Weight";
	private AlertAccessor alertAccessor;
	private MetricsAccessor metricsAccessor;
	private Metric currentMetric;
	
	public UnderWeightRule() 
	{
		alertAccessor = new AlertAccessor(DatabaseAccessor.getInstance().getDatastore());
		metricsAccessor = new MetricsAccessor(DatabaseAccessor.getInstance().getDatastore());
	}

	@Condition
    public boolean when() {
		
		currentMetric = metricsAccessor.getLatestMetric();
		if(metricsAccessor.getBaseMetric()==metricsAccessor.getLatestMetric()) return false;
		if((double) currentMetric.getValue() / metricsAccessor.getBaseMetric().getValue() < 0.9)
			return true;
		return false;
		
    }
    @Action
    public void then() {
    	Alert alert = new Alert();
    	alert.setTimeStamp(currentMetric.getTimeStamp());
    	alert.setType(TYPE);
    	alert.setValue(currentMetric.getValue());
    	alertAccessor.create(alert);
    }
    
	public void setAlertAccessor(AlertAccessor alertAccessor) {
		this.alertAccessor = alertAccessor;
	}

	public void setMetricsAccessor(MetricsAccessor metricsAccessor) {
		this.metricsAccessor = metricsAccessor;
	}
}
