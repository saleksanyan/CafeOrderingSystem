package com.example.cafeorderingsystem.repository;

import com.example.cafeorderingsystem.entity.Bills;
import com.example.cafeorderingsystem.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bills, Long> {
    Bills findByOrder(Orders order);

}
