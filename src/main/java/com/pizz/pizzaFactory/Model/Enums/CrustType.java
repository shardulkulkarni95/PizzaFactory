package com.pizz.pizzaFactory.Model.Enums;

public enum CrustType {
	NEW_HAND_TOSSED("New hand tossed"),
    WHEAT_THIN_CRUST("Wheat thin crust"),
    CHEESE_BURST("Cheese Burst"),
    FRESH_PAN_PIZZA("Fresh pan pizza");

    private final String displayName;

    CrustType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
