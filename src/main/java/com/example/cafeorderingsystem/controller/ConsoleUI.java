package com.example.cafeorderingsystem.controller;

import com.example.cafeorderingsystem.entity.Menu_items;
import com.example.cafeorderingsystem.entity.Order_details;
import com.example.cafeorderingsystem.entity.Orders;
import com.example.cafeorderingsystem.service.MenuService;
import com.example.cafeorderingsystem.service.OrderHistoryService;
import com.example.cafeorderingsystem.service.OrderService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class ConsoleUI {

    private MenuService menuService;

    private OrderService orderService;

    private OrderHistoryService orderHistoryService;

    @PostConstruct
    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("==== Cafe Ordering App ====");
            System.out.println("1. Add Menu Item");
            System.out.println("2. Place Order");
            System.out.println("3. View Order");
            System.out.println("4. Cancel Order");
            System.out.println("5. View Order History");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            int choice = (int)isNumeric(scanner);

            switch (choice) {
                case 1:
                    addMenuItem(scanner);
                    break;
                case 2:
                    placeOrder(scanner);
                    break;
                case 3:
                    viewOrder(scanner);
                    break;
                case 4:
                    cancelOrder(scanner);
                    break;
                case 5:
                    viewOrderHistory(scanner);
                    break;
                case 0:
                    System.out.println("Exiting the application. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    //checking if the input is numeric or not and return the value
    private static double isNumeric(Scanner scanner){
        String s = scanner.nextLine().trim();
        while(!s.matches("\\d+")){
            System.out.println("Enter digits: ");
            s = scanner.nextLine().trim();
        }
        return Double.parseDouble(s);
    }

    private static long isNum(String s, Scanner scanner){
        while(!s.matches("\\d+")){
            System.out.println("Enter digits instead of "+ s + ": ");
            s = scanner.nextLine().trim();
        }
        return Long.parseLong(s);
    }

    private void addMenuItem(Scanner scanner) {
        System.out.println("Enter item name: ");
        String name = scanner.nextLine();
        System.out.println("Enter description: ");
        String description = scanner.nextLine();
        System.out.println("Enter price: ");
        BigDecimal price = BigDecimal.valueOf(isNumeric(scanner));
        String category = scanner.nextLine();
        System.out.println(menuService.addMenu(new Menu_items(name, description, price, category)));

    }

    private void placeOrder(Scanner scanner) {
        System.out.println("Available Menu Items:");
        List<Menu_items> menuItems = menuService.getAllMenuItems();
        for (Menu_items menuItem : menuItems) {
            System.out.println(menuItem.getItemId() + ". " + menuItem.getName() + " - $" + menuItem.getPrice());
        }

        System.out.println("Enter item IDs and quantities to add to the order (e.g., 1 2 3):");
        String[] inputItems = scanner.nextLine().split(" ");

        List<Order_details> orderDetails = new ArrayList<>();

        for (String inputItem : inputItems) {
            Long itemId = isNum(inputItem,scanner);
            Optional<Menu_items> menuItem = menuService.getMenuItemById(itemId);

            if (menuItem.isPresent()) {
                System.out.println("Enter quantity for " + menuItem.get().getName() + ":");
                int quantity = scanner.nextInt();
                scanner.nextLine();
                BigDecimal priceAtTime = menuItem.get().getPrice().multiply(BigDecimal.valueOf(quantity));

                Order_details orderDetail = new Order_details();
                orderDetail.setMenuItem(menuItem.get());
                orderDetail.setQuantity(quantity);
                orderDetail.setPriceAtTime(priceAtTime);

                orderDetails.add(orderDetail);
            } else {
                System.out.println("Invalid item ID: " + itemId);
            }
        }

        System.out.println("Enter waiter ID:");
        int waiterId = (int) isNumeric(scanner);
        scanner.nextLine();

        System.out.println("Enter table number:");
        int tableNumber = (int) isNumeric(scanner);
        scanner.nextLine();

        Orders order = new Orders();
        order.setWaiterId(waiterId);
        order.setTableNumber(tableNumber);

        orderService.placeOrder(order, orderDetails);

        System.out.println("Order placed successfully!");
    }

    private void viewOrder(Scanner scanner) {
        System.out.println("Enter order ID: ");
        Optional<Orders> order = orderService.viewOrder((long) isNumeric(scanner));
        if(order.isPresent())
            System.out.println(order.get());
        else
            System.out.println("Given ID is invalid!");
    }

    private void cancelOrder(Scanner scanner) {
        System.out.println("Enter order ID: ");
        orderService.cancelOrder((long) isNumeric(scanner));
        System.out.println("Order has been canceled successfully!");
    }

    private void viewOrderHistory(Scanner scanner) {
        String startString = "Enter the start date and time (YYYY-MM-DD HH:mm:ss): ";
        String endString = "Enter the end date and time (YYYY-MM-DD HH:mm:ss): ";
        LocalDateTime s = dateGetter(scanner, startString);
        LocalDateTime e = dateGetter(scanner, endString);
        while(s.isAfter(e)) {
            System.out.println("Invalid date! Please enter them again!");
            s = dateGetter(scanner, startString);
            e = dateGetter(scanner, endString);
        }
        System.out.println(orderHistoryService.getOrdersWithinDateRange(s, e));
    }



    private static LocalDateTime dateGetter(Scanner scanner, String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();

                return LocalDateTime.parse(input, formatter);
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a valid date and time in the format (YYYY-MM-DD HH:mm:ss).");
            }
        }
    }
}
