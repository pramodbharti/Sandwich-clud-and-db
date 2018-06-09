package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject sandwichJson = new JSONObject(json);
            JSONObject name = sandwichJson.getJSONObject("name");
            String mainName = name.getString("mainName");
            JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
            List<String> listAlsoKnownAs = new ArrayList<>();
            for (int i = 0; i < alsoKnownAs.length(); i++) {
                listAlsoKnownAs.add(alsoKnownAs.getString(i));
            }
            String placeOfOrigin = sandwichJson.getString("placeOfOrigin");
            String description = sandwichJson.getString("description");
            String image = sandwichJson.getString("image");
            JSONArray ingredients = sandwichJson.getJSONArray("ingredients");
            List<String> listIngredients = new ArrayList<>();
            for (int i = 0; i < ingredients.length(); i++) {
                listIngredients.add(ingredients.getString(i));
            }
            return new Sandwich(mainName, listAlsoKnownAs, placeOfOrigin, description, image, listIngredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
