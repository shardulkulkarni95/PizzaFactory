package com.pizz.pizzaFactory.Model;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Inventory {
	private Map<String, Integer> ingredients;

}
