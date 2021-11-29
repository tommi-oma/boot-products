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

>---
> The labs assume you have a Postgres server available. If not you can change the
> connection, and update the connection strings in the application.yaml file.
> Simplest is to use H2 also for development, in that case change the scope back to 
> *runtime* (and remove the Postgres dependency if so desired).
---

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


# Database setup

If you are using PostgreSQL, there are a few things that may help in getting started:
- Note that unless the PostgreSQL command line utilities are in the path (in Windows they typically
are not) you need to have a way to find them, so the examples here are run in Postgres' `bin` directory.
- Create the database before running the labs. Assuming the connection is to a DB named `productlabs`,
and you are using the default `postgres` user, then you can create the database:

```
	C:\Program Files\PostgreSQL\12\bin>createdb -p 5432 -U postgres productlabs
	Password:
	
	C:\Program Files\PostgreSQL\12\bin>	
```
- Accessing the database from command line is easiest with the `psql` command

```
	C:\Program Files\PostgreSQL\12\bin>psql -U postgres productlabs
	Password for user postgres:
	psql (12.9)
	Type "help" for help.
	
	productlabs=# \dt
	          List of relations
	 Schema |  Name   | Type  |  Owner   
	--------+---------+-------+----------
	 public | product | table | postgres
	(1 row)
	
	productlabs=# select count(*) from product;
	 count 
	-------
	     5
	(1 row)

	productlabs=# \q
	
	C:\Program Files\PostgreSQL\12\bin>		
```