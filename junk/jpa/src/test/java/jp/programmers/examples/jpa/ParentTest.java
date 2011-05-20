package jp.programmers.examples.jpa;

import java.util.List;
import javax.persistence.Persistence;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import junit.framework.TestCase;

public class ParentTest extends TestCase {

    public void testPersist() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("SET WRITE_DELAY FALSE").executeUpdate();
        em.getTransaction().commit();
        
        em.getTransaction().begin();
        Parent parent = new Parent();
        parent.setId(1L);
        Child child1 = new Child();
        child1.setId(2L);
        child1.setParent(parent);
        Child child2 = new Child();
        child2.setId(3L);
        child2.setParent(parent);
        parent.getChildren().add(child1);
        parent.getChildren().add(child2);
        em.persist(parent);
        em.persist(child1);
        em.persist(child2);
        em.flush();
        em.getTransaction().commit();
        em.close();

        
        em = emf.createEntityManager();
        List<Parent> list = (List<Parent>)em.createQuery("select distinct p from Parent p join fetch p.children").getResultList();
        System.out.println(list);
        System.out.println(list.get(0).getChildren().size());
        em.close();
    }

}
