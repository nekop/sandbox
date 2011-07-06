#!/usr/bin/env ruby

require 'time'

from = /topLevelPrepare/
to = /topLevelCommit|topLevelAbort/
date_re = /\d\d\d\d-\d\d-\d\d \d\d:\d\d:\d\d/

start_time = nil

while gets do
  if $_ =~ from then
    $_ =~ date_re
    start_time = Time.parse($&)
  end                  
  if $_ =~ to and not start_time.nil? then
    $_ =~ date_re
    end_time = Time.parse($&)
    result = end_time - start_time
    start_time = nil
    puts $_ if result > 1
  end
end

