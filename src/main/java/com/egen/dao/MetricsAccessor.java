package com.egen.dao;

import java.util.List;

import org.mongodb.morphia.query.Query;

import com.egen.models.Metric;

/**
 * The Class MetricsAccessor provides access to 
 * 
 */
public class MetricsAccessor {

	
	private static final String TIMESTAMP_KEY = "timeStamp";

	/**
	 * Creates a new metric and saves to datastore
	 *
	 * @param metric the metric
	 */
	public void create(Metric metric) {
		DatabaseAccessor.getInstance().getDatastore().save(metric);
	}

	/**
	 * Returns all Metrics
	 *
	 * @return the list of all metrics
	 */
	public List<Metric> read() {
		final Query<Metric> query = DatabaseAccessor.getInstance().getDatastore().createQuery(Metric.class);
		return query.asList();

	}
	
	/**
	 * Returns the metrics between the two timestamps(exclusive)
	 *
	 * @param start the start timestamp
	 * @param end the end timestamp
	 * @return the list
	 */
	public List<Metric> readByTimestamp(long start,long end)
	{
		final Query<Metric> query = DatabaseAccessor.getInstance().getDatastore().createQuery(Metric.class);
	    query.and(query.criteria(TIMESTAMP_KEY).lessThan(end),query.criteria(TIMESTAMP_KEY).greaterThan(start));
	    return query.asList();

	}
	
	/**
	 * Gets the base metric.
	 *
	 * @return the base metric
	 */
	public Metric getBaseMetric()
	{
		return getMetricByOrder(TIMESTAMP_KEY);
	}
	
	/**
	 * Gets the latest metric.
	 *
	 * @return the latest metric
	 */
	public Metric getLatestMetric()
	{
		return getMetricByOrder("-" + TIMESTAMP_KEY);
	}
	
	/**
	 * Gets the first metric by ordering them according to the key
	 *
	 * @param key the key by which metrics need to be sorted
	 * @return the metric first metric from the result of sort
	 */
	private Metric getMetricByOrder(String key)
	{
		final Query<Metric> query = DatabaseAccessor.getInstance().getDatastore().createQuery(Metric.class);
		return query.order(key).get();
	}
}
