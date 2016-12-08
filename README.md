# service-prototype
just spring-extension dummy service example

## to start 

##### get dependencies
* git clone https://github.com/mix-sloboda/spring-extension.git
* cd spring-extension; sbt publishLocal

#### add lo0 alias
* ifconfig lo0 alias 127.0.0.2
* ifconfig lo0 alias 127.0.0.3

##### generate fat jar (in current project)
* sbt assembly
* java -Dconfig.resource=master.conf -jar target/scala-2.11/service-1.0.jar
* java -Dconfig.resource=worker.conf -Dhost=127.0.0.2 -jar target/scala-2.11/service-1.0.jar
* java -Dconfig.resource=worker.conf -Dhost=127.0.0.3 -jar target/scala-2.11/service-1.0.jar
