#/bin/bah

ssh vagrant@ose300-master sudo subscription-manager unregister &
ssh vagrant@ose300-node1 sudo subscription-manager unregister &
ssh vagrant@ose300-node2 sudo subscription-manager unregister &
wait
vagrant destroy
