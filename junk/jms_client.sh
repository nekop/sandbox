#!/bin/sh

#JBOSS_HOME=~/usr/local/jboss-eap-4.3_CP03/jboss-as
#JBOSS_HOME=~/usr/src/JBPAPP_4_3_0_GA_CP04/build/output/jboss-4.3.0.GA_CP04
#JBOSS_HOME=~/jboss
JBOSS_HOME=~/usr/local/old/jboss-eap-4.2_CP07/jboss-as

CLASSPATH=.
#CLASSPATH=$CLASSPATH:$JBOSS_HOME/server/remoting/lib/jboss-remoting.jar
CLASSPATH=$CLASSPATH:$JBOSS_HOME/client/jbossall-client.jar
CLASSPATH=$CLASSPATH:$JBOSS_HOME/client/log4j.jar
CLASSPATH=$CLASSPATH:$JBOSS_HOME/client/javassist.jar
CLASSPATH=$CLASSPATH:$JBOSS_HOME/server/all/deploy/jboss-aop-jdk50.deployer/jboss-aop-jdk50.jar
CLASSPATH=$CLASSPATH:$JBOSS_HOME/server/all/deploy/jboss-aop-jdk50.deployer/trove.jar

JAVA_OPTS="-server -Xss128k -Xms512m -Xmx512m"

JAVA_OPTS=$JAVA_OPTS groovy -classpath $CLASSPATH jms_client.groovy $*
