package com.pizz.pizzaFactory.Repository;

import com.pizz.pizzaFactory.Model.Inventory;

public class InventoryRepository {

	private final Inventory inventory = new Inventory();
    
    public Inventory getInventory() {
        return inventory;
    }
}
