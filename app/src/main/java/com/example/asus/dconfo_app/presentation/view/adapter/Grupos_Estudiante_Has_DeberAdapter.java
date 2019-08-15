package com.example.asus.dconfo_app.presentation.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.domain.model.GrupoEstudiantesHasDeber;
import com.example.asus.dconfo_app.domain.model.Grupo_Estudiantes;

import java.util.List;


/**
 * Created by Andrés Cabal on 20/12/2018.
 */

public class Grupos_Estudiante_Has_DeberAdapter extends RecyclerView.Adapter<Grupos_Estudiante_Has_DeberAdapter.GruposHolder> implements View.OnClickListener {

    List<GrupoEstudiantesHasDeber> listaGruposEstHasDeber;
    private View.OnClickListener listener;

    public Grupos_Estudiante_Has_DeberAdapter(List<GrupoEstudiantesHasDeber> listaGruposEstHasDeber) {
        this.listaGruposEstHasDeber = listaGruposEstHasDeber;
    }


    @NonNull
    @Override
    public GruposHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grupo_est_has_deberdocente, parent, false);
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
        holder.txtidGrupoEst.setText(listaGruposEstHasDeber.get(position).getGrupo_estudiante_idgrupo_estudiante().toString());
        holder.txtIdGrupoHasDeber.setText(listaGruposEstHasDeber.get(position).getId_GE_H_D().toString());
        holder.txtFechaAsignacion.setText(listaGruposEstHasDeber.get(position).getFecha_gehd());
       // holder.txtCantEstudiantes.setText(listaGruposEstudiantes.get(position).getIdGrupoEstudiantes().toString());


    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }


    @Override
    public int getItemCount() {
        return listaGruposEstHasDeber.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    public class GruposHolder extends RecyclerView.ViewHolder {
        TextView txtidGrupoEst, txtFechaAsignacion, txtIdGrupoHasDeber;

        public GruposHolder(View itemView) {
            super(itemView);
            txtidGrupoEst = (TextView) itemView.findViewById(R.id.txt_GrupoEstHasDeber_IdGrupoEst);
            txtIdGrupoHasDeber = (TextView) itemView.findViewById(R.id.txt_GrupoEstHasDeber_IdGEhD);
            txtFechaAsignacion = (TextView) itemView.findViewById(R.id.txt_GrupoEstHasDeber_fechaAsig);
        }
    }
}
