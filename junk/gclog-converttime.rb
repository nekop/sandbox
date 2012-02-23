#!/usr/bin/env ruby

# todo: use optparse and implement delta option

delta = 0

while gets do
  if /^(\d+)\.(\d{3}):/ =~ $_.chomp! then
    totalsec = $1.to_i
    totalsec += delta
    while totalsec < 0 do totalsec = totalsec + (24 * 60 * 60) end
    while (24 * 60 * 60) <= totalsec do totalsec = totalsec - (24 * 60 * 60) end
    hour = totalsec / 60 / 60
    min = totalsec / 60 % 60
    sec = totalsec % 60
    puts "#{sprintf("%02d", hour)}:#{sprintf("%02d", min)}:#{sprintf("%02d", sec)}.#{$2} #{$_}"
  else
    puts $_
  end
end
