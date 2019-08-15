package com.example.asus.dconfo_app.presentation.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.domain.model.Imagen;
import com.example.asus.dconfo_app.domain.model.VolleySingleton;
import com.example.asus.dconfo_app.helpers.Globals;

import java.util.List;



public class ImagenUrlAdapter extends RecyclerView.Adapter<ImagenUrlAdapter.UsuariosHolder> implements View.OnClickListener {
    List<Imagen> listaImagenes;
    private View.OnClickListener listener;
    //RequestQueue request;
    Context context;


    public ImagenUrlAdapter(List<Imagen> listaImagenes, Context context) {
        this.listaImagenes = listaImagenes;
        this.context = context;
        //request= Volley.newRequestQueue(context);
    }

    @Override
    public UsuariosHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_bank_url_vert, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        vista.setOnClickListener(this);
        return new UsuariosHolder(vista);
    }

    @Override
    public void onBindViewHolder(UsuariosHolder holder, int position) {
        holder.txtId.setText(listaImagenes.get(position).getIdImagen().toString());
        holder.txtNombre.setText(listaImagenes.get(position).getNameImagen().toString());
        holder.txtLetraInicial.setText(listaImagenes.get(position).getLetraInicialImagen().toString());
        holder.txtLetraFinal.setText(listaImagenes.get(position).getLetraFinalImagen().toString());
        holder.txtCantSilabas.setText(listaImagenes.get(position).getCantSilabasImagen().toString());

        if (listaImagenes.get(position).getRutaImagen() != null) {
            //
            cargarImagenWebService(listaImagenes.get(position).getRutaImagen(), holder);
        } else {
            holder.imagen.setImageResource(R.drawable.ic_no_foto_80dp);
        }
    }

    private void cargarImagenWebService(String rutaImagen, final UsuariosHolder holder) {

       // String ip = context.getString(R.string.ip);

        String url_lh= Globals.url;

        String urlImagen = "http://"+url_lh+"/proyecto_dconfo_v1/" + rutaImagen;
        urlImagen = urlImagen.replace(" ", "%20");

        ImageRequest imageRequest = new ImageRequest(urlImagen, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                holder.imagen.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
            }
        });
        //request.add(imageRequest);
        VolleySingleton.getIntanciaVolley(context).addToRequestQueue(imageRequest);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return listaImagenes.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    public class UsuariosHolder extends RecyclerView.ViewHolder {

        TextView txtId, txtNombre, txtLetraInicial,txtLetraFinal,txtCantSilabas;
        ImageView imagen;

        public UsuariosHolder(View itemView) {
            super(itemView);
            txtId = (TextView) itemView.findViewById(R.id.txt_bank_id_Image);
            txtNombre = (TextView) itemView.findViewById(R.id.txt_bank_Nombre_Image);
            txtLetraInicial = (TextView) itemView.findViewById(R.id.txt_bank_letra_Inicial);
            txtLetraFinal = (TextView) itemView.findViewById(R.id.txt_bank_letra_Final);
            txtCantSilabas = (TextView) itemView.findViewById(R.id.txt_bank_cant_Silabas);
            imagen = (ImageView) itemView.findViewById(R.id.imv_bank_Imagen_URL);
        }
    }
}
