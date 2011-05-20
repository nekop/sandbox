package com.redhat.jboss.support;

public class NoopPojo {

    public NoopPojo() {
        System.out.println("new NoopPojo()");
    }
    public void init() throws Exception {
        System.out.println("NoopPojo.init()");
    }
    public void init2() throws Exception {
        System.out.println("NoopPojo.init2()");
    }

}
