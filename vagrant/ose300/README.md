Steps
====

- vagrant up
- init.sh
- ssh vagrant@ose300-node1
  - Fix /etc/resolv.conf.upstream
- ssh vagrant@ose300-master
- ssh-keygen
- ssh-copy-id vagrant@ose300-master
- ssh-copy-id vagrant@ose300-node1
- ssh-copy-id vagrant@ose300-node2
- sudo yum install python-virtualenv
- sh -x <(curl -s https://install.openshift.com/ose)

ose300-node1,192.168.121.77,192.168.232.201,ose300-node1.example.com,ose300-node1.example.com
ose300-node2,192.168.121.206,192.168.232.202,ose300-node2.example.com,ose300-node2.example.com
ose300-master,192.168.121.20,192.168.232.101,ose300-master.example.com,ose300-master.example.com

- Modify iptables rule on ose300-node1

Tests
====

$ dig @ose300-node1 hello.cloudapps.example.com
;; ANSWER SECTION:
hello.cloudapps.example.com. 0	IN	A	192.168.232.101

[root@ose300-master ~]# oc get nodes
NAME                        LABELS                                             STATUS
ose300-master.example.com   kubernetes.io/hostname=ose300-master.example.com   Ready,SchedulingDisabled
ose300-node1.example.com    kubernetes.io/hostname=ose300-node1.example.com    Ready
ose300-node2.example.com    kubernetes.io/hostname=ose300-node2.example.com    Ready
[root@ose300-master ~]# oc get all
NAME      TYPE      SOURCE
NAME      TYPE      STATUS    POD
NAME      DOCKER REPO   TAGS      UPDATED
NAME      TRIGGERS   LATEST VERSION
CONTROLLER   CONTAINER(S)   IMAGE(S)   SELECTOR   REPLICAS
NAME      HOST/PORT   PATH      SERVICE   LABELS
NAME            LABELS                                    SELECTOR   IP(S)        PORT(S)
kubernetes      component=apiserver,provider=kubernetes   <none>     172.30.0.2   443/TCP
kubernetes-ro   component=apiserver,provider=kubernetes   <none>     172.30.0.1   80/TCP
NAME      READY     REASON    RESTARTS   AGE

Notes
====

- Quick Installer doesn't have an ability to modify host names?
- Advanced isntall requires ansible 1.8, do not do this on F22 / oh-my-vagrant
  - git clone https://github.com/openshift/openshift-ansible
  - cd openshift-ansible
  - git checkout -b 3.x v3.0.0
  
TODOs
====

- Automate everything!
  - iptables
  - resolv.conf


Memo
====

https://access.redhat.com/search/#/container-images
docker pull registry.access.redhat.com/openshift3/ose-haproxy-router
docker pull registry.access.redhat.com/openshift3/ose-deployer
docker pull registry.access.redhat.com/openshift3/ose-sti-builder
docker pull registry.access.redhat.com/openshift3/ose-sti-image-builder
docker pull registry.access.redhat.com/openshift3/ose-docker-builder
docker pull registry.access.redhat.com/openshift3/ose-pod
docker pull registry.access.redhat.com/openshift3/ose-docker-registry
docker pull registry.access.redhat.com/openshift3/sti-basicauthurl
docker pull registry.access.redhat.com/openshift3/ose-keepalived-ipfailover

