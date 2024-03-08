package com.example.cafeorderingsystem.service;


import com.example.cafeorderingsystem.entity.Order_details;
import com.example.cafeorderingsystem.entity.Orders;
import com.example.cafeorderingsystem.repository.OrderDetailRepository;
import com.example.cafeorderingsystem.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private BillingService billingService;


    public void placeOrder(Orders order, List<Order_details> orderDetails) {
        orderRepository.save(order);
        for (Order_details orderDetail : orderDetails) {
            orderDetail.setOrder(order);
            orderDetailRepository.save(orderDetail);
        }
        // Generate the bill
        billingService.generateBill(order, orderDetails);    }


    public void cancelOrder(Long orderId) {
        Orders order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            orderRepository.delete(order);
        }
    }
     public Optional<Orders> viewOrder(Long id){
        return orderRepository.findById(id);
     }

}
