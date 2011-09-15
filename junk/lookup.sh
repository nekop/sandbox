#/bin/sh

OPTIONS="-J-classpath /home/nekop/jboss510/client/jbossall-client.jar:/home/nekop/jboss510/client/log4j.jar"
OPTIONS="$OPTIONS -J-Dlog4j.configuration=client-log4j.xml"



# OPTIONS="$OPTIONS -J-Dsun.rmi.transport.tcp.readTimeout=1000"
# OPTIONS="$OPTIONS -J-Dsun.rmi.transport.tcp.handshakeTimeout=1000"
# OPTIONS="$OPTIONS -J-Dsun.rmi.transport.connectionTimeout=1000"
# OPTIONS="$OPTIONS -J-Dsun.rmi.transport.logLevel=VERBOSE"
# OPTIONS="$OPTIONS -J-Dsun.rmi.transport.tcp.logLevel=VERBOSE"
# OPTIONS="$OPTIONS -J-Dsun.rmi.transport.tcp.responseTimeout=1000"
# OPTIONS="$OPTIONS -J-Dsun.rmi.transport.proxy.connectTimeout=1000"

jruby $OPTIONS lookup.jrb
