package com.egen.weightwatcher;

import java.net.UnknownHostException;

import org.easyrules.spring.RulesEngineFactoryBean;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;


@Configuration
@EnableAutoConfiguration
@ComponentScan({ "com.egen.weightwatcher.demo", "com.egen.weightwatcher.controller" })

public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@Bean
	public static RulesEngineFactoryBean rulesEngine() {
		return new RulesEngineFactoryBean();
	}
	@Bean
	public static Datastore datastore() throws UnknownHostException{
		String dbName = new String("weightwatcher");
		MongoClient mongo = new MongoClient();
		Morphia morphia = new Morphia();
		Datastore ds = morphia.createDatastore(mongo, dbName);
		return ds;
	}
	/*	public void openDB() throws UnknownHostException {
	String dbName = new String("weightwatcher");
	MongoClient mongo = new MongoClient();
	Morphia morphia = new Morphia();
	ds = morphia.createDatastore(mongo, dbName);
}*/
	
}
