package com.pizz.pizzaFactory.Service;

import com.pizz.pizzaFactory.DTO.OrderResponseDTO;
import com.pizz.pizzaFactory.DTO.PizzaDTO;
import com.pizz.pizzaFactory.DTO.PizzaOrderDTO;
import com.pizz.pizzaFactory.DTO.SideOrderDTO;
import com.pizz.pizzaFactory.ExceptionHandler.PizzaException;
import com.pizz.pizzaFactory.Model.Menu;
import com.pizz.pizzaFactory.Model.Menu.Category;
import com.pizz.pizzaFactory.Model.Menu.Pizza;
import com.pizz.pizzaFactory.Model.Menu.Side;
import com.pizz.pizzaFactory.Model.Menu.Topping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    
    
    public OrderResponseDTO processOrder(PizzaOrderDTO orderRequest) {
        
        
        // Validate each pizza in the order
        for (int i = 0; i < orderRequest.getPizzas().size(); i++) {
            PizzaDTO pizza = orderRequest.getPizzas().get(i);
            String pizzaIdentifier = "Pizza #" + (i + 1) + " (" + pizza.getName() + ")";
            
            // Validate pizza exists in menu
            if (!isPizzaInMenu(pizza.getName())) {
            	// throw exception 
            	throw new PizzaException("Pizza not found in menu");
            }
            
            // Validate toppings for vegetarian pizzas
            if (isVegetarianPizza(pizza.getName())) {
            	// throw exception 
                validateVegetarianToppings(pizza, pizzaIdentifier);
                
            }
            
            // Validate toppings for non-vegetarian pizzas
            if (isNonVegetarianPizza(pizza.getName())) {
            	// throw exception 
                validateNonVegetarianToppings(pizza, pizzaIdentifier);
            }
            
            // Validate crust type
            validateCrustType(pizza, pizzaIdentifier);
            
  
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
    private void validateVegetarianToppings(PizzaDTO pizza, String pizzaIdentifier) {
        
        if (pizza.getExtraToppings() != null) {
            List<String> nonVegToppingsFound = pizza.getExtraToppings().stream()
                .filter(this::isNonVegTopping)
                .collect(Collectors.toList());
                
            if (!nonVegToppingsFound.isEmpty()) {
            	throw new PizzaException(pizzaIdentifier + ": Vegetarian pizza cannot have non-vegetarian toppings: " + 
                           String.join(", ", nonVegToppingsFound));
            }
        }

    }
    
    // Rule 2: Non-vegetarian pizza cannot have paneer topping
    private void validateNonVegetarianToppings(PizzaDTO pizza, String pizzaIdentifier) {
     
        
        if (pizza.getExtraToppings() != null) {
            boolean hasPaneerTopping = pizza.getExtraToppings().contains("Paneer");
            
            if (hasPaneerTopping) {
            	throw new PizzaException(pizzaIdentifier + ": Non-vegetarian pizza cannot have paneer topping");
            }
            
            // Rule 4: You can add only one of the non-veg toppings in non-vegetarian pizza
            List<String> nonVegToppingsSelected = pizza.getExtraToppings().stream()
                .filter(this::isNonVegTopping)
                .collect(Collectors.toList());
            System.out.println("before exception");
            if (nonVegToppingsSelected.size() > 1) {
            	throw new PizzaException(pizzaIdentifier + ": Non-vegetarian pizza can have only one non-vegetarian topping");
            }
            System.out.println("after exception");
        }
        
      
    }
    
    // Rule 3: Only one type of crust can be selected for any pizza
    private void validateCrustType(PizzaDTO pizza, String pizzaIdentifier) {
        
        
        if (pizza.getCrustType() == null || pizza.getCrustType().isEmpty()) {
        	throw new PizzaException(pizzaIdentifier + ": Crust type must be selected");
        } else if (!validCrustTypes.contains(pizza.getCrustType())) {
        	throw new PizzaException(pizzaIdentifier + ": Invalid crust type. Valid options are: " + 
                       String.join(", ", validCrustTypes));
        }
       
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
    
    private double calculatePizzaPrice(PizzaDTO pizza) {
        
        double basePrice = 0.0;
        
        List<Pizza> allPizza = pizzaMenu.getCategories().stream().flatMap(e -> e.getPizzas().stream())
                .collect(Collectors.toList());
        
        Optional<Pizza> matchingPizza = allPizza.stream()
        	    .filter(e -> e.getName().equals(pizza.getName()))
        	    .findFirst();
        

        if (matchingPizza.isPresent()) {
        	basePrice = matchingPizza.get().getSizes().get(pizza.getSize());
        }
        
        if (pizza.isExtraCheese()) {
            basePrice += 35;
        }
        
        Map<String, Integer> toppingPriceMap = pizzaMenu.getToppings()
        	    .values()                           
        	    .stream()                           
        	    .flatMap(List::stream)             
        	    .collect(Collectors.toMap(
        	        Topping::getName,               
        	        Topping::getPrice               
        	    ));
        

        if (pizza.getExtraToppings() != null && !pizza.getExtraToppings().isEmpty()) {
            // For large pizzas, first 2 toppings are free
            int paidToppings = pizza.getExtraToppings().size();
            if (pizza.getSize().equals("Large")) {
                paidToppings = Math.max(0, paidToppings - 2);
            }
            

            for (int i = 0; i < paidToppings; i++) {
                String topping = pizza.getExtraToppings().get(i);
                basePrice += toppingPriceMap.get(topping);
            }
        }
        
        return basePrice;
    }
    
    // Calculate price for a side order
    private double calculateSideOrderPrice(SideOrderDTO sideOrder) {
        double price = 0.0;
        
        Map<String, Integer> sidePriceMap = pizzaMenu.getSides()
        	    .stream()
        	    .collect(Collectors.toMap(
        	        Side::getName,
        	        Side::getPrice
        	    ));
        // Set price based on side order type
        price = sidePriceMap.getOrDefault(sideOrder.getName(), 0);
        
        return price * sideOrder.getQuantity();
    }

}
