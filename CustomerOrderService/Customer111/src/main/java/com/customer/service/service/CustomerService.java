package com.customer.service.service;

import com.customer.service.model.Customer;
import com.customer.service.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "customer_topic";

    public Customer saveCustomer(Customer customer) {
        Customer saved = customerRepository.save(customer);
        kafkaTemplate.send(TOPIC, "New Customer Created: " + saved.getName());
        return saved;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
