package com.egen.weightwatcher.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity (noClassnameStored=true)
public class Alerts {

	@Id
	private ObjectId id;

	private int value;

	private int base_weight;

	private String timestamp;
	
	private String observation;
	
	public int getbase_weight() {
		return base_weight;
	}

	public void setBase_weight(int base_weight) {
		this.base_weight = base_weight;
	}
	
	public String getobservation() {
		return observation;
	}

	public void setObservation(String description) {
		this.observation = description;
	}

	public Alerts() {
	}
	
	public String gettimestamp() {
		return timestamp;
	}

	public void setTimeStamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public int getvalue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String toString() {
		return "Data: { " + "timestamp = '" + timestamp + '\'' + ", value=" + value + '}';
	}
}