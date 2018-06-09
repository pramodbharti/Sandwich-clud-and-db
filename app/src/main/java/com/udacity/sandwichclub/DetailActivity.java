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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.name_tv)
    TextView main_name;
    @BindView(R.id.also_known_as_tv)
    TextView also_known_as;
    @BindView(R.id.place_of_origin_tv)
    TextView place_of_origin;
    @BindView(R.id.ingredients_tv)
    TextView ingredients;
    @BindView(R.id.description_tv)
    TextView descriptions;
    @BindView(R.id.image_iv)
    ImageView sandwich_image;

    Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

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
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(sandwich_image);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        if (sandwich.getMainName() != null)
            main_name.setText(sandwich.getMainName());
        else
            main_name.setText(getResources().getString(R.string.not_available));
        if (sandwich.getPlaceOfOrigin() != null)
            place_of_origin.setText(sandwich.getPlaceOfOrigin());
        else
            place_of_origin.setText(getResources().getString(R.string.not_available));
        if (sandwich.getDescription() != null)
            descriptions.setText(sandwich.getDescription());
        else
            descriptions.setText(getResources().getString(R.string.not_available));

        List<String> also_known = sandwich.getAlsoKnownAs();
        List<String> ingredient = sandwich.getIngredients();

        if (ingredient.size() > 0) {
            for (String string : ingredient) {
                ingredients.append(string + "\n");
            }
        } else {
            ingredients.setText(getResources().getString(R.string.not_available));
        }

        if (also_known.size() > 0) {
            for (String string : also_known) {
                also_known_as.append(string + "\n");
            }
        } else {
            also_known_as.setText(getResources().getString(R.string.not_available));
        }

    }
}
