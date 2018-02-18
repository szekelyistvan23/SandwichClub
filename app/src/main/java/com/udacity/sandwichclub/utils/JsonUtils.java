package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This class with its method parses Json data into a Sandwich object.
 */

public class JsonUtils {

    /** Parses Json data into a Sandwich object. */
    public static Sandwich parseSandwichJson(String json) throws JSONException {
        Sandwich sandwich = new Sandwich();
        JSONObject sandwichDetails = new JSONObject(json);

        JSONObject temporalName = new JSONObject(sandwichDetails.getString("name"));
        JSONArray ingredients = sandwichDetails.getJSONArray("ingredients");
        JSONArray knownAs = temporalName.getJSONArray("alsoKnownAs");

        sandwich.setMainName(temporalName.getString("mainName"));
        sandwich.setAlsoKnownAs(jsonArrayToStringArray(knownAs));
        sandwich.setPlaceOfOrigin(sandwichDetails.getString("placeOfOrigin"));

        if (sandwichDetails.getString("placeOfOrigin").equals("")){
            sandwich.setPlaceOfOrigin("unknown");
        }

        sandwich.setDescription(sandwichDetails.getString("description"));
        sandwich.setImage(sandwichDetails.getString("image"));
        sandwich.setIngredients(jsonArrayToStringArray(ingredients));

        return sandwich;
    }

    /** Transforms a JSONArray to an ArrayList<String>. */
    private static List<String> jsonArrayToStringArray(JSONArray array){
        ArrayList<String> temporalArray = new ArrayList<>();
        if (array != null && array.length()> 0){
            for (int i = 0; i < array.length(); i++){
                try {
                    temporalArray.add(array.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
      return temporalArray;
    }
}
