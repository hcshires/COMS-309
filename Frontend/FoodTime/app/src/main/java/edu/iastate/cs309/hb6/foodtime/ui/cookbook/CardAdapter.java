package edu.iastate.cs309.hb6.foodtime.ui.cookbook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.iastate.cs309.hb6.foodtime.R;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder>{

    private List<Recipe> recipes;

    /**
     * Constructor for Card Adapter
     * @param recipes
     */
    public CardAdapter(List<Recipe> recipes){
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        CardViewHolder pvh = new CardViewHolder(v);
        return pvh;
    }

    /**
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        TextView tt1 = holder.text1;
        //tt1.setText(recipe.getTextTest1());
        TextView tt2 = holder.text2;
        //tt2.setText(recipe.getTextTest2());
//        Ion.with(holder.stockPhoto).error(R.mipmap.ic_launcher).load(recipes.get(position).getPhotoId());
    }
    /**
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
    public static class CardViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView text1;
        TextView text2;


        CardViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            text1 = (TextView)itemView.findViewById(R.id.textTest1);
            text2 = (TextView)itemView.findViewById(R.id.textTest2);

        }
    }

}
