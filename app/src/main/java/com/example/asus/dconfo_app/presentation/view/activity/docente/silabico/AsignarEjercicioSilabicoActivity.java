package com.example.asus.dconfo_app.presentation.view.activity.docente.silabico;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.example.asus.dconfo_app.presentation.view.adapter.TipoEjerciciosDocenteAdapter;
import com.example.asus.dconfo_app.presentation.view.adapter.TipoEjerciciosDocente_EjerG2Adapter;
import com.example.asus.dconfo_app.presentation.view.adapter.TipoEjerciciosG2DocenteAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AsignarEjercicioSilabicoActivity extends AppCompatActivity {
    EditText edt_idEstudiante;
    EditText edt_idEjercicio;
    EditText edt_TipoDeber;
    Button btn_asignar;
    ProgressDialog progreso;

    TextView txterror;

    ArrayList<Estudiante> listaEstudiantes;
    //ArrayList<EjercicioG1> listaEjercicios;
    ArrayList<EjercicioG2> listaEjercicios;
    ArrayList<EjercicioG2> listaEjerciciosT2;

    RecyclerView rv_listaEjercicios;
    RecyclerView rv_listaEjercicios_E2;

    Spinner spinner;
    Spinner spinnerEjercicios;
    Spinner spinnerTipoDeber;
    Integer idgrupo;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;

    List<String> listaStringEjercicios = new ArrayList<>();

    EjercicioG1 ejercicioG1 = null;
    EjercicioG2 ejercicioG2 = null;

    EjercicioG1 ejercicio_G1 = null;
    EjercicioG2 ejercicio_G2 = null;

    TipoEjerciciosDocente_EjerG2Adapter tipoEjerciciosDocenteEjerG2Adapter;
    TipoEjerciciosG2DocenteAdapter tipoEjerciciosG2DocenteAdapter;

    int tipo_ejercicio;
    int ID_ejercicio;

    //******** CONEXIÓN CON WEBSERVICE
    //RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    StringRequest stringRequest;

    public int iddocente = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignar_ejercicio_silabico);
        Bundle datos = this.getIntent().getExtras();

        iddocente = datos.getInt("iddocente");
        idgrupo = datos.getInt("idgrupo");

        txterror = (TextView) findViewById(R.id.txt_error);

        this.setTitle("id docente:" + iddocente);
        progreso = new ProgressDialog(getApplicationContext());

        edt_idEstudiante = findViewById(R.id.edt_id_silabico_estudiate_deber);
        edt_idEjercicio = findViewById(R.id.edt_id_silabico_ejercicio_deber);
        edt_TipoDeber = findViewById(R.id.edt_docente_silabico_tipoDeber);

        btn_asignar = findViewById(R.id.btn_silabico_asignarDeber_deber);

        rv_listaEjercicios = findViewById(R.id.rv_docente_sil2_asignar);
        rv_listaEjercicios.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //rv_listaEjercicios.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        rv_listaEjercicios.setHasFixedSize(true);

        rv_listaEjercicios_E2 = findViewById(R.id.rv_docente_sil2_asignar_E2);
        rv_listaEjercicios_E2.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //rv_listaEjercicios_E2.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        rv_listaEjercicios_E2.setHasFixedSize(true);

        spinner = (Spinner) findViewById(R.id.sp_docente_silabico_estudiantes);
        spinnerEjercicios = (Spinner) findViewById(R.id.sp_docente_silabico_ejercicios);
        spinnerTipoDeber = (Spinner) findViewById(R.id.sp_docente_silabico_tipoDeber);

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


        btn_asignar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService();

            }
        });
        listarEstudiantes();
        listarEjerciciosDocente();
        //listarEjerciciosDocenteT2();
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("fecha actual: " + simpleDateFormat.format(calendar.getTime()));
        //listar_Ejercicios_Docente();
    }

    //***********************************
    public void listarEstudiantes() {

        String url_lh = Globals.url;

       // String url = "http://" + url_lh + "/proyecto_dconfo_v1/wsJSONConsultarListaEstudiantesGrupoDocente.php?idgrupo=" + idgrupo;
        String url = "http://" + url_lh + "/proyecto_dconfo_v1/4wsJSONConsultarListaEstudiantesGrupoDocente.php?idgrupo=" + idgrupo;

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


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

                                JSONObject student = array.getJSONObject(i);
                                estudiante = new Estudiante();
                                JSONObject jsonObject = null;
                                jsonObject = array.getJSONObject(i);
                                estudiante.setIdestudiante(jsonObject.optInt("estudiante_idestudiante"));
                                estudiante.setNameestudiante(jsonObject.optString("nameEstudiante"));

                                listaEstudiantes.add(estudiante);
                            }

                            final List<String> listaStringEstudiantes = new ArrayList<>();
                            final List<String> listaStringId_Name = new ArrayList<>();
                            listaStringEstudiantes.add("Seleccione Id Estudiante");
                            for (int i = 0; i < listaEstudiantes.size(); i++) {
                                listaStringEstudiantes.add(listaEstudiantes.get(i).getIdestudiante().toString());
                                listaStringId_Name.add(listaEstudiantes.get(i).getIdestudiante().toString() + " - " + listaEstudiantes.get(i).getNameestudiante().toString());
                            }
                            System.out.println("lista estudiantes: " + listaStringId_Name.toString());
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

                            // Toast.makeText(getApplicationContext(), "lista estudiantes" + listaStringEstudiantes, Toast.LENGTH_LONG).show();
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
    public void listarEjerciciosDocente() {
        // final String usuario = etUsuario.getText().toString();
        // final String password = etPass.getText().toString();

        // INICIAR LA CONEXION CON VOLLEY

        Toast.makeText(getApplicationContext(), "MIS EJERCICIOS FUNCION: ", Toast.LENGTH_LONG).show();

        String url_lh = Globals.url;

        System.out.println("Ejercicio Silábico docente buscar: " + iddocente);

        //String url = "http://" + url_lh + "/proyecto_dconfo/wsJSONConsultarListaEjerciciosDocente.php?iddocente=" + iddocente;
        //String url = "http://" + url_lh + "/proyecto_dconfo_v1/wsJSONConsultarListaEjercicios_Silabico_A3_Docente.php?iddocente=" + iddocente + "& idactividad=" + 3;
        String url = "http://" + url_lh + "/proyecto_dconfo_v1/3wsJSONConsultarListaEjerciciosDocente.php?iddocente=" + iddocente + "& idactividad=" + 3;



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

                        // EjercicioG1 ejercicioG1 = null;


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

                            // final List<String> listaStringEjercicios = new ArrayList<>();
                            final List<String> listaStringNombres = new ArrayList<>();
                            final List<String> listaStringId_Nombre = new ArrayList<>();

                            listaStringEjercicios.add("Seleccione  Ejercicio");
                            for (int i = 0; i < listaEjercicios.size(); i++) {
                                listaStringEjercicios.add(listaEjercicios.get(i).getIdEjercicioG2().toString());
                                listaStringNombres.add(listaEjercicios.get(i).getNameEjercicioG2());
                                listaStringId_Nombre.add(listaEjercicios.get(i).getIdEjercicioG2().toString() + " - " + listaEjercicios.get(i).getNameEjercicioG2());
                                // listaStringId_Nombre.add();
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, listaStringEjercicios);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerEjercicios.setAdapter(adapter);
                            spinnerEjercicios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position != 0) {
                                        edt_idEjercicio.setText(listaStringEjercicios.get(position));
                                        ejercicio_G1 = new EjercicioG1();
                                        ejercicio_G1.setIdTipo(listaEjercicios.get(position).getIdTipo());
                                    } else {

                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });


                            tipoEjerciciosDocenteEjerG2Adapter = new TipoEjerciciosDocente_EjerG2Adapter(listaEjercicios);
                            tipoEjerciciosDocenteEjerG2Adapter.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(getApplicationContext(), "Seleccion rv1: " +
                                            listaEjercicios.get(rv_listaEjercicios.
                                                    getChildAdapterPosition(v)).getIdEjercicioG2(), Toast.LENGTH_SHORT).show();

                                    edt_idEjercicio.setText(listaEjercicios.get(rv_listaEjercicios.
                                            getChildAdapterPosition(v)).getIdEjercicioG2().toString());

                                    tipo_ejercicio = listaEjercicios.get(rv_listaEjercicios.
                                            getChildAdapterPosition(v)).getIdTipo();

                                    ID_ejercicio = listaEjercicios.get(rv_listaEjercicios.
                                            getChildAdapterPosition(v)).getIdEjercicioG2();

                                }
                            });

                            rv_listaEjercicios.setAdapter(tipoEjerciciosDocenteEjerG2Adapter);
                            rv_listaEjercicios.setVisibility(View.VISIBLE);

                            Toast.makeText(getApplicationContext(), "lista EJERCICIOS: " + listaStringEjercicios.toString(), Toast.LENGTH_LONG).show();
                           /* System.out.println("estudiantes size: " + listaEstudiantes.size());
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
                        txterror.setText(error.toString());
                        Log.d("ERROR Ejercicios: ", error.toString());
                        Toast.makeText(getApplicationContext(), "ERROR" + error.toString(), Toast.LENGTH_LONG).show();
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
/*
    //***********************************
    public void listarEjerciciosDocenteT2() {
        // final String usuario = etUsuario.getText().toString();
        // final String password = etPass.getText().toString();

        // INICIAR LA CONEXION CON VOLLEY

        //Toast.makeText(getApplicationContext(), "MIS EJERCICIOS FUNCION: ", Toast.LENGTH_LONG).show();

        String url_lh = Globals.url;

        System.out.println("Ejercicio Silábico docente buscar: " + iddocente);

        //String url = "http://" + url_lh + "/proyecto_dconfo/wsJSONConsultarListaEjerciciosDocente.php?iddocente=" + iddocente;
        String url = "http://" + url_lh + "/proyecto_dconfo_v1/wsJSON_T2_ConsultarListaEjercicios_Silabico_A3_Docente.php?iddocente=" + iddocente + "& idactividad=" + 3;


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

                        listaEjerciciosT2 = new ArrayList<>();

                        // EjercicioG2 ejercicioG2 = null;


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

                                listaEjerciciosT2.add(ejercicioG2);
                            }

                            // final List<String> listaStringEjercicios = new ArrayList<>();
                            final List<String> listaStringNombres = new ArrayList<>();
                            final List<String> listaStringId_Nombre = new ArrayList<>();

                            // listaStringEjercicios.add("Seleccione  Ejercicio Tipo 2: ");
                            for (int i = 0; i < listaEjerciciosT2.size(); i++) {
                                listaStringEjercicios.add(listaEjerciciosT2.get(i).getIdEjercicioG2().toString());
                                listaStringNombres.add(listaEjerciciosT2.get(i).getNameEjercicioG2());
                                listaStringId_Nombre.add(listaEjerciciosT2.get(i).getIdEjercicioG2().toString() + " - " + listaEjerciciosT2.get(i).getNameEjercicioG2());
                                // listaStringId_Nombre.add();
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, listaStringEjercicios);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerEjercicios.setAdapter(adapter);
                            spinnerEjercicios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position != 0) {
                                        edt_idEjercicio.setText(listaStringEjercicios.get(position));
                                        // ejercicio_G2.setIdTipo(listaEjerciciosT2.get(position - listaStringEjercicios.size()).getIdTipo());
                                        System.out.println("id ejercicio G2 size---: " + listaStringEjercicios.size());
                                        System.out.println("id ejercicio G2 position---: " + position);
                                        System.out.println("posicion en lista G2 ---: " + (listaStringEjercicios.size() - position));
                                        Toast.makeText(getApplicationContext(), "spinner g2: ", Toast.LENGTH_LONG).show();
                                    } else {

                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            tipoEjerciciosG2DocenteAdapter = new TipoEjerciciosG2DocenteAdapter(listaEjerciciosT2, getApplicationContext());
                            tipoEjerciciosG2DocenteAdapter.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(getApplicationContext(), "Seleccion rv2: " +
                                            listaEjerciciosT2.get(rv_listaEjercicios.
                                                    getChildAdapterPosition(v)).getIdEjercicioG2(), Toast.LENGTH_SHORT).show();

                                    edt_idEjercicio.setText(listaEjerciciosT2.get(rv_listaEjercicios_E2.
                                            getChildAdapterPosition(v)).getIdEjercicioG2().toString());

                                    tipo_ejercicio = listaEjerciciosT2.get(rv_listaEjercicios_E2.
                                            getChildAdapterPosition(v)).getIdTipo();

                                    ID_ejercicio = listaEjerciciosT2.get(rv_listaEjercicios_E2.
                                            getChildAdapterPosition(v)).getIdEjercicioG2();

                                }
                            });

                            rv_listaEjercicios_E2.setAdapter(tipoEjerciciosG2DocenteAdapter);
                            rv_listaEjercicios_E2.setVisibility(View.VISIBLE);


                            // Toast.makeText(getApplicationContext(), "lista EJERCICIOS: " + listaStringEjercicios.toString(), Toast.LENGTH_LONG).show();
                           /* System.out.println("estudiantes size: " + listaEstudiantes.size());
                            System.out.println("estudiantes: " + listaEstudiantes.get(0).getIdestudiante());*/

                     /*   } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        System.out.println();
                        txterror.setText(error.toString());
                        Log.d("ERROR Ejercicios: ", error.toString());
                        Toast.makeText(getApplicationContext(), "ERROR" + error.toString(), Toast.LENGTH_LONG).show();
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
    }*/
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

                    // Toast.makeText(getContext(), "Se ha cargado con éxito", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No se ha cargado con éxito", Toast.LENGTH_LONG).show();
                    Log.i("ERROR", "RESPONSE: " + response.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No se ha podido conectar", Toast.LENGTH_LONG).show();
                //  progreso.hide();
            }
        }) {//enviar para metros a webservice, mediante post
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String idestudiante = edt_idEstudiante.getText().toString();
                // String idejercicio = edt_idEjercicio.getText().toString();
                String idejercicio = String.valueOf(ID_ejercicio);
                System.out.println("id ejercicio: " + idejercicio);
                String iddocente1 = String.valueOf(iddocente);
                String fecha = simpleDateFormat.format(calendar.getTime());
                String tipoDeber = edt_TipoDeber.getText().toString();
                String calificacion = "";
//                Toast.makeText(getApplicationContext(), "La fecha de hoy: " + fecha, Toast.LENGTH_SHORT).show();

                Map<String, String> parametros = new HashMap<>();
                parametros.put("estudiante_idestudiante", idestudiante);
                parametros.put("docente_iddocente", iddocente1);
                parametros.put("EjercicioG2_idEjercicioG2", idejercicio);

                parametros.put("fechaestudiante_has_Deber", fecha);
                parametros.put("tipoDeber", tipoDeber);
                    //parametros.put("EjercicioG1_idEjercicioG2", "");
                   /* if (ejercicio_G1.getIdTipo() == 5) {
                        parametros.put("EjercicioG1_idEjercicioG1", idejercicio);
                        System.out.println("id ejercicio G1: " + ejercicio_G1.getNameEjercicio());
                    }*/
                    System.out.println("id ejercicio G1 null: no es null ");


                parametros.put("fechaestudiante_has_Deber", fecha);


                return parametros;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);//p21

    }


}
