# Mogura - find grep tool which makes raw logs human friendly.
#
# Work in progress.
# 
# Mogura is find-grep tool which supports pre-defined pattern,
# multiple match keywords and/or multiple exclude keywords, timestamp
# query ... everything useful for logs analysis.
#
# Features:
# 
# - pre-defined patterns
# - filter out not interested logs
# - how many times same log appeared
# - search corresponding start and end
# 

# Search all files under current directory with specified pattern
#   $ mogura jboss
# grep mode, only "jgroups"
#   $ mogura -g jgroups
# Multiple grep keywords
#   $ mogura -g jgroups -g infinispan
# Also regexp
#   $ mogura -g 'jgroups|infinispan'
# grep with exclude
#   $ mogura -g jgroups -v NAKACK
# find-grep mode, Search all files with specific keyword (-i to ignore case), allow same option multiple times
#   $ mogura -g [pattern] [-i] [-v excludes]
# Search **/server** files, jgroups related logs, only 100 lines of each file
#   $ mogura -f server -n jgroups -h 100
# Sort by timestamps
#   $ mogura -t

require 'optparse'

# Impl note
# For grep -v, reject or try "(array - array.grep)"

def main

  OptionParser.new { |opt|
    opt.banner = "Usage: #{$0} [options] <pattern>"
    opt.on('-f filename', '--filename') { |v| option_hash[:f] = v }
    opt.on('-n named pattern', '--named-pattern') { |v| option_hash[:n] = v }
    opt.on('-g grep pattern', '--grep-pattern') { |v| option_hash[:g] = v }
    opt.on('-i', '--ignore case') { |v| option_hash[:i] = v }
    opt.on('-v exclude pattern', '--exclude-pattern') { |v| option_hash[:v] = v }
    opt.on('-h head', '--head') { |v| option_hash[:h] = h }
    opt.on('--help', 'Prints this message and quit.') {
      puts parser.help
      exit 0
    }
    opt.parse!(ARGV)
  }

  mogura = Mogura.new
  mogura.perform

end


class Mogura
  attr_accessor :pattern, :filename_pattern, :includes, :excludes, :ignore_case, :head

  def perform
    @filename_pattern = "*" unless @filename_pattern
    puts @filename_pattern
    Dir["./**/#{@filename_pattern}"].select { |f| File.file?(f) }.each { |f| puts f } 
  end

end

main


