# Mortgages REST API

## Requirements

We would like you to create a java based backend application using REST.
It should contain the following endpoints;

* GET /api/interest-rates (get a list of current interest rates)
* POST /api/mortgage-check (post the parameters to calculate for a mortgage check)

The list of current mortgage rates should be created in memory on application startup.
The mortgage rate object contains the fields; maturityPeriod (integer), interestRate (Percentage) and lastUpdate (Timestamp)
The posted data for the mortgage check contains at least the fields; income (Amount), maturityPeriod (integer), loanValue (Amount), homeValue (Amount).
The mortgage check return if the mortgage is feasible (boolean) and the monthly costs (Amount) of the mortgage.

Business rules that apply are
- a mortgage should not exceed 4 times the income
- a mortgage should not exceed the home value

Use the frameworks as you see fit to build and test this.

### Implementation
Treat this application as a real MVP that should go to production.

### Duration
This assignment should take 4-5 hours at the most.


## Running the application locally

The only pre-requisite for running the application is to have JDK 17+ installed in the computer.

1st) From the root of the project folder, run the Maven command for compiling and packaging the app:

Linux
```
./mvnw clean package

```
Windows

```
./mvnw.cmd clean package

```

2nd) From the root of the project folder, run the Maven command for running the Spring Boot app:

```
./mvnw spring-boot:run
```


## Exercising the REST API

### Via Swagger UI

Once the app is running, we can go to the link: http://localhost:8080/swagger-ui/index.html

The openAPI definition can be found here: http://localhost:8080/v3/api-docs

### Via Command line using Curl

List all Interest Rates

```
curl -X 'GET' \
  'http://localhost:8080/api/interest-rates' \
  -H 'accept: application/json'
```


Checking feasibility of a Mortgage Proposal
```
curl -X 'POST' \
  'http://localhost:8080/api/mortgage-check' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "income": 10000,
  "maturityPeriod": 36,
  "loanValue": 100000,
  "homeValue": 180000,
  "interestRate": 5.5,
  "currency": "EUR"
}'
```