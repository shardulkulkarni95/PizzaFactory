package com.pizz.pizzaFactory.Model;

import java.util.Map;

import com.pizz.pizzaFactory.Model.Enums.PizzaSize;
import com.pizz.pizzaFactory.Model.Enums.PizzaType;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Pizza {

	private String id;
    private String name;
    private PizzaType type;
    private Map<PizzaSize, Double> prices;
}
