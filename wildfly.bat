SET JBOSS_HOME=%cd%\wildfly-10.0.0.Final
CALL %JBOSS_HOME%\bin\standalone.bat -c standalone-full-ha.xml
