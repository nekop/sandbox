#!/bin/bash

grep "192.168.232.101 ose300-master.example.com ose300-master" /etc/hosts > /dev/null
if [ $? -eq 1 ]; then
    sudo sh -c "cat << EOM >> /etc/hosts

192.168.232.101 ose300-master.example.com ose300-master
192.168.232.201 ose300-node1.example.com ose300-node1
192.168.232.202 ose300-node2.example.com ose300-node2
EOM"
fi

grep "nameserver 192.168.232.201" /etc/resolv.conf > /dev/null
if [ $? -eq 1 ]; then
    sudo sh -c "echo \"nameserver 192.168.232.201
$(cat /etc/resolv.conf)\" > /etc/resolv.conf"
fi
