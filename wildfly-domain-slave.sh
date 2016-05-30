export JBOSS_HOME="`pwd`/wildfly-10.0.0.Final"
sh $JBOSS_HOME/bin/domain.sh --host-config host-slave.xml -Djboss.domain.master.address=127.0.0.1