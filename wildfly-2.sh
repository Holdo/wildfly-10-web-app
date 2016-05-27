export JBOSS_HOME="`pwd`/wildfly-10.0.0.Final-2"

rm -rf $JBOSS_HOME/standalone/data
rm -rf $JBOSS_HOME/standalone/tmp
rm -rf $JBOSS_HOME/standalone/deployments
mkdir $JBOSS_HOME/standalone/deployments

cp standalone-app-conf.xml $JBOSS_HOME/standalone/configuration/standalone-full-ha.xml
cp ui/target/ui-1.0-SNAPSHOT.war wildfly-10.0.0.Final-2/standalone/deployments

sh $JBOSS_HOME/bin/standalone.sh -c standalone-full-ha.xml -Djboss.node.name=`whoami`-node2 -Djboss.socket.binding.port-offset=100