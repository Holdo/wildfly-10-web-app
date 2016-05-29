export JBOSS_HOME="`pwd`/wildfly-10.0.0.Final"

rm -rf $JBOSS_HOME/domain/data
rm -rf $JBOSS_HOME/domain/tmp
rm -rf $JBOSS_HOME/domain/deployments
mkdir $JBOSS_HOME/domain/deployments

cp domain.xml $JBOSS_HOME/domain/configuration/domain.xml
cp host.xml $JBOSS_HOME/domain/configuration/host.xml
# cp ui/target/ui-1.0-SNAPSHOT.war wildfly-10.0.0.Final/domain/deployments

sh $JBOSS_HOME/bin/domain.sh