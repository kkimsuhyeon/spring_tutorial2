package demo.spring_tutorial3.repository.simplequery;

import demo.spring_tutorial3.domain.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class OrderSimpleQueryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<OrderSimpleQueryDTO> findAllOrderDTO() {
        String query = "SELECT new demo.spring_tutorial3.repository.simplequery.OrderSimpleQueryDTO(o.id, m.name, o.orderDate, o.status, d.address) " +
                "FROM Order AS o " +
                "JOIN o.member AS m " +
                "JOIN o.delivery AS d";

        return entityManager.createQuery(query, OrderSimpleQueryDTO.class).getResultList();
    }
}
