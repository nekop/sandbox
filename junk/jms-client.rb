# jruby -classpath $JBOSS_HOME/client/jbossall-client.jar jms-client.rb

require 'java'

$providerURL = "localhost:1099"
$connectionFactoryJNDIName = "ConnectionFactory"
$destinationName = "queue/testQueue"
$clientID = "jms_client"
$isSender = ARGV.include?("-s")
$message = "hello"
$appendCountToMessage = true
$count = 2000
$sendInterval = 0
$receiveWait = 5000

class CloseConnectionExceptionListener
  import javax.jms.ExceptionListener
  def initialize(conn)
    @conn = conn
  end
  def onException(ex)
    println("ExceptionListener#onException(): " + Thread.currentThread().getName())
    ex.printStackTrace()
    begin
      @conn.close()
    rescue
      # ignore
    end
  end
end

class JmsClient

  def run
    props = java.util.Properties.new()
    props.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY,
              "org.jnp.interfaces.NamingContextFactory")
    props.put(javax.naming.Context.URL_PKG_PREFIXES,
              "jboss.naming:org.jnp.interfaces")
    props.put(javax.naming.Context.PROVIDER_URL, $providerURL)
    context = javax.naming.InitialContext.new(props)
    cf = context.lookup($connectionFactoryJNDIName)
    destination = context.lookup($destinationName)

    conn = cf.createConnection()
    begin
      conn.setClientID($clientID)
      conn.setExceptionListener(CloseConnectionExceptionListener.new(conn))
      session =
        conn.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE)
      if $isSender then
        producer = session.createProducer(destination)
        $count.times do |i|
          puts "send:" + i.to_s
          if $appendCountToMessage then
            producer.send(session.createTextMessage($message + i.to_s))
          else
            producer.send(session.createTextMessage($message))
          end
          Thread.sleep($sendInterval) if $sendInterval > 0
        end
        producer.close()
      else
        conn.start()
        consumer = session.createConsumer(destination)
        $count.times do |i|
          puts "receive:" + i.to_s
          message = consumer.receive($receiveWait)
          puts message
        end
        consumer.close()
      end
      session.close()
    ensure
      begin
        conn.close()
      rescue
        # ignore
      end
    end
  end

end

JmsClient.new.run
