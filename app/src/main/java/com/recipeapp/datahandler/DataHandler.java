package com.recipeapp.datahandler;

import java.io.IOException;
import java.util.ArrayList;

import com.recipeapp.model.Recipe;

/**
 * DataHandler
 */
public interface DataHandler {

    // 現在のモードを返す
    public String getMode();

    // レシピデータを読み込んで、 Recipe オブジェクトのリストとして返す
    public ArrayList<Recipe> readData() throws IOException;

    // 指定された Recipe オブジェクトを追加
    public void writeData(Recipe recipe) throws IOException;

    // 指定されたキーワードに一致する Recipe オブジェクトのリストを返す
    public ArrayList<Recipe> searchData(String keyword) throws IOException;
}