package edu.iastate.cs309.hb6.foodtime.ui.cookbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.iastate.cs309.hb6.foodtime.R;

/**
 * CardAdapter to render Cookbook Cards
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    /** The current context */
    private final Context context;

    /** Recipes to populate cards to be used by RecyclerView */
    private final ArrayList<String> recipes;
    private String UID;


    /**
     * CardAdapter
     *
     * @param context - The current context.
     * @param recipes - The data (recipes from database) to populate cards to be used by RecyclerView.
     */
    public CardAdapter(Context context, ArrayList<String> recipes, String UID) {
        this.context = context;
        this.recipes = recipes;
        this.UID = UID;
    }

    public String getUID() {
        return UID;
    }

    /**
     * View holder for Card View
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_layout, parent, false);
        return new CardViewHolder(v);
    }

    /**
     * onBindViewHolder
     * When the card is created, set properties, such as the recipe title and image
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        TextView recipeTitle = holder.recipeTitle;
        recipeTitle.setText(recipes.get(position));
    }

    /**
     * Get the number of recipes (items) in the Cookbook
     * @return The total number of items in the data set held by the adapter (Cookbook).
     */
    @Override
    public int getItemCount() {
        return recipes.size();
    }

    /**
     * onAttachedToRecyclerView
     * @param recyclerView - The RecyclerView instance which is currently connected to this adapter.
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    /**
     * CardViewHolder
     * <p>
     * The contents of an instance of CardView
     */
    public static class CardViewHolder extends RecyclerView.ViewHolder {

        /** The title of the recipe */
        private final TextView recipeTitle;
//        private final String UID;

        /**
         * CardViewHolder
         * Set properties of the CardView, such as handling ViewRecipeActivity intent when clicked
         *
         * @param itemView - The CardView
         */
        public CardViewHolder(View itemView) {
            super(itemView);
            recipeTitle = itemView.findViewById((R.id.recipeTitle));



            // Create intent to ViewRecipeActivity when card is clicked
            itemView.setOnClickListener(view -> {
                Intent viewRecipeIntent = new Intent(view.getContext(), ViewRecipeActivity.class);
                viewRecipeIntent.putExtra("RecipeTitle", recipeTitle.getText().toString());
//                viewRecipeIntent.putExtra("UID", UID);
                Toast.makeText(view.getContext(), recipeTitle.getText().toString(), Toast.LENGTH_LONG).show();
                view.getContext().startActivity(viewRecipeIntent);
            });
        }
    }

}
