package com.example.cafeorderingsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "waiter_id", nullable = false)
    private Integer waiterId;

    @Column(name = "table_number", nullable = false)
    private Integer tableNumber;

    @Column(name = "order_time", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime orderTime;

    @Column(name = "is_finalized", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isFinalized;

}
