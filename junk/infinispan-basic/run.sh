#!/bin/sh

JRUBY_OPTS="$JRUBY_OPTS -J-Xloggc:gc.log -J-XX:+PrintGCDetails -J-XX:+PrintGCDateStamps"
JRUBY_OPTS="$JRUBY_OPTS -J-Djava.net.preferIPv4Stack=true -J-Djgroups.bind_addr=127.0.0.1 -J-Dlogging.configuration=file:logging.properties -J-Djava.util.logging.manager=org.jboss.logmanager.LogManager -J-Dmax.list.print_size=1024"
JRUBY_OPTS="$JRUBY_OPTS -J-classpath /home/tkimura/usr/local/jboss-datagrid-6.2.1-library/lib/jboss-logmanager-1.4.3.Final-redhat-1.jar"

export JRUBY_OPTS

jruby infinispan-basic.rb
