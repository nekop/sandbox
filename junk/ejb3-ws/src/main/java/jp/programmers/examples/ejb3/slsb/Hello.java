package jp.programmers.examples.ejb3.slsb;

import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService
public interface Hello {
    @WebMethod
    public String hello();
    //    public String hello(String name);
    public void exception();
}
