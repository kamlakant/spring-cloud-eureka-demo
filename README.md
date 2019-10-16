# Spring Cloud Eureka Demo
Demo of Netflix Eureka with replicated Eureka servers and multiple interdependent clients

## Running the Eureka Server

Eureka server can be run in standalone (test) or replicated mode (production - _default_).

### Running in standalone mode

1. Navigate to `eureka-server` directory.
2. Run `mvn spring-boot:run` in terminal.
3. Open `http://localhost:8001` to see Eureka Server console.

### Running in replicated mode

Eureka server is supposed to be replicated in different hosts. To simulate this in single host, modify `/etc/hosts` file and add below entries:
```
127.0.0.1   dancer-host
127.0.0.1   dasher-host
```
Restart the system for the host change to be effective.

1. Navigate to `eureka-server` directory in two different terminals.
2. Run `mvn spring-boot:run -Dspring-boot.run.profiles=dasher` in 1st terminal. Ignore the warnings in logs.
3. Run `mvn spring-boot:run -Dspring-boot.run.profiles=dancer` in 2nd terminal.
3. Open `http://dasher-host:8001` and `http://dancer-host:8002` to see replicated Eureka Server consoles.

## Running the Eureka clients

1. Navigate to `eureka-client-alice` directory.
2. Run `mvn spring-boot:run` in terminal.
3. Navigate to `eureka-client-bob` directory.
4. Run `mvn spring-boot:run` in terminal.
5. Open `http://localhost:9001/get-stuff/BOB` in browser to fetch data from BOB service into ALICE service.
6. Open `http://localhost:9002/get-stuff/ALICE` in browser to fetch data from ALICE service into BOB service.

![Dasher Server](/screenshots/dasher-server.png)
![Dancer Server](/screenshots/dancer-server.png)