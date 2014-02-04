threads = []

while gets do
  line = $_
  case line
  when /^\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}/
    puts line
  when /^Full thread dump/
    puts line
    puts
  when /^".* nid=0x([^\s]+) /
    nid = $1 
    pid = sprintf("%5d", nid.hex)
    threads << "#{pid} #{line}"
  end
end

puts threads.sort
