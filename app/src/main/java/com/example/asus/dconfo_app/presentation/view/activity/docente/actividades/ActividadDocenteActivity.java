package com.example.asus.dconfo_app.presentation.view.activity.docente.actividades;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.domain.model.Asignacion;
import com.example.asus.dconfo_app.domain.model.EjercicioG2;
import com.example.asus.dconfo_app.domain.model.VolleySingleton;
import com.example.asus.dconfo_app.helpers.Globals;
import com.example.asus.dconfo_app.presentation.view.adapter.TipoEjerciciosActividadDocenteAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActividadDocenteActivity extends AppCompatActivity implements Response.Listener<JSONObject>,
        Response.ErrorListener {

    private LinearLayout ll_rv_ejercicios;
    private Button btn_fon;
    private Button btn_lex;
    private Button btn_sil;
    private Button btn_crear_actividad;
    private EditText edt_name_actividad;
    private CheckBox cb_agregar_ejercicio;
    private RecyclerView rv_ejercicios_act;
    private CardView cv_item_ejecicio;

    private int iddocente;
    private int idgrupo;
    private int idactividad;
    private int flag;
    private int flag_1 = 0;
    private int asignacion_idGrupoAsignacion;
    private int ejercicioG2_idEjercicioG2;

    View view;

    EjercicioG2 ejercicioG2 = null;

    ArrayList<EjercicioG2> listaEjercicios;
    ArrayList<Integer> listaIdEjercicios;
    ArrayList<Asignacion> listaAsignaciones;

    ProgressDialog progreso;
    //******** CONEXIÓN CON WEBSERVICE
    //RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_docente);

        progreso = new ProgressDialog(this);

        Intent intent = this.getIntent();
        Bundle datos = intent.getExtras();
        iddocente = datos.getInt("iddocente");
        idgrupo = datos.getInt("idgrupo");
        showToolbar("Mis Actividades" + " - " + iddocente, true);

        view = findViewById(android.R.id.content);

        listaIdEjercicios = new ArrayList<>();

        ll_rv_ejercicios = (LinearLayout) findViewById(R.id.ll_docente_act_rv);

        btn_fon = (Button) findViewById(R.id.btn_docentente_act_fon);
        btn_fon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                listaEjercicios = new ArrayList<>();
                idactividad = 1;
                cargarEjerciciosPorActividad();
                ll_rv_ejercicios.setVisibility(View.VISIBLE);

            }
        });
        btn_lex = (Button) findViewById(R.id.btn_docentente_act_lex);
        btn_lex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                listaEjercicios = new ArrayList<>();
                idactividad = 2;
                cargarEjerciciosPorActividad();
                ll_rv_ejercicios.setVisibility(View.VISIBLE);
            }
        });
        btn_sil = (Button) findViewById(R.id.btn_docentente_act_sil);
        btn_sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                listaEjercicios = new ArrayList<>();
                idactividad = 3;
                cargarEjerciciosPorActividad();
                ll_rv_ejercicios.setVisibility(View.VISIBLE);
            }
        });

        btn_crear_actividad = (Button) findViewById(R.id.btn_docente_actividad_crear);
        // btn_crear_actividad.setEnabled(false);
        btn_crear_actividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearAsignacion();

                //listar_Actividades_Docente();
            }
        });

        edt_name_actividad = (EditText) findViewById(R.id.edt_docente_actividad_name);

        cb_agregar_ejercicio = (CheckBox) findViewById(R.id.checkBox_item_ejercicio_act);
        cv_item_ejecicio = (CardView) findViewById(R.id.cv_item_ejercicio);
