package com.recipeapp.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import com.recipeapp.datahandler.DataHandler;
import com.recipeapp.model.Ingredient;
import com.recipeapp.model.Recipe;

public class RecipeUI {
    private BufferedReader reader;
    private DataHandler dataHandler;

    public RecipeUI(DataHandler dataHandler) {
        reader = new BufferedReader(new InputStreamReader(System.in));
        this.dataHandler = dataHandler;
    }

    public void displayMenu() {

        System.out.println("Current mode: " + dataHandler.getMode());

        while (true) {
            try {
                System.out.println();
                System.out.println("Main Menu:");
                System.out.println("1: Display Recipes");
                System.out.println("2: Add New Recipe");
                System.out.println("3: Search Recipe");
                System.out.println("4: Exit Application");
                System.out.print("Please choose an option: ");

                String choice = reader.readLine();

                switch (choice) {
                    case "1":
                        this.displayRecipes();
                        break;
                    case "2":
                        this.addNewRecipe();
                        break;
                    case "3":
                        this.searchRecipe();
                        break;
                    case "4":
                        System.out.println("Exiting the application.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please select again.");
                        break;
                }
            } catch (IOException e) {
                System.out.println("Error reading input from user: " + e.getMessage());
            }
        }
    }

    private void displayRecipes() {
        try {
            // レシピデータを取得
            ArrayList<Recipe> recipes = dataHandler.readData();

            if (recipes.isEmpty()) {
                System.out.println("\nNo recipes available.");
            } else {
                System.out.println("\nRecipes:");
                System.out.println("-----------------------------------");

                // 各レシピを出力
                recipes.forEach(recipe -> {
                    // レシピ名を出力
                    System.out.println("Recipe Name: " + recipe.getName());

                    // 材料を出力
                    System.out.print("Main Ingred1ients: ");
                    // 材料名のリストを宣言
                    ArrayList<String> ingredientNames = new ArrayList<>();
                    recipe.getIngredients().forEach(ingredient -> ingredientNames.add(ingredient.getName()));
                    System.out.println(String.join(", ", ingredientNames));

                    System.out.println("-----------------------------------");
                });
            }
        } catch (IOException ex) {
            System.out.println("Error reading file: " + ex.getMessage());
        }
    }

    private void addNewRecipe() {
        System.out.println("\nAdding a new recipe.");
        System.out.print("Enter recipe name: ");

        try {
            // レシピ名入力受付
            String recipeName = reader.readLine();
            // 材料の入力受付
            ArrayList<Ingredient> ingredients = new ArrayList<>();
            System.out.println("Enter ingredients (type 'done' when finished):");
            while (true) {
                System.out.print("Ingredient: ");
                String content = reader.readLine();

                // done で入力終了
                if (content.equals("done")) {
                    break;
                } else {
                    ingredients.add(new Ingredient(content));
                }
            }
            // ファイルに書き込み
            dataHandler.writeData(new Recipe(recipeName, ingredients));
        } catch (IOException e) {
            System.out.println("Failed to add new recipe: " + e.getMessage());
        }

        System.out.println("Recipe added successfully.");
    }

    private void searchRecipe() {
        System.out.print("Enter search query (e.g., 'name=Tomato&ingredient=Garlic'): ");
        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            String keyword = reader.readLine();

            recipes = dataHandler.searchData(keyword);
        } catch (Exception ex) {
            System.out.println("Failed to search recipes: " + ex.getMessage());
        }

        // 出力
        if (recipes.isEmpty()) {
            System.out.println("\nNo matching recipes found.");
        } else {
            System.out.println("\nMatching Recipes:\n");

            // 各レシピを出力
            recipes.forEach(recipe -> {
                // レシピ名を出力
                System.out.println("Name: " + recipe.getName());

                // 材料を出力
                System.out.print("Ingred1ients: ");
                // 材料名のリストを宣言
                ArrayList<String> ingredientNames = new ArrayList<>();
                recipe.getIngredients().forEach(ingredient -> ingredientNames.add(ingredient.getName()));
                System.out.println(String.join(", ", ingredientNames));

                System.out.println();
            });
        }
    }
}
