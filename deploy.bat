SET JBOSS_HOME=%cd%\wildfly-10.0.0.Final
CALL %JBOSS_HOME%\bin\jboss-cli.bat --connect --command="deploy --all-server-groups %cd%\ui\target\ui-1.0-SNAPSHOT.war"