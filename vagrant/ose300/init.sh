#/bin/bah

INIT_SSH=true
INIT_RHSM=true
INIT_DNSMASQ=true
INIT_RESOLVCONF=true
INIT_HOSTS=true

if [ "$INIT_SSH" == "true" ]; then
    echo "Init SSH"
    ssh-copy-id vagrant@ose300-master
    ssh-copy-id vagrant@ose300-node1
    ssh-copy-id vagrant@ose300-node2
fi

if [ "$INIT_RHSM" == "true" ]; then
    echo "Init RHSM"
    scp setup-subscription-manager.sh .rhn-* vagrant@ose300-master:
    scp setup-subscription-manager.sh .rhn-* vagrant@ose300-node1:
    scp setup-subscription-manager.sh .rhn-* vagrant@ose300-node2:
    ssh vagrant@ose300-master sudo sh setup-subscription-manager.sh &
    ssh vagrant@ose300-node1 sudo sh setup-subscription-manager.sh &
    ssh vagrant@ose300-node2 sudo sh setup-subscription-manager.sh &
    wait
    ssh vagrant@ose300-master rm setup-subscription-manager.sh .rhn-\*
    ssh vagrant@ose300-node1 rm setup-subscription-manager.sh .rhn-\*
    ssh vagrant@ose300-node2 rm setup-subscription-manager.sh .rhn-\*
fi

echo "Remove NetworkManager"
ssh vagrant@ose300-master sudo yum remove NetworkManager\* -y &
ssh vagrant@ose300-node1 sudo yum remove NetworkManager\* -y &
ssh vagrant@ose300-node2 sudo yum remove NetworkManager\* -y &
wait

echo "Install docker and tools"
ssh vagrant@ose300-master sudo yum install docker wget git net-tools bind-utils iptables-services bridge-utils -y &
ssh vagrant@ose300-node1 sudo yum install docker wget git net-tools bind-utils iptables-services bridge-utils -y &
ssh vagrant@ose300-node2 sudo yum install docker wget git net-tools bind-utils iptables-services bridge-utils -y &
wait

if [ "$INIT_DNSMASQ" == "true" ]; then
    echo "Init dnsmasq on node1"
    scp setup-dnsmasq.sh vagrant@ose300-node1:
    ssh vagrant@ose300-node1 sudo sh setup-dnsmasq.sh
    ssh vagrant@ose300-node1 rm setup-dnsmasq.sh
fi

if [ "$INIT_RESOLVCONF" == "true" ]; then
    echo "Modify /etc/resolv.conf"
    ssh vagrant@ose300-master sudo sh -c '"echo \"nameserver 192.168.232.201
$(cat /etc/resolv.conf)\" > /etc/resolv.conf"'
    ssh vagrant@ose300-node1 sudo sh -c '"echo \"nameserver 192.168.232.201
$(cat /etc/resolv.conf)\" > /etc/resolv.conf"'
    ssh vagrant@ose300-node2 sudo sh -c '"echo \"nameserver 192.168.232.201
$(cat /etc/resolv.conf)\" > /etc/resolv.conf"'
fi

if [ "$INIT_HOSTS" == "true" ]; then
    echo "Modify /etc/hosts"
    ssh vagrant@ose300-master sudo sh -c '"cat << EOM >> /etc/hosts

192.168.232.101 ose300-master.example.com ose300-master
192.168.232.201 ose300-node1.example.com ose300-node1
192.168.232.202 ose300-node2.example.com ose300-node2
EOM"'
    ssh vagrant@ose300-node1 sudo sh -c '"cat << EOM >> /etc/hosts

192.168.232.101 ose300-master.example.com ose300-master
192.168.232.201 ose300-node1.example.com ose300-node1
192.168.232.202 ose300-node2.example.com ose300-node2
EOM"'
    ssh vagrant@ose300-node2 sudo sh -c '"cat << EOM >> /etc/hosts

192.168.232.101 ose300-master.example.com ose300-master
192.168.232.201 ose300-node1.example.com ose300-node1
192.168.232.202 ose300-node2.example.com ose300-node2
EOM"'

    ssh vagrant@ose300-master sudo hostnamectl set-hostname ose300-master.example.com
    ssh vagrant@ose300-node1 sudo hostnamectl set-hostname ose300-node1.example.com
    ssh vagrant@ose300-node2 sudo hostnamectl set-hostname ose300-node2.example.com
fi

