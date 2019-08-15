package com.example.asus.dconfo_app.presentation.view.activity.docente.fonico;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.asus.dconfo_app.domain.model.EjercicioG1;
import com.example.asus.dconfo_app.domain.model.EjercicioG2;
import com.example.asus.dconfo_app.domain.model.Estudiante;
import com.example.asus.dconfo_app.domain.model.VolleySingleton;
import com.example.asus.dconfo_app.helpers.Globals;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AsignarEjercicioFonicoActivity extends AppCompatActivity {
    EditText edt_idEstudiante;
    EditText edt_idEjercicio;
    //EditText edt_TipoDeber;
    Button btn_asignar;
    ProgressDialog progreso;

    ArrayList<Estudiante> listaEstudiantes;
    ArrayList<EjercicioG2> listaEjercicios;

    Spinner spinner;
    Spinner spinnerEjercicios;

    Spinner spinnerTipoDeber;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    EditText edt_TipoDeber;



    //******** CONEXIÓN CON WEBSERVICE
    //RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    StringRequest stringRequest;

    public int iddocente = 0;
    Integer idgrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignar_ejercicio_fonico);
        Bundle datos = this.getIntent().getExtras();

        iddocente = datos.getInt("iddocente");
        idgrupo = datos.getInt("idgrupo");
        System.out.println("id GRUPO: " + idgrupo);
        //Toast.makeText(getApplicationContext(), "id docente: " + iddocente, Toast.LENGTH_LONG).show();
        //toolbar.setLabelFor();
        this.setTitle("id docente:" + iddocente);
        progreso = new ProgressDialog(getApplicationContext());

        edt_idEstudiante = findViewById(R.id.edt_id_fonico_estudiate_deber);
        edt_idEjercicio = findViewById(R.id.edt_id_fonico_ejercicio_deber);

        edt_TipoDeber = findViewById(R.id.edt_docente_fonico_tipoDeber);

        btn_asignar = findViewById(R.id.btn_fonico_asignarDeber_deber);

        spinner = (Spinner) findViewById(R.id.sp_docente_fonico_estudiantes);
        spinnerEjercicios = (Spinner) findViewById(R.id.sp_docente_fonico_ejercicios);
        spinnerTipoDeber = (Spinner) findViewById(R.id.sp_docente_fonico_tipoDeber);

        final ArrayList<String>listaDeber=new ArrayList<>();
        listaDeber.add("Seleccione :");
        listaDeber.add("EJERCICIO");
        listaDeber.add("EVALUAR");
        listaDeber.add("PRUEBA");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, listaDeber);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoDeber.setAdapter(adapter);
        spinnerTipoDeber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    // edt_idEstudiante.setText(listaStringEstudiantes.get(position));
                    edt_TipoDeber.setText(listaDeber.get(position));
                } else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("fecha actual: " + simpleDateFormat.format(calendar.getTime()));

        btn_asignar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // cargarWebService();

            }
        });
        showToolbar("Asignacionar Ejercicios" + " - " + iddocente, true);
        listarEstudiantes();
        listarEjerciciosDocente();
    }

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

    //***********************************
    public void listarEstudiantes() {
        // final String usuario = etUsuario.getText().toString();
        // final String password = etPass.getText().toString();

        // INICIAR LA CONEXION CON VOLLEY

        String url_lh = Globals.url;

        //String url = "http://" + url_lh + "/proyecto_dconfo_v1/wsJSONConsultarListaEstudiantesGrupoDocente.php?idgrupo=" + idgrupo;
        String url = "http://" + url_lh + "/proyecto_dconfo_v1/4wsJSONConsultarListaEstudiantesGrupoDocente.php?idgrupo="+idgrupo;
        //String url = "http://" + url_lh + "/proyecto_dconfo/wsJSONConsultarListaCursos.php";

        //jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, this, this);
        //VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);//p21

        //RequestQueu requestQueue = Volley.newRequestQueue(mContext);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        // Initialize a new JsonObjectRequest instance
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

                                listaEstudiantes.add(estudiante);
                            }

                            final List<String> listaStringEstudiantes = new ArrayList<>();
                            listaStringEstudiantes.add("Seleccione Id Estudiante");
                            for (int i = 0; i < listaEstudiantes.size(); i++) {
                                listaStringEstudiantes.add(listaEstudiantes.get(i).getIdestudiante().toString());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, listaStringEstudiantes);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(adapter);
                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position != 0) {
                                        edt_idEstudiante.setText(listaStringEstudiantes.get(position));
                                    } else {

                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            Toast.makeText(getApplicationContext(), "lista estudiantes" + listaStringEstudiantes, Toast.LENGTH_LONG).show();
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
                        Log.d("ERROR estudiantes: ", error.toString());
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
        //stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);//p21
    }
    //***********************************

    //***********************************
    public void listarEjerciciosDocente() {
        // final String usuario = etUsuario.getText().toString();
        // final String password = etPass.getText().toString();

        // INICIAR LA CONEXION CON VOLLEY

        String url_lh = Globals.url;

        System.out.println("Ejercicio Fonico docente buscar: " + iddocente);

        //String url = "http://" + url_lh + "/proyecto_dconfo/wsJSONConsultarListaEjerciciosDocente.php?iddocente=" + iddocente;
        //String url = "http://" + url_lh + "/proyecto_dconfo_v1/wsJSONConsultarListaEjercicios_Fonico1_Docente.php?iddocente=" + iddocente;
        String url = "http://" + url_lh + "/proyecto_dconfo_v1/3wsJSONConsultarListaEjerciciosDocente.php?iddocente="+iddocente+"&idactividad="+1;


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Do something with response

                        listaEjercicios = new ArrayList<>();

                        EjercicioG2 ejercicioG2 = null;


                        try {
                            JSONArray json = response.optJSONArray("ejerciciog2");
                            for (int i = 0; i < json.length(); i++) {
                                ejercicioG2 = new EjercicioG2();
                                JSONObject jsonObject = null;
                                jsonObject = json.getJSONObject(i);
                                ejercicioG2.setIdEjercicioG2(jsonObject.optInt("idEjercicioG2"));
                                ejercicioG2.setNameEjercicioG2(jsonObject.optString("nameEjercicioG2"));
                                ejercicioG2.setIdDocente(jsonObject.optInt("docente_iddocente"));
                                ejercicioG2.setIdTipo(jsonObject.optInt("Tipo_idTipo"));
                                ejercicioG2.setIdActividad(jsonObject.optInt("Tipo_Actividad_idActividad"));

                                listaEjercicios.add(ejercicioG2);
                            }

                            final List<String> listaStringEjercicios = new ArrayList<>();
                            listaStringEjercicios.add("Seleccione Id Ejercicio");
                            for (int i = 0; i < listaEjercicios.size(); i++) {
                                listaStringEjercicios.add(listaEjercicios.get(i).getIdEjercicioG2().toString());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, listaStringEjercicios);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerEjercicios.setAdapter(adapter);
                            spinnerEjercicios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position != 0) {
                                        edt_idEjercicio.setText(listaStringEjercicios.get(position));
                                    } else {

                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                           /* Toast.makeText(getApplicationContext(), "lista estudiantes" + listaStringEstudiantes, Toast.LENGTH_LONG).show();
                            System.out.println("estudiantes size: " + listaEstudiantes.size());
                            System.out.println("estudiantes: " + listaEstudiantes.get(0).getIdestudiante());*/

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
                        Log.d("ERROR Ejercicios: ", error.toString());
                    }
                }
        );
        final int MY_DEFAULT_TIMEOUT = 15000;
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonObjectRequest);
        //stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);//p21
    }
    //***********************************

    //***********************************


    private void cargarWebService() {

        //     progreso.setMessage("Cargando...");
        //     progreso.show();
        String url_lh = Globals.url;
        String url =
                //"http://192.168.0.13/proyecto_dconfo/wsJSONCrearCurso.php?";
                "http://" + url_lh + "/proyecto_dconfo_v1/5wsJSONAsignarDeberEstudiante.php";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//recibe respuesta del webservice,cuando esta correcto
                // progreso.hide();
                if (response.trim().equalsIgnoreCase("registra")) {
                    edt_idEstudiante.setText("");
                    edt_idEjercicio.setText("");
                    edt_TipoDeber.setText("");
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
                //  progreso.hide();
            }
        }) {//enviar para metros a webservice, mediante post
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String idestudiante = edt_idEstudiante.getText().toString();
                String idejercicio = edt_idEjercicio.getText().toString();
                String iddocente1 = String.valueOf(iddocente);
                String fecha = simpleDateFormat.format(calendar.getTime());
                String tipoDeber = edt_TipoDeber.getText().toString();
                String calificacion = "";


                Map<String, String> parametros = new HashMap<>();
                parametros.put("estudiante_idestudiante", idestudiante);
                parametros.put("docente_iddocente", iddocente1);
                parametros.put("EjercicioG2_idEjercicioG2", idejercicio);
                parametros.put("fechaestudiante_has_Deber", fecha);
                parametros.put("tipoDeber", tipoDeber);


                return parametros;
            }
        };
        //request.add(stringRequest);
        //p25 duplicar tiempo x defecto
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);//p21

        //reemplazar espacios en blanco del nombre por %20
        // url = url.replace(" ", "%20");

        //hace el llamado a la url,no usa en p12
        /*jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);*/
    }

}
