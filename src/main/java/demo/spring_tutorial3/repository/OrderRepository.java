package demo.spring_tutorial3.repository;

import demo.spring_tutorial3.domain.Order;
import demo.spring_tutorial3.domain.OrderStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Order order) {
        entityManager.persist(order);
    }

    public Order findOne(Long id) {
        return entityManager.find(Order.class, id);
    }

    public List<Order> findAllByString(OrderSearch orderSearch) {

        String query = "SELECT o FROM Order AS o " +
                "JOIN o.member AS m " +
                "WHERE o.status = :status " +
                "AND m.name LIKE :name ";

        List<Order> resultList = entityManager.createQuery(query, Order.class)
                .setParameter("status", orderSearch.getOrderStatus())
                .setParameter("name", orderSearch.getMemberName())
                .getResultList();

        return resultList;
    }

    public List<Order> findAll() {
        return entityManager.createQuery("SELECT o FROM Order AS o", Order.class).getResultList();
    }

    public List<Order> findAllWithMemberDelivery() {
        String query = "SELECT o FROM Order AS o" +
                " JOIN FETCH o.member AS m" +
                " JOIN FETCH o.delivery AS d";

        return entityManager.createQuery(query, Order.class).getResultList();
    }

    public List<Order> findAllWithItem() {
        String query = "SELECT DISTINCT o FROM Order AS o" +
                "JOIN FETCH o.member AS m" +
                "JOIN FETCH o.delivery AS d" +
                "JOIN FETCH o.orderItems AS oi" +
                "JOIN FETCH oi.item AS i";

        return entityManager.createQuery(query, Order.class).getResultList();
    }

    public List<Order> findAllWithMemberDelivery(int offset, int limit) {

        String query = "SELECT o FROM Order AS o" +
                " JOIN FETCH o.member AS m" +
                " JOIN FETCH o.delivery AS d";

        return entityManager.createQuery(query, Order.class)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

}
