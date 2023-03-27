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

    List<Recipe> recipes;

    public CardAdapter(List<Recipe> recipes){
        this.recipes = recipes;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        CardViewHolder pvh = new CardViewHolder(v);
        return pvh;
    }


    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        holder.text1.setText(recipes.get(position).getTextTest1());
        holder.text2.setText(recipes.get(position).getTextTest2());
        //Ion.with(holder.stockPhoto).error(R.mipmap.ic_launcher).load(recipes.get(position).getPhotoId());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

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
