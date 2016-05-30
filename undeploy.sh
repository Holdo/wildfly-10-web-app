export JBOSS_HOME="`pwd`/wildfly-10.0.0.Final"

sh $JBOSS_HOME/bin/jboss-cli.sh --connect --command="undeploy ui-1.0-SNAPSHOT.war"