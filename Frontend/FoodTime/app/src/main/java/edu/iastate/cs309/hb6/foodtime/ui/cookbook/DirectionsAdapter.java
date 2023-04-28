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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.iastate.cs309.hb6.foodtime.R;

public class DirectionsAdapter extends RecyclerView.Adapter<DirectionsAdapter.CardViewHolder> {

    private final Context context;
    private final ArrayList<String> directions = new ArrayList<>();

    public DirectionsAdapter(Context context) {
        this.context = context;
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
    public void onBindViewHolder(@NonNull DirectionsAdapter.CardViewHolder holder, int position) {
        TextView tt1 = holder.txtDirection;
        int truePosition = position + 1;
        tt1.setText("Direction " + truePosition);
    }

    /**
     * @return
     */
    @Override
    public int getItemCount() {
        return 10;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        private  TextView txtDirection;
        private EditText edtDirection;
        /**
         *
         * @param itemView
         */
        public CardViewHolder(View itemView) {
            super(itemView);
            CardView cv = itemView.findViewById(R.id.cvDirections);
            Context context = itemView.getContext();

            txtDirection = itemView.findViewById((R.id.txtDirection));
            edtDirection = itemView.findViewById(R.id.edtIngredient);
        }
    }
}
