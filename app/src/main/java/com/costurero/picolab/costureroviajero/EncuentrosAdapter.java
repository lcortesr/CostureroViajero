package com.costurero.picolab.costureroviajero;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

/**
 * Created by gapiedrahita on 12/04/2016.
 */
// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class EncuentrosAdapter extends RecyclerView.Adapter<EncuentrosAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Encuentro> mEncuentros;
    private Context contexto;
    private View generalView;

    public EncuentrosAdapter(Context context, List<Encuentro> encs) {
        mEncuentros = encs;
        inflater = LayoutInflater.from(context);
        contexto=context;
    }

    @Override
    public EncuentrosAdapter .ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Context context = parent.getContext();
        //inflater = LayoutInflater.from(context);
        Log.println(Log.INFO,"Info", "Pos2");

        // Inflate the custom layout
        generalView = inflater.inflate(R.layout.container_encuentros, parent, false);
        ViewHolder viewHolder = new ViewHolder(generalView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(EncuentrosAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Encuentro encu = mEncuentros.get(position);
        Log.println(Log.INFO,"Info", "Pos 1");

        // Set item views based on the data model
        TextView fechaEnc = viewHolder.fechTextView;
        fechaEnc.setText(encu.getFecha());
    }
    @Override
    public int getItemCount() {
        return mEncuentros.size();
    }
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView fechTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            fechTextView = (TextView) itemView.findViewById(R.id.fechaEncuentro_txt);
            // Attach a click listener to the entire row view
            itemView.setOnClickListener(this);

        }
        // Handles the row being being clicked
        @Override
        public void onClick(View view) {
            int position = getLayoutPosition(); // gets item position
            Encuentro encuent = mEncuentros.get(position);
            Intent intent = new Intent(contexto,EncuentroActivity.class);
            intent.putExtra("ID_ENCUENTRO", ""+encuent.getId());

            intent.putExtra("FECHA_ENCUENTRO", ""+encuent.getFecha());
            intent.putExtra("NOMBRE_COSTURERO", ""+encuent.getNombreCosturero());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            contexto.startActivity(intent);
        }

    }
}