#!/usr/bin/env ruby

PROCESS_LINE_RE = /^\d+: /
JAVA_PROCESS_LINE_RE = /^\d+:.*\/java\s.*-Xms.*-Xmx.*/
HEADER_RE = /Address           Kbytes     RSS   Dirty Mode  Mapping/
ENTRY_RE = /^([0-9a-f]+)\s+(\d+)\s+(\d+)\s+(\d+)\s+([\w-])+\s+(.*)$/
THRESHOLD = 16 * 1024
ANON = "[ anon ]"

java = false
large_chunks = []
large_chunks_sum = 0
small_chunks = []
small_chunks_sum = 0
thread_stack_sum = 0
num_of_threads = 0

while gets do
  line = $_
  if line =~ ENTRY_RE then
    address = $1
    kbytes = $2.to_i
    rss = $3.to_i
    mapping = $6

    # First entry should be "java", otherwise skip until "java" appears
    if mapping == "java" then
      java = true
    end
    next unless java

    if rss > THRESHOLD then
      large_chunks << line
      large_chunks_sum += rss
    elsif kbytes == 1016 then
      num_of_threads += 1
      thread_stack_sum += rss
    else
      small_chunks << line
      small_chunks_sum += rss
    end
  else
    # Found non-entry, turn off java switch
    java = false
    puts line
  end
end

puts "== Large chunks =="
puts large_chunks
puts "Large chunks Total: #{large_chunks_sum}"
puts "Small chunks Total: #{small_chunks_sum}"
puts "Thread Stack Total: #{thread_stack_sum}"
puts "Number of threads:  #{num_of_threads}"
# puts "== Small chunks =="
# puts small_chunks




