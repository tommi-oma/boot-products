#Products labs

The Products labs are used in the Database labs for Spring Boot development course.
Like others there are branches for different individual labs.

The Project was created using a Spring Starter project with the following
dependencies
- Developer Tools: Spring Boot DevTools
- I/O: Validation
- SQL: Spring Data JPA, H2 Database, PostgreSQL Driver
- Web: Spring Web, Spring Reactive Web

The H2 dependency is modified to be in the *test* scope.

> The labs assume you have a Postgres server available. If not you can change the
> connection, and update the connection strings in the application.yaml file.
> Simplest is to use H2 also for development, in that case change the scope back to 
> *runtime* (and remove the Postgres dependency if so desired).

In addition there is a dependency for SpringDoc to enable OpenAPI documentation.

```
	<dependency>
	    <groupId>org.springdoc</groupId>
	    <artifactId>springdoc-openapi-ui</artifactId>
	   	<version>1.5.12</version>
	</dependency>

```

And also reactor tests

```
	<dependency>
		<groupId>io.projectreactor</groupId>
		<artifactId>reactor-test</artifactId>
		<scope>test</scope>
	</dependency>
```


Remember to use profiles, lab assumes "dev" and "test". Modify the Run configuration and add
`-Dprofile=dev` for dev, and obviously `-Dprofile=test` for test profile.
