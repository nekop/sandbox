#!/bin/sh

JBOSS4_HOME=/home/nekop/jboss4309

CLASSPATH=./target/classes
CLASSPATH=$CLASSPATH:$JBOSS4_HOME/client/jbossall-client.jar

java -classpath $CLASSPATH jp.programmers.examples.jmx.client.JmxClient
