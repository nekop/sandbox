#
# Put this script under $JDG_HOME/client/java dir.
# $ jruby jruby-hotrod.rb
#

require 'java'
require 'optparse'

DEFAULT_SERVER = "localhost:11222"
DEFAULT_KEY = "key"
DEFAULT_VALUE = "value"
DEFAULT_COUNT = 1
DEFAULT_OPERATION = "help"

class SimpleHotrodClient
  attr_accessor :operation, :server_list, :key, :value, :count

  def initialize(operation, server_list, key, value, count)
    @operation = operation
    @server_list = server_list
    @key = key
    @value = value
    @count = count
  end

  def with_cache(&block)
    Dir["*.jar"].each { |jar| require jar }
    java_import "org.infinispan.client.hotrod.impl.ConfigurationProperties"
    java_import "org.infinispan.client.hotrod.RemoteCacheManager"
    java_import "java.util.Properties"

    props = Properties.new
    props.put(ConfigurationProperties::SERVER_LIST, @server_list)
    cache_manager = RemoteCacheManager.new(props)
    cache = cache_manager.getCache

    yield cache

    cache_manager.stop
  end

  def execute
    case @operation
    when "get"
      with_cache { |cache|
        @count.times do |i|
          puts "#{@key + i.to_s} = #{cache.get(@key + i.to_s)}"
        end
      }
    when "put"
      with_cache { |cache|
        @count.times do |i|
          cache.put(@key + i.to_s, @value)
        end
      }
    when "getbulk"
      with_cache { |cache|
        puts cache.getBulk
      }
    when "keyset"
      with_cache { |cache|
        puts cache.keySet
      }
    when "clear"
      with_cache { |cache|
        cache.clear
      }
    when "size"
      with_cache { |cache|
        puts cache.size
      }
    when "clear"
      with_cache { |cache|
        cache.clear
      }
    else
      puts $opt.help
      exit 1
    end
  end
end

def main
  option_hash = {}
  $opt = OptionParser.new { |opt|
    opt.banner = "Usage: jruby-hotrod.rb [options] operation"
    opt.on('-s server list', '--server-list') { |v| option_hash[:s] = v }
    opt.on('-k key', '--key') { |v| option_hash[:k] = v }
    opt.on('-v value', '--value') { |v| option_hash[:v] = v }
    opt.on('-c count', '--count') { |v| option_hash[:c] = v }
    opt.parse!(ARGV)
  }

  operation = ARGV.shift || DEFAULT_OPERATION
  server_list = option_hash[:s] || DEFAULT_SERVER
  key = option_hash[:k] || DEFAULT_KEY
  value = option_hash[:v] || DEFAULT_VALUE
  count = option_hash[:c] || DEFAULT_COUNT

  client = SimpleHotrodClient.new(operation, server_list, key, value, count)
  client.execute
end

main
