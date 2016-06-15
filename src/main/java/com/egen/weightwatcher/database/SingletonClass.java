package com.egen.weightwatcher.database;

import java.net.UnknownHostException;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

public class SingletonClass {
	
	  private static SingletonClass instance = null;
	  private static Datastore ds;
	  
	    private SingletonClass() throws UnknownHostException {
	    	
			String dbName = new String("weightwatcher");
			MongoClient mongo = new MongoClient();
			Morphia morphia = new Morphia();
			ds = morphia.createDatastore(mongo, dbName);
	    }

	    public static SingletonClass getInstance() throws UnknownHostException {
	        if (instance == null) {
	            instance = new SingletonClass();
	        }
	        return instance;
	    }
	    public Datastore getDatastore() {
	        return ds;
	    }
	    

}
