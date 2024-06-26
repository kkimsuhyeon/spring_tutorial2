package demo.spring_tutorial2.repository.member;

import demo.spring_tutorial2.domain.Member;
import demo.spring_tutorial2.domain.constant.MemberSearchType;
import demo.spring_tutorial2.dto.SearchValue;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepositoryJPA implements MemberRepository {

    @PersistenceContext
    EntityManager entityManager;

    public Page<Member> findBySearchType(MemberSearchType type, String searchValue, Pageable pageable) {
        String query = "SELECT m FROM Member AS m";
        if (searchValue != null && !searchValue.isBlank()) {
            query += searchQueryParser(type, searchValue);
        }

        if (pageable.getSort().isSorted()) {
            query += pageQueryParser(pageable);
        }

        Long totalCount = getTotalCount(type, searchValue);

        List<Member> members = entityManager.createQuery(query, Member.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(members, pageable, totalCount);
    }

    public Page<Member> findBySearchTypeWithRoles(MemberSearchType type, String searchValue, Pageable pageable) {
        String query = "SELECT m FROM Member AS m" +
                " LEFT JOIN FETCH m.roles AS r";
        
        if (searchValue != null && !searchValue.isBlank()) {
            query += searchQueryParser(type, searchValue);
        }

        if (pageable.getSort().isSorted()) {
            query += pageQueryParser(pageable);
        }

        Long totalCount = getTotalCount(type, searchValue);

        List<Member> members = entityManager.createQuery(query, Member.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(members, pageable, totalCount);
    }

    public Optional<Member> findById(Long id) {
        Member member = entityManager.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    public Optional<Member> findByEmail(String email) {

        String query = "SELECT m FROM Member AS m" +
                " WHERE m.email = :email";

        Member member = entityManager.createQuery(query, Member.class).setParameter("email", email).getSingleResult();

        return Optional.ofNullable(member);
    }

    public Optional<Member> findByEmailWithRoles(String email) {
        String query = "SELECT m FROM Member AS m" +
                " LEFT JOIN FETCH m.roles AS r" +
                " WHERE m.email = :email";

        Member member = entityManager
                .createQuery(query, Member.class)
                .setParameter("email", email)
                .getSingleResult();

        return Optional.ofNullable(member);
    }

    public Member save(Member member) {
        entityManager.persist(member);
        return member;
    }

    public void delete(Member member) {
        member.setExpiredAt(LocalDateTime.now());
    }

    public void deleteFromDatabase(Member member) {
        entityManager.remove(member);
    }

    public void flush() {
        entityManager.flush();
        entityManager.clear();
    }

    private Long getTotalCount(MemberSearchType type, String searchValue) {
        String countQuery = "SELECT COUNT(m) FROM Member AS m";

        if (searchValue != null && !searchValue.isBlank()) {
            countQuery += searchQueryParser(type, searchValue);
        }

        return entityManager.createQuery(countQuery, Long.class).getSingleResult();
    }

    private String pageQueryParser(Pageable pageable) {

        String orderQuery = " ORDER BY";
        StringBuilder queryBuilder = new StringBuilder(orderQuery);

        for (Sort.Order order : pageable.getSort()) {
            queryBuilder.append(" m.%s %s".formatted(order.getProperty(), order.getDirection()));
        }

        return queryBuilder.toString();
    }

    private String searchQueryParser(MemberSearchType type, String searchValue) {
        String searchQuery = " WHERE";

        if (type == MemberSearchType.EMAIL) {
            searchQuery += " m.email LIKE '%%%s%%'".formatted(searchValue);
        }

        if (type == MemberSearchType.NICKNAME) {
            searchQuery += " m.nickname LIKE '%%%s%%'".formatted(searchValue);
        }

        if (type == MemberSearchType.MEMO) {
            searchQuery += " m.memo LIKE '%%%s%%'".formatted(searchValue);
        }

        return searchQuery;
    }
}
