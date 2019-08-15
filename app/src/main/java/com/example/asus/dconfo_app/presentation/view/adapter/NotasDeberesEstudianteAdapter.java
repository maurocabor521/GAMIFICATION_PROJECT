package com.example.asus.dconfo_app.presentation.view.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.domain.model.DeberEstudiante;
import com.example.asus.dconfo_app.domain.model.EjercicioG2;

import java.util.List;


/**
 * Created by Andrés Cabal on 20/12/2018.
 */

public class NotasDeberesEstudianteAdapter extends RecyclerView.Adapter<NotasDeberesEstudianteAdapter.EjerciciosHolder>
        implements View.OnClickListener {

    // List<Integer> listaDeberes;
    List<DeberEstudiante> listaDeberes;
    /* List<Integer> listaIdActividad;
     List<Integer> listaIdEjercicio;
     List<EjercicioG2> listaEjercicioG2;*/
    private View.OnClickListener listener;

    // public DeberesEstudianteAdapter(List<Integer> listaDeberes, List<Integer> listaIdActividad) {
    public NotasDeberesEstudianteAdapter(List<DeberEstudiante> listaDeberes) {
        this.listaDeberes = listaDeberes;
       /* this.listaIdActividad = listaIdActividad;
        this.listaIdEjercicio = listaIdEjercicio;
        this.listaEjercicioG2 = listaEjercicioG2;*/
        for (int i = 0; i < listaDeberes.size(); i++) {
            //System.out.println("lista deberes: " + (i + 1) + " g1: " + listaDeberes.get(i).getIdEjercicio() + " g2: " + listaDeberes.get(i).getIdEjercicio2());
        }
    }


    @NonNull
    @Override
    public EjerciciosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nota_deber_estudiante, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        vista.setOnClickListener(this);
        return new EjerciciosHolder(vista);
    }

    @Override
    public void onBindViewHolder(EjerciciosHolder holder, int position) {
        //holder.txtidGrupo.setText(listaGrupos.get(position).getIdGrupo().toString());
        Log.i("size adapter", "lista_: " + listaDeberes.size());
        holder.txt_IDdeber_notaDeber.setText(listaDeberes.get(position).getIdEstHasDeber().toString());
        holder.txt_IdEstudiante_notaDeber.setText(listaDeberes.get(position).getIdEstudiante().toString());
        holder.txt_id_Ejercicio_notaDeber.setText(listaDeberes.get(position).getIdEjercicio2().toString());

        if (listaDeberes.get(position).getIdCalificacion().equals(0)) {
            holder.txt_nota_notaDeber.setText("No se ha desarrollado el ejercicio");

        } else {
            holder.txt_nota_notaDeber.setText(listaDeberes.get(position).getIdCalificacion().toString());

        }

        if (listaDeberes.get(position).getIdGrupoHdeber().equals(0)) {
            int c = holder.ll_show.getResources().getColor(R.color.colorAccent);
            holder.ll_show.setBackgroundColor(c);
        } else {
            int c = holder.ll_show.getResources().getColor(R.color.colorPrimaryDark);
            holder.ll_show.setBackgroundColor(c);
            Drawable drawable = holder.ll_show.getResources().getDrawable(R.drawable.letra_g);
            holder.imageView.setBackground(drawable);

        }

        if (!(listaDeberes.get(position).getIdAsignacion().equals(0))) {
            int c = holder.ll_show.getResources().getColor(R.color.graylight);
            holder.ll_show.setBackgroundColor(c);
            Drawable drawable = holder.ll_show.getResources().getDrawable(R.drawable.letra_a);
            holder.imageView.setBackground(drawable);
        }
        if (listaDeberes.get(position).getIdAsignacion().equals(0) && listaDeberes.get(position).getIdGrupoHdeber().equals(0) ) {
            int c = holder.ll_show.getResources().getColor(R.color.colorPrimary);
            holder.ll_show.setBackgroundColor(c);
            Drawable drawable = holder.ll_show.getResources().getDrawable(R.drawable.letra_l);
            holder.imageView.setBackground(drawable);
        }
        holder.txt_fecha_notaDeber.setText(listaDeberes.get(position).getFechaDeber());
    }


    //*******************----------------------------////////////////------------------------

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }


    @Override
    public int getItemCount() {
        return listaDeberes.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    public class EjerciciosHolder extends RecyclerView.ViewHolder {
        TextView txt_IDdeber_notaDeber, txt_IdEstudiante_notaDeber, txt_id_Ejercicio_notaDeber, txt_nota_notaDeber, txt_fecha_notaDeber;
        LinearLayout ll_show;
        ImageView imageView;

        public EjerciciosHolder(View itemView) {
            super(itemView);
            txt_IDdeber_notaDeber = (TextView) itemView.findViewById(R.id.txt_IDdeber_notaDeber);
            txt_IdEstudiante_notaDeber = (TextView) itemView.findViewById(R.id.txt_IdEstudiante_notaDeber);
            txt_id_Ejercicio_notaDeber = (TextView) itemView.findViewById(R.id.txt_id_Ejercicio_notaDeber);
            txt_nota_notaDeber = (TextView) itemView.findViewById(R.id.txt_nota_notaDeber);
            txt_fecha_notaDeber = (TextView) itemView.findViewById(R.id.txt_fecha_notaDeber);
            ll_show = (LinearLayout) itemView.findViewById(R.id.ll_showNotas);
            imageView = (ImageView) itemView.findViewById(R.id.imv_show_image);

        }
    }
}
