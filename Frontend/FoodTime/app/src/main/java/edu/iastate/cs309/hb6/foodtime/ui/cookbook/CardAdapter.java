package edu.iastate.cs309.hb6.foodtime.ui.cookbook;


import android.content.ClipData.*;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.iastate.cs309.hb6.foodtime.R;



public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder>{

    private Context context;
    private ArrayList<String> recipes;

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public CardView cv;
        public TextView text2;
        public View view;
        public Context context;
        public String currRecipe;
        public CardViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            cv = (CardView)itemView.findViewById(R.id.cv);
            text2 = (TextView)itemView.findViewById((R.id.textTest2));
            context = view.getContext();
            view.setOnClickListener(view -> {
                Intent viewRecipeIntent = new Intent(view.getContext(), ViewRecipeActivity.class);
                Toast.makeText(view.getContext(), "Clicked", Toast.LENGTH_LONG).show();
                view.getContext().startActivity(viewRecipeIntent);
            });
        }
    }

    //constructor

    public CardAdapter(Context context, ArrayList<String> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    /**
     * View holder for Card View
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return
     */
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_layout, parent, false);
        CardViewHolder pvh = new CardViewHolder(v);
        return pvh;
    }

    /**
     *onBindViewHolder
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {

        TextView tt2 = holder.text2;
        tt2.setText(recipes.get(position));
        Log.d("RECIPE", recipes.toString());

    }
    /**
     * getItemCount
     *
     *getter method to get the number of recipes in the cook book.
     * @return
     */
    @Override
    public int getItemCount() {
        return recipes.size();
    }

    /**
     *
     * @param recyclerView The RecyclerView instance which started observing this adapter.
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    /**
     *
     */


}
