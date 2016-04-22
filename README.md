# WildFly 10 Demo Web App

run server:
`./wildfly.sh`

kill server (in case of errors):
`./wildfly-kill.sh`

#### Arquillian
To run arquillian tests, you must have wildfly up and running. We use remote connection.

All arquillian tests should end with AQ.java (i.e. DryTestAQ.java). Maven ignores these tests when `mvn clean install`.
Maven failsafe plugin is used to run arquillian tests (failsafe runs after the package goal).

To run arquillian tests, use profile "aq" `mvn clean verify -Paq`
