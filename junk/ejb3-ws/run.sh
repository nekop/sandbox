#!/bin/sh

JBOSS_HOME=/home/nekop/jboss
JBOSS_SERVER_CONFIG=all

CLASSPATH=./target/classes
CLASSPATH=$CLASSPATH:$JBOSS_HOME/client/jbossall-client.jar
CLASSPATH=$CLASSPATH:$JBOSS_HOME/client/log4j.jar
CLASSPATH=$CLASSPATH:$JBOSS_HOME/client/jbossws-client.jar
CLASSPATH=$CLASSPATH:$JBOSS_HOME/client/jbossws-common.jar
CLASSPATH=$CLASSPATH:$JBOSS_HOME/client/jbossws-framework.jar
CLASSPATH=$CLASSPATH:$JBOSS_HOME/client/jbossws-jboss42.jar
CLASSPATH=$CLASSPATH:$JBOSS_HOME/client/jbossws-spi.jar

java -Djava.endorsed.dirs=$JBOSS_HOME/lib/endorsed/ -classpath $CLASSPATH jp.programmers.examples.ejb3.slsb.HelloSLSBWSClient
