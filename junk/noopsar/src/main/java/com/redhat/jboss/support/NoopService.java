package com.redhat.jboss.support;

import org.jboss.system.ServiceMBeanSupport;

public class NoopService extends ServiceMBeanSupport implements NoopServiceMBean {

    @Override
    public void createService() throws Exception {
        super.createService();
        System.out.println("NoopService.createService()");
    }
    @Override
    public void startService() throws Exception {
        super.startService();
        System.out.println("NoopService.startService()");
    }
    @Override
    public void stopService() throws Exception {
        super.stopService();
        System.out.println("NoopService.stopService()");
    }
    @Override
    public void destroyService() throws Exception {
        super.destroyService();
        System.out.println("NoopService.destroyService()");
    }
    
}
