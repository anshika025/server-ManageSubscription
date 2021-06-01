## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)

## General info
This project is simple Building Manage Subscription Application.
	
## Technologies
Project is created with:
* SpringBoot version: 2.2.2
* java version: 1.8
* Gradle 4.4.1

	
## Setup
To run this project, install it locally using gradle:

```
$ cd ../server-manageSubscription
$ ./gradlew build --refresh-dependencies
$ ./gradlew clean build -x check

Execute all db queries mentioned in project "/server-ManageSubscription//db" folder

$ The database detail is stored in `application.properties.
spring.datasource.url=jdbc:${DB_URL}?useSSL=false
spring.datasource.username=root:${DB_USERNAME}
spring.datasource.password=${DB_PASSOWRD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto = update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

Run Project on : http://localhost:8080/



