# JavaScript Framework Manager
This repository contains an implementation of the "JavaScript Framework Manager" 
service according to the specs in [zadani.txt](./zadani.txt).

## Prerequisites
In order to compile this project, a properly installed JDK 1.8 is needed. 

## How To Compile & Run
A test coverage report and stand-alone binary can be generated via command 
line issuing following command (for Linux/Unix like systems)
 
`./gradlew clean check bootJar` 

The service can be than started by typing

`java -jar build/libs/java-test-default-*.jar`

Test coverage report is available in the `build/reports/tests/test` folder.

### Alternative Running Ways
The service can be also run via gradle by issuing following command

`./gradlew bootJar`

However, this is not a preferred way.

## Customizations
The default port is `8080`. This can be changed via command line parameter 
`-Dserver.port=<desired-port>`.

E.g. in order to start the service on port `8090`, issue following command

`java -Dserver.port=8090 -jar build/libs/java-test-default-*.jar`

The service uses H2 in the embedded in-memory mode with the console switched on.
The H2 console is available (assuming service runs on port `8080`) on URI
[http://localhost:8080/h2-console](http://localhost:8080/h2-console). The JDBC 
URL is `jdbc:h2:mem:js-frameworks` with user name `sa` and no password.
