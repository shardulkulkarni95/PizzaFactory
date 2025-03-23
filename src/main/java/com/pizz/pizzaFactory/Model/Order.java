package com.pizz.pizzaFactory.Model;

import java.util.List;

import com.pizz.pizzaFactory.Model.Enums.CrustType;
import com.pizz.pizzaFactory.Model.Enums.OrderStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Order {
	private String orderId;
    private List<Pizza> pizzas;
    private List<Topping> toppings;
    private CrustType crust;
    private List<String> sides;
    private double totalAmount;
    private OrderStatus status;

}
