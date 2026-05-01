CargoTrak third-party libraries
================================

The following JARs are expected at build time in this directory.
On bare metal, ops drops them in here from the shared library share.
In the Docker dev image, the Dockerfile wgets them from Maven Central.

JAR                                              Maven coordinates
-------------------------------------------------------------------------------
struts-1.3.10.jar                                org.apache.struts:struts-core:1.3.10
struts-taglib-1.3.10.jar                         org.apache.struts:struts-taglib:1.3.10
struts-tiles-1.3.10.jar                          org.apache.struts:struts-tiles:1.3.10
commons-beanutils-1.8.0.jar                      commons-beanutils:commons-beanutils:1.8.0
commons-chain-1.2.jar                            commons-chain:commons-chain:1.2
commons-digester-2.1.jar                         commons-digester:commons-digester:2.1
commons-validator-1.3.1.jar                      commons-validator:commons-validator:1.3.1
commons-fileupload-1.2.2.jar                     commons-fileupload:commons-fileupload:1.2.2
commons-io-2.0.1.jar                             commons-io:commons-io:2.0.1
commons-lang-2.6.jar                             commons-lang:commons-lang:2.6
commons-collections-3.2.1.jar                    commons-collections:commons-collections:3.2.1
commons-codec-1.4.jar                            commons-codec:commons-codec:1.4
commons-logging-1.1.1.jar                        commons-logging:commons-logging:1.1.1
commons-pool-1.5.6.jar                           commons-pool:commons-pool:1.5.6
commons-dbcp-1.4.jar                             commons-dbcp:commons-dbcp:1.4
log4j-1.2.17.jar                                 log4j:log4j:1.2.17
slf4j-api-1.6.1.jar                              org.slf4j:slf4j-api:1.6.1
slf4j-log4j12-1.6.1.jar                          org.slf4j:slf4j-log4j12:1.6.1
spring-core-3.0.7.RELEASE.jar                    org.springframework:spring-core:3.0.7.RELEASE
spring-beans-3.0.7.RELEASE.jar                   org.springframework:spring-beans:3.0.7.RELEASE
spring-context-3.0.7.RELEASE.jar                 org.springframework:spring-context:3.0.7.RELEASE
spring-context-support-3.0.7.RELEASE.jar         org.springframework:spring-context-support:3.0.7.RELEASE
spring-aop-3.0.7.RELEASE.jar                     org.springframework:spring-aop:3.0.7.RELEASE
spring-expression-3.0.7.RELEASE.jar              org.springframework:spring-expression:3.0.7.RELEASE
spring-asm-3.0.7.RELEASE.jar                     org.springframework:spring-asm:3.0.7.RELEASE
spring-jdbc-3.0.7.RELEASE.jar                    org.springframework:spring-jdbc:3.0.7.RELEASE
spring-orm-3.0.7.RELEASE.jar                     org.springframework:spring-orm:3.0.7.RELEASE
spring-tx-3.0.7.RELEASE.jar                      org.springframework:spring-tx:3.0.7.RELEASE
spring-web-3.0.7.RELEASE.jar                     org.springframework:spring-web:3.0.7.RELEASE
spring-webmvc-3.0.7.RELEASE.jar                  org.springframework:spring-webmvc:3.0.7.RELEASE
hibernate-core-3.6.10.Final.jar                  org.hibernate:hibernate-core:3.6.10.Final
hibernate-commons-annotations-3.2.0.Final.jar    org.hibernate:hibernate-commons-annotations:3.2.0.Final
hibernate-jpa-2.0-api-1.0.1.Final.jar            org.hibernate.javax.persistence:hibernate-jpa-2.0-api:1.0.1.Final
javassist-3.12.0.GA.jar                          javassist:javassist:3.12.0.GA
antlr-2.7.6.jar                                  antlr:antlr:2.7.6
dom4j-1.6.1.jar                                  dom4j:dom4j:1.6.1
jta-1.1.jar                                      javax.transaction:jta:1.1
mysql-connector-java-5.1.36.jar                  mysql:mysql-connector-java:5.1.36
quartz-1.8.6.jar                                 org.quartz-scheduler:quartz:1.8.6
mail-1.4.7.jar                                   javax.mail:mail:1.4.7
activation-1.1.1.jar                             javax.activation:activation:1.1.1
itext-2.1.7.jar                                  com.lowagie:itext:2.1.7
poi-3.7.jar                                      org.apache.poi:poi:3.7
poi-ooxml-3.7.jar                                org.apache.poi:poi-ooxml:3.7
axis-1.4.jar                                     org.apache.axis:axis:1.4
axis-jaxrpc-1.4.jar                              org.apache.axis:axis-jaxrpc:1.4
axis-saaj-1.4.jar                                org.apache.axis:axis-saaj:1.4
axis-wsdl4j-1.5.1.jar                            wsdl4j:wsdl4j:1.5.1
dwr-2.0.10.jar                                   uk.ltd.getahead:dwr:2.0.10  (or org.directwebremoting)
json-20090211.jar                                org.json:json:20090211
jstl-1.1.2.jar                                   javax.servlet:jstl:1.1.2
standard-1.1.2.jar                               taglibs:standard:1.1.2

NOTE: Do not check binary JARs into source control.
      Ops keeps a tarball at //fileshare/cargotrak/lib-3.4.2.tgz that
      matches this exact list. Refresh from there before each release.
