#!/usr/bin/env ruby

# %h %l %u %t %D "%r" %s %b

class Stat
  attr_reader :total, :count, :slow_count
  def initialize(url)
    @url = url
    @count = 0
    @total = 0
    @slow_count = 0
  end
  def put(ms)
    @count = @count + 1
    @total = @total + ms
    @slow_count = @slow_count + 1 if ms > 1000
  end
  def average
    @total.to_f / @count.to_f
  end
end

stats = {}

while gets do
  /.+\d{4}\] (\d+) (?:GET|POST) (\S+)/ =~ $_.chomp!
  ms = $1.to_i
  url = $2.gsub(/\?.*/, "")
  stats[url] = Stat.new(url) if stats[url].nil?
  stats[url].put(ms)
  # puts $_ if ms > 1000
end

stats.keys.each do |url|
  puts "#{url} - #{stats[url].average}, #{stats[url].total}, #{stats[url].count}, #{stats[url].slow_count}"
end
