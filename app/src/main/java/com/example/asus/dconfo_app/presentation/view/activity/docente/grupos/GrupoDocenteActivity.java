package com.example.asus.dconfo_app.presentation.view.activity.docente.grupos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.domain.model.Grupo;
import com.example.asus.dconfo_app.domain.model.Grupo_Estudiantes;
import com.example.asus.dconfo_app.domain.model.VolleySingleton;
import com.example.asus.dconfo_app.helpers.Globals;
import com.example.asus.dconfo_app.presentation.view.activity.docente.HomeDocenteActivity;
import com.example.asus.dconfo_app.presentation.view.activity.docente.ManageCursosDocenteActivity;
import com.example.asus.dconfo_app.presentation.view.adapter.GruposDocenteAdapter;
import com.example.asus.dconfo_app.presentation.view.adapter.Grupos_Estudiante_DocenteAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GrupoDocenteActivity extends AppCompatActivity implements Response.Listener<JSONObject>,
        Response.ErrorListener {

    private RecyclerView rv_GrupoDocente_Grupos;
    ArrayList<Grupo_Estudiantes> listaGrupoEstudiantes;
    int idgrupo;
    int iddocente;
    ProgressDialog progreso;
    FloatingActionButton fb_nuevo_GrupoEstudiantes;
    //RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo_docente2);
        showToolbar("Mis Grupos de Estudiantes", true);

        Bundle datos = this.getIntent().getExtras();
        idgrupo = datos.getInt("idgrupo");
        iddocente = datos.getInt("iddocente");

        rv_GrupoDocente_Grupos = (RecyclerView) findViewById(R.id.rv_docente_Grupo_misGruposEst);
        rv_GrupoDocente_Grupos.setLayoutManager(new LinearLayoutManager(this));
        rv_GrupoDocente_Grupos.setHasFixedSize(true);

        fb_nuevo_GrupoEstudiantes = (FloatingActionButton) findViewById(R.id.fb_Grupo_newGrupo);
        fb_nuevo_GrupoEstudiantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("idgrupo", idgrupo);
                bundle.putInt("iddocente", iddocente);
                Intent intent = new Intent(GrupoDocenteActivity.this, GrupoNuevoActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        progreso = new ProgressDialog(this);

        cargarWebService();

        // Toast.makeText(getApplicationContext(), "idgrupo: " + idgrupo, Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplicationContext(), "iddocente: " + iddocente, Toast.LENGTH_LONG).show();
    }

    //método que permite volver al padre conservando las variables
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void cargarWebService() {

        progreso.setMessage("Cargando...");
        progreso.show();
        // String ip = getString(R.string.ip);
        //int iddoc=20181;
        String iddoc = "20181";
        String url_lh = Globals.url;

        String url = "http://" + url_lh + "/proyecto_dconfo_v1/12wsJSONConsultar_Lista_Grupo_Est.php?idgrupo=" + idgrupo + "& iddocente=" + iddocente;

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
        progreso.hide();
        Toast.makeText(getApplicationContext(), "No se puede cone , grupo doc" + error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("ERROR", error.toString());
        progreso.hide();
    }

    // si esta bien el llamado a la url entonces entra a este metodo
    @Override
    public void onResponse(JSONObject response) {
        progreso.hide();
        //Toast.makeText(getApplicationContext(), "Mensaje: " + response.toString(), Toast.LENGTH_SHORT).show();
        Grupo_Estudiantes grupo_estudiantes = null;
        JSONArray json = response.optJSONArray("grupo_estudiante");
        listaGrupoEstudiantes = new ArrayList<>();

        try {
            for (int i = 0; i < json.length(); i++) {
                grupo_estudiantes = new Grupo_Estudiantes();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);
                // jsonObject = new JSONObject(response);
                grupo_estudiantes.setIdGrupoEstudiantes(jsonObject.optInt("idgrupo_estudiante"));
                grupo_estudiantes.setNameGrupoEstudiantes(jsonObject.optString("name_grupo_estudiante"));
                grupo_estudiantes.setIdDocente(jsonObject.optInt("docente_iddocente"));
                grupo_estudiantes.setIdGrupo(jsonObject.optInt("grupo_idgrupo"));
                listaGrupoEstudiantes.add(grupo_estudiantes);

//idgrupo,namegrupo,curso_idcurso,curso_Instituto_idInstituto
            }
            //Toast.makeText(getApplicationContext(), "listagrupos: " + listaGrupos.size(), Toast.LENGTH_LONG).show();
            // Log.i("size", "lista: " + listaGrupos.size());
            Grupos_Estudiante_DocenteAdapter grupos_estudiante_docenteAdapter = new Grupos_Estudiante_DocenteAdapter(listaGrupoEstudiantes);

            grupos_estudiante_docenteAdapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   /*Toast.makeText(getApplicationContext(), "Seleccion: " +
                            listaGrupos.get(rvListaCursos.
                                    getChildAdapterPosition(view)).getNameGrupo(), Toast.LENGTH_SHORT).show();//video p1*/

             /*       Bundle parametros = new Bundle();
                    int idgrupo = listaGrupos.get(rvListaCursos.
                            getChildAdapterPosition(view)).getIdGrupo();
                    parametros.putInt("idgrupo", idgrupo);

                    Toast.makeText(getApplicationContext(), "idgrupos: " + idgrupo, Toast.LENGTH_LONG).show();

                    int idcurso = listaGrupos.get(rvListaCursos.
                            getChildAdapterPosition(view)).getCurso_idCurso();
                    parametros.putInt("idcurso", idcurso);

                    String namegrupo = listaGrupos.get(rvListaCursos.
                            getChildAdapterPosition(view)).getNameGrupo();


                    parametros.putInt("idcurso", idcurso);

                    String idDocente1 = String.valueOf(listaGrupos.get(rvListaCursos.
                            getChildAdapterPosition(view)).getIdDocente());
                    parametros.putInt("idcurso", idcurso);

                    Intent intent = new Intent(ManageCursosDocenteActivity.this, HomeDocenteActivity.class);
                    //intent.putExtras(parametros);
                    intent.putExtra("idgrupo", idgrupo);
                    intent.putExtra("idcurso", idcurso);
                    intent.putExtra("idDoc", idDocente);
                    intent.putExtra("nameDoc", nameDocente);
                    // Toast.makeText(getApplicationContext(), "id Doc MCDA: " + idDocente, Toast.LENGTH_LONG).show();
                    intent.putExtra("namegrupo", namegrupo);
                    startActivity(intent);
                  /*  interfaceComunicaFragments.
                            enviarEjercicio
                                    (listaEjercicios.get(recyclerView.getChildAdapterPosition(view)));//video p2 detalle envia el objeto completo
                                    */

                }
            });
            rv_GrupoDocente_Grupos.setAdapter(grupos_estudiante_docenteAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("error", response.toString());

            Toast.makeText(getApplicationContext(), "No se ha podido establecer conexión: " + response.toString(), Toast.LENGTH_LONG).show();

            progreso.hide();
        }
    }


    public void showToolbar(String tittle, boolean upButton) {
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_ejercicio);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        //getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
