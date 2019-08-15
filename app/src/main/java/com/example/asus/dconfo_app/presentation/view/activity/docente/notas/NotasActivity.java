package com.example.asus.dconfo_app.presentation.view.activity.docente.notas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.domain.model.DeberEstudiante;
import com.example.asus.dconfo_app.domain.model.EjercicioG2;
import com.example.asus.dconfo_app.domain.model.Grupo_Estudiantes;
import com.example.asus.dconfo_app.domain.model.VolleySingleton;
import com.example.asus.dconfo_app.helpers.Globals;
import com.example.asus.dconfo_app.presentation.view.adapter.Grupos_Estudiante_DocenteAdapter;
import com.example.asus.dconfo_app.presentation.view.adapter.NotasDeberesEstudianteAdapter;
import com.example.asus.dconfo_app.presentation.view.fragment.docente.notas.FindNotasXEstudianteFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.docente.notas.FindNotasXGrupoEstFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.docente.notas.FindNotasXTipoAsigFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.docente.notas.ShowNotasGrupoEstudianteFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.docente.tipoFragments.HomeTiposFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotasActivity extends AppCompatActivity implements Response.Listener<JSONObject>,
        Response.ErrorListener,
        FindNotasXEstudianteFragment.OnFragmentInteractionListener,
        FindNotasXGrupoEstFragment.OnFragmentInteractionListener,
        FindNotasXTipoAsigFragment.OnFragmentInteractionListener,
        ShowNotasGrupoEstudianteFragment.OnFragmentInteractionListener {

    public int iddocente = 0;
    public String id_estudiante;
    String flag;
    Integer idgrupo;
    RecyclerView rv_docente_notas;
    ProgressDialog progreso;
    FloatingActionButton fb_;
    //RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    ArrayList<DeberEstudiante> listaDeberes_full;
    ArrayList<Integer> lista_idEstudiante;
    private BottomBar bottomBar;
    FindNotasXEstudianteFragment fnXest;
    FindNotasXGrupoEstFragment fnXgrupo;
    FindNotasXTipoAsigFragment fnXtipo;
    String idestudiante = "";

    ArrayList<Integer> lista_idEjercicios;
    ArrayList<Integer> lista_calificaciones;
    ArrayList<EjercicioG2> listaEjerciciosG2;
    ArrayList<Integer> listaActividades;
    ArrayList<Integer> listanotaFonico;
    ArrayList<Integer> listanotaLexico;
    ArrayList<Integer> listanotaSilabico;
    ArrayList<String> lista_String_Actividades;
    int id_Ejercicio;
    int bandera = 0;
    int NDF = 0;
    int NDL = 0;
    int NDS = 0;
    int SUMNDF = 0;
    int SUMNDL = 0;
    int SUMNDS = 0;
    String nameEst;
    String actividad;
    Button btn_verporcentaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);


        Bundle datos = this.getIntent().getExtras();

        iddocente = datos.getInt("iddocente");
        idgrupo = datos.getInt("idgrupo");

        lista_idEjercicios = new ArrayList<>();
        lista_calificaciones = new ArrayList<>();
        listaActividades = new ArrayList<>();
        listaEjerciciosG2 = new ArrayList<>();
        listanotaFonico = new ArrayList<>();
        listanotaLexico = new ArrayList<>();
        listanotaSilabico = new ArrayList<>();
        lista_String_Actividades = new ArrayList<>();
        nameEst = "Grupo " + idgrupo;

        btn_verporcentaje = (Button) findViewById(R.id.btn_notas_porc_todas);
        btn_verporcentaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEjercicioAct();


            }
        });


        rv_docente_notas = findViewById(R.id.rv_docente_notas);
        rv_docente_notas.setLayoutManager(new LinearLayoutManager(this));
        rv_docente_notas.setHasFixedSize(true);

        listaDeberes_full = new ArrayList<>();
        lista_idEstudiante = new ArrayList<>();

        progreso = new ProgressDialog(this);
        flag = "1";
        cargarWebService();

        showToolbar("Notas Estudiantes" + " - " + iddocente, true);
        bottomBar = findViewById(R.id.bottombar_notas);
        cargarBottombar();

    }

    private void callBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt("fonico", NDF);
        bundle.putInt("lexico", NDL);
        bundle.putInt("silabico", NDS);
        bundle.putString("nameEst", nameEst);
        System.out.println("nameEst_: " + nameEst);
        bundle.putStringArrayList("list", null);
        bundle.putIntegerArrayList("listCalificacion", null);
        Intent intent = new Intent(NotasActivity.this, PorcentNotasActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void cargarBottombar() {

        bottomBar.setDefaultTab(R.id.btn_home_notas);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId) {
                    case R.id.btn_home_notas:

                        btn_verporcentaje.setVisibility(View.VISIBLE);
                        rv_docente_notas.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "Notas Home", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.btn_estudiante:
                        Toast.makeText(getApplicationContext(), "Notas X Estudiante", Toast.LENGTH_LONG).show();
                        rv_docente_notas.setVisibility(View.GONE);
                        btn_verporcentaje.setVisibility(View.GONE);

                        Bundle args1 = new Bundle();

                        args1.putInt("iddocente", iddocente);
                        args1.putInt("idgrupo", idgrupo);

                        fnXest = new FindNotasXEstudianteFragment();
                        fnXest.setArguments(args1);

                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_contenedor_notas, fnXest)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();

                        break;
                    case R.id.btn_grupos:
                        Toast.makeText(getApplicationContext(), "Notas X Estudiante", Toast.LENGTH_LONG).show();
                        rv_docente_notas.setVisibility(View.GONE);
                        btn_verporcentaje.setVisibility(View.GONE);
                        Bundle args2 = new Bundle();

                        args2.putInt("iddocente", iddocente);
                        args2.putInt("idgrupo", idgrupo);

                        fnXgrupo = new FindNotasXGrupoEstFragment();
                        fnXgrupo.setArguments(args2);

                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_contenedor_notas, fnXgrupo)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();

                        break;

                    case R.id.btn_tipoAsignacion:
                        //Toast.makeText(getApplicationContext(), "Notas X Estudiante", Toast.LENGTH_LONG).show();
                        rv_docente_notas.setVisibility(View.GONE);
                        btn_verporcentaje.setVisibility(View.GONE);
                        Bundle args3 = new Bundle();

                        args3.putInt("iddocente", iddocente);
                        args3.putInt("idgrupo", idgrupo);

                        fnXtipo = new FindNotasXTipoAsigFragment();
                        fnXtipo.setArguments(args3);

                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_contenedor_notas, fnXtipo)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();

                        break;
                }

            }
        });
    }

    private void cargarWebService() {

        progreso.setMessage("Cargando...");
        progreso.show();
        // String ip = getString(R.string.ip);
        //int iddoc=20181;
        String iddoc = "20181";
        String url_lh = Globals.url;

        if (flag.equals("1")) {

            String url = "http://" + url_lh + "/proyecto_dconfo_v1/8_5wsJSONConsultarListaDeberesEst_nota.php?estudiante_idestudiante=" + idestudiante + "&iddocente=" + iddocente;

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
            //Toast.makeText(getApplicationContext(), "web service 1111", Toast.LENGTH_LONG).show();}
        } else if (flag.equals("2")) {

            String url = "http://" + url_lh + "/proyecto_dconfo_v1/9wsJSONConsultarEjercicioEstudiante.php?idEjercicioG2=" + id_Ejercicio;

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
            //Toast.makeText(getApplicationContext(), "web service 1111", Toast.LENGTH_LONG).show();}
        }//flag="2"


    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getApplicationContext(), "No se puede cone , grupo doc" + error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("ERROR notasact: ", error.toString());
        progreso.hide();
    }

    // si esta bien el llamado a la url entonces entra a este metodo
    @Override
    public void onResponse(JSONObject response) {
        progreso.hide();
        if (flag.equals("1")) {
            //Toast.makeText(getApplicationContext(), "Mensaje: " + response.toString(), Toast.LENGTH_SHORT).show();
            DeberEstudiante deberEstudiante = null;
            JSONArray json = response.optJSONArray("deber_nota");


            try {
                for (int i = 0; i < json.length(); i++) {
                    deberEstudiante = new DeberEstudiante();
                    JSONObject jsonObject = null;
                    jsonObject = json.getJSONObject(i);
                    // jsonObject = new JSONObject(response);
                    deberEstudiante.setIdEjercicio2(jsonObject.optInt("EjercicioG2_idEjercicioG2"));
                    deberEstudiante.setIdEstudiante(jsonObject.optInt("estudiante_idestudiante"));
                    deberEstudiante.setFechaDeber(jsonObject.optString("fechaestudiante_has_Deber"));
                    deberEstudiante.setTipoDeber(jsonObject.optString("tipoDeber"));
                    deberEstudiante.setIdDocente(jsonObject.optInt("docente_iddocente"));
                    deberEstudiante.setIdCalificacion(jsonObject.optInt("calificacionestudiante_has_Deber"));
                    deberEstudiante.setIdEstHasDeber(jsonObject.optInt("id_estudiante_has_Debercol"));
                    deberEstudiante.setIdAsignacion(jsonObject.optInt("Asignacion_idGrupoAsignacion"));
                    deberEstudiante.setIdGrupoHdeber(jsonObject.optInt("grupo_estudiante_has_deber_id_GE_H_D"));
                    listaDeberes_full.add(deberEstudiante);
                    lista_idEstudiante.add(deberEstudiante.getIdEstudiante());
                    lista_idEstudiante.add(deberEstudiante.getIdEstudiante());
                    lista_idEjercicios.add(deberEstudiante.getIdEjercicio2());
                    lista_calificaciones.add(deberEstudiante.getIdCalificacion());

                }
                //Toast.makeText(getApplicationContext(), "listagrupos: " + listaGrupos.size(), Toast.LENGTH_LONG).show();
                // Log.i("size", "lista: " + listaGrupos.size());
                NotasDeberesEstudianteAdapter notasDeberesEstudianteAdapter = new NotasDeberesEstudianteAdapter(listaDeberes_full);

                notasDeberesEstudianteAdapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                    }
                });
                rv_docente_notas.setAdapter(notasDeberesEstudianteAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("error", response.toString());

                Toast.makeText(getApplicationContext(), "No se ha podido establecer conexión: " + response.toString(), Toast.LENGTH_LONG).show();

                progreso.hide();
            }

           // getEjercicioAct();
            // System.out.println("Lista id estudiante: " + lista_idEstudiante.toString());
            for (int i = 0; i < listaDeberes_full.size(); i++) {
                System.out.println("Lista listaDeberes_full show: i=" + (i + 1) + " - asignación - " + listaDeberes_full.get(i).getIdAsignacion());
                System.out.println("Lista listaDeberes_full show: i=" + (i + 1) + " - GrupoHdeber - " + listaDeberes_full.get(i).getIdGrupoHdeber());
            }
        }//flag="1"
        else
            //flag="1"
            if (flag.equals("2")) {
                //Toast.makeText(getApplicationContext(), "Mensaje: " + response.toString(), Toast.LENGTH_SHORT).show();
                DeberEstudiante deberEstudiante = null;

                bandera++;
                EjercicioG2 ejercicioG2 = null;

                JSONArray json = response.optJSONArray("ejerciciog2");
                //System.out.println("response: " + response.toString());
                try {
                    for (int i = 0; i < json.length(); i++) {
                        ejercicioG2 = new EjercicioG2();
                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(i);
                        ejercicioG2.setIdEjercicioG2(jsonObject.optInt("idEjercicioG2"));
                        ejercicioG2.setNameEjercicioG2(jsonObject.optString("nameEjercicioG2"));
                        ejercicioG2.setIdDocente(jsonObject.optInt("docente_iddocente"));
                        ejercicioG2.setIdTipo(jsonObject.optInt("Tipo_idTipo"));
                        ejercicioG2.setIdActividad(jsonObject.optInt("Tipo_Actividad_idActividad"));
                        ejercicioG2.setLetra_inicial_EjercicioG2(jsonObject.optString("letra_inicial_EjercicioG2"));
                        ejercicioG2.setLetra_final_EjercicioG2(jsonObject.optString("letra_final_EjercicioG2"));

                        if (ejercicioG2.getIdActividad() == 1) {
                            lista_String_Actividades.add("f");
                            listanotaFonico.add(ejercicioG2.getIdActividad());
                        } else if (ejercicioG2.getIdActividad() == 2) {
                            lista_String_Actividades.add("l");
                            listanotaLexico.add(ejercicioG2.getIdActividad());
                        } else if (ejercicioG2.getIdActividad() == 3) {
                            lista_String_Actividades.add("s");
                            listanotaSilabico.add(ejercicioG2.getIdActividad());
                        }


                        listaEjerciciosG2.add(ejercicioG2);
                        listaActividades.add(ejercicioG2.getIdActividad());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("error", response.toString());

                    Toast.makeText(getApplicationContext(), "No se ha podido establecer conexión: " + response.toString(), Toast.LENGTH_LONG).show();

                    progreso.hide();
                }

                getEjercicioAct();

                for (int i = 0; i < listaActividades.size(); i++) {
                    System.out.println("listaActividades: " + listaActividades.get(i));
                }

                for (int i = 0; i < lista_String_Actividades.size(); i++) {
                    System.out.println("lista_String_Actividades: " + lista_String_Actividades.get(i));
                }

                System.out.println("listanotaFonico: " + listanotaFonico.size());
                System.out.println("listanotaLexico: " + listanotaLexico.size());
                System.out.println("listanotaSilabico: " + listanotaSilabico.size());
            }//flag="2"


    }//********************************************


    //********************************************

    private void getEjercicioAct() {
        if (bandera < listaDeberes_full.size()) {

            id_Ejercicio = lista_idEjercicios.get(bandera);
            System.out.println("id_Ejercicio: " + id_Ejercicio);
            flag = "2";
            cargarWebService();
        } else {
            calculaNotaAct();
        }

    }

    private void calculaNotaAct() {


        for (int i = 0; i < lista_calificaciones.size(); i++) {
            if (lista_String_Actividades.get(i) == "f") {
                SUMNDF += lista_calificaciones.get(i);
            } else if (lista_String_Actividades.get(i) == "l") {
                SUMNDL += lista_calificaciones.get(i);
            } else if (lista_String_Actividades.get(i) == "s") {
                SUMNDS += lista_calificaciones.get(i);
            }

        }

        calculaDefAct();

        System.out.println("SUMNDF: " + SUMNDF);
        System.out.println("SUMNDL: " + SUMNDL);
        System.out.println("SUMNDS: " + SUMNDS);


    }

    private void calculaDefAct() {
        if (listanotaFonico.size() != 0) {
            NDF = SUMNDF / listanotaFonico.size();
        }
        if (listanotaLexico.size() != 0) {
            NDL = SUMNDL / listanotaLexico.size();
        }
        if (listanotaSilabico.size() != 0) {
            NDS = SUMNDS / listanotaSilabico.size();
        }

        callBundle();
        // btn_BuscarEstudiante.setVisibility(View.VISIBLE);
        System.out.println("NDF: " + NDF);
        System.out.println("NDL: " + NDL);
        System.out.println("NDS: " + NDS);
    }


    //***********************************

    public void showToolbar(String tittle, boolean upButton) {
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_ejercicio);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        //getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    //método que permite volver al padre conservando las variables
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
