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
@Table(name = "order_details")
public class Order_details {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long orderDetailId;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "item_id")
    private Menu_items menuItem;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price_at_time", nullable = false, precision = 5, scale = 2)
    private BigDecimal priceAtTime;


}
