#!/usr/bin/env ruby

# Prints time gaps (possible stop-the-world GC) from standard log.
# The supported date format is "2011-10-01 11:22:33,444"

require "time"

TIME_RE = /^(\d{4}-\d\d-\d\d \d\d:\d\d:\d\d,\d{3})/
GAP_THRESHOLD_SEC = 10

prev_time = Time.now

while gets
  if TIME_RE =~ $_ then
    time = Time.parse($1)

    if time - prev_time > GAP_THRESHOLD_SEC then
      time_s = time.strftime("%Y-%m-%d %H:%M:%S") + ",#{time.usec / 1000}"
      prev_time_s = prev_time.strftime("%Y-%m-%d %H:%M:%S") + ",#{prev_time.usec / 1000}"
      gap = time - prev_time
      puts "gap=#{gap}, prev_time=#{prev_time_s}, time=#{time_s}"
    end
    prev_time = time
  end
end
