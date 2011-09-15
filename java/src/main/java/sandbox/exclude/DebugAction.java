package org.jboss.seam.example.booking;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Enumeration;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.jboss.seam.annotations.Name;

@Stateless
@Name("debug")
public class DebugAction implements Debug {

    public int getSessionSize() {
        HttpSession session =
            (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session == null) {
            return 0;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        String key = null;
        Object value = null;
        int total = 0;
        try {
            oos = new ObjectOutputStream(baos);
            for (Enumeration e = session.getAttributeNames();
                 e.hasMoreElements(); ) {
                key = (String)e.nextElement();
                value = session.getAttribute(key);
                oos.writeObject(value);
                int prev = total;
                total = baos.toByteArray().length;
                System.out.println("key=" + key + ",size=" + (total - prev));
            }
            oos.flush();
            return baos.toByteArray().length;
        } catch (IOException ex) {
            System.out.println("Exception while processing the session attribute with key=" + key + ", class=" + value.getClass());
            ex.printStackTrace();
            return -1;
        } finally {
            try {
                oos.close();
            } catch (Exception ignore) { }
        }
    }
   
}
