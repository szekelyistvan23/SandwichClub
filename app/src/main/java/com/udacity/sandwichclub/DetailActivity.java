package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

/**
 * Extracts data from strings.xml and populates the UI for the selected list item.
 */

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private Sandwich sandwich;
    private TextView knownAs;
    private TextView placeOfOrigin;
    private TextView description;
    private TextView ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        knownAs = findViewById(R.id.also_known_tv);
        placeOfOrigin = findViewById(R.id.origin_tv);
        description = findViewById(R.id.description_tv);
        ingredients = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = new Sandwich();
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e){
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }
    /** If sandwich object equals null displays a toast message. */
    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /** Populates the UI with data from a Sandwich object. */
    private void populateUI() {
        knownAs.setText(arrayToString(sandwich.getAlsoKnownAs()));
        placeOfOrigin.setText(sandwich.getPlaceOfOrigin());
        description.setText(sandwich.getDescription());
        ingredients.setText(arrayToString(sandwich.getIngredients()));
    }
    /** Transforms the elements of a String array into a String. */
    private String arrayToString (List<String> array){
        String s = "";
        if (array != null && array.size()>0){
            for (int i = 0; i < array.size(); i++) {
                if (i < array.size()-1) {
                    s = s + array.get(i) + ", ";
                } else {
                    s = s + array.get(i);
                }
            }
        } else {
            return "-";
        }
        return s;
    }
}
