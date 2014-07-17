require 'java'
Dir["./target/*.jar"].each { |jar| require jar }

java_import "java.util.Properties"
java_import "javax.naming.Context"
java_import "javax.naming.InitialContext"

p = Properties.new()
p.put("remote.connections", "default")
p.put("remote.connection.default.port", "4447")
p.put("remote.connection.default.host", "localhost")
p.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", "false")
p.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming")
p.put("org.jboss.ejb.client.scoped.context", true)
initial_context = InitialContext.new(p)

ear_name = "ee6-ejb-interfaces"
ejbjar_name = "ee6-ejb-interfaces"
ejb_name = "Hello"
interface_name = "com.github.nekop.examples.HelloRemote"

ejb_context = initial_context.lookup("ejb:")
begin
  bean = ejb_context.lookup("#{ear_name}/#{ejbjar_name}/#{ejb_name}!#{interface_name}")
  puts bean.hello("world")
ensure
  begin
    ejb_context.close
  rescue
    # no-op
  end
end
