package com.example.asus.dconfo_app.presentation.view.activity.docente.fonico;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.domain.model.Grupo;
import com.example.asus.dconfo_app.domain.model.Imagen;
import com.example.asus.dconfo_app.domain.model.VolleySingleton;
import com.example.asus.dconfo_app.helpers.Globals;
import com.example.asus.dconfo_app.presentation.view.activity.docente.HomeDocenteActivity;
import com.example.asus.dconfo_app.presentation.view.activity.docente.ManageCursosDocenteActivity;
import com.example.asus.dconfo_app.presentation.view.adapter.GruposDocenteAdapter;
import com.example.asus.dconfo_app.presentation.view.adapter.ImagenUrlAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BankImagesActivity extends AppCompatActivity implements Response.Listener<JSONObject>,
        Response.ErrorListener {

    private RecyclerView rv_bankimages;
    ArrayList<Imagen> listaImagenes;
    ImageView iv_bank_prueba;
    //******** CONEXIÓN CON WEBSERVICE
    //RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_images);

        listaImagenes = new ArrayList<>();

        iv_bank_prueba = findViewById(R.id.iv_bank_prueba);

        rv_bankimages = findViewById(R.id.rv_bankImages);
        rv_bankimages.setLayoutManager(new LinearLayoutManager(this));
        rv_bankimages.setHasFixedSize(true);

        cargarWebService();
    }

    private void cargarWebService() {

       /* progreso.setMessage("Cargando...");
        progreso.show();*/
        // String ip = getString(R.string.ip);
        //int iddoc=20181;
        String iddoc = "20181";
        String url_lh = Globals.url;

        //String url = "http://192.168.0.13/proyecto_dconfo/wsJSONConsultarListaCursosDocente.php?iddocente=" + txtiddoc.getText().toString();

        // String url = "http://"+url_lh+"/proyecto_dconfo/wsJSONConsultarListaCursosDocente.php?iddocente=" + txtiddoc.getText().toString();
        String url = "http://" + url_lh + "/proyecto_dconfo_v1/wsJSON1ConsultarListaImagenes.php";
        // http://localhost/proyecto_dconfo/
///wsJSONConsultarEstudiante.php?documento=" + edt_codigo.getText().toString();
        url = url.replace(" ", "%20");
        //hace el llamado a la url
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);

        final int MY_DEFAULT_TIMEOUT = 15000;
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);//p21
        //Toast.makeText(getApplicationContext(), "web service 1111", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //progreso.hide();
        Toast.makeText(getApplicationContext(), "No se puede cone , grupo doc" + error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("ERROR", error.toString());
        //progreso.hide();
    }

    // si esta bien el llamado a la url entonces entra a este metodo
    @Override
    public void onResponse(final JSONObject response) {
        //progreso.hide();
        //Toast.makeText(getApplicationContext(), "Mensaje: " + response.toString(), Toast.LENGTH_SHORT).show();
        Imagen imagen = null;
        JSONArray json = response.optJSONArray("imagen");

        try {
            for (int i = 0; i < json.length(); i++) {
                imagen = new Imagen();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);
                // jsonObject = new JSONObject(response);
                imagen.setIdImagen(jsonObject.optInt("idImagen_Ejercicio"));
                imagen.setNameImagen(jsonObject.optString("name_Imagen_Ejercicio"));
                imagen.setRutaImagen(jsonObject.optString("ruta_Imagen_Ejercicio"));
                imagen.setLetraInicialImagen(jsonObject.optString("letra_inicial_Imagen"));
                imagen.setLetraFinalImagen(jsonObject.optString("letra_final_Imagen"));
                imagen.setCantSilabasImagen(jsonObject.optInt("cant_silabas_Imagen"));

                listaImagenes.add(imagen);

//idgrupo,namegrupo,curso_idcurso,curso_Instituto_idInstituto
            }

            ImagenUrlAdapter imagenUrlAdapter = new ImagenUrlAdapter(listaImagenes, getApplicationContext());
            imagenUrlAdapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String rutaImagen = listaImagenes.get(rv_bankimages.
                            getChildAdapterPosition(v)).getRutaImagen();

                    cargarImagenWebService(rutaImagen);

                    //Toast.makeText(getApplicationContext(), "on click: " + rutaImagen, Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(), "on click: " , Toast.LENGTH_LONG).show();

                }
            });

            rv_bankimages.setAdapter(imagenUrlAdapter);

            //Toast.makeText(getApplicationContext(), "listagrupos: " + listaGrupos.size(), Toast.LENGTH_LONG).show();
            //Log.i("size", "lista Imágenes: " + listaImagenes.get(0).getNameImagen());

            //rv_bankimages.setAdapter(gruposDocenteAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("error", response.toString());

            Toast.makeText(getApplicationContext(), "No se ha podido establecer conexión: " + response.toString(), Toast.LENGTH_LONG).show();

            //progreso.hide();
        }
    }

    private void cargarImagenWebService(String rutaImagen) {

        // String ip = context.getString(R.string.ip);

        String url_lh = Globals.url;

        String urlImagen = "http://" + url_lh + "/proyecto_dconfo_v1/" + rutaImagen;
        urlImagen = urlImagen.replace(" ", "%20");

        ImageRequest imageRequest = new ImageRequest(urlImagen, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                iv_bank_prueba.setBackground(null);
                iv_bank_prueba.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
            }
        });
        //request.add(imageRequest);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(imageRequest);
    }


}
