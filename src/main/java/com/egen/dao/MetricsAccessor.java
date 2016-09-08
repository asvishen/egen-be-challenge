package com.egen.dao;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.egen.models.Metric;

public class MetricsAccessor {

	private Datastore datastore;
	
	private static final String TIMESTAMP_KEY = "timeStamp";

	public MetricsAccessor(Datastore store) {
		this.datastore = store;
	}

	public void create(Metric metric) {
		datastore.save(metric);
	}

	public List<Metric> read() {
		final Query<Metric> query = datastore.createQuery(Metric.class);
		return query.asList();

	}
	public List<Metric> readByTimestamp(long start,long end)
	{
		final Query<Metric> query = datastore.createQuery(Metric.class);
	    query.and(query.criteria(TIMESTAMP_KEY).lessThan(end),query.criteria(TIMESTAMP_KEY).greaterThan(start));
	    return query.asList();

	}
	
	public Metric getBaseMetric()
	{
		return getMetricByOrder(TIMESTAMP_KEY);
	}
	
	public Metric getLatestMetric()
	{
		return getMetricByOrder("-" + TIMESTAMP_KEY);
	}
	private Metric getMetricByOrder(String key)
	{
		final Query<Metric> query = datastore.createQuery(Metric.class);
		return query.order(key).get();
	}
}
