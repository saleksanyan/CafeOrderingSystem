package com.example.cafeorderingsystem.repository;

import com.example.cafeorderingsystem.entity.Order_details;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<Order_details, Long> {
}
