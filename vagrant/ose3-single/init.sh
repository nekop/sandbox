#/bin/bah

ssh-copy-id vagrant@ose3
scp setup-subscription-manager.sh .rhn-* vagrant@ose3:
ssh vagrant@ose3 sudo sh setup-subscription-manager.sh 
ssh vagrant@ose3 rm setup-subscription-manager.sh .rhn-\*
ssh vagrant@ose3 sudo yum remove NetworkManager\* -y
ssh vagrant@ose3 sudo yum install wget git net-tools bind-utils iptables-services bridge-utils -y
ssh vagrant@ose3 sudo yum install docker -y
ssh vagrant@ose3 sudo yum install python-virtualenv gcc -y
ssh vagrant@ose3 sudo sh -c '"cat << EOM >> /etc/hosts

192.168.233.101 ose3.example.com ose3
EOM"'
ssh vagrant@ose3 sudo hostnamectl set-hostname ose3.example.com
