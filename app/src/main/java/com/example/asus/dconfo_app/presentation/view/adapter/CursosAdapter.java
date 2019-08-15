package com.example.asus.dconfo_app.presentation.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.domain.model.Curso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Andrés Cabal on 20/12/2018.
 */

public class CursosAdapter extends RecyclerView.Adapter<CursosAdapter.UsuariosHolder> {

    ArrayList<Curso> listaCursos;

    public CursosAdapter(ArrayList<Curso> listaUsuarios) {
        this.listaCursos = listaUsuarios;
    }


    @NonNull
    @Override
    public UsuariosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_curso, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new UsuariosHolder(vista);
    }

    @Override
    public void onBindViewHolder(UsuariosHolder holder, int position) {
        holder.txtidCurso.setText(listaCursos.get(position).getIdCurso().toString());
        holder.txtNombreCurso.setText(listaCursos.get(position).getNameCurso().toString());
        holder.txtidInst.setText(listaCursos.get(position).getIdInstitutoCurso().toString());
        holder.txtPeriodoCurso.setText(listaCursos.get(position).getPeriodoCurso().toString());
    }

    @Override
    public int getItemCount() {
        return listaCursos.size();
    }

    public class UsuariosHolder extends RecyclerView.ViewHolder {
        TextView txtidCurso, txtNombreCurso,txtidInst,txtPeriodoCurso;
        public UsuariosHolder(View itemView) {
            super(itemView);
            txtidCurso=(TextView)itemView.findViewById(R.id.txtIDcurso_CL);
            txtNombreCurso=(TextView)itemView.findViewById(R.id.txtNombreCurso_CL);
            txtidInst=(TextView)itemView.findViewById(R.id.txtInstituto_CL);
            txtPeriodoCurso=(TextView)itemView.findViewById(R.id.txtPeriodo_CL);
        }
    }
}
