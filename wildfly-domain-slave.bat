SET JBOSS_HOME=%cd%\wildfly-10.0.0.Final
CALL %JBOSS_HOME%\bin\domain.bat --host-config host-slave.xml -Djboss.domain.master.address=127.0.0.1