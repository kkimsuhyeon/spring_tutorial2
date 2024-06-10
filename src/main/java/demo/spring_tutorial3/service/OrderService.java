package demo.spring_tutorial3.service;

import demo.spring_tutorial3.domain.Delivery;
import demo.spring_tutorial3.domain.Member;
import demo.spring_tutorial3.domain.Order;
import demo.spring_tutorial3.domain.OrderItem;
import demo.spring_tutorial3.domain.item.Item;
import demo.spring_tutorial3.repository.ItemRepository;
import demo.spring_tutorial3.repository.MemberRepository;
import demo.spring_tutorial3.repository.OrderRepository;
import demo.spring_tutorial3.repository.OrderSearch;
import demo.spring_tutorial3.repository.simplequery.OrderSimpleQueryDTO;
import demo.spring_tutorial3.repository.simplequery.OrderSimpleQueryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    public OrderService(OrderRepository orderRepository, MemberRepository memberRepository, ItemRepository itemRepository, OrderSimpleQueryRepository orderSimpleQueryRepository) {
        this.orderRepository = orderRepository;
        this.memberRepository = memberRepository;
        this.itemRepository = itemRepository;
        this.orderSimpleQueryRepository = orderSimpleQueryRepository;
    }

    public Long order(Long memberId, Long itemId, int count) {

        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        Order order = Order.createOrder(member, delivery, orderItem);
        orderRepository.save(order);

        return order.getId();
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    public List<Order> findOrders(OrderSearch search) {
        return orderRepository.findAllByString(search);
    }

    public List<OrderSimpleQueryDTO> findAllOrderDtos() {
        List<OrderSimpleQueryDTO> orderDtos = orderSimpleQueryRepository.findAllOrderDTO();
        return orderDtos;
    }


}
