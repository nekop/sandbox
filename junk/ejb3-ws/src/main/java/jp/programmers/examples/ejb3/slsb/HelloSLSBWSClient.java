package jp.programmers.examples.ejb3.slsb;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class HelloSLSBWSClient {

    public static void main(String[] args) throws Exception {
        URL wsdlLocation = new URL("http://127.0.0.1:8080/example-ejb3/HelloSLSB?wsdl");
        QName serviceName = new QName("http://slsb.ejb3.examples.programmers.jp/", "HelloSLSBService");
        Service service = Service.create(wsdlLocation, serviceName);
        Hello hello = service.getPort(Hello.class);
        hello.hello();
        System.out.println("sleep 20 sec");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException ignore) { }
        System.out.println("Execute GC and sleep 20 sec");
        service = null;
        hello = null;
        System.gc();
        try {
            Thread.sleep(20000);
        } catch (InterruptedException ignore) { }
    }

}
