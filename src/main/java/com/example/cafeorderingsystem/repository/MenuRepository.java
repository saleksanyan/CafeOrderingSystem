package com.example.cafeorderingsystem.repository;

import com.example.cafeorderingsystem.entity.Menu_items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface MenuRepository extends JpaRepository<Menu_items, Long> {

    @Modifying
    @Query("UPDATE Menu_items m SET m.name = :name, m.description = :description, m.price = :price, m.category = :category WHERE m.itemId = :itemId")
    void updateMenuItem(
            @Param("itemId") Long itemId,
            @Param("name") String name,
            @Param("description") String description,
            @Param("price") BigDecimal price,
            @Param("category") String category
    );
    Menu_items deleteMenu_itemsByItemId(Long id);
}
