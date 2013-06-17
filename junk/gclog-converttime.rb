#!/usr/bin/env ruby

require 'optparse'
require 'time'

Version = "2.0"

option_hash = {}
OptionParser.new { |opt|
  opt.on('-b base datetime', '--base-datetime') { |v| option_hash[:b] = v }
  opt.parse!(ARGV)
}

base_datetime_s = option_hash[:b]
if base_datetime_s.nil? then
  raise ArgumentError, "-b <base datetime> option is required"
end
base_datetime = Time.parse(base_datetime_s)
delta = base_datetime.to_f

while gets do
  if /^(\d+)\.(\d{3}):/ =~ $_.chomp! then
    gc_msec = "#{$1}.#{$2}".to_f
    gc_datetime = gc_msec + delta
    t = Time.at(gc_datetime)
    actual_datetime = t.strftime("%Y-%m-%d %H:%M:%S.%L")
    puts "#{actual_datetime} #{$_}"
  else
    puts $_
  end
end
