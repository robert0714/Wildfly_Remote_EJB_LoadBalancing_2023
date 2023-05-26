# NodeSelector - EJB LoadBalancer Example
* source code: https://github.com/akquinet/jbosscc-as7-examples/tree/master/cluster-client-example
* description: https://blog.akquinet.de/2012/11/09/load-balancing-and-failover-of-remote-ejb-clients-in-eap6-and-jboss-as7/

## NodeSelector Migration

* https://access.redhat.com/solutions/3486221

## References
* [Official Document](https://docs.wildfly.org/26.1/Client_Guide.html#introduction)
  * [Client description-1](https://github.com/wildfly/wildfly/blob/main/docs/src/main/asciidoc/_elytron/Passwords.adoc)
  * [Client description-password](https://github.com/wildfly/wildfly-proposals/blob/main/elytron/ELY-816-masked-password.adoc)
  * [Client description-password-others](https://blogs.nologin.es/rickyepoderi/index.php?/archives/186-Masking-passwords-for-the-wildfly-config.xml-file.html)
  * [RedHat Official Document](https://access.redhat.com/documentation/zh-tw/red_hat_jboss_enterprise_application_platform/7.4/html/development_guide/configuring_clients)

# Local Test Scripts
```
mvn clean install
mvn -f cluster-example-client/pom.xml clean package spring-boot:repackage

java -jar cluster-example-client/target/cluster-example-client-1.0-SNAPSHOT.jar

java -Djboss.http.port=8011 \ 
-Djboss.bind.address=0.0.0.0 \
-jar cluster-example-server/target/cluster-example-server-bootable.jar

java -Djboss.http.port=8012 \
-Djboss.bind.address=0.0.0.0 \
-Djboss.management.http.port=9991 \
-jar cluster-example-server/target/cluster-example-server-bootable.jar 
```

# Istio Load Balance
https://istio.io/v0.7/docs/concepts/traffic-management/img/pilot/LoadBalancing.svg

![Alt text](https://istio.io/v1.1/docs/concepts/traffic-management/LoadBalancing.svg)
 
* https://istio.io/latest/docs/concepts/traffic-management/#destination-rule-example
* https://istiobyexample.dev/load-balancing/