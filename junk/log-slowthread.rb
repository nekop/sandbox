#!/usr/bin/env ruby

# Format is:
# 2013-01-01 01:02:03,004 DEBUG [com.example.foo] (thread-name) message

require 'time'

SERVER_LOG_RE = /(\d{4}-\d\d-\d\d \d\d:\d\d:\d\d,\d{3}) (\w+) \[([\w.]+)\] \(([\w-.]+)\) (.*)/

THRESHOLD = 0.200
IGNORE_THRESHOLD = 1.000

last_logs = {}
last_times = {}

while gets do
  if $_ =~ SERVER_LOG_RE then
    time_s = $1
    level = $2
    logger = $3
    thread = $4
    message = $5
#    puts "#{$1} #{$2} #{$3} #{$4} #{$5}"
    time = Time.parse(time_s)
    last_time = last_times[thread] || time
    gap = time - last_time
    if gap > THRESHOLD then
      puts gap
      puts last_logs[thread]
      puts $_
    end
    last_logs[thread] = $_
    last_times[thread] = time
  end                  
end
