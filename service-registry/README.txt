Description:
This project implements a Service Registry based upon Eureka, developed at Netflix and integrated into Spring Cloud
(see: http://cloud.spring.io/spring-cloud-netflix/).

This service registry maintains a list of service instances grouped by service name. Service instances are responsible
for registering and deregistering themselves in/from this registry.
Clients can use the registry to obtain references
to service instances given a service name. To see how this can be done see:
https://spring.io/blog/2015/01/20/microservice-registration-and-discovery-with-spring-cloud-and-netflix-s-eureka


How to build this Service Registry?
1. Open a command prompt/shell and go to the folder where this project has been placed.
2. Execute the following command:
   mvn clean verify

   If everything went as expected you should see "BUILD SUCCESS" appearing on the prompt.

How to run this Service Registry?
1. Open a command prompt/shell and go to the folder where this project has been placed.
2. Execute the following command:
   java -jar target/service-registry-1.0-SNAPSHOT.jar <port>

   You must provide the port in which this Service Registry should run.

   If everything went as expected, after a couple of seconds, you should see the following message:
   Started ServiceRegistry in x.xx seconds

How to check that the Service Registry is running?
1. Open a browser and go to http://localhost:<port>/.
   You should see a simple web page providing information on the service registry, including the services that are
   registered into it.
