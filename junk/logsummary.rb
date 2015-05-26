#!/usr/bin/env ruby
# encoding: utf-8

# TODOs:
#   - entry count
#   - Save whole exception

DATETIME_RE = /\d{4}[\/-]\d{2}[\/-]\d{2}[ -,:]\d{2}:\d{2}:\d{2}[ .,]\d{3}/
LOG_RE = /(#{DATETIME_RE}) (\w+) +\[([\w.]+)\] \((.*?)\) (.*)/

DEBUG = false

def debug(s)
  puts s if DEBUG
end    

class Entry
  attr_accessor :line, :lineno, :time_s, :level, :logger, :thread, :message, :message_array

  def initialize(time_s, level, logger, thread, message)
    @time_s = time_s
    @level = level
    @logger = logger
    @thread = thread
    @message = message
    @message_array = message.split
    @stacktraces = []
  end

  def similar?(other)
    return false if @logger != other.logger
    diff = @message_array - other.message_array
    similarity = diff.size / @message_array.size.to_f
    return similarity < 0.3
  end
end

known_entries = Array.new
known_entry_count = Hash.new

def should_log?(e)
  # Exclude list
  return false if e.message.start_with?("JGRP000011:")
  # Include list
  return true if e.level =~ /WARN|ERROR/ || 
                 e.message.start_with?("ISPN000093: Received new, MERGED cluster view:") ||
                 e.message.start_with?("ISPN000094: Received new cluster view:") ||
                 e.message.start_with?("GMS: address=") || # GMS debug print
                 e.message.start_with?("JBoss Modules version ") || # first log when boot
                 e.message.start_with?("JBAS015874:") || # normal start
                 e.message.start_with?("JBAS015875:") || # error start
                 e.message.start_with?("JBAS015950:") # shutdown
  # Otherwise false
  return false
end

while line = gets
  line.chomp!
  if line =~ LOG_RE then
    time_s = $1
    level = $2
    logger = $3
    thread = $4
    message = $5

    e = Entry.new(time_s, level, logger, thread, message)
    if should_log?(e)
      e.line = line
      e.lineno = ARGF.lineno
      exists = known_entries.find do |known|
        e.similar? known
      end
      if exists then
        known_entry_count[exists] = known_entry_count[exists] + 1
      else
        known_entries << e
        known_entry_count[e] = 1
        puts line
      end
    end

  end
end
