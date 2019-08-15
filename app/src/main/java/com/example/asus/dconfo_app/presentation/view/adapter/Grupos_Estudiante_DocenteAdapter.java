package com.example.asus.dconfo_app.presentation.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.domain.model.Grupo;
import com.example.asus.dconfo_app.domain.model.Grupo_Estudiantes;

import java.util.List;


/**
 * Created by Andrés Cabal on 20/12/2018.
 */

public class Grupos_Estudiante_DocenteAdapter extends RecyclerView.Adapter<Grupos_Estudiante_DocenteAdapter.GruposHolder> implements View.OnClickListener {

    List<Grupo_Estudiantes> listaGruposEstudiantes;
    private View.OnClickListener listener;

    public Grupos_Estudiante_DocenteAdapter(List<Grupo_Estudiantes> listaGruposEstudiantes) {
        this.listaGruposEstudiantes = listaGruposEstudiantes;
    }


    @NonNull
    @Override
    public GruposHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_grupo_estudiantes_docente, parent, false);
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
    /*    if (listaGrupos.size() != 0) {
            holder.txtidGrupo.setText(listaGrupos.get(position).getIdGrupo().toString());
            holder.txtNombreGrupo.setText(listaGrupos.get(position).getNameGrupo().toString());
            holder.txtidCurso.setText(listaGrupos.get(position).getCurso_idCurso().toString());
            holder.txtidInst.setText(listaGrupos.get(position).getIdInstituto().toString());

        } else {

        }*/
        holder.txtidGrupoEstudiantes.setText(listaGruposEstudiantes.get(position).getIdGrupoEstudiantes().toString());
        holder.txtNombreGrupoEstudiantes.setText(listaGruposEstudiantes.get(position).getNameGrupoEstudiantes());
       // holder.txtCantEstudiantes.setText(listaGruposEstudiantes.get(position).getIdGrupoEstudiantes().toString());


    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }


    @Override
    public int getItemCount() {
        return listaGruposEstudiantes.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    public class GruposHolder extends RecyclerView.ViewHolder {
        TextView txtidGrupoEstudiantes, txtCantEstudiantes, txtNombreGrupoEstudiantes;

        public GruposHolder(View itemView) {
            super(itemView);
            txtidGrupoEstudiantes = (TextView) itemView.findViewById(R.id.txt_GrupoEst_IdGrupo);
            txtNombreGrupoEstudiantes = (TextView) itemView.findViewById(R.id.txt_GrupoEst_NombreGrupo);
            txtCantEstudiantes = (TextView) itemView.findViewById(R.id.txt_GrupoEst_Cant_Estu);
        }
    }
}
