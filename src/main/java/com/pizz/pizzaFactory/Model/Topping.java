package com.pizz.pizzaFactory.Model;

import com.pizz.pizzaFactory.Model.Enums.PizzaType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Topping {
	private String id;
    private String name;
    private PizzaType type;
    private double price;

}
