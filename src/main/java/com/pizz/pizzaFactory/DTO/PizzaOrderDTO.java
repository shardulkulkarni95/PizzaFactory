package com.pizz.pizzaFactory.DTO;

import java.util.List;

public class PizzaOrderDTO {
	private List<PizzaDTO> pizzas;
    private List<SideOrderDTO> sideOrders;
    
    // Getters and setters
    public List<PizzaDTO> getPizzas() {
        return pizzas;
    }
    
    public void setPizzas(List<PizzaDTO> pizzas) {
        this.pizzas = pizzas;
    }
    
    public List<SideOrderDTO> getSideOrders() {
        return sideOrders;
    }
    
    public void setSideOrders(List<SideOrderDTO> sideOrders) {
        this.sideOrders = sideOrders;
    }

}
