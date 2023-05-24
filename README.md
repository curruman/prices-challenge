bo# MICROSERVICE PRICES

Contacts
========
* dlopezo@gmail.com

## Clone Project
1. Clone repository 

	`git clone https://github.com/curruman/prices-challenge.git`

2. Change for branch **master** to **develop** (or any other)

	`git checkout develop`

##  Deploy
Description of the procedures for generating the prices project packages

### Execution of Build

Run in the root folder project, the task gradle

				./gradlew clean
				./gradlew build
		
However, it is not uncommon to break tests during the build, in which case it will be necessary to:
** a) ** identify the project (s) whose tests failed
** b) ** correct the reason (s) for each failure
** c) ** run the project tests again the packages
> * the deploy packages for each project will be automatically copied to the subfolder **. \ build \  ** in 'zip' format

The generator of migration of flyway is generated by executing:

				./gradlew flywayMigrate -i

## Run

`bootRun`

Using microservices prices
================
Using a tool like Postman, a GET request is made with the following URL
Example:
[prices Microservices](localhost:9090/prices/api/v1/searchPrice/1/35455?date=2020-06-14-10.00.00)
