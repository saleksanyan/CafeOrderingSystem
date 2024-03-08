package com.example.cafeorderingsystem.repository;

import com.example.cafeorderingsystem.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByOrderTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
}
