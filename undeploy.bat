SET JBOSS_HOME=%cd%\wildfly-10.0.0.Final
CALL %JBOSS_HOME%\bin\jboss-cli.bat --connect --command="undeploy --all-relevant-server-groups ui-1.0-SNAPSHOT.war"