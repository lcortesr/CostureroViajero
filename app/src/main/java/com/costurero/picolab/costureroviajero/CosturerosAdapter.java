package com.costurero.picolab.costureroviajero;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

/**
 * Created by gapiedrahita on 12/04/2016.
 */
// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class CosturerosAdapter extends RecyclerView.Adapter<CosturerosAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Costurero> mConstureros;
    private Context contexto;
    private View generalView;

    public CosturerosAdapter(Context context, List<Costurero> costs) {
        mConstureros = costs;
        inflater = LayoutInflater.from(context);
        contexto=context;
    }

    @Override
    public CosturerosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.println(Log.INFO,"Info", "Pos2");
        // Inflate the custom layout
        generalView = inflater.inflate(R.layout.container_costurero, parent, false);
        ViewHolder viewHolder = new ViewHolder(generalView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(CosturerosAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Costurero cost = mConstureros.get(position);
        Log.println(Log.INFO,"Info", "Pos 1");

        // Set item views based on the data model
        TextView nombreCosturero = viewHolder.nameTextView;
        nombreCosturero.setText(cost.getNombre());
        TextView municipioCosturero = viewHolder.munTextView;
        municipioCosturero.setText(cost.getMunicipio());
        TextView lugarCosturero = viewHolder.lugTextView;
        lugarCosturero.setText(cost.getLugar());
    }
    @Override
    public int getItemCount() {
        return mConstureros.size();
    }
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public TextView munTextView;
        public TextView lugTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.nombreCosturero_txt);
            munTextView = (TextView) itemView.findViewById(R.id.municipioCosturero_txt);
            lugTextView = (TextView) itemView.findViewById(R.id.lugCosturero_txt);
            // Attach a click listener to the entire row view
            itemView.setOnClickListener(this);

        }
        // Handles the row being being clicked
        @Override
        public void onClick(View view) {
            int position = getLayoutPosition(); // gets item position
            Costurero costu = mConstureros.get(position);
            Intent intent = new Intent(contexto,EncuentrosActivity.class);
            intent.putExtra("ID_COSTURERO", ""+position);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            contexto.startActivity(intent);
        }

    }
}