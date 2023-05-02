package edu.iastate.cs309.hb6.foodtime.ui.cookbook;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.iastate.cs309.hb6.foodtime.R;

public class AddDirectionsAdapter extends RecyclerView.Adapter<AddDirectionsAdapter.CardViewHolder> {
    private final Context context;
    private final List<Direction> directions;
    private OnEditTextChange onEditTextChange;

    public AddDirectionsAdapter(Context context, List<Direction> directions, OnEditTextChange onEditTextChange) {
        this.context = context;
        this.directions = directions;
        this.onEditTextChange = onEditTextChange;

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
    public void onBindViewHolder(@NonNull AddDirectionsAdapter.CardViewHolder holder, int position) {
        int truePosition = position + 1;
        holder.txtDirection.setText("Direction " + truePosition);
        holder.edtDirection.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                onEditTextChange.onTextChanged(holder.getAbsoluteAdapterPosition(), charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * @return
     */
    @Override
    public int getItemCount() {
        return 10;
    }

    public List<Direction> getDirections () {
        return directions;
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
            txtDirection = itemView.findViewById((R.id.txtDirection));
            edtDirection = itemView.findViewById(R.id.edtDirection);
        }

    }
}
