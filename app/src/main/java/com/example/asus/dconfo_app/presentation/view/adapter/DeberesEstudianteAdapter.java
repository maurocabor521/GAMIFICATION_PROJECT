package com.example.asus.dconfo_app.presentation.view.adapter;

import android.content.Context;
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
import com.example.asus.dconfo_app.domain.model.Grupo;
import com.example.asus.dconfo_app.domain.model.Imagen;

import java.util.List;


/**
 * Created by Andrés Cabal on 20/12/2018.
 */

public class DeberesEstudianteAdapter extends RecyclerView.Adapter<DeberesEstudianteAdapter.EjerciciosHolder>
        implements View.OnClickListener {

    // List<Integer> listaDeberes;
    List<DeberEstudiante> listaDeberes;
    List<Integer> listaIdActividad;
    List<Integer> listaIdEjercicio;
    List<EjercicioG2> listaEjercicioG2;
    private View.OnClickListener listener;

    // public DeberesEstudianteAdapter(List<Integer> listaDeberes, List<Integer> listaIdActividad) {
    public DeberesEstudianteAdapter(List<DeberEstudiante> listaDeberes, List<Integer> listaIdActividad, List<Integer> listaIdEjercicio,   List<EjercicioG2> listaEjercicioG2) {
        this.listaDeberes = listaDeberes;
        this.listaIdActividad = listaIdActividad;
        this.listaIdEjercicio = listaIdEjercicio;
        this.listaEjercicioG2 = listaEjercicioG2;
        for (int i = 0; i < listaDeberes.size(); i++) {
            //System.out.println("lista deberes: " + (i + 1) + " g1: " + listaDeberes.get(i).getIdEjercicio() + " g2: " + listaDeberes.get(i).getIdEjercicio2());
        }
    }


    @NonNull
    @Override
    public EjerciciosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deber_estudiante, parent, false);
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
        holder.txtidEjercicio.setText(listaDeberes.get(position).getIdEjercicio2().toString());

        if (listaIdActividad.get(position) == 1) {
            holder.txtActividad.setText("Fónico");
           // holder.txtidEjercicio.setText(listaDeberes.get(position).getIdEjercicio2().toString());
           // holder.txtnameDeber.setText(listaDeberes.get(position).getTipoDeber().toString());
            int c = holder.ll_ejercicio.getResources().getColor(R.color.colorPrimary);
            holder.ll_ejercicio.setBackgroundColor(c);
            Drawable drawable = holder.ll_ejercicio.getResources().getDrawable(R.drawable.letra_f);
            holder.imageView.setBackground(drawable);
        } else if (listaIdActividad.get(position) == 2) {
          /*  if (listaDeberes.get(position).equals("NULL")){
                holder.txtAsignacion.setText(listaDeberes.get(position).get);
            }*/
            holder.txtActividad.setText("Léxico");
           // holder.txtidEjercicio.setText(listaDeberes.get(position).getIdEjercicio2().toString());
           // holder.txtnameDeber.setText(listaDeberes.get(position).getTipoDeber().toString());
            int c = holder.ll_ejercicio.getResources().getColor(R.color.colorAccent);
            holder.ll_ejercicio.setBackgroundColor(c);
            Drawable drawable = holder.ll_ejercicio.getResources().getDrawable(R.drawable.letra_l);
            holder.imageView.setBackground(drawable);
        } else if (listaIdActividad.get(position) == 3) {
            holder.txtActividad.setText("Silábico");
           // holder.txtidEjercicio.setText(listaDeberes.get(position).getIdEjercicio2().toString());
//            holder.txtnameDeber.setText(listaDeberes.get(position).getTipoDeber().toString());
            int c = holder.ll_ejercicio.getResources().getColor(R.color.design_default_color_primary);
            holder.ll_ejercicio.setBackgroundColor(c);
            Drawable drawable = holder.ll_ejercicio.getResources().getDrawable(R.drawable.letra_s);
            holder.imageView.setBackground(drawable);
        }
        //holder.txtidEjercicio.setText(listaDeberes.get(position).get);

      /*  if (listaDeberes.size() != 0) {
            //if (!(listaDeberes.get(position).equals(0)) && listaIdActividad.get(position)==1) {
            if (listaIdActividad.get(position).equals(1)) {
                holder.txtidEjercicio.setText(listaDeberes.get(position).toString());
                holder.txtActividad.setText("Fónico");
                // ;
                int c = holder.ll_ejercicio.getResources().getColor(R.color.colorPrimary);
                holder.ll_ejercicio.setBackgroundColor(c);
                Drawable drawable = holder.ll_ejercicio.getResources().getDrawable(R.drawable.crear_fonica);
                holder.imageView.setBackground(drawable);

            //}  if (!(listaDeberes.get(position).equals(0)) && listaIdActividad.get(position)==2) {
            }else  if (listaIdActividad.get(position)==2) {
                holder.txtidEjercicio.setText(listaDeberes.get(position).toString());
                holder.txtActividad.setText("Léxico");

                int c = holder.ll_ejercicio.getResources().getColor(R.color.colorAccent);
                holder.ll_ejercicio.setBackgroundColor(c);

                Drawable drawable = holder.ll_ejercicio.getResources().getDrawable(R.drawable.ic_conciencia_lexica);
                holder.imageView.setBackground(drawable);

            //}  if (!(listaDeberes.get(position).equals(0)) && listaIdActividad.get(position)==3) {
            }else  if ( listaIdActividad.get(position)==3) {
                holder.txtidEjercicio.setText(listaDeberes.get(position).toString());
                holder.txtActividad.setText("Silábico");

                int c = holder.ll_ejercicio.getResources().getColor(R.color.design_default_color_primary);
                holder.ll_ejercicio.setBackgroundColor(c);

                Drawable drawable = holder.ll_ejercicio.getResources().getDrawable(R.drawable.ic_conciencia_silabica);
                holder.imageView.setBackground(drawable);
            }
           // holder.txtnameDeber.setText(listaDeberes.get(position).getTipoDeber().toString());
        } else {

        }*/

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
        TextView txtidEjercicio, txtnameDeber, txtActividad, txtAsignacion;
        LinearLayout ll_ejercicio;
        ImageView imageView;

        public EjerciciosHolder(View itemView) {
            super(itemView);
            txtidEjercicio = (TextView) itemView.findViewById(R.id.txt_deber_idejercicio);
            //txtnameDeber = (TextView) itemView.findViewById(R.id.txt_deber_tipodeber);
            txtActividad = (TextView) itemView.findViewById(R.id.txt_actividad_tipodeber);
            //txtAsignacion = (TextView) itemView.findViewById(R.id.txt_asignacion);
            ll_ejercicio = (LinearLayout) itemView.findViewById(R.id.ll_item_ejercicio);
            imageView = (ImageView) itemView.findViewById(R.id.iv_item_deber_est);

        }
    }
}
