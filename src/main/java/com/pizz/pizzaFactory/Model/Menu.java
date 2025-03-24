package com.pizz.pizzaFactory.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
@Component
public class Menu {
	

//	prices should have been in map. this is casing iteration in servce. to be added.
	
	private List<Category> categories;
    private List<CrustType> crustTypes;
    private List<Topping> vegToppings;
    private List<Topping> nonVegToppings;
    private List<Topping> extraCheese;
    private List<Side> sides;

    public Menu() {
        initialize();
    }

    private void initialize() {
        // Initialize Vegetarian Pizza category
        List<Pizza> vegPizzas = List.of(
            new Pizza("Deluxe Veggie", Map.of(
                "Regular", 150,
                "Medium", 200,
                "Large", 325
            )),
            new Pizza("Cheese and corn", Map.of(
                "Regular", 175,
                "Medium", 375,
                "Large", 475
            )),
            new Pizza("Paneer Tikka", Map.of(
                "Regular", 160,
                "Medium", 290,
                "Large", 340
            ))
        );

        // Initialize Non-Vegetarian Pizza category
        List<Pizza> nonVegPizzas = List.of(
            new Pizza("Non-Veg Supreme", Map.of(
                "Regular", 190,
                "Medium", 325,
                "Large", 425
            )),
            new Pizza("Chicken Tikka", Map.of(
                "Regular", 210,
                "Medium", 370,
                "Large", 500
            )),
            new Pizza("Pepper Barbecue Chicken", Map.of(
                "Regular", 220,
                "Medium", 380,
                "Large", 525
            ))
        );

        // Set categories
        categories = List.of(
            new Category("Vegetarian Pizza", vegPizzas),
            new Category("Non-Vegetarian", nonVegPizzas)
        );

        // Set crust types
        crustTypes = List.of(
            new CrustType("New hand tossed"),
            new CrustType("Wheat thin crust"),
            new CrustType("Cheese Burst"),
            new CrustType("Fresh pan pizza")
        );

        // Set veg toppings
        vegToppings = List.of(
            new Topping("Black olive", 20),
            new Topping("Capsicum", 25),
            new Topping("Paneer", 35),
            new Topping("Mushroom", 30),
            new Topping("Fresh tomato", 10)
        );

        // Set non-veg toppings
        nonVegToppings = List.of(
            new Topping("Chicken tikka", 35),
            new Topping("Barbeque chicken", 45),
            new Topping("Grilled chicken", 40)
        );

        // Set extra cheese
        extraCheese = List.of(new Topping("Extra cheese", 35));

        // Set sides
        sides = List.of(
            new Side("Cold drink", 55),
            new Side("Mousse cake", 90)
        );
    }

    // Getters
    public List<Category> getCategories() {
        return categories;
    }

    public List<CrustType> getCrustTypes() {
        return crustTypes;
    }

    public Map<String, List<Topping>> getToppings() {
        Map<String, List<Topping>> toppings = new HashMap<>();
        toppings.put("Veg toppings", vegToppings);
        toppings.put("Non-Veg toppings", nonVegToppings);
        toppings.put("Extra cheese", extraCheese);
        return toppings;
    }

    public List<Side> getSides() {
        return sides;
    }

    // Supporting classes for menu structure
    public static class Category {
        private String name;
        private List<Pizza> pizzas;

        public Category(String name, List<Pizza> pizzas) {
            this.name = name;
            this.pizzas = pizzas;
        }

        public String getName() {
            return name;
        }

        public List<Pizza> getPizzas() {
            return pizzas;
        }
    }

    public static class Pizza {
        private String name;
        private Map<String, Integer> sizes;

        public Pizza(String name, Map<String, Integer> sizes) {
            this.name = name;
            this.sizes = sizes;
        }

        public String getName() {
            return name;
        }

        public Map<String, Integer> getSizes() {
            return sizes;
        }
    }

    public static class CrustType {
        private String name;

        public CrustType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static class Topping {
        private String name;
        private int price;

        public Topping(String name, int price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }
    }

    public static class Extra {
        private String name;
        private int price;

        public Extra(String name, int price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }
    }

    public static class Side {
        private String name;
        private int price;

        public Side(String name, int price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }
    }

	

}
