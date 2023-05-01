package edu.iastate.cs309.hb6.foodtime.ui.cookbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.iastate.cs309.hb6.foodtime.R;

public class DirectionsAdaper extends RecyclerView.Adapter<DirectionsAdaper.CardViewHolder> {

    private final Context context;
    private final ArrayList<String> directions;

    public DirectionsAdaper(Context context, ArrayList<String> directions) {
        this.context = context;
        this.directions = directions;
    }

    /**
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return
     */
    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.directions_cardview, parent, false);
        return new CardViewHolder(v);
    }

    /**
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        TextView directionNum = holder.txtDirection;
        EditText direction = holder.edtDirection;
        int truePosition = position + 1;
        direction.setText(directions.get(position));
        directionNum.setText("Direction: " + truePosition);
    }

    /**
     * @return
     */
    @Override
    public int getItemCount() {
        return directions.size();
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
        private final TextView txtDirection;

        /** edtViews*/
        private final EditText edtDirection;


        /**
         * CardViewHolder
         * Set properties of the CardView, such as handling ViewRecipeActivity intent when clicked
         *
         * @param itemView - The CardView
         */
        public CardViewHolder(View itemView) {
            super(itemView);

            /*initialize widgets*/
            txtDirection = itemView.findViewById(R.id.txtDirection);
            edtDirection = itemView.findViewById(R.id.edtDirection);

        }
    }
}
