package com.egen.weightwatcher.controller;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.List;

import javax.validation.Valid;

import org.easyrules.api.RulesEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.egen.weightwatcher.database.SetupMongoDB;
import com.egen.weightwatcher.model.Alerts;
import com.egen.weightwatcher.model.Metrics;
import com.egen.weightwatcher.rules.OverWtRules;
import com.egen.weightwatcher.rules.UnderWtRules;
import com.mongodb.MongoException;

@Controller
public class DataController {

	@Autowired
	private RulesEngine rulesEngine;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final int def_wt = 150;

	OverWtRules overwt = new OverWtRules(def_wt);
	UnderWtRules underwt = new UnderWtRules(def_wt);

	private SetupMongoDB SetupMongoDB() throws UnknownHostException, MongoException {
		SetupMongoDB setup = new SetupMongoDB();
		return setup;
	}

	private void runRules(Metrics metrics) {
		overwt.setAlerts(metrics);
		underwt.setAlerts(metrics);
		rulesEngine.registerRule(overwt);
		rulesEngine.registerRule(underwt);
		rulesEngine.fireRules();
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody void create(@RequestBody @Valid Metrics metrics, Model model)
			throws UnknownHostException, MongoException {
		if (metrics != null) {
			model.addAttribute("metric", metrics);
			SetupMongoDB().persistToMetrics(metrics);
			logger.info("Added: " + metrics.toString());
			runRules(metrics);
		} else {
			logger.error("Error receiving data");
		}
	}

	@RequestMapping(value = "/read/metrics", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Metrics> readMetrics() throws UnknownHostException, MongoException {
		List<Metrics> metrics = SetupMongoDB().readMetrics();
		if (!metrics.isEmpty()) {
			return metrics;
		}
		return null;
	}

	@RequestMapping(value = "/read/metrics/timerange", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Metrics> readMetricsByTimeRange(@RequestParam("startdate") String start,
			@RequestParam("enddate") String end) throws UnknownHostException, MongoException, ParseException {
		String startdate = start;
		String enddate = end;
		List<Metrics> metrics = SetupMongoDB().readMetricsWithTime(startdate, enddate);
		if (!metrics.isEmpty()) {
			return metrics;
		}
		return null;
	}

	@RequestMapping(value = "/read/alerts", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Alerts> readAlerts() throws UnknownHostException, MongoException {
		List<Alerts> alerts = SetupMongoDB().readAlerts();
		if (!alerts.isEmpty()) {
			return alerts;
		}
		return null;
	}

	@RequestMapping(value = "/read/alerts/timerange", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Alerts> readMAlertsByTimeRange(@RequestParam("startdate") String start,
			@RequestParam("enddate") String end) throws UnknownHostException, MongoException, ParseException {
		String startdate = start;
		String enddate = end;
		List<Alerts> alerts = SetupMongoDB().readAlertsWithTime(startdate, enddate);
		if (!alerts.isEmpty()) {
			return alerts;
		}
		return null;
	}

	@RequestMapping(value = "/delete/alerts", method = RequestMethod.DELETE, headers = "Accept=application/text")
	public @ResponseBody String deleteAlerts() throws UnknownHostException, MongoException {
		int num = SetupMongoDB().deleteAlerts();
		return "Deleted Alerts table with " + num + " values.";
	}

	@RequestMapping(value = "/delete/metrics", method = RequestMethod.DELETE, headers = "Accept=application/text")
	public @ResponseBody String deleteMetrics() throws UnknownHostException, MongoException {
		int num = SetupMongoDB().deleteMetrics();
		return "Deleted Alerts table with " + num + " values.";
	}

}
