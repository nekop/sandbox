require 'java'

wait_futures = true
num_of_members = 8

puts "==================== start"

# jdg import

JDG_LIB_DIR="/home/tkimura/usr/local/jboss-datagrid-6.2.1-library/"

artifact_names = ["infinispan-core",
                  "infinispan-commons",
                  "jboss-logging",
                  "jboss-logmanager",
                  "jboss-marshalling",
                  "jboss-marshalling-river",
                  "jboss-transaction-api_1.1_spec",
                  "jgroups"]

artifact_names.each do |n|
  Dir["#{JDG_LIB_DIR}/**/#{n}-*.jar"].each { |jar| require jar }
end

# lib dir import

LIB_DIR = nil
lib_dir = LIB_DIR || "./lib"
Dir["#{lib_dir}/*.jar"].each { |jar| require jar }

java_import "org.infinispan.manager.DefaultCacheManager"
java_import "org.infinispan.configuration.global.GlobalConfigurationBuilder"
java_import "org.infinispan.configuration.cache.Configuration"
java_import "org.infinispan.configuration.cache.ConfigurationBuilder"
java_import "org.infinispan.configuration.cache.CacheMode"

cache_manager_list = []
cache_list = []

# setup
puts "JRUBY_OPTS=" + ENV["JRUBY_OPTS"]

num_of_members.times do
  # defaultTransport() actually creates the transport instance, so the global_config cannot be reused!
  global_config = 
    GlobalConfigurationBuilder.new()
    .globalJmxStatistics().allowDuplicateDomains(true)
    .transport()
    .defaultTransport()
    .build()
  default_cache_config =
    ConfigurationBuilder.new()
    .clustering()
    .cacheMode(CacheMode::DIST_ASYNC)
    .build()
  cache_manager = DefaultCacheManager.new(global_config, default_cache_config)
  cache_manager_list << cache_manager
  cache = cache_manager.getCache()
  cache_list << cache
end

# benchmark

futures = []
cache = cache_list.first()
num_of_entries = 2 * 1000 * 1000
key = "key"
value = "0" * 1024 # 1KB string
puts "========== putAsync start"
put_start_time = java.lang.System.currentTimeMillis()
num_of_entries.times do |i|
  f = cache.putAsync(key + i.to_s, value)
  futures << f if wait_futures and i > num_of_entries - 100
  puts i if i % 100000 == 0
end
put_end_time = java.lang.System.currentTimeMillis()
puts "========== putAsync end"
puts put_end_time - put_start_time
if wait_futures then
  futures.each do |f|
    f.get()
  end
  futures = nil
else
  sleep 10
end
put_end_time = java.lang.System.currentTimeMillis()
puts "========== put phase finished"
puts put_end_time - put_start_time

# teardown

cache_manager_list.reverse.each do |cache_manager|
  cache_manager.getCache().stop()
  cache_manager.stop()
  while true do
    rehash = false
    cache_manager_list.each do |cm|
      if cm.isDefaultRunning() then
        begin
          rehash |= cm.getCache().getDistributionManager().isRehashInProgress()
        rescue # ignore exception, cm.isDefaultRunning() is not reliable here
        end
      end
    end
    sleep 1 if rehash
    break unless rehash
  end
end
cache_manager_list = nil
cache_list = nil

puts "==================== finished"


