package edu.iastate.cs309.hb6.foodtime.ui.cookbook;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.iastate.cs309.hb6.foodtime.R;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.CardViewHolder> {
    private final Context context;
    private final ArrayList<Object> strings;
    private final ArrayList<Object> quantity;
    private final ArrayList<Object> types;

    public IngredientsAdapter(Context context, ArrayList<Object> strings, ArrayList<Object> quantity, ArrayList<Object> types) {
        this.context = context;
        this.strings = strings;
        this.quantity = quantity;
        this.types = types;

    }

    /**
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return
     */
    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.ingredients_cardview, parent, false);
        return new CardViewHolder(v);
    }

    /**
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        EditText ingredientTitle = holder.edtIngredients;
        ingredientTitle.setText(strings.get(position).toString());
        EditText quantityTitle = holder.edtQuantity;
        quantityTitle.setText(quantity.get(position).toString());
        EditText typeTitle = holder.edtType;
        typeTitle.setText(types.get(position).toString());

    }

    /**
     * @return
     */
    @Override
    public int getItemCount() {
        return strings.size();
    }

    /**
     * @param recyclerView The RecyclerView instance which started observing this adapter.
     */
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        /** txtViews */
        private final TextView txtIngredients;
        private final TextView txtQuantity;
        private final TextView txtType;

        /** edtViews*/
        private final EditText edtIngredients;
        private final EditText edtQuantity;
        private final EditText edtType;


        /**
         * CardViewHolder
         * Set properties of the CardView, such as handling ViewRecipeActivity intent when clicked
         *
         * @param itemView - The CardView
         */
        public CardViewHolder(View itemView) {
            super(itemView);

            /*initialize widgets*/
            txtIngredients = itemView.findViewById(R.id.txtIngredient);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            txtType = itemView.findViewById(R.id.txtType);
            edtIngredients = itemView.findViewById(R.id.edtIngredient);
            edtQuantity = itemView.findViewById(R.id.edtQuantity);
            edtType = itemView.findViewById(R.id.edtType);


        }
    }
}
