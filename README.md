## Synopsis

    The goal of this exercise is to build a system that works like an IoT platform – in this case, a personal weight tracker.
    
    This system is responsible for the following:
    
    Consuming data sent from different sensors (emulators)
    
    Storing the received data in MongoDB
    
    Running the data through different rules to make basic predictions

## Technologies/Frameworks

     Java 1.8
    
     Spring Boot 1.3.5
    
     Mongo DB 3.2.7
    
     Morphia 1.2.0
    
     EasyRules 2.2.0

## Overview

    Used MongoDB as the datastore with two collections
    
    Metrics – stores the data that comes from sensor
    
    Alerts – stores the alerts that were created by the rules

## API Reference

   Exposed the below Metric APIs using Spring MVC

    o create – This is the API that will consume data from the sensor emulator
  
      path: http://localhost:8080/create

    o read – Reads all the metrics stored in your database
  
      path: http://localhost:8080/read/metrics

    o readByTimeRange – Reads all the metrics that were created between the given two timestamps
  
      path: http://localhost:8080/read/metrics/timerange?startdate=06-15-2016 15:00:00&enddate=06-15-2016 15:55:35

    o delete : Deletes all metrics from the collection
  
      path: http://localhost:8080/delete/metrics


    Exposed the below Alert APIs using Spring MVC

    o read – Reads all alerts that are stored in the database
  
      path: http://localhost:8080/read/alerts

    o readByTimeRange – Reads all alerts that are created between the given two timestamps
  
      path: http://localhost:8080/read/alerts/timerange?startdate=06-15-2016 15:00:00&enddate=06-15-2016 15:55:35

    o delete : Deletes all alerts from the collection
  
      path: http://localhost:8080/delete/metrics

## JSON Responses

    Describe and show how to run the tests with code examples.
    
    Metrics:
    
    {
      "value": 150,
      "timestamp": "1466021263862"
    }
    
    Alerts:
    
    {
      "value": 166,
      "base_weight": 150,
      "timestamp": "1466021344976",
      "observation": "Overweight"
    }
    
    {
      "value": 150,
      "base_weight": 167,
      "timestamp": "1466021344976",
      "observation": "Underweight"
    }

## Contributors

    Shyamal Munshi


## Version
    
    Current version: 1.0.0
    Date: 06/15/2016

## Future Enhancements

 Develop UI

 Add Lumbok support

## Contributors

Shyamal Munshi

## License

Open
