package com.example.asus.dconfo_app.presentation.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.domain.model.EjercicioG1;
import com.example.asus.dconfo_app.domain.model.Grupo;

import java.util.List;


/**
 * Created by Andrés Cabal on 20/12/2018.
 */

public class TipoEjerciciosDocenteAdapter extends RecyclerView.Adapter<TipoEjerciciosDocenteAdapter.EjerciciosHolder> implements View.OnClickListener {

    List<EjercicioG1> listaEjerciciosG1;
    private View.OnClickListener listener;

    public TipoEjerciciosDocenteAdapter(List<EjercicioG1> listaEjerciciosG1) {
        this.listaEjerciciosG1 = listaEjerciciosG1;
    }


    @NonNull
    @Override
    public EjerciciosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ejercicio, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        vista.setOnClickListener(this);
        return new EjerciciosHolder(vista);
    }

    @Override
    public void onBindViewHolder(EjerciciosHolder holder, int position) {
        // holder.txtidGrupo.setText(listaGrupos.get(position).getIdGrupo().toString());
       // Log.i("size", "lista_: " + listaGrupos.size());
        if (listaEjerciciosG1.size() != 0) {
            holder.txtidEjercicio.setText(listaEjerciciosG1.get(position).getIdEjercicio().toString());
            holder.txtidDocente.setText(listaEjerciciosG1.get(position).getIdDocente().toString());
            holder.txtnameEjercicio.setText(listaEjerciciosG1.get(position).getNameEjercicio().toString());
            holder.txtidActividad.setText(listaEjerciciosG1.get(position).getIdActividad().toString());
            holder.txtidTipo.setText(listaEjerciciosG1.get(position).getIdTipo().toString());

        } else {

        }

    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }


    @Override
    public int getItemCount() {
        return listaEjerciciosG1.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    public class EjerciciosHolder extends RecyclerView.ViewHolder {
        TextView txtidEjercicio,txtidDocente, txtnameEjercicio, txtidActividad, txtidTipo;

        public EjerciciosHolder(View itemView) {
            super(itemView);
            txtidEjercicio = (TextView) itemView.findViewById(R.id.txt_ejercicioId_home);
            txtidDocente = (TextView) itemView.findViewById(R.id.txt_docenteId_home);
            txtnameEjercicio = (TextView) itemView.findViewById(R.id.txt_ejercicioNombre_home);
            txtidActividad = (TextView) itemView.findViewById(R.id.txt_ejercicioActividad_home);
            txtidTipo = (TextView) itemView.findViewById(R.id.txt_ejercicioTipo_home);

        }
    }
}
