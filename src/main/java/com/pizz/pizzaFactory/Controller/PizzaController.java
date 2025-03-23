package com.pizz.pizzaFactory.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pizz.pizzaFactory.DTO.OrderResponseDTO;
import com.pizz.pizzaFactory.DTO.PizzaOrderDTO;
import com.pizz.pizzaFactory.Model.Menu;
import com.pizz.pizzaFactory.Model.Enums.OrderStatus;
import com.pizz.pizzaFactory.Model.Enums.PizzaType;
import com.pizz.pizzaFactory.Service.PizzaOrderService;

@RestController
public class PizzaController {
	
	// two endpoints
	
	@Autowired
	private Menu pizzaMenu;
	
	@Autowired
	private PizzaOrderService pizzaOrderService;
	
	// get all menu
	@GetMapping("/PizzaMenu")
    public ResponseEntity<?> getAllPizzas() {		
        return ResponseEntity.ok(pizzaMenu);
    }
	
	// accept order
	
	@PostMapping("/PlaceOrder")
    public ResponseEntity<?> placeOrder(@RequestBody PizzaOrderDTO orderRequest) {
		try {
            OrderResponseDTO response = pizzaOrderService.processOrder(orderRequest);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred: " + e.getMessage());
        }
    }
	

}
