package com.egen.weightwatcher.rules;

import java.net.UnknownHostException;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egen.weightwatcher.database.SetupMongoDB;
import com.egen.weightwatcher.model.Alerts;
import com.egen.weightwatcher.model.Metrics;
import com.mongodb.MongoException;

@Rule
public class OverWtRules {

	private Alerts alerts;
	private int base_wt;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public OverWtRules(int base_wt) {
		this.base_wt = base_wt;
	}

	public void setAlerts(Metrics metrics) {
		alerts = new Alerts();
		alerts.setTimeStamp(metrics.gettimestamp());
		alerts.setValue(metrics.getvalue());
		alerts.setObservation("Overweight");
		alerts.setBase_weight(base_wt);
	}

	@Condition
	public boolean when() {
		return alerts.getvalue() > base_wt + (base_wt * 0.1);
	}

	@Action
	public void save() throws UnknownHostException, MongoException {
		SetupMongoDB setup = new SetupMongoDB();
		//setup.openDB();
		setup.persistToAlerts(alerts);
		logger.info("Greater than " + base_wt);
	}

}
