package com.pizz.pizzaFactory.DTO;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public class PizzaDTO {

	@NotBlank(message = "Pizza name cannot be blank")
	private String name;
    private String size; // Regular, Medium, Large
    private String crustType;
    private List<String> extraToppings;
    private boolean extraCheese;
    
    // Getters and setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getSize() {
        return size;
    }
    
    public void setSize(String size) {
        this.size = size;
    }
    
    public String getCrustType() {
        return crustType;
    }
    
    public void setCrustType(String crustType) {
        this.crustType = crustType;
    }
    
    public List<String> getExtraToppings() {
        return extraToppings;
    }
    
    public void setExtraToppings(List<String> extraToppings) {
        this.extraToppings = extraToppings;
    }
    
    public boolean isExtraCheese() {
        return extraCheese;
    }
    
    public void setExtraCheese(boolean extraCheese) {
        this.extraCheese = extraCheese;
    }
}
