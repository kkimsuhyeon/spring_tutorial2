package demo.spring_tutorial3.repository.query;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class OrderQueryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // 컬렉션 별도 조회
    // 루트 1번, 컬렉션 n번
    // 단건에서 많이 사용된다고 한다
    public List<OrderQueryDto> findOrderQueryDtos() {
        // 루트 조회
        List<OrderQueryDto> result = findOrders();

        // 루프 돌면서 컬렉션 추가
        result.forEach(order -> {
            List<OrderItemQueryDto> orderItems = findOrderItems(order.getOrderId());
            order.setOrderItems(orderItems);
        });

        return result;
    }

    // 위의 최적화 코드에서 추가적으로 최적화
    // 1 + N 조회에서 1 + 1 조회로 최적화, OrderItem도 한번에 호출
    public List<OrderQueryDto> findAllByDto_optimization() {

        List<OrderQueryDto> result = findOrders();

        Map<Long, List<OrderItemQueryDto>> orderItemMap = findOrderItemMap(toOrderIds(result));
        result.forEach(order -> order.setOrderItems(orderItemMap.get(order.getOrderId())));

        return result;
    }

    public List<OrderFlatDto> findAllByDto_flat() {
        return entityManager.createQuery("SELECT new demo.spring_tutorial3.repository.query.OrderFlatDto()" +
                " FROM Order AS o" +
                " JOIN o.member AS m" +
                " JOIN o.delivery AS d" +
                " JOIN o.orderItems AS oi" +
                " JOIN oi.item AS i", OrderFlatDto.class).getResultList();
    }

    private List<Long> toOrderIds(List<OrderQueryDto> result) {
        return result.stream().map(OrderQueryDto::getOrderId).collect(Collectors.toList());
    }


    private Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<Long> orderIds) {
        List<OrderItemQueryDto> resultList = entityManager.createQuery("SELECT new demo.spring_tutorial3.repository.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                " FROM OrderItem AS oi" +
                " JOIN oi.item AS i" +
                " WHERE oi.order.id IN :orderIds", OrderItemQueryDto.class).getResultList();

        return resultList.stream().collect(Collectors.groupingBy(OrderItemQueryDto::getOrderId));

    }

    // 1:N을 제외한 나머지 조회
    private List<OrderQueryDto> findOrders() {
        return entityManager.createQuery("SELECT new demo.spring_tutorial3.repository.query.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
                " FROM Order AS o" +
                " JOIN o.member AS m" +
                " JOIN o.delivery AS d", OrderQueryDto.class
        ).getResultList();
    }

    // 1:N관계 조회
    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return entityManager.createQuery("SELECT new demo.spring_tutorial3.repository.query.OrderItemQueryDto()" +
                " FROM OrderItem AS oi" +
                " JOIN oi.item AS i" +
                " WHERE oi.order.id = :orderId", OrderItemQueryDto.class).getResultList();
    }


}
