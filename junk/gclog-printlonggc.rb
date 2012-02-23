#!/usr/bin/env ruby

THRESHOLD = 10.0

while gets
  /(\d+\.\d+) secs/ =~ $_
  puts "#{ARGF.filename}:#{$.}: #{$_}" if $& and $1.to_f > THRESHOLD
end
