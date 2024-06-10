package demo.spring_tutorial3.repository;

import demo.spring_tutorial3.domain.OrderStatus;
import lombok.*;

@Getter
@Builder
@Setter
public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;

}
