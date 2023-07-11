package org.example.service;

import org.example.dto.orderDto.OrderDtoCreateResponse;
import org.example.entity.Order;
import org.example.mapper.OrderMapper;
import org.example.mapper.UserMapper;
import org.example.repository.OrderRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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

    @BeforeAll
    void setUp() {
        orderDtoCreateResponse = OrderDtoCreateResponse.builder().build();

        order1 = Order.builder()

                .build();
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

    }

    @Test
    void getAllOrdersByUser() {
    }
}