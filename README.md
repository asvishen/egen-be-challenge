# egen-be-challenge

This is a Personal Weight Tracker Application built using Spring Boot API. 
## Model
- Metric(weight)
- Alert(Over or Under weight)

## Features

- Adds a new Metric
- Read all Metrics
- Reads metrics between timestamps
- Read all Alerts
- Read alerts between timestamps

## Dependencies

- Spring Boot API
- JUnit
- Mockito
- EasyRules
- Morphia
- Maven

## Execution

- Run the MongoDb instance
- Execute the maven command to run the service
```
mvn spring-boot:run -Dserver.port=9000
```
## Java Version

- 1.8

## APIs

### Metrics

1) Create a new metric

```POST localhost:9000/metrics/create```

2) Read all Values

```GET localhost:9000/metrics/read```

3) Read between time range

```GET localhost:9000/metrics/readByTimeRange/{start}/{end} ```

### Alerts

1)Read all Values

```GET localhost:9000/alerts/read```

2) Read values between timestamps

``` GET localhost:9000/alerts/readByTimeRange/{start}/{end}```

## Running the Sensor Emulator

Build the sensor emulator using mvn:package

Execute the jar

```java -jar -Dbase.value=150 -Dapi.url=http://localhost:9000/metrics/create target/sensor-emulator-0.0.1-SNAPSHOT.jar```
```
