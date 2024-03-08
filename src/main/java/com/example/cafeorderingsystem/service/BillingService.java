package com.example.cafeorderingsystem.service;


import com.example.cafeorderingsystem.entity.Bills;
import com.example.cafeorderingsystem.entity.Order_details;
import com.example.cafeorderingsystem.entity.Orders;
import com.example.cafeorderingsystem.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class BillingService {

    @Autowired
    private BillRepository billRepository;

    public void generateBill(Orders order, List<Order_details> orderDetails) {
        BigDecimal subtotal = calculateSubtotal(orderDetails);

        BigDecimal serviceFee = subtotal.multiply(BigDecimal.valueOf(0.10));

        BigDecimal tax = subtotal.multiply(BigDecimal.valueOf(0.20));

        // Calculate total (subtotal + service fee + tax)
        BigDecimal total = subtotal.add(serviceFee).add(tax);

        Bills bill = new Bills();
        bill.setOrder(order);
        bill.setSubtotal(subtotal);
        bill.setServiceFee(serviceFee);
        bill.setTax(tax);
        bill.setTotal(total);

        // Save the bill
        billRepository.save(bill);
    }

    public void updateBill(Orders order, List<Order_details> modifiedOrderDetails) {
        Bills bill = billRepository.findByOrder(order);

        if (bill != null) {
            BigDecimal subtotal = calculateSubtotal(modifiedOrderDetails);
            BigDecimal serviceFee = subtotal.multiply(BigDecimal.valueOf(0.10));
            BigDecimal tax = subtotal.multiply(BigDecimal.valueOf(0.20));
            BigDecimal total = subtotal.add(serviceFee).add(tax);

            // Update the bill
            bill.setSubtotal(subtotal);
            bill.setServiceFee(serviceFee);
            bill.setTax(tax);
            bill.setTotal(total);

            billRepository.save(bill);
        }
    }

    private BigDecimal calculateSubtotal(List<Order_details> orderDetails) {
        BigDecimal subtotal = BigDecimal.ZERO;
        for (Order_details orderDetail : orderDetails) {
            subtotal = subtotal.add(orderDetail.getPriceAtTime());
        }
        return subtotal;
    }
}
