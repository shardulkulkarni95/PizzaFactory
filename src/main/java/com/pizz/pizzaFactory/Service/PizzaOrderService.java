package com.pizz.pizzaFactory.Service;

import com.pizz.pizzaFactory.DTO.OrderResponseDTO;
import com.pizz.pizzaFactory.DTO.PizzaDTO;
import com.pizz.pizzaFactory.DTO.PizzaOrderDTO;
import com.pizz.pizzaFactory.DTO.SideOrderDTO;
import com.pizz.pizzaFactory.Model.Menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PizzaOrderService {
	
	@Autowired
	private Menu pizzaMenu;
    
    // Lists to store veg and non-veg pizza names and toppings for validation
    private final List<String> vegetarianPizzas = Arrays.asList("Deluxe Veggie", "Cheese and corn", "Paneer Tikka");
    private final List<String> nonVegetarianPizzas = Arrays.asList("Non-Veg Supreme", "Chicken Tikka", "Pepper Barbecue Chicken");
    private final List<String> vegToppings = Arrays.asList("Black olive", "Capsicum", "Paneer", "Mushroom", "Fresh tomato");
    private final List<String> nonVegToppings = Arrays.asList("Chicken tikka", "Barbeque chicken", "Grilled chicken");
    private final List<String> validCrustTypes = Arrays.asList("New hand tossed", "Wheat thin crust", "Cheese Burst", "Fresh pan pizza");
    
    
    public OrderResponseDTO processOrder(PizzaOrderDTO orderRequest) throws IllegalArgumentException {
        // List to store validation errors
        List<String> validationErrors = new ArrayList<>();
        
        // Validate each pizza in the order
        for (int i = 0; i < orderRequest.getPizzas().size(); i++) {
            PizzaDTO pizza = orderRequest.getPizzas().get(i);
            String pizzaIdentifier = "Pizza #" + (i + 1) + " (" + pizza.getName() + ")";
            
            // Validate pizza exists in menu
            if (!isPizzaInMenu(pizza.getName())) {
            	// throw exception 
                validationErrors.add(pizzaIdentifier + ": Pizza not found in menu");
                continue;
            }
            
            // Validate toppings for vegetarian pizzas
            if (isVegetarianPizza(pizza.getName())) {
            	// throw exception 
                List<String> errors = validateVegetarianToppings(pizza, pizzaIdentifier);
                validationErrors.addAll(errors);
            }
            
            // Validate toppings for non-vegetarian pizzas
            if (isNonVegetarianPizza(pizza.getName())) {
            	// throw exception 
                List<String> errors = validateNonVegetarianToppings(pizza, pizzaIdentifier);
                validationErrors.addAll(errors);
            }
            
            // Validate crust type
            List<String> crustErrors = validateCrustType(pizza, pizzaIdentifier);
            validationErrors.addAll(crustErrors);
            
            // Validate if toppings in large pizza comply with free toppings rule
            if ("Large".equals(pizza.getSize())) {
                List<String> freeTopErrors = validateLargePizzaToppings(pizza, pizzaIdentifier);
                validationErrors.addAll(freeTopErrors);
            }
        }
        
        // If there are validation errors, throw exception with all errors
        if (!validationErrors.isEmpty()) {
            throw new IllegalArgumentException("Order validation failed: " + String.join("; ", validationErrors));
        }
        
        // Calculate total price for the order
        double totalAmount = calculateOrderAmount(orderRequest);
        
        // Create order response
        OrderResponseDTO response = new OrderResponseDTO();
        response.setOrderId(UUID.randomUUID().toString());
        response.setTotalAmount(totalAmount);
        response.setEstimatedDeliveryTime("30-45 minutes");
        response.setStatus("Order Placed Successfully");
        
        return response;
    }
    
    // Rule 1: Vegetarian pizza cannot have a non-vegetarian topping
    private List<String> validateVegetarianToppings(PizzaDTO pizza, String pizzaIdentifier) {
        List<String> errors = new ArrayList<>();
        
        if (pizza.getExtraToppings() != null) {
            List<String> nonVegToppingsFound = pizza.getExtraToppings().stream()
                .filter(this::isNonVegTopping)
                .collect(Collectors.toList());
                
            if (!nonVegToppingsFound.isEmpty()) {
                errors.add(pizzaIdentifier + ": Vegetarian pizza cannot have non-vegetarian toppings: " + 
                           String.join(", ", nonVegToppingsFound));
            }
        }
        
        return errors;
    }
    
    // Rule 2: Non-vegetarian pizza cannot have paneer topping
    private List<String> validateNonVegetarianToppings(PizzaDTO pizza, String pizzaIdentifier) {
        List<String> errors = new ArrayList<>();
        
        if (pizza.getExtraToppings() != null) {
            boolean hasPaneerTopping = pizza.getExtraToppings().contains("Paneer");
            
            if (hasPaneerTopping) {
                errors.add(pizzaIdentifier + ": Non-vegetarian pizza cannot have paneer topping");
            }
            
            // Rule 4: You can add only one of the non-veg toppings in non-vegetarian pizza
            List<String> nonVegToppingsSelected = pizza.getExtraToppings().stream()
                .filter(this::isNonVegTopping)
                .collect(Collectors.toList());
                
            if (nonVegToppingsSelected.size() > 1) {
                errors.add(pizzaIdentifier + ": Non-vegetarian pizza can have only one non-vegetarian topping");
            }
        }
        
        return errors;
    }
    
    // Rule 3: Only one type of crust can be selected for any pizza
    private List<String> validateCrustType(PizzaDTO pizza, String pizzaIdentifier) {
        List<String> errors = new ArrayList<>();
        
        if (pizza.getCrustType() == null || pizza.getCrustType().isEmpty()) {
            errors.add(pizzaIdentifier + ": Crust type must be selected");
        } else if (!validCrustTypes.contains(pizza.getCrustType())) {
            errors.add(pizzaIdentifier + ": Invalid crust type. Valid options are: " + 
                       String.join(", ", validCrustTypes));
        }
        
        return errors;
    }
    
    // Rule 5: Large size pizzas come with any 2 toppings of customer's choice with no additional cost
    private List<String> validateLargePizzaToppings(PizzaDTO pizza, String pizzaIdentifier) {
        // This is more of a pricing rule than a validation rule
        // We'll use this to adjust pricing rather than to validate
        return new ArrayList<>();
    }
    
    // Utility method to check if a pizza is in the menu
    private boolean isPizzaInMenu(String pizzaName) {
        return vegetarianPizzas.contains(pizzaName) || nonVegetarianPizzas.contains(pizzaName);
    }
    
    // Utility method to check if a pizza is vegetarian
    private boolean isVegetarianPizza(String pizzaName) {
        return vegetarianPizzas.contains(pizzaName);
    }
    
    // Utility method to check if a pizza is non-vegetarian
    private boolean isNonVegetarianPizza(String pizzaName) {
        return nonVegetarianPizzas.contains(pizzaName);
    }
    
    // Utility method to check if a topping is non-vegetarian
    private boolean isNonVegTopping(String toppingName) {
        return nonVegToppings.contains(toppingName);
    }
    
    // Calculate the total order amount
    public double calculateOrderAmount(PizzaOrderDTO order) {
        double total = 0.0;
        
        // Calculate pizza costs
        for (PizzaDTO pizza : order.getPizzas()) {
            total += calculatePizzaPrice(pizza);
        }
        
        // Calculate side order costs
        if (order.getSideOrders() != null) {
            for (SideOrderDTO sideOrder : order.getSideOrders()) {
                total += calculateSideOrderPrice(sideOrder);
            }
        }
        
        return total;
    }
    
    // Calculate price for a single pizza with its customizations
    private double calculatePizzaPrice(PizzaDTO pizza) {
        // Base price lookup - in a real application, you would fetch this from the menu
        // This is simplified for the example
        double basePrice = 0.0;
        
        // Set base pizza price based on type and size
        // These would normally come from the menu database
        if (pizza.getName().equals("Deluxe Veggie")) {
            if (pizza.getSize().equals("Regular")) basePrice = 150;
            else if (pizza.getSize().equals("Medium")) basePrice = 200;
            else if (pizza.getSize().equals("Large")) basePrice = 325;
        }
        // Add similar price definitions for other pizzas
        
        // Add cost for extra cheese if selected
        if (pizza.isExtraCheese()) {
            basePrice += 35;
        }
        
        // Add cost for extra toppings
        if (pizza.getExtraToppings() != null && !pizza.getExtraToppings().isEmpty()) {
            // For large pizzas, first 2 toppings are free
            int paidToppings = pizza.getExtraToppings().size();
            if (pizza.getSize().equals("Large")) {
                paidToppings = Math.max(0, paidToppings - 2);
            }
            
            // Calculate topping costs - in a real app, you'd use actual prices from the menu
            for (int i = 0; i < paidToppings; i++) {
                String topping = pizza.getExtraToppings().get(i);
                // Add different prices based on topping type
                if (topping.equals("Black olive")) basePrice += 20;
                else if (topping.equals("Capsicum")) basePrice += 25;
                // Add similar price definitions for other toppings
            }
        }
        
        return basePrice;
    }
    
    // Calculate price for a side order
    private double calculateSideOrderPrice(SideOrderDTO sideOrder) {
        double price = 0.0;
        
        // Set price based on side order type
        if (sideOrder.getName().equals("Cold drink")) {
            price = 55;
        } else if (sideOrder.getName().equals("Mousse cake")) {
            price = 90;
        }
        
        return price * sideOrder.getQuantity();
    }

}
