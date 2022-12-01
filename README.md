### URL SHORTCUT

### About
Job4j_url_shortcut is an educational project based on RESTful API. 
Sites-clients after registration can convert web-links into some code. 
This code can be sent to the server by anyone, and they will be redirected to 
the original link. The project uses JWT authorization.

### Used Technologies
The project is based on these technologies: Spring Boot 2.7.6,
spring-boot-starter-oauth2-resource-server, spring-boot-starter-data-jpa,
spring-boot-starter-web, liquibase 4.17.2, json-path 2.7.0. It uses PostgreSQL 42.3.7 
and h2database for tests.

### Getting Started
To run the project you should use Java 17, Maven 3.8, PostgreSQL 42.3.7. First, create
a new database with the command
```shell
create database url_shortcut;
```
Then run the "main" method in the 
class Job4jUrlShortcutApplication in an IDE, or use
```shell
mvn spring-boot:run
```

### RESTful endpoints
- POST /registration (with the body {"site" : "`your_site`"}) to register.
The server responds with {"registration" : "true", "login": "`your_site`", "password" : "`generated_password`"}.
If this site has already been registered, then it should be {"registration" : "false", "login": "`your_site`"}.
- POST /login (with the body {"login" : "`your_site`", "password" : "`generated_password`"}).
The server sends back a response with Authorization: Bearer `generated_JWT` in headers. The client site is supposed to
save this JWT and use it in further requests in the headers.
- POST /convert (with the body {"url": "`your_web_link`"}). The server's response is {"code": "`code`"}. The code
is a randomly generated alphanumeric string that looks like "ue7PThYExL".
- GET /redirect/`code` (no authorization needed for this request). The server returns a response with one of the headers
being Location: `original_link` and status code 302. If the original link had no "http" prefix (like google.com), then
the server adds it to it (making it http://google.com) so that the client gets redirected to the original link right away.
- GET /statistic returns a JSON with all the links from this client with an amount of times each of them was requested.

### Contacts
You can contact me, Kamyshentsev Vasilii, at kamyshen@gmail.com.