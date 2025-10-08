package com.order.service.service;

import com.order.service.model.Order;
import com.order.service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "order_topic";

    public Order saveOrder(Order order) {
        Order saved = orderRepository.save(order);
        kafkaTemplate.send(TOPIC, "New Order Created: " + saved.getProductName());
        return saved;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
