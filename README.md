# Getting Started

### Project structure

- This project is using Clean Architecture template with some adjustments to make it suitable for the usecase.
- Key concepts:
    - Any layer cannot reference, as well as know nothing about the layer above it
    - `usecase` & `domain` are the core of application, business logic should be defined in here, with minimal or no
      external libraries,dependencies.

```
wf-identification
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

### Code styles & formatting

- Please import the file `CodeStyle.xml` to your IDEA before working on the project.
- For Intellij, choose `File > Setting > Code Style > Scheme > Import Scheme > Intellij IDEA code style XML` and choose
  the file to import

## Build and Test

TODO: Describe and show how to build your code and run the tests.[TBD]

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

