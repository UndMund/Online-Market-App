package org.example.mapper;

import org.example.dto.orderDto.OrderDtoCreateResponse;
import org.example.dto.orderDto.OrderDtoRequest;
import org.example.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {UserMapper.class, ProductMapper.class})
public interface OrderMapper {
    Order toOrder(OrderDtoCreateResponse orderDto);

    @Mapping(target = "username", expression = "java(order.getUser().getUsername())")
    @Mapping(target = "productName", expression = "java(order.getProduct().getProductName())")
    @Mapping(target = "sellerName", expression = "java(order.getProduct().getUser().getUsername())")
    @Mapping(target = "price", expression = "java(order.getProduct().getPrice())")
    OrderDtoRequest toOrderDto(Order order);
}
