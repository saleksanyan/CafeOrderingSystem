package com.example.cafeorderingsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Bills {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bill_id;


    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Orders order;


    @Column(name = "subtotal", nullable = false, precision = 5, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "service_fee", nullable = false, precision = 5, scale = 2)
    private BigDecimal serviceFee;

    @Column(name = "tax", nullable = false, precision = 5, scale = 2)
    private BigDecimal tax;

    @Column(name = "tip", precision = 5, scale = 2, columnDefinition = "NUMERIC(5, 2) DEFAULT 0.00")
    private BigDecimal tip;

    @Column(name = "total", nullable = false, precision = 5, scale = 2)
    private BigDecimal total;



}
