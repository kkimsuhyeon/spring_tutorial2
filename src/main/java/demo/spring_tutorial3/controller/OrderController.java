package demo.spring_tutorial3.controller;

import demo.spring_tutorial3.domain.Order;
import demo.spring_tutorial3.domain.OrderStatus;
import demo.spring_tutorial3.dto.SimpleOrderDto;
import demo.spring_tutorial3.repository.OrderRepository;
import demo.spring_tutorial3.repository.OrderSearch;
import demo.spring_tutorial3.repository.query.OrderFlatDto;
import demo.spring_tutorial3.repository.query.OrderQueryDto;
import demo.spring_tutorial3.repository.query.OrderQueryRepository;
import demo.spring_tutorial3.repository.simplequery.OrderSimpleQueryDTO;
import demo.spring_tutorial3.repository.simplequery.OrderSimpleQueryRepository;
import demo.spring_tutorial3.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OrderController {

    private final OrderQueryRepository orderQueryRepository;
    private OrderService orderService;
    private OrderRepository orderRepository;
    private OrderSimpleQueryRepository orderSimpleQueryRepository;

    public OrderController(OrderService orderService, OrderRepository orderRepository, OrderSimpleQueryRepository orderSimpleQueryRepository, OrderQueryRepository orderQueryRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.orderSimpleQueryRepository = orderSimpleQueryRepository;
        this.orderQueryRepository = orderQueryRepository;
    }

    @GetMapping("/api/orders")
    public ResponseEntity<List<OrderSimpleQueryDTO>> orders() {

        List<OrderSimpleQueryDTO> orderDtos = orderService.findAllOrderDtos();

        return new ResponseEntity<>(orderDtos, HttpStatus.OK);
    }


    @GetMapping("/api/orders/v1")
    public ResponseEntity<List<Order>> ordersV1() {
        OrderSearch search = OrderSearch.builder().memberName("userA").orderStatus(OrderStatus.ORDER).build();
        List<Order> orders = orderRepository.findAllByString(search);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/api/orders/v2")
    public ResponseEntity<List<SimpleOrderDto>> ordersV2() {
        OrderSearch search = OrderSearch.builder().memberName("userA").orderStatus(OrderStatus.ORDER).build();
        List<Order> orders = orderRepository.findAllByString(search);

        // 여기서 member, order, delivery 테이블 각각 조회
        List<SimpleOrderDto> result = orders.stream().map(order -> new SimpleOrderDto(order)).collect(Collectors.toList());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/orders/v3")
    public ResponseEntity<List<SimpleOrderDto>> ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();

        // fetch join을 통해서 지연로딩이 걸리지 않음
        List<SimpleOrderDto> result = orders.stream().map(order -> new SimpleOrderDto(order)).collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/api/orders/v3_1")
    public ResponseEntity<List<SimpleOrderDto>> ordersV3_1(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {

        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);
        List<SimpleOrderDto> result = orders.stream().map(SimpleOrderDto::new).toList();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/api/orders/v4")
    public ResponseEntity<List<OrderSimpleQueryDTO>> ordersV4() {

        // 직접 dto로 조회해도 지연로딩이 걸리지 않음
        List<OrderSimpleQueryDTO> list = orderSimpleQueryRepository.findAllOrderDTO();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/api/orders/v5")
    public List<OrderQueryDto> ordersV5() {
        // 1 + 1 호출
        return orderQueryRepository.findAllByDto_optimization();
    }

    @GetMapping("/api/orders/v6")
    public ResponseEntity<?> ordersV6() {

        // query 1번
        // 상황에 따라 v5보다 느릴수 있음
        // 페이징 불가능
        List<OrderFlatDto> flats = orderQueryRepository.findAllByDto_flat();

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
