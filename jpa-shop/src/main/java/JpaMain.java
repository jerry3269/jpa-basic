import domain.Member;
import domain.Order;
import domain.OrderItem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Member member = new Member();
            member.setName("memberA");

            System.out.println("===============");
            em.persist(member);

            Order order = new Order();
            order.setMember(member);

            System.out.println("===============");
            em.persist(order);

            System.out.println("===============");

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member.getId());
            Order findOrder = em.find(Order.class, order.getId());

            System.out.println("===============");

            for (Order findMemberOrder : findMember.getOrders()) {
                System.out.println("findMemberOrder = " + findMemberOrder);
            }

            System.out.println("===============");

            System.out.println("findOrder.getMember() = " + findOrder.getMember());

            System.out.println("===============");
            tx.commit();
        }catch (Exception e) {
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
