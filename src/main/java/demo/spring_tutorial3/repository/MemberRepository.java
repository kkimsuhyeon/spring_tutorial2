package demo.spring_tutorial3.repository;

import demo.spring_tutorial3.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Member member) {
        entityManager.persist(member);
    }

    public Member findOne(Long id) {
        return entityManager.find(Member.class, id);
    }

    public List<Member> findAll() {
        String query = "SELECT m FROM Member AS m";
        return entityManager.createQuery(query, Member.class).getResultList();
    }

    public List<Member> findByName(String name) {
        String query = "SELECT m FROM Member as m WHERE name = :name";
        TypedQuery<Member> memberTypedQuery = entityManager.createQuery(query, Member.class).setParameter("name", name);

        return memberTypedQuery.getResultList();
    }

}
