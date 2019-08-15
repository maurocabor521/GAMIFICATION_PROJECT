package com.example.asus.dconfo_app.presentation.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.domain.model.Grupo;

import java.util.List;


/**
 * Created by Andrés Cabal on 20/12/2018.
 */

public class EjerciciosDocenteAdapter extends RecyclerView.Adapter<EjerciciosDocenteAdapter.EjerciciosHolder> implements View.OnClickListener {

    List<Grupo> listaEstudiantes;
    private View.OnClickListener listener;

    public EjerciciosDocenteAdapter(List<Grupo> listaGrupos) {
        this.listaEstudiantes = listaEstudiantes;
    }


    @NonNull
    @Override
    public EjerciciosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_cursos_docente, parent, false);
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
        if (listaEstudiantes.size() != 0) {
            holder.txtidEjercicio.setText(listaEstudiantes.get(position).getIdGrupo().toString());
            holder.txtnameEjercicio.setText(listaEstudiantes.get(position).getNameGrupo().toString());
            holder.txtidActividad.setText(listaEstudiantes.get(position).getCurso_idCurso().toString());
            holder.txtidTipo.setText(listaEstudiantes.get(position).getIdInstituto().toString());

        } else {

        }

    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }


    @Override
    public int getItemCount() {
        return listaEstudiantes.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    public class EjerciciosHolder extends RecyclerView.ViewHolder {
        TextView txtidEjercicio, txtnameEjercicio, txtidActividad, txtidTipo;

        public EjerciciosHolder(View itemView) {
            super(itemView);
            txtidEjercicio = (TextView) itemView.findViewById(R.id.txtIDgrupo_LCD);
            txtnameEjercicio = (TextView) itemView.findViewById(R.id.txtNombreGrupo_LCD);
            txtidActividad = (TextView) itemView.findViewById(R.id.txtID_Curso_LCD);
            txtidTipo = (TextView) itemView.findViewById(R.id.txtInstituto_LCD);

        }
    }
}
