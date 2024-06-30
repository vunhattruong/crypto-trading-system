# Getting Started

### Project structure

- This project is using Clean Architecture template with some adjustments to make it suitable for the use case.
- Key concepts:
    - Any layer cannot reference, as well as know nothing about the layer above it
    - `usecase` & `domain` are the core of application, business logic should be defined in here, with minimal or no
      external libraries,dependencies.

It is a Maven project, and Java is used as the language.
Java 17 is set as the compatible version.

The framework used is Spring Boot.

API collections are available for you to quickly test the API:

Postman API collection: ~\crypto-trading-system\src\main\resources\Crypto.postman_collection.json
```
crypto-trading-system
│   README.md 
│   ...
└───domain (Core)
│   └───client
│   └───entity
│   └───exception
│   └───model
│   
└───usecase (Core)
│   └───getBookTicker
│   └───categorize
│   └───...
│
└───infra
│   └───consumer
│   └───producer
│   └───http
│   └───persistence
│
└───presentation
│   └───rest
│   └───...
│
```

## Build and Test

1. mvn clean install
2. Start project using IDE
3. Go to http://localhost:8080/h2-console with default credential(sa/password)
4. Run script to add data sample: ~\crypto-trading-system\src\main\resources\data\h2data.sql
5. Download postman API then run the endpoint:
   - GET - Latest price by currencyPair: localhost:8080/api/v1/prices/latest?currencyPair=BTCUSDT
   - POST - Execute trade: localhost:8080/api/v1/trades/execute
   - GET -Get user wallet: localhost:8080/api/v1/wallets?userId=1
   - GET -Get trade history by user: localhost:8080/api/v1/trades/history?userId=1

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.3.1/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.3.1/maven-plugin/reference/html/#build-image)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.3.1/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)

### Guides

The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the
parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

