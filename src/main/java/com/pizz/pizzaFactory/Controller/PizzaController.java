package com.pizz.pizzaFactory.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pizz.pizzaFactory.DTO.OrderResponseDTO;
import com.pizz.pizzaFactory.DTO.PizzaOrderDTO;
import com.pizz.pizzaFactory.DTO.ResponseDTO;
import com.pizz.pizzaFactory.Model.Menu;
import com.pizz.pizzaFactory.Model.Enums.OrderStatus;
import com.pizz.pizzaFactory.Model.Enums.PizzaType;
import com.pizz.pizzaFactory.Service.PizzaOrderService;

import jakarta.validation.Valid;

@RestController
public class PizzaController {
	
	// two endpoints
	
	@Autowired
	private Menu pizzaMenu;
	
	@Autowired
	private PizzaOrderService pizzaOrderService;
	
	// get all menu
	@GetMapping("/PizzaMenu")
    public ResponseEntity<ResponseDTO> getAllPizzas() {		
        return ResponseEntity.ok(new ResponseDTO(pizzaMenu,false,null));
    }
	
	// accept order
	
	@PostMapping(value = "/PlaceOrder", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> placeOrder(@Valid @RequestBody PizzaOrderDTO orderRequest) {
            OrderResponseDTO response = pizzaOrderService.processOrder(orderRequest);
            return ResponseEntity.ok(new ResponseDTO(response,false,null));
        
    }
	

}