//        cv_item_ejecicio.getContext();
        //cb_agregar_ejercicio.setOnCheckedChangeListener();

        rv_ejercicios_act = (RecyclerView) findViewById(R.id.rv_docente_actividad_ejercicios);
        rv_ejercicios_act.setLayoutManager(new GridLayoutManager(this, 2));
        rv_ejercicios_act.setHasFixedSize(true);


    }

    private void asignacionHasEjercicios() {

        if (flag_1 < listaIdEjercicios.size()) {
            System.out.println("flag met: " + flag_1);
            ejercicioG2_idEjercicioG2 = listaIdEjercicios.get(flag_1);
            System.out.println("IdEjercicioG2: " + ejercicioG2_idEjercicioG2);
            crearAsignacionHasEjercicio();
        } else if (flag_1 == listaIdEjercicios.size()) {
            listaIdEjercicios.clear();
        }

    }

    private void buttonActive() {
        if (listaIdEjercicios.size() != 0 && !(edt_name_actividad.getText().equals(""))) {
            btn_crear_actividad.setEnabled(true);
            System.out.println("lista id ejercicios" + listaIdEjercicios.size());
        }
    }

    private void crearAsignacionHasEjercicio() {
        flag_1++;
        progreso.setMessage("Cargando...");
        progreso.show();
        String url_lh = Globals.url;
        String url =
                //"http://192.168.0.13/proyecto_dconfo/wsJSONCrearCurso.php?";
                "http://" + url_lh + "/proyecto_dconfo_v1/22wsJSONCrearAsignacion_has_ejercicioG2.php";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//recibe respuesta del webservice,cuando esta correcto
                progreso.hide();
                if (response.trim().equalsIgnoreCase("registra")) {

                    edt_name_actividad.setText("");
                    //listar_Actividades_Docente();
                    asignacionHasEjercicios();

                    Toast.makeText(getApplicationContext(), "Se ha cargado con éxito", Toast.LENGTH_LONG).show();
                } else {
                    // Toast.makeText(getContext(), "No se ha cargado con éxito", Toast.LENGTH_LONG).show();
                    Log.i("ERROR", "RESPONSE" + response.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(getContext(), "No se ha podido conectar", Toast.LENGTH_LONG).show();
                progreso.hide();
            }
        }) {//enviar para metros a webservice, mediante post
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String asignacion_id_GrupoAsignacion = String.valueOf(asignacion_idGrupoAsignacion);
                String ejercicioG2_id_EjercicioG2 = String.valueOf(ejercicioG2_idEjercicioG2);

                Map<String, String> parametros = new HashMap<>();
                parametros.put("asignacion_idGrupoAsignacion", asignacion_id_GrupoAsignacion);
                parametros.put("ejercicioG2_idEjercicioG2", ejercicioG2_id_EjercicioG2);


                return parametros;
            }
        };
        //request.add(stringRequest);
        //p25 duplicar tiempo x defecto
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);//p21

    }

    //***********************************

    private void crearAsignacion() {

        progreso.setMessage("Cargando...");
        progreso.show();
        String url_lh = Globals.url;
        String url =
                //"http://192.168.0.13/proyecto_dconfo/wsJSONCrearCurso.php?";
                "http://" + url_lh + "/proyecto_dconfo_v1/21wsJSONCrearAsignacion.php";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//recibe respuesta del webservice,cuando esta correcto
                progreso.hide();
                if (response.trim().equalsIgnoreCase("registra")) {

                    edt_name_actividad.setText("");
                    listar_Actividades_Docente();

                    Toast.makeText(getApplicationContext(), "Se ha cargado con éxito", Toast.LENGTH_LONG).show();
                } else {
                    // Toast.makeText(getContext(), "No se ha cargado con éxito", Toast.LENGTH_LONG).show();
                    Log.i("ERROR", "RESPONSE" + response.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(getContext(), "No se ha podido conectar", Toast.LENGTH_LONG).show();
                progreso.hide();
            }
        }) {//enviar para metros a webservice, mediante post
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String nameGrupoAsignacion = edt_name_actividad.getText().toString();
                String id_docente = String.valueOf(iddocente);
                String id_actividad = String.valueOf(idactividad);
                String id_grupo = String.valueOf(idgrupo);

                Map<String, String> parametros = new HashMap<>();
                parametros.put("nameGrupoAsignacion", nameGrupoAsignacion);
                parametros.put("docente_iddocente", id_docente);
                parametros.put("Actividad_idActividad", id_actividad);
                parametros.put("grupo_idgrupo", id_grupo);

                return parametros;
            }
        };
        //request.add(stringRequest);
        //p25 duplicar tiempo x defecto
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);//p21

    }

    //***********************************
    public void listar_Actividades_Docente() {


        String url_lh = Globals.url;


        String url = "http://" + url_lh +
                "/proyecto_dconfo_v1/13wsJSONConsultar_Lista_Asignaciones.php?idgrupo="+idgrupo+"&iddocente="+iddocente+"&idactividad="+idactividad;

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Asignacion asignacion = null;
                        listaAsignaciones = new ArrayList<>();

                        // Process the JSON
                        try {

                            JSONArray array = response.optJSONArray("asignacion");

                            // Loop through the array elements
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject student = array.getJSONObject(i);
                                asignacion = new Asignacion();
                                JSONObject jsonObject = null;
                                jsonObject = array.getJSONObject(i);
                                asignacion.setIdGrupoAsignacion(jsonObject.optInt("idGrupoAsignacion"));
                                asignacion.setNameGrupoAsignacion(jsonObject.optString("nameGrupoAsignacion"));
                                asignacion.setDocente_iddocente(jsonObject.optInt("docente_iddocente"));
                                asignacion.setGrupo_idgrupo(jsonObject.optInt("grupo_idgrupo"));
                                asignacion.setActividad_idActividad(jsonObject.optInt("Actividad_idActividad"));

                                listaAsignaciones.add(asignacion);
                            }

                            final List<String> listaStringAsignacion = new ArrayList<>();
                            listaStringAsignacion.add("Seleccione Id Actividad");
                            for (int i = 0; i < listaAsignaciones.size(); i++) {
                                listaStringAsignacion.add(listaAsignaciones.get(i).getIdGrupoAsignacion().toString() + " - " + listaAsignaciones.get(i).getNameGrupoAsignacion());
                            }


                        /*    Toast.makeText(getApplicationContext(), "lista Grupo estudiantes" + listaStringGrupo_Estudiantes, Toast.LENGTH_LONG).show();
                            System.out.println("listaGrupoEstudiantes size: " + listaGrupoEstudiantes.size());
                            System.out.println("listaGrupoEstudiantes: " + listaGrupoEstudiantes.get(0).getIdGrupoEstudiantes());*/

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        asignacion_idGrupoAsignacion = listaAsignaciones.get(listaAsignaciones.size() - 1).getIdGrupoAsignacion();
                        System.out.println("Asignacion_idGrupoAsignacion: " + asignacion_idGrupoAsignacion + " id actividad: " + idactividad);
                        asignacionHasEjercicios();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        System.out.println();
                        Log.d("ERROR ACTIVIDADES: ", error.toString());
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
        //stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);//p21
    }
    //***********************************


    private void cargarEjerciciosPorActividad() {

        progreso.setMessage("Cargando...");
        progreso.show();

        String iddoc = "20181";
        String url_lh = Globals.url;

        String url = "http://" + url_lh + "/proyecto_dconfo_v1/20wsJSONConsultarListaEjerciciosXactividad.php?iddocente="+iddocente+"&idactividad="+idactividad;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);

        final int MY_DEFAULT_TIMEOUT = 15000;
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        // Toast.makeText(getContext(), "No se puede cone , grupo doc" + error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("ERROR", error.toString());
        // progreso.hide();
    }

    // si esta bien el llamado a la url entonces entra a este metodo
    @Override
    public void onResponse(JSONObject response) {

        progreso.hide();
        //Toast.makeText(getApplicationContext(), "Mensaje: " + response.toString(), Toast.LENGTH_SHORT).show();

        JSONArray json = response.optJSONArray("ejerciciog2");

        try {
            for (int i = 0; i < json.length(); i++) {
                ejercicioG2 = new EjercicioG2();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);
                // jsonObject = new JSONObject(response);
                ejercicioG2.setIdEjercicioG2(jsonObject.optInt("idEjercicioG2"));
                ejercicioG2.setNameEjercicioG2(jsonObject.optString("nameEjercicioG2"));
                ejercicioG2.setIdDocente(jsonObject.optInt("docente_iddocente"));
                ejercicioG2.setIdTipo(jsonObject.optInt("Tipo_idTipo"));
                ejercicioG2.setIdActividad(jsonObject.optInt("Tipo_Actividad_idActividad"));

                listaEjercicios.add(ejercicioG2);

//idgrupo,namegrupo,curso_idcurso,curso_Instituto_idInstituto
            }
            TipoEjerciciosActividadDocenteAdapter tipoEjerciciosActividadDocenteAdapter = new TipoEjerciciosActividadDocenteAdapter(listaEjercicios, 1);
            tipoEjerciciosActividadDocenteAdapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*if (cb_agregar_ejercicio.isChecked()==true) {
                        listaIdEjercicios.add(ejercicioG2.getIdEjercicioG2());
                        System.out.println("lista id ejercicio: "+listaIdEjercicios);
                        //System.out.println("CB: "+cb_agregar_ejercicio.isChecked());
                    }*/
                    int idejercicio = listaEjercicios.get(rv_ejercicios_act.
                            getChildAdapterPosition(v)).getIdEjercicioG2();
                    listaIdEjercicios.add(idejercicio);
                    int c = getResources().getColor(R.color.green);
                    // cv_item_ejecicio.setCardBackgroundColor(c);
                    System.out.println("lista id ejercicio: " + listaIdEjercicios);
                    // buttonActive();
                    Toast.makeText(getApplicationContext(), "lista id ejercicios: " + listaIdEjercicios.toString(), Toast.LENGTH_SHORT).show();

                }
            });
            System.out.println("lista ejercicios: " + listaEjercicios.size() + " idactividad: " + idactividad);
            rv_ejercicios_act.setAdapter(tipoEjerciciosActividadDocenteAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("error", response.toString());

            //Toast.makeText(getApplicationContext(), "No se ha podido establecer conexión: " + response.toString(), Toast.LENGTH_LONG).show();

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

    //método que permite volver al padre conservando las variables
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
