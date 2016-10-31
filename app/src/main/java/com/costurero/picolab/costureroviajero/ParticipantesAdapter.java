package com.costurero.picolab.costureroviajero;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by PiCoLab on 2016-10-30.
 */

public class ParticipantesAdapter extends RecyclerView.Adapter<ParticipantesAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Participante> mParticipantes;
    private Context contexto;
    private View generalView;

    public ParticipantesAdapter(Context context, List<Participante> parts) {
        mParticipantes = parts;
        inflater = LayoutInflater.from(context);
        contexto=context;
    }

    @Override
    public ParticipantesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Context context = parent.getContext();
        //inflater = LayoutInflater.from(context);
        Log.println(Log.INFO,"Info", "Pos2");

        // Inflate the custom layout
        generalView = inflater.inflate(R.layout.container_encuentros, parent, false);
        ViewHolder viewHolder = new ViewHolder(generalView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ParticipantesAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Participante parts= mParticipantes.get(position);
        Log.println(Log.INFO,"Info", "Pos 1");

        // Set item views based on the data model
        TextView fechaEnc = viewHolder.fechTextView;
        fechaEnc.setText(parts.getNombre());
        ImageView flecha=viewHolder.flechaImaView;
        flecha.setImageResource(R.drawable.ojo);
    }
    @Override
    public int getItemCount() {
        return mParticipantes.size();
    }
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView fechTextView;
        public ImageView flechaImaView;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            fechTextView = (TextView) itemView.findViewById(R.id.fechaEncuentro_txt);
            flechaImaView= (ImageView) itemView.findViewById(R.id.imageView);
            // Attach a click listener to the entire row view
            itemView.setOnClickListener(this);

        }
        // Handles the row being being clicked
        @Override
        public void onClick(View view) {
            int position = getLayoutPosition(); // gets item position
            Participante part = mParticipantes.get(position);
            Intent intent = new Intent(contexto,ParicipantesActivity.class);
            intent.putExtra("ID_PARTICIPANTE", ""+part.getId());
            intent.putExtra("NOMBRE_PARTICIPANTE", ""+part.getNombre());
            intent.putExtra("PATH_PARTICIPANTE", ""+part.getPath());
            intent.putExtra("HISTORIA_PARTICIPANTE", ""+part.getHistoria());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            contexto.startActivity(intent);
        }

    }
}
