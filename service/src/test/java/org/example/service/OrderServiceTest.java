package org.example.service;

import org.example.dto.orderDto.OrderDtoCreateResponse;
import org.example.dto.userDto.UserDtoRequest;
import org.example.entity.Order;
import org.example.entity.User;
import org.example.mapper.OrderMapper;
import org.example.mapper.UserMapper;
import org.example.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private UserMapper userMapper;

    private OrderDtoCreateResponse orderDtoCreateResponse;
    private Order order1;
    private Order order2;
    private User user;

    @BeforeEach
    void setUp() {
        orderDtoCreateResponse = OrderDtoCreateResponse.builder().build();

        order1 = new Order();
        order2 = new Order();

        user = new User();
    }

    @Test
    void createOrder() {
        doReturn(new Order()).when(orderRepository).saveAndFlush(any(Order.class));
        doReturn(new Order()).when(orderMapper).toOrder(any(OrderDtoCreateResponse.class));

        orderService.createOrder(orderDtoCreateResponse);

        verify(orderRepository, times(1)).saveAndFlush(any(Order.class));
        verify(orderMapper, times(1)).toOrder(any(OrderDtoCreateResponse.class));
    }

    @Test
    void getAllOrders() {
        List<Order> categoryList = Arrays.asList(order1, order2);
        Mockito.doReturn(categoryList).when(orderRepository).findAll();

        assertThat(orderService.getAllOrders()).hasSize(2);
        verify(orderMapper, times(2)).toOrderDto(any(Order.class));
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void getAllOrdersByUser() {
        List<Order> categoryList = Arrays.asList(order1, order2);
        Mockito.doReturn(categoryList).when(orderRepository).findAllByUser(null);

        assertThat(orderService.getAllOrdersByUser(UserDtoRequest.builder().build())).hasSize(2);
        verify(orderMapper, times(2)).toOrderDto(any(Order.class));
        verify(userMapper, times(1)).toUser(any(UserDtoRequest.class));
        verify(orderRepository, times(1)).findAllByUser(null);
    }
}