package com.recipeapp.model;

/**
 * Ingredient
 */
public class Ingredient {

    // 材料の名前
    private String name;

    public Ingredient(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}