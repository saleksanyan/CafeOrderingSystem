package com.example.cafeorderingsystem.service;


import com.example.cafeorderingsystem.entity.Orders;
import com.example.cafeorderingsystem.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class OrderHistoryService {
    @Autowired
    private OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public List<Orders> getOrdersWithinDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByOrderTimeBetween(startDate, endDate);
    }
}
