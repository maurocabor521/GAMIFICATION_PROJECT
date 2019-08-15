package com.example.asus.dconfo_app.presentation.view.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.domain.model.EjercicioG1;
import com.example.asus.dconfo_app.domain.model.EjercicioG2;

import java.util.List;


/**
 * Created by Andrés Cabal on 20/12/2018.
 */

public class TipoEjerciciosG2DocenteAdapter extends RecyclerView.Adapter<TipoEjerciciosG2DocenteAdapter.EjerciciosHolder> implements View.OnClickListener {

    List<EjercicioG2> listaEjerciciosG2;
    private View.OnClickListener listener;
    Context context;

    public TipoEjerciciosG2DocenteAdapter(List<EjercicioG2> listaEjerciciosG2,Context context) {
        this.listaEjerciciosG2 = listaEjerciciosG2;
        this.context=context;
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
        if (listaEjerciciosG2.size() != 0) {
            holder.txtidEjercicio.setText(listaEjerciciosG2.get(position).getIdEjercicioG2().toString());
            holder.txtidDocente.setText(listaEjerciciosG2.get(position).getIdDocente().toString());
            holder.txtnameEjercicio.setText(listaEjerciciosG2.get(position).getNameEjercicioG2().toString());
            holder.txtidActividad.setText(listaEjerciciosG2.get(position).getIdActividad().toString());
            holder.txtidTipo.setText(listaEjerciciosG2.get(position).getIdTipo().toString());
            holder.ll_item_ejercicio.setBackground(ContextCompat.getDrawable(context, R.color.colorAccent));

        } else {

        }

    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }


    @Override
    public int getItemCount() {
        return listaEjerciciosG2.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    public class EjerciciosHolder extends RecyclerView.ViewHolder {
        TextView txtidEjercicio,txtidDocente, txtnameEjercicio, txtidActividad, txtidTipo;
        LinearLayout ll_item_ejercicio;

        public EjerciciosHolder(View itemView) {
            super(itemView);
            txtidEjercicio = (TextView) itemView.findViewById(R.id.txt_ejercicioId_home);
            txtidDocente = (TextView) itemView.findViewById(R.id.txt_docenteId_home);
            txtnameEjercicio = (TextView) itemView.findViewById(R.id.txt_ejercicioNombre_home);
            txtidActividad = (TextView) itemView.findViewById(R.id.txt_ejercicioActividad_home);
            txtidTipo = (TextView) itemView.findViewById(R.id.txt_ejercicioTipo_home);
            ll_item_ejercicio = (LinearLayout) itemView.findViewById(R.id.ll_item_ejercicio);

        }
    }
}
