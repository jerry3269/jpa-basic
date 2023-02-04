package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            Team teamA = new Team();
            teamA.setName("teamA");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setAge(10);
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setAge(20);
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setAge(30);
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

//            String jpql = "select m from Member m join m.team t";
//            List<Member> members = em.createQuery(jpql, Member.class)
//                    .getResultList();
//
//            for (Member member : members) {
//                System.out.println("username = " + member.getUsername() + ", " +
//                        "teamName = " + member.getTeam().getName());
//            }

            Member findMember = em.createNamedQuery("Member.findByUsername", Member.class)
                    .setParameter("username", "회원3")
                    .getSingleResult();

            int query = em.createQuery("update Member m set m.age = 20")
                    .executeUpdate();

            em.clear();

            String jpql = "select t from Team t join t.members";
            List<Team> teams = em.createQuery(jpql, Team.class)
                    .setFirstResult(0)
                    .setMaxResults(2)
                    .getResultList();


            for (Team team : teams) {
                System.out.println("teamName = " + team.getName() + ",");
                for (Member member : team.getMembers()) {
                    System.out.println("member.getUsername() = " + member.getUsername());
                }
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
