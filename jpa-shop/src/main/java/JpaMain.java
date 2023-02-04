import domain.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            Book book = new Book();
            book.setAuthor("김영한");
            book.setName("자바 ORM 표준 JPA");
            em.persist(book);

            em.flush();
            em.clear();

            String string = "select i.name from Item i where type(i) = Book";
            List<String> resultList = em.createQuery(string, String.class)
                    .getResultList();

            for (String s : resultList) {
                System.out.println("s = " + s);
            }


            tx.commit();
        }catch (Exception e) {
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
