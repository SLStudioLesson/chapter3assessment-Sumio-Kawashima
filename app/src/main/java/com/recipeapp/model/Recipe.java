package com.recipeapp.model;

import java.util.ArrayList;

/**
 * Recipe
 */
public class Recipe {

    // レシピの名前
    private String name;
    // レシピの材料リスト
    private ArrayList<Ingredient> ingredients;

    public Recipe(String name, ArrayList<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return this.ingredients;
    }
}