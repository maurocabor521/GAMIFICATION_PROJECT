package com.example.asus.dconfo_app.presentation.view.activity.docente.grupos;

import android.app.ProgressDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
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
import com.example.asus.dconfo_app.domain.model.Curso;
import com.example.asus.dconfo_app.domain.model.Estudiante;
import com.example.asus.dconfo_app.domain.model.Grupo_Estudiantes;
import com.example.asus.dconfo_app.domain.model.VolleySingleton;
import com.example.asus.dconfo_app.helpers.Globals;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrupoNuevoActivity extends AppCompatActivity {
    int idgrupo;
    int iddocente;
    ProgressDialog progreso;

    Spinner sp_GrupoEstudiantes_nuevo;
    EditText edt_NombreGrupo_nuevo;
    Button btn_Crear_Nuevo_GrupoEst;
    ListView lv_estudiantes;

    private String idGrupoEstudiante;
    private int idEstudiante;
    private int flag = 0;

    ArrayList<String> listaStringIdGrupoEstudiantes;
    ArrayList<Estudiante> listaEstudiantes;
    ArrayList<Integer> listaIdEstudiantes;
    ArrayList<Grupo_Estudiantes> listaGrupoEstudiantes;
    List<String> listaStringEstudiantes = new ArrayList<>();

    //RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo_nuevo);
        showToolbar("Grupo Nuevo de Estudiantes", true);

        Bundle datos = this.getIntent().getExtras();
        idgrupo = datos.getInt("idgrupo");
        iddocente = datos.getInt("iddocente");

        sp_GrupoEstudiantes_nuevo = (Spinner) findViewById(R.id.sp_GrupoEstNew_Estudiantes);
        edt_NombreGrupo_nuevo = (EditText) findViewById(R.id.edt_GrupoEstNew_nombreGrupo);
        lv_estudiantes = (ListView) findViewById(R.id.lv_GrupoEst_estudiantes);

        listaIdEstudiantes = new ArrayList<>();
        listarEstudiantes();

        btn_Crear_Nuevo_GrupoEst = (Button) findViewById(R.id.btn_GrupoEstNew_CrearGrupoEst);
        btn_Crear_Nuevo_GrupoEst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService();
            }
        });
    }

    //***********************************
    public void listarEstudiantes() {

        String url_lh = Globals.url;

        String url = "http://" + url_lh + "/proyecto_dconfo_v1/4wsJSONConsultarListaEstudiantesGrupoDocente.php?idgrupo=" + idgrupo;

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Do something with response
                        //mTextView.setText(response.toString());

                        Curso curso = null;
                        Estudiante estudiante = null;


                        ArrayList<Curso> listaDCursos = new ArrayList<>();
                        //listaCursos1 = new ArrayList<>();

                        listaEstudiantes = new ArrayList<>();

                        // Process the JSON
                        try {
                            // Get the JSON array
                            //JSONArray array = response.getJSONArray("students");
                            JSONArray array = response.optJSONArray("grupo_h_e");

                            // Loop through the array elements
                            for (int i = 0; i < array.length(); i++) {
                                // curso = new Curso();
                                // JSONObject jsonObject = null;
                                // jsonObject = json.getJSONObject(i);

                                // Get current json object
                                JSONObject student = array.getJSONObject(i);
                                estudiante = new Estudiante();
                                JSONObject jsonObject = null;
                                jsonObject = array.getJSONObject(i);
                                estudiante.setIdestudiante(jsonObject.optInt("estudiante_idestudiante"));
                                estudiante.setNameestudiante(jsonObject.optString("nameEstudiante"));

                                listaEstudiantes.add(estudiante);
                            }

                            listaStringEstudiantes = new ArrayList<>();
                            listaStringEstudiantes.add("Seleccione Id Estudiante");
                            for (int i = 0; i < listaEstudiantes.size(); i++) {
                                listaStringEstudiantes.add(listaEstudiantes.get(i).getIdestudiante().toString() + " - " + listaEstudiantes.get(i).getNameestudiante());
                            }

                            listaIdEstudiantes.add(0000);

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, listaStringEstudiantes);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sp_GrupoEstudiantes_nuevo.setAdapter(adapter);
                            sp_GrupoEstudiantes_nuevo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position != 0) {
                                        //listaIdEstudiantes.add(listaEstudiantes.get(position - 1).getIdestudiante());
                                        listaIdEstudiantes.add(listaEstudiantes.get(position).getIdestudiante());
                                        //System.out.println("lista id est: " + listaIdEstudiantes.toString());
//                                        Toast.makeText(getApplicationContext(), "id est: " + listaIdEstudiantes.get(position), Toast.LENGTH_LONG).show();
                                        showListView();
                                    } else {

                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            //Toast.makeText(getApplicationContext(), "lista estudiantes" + listaStringEstudiantes, Toast.LENGTH_LONG).show();
                            System.out.println("estudiantes size: " + listaEstudiantes.size());
                            System.out.println("estudiantes: " + listaEstudiantes.get(0).getIdestudiante());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        System.out.println();
                        Log.d("ERROR: ", error.toString());
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
        //stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);//p21
    }

    //***********************************
    public void showListView() {
        List<String> cad = new ArrayList<String>();
        cad.add("uno");
        cad.add("dos");
        cad.add("tres");
        ArrayAdapter<String> adapterListView = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listaIdEstudiantes);
        lv_estudiantes.setAdapter(adapterListView);
        Toast.makeText(getApplicationContext(), "lista cad:" + cad.size(), Toast.LENGTH_SHORT).show();
        lv_estudiantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String informacion="Id: "+lst_NomAli_de_Receta.get(position-1)+"\n";
                //Toast.makeText(getContext(),"nombre: "+informacion,Toast.LENGTH_SHORT).show();
            }
        });
    }

    //***********************************

    private void cargarWebService() {


        String url_lh = Globals.url;
        String url =
                //"http://192.168.0.13/proyecto_dconfo/wsJSONCrearCurso.php?";
                "http://" + url_lh + "/proyecto_dconfo_v1/18wsJSONCrearGrupoEstudiantesDocente.php";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//recibe respuesta del webservice,cuando esta correcto
                // progreso.hide();
                if (response.trim().equalsIgnoreCase("registra")) {
                    edt_NombreGrupo_nuevo.setText("");
                    listarGrupo_Estudiantes_Docente();

                } else {
                    // Toast.makeText(getContext(), "No se ha cargado con éxito", Toast.LENGTH_LONG).show();
                    Log.i("ERROR :", "RESPONSE: " + response.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(getContext(), "No se ha podido conectar", Toast.LENGTH_LONG).show();
                //  progreso.hide();
            }
        }) {//enviar para metros a webservice, mediante post
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                String name_grupo_estudiante = edt_NombreGrupo_nuevo.getText().toString();
                String grupo_idgrupo = String.valueOf(idgrupo);
                String docente_iddocente = String.valueOf(iddocente);


                Map<String, String> parametros = new HashMap<>();
                parametros.put("name_grupo_estudiante", name_grupo_estudiante);
                parametros.put("docente_iddocente", docente_iddocente);
                parametros.put("grupo_idgrupo", grupo_idgrupo);


                System.out.println("PARAMETROS : " + parametros.toString());

                return parametros;
            }
        };


        //request.add(stringRequest);
        //p25 duplicar tiempo x defecto
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);//p21
        // if (flagAsignarEjercicio==true){


        //asignar_deber_Ejercicios_de_asignacion();
        //}
    }//cargarWebService --> asignar Deber a Estudiante

    //***********************************
    public void listarGrupo_Estudiantes_Docente() {


        String url_lh = Globals.url;

        String url = "http://" + url_lh +
                "/proyecto_dconfo_v1/12wsJSONConsultar_Lista_Grupo_Est.php?idgrupo=" + idgrupo + "& iddocente=" + iddocente;

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Grupo_Estudiantes grupo_estudiantes = null;


                        ArrayList<Curso> listaDCursos = new ArrayList<>();
                        //listaCursos1 = new ArrayList<>();

                        listaGrupoEstudiantes = new ArrayList<>();

                        // Process the JSON
                        try {

                            JSONArray array = response.optJSONArray("grupo_estudiante");

                            // Loop through the array elements
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject student = array.getJSONObject(i);
                                grupo_estudiantes = new Grupo_Estudiantes();
                                JSONObject jsonObject = null;
                                jsonObject = array.getJSONObject(i);
                                grupo_estudiantes.setIdGrupoEstudiantes(jsonObject.optInt("idgrupo_estudiante"));
                                grupo_estudiantes.setNameGrupoEstudiantes(jsonObject.optString("name_grupo_estudiante"));
                                grupo_estudiantes.setIdDocente(jsonObject.optInt("docente_iddocente"));
                                grupo_estudiantes.setIdGrupo(jsonObject.optInt("grupo_idgrupo"));

                                listaGrupoEstudiantes.add(grupo_estudiantes);
                            }

                            final List<String> listaStringGrupo_Estudiantes = new ArrayList<>();

                            listaStringGrupo_Estudiantes.add("Seleccione Id Grupo");

                            listaStringIdGrupoEstudiantes = new ArrayList<>();
                            // listaStringEstudiantes
                            for (int i = 0; i < listaGrupoEstudiantes.size(); i++) {
                                listaStringGrupo_Estudiantes.add(listaGrupoEstudiantes.get(i).getIdGrupoEstudiantes().toString() + " - " + listaGrupoEstudiantes.get(i).getNameGrupoEstudiantes());
                                listaStringIdGrupoEstudiantes.add(listaGrupoEstudiantes.get(i).getIdGrupoEstudiantes().toString());
                            }


                            // Toast.makeText(getApplicationContext(), "lista Grupo estudiantes" + listaStringGrupo_Estudiantes, Toast.LENGTH_LONG).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println("listaStringGrupoEstudiantes size: " + listaStringIdGrupoEstudiantes.size());
                        System.out.println("listaStringGrupoEstudiantes: " + listaStringIdGrupoEstudiantes.toString());

                        idGrupoEstudiante = listaStringIdGrupoEstudiantes.get(listaStringIdGrupoEstudiantes.size() - 1);

                        cargarEstudiante();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        System.out.println();
                        Log.d("ERROR: ", error.toString());
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
        //stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);//p21
    }

    //***********************************
    private void cargarEstudiante() {
        if (flag < listaIdEstudiantes.size()) {

            System.out.println("flag cargarEstudiante: " + flag);
            System.out.println("flag listaIdEstudiantes: " + listaIdEstudiantes.toString());
            idEstudiante = listaIdEstudiantes.get(flag);
            System.out.println("idEstudiante: " + idEstudiante);

            cargarEstudiante_Grupo_Has_Estudiante();

        } else if (flag == listaIdEstudiantes.size()) {
            listaIdEstudiantes.clear();
        }
    }
    //***********************************

    private void cargarEstudiante_Grupo_Has_Estudiante() {

        flag++;

        String url_lh = Globals.url;
        String url =
                //"http://192.168.0.13/proyecto_dconfo/wsJSONCrearCurso.php?";
                "http://" + url_lh + "/proyecto_dconfo_v1/19wsJSONRegistro_GrupoEst_H_Estudiante.php";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//recibe respuesta del webservice,cuando esta correcto
                // progreso.hide();
                if (response.trim().equalsIgnoreCase("registra")) {

                    cargarEstudiante();

                    Toast.makeText(getApplicationContext(), "Se ha cargado Estudiante con éxito", Toast.LENGTH_LONG).show();
                } else {

                    Log.i("ERROR :", "RESPONSE: " + response.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(getContext(), "No se ha podido conectar", Toast.LENGTH_LONG).show();
                //  progreso.hide();
            }
        }) {//enviar para metros a webservice, mediante post
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                String grupo_estudiante_idgrupo_estudiante = idGrupoEstudiante;
                String estudiante_idestudiante = String.valueOf(idEstudiante);

                Map<String, String> parametros = new HashMap<>();
                parametros.put("grupo_estudiante_idgrupo_estudiante", grupo_estudiante_idgrupo_estudiante);
                parametros.put("estudiante_idestudiante", estudiante_idestudiante);

                System.out.println("PARAMETROS GE_H_EST: " + parametros.toString());

                return parametros;
            }
        };


        //request.add(stringRequest);
        //p25 duplicar tiempo x defecto
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);//p21
        // if (flagAsignarEjercicio==true){


        //asignar_deber_Ejercicios_de_asignacion();
        //}
    }//cargarWebService --> asignar Deber a Estudiante

    //**********************************************************************************************


    //método que permite volver al padre conservando las variables
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
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
