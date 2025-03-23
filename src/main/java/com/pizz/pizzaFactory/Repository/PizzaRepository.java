package com.pizz.pizzaFactory.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.pizz.pizzaFactory.Model.Pizza;

public class PizzaRepository {
	private final Map<String, Pizza> pizzas = new ConcurrentHashMap()<>();
    
    public Pizza save(Pizza pizza) {
        pizzas.put(pizza.getId(), pizza);
        return pizza;
    }

}
