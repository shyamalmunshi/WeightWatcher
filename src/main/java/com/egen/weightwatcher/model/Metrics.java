package com.egen.weightwatcher.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(noClassnameStored=true)

public class Metrics {

	@Id
	private ObjectId id;
	private int value;
	private String timestamp;


	public Metrics() {
	}
	
	public String gettimestamp() {
		return timestamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timestamp = timeStamp;
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