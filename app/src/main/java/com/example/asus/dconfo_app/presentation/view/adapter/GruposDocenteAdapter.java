package com.example.asus.dconfo_app.presentation.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.domain.model.Curso;
import com.example.asus.dconfo_app.domain.model.Grupo;

import java.util.List;


/**
 * Created by Andrés Cabal on 20/12/2018.
 */

public class GruposDocenteAdapter extends RecyclerView.Adapter<GruposDocenteAdapter.GruposHolder> implements View.OnClickListener {

    List<Grupo> listaGrupos;
    private View.OnClickListener listener;

    public GruposDocenteAdapter(List<Grupo> listaGrupos) {
        this.listaGrupos = listaGrupos;
    }


    @NonNull
    @Override
    public GruposHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_cursos_docente, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        vista.setOnClickListener(this);
        return new GruposHolder(vista);
    }

    @Override
    public void onBindViewHolder(GruposHolder holder, int position) {
        // holder.txtidGrupo.setText(listaGrupos.get(position).getIdGrupo().toString());
       // Log.i("size", "lista_: " + listaGrupos.size());
        if (listaGrupos.size() != 0) {
            holder.txtidGrupo.setText(listaGrupos.get(position).getIdGrupo().toString());
            holder.txtNombreGrupo.setText(listaGrupos.get(position).getNameGrupo().toString());
            holder.txtidCurso.setText(listaGrupos.get(position).getCurso_idCurso().toString());
            holder.txtidInst.setText(listaGrupos.get(position).getIdInstituto().toString());

        } else {

        }

    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }


    @Override
    public int getItemCount() {
        return listaGrupos.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    public class GruposHolder extends RecyclerView.ViewHolder {
        TextView txtidGrupo, txtidCurso, txtNombreGrupo, txtidInst;

        public GruposHolder(View itemView) {
            super(itemView);
            txtidGrupo = (TextView) itemView.findViewById(R.id.txtIDgrupo_LCD);
            txtNombreGrupo = (TextView) itemView.findViewById(R.id.txtNombreGrupo_LCD);
            txtidCurso = (TextView) itemView.findViewById(R.id.txtID_Curso_LCD);
            txtidInst = (TextView) itemView.findViewById(R.id.txtInstituto_LCD);

        }
    }
}
