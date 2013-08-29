# GC log format tool
# TODO: CMS support, Non-parallel collector support 

DATESTAMP_RE = /\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{3}(?:\+|-)\d{4}: /
TIMESTAMP_RE = /\d+\.\d{3}: /
FIRST_GC_RE = /(#{DATESTAMP_RE})?(#{TIMESTAMP_RE})?\[(GC|Full GC)/
MEMORY_RE = /(\d+)K->(\d+)K\((\d+)K\)/
MINOR_GC_RE = /\[GC \[PSYoungGen: #{MEMORY_RE}\] #{MEMORY_RE}, (\d+\.\d+) secs\]/
FULL_GC_RE = /\[Full GC (\(\w+\) )?\[PSYoungGen: #{MEMORY_RE}\] \[ParOldGen: #{MEMORY_RE}\] #{MEMORY_RE} \[PSPermGen: #{MEMORY_RE}\], (\d+\.\d+) secs\]/
TIMES_RE = /\[Times: user=(\d+\.\d+) sys=(\d+\.\d+), real=(\d+\.\d+) secs\]/

while gets do
  line = $_
  line =~ FIRST_GC_RE
  result = ""
  datestamp = $1
  timestamp = $2
  type = $3
  case type
  when "GC"
    line =~ MINOR_GC_RE
    new_before = sprintf("%8s", $1)
    new_after = sprintf("%8s", $2)
    new_max = sprintf("%8s", $3)
    total_before = sprintf("%8s", $4)
    total_after = sprintf("%8s", $5)
    total_max = sprintf("%8s", $6)
    took = sprintf("%10s", $7)
    line =~ TIMES_RE
    user = sprintf("%5s", $1)
    sys = sprintf("%5s", $2)
    real = sprintf("%5s", $3)
    result << datestamp if datestamp
    result << sprintf("%12s", timestamp) if timestamp
    result << "[GC [PSYoungGen: #{new_before}K->#{new_after}K(#{new_max}K)] "
    result << "#{total_before}K->#{total_after}K(#{total_max}K), #{took} secs] "
    result << "Times: user=#{user} sys=#{sys}, real=#{real} secs]"
    puts result
  when "Full GC"
    line =~ FULL_GC_RE
    full_gc_desc = $1
    new_before = sprintf("%8s", $2)
    new_after = sprintf("%8s", $3)
    new_max = sprintf("%8s", $4)
    old_before = sprintf("%8s", $5)
    old_after = sprintf("%8s", $6)
    old_max = sprintf("%8s", $7)
    total_before = sprintf("%8s", $8)
    total_after = sprintf("%8s", $9)
    total_max = sprintf("%8s", $10)
    perm_before = sprintf("%8s", $11)
    perm_after = sprintf("%8s", $12)
    perm_max = sprintf("%8s", $13)
    took = sprintf("%10s", $14)
    line =~ TIMES_RE
    user = sprintf("%5s", $1)
    sys = sprintf("%5s", $2)
    real = sprintf("%5s", $3)
    result << datestamp if datestamp
    result << sprintf("%12s", timestamp) if timestamp
    result << "[Full GC #{full_gc_desc}[PSYoungGen: #{new_before}K->#{new_after}K(#{new_max}K)] "
    result << "[ParOldGen: #{old_before}K->#{old_after}K(#{old_max}K)] "
    result << "#{total_before}K->#{total_after}K(#{total_max}K) "
    result << "[PSPermGen: #{perm_before}K->#{perm_after}K(#{perm_max}K)], #{took} secs] "
    result << "Times: user=#{user} sys=#{sys}, real=#{real} secs]"
    puts result
  end
end
  
