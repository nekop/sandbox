require 'java'

require 'client/jboss-client.jar'

java_import 'java.util.HashMap'
java_import 'javax.management.remote.JMXConnectorFactory'
java_import 'javax.management.remote.JMXConnector'
java_import 'javax.management.remote.JMXServiceURL'

host_name = "localhost"
port = 9999

jmx_service_url = JMXServiceURL.new("service:jmx:remoting-jmx://#{host_name}:#{port}")
puts jmx_service_url
environment = HashMap.new
jmx_connector = JMXConnectorFactory::connect(jmx_service_url, environment)
conn = jmx_connector.getMBeanServerConnection()
puts conn.getMBeanCount()
jmx_connector.close


