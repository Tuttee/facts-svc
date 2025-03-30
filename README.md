#Getting started with Fact Service part of Cacti Encyclopedia

1. In application.properties configure spring.kafka.bootstrap-servers with your kafka address. The one created for the Cacti-Encyclopedia app.
2. Enter credentials for database use one of the following options:
    a. edit the application.properties file with your credentials
    b. use MYSQL_USER and MYSQL_PASS environment variables to enter credentials
3. Initially start the app without importing data.sql, so that the DB can be created
4. After first start, import the data.sql. To import, add spring.sql.init.mode=always to the application.properties file.
5. After import, change always to never.
