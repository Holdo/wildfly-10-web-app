# WildFly 10 Demo Web App

## Intro
This application serves artists who are willing to upload their audio track to our server, where reviewers might listen and comment the uploaded track. Administrator might manage the application via his own interface as well.
For more technical information please visit our [wiki](https://github.com/Holdo/wildfly-10-web-app/wiki).

### Running
run server:
`./wildfly.sh`

kill server (in case of errors):
`./wildfly-kill.sh`

You can also run the server in domain mode. Scripts are in the main directory.
WAR is always built in UI module inside target folder.

#### Arquillian
To run arquillian tests, you must have wildfly up and running. We use remote connection.

All arquillian tests should end with AQ.java (i.e. DryTestAQ.java). Maven ignores these tests when `mvn clean install`.
Maven failsafe plugin is used to run arquillian tests (failsafe runs after the package goal).

To run arquillian tests, use profile "aq" `mvn clean verify -Paq`

Typical workflow:

```
mvn clean install
./wildfly.sh
mvn clean verify -Paq
cd ui/
mvn wildfly:deploy
```
