### Build

~~~
$ mvn clean package
~~~

or

~~~
$ mvn clean install
~~~

### Access the application

The application will be running at <http://localhost:8080/drools-test/hello>

You can specify "user" as a query parameter like:

~~~
$ curl http://localhost:8080/drools-test/hello?user=drools
~~~


For example:

~~~
$ curl http://localhost:8080/drools-test/hello
<html>
 <head>
  <title>DroolsExampleServlet</title>
 </head>
 <body>
 <h1>
   message = Goodbye cruel world
 </h1>
 </body>
</html>
~~~
~~~
$ curl http://localhost:8080/drools-test/hello?user=drools
<html>
 <head>
  <title>DroolsExampleServlet</title>
 </head>
 <body>
 <h1>
   message = Goodbye cruel drools
 </h1>
 </body>
</html>
~~~

And you can see the log messages like the following in server.log:

~~~
15:57:44,022 INFO  [com.sample.DroolsExampleServlet] (default task-1) doGet() is invoked
15:57:44,030 INFO  [stdout] (default task-1) KieSession[2] : Hello, world
15:57:44,034 INFO  [stdout] (default task-1) KieSession[2] : Goodbye cruel world
15:57:51,216 INFO  [com.sample.DroolsExampleServlet] (default task-1) doGet() is invoked
15:57:51,221 INFO  [stdout] (default task-1) KieSession[3] : Hello, drools
15:57:51,230 INFO  [stdout] (default task-1) KieSession[3] : Goodbye cruel drools
~~~
