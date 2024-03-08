package com.example.cafeorderingsystem.service;


import com.example.cafeorderingsystem.entity.Menu_items;
import com.example.cafeorderingsystem.repository.MenuRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service

public class MenuService{

    private final MenuRepository menuRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public Menu_items addMenu(Menu_items menuItem) {
        return menuRepository.save(menuItem);
    }

    public void updateMenuItem(Long itemId, String name, String description, BigDecimal price, String category) {
        menuRepository.updateMenuItem(itemId, name, description, price, category);
    }
    public Menu_items deleteMenu_itemsByItemId(Long id) {
        return menuRepository.deleteMenu_itemsByItemId(id);
    }

    public List<Menu_items> getAllMenuItems() {
        return menuRepository.findAll();
    }

    public Optional<Menu_items> getMenuItemById(Long itemId) {
        return menuRepository.findById(itemId);
    }
}
