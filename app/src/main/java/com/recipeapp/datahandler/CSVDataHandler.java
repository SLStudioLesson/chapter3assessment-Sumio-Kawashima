package com.recipeapp.datahandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.recipeapp.model.Ingredient;
import com.recipeapp.model.Recipe;

/**
 * CSVDataHandler
 */
public class CSVDataHandler implements DataHandler {

    // レシピデータを格納するCSVファイルのパス
    private String filePath;

    public CSVDataHandler() {
        this.filePath = "app/src/main/resources/recipes.csv";
    }

    public CSVDataHandler(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getMode() {
        return "CSV";
    }

    @Override
    public ArrayList<Recipe> readData() throws IOException {
        // レシピを格納するリストを宣言
        ArrayList<Recipe> recipes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            // recipes.csv ファイルからレシピデータを読み込み、リストに格納
            String line;
            while ((line = reader.readLine()) != null) {
                // 文字列をレシピ名と材料に分割
                String[] keyValue = line.split(",", 2);
                String recipeName = keyValue[0];
                String[] ingredientNames = keyValue[1].split(", ");

                // 材料のリストを宣言
                ArrayList<Ingredient> ingredients = new ArrayList<>();
                // 材料をリストにまとめる
                for (String ingredientName : ingredientNames) {
                    Ingredient ingredient = new Ingredient(ingredientName);
                    ingredients.add(ingredient);
                }

                // レシピ名と材料のリストからレシピを生成
                Recipe recipe = new Recipe(recipeName, ingredients);

                // レシピをリストに追加
                recipes.add(recipe);
            }
        }

        return recipes;
    }

    @Override
    public void writeData(Recipe recipe) throws IOException {
        // 材料の名前のリストを作成
        ArrayList<String> ingredients = new ArrayList<>();
        recipe.getIngredients().forEach(ingredient -> ingredients.add(ingredient.getName()));
        // 材料の名前をカンマで区切った文字列を生成
        String ingredientNames = String.join(", ", ingredients);
        // レシピ名と材料をまとめた文字列を生成
        String content = String.join(",", recipe.getName(), ingredientNames);

        // ファイルに書き込む
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(content);
            writer.newLine();
        }
        ;
    }

    @Override
    public ArrayList<Recipe> searchData(String keyword) throws IOException {
        String searchName = "";
        String searchIngredient = "";
        // 検索ワードをクエリから抽出
        if (keyword.contains("&")) {
            String[] keyValues = keyword.split("&");

            for (String keyValue : keyValues) {
                String[] query = keyValue.split("=");
                switch (query[0]) {
                    case "name":
                        searchName = query[1];
                        break;
                    case "ingredient":
                        searchIngredient = query[1];
                        break;
                }
            }
        } else {
            String[] query = keyword.split("=");
            switch (query[0]) {
                case "name":
                    searchName = query[1];
                    break;
                case "ingredient":
                    searchIngredient = query[1];
                    break;
            }
        }

        // レシピデータを取得
        ArrayList<Recipe> recipes = readData();
        // 一致したレシピを格納
        ArrayList<Recipe> searchedRecipe = new ArrayList<>();

        for (Recipe recipe : recipes) {
            // レシピ名が一致、もしくはレシピ名が空白の場合
            if (searchName.isEmpty() || recipe.getName().contains(searchName)) {
                // 材料が空白ならそのまま追加
                for (Ingredient ingredient : recipe.getIngredients()) {
                    if (ingredient.getName().contains(searchIngredient)) {
                        searchedRecipe.add(recipe);
                        break;
                    }
                }
            }
        }

        return searchedRecipe;
    }
}