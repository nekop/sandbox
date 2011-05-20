#!/bin/sh

while true; do
  sleep 30
  sudo /sbin/iptables -A INPUT -s 127.0.1.1/32 -j DROP
  sudo /sbin/iptables -A OUTPUT -d 127.0.1.1/32 -j DROP
  sleep 30
  sudo /sbin/iptables -F
done

