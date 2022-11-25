Job4j_url_shortcut is an educational project based on RESTful API. 
Sites-clients after registration can convert web-links into some code. 
This code can be sent to the server by anyone, and they will be redirected to 
the original link. The project uses the JWT authorization.

The project is built with the use of these technologies: Spring Boot 2.7.6,
spring-boot-starter-oauth2-resource-server, spring-boot-starter-data-jpa,
spring-boot-starter-web, liquibase 4.17.2, json-path 2.7.0. It uses PostgreSQL 42.3.7 
and h2database for tests.

To run the project you should use Java 17, Maven 3.8, PostgreSQL 42.3.7. First, create
a new database with the command '''create database url_shortcut;'''. Then run the "main" method in the 
class Job4jUrlShortcutApplication.

You can contact the author at kamyshen@gmail.com