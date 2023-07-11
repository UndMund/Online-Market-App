package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.orderDto.OrderDtoCreateResponse;
import org.example.dto.orderDto.OrderDtoRequest;
import org.example.dto.userDto.UserDtoRequest;
import org.example.mapper.OrderMapper;
import org.example.mapper.UserMapper;
import org.example.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;

    @Transactional
    public void createOrder(OrderDtoCreateResponse orderDto) {
        Optional.of(orderDto)
                .map(orderMapper::toOrder)
                .map(order -> {
                    order.setOrderDate(LocalDate.now());
                    return orderRepository.saveAndFlush(order);
                });
    }

    public List<OrderDtoRequest> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toOrderDto)
                .toList();
    }

    public List<OrderDtoRequest> getAllOrdersByUser(UserDtoRequest userDto) {
        return orderRepository.findAllByUser(
                userMapper.toUser(userDto)
                )
                .stream()
                .map(orderMapper::toOrderDto)
                .toList();
    }


}
