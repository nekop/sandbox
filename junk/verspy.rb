#!/usr/bin/env ruby

# Example command
# ruby verspy.rb -s /path/to/jboss-seam-src/src/main -p org.jboss.seam stacktrace.log
# ruby verspy.rb -s http://anonsvn.jboss.org/repos/seam/tags/JBPAPP_4_3_CP05_FP/src/main -p org.jboss.seam stacktrace.log

require 'optparse'
require 'open-uri'

Version = "2.0"

option_hash = {}
OptionParser.new { |opt|
  opt.on('-s source path', '--sourcepath') { |v| option_hash[:s] = v }
  opt.on('-p target package name', '--packagename') { |v| option_hash[:p] = v }
  opt.parse!(ARGV)
}

opt_source_path = option_hash[:s]
opt_package_name = option_hash[:p]

raise if opt_source_path.nil? or opt_package_name.nil?

opt_source_path.chop! if opt_source_path[-1] == '/'[0] # drop last slash

STACKTRACE_RE = /\s+at\s([^\(]+)\(([\w\.]+):(\d+)\)\s*$/

class StackTraceElement
  attr_accessor :fqcn, :package_name, :method_name, :source_filename, :source_linenum
  def self.parse(line)
    if line.match(STACKTRACE_RE) then
      e = StackTraceElement.new
      fqcn_method_name = $1
      e.source_filename = $2
      e.source_linenum = $3.to_i
      e.fqcn = fqcn_method_name[0...fqcn_method_name.rindex('.')] # drop method name
      e.method_name = fqcn_method_name[fqcn_method_name.rindex('.')+1..-1] # drop fqcn
      e.package_name = e.fqcn[0...e.fqcn.rindex('.')] # drop class name from fqcn
      e
    else
      nil
    end
  end
end


lines_done = []
prev_e = nil

ARGF.each do |line|
  e = StackTraceElement.parse(line)

  if e.nil? then
    prev_e = nil
    next
  end

  if e.package_name =~ /#{opt_package_name}/ then
    lines_done << line
    source_file = "#{opt_source_path}/#{e.package_name.gsub(/\./, '/')}/#{e.source_filename}"
    open(source_file) do |f|
      body = f.readlines
      target_line = body[e.source_linenum-1]

      suspicious = false
      if prev_e.nil? then
        # first line in the stack, it should contain "throw" keyword
        suspicious = true unless target_line =~ /^\s*throw\s/
      else
        if prev_e.method_name == "<init>" then
          # ctor, so it should contain "new" keyword
          suspicious = true unless target_line =~ /new\s/
        else
          # normal method, it should contain prev_e.method_name
          suspicious = true unless target_line =~ /#{prev_e.method_name}/
        end
      end
      if suspicious then
        puts "==== #{line}"
        puts "#{e.source_linenum-1}: #{body[e.source_linenum-2]}"
        puts "#{e.source_linenum}: #{body[e.source_linenum-1]}"
        puts "#{e.source_linenum+1}: #{body[e.source_linenum]}"
      end
    end rescue puts "#{source_file} not found"
  end

  prev_e = e

end

puts "Done, processed #{lines_done.size} stack trace elements."
