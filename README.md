# MTM
Money Transfer Microservice

This microservice is developed using Spark Java, Maven, Guice, Gson, Lombok, H2 in-memory database, Hibernate ORM, Mockito and Junit.

## Create Jar

    mvn clean install

## Run the app

    java -jar target/MTM-release.jar

# REST API

The REST API to the service is described below.

## Create a new account

### Request

`POST /api/accounts/createAccount`

    curl -d '{"accountOwnerName":"vinoth kumar"}' -H "Content-Type: application/json" -X POST http://localhost:4567/api/accounts/createAccount

### Response

    {"accountNumber":"7a0feb7e-463a-40fc-9b9c-3820e65fca51"}

## Get All Accounts

### Request

`GET /api/accounts/`
 
    curl -H 'Accept: application/json' -X GET http://localhost:4567/api/accounts/

### Response

    [{"id":"7a0feb7e-463a-40fc-9b9c-3820e65fca51","accountOwnerName":"vinoth kumar","accountBalance":0.00}]


## Get a specific Account

### Request

`GET /api/accounts/:id`

    curl -H 'Accept: application/json' -X GET http://localhost:4567/api/accounts/7a0feb7e-463a-40fc-9b9c-3820e65fca51

### Response

    {"id":"7a0feb7e-463a-40fc-9b9c-3820e65fca51","accountOwnerName":"vinoth kumar","accountBalance":0.00}

## Get a non-existent Account

### Request

`GET /api/accounts/:id`

    curl -H 'Accept: application/json' -X GET http://localhost:4567/api/accounts/7a0feb7e

### Response

    Account doesn't exist. Please check the account number

## Credit amount to a specific account

### Request

`POST /api/accounts/credit`

    curl -d '{"id":"7a0feb7e-463a-40fc-9b9c-3820e65fca51","creditAmount":"500"}' -H "Content-Type: application/json" -X POST http://localhost:4567/api/accounts/credit

### Response

    Account credited successfully

## Transfer money between accounts

### Request

`POST /api/accounts/transfer`

    curl -d '{"senderId":"7a0feb7e-463a-40fc-9b9c-3820e65fca51", "receiverId":"ad9393c2-cce0-4f3c-8011-ae4e256b49bf", "amount":"25"}' -H "Content-Type: application/json" -X POST http://localhost:4567/api/accounts/transfer

### Response

    Amount transferred successfully

