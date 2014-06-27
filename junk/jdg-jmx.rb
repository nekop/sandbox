require 'java'

JDG_HOME="/home/nekop/jdg6"
require "#{JDG_HOME}/modules/system/layers/base/org/jboss/remoting-jmx/main/remoting-jmx-1.1.0.Final-redhat-1.jar"
require "#{JDG_HOME}/modules/system/layers/base/org/jboss/remoting3/main/jboss-remoting-3.2.16.GA-redhat-1.jar"
require "#{JDG_HOME}/modules/system/layers/base/org/jboss/logging/main/jboss-logging-3.1.2.GA-redhat-1.jar"
require "#{JDG_HOME}/modules/system/layers/base/org/jboss/xnio/main/xnio-api-3.0.7.GA-redhat-1.jar"
require "#{JDG_HOME}/modules/system/layers/base/org/jboss/xnio/nio/main/xnio-nio-3.0.7.GA-redhat-1.jar"
require "#{JDG_HOME}/modules/system/layers/base/org/jboss/marshalling/main/jboss-marshalling-1.4.2.Final-redhat-2.jar"
require "#{JDG_HOME}/modules/system/layers/base/org/jboss/marshalling/river/main/jboss-marshalling-river-1.4.2.Final-redhat-2.jar"

java_import 'java.util.HashMap'
java_import 'javax.management.remote.JMXConnectorFactory'
java_import 'javax.management.remote.JMXConnector'
java_import 'javax.management.remote.JMXServiceURL'

host_name = "localhost"
port = 9999

jmx_service_url = JMXServiceURL.new("service:jmx:remoting-jmx://#{host_name}:#{port}")
puts jmx_service_url
environment = HashMap.new
creds = ["admin", "admin123!"].to_java(:String)
environment.put(JMXConnector.CREDENTIALS, creds);
jmx_connector = JMXConnectorFactory::connect(jmx_service_url, environment)
conn = jmx_connector.getMBeanServerConnection()
puts conn.getMBeanCount()
jmx_connector.close
