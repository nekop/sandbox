#/bin/bash

yum install dnsmasq -y
cat << EOM > /etc/dnsmasq.conf
strict-order                                                                                             
domain-needed                                                                                            
local=/example.com/                                                                                      
bind-dynamic                                                                                             
resolv-file=/etc/resolv.conf.upstream
address=/.cloudapps.example.com/192.168.232.101
log-queries
EOM
chown root.root /etc/dnsmasq.conf
systemctl enable dnsmasq; systemctl start dnsmasq


