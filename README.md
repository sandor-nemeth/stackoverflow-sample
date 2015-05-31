# Sample project for [this question](http://stackoverflow.com/questions/30555435/mysql-jdbc-web-program-error-in-db-config)

To run with H2 in-memory database: 

```
mvn clean package tomcat7:run -Ph2
```

To run with MySQL:

```
mvn clean package tomcat7:run -Pmysql
```

The resource files are located in src/test/resources/tomcat-*-context.xml 