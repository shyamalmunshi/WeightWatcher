package com.egen.weightwatcher.database;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egen.weightwatcher.model.Alerts;
import com.egen.weightwatcher.model.Metrics;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;

public class SetupMongoDB {

	private Datastore ds = SingletonClass.getInstance().getDatastore();

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private <T> Query<T> getTimeStamp(String start, String end, Class<T> clazz) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		Date sdate = sdf.parse(start);
		Date edate = sdf.parse(end);
		String sdatestring = String.valueOf(sdate.getTime());
		String edatestring = String.valueOf(edate.getTime());
		Query<T> query = ds.createQuery(clazz).filter("timestamp >=", sdatestring).filter("timestamp <=", edatestring);
		return query;
	}

	public SetupMongoDB() throws UnknownHostException, MongoException {

	}

	public void persistToMetrics(Metrics data) {
		Key<Metrics> savedModel = ds.save(data);
		if (savedModel.getId() != null) {
			logger.info(savedModel.getId().toString());
		} else {
			logger.error("Could not save Metrics to DB");
		}
	}

	public void persistToAlerts(Alerts data) {
		Key<Alerts> savedModel = ds.save(data);
		if (savedModel.getId() != null) {
			logger.info(savedModel.getId().toString());
		} else {
			logger.error("Could not save Alert to DB");
		}
	}

	public List<Metrics> readMetrics() {
		List<Metrics> metrics = ds.find(Metrics.class).asList();
		if (metrics.isEmpty()) {
			logger.error("Could find any Metrics");
		}
		return metrics;
	}

	public List<Metrics> readMetricsWithTime(String startdate, String enddate) throws ParseException {

		if (startdate == null || enddate == null) {
			return null;
		} else {
			List<Metrics> metrics = getTimeStamp(startdate, enddate, Metrics.class).asList();
			if (metrics.isEmpty()) {
				logger.error("Could find any Metrics for the time range: " + startdate + " to " + enddate);
			}
			return metrics;
		}
	}

	public List<Alerts> readAlerts() {
		List<Alerts> alerts = ds.find(Alerts.class).asList();
		if (alerts.isEmpty()) {
			logger.error("Could find any Alerts");
		}
		return alerts;
	}

	public List<Alerts> readAlertsWithTime(String startdate, String enddate) throws ParseException {

		if (startdate == null || enddate == null) {
			return null;
		} else {
			List<Alerts> alerts = getTimeStamp(startdate, enddate, Alerts.class).asList();
			if (alerts.isEmpty()) {
				logger.error("Could find any Alerts for the time range: " + startdate + " to " + enddate);
			}
			return alerts;
		}
	}

	public int deleteAlerts() {
		WriteResult writeresult = ds.delete(ds.createQuery(Alerts.class));
		int num = writeresult.getN();
		return num;
	}

	public int deleteMetrics() {
		WriteResult writeresult = ds.delete(ds.createQuery(Metrics.class));
		int num = writeresult.getN();
		return num;
	}
}
