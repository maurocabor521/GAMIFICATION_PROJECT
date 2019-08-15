package com.example.asus.dconfo_app.presentation.view.activity.docente;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.example.asus.dconfo_app.domain.model.Asignacion;
import com.example.asus.dconfo_app.domain.model.AsignacionHasEjercicioG2;
import com.example.asus.dconfo_app.domain.model.Curso;
import com.example.asus.dconfo_app.domain.model.EjercicioG1;
import com.example.asus.dconfo_app.domain.model.EjercicioG2;
import com.example.asus.dconfo_app.domain.model.Estudiante;
import com.example.asus.dconfo_app.domain.model.GrupoEstudiantesHasDeber;
import com.example.asus.dconfo_app.domain.model.GrupoHEstudiante;
import com.example.asus.dconfo_app.domain.model.GrupoHasEstudiante;
import com.example.asus.dconfo_app.domain.model.Grupo_Estudiantes;
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

public class AsignarEstudianteDeberActivity extends AppCompatActivity {

    EditText edt_idEstudiante;
    EditText edt_idEjercicio;
    EditText edt_TipoDeber;

    EditText edt_idGrupoEstudiantes;
    EditText edt_idActividades;

    Button btn_asignar;
    ProgressDialog progreso;

    ArrayList<Estudiante> listaEstudiantes;
    //ArrayList<EjercicioG1> listaEjercicios;
    ArrayList<EjercicioG2> listaEjercicios;

    ArrayList<Grupo_Estudiantes> listaGrupoEstudiantes;

    ArrayList<Asignacion> listaAsignaciones;
    ArrayList<AsignacionHasEjercicioG2> listaAsigHasEjerG2;
    ArrayList<Integer> listaIdEjerG2_Asi_H_E;
    ArrayList<GrupoHEstudiante> listaGrupoHEstudiantes;
    ArrayList<String> listaString_id_Estudiante_GrupoHE;

    ArrayList<GrupoEstudiantesHasDeber> listaGrupoEstHasDeber;
    ArrayList<Integer> lista_String_id_GrupoEstHasDeber;

    Spinner spinner;
    Spinner spinnerEjercicios;
    Spinner spinnerGrupoEstudiantes;
    Spinner spinnerTipoDeber;
    Spinner spinnerActividades;
    Integer idgrupo;

    Button btn_est;
    Button btn_doc;

    Button btn_eje;
    Button btn_act;

    LinearLayout ll_est;
    LinearLayout ll_doc;

    LinearLayout ll_eje;
    LinearLayout ll_act;

    Boolean flagEst = false;
    Boolean flagXGrupo = false;

    Boolean flagEje = false;
    Boolean flagAct = false;

   /* Boolean G1 = false;
    Boolean G2 = false;
    Boolean G3 = false;
    Boolean G4 = false;*/


    Boolean flagAsignarEjercicio = false;
    int flag = 0;
    int flag1 = 0;

    //******** CONEXIÓN CON WEBSERVICE
    //RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    StringRequest stringRequest;

    public int iddocente = 0;
    public int idactividad = 0;
    public int grupoestudiante = 0;
    public int IdEjercicioG2;
    public int Id_Estudiante = 0;
    String idejercicio;
    String idestudiante;
    String grupo_estudiante_has_deber;
    String estudiante_has_Asignacion;
    String id_grupo;
    String idEjercicio;
    String idAsignacion;

    Button btn_act_fon;
    Button btn_act_lex;
    Button btn_act_sil;

    int id_Actividad;
    TextView tittle;

    final List<String> listaStringEjercicios = new ArrayList<>();
    final List<String> listaStringAsignacion = new ArrayList<>();


    Calendar calendar;
    SimpleDateFormat simpleDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignar_estudiante_deber);

        Bundle datos = this.getIntent().getExtras();

        iddocente = datos.getInt("iddocente");
        idgrupo = datos.getInt("idgrupo");
        //Toast.makeText(getApplicationContext(), "id docente: " + iddocente, Toast.LENGTH_LONG).show();
        //toolbar.setLabelFor();
        this.setTitle("id docente:" + iddocente);

        listaIdEjerG2_Asi_H_E = new ArrayList<>();
        listaString_id_Estudiante_GrupoHE = new ArrayList<>();
        listaEjercicios = new ArrayList<>();
        listaAsignaciones = new ArrayList<>();

        progreso = new ProgressDialog(getApplicationContext());
        edt_idEstudiante = findViewById(R.id.edt_id_estudiate_deber);
        edt_idGrupoEstudiantes = findViewById(R.id.edt_id_grupo_estudiate_deber);
        edt_idEjercicio = findViewById(R.id.edt_id_ejercicio_deber);
        edt_TipoDeber = findViewById(R.id.edt_docente_lex_tipoDeber);
        edt_idActividades = findViewById(R.id.edt_id_ejercicio_actividad_deber);
        btn_asignar = findViewById(R.id.btn_asignarDeber_deber);

        spinner = (Spinner) findViewById(R.id.sp_docente_AED_estudiantes);
        spinnerGrupoEstudiantes = (Spinner) findViewById(R.id.sp_docente_AED_grupo_est);
        spinnerEjercicios = (Spinner) findViewById(R.id.sp_docente_AED_ejercicios);
        spinnerTipoDeber = (Spinner) findViewById(R.id.sp_docente_AED_tipoDeber);
        spinnerActividades = (Spinner) findViewById(R.id.sp_docente_AED_actividades);

        ll_est = (LinearLayout) findViewById(R.id.ll_docente_est);
        ll_doc = (LinearLayout) findViewById(R.id.ll_docente_doc);

        ll_eje = (LinearLayout) findViewById(R.id.ll_docente_X_ejer_sp);
        ll_act = (LinearLayout) findViewById(R.id.ll_docente_X_actividades_sp);

        tittle= (TextView) findViewById(R.id.txt_tittle_asig);

        btn_act_fon = (Button) findViewById(R.id.btn_asignar_choise_act_1);
        btn_act_fon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_Actividad=1;
                listaEjercicios.clear();
                listaStringEjercicios.clear();

                listaAsignaciones.clear();
                listaStringAsignacion.clear();
                listarEjercicios_Docente();
                listar_Actividades_Docente();
                tittle.setText("Ejercicio Fónico");
            }
        });
        btn_act_lex = (Button) findViewById(R.id.btn_asignar_choise_act_2);
        btn_act_lex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_Actividad=2;
                listaEjercicios.clear();
                listaStringEjercicios.clear();
                listaAsignaciones.clear();
                listaStringAsignacion.clear();
                listarEjercicios_Docente();
                listar_Actividades_Docente();
                tittle.setText("Ejercicio Léxico");
            }
        });
        btn_act_sil = (Button) findViewById(R.id.btn_asignar_choise_act_3);
        btn_act_sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_Actividad=3;
                listaEjercicios.clear();
                listaStringEjercicios.clear();
                listaAsignaciones.clear();
                listaStringAsignacion.clear();
                listarEjercicios_Docente();
                listar_Actividades_Docente();
                tittle.setText("Ejercicio Silábico");
            }
        });

        btn_est = (Button) findViewById(R.id.btn_docente_sp_est);
        btn_est.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagEst == false) {
                    ll_est.setVisibility(View.VISIBLE);
                    ll_doc.setVisibility(View.GONE);
                    flagEst = true;
                    flagXGrupo = false;
                } else if (flagEst == true) {
                    ll_est.setVisibility(View.GONE);
                    flagEst = false;
                }
            }
        });
        btn_doc = (Button) findViewById(R.id.btn_docente_sp_doc);
        btn_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagXGrupo == false) {
                    ll_doc.setVisibility(View.VISIBLE);
                    ll_est.setVisibility(View.GONE);
                    flagXGrupo = true;
                    flagEst = false;
                } else if (flagXGrupo == true) {
                    ll_doc.setVisibility(View.GONE);
                    flagXGrupo = false;
                }
            }
        });
        //*********

        btn_eje = (Button) findViewById(R.id.btn_docente_sp_X_ejercicio);
        btn_eje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagEje == false) {
                    ll_eje.setVisibility(View.VISIBLE);
                    ll_act.setVisibility(View.GONE);
                    flagEje = true;
                    flagAct = false;
                } else if (flagEje == true) {
                    ll_eje.setVisibility(View.GONE);
                    flagEje = false;
                }
            }
        });

        btn_act = (Button) findViewById(R.id.btn_docente_sp_X_actividades);
        btn_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagAct == false) {
                    ll_act.setVisibility(View.VISIBLE);
                    ll_eje.setVisibility(View.GONE);
                    flagAct = true;
                    flagEje = false;
                } else if (flagAct == true) {
                    ll_act.setVisibility(View.GONE);
                    flagAct = false;
                }
            }
        });

        final ArrayList<String> listaDeber = new ArrayList<>();
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
                if (flagEst && flagEje) {
                    flag=0;
                    flag1=0;
                    listaIdEjerG2_Asi_H_E.clear();
                    listaString_id_Estudiante_GrupoHE.clear();
                    listaIdEjerG2_Asi_H_E.clear();
                    cargarWebService();
                    //System.out.println("if 1 : ");
                    Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_LONG).show();
                } else if (flagEst && flagAct) {
                    flag=0;
                    flag1=0;
                    listaIdEjerG2_Asi_H_E.clear();
                    listaString_id_Estudiante_GrupoHE.clear();
                    listaIdEjerG2_Asi_H_E.clear();
                    idactividad = Integer.parseInt(edt_idActividades.getText().toString());
                    System.out.println("idactividad : " + idactividad);
                    listar_Ejercicios_de_Asignacion();

                    // asignar_deber_Ejercicios_de_asignacion();
                    Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_LONG).show();
                    //cargarEstudiante_X_Actividad(idactividad);
                } else if (flagXGrupo && flagEje) {
                    //idactividad = Integer.parseInt(edt_idActividades.getText().toString());
                    //System.out.println("idactividad : " + idactividad);
                    flag=0;
                    flag1=0;
                    listaIdEjerG2_Asi_H_E.clear();
                    listaString_id_Estudiante_GrupoHE.clear();
                    listaIdEjerG2_Asi_H_E.clear();

                    grupoestudiante = Integer.parseInt(edt_idGrupoEstudiantes.getText().toString());
                    crearGrupoEstudiantesHasDeber();
                    //cargarEstudiante_X_Actividad(idactividad);
                    Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_LONG).show();
                } else if (flagXGrupo && flagAct) {
                    //idactividad = Integer.parseInt(edt_idActividades.getText().toString());
                    //System.out.println("idactividad : " + idactividad);
                    flag=0;
                    flag1=0;
                    listaIdEjerG2_Asi_H_E.clear();
                    listaString_id_Estudiante_GrupoHE.clear();
                    listaIdEjerG2_Asi_H_E.clear();

                    grupoestudiante = Integer.parseInt(edt_idGrupoEstudiantes.getText().toString());

                    idactividad = Integer.parseInt(edt_idActividades.getText().toString());
                    listar_Ejercicios_de_Asignacion();
                    crearGrupoEstudiantesHasDeber();

                    Toast.makeText(getApplicationContext(), "4", Toast.LENGTH_LONG).show();
                    //cargarEstudiante_X_Actividad(idactividad);
                }

            }
        });

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("fecha actual: " + simpleDateFormat.format(calendar.getTime()));
       // listarEjercicios_Docente();
        listarEstudiantes();
        listarGrupo_Estudiantes_Docente();
        //listar_Actividades_Docente();
        //cargarEjercicios();
        showToolbar("Mis Asignaciones" + " - " + iddocente, true);
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
    //**********************************************************************************************

    private void asignar_deber_Ejercicios_de_asignacion() {

        if (flag < listaIdEjerG2_Asi_H_E.size() && flagAsignarEjercicio == true) {
            System.out.println("flag met: " + flag);
            IdEjercicioG2 = listaIdEjerG2_Asi_H_E.get(flag);
            System.out.println("IdEjercicioG2: " + IdEjercicioG2);
            cargarWebService();
        } else if (flag == listaIdEjerG2_Asi_H_E.size()) {
            listaIdEjerG2_Asi_H_E.clear();
        }

      /*  for (int i=0;i<listaIdEjerG2_Asi_H_E.size();i++){
            IdEjercicioG2 = listaIdEjerG2_Asi_H_E.get(i);
            System.out.println("IdEjercicioG2: " + IdEjercicioG2);
            cargarWebService();
        }*/
    }

    //***********************************
    private void asignar_deber_Estudiante_Grupo() {
        if (flagXGrupo && flagEje) {
            if (flag < listaString_id_Estudiante_GrupoHE.size()) {
                System.out.println("flag asignar a grupo: " + flag);
                Id_Estudiante = Integer.parseInt(listaString_id_Estudiante_GrupoHE.get(flag));
                System.out.println("IdEjercicioG2 x Grupo: " + IdEjercicioG2);
                cargarWebService();
            } else if (flag == listaString_id_Estudiante_GrupoHE.size()) {
                listaString_id_Estudiante_GrupoHE.clear();
            }

        } else if (flagXGrupo && flagAct) {
            if (flag < listaString_id_Estudiante_GrupoHE.size()) {
                if (flag1 < listaIdEjerG2_Asi_H_E.size()) {
                    int flagTemp = flag;
                    Id_Estudiante = Integer.parseInt(listaString_id_Estudiante_GrupoHE.get(flag));

                    System.out.println("IdEstudiante met: " + Id_Estudiante);
                    IdEjercicioG2 = listaIdEjerG2_Asi_H_E.get(flag1);

                    System.out.println("IdEjercicioG2 met: " + IdEjercicioG2);
                    cargarWebService();
                }
                //System.out.println("flag asignar a grupo: " + flag);


            } else if (flag == listaString_id_Estudiante_GrupoHE.size() && flag1 == listaIdEjerG2_Asi_H_E.size()) {
                listaString_id_Estudiante_GrupoHE.clear();
                listaIdEjerG2_Asi_H_E.clear();
            }

        }
    }

    /*private void asignar_deber_Estudiante_Grupo() {
        if (flag < listaString_id_Estudiante_GrupoHE.size()) {
            System.out.println("flag asignar a grupo: " + flag);
            Id_Estudiante = Integer.parseInt(listaString_id_Estudiante_GrupoHE.get(flag));
            System.out.println("IdEjercicioG2 x Grupo: " + IdEjercicioG2);
            cargarWebService();
        } else if (flag == listaString_id_Estudiante_GrupoHE.size()) {
            listaString_id_Estudiante_GrupoHE.clear();
        } else if (flagXGrupo && flagAct) {

        }
    }*/

    //**********************************************************************************************
    private void asignar_deber_Estudiante_Grupo_H_Deber() {
        Id_Estudiante = 0;
        if (flag < listaString_id_Estudiante_GrupoHE.size()) {
            System.out.println("flag asignar a grupo: " + flag);
            Id_Estudiante = Integer.parseInt(listaString_id_Estudiante_GrupoHE.get(flag));
            System.out.println("IdEjercicioG2 x Grupo: " + IdEjercicioG2);
            //cargarWebService();
        } else if (flag == listaString_id_Estudiante_GrupoHE.size()) {
            listaString_id_Estudiante_GrupoHE.clear();
        }
    }

    //**********************************************************************************************

    private void cargarEstudiante_X_Actividad(int idActividad) {
        // for (int i=0;i<listaAsignaciones.size();i++){}
        if (flag < listaAsignaciones.size()) {
            //System.out.println("flag2 met: " + flag2);
            IdEjercicioG2 = listaIdEjerG2_Asi_H_E.get(flag);
            cargarWebService();
        }
    }

    //***********************************
    public void listarEstudiantes() {
        // final String usuario = etUsuario.getText().toString();
        // final String password = etPass.getText().toString();

        // INICIAR LA CONEXION CON VOLLEY

        String url_lh = Globals.url;

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
                                estudiante.setNameestudiante(jsonObject.optString("nameEstudiante"));

                                listaEstudiantes.add(estudiante);
                            }

                            final List<String> listaStringEstudiantes = new ArrayList<>();
                            listaStringEstudiantes.add("Seleccione Id Estudiante");
                            for (int i = 0; i < listaEstudiantes.size(); i++) {
                                listaStringEstudiantes.add(listaEstudiantes.get(i).getIdestudiante().toString() + " - " + listaEstudiantes.get(i).getNameestudiante());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, listaStringEstudiantes);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(adapter);
                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position != 0) {
                                        // edt_idEstudiante.setText(listaStringEstudiantes.get(position));
                                        edt_idEstudiante.setText(listaEstudiantes.get(position - 1).getIdestudiante().toString());
                                        edt_idGrupoEstudiantes.setText("");
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

    //***********************************
    public void listarGrupo_Estudiantes_Docente() {


        String url_lh = Globals.url;

        String url = "http://" + url_lh +
                "/proyecto_dconfo_v1/12wsJSONConsultar_Lista_Grupo_Est.php?idgrupo="+idgrupo+"&iddocente="+iddocente;

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
                            for (int i = 0; i < listaGrupoEstudiantes.size(); i++) {
                                listaStringGrupo_Estudiantes.add(listaGrupoEstudiantes.get(i).getIdGrupoEstudiantes().toString() + " - " + listaGrupoEstudiantes.get(i).getNameGrupoEstudiantes());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, listaStringGrupo_Estudiantes);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerGrupoEstudiantes.setAdapter(adapter);
                            spinnerGrupoEstudiantes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position != 0) {
                                        edt_idGrupoEstudiantes.setText(listaGrupoEstudiantes.get(position - 1).getIdGrupoEstudiantes().toString());
                                        edt_idEstudiante.setText("");
                                    } else {

                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            // Toast.makeText(getApplicationContext(), "lista Grupo estudiantes" + listaStringGrupo_Estudiantes, Toast.LENGTH_LONG).show();
                            System.out.println("listaGrupoEstudiantes size: " + listaGrupoEstudiantes.size());
                            System.out.println("listaGrupoEstudiantes: " + listaGrupoEstudiantes.get(0).getIdGrupoEstudiantes());

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

    //***********************************
    public void listar_Actividades_Docente() {


        String url_lh = Globals.url;
        int idactividad = 3;

        String url = "http://" + url_lh +
                "/proyecto_dconfo_v1/13wsJSONConsultar_Lista_Asignaciones.php?idgrupo="+idgrupo+"&iddocente="+iddocente+"&idactividad="+id_Actividad;

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


                            listaStringAsignacion.add("Seleccione Id Actividad");
                            for (int i = 0; i < listaAsignaciones.size(); i++) {
                                listaStringAsignacion.add(listaAsignaciones.get(i).getIdGrupoAsignacion().toString() + " - " + listaAsignaciones.get(i).getNameGrupoAsignacion());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, listaStringAsignacion);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerActividades.setAdapter(adapter);
                            spinnerActividades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position != 0) {
                                        edt_idActividades.setText(listaAsignaciones.get(position - 1).getIdGrupoAsignacion().toString());
                                        edt_idEjercicio.setText("");
                                    } else {

                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        /*    Toast.makeText(getApplicationContext(), "lista Grupo estudiantes" + listaStringGrupo_Estudiantes, Toast.LENGTH_LONG).show();
                            System.out.println("listaGrupoEstudiantes size: " + listaGrupoEstudiantes.size());
                            System.out.println("listaGrupoEstudiantes: " + listaGrupoEstudiantes.get(0).getIdGrupoEstudiantes());*/

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
                        Log.d("ERROR ACTIVIDADES: ", error.toString());
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
        //stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);//p21
    }
    //***********************************

    //***********************************
    public void listar_Ejercicios_de_Asignacion() {
        String url_lh = Globals.url;
        String url = "http://" + url_lh + "/proyecto_dconfo_v1/14wsJSONConsultar_Lista_idEjer_Act.php?idactividad="+idactividad;

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

                        listaAsigHasEjerG2 = new ArrayList<>();

                        AsignacionHasEjercicioG2 asignacionHasEjercicioG2 = null;


                        try {
                            JSONArray json = response.optJSONArray("asignacion_has_ejerciciog2");
                            for (int i = 0; i < json.length(); i++) {
                                asignacionHasEjercicioG2 = new AsignacionHasEjercicioG2();
                                JSONObject jsonObject = null;
                                jsonObject = json.getJSONObject(i);
                                asignacionHasEjercicioG2.setAsignacion_idGrupoAsignacion(jsonObject.optInt("Asignacion_idGrupoAsignacion"));
                                asignacionHasEjercicioG2.setEjercicioG2_idEjercicioG2(jsonObject.optInt("EjercicioG2_idEjercicioG2"));

                                listaAsigHasEjerG2.add(asignacionHasEjercicioG2);
                            }

                            final List<String> listaString_id_Ejercicios_Asi = new ArrayList<>();
                            // listaString_id_Ejercicios_Asi.add("Seleccione Id Ejercicio");
                            for (int i = 0; i < listaAsigHasEjerG2.size(); i++) {
                                listaIdEjerG2_Asi_H_E.add(listaAsigHasEjerG2.get(i).getEjercicioG2_idEjercicioG2());
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        System.out.println("lista ejercicios: " + listaIdEjerG2_Asi_H_E.toString());

                        flag = 0;
                        flagAsignarEjercicio = true;

                        if (!flagXGrupo && flagAct) {
                            asignar_deber_Ejercicios_de_asignacion();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        System.out.println("ERROR Ejercicios Asig: " + error.toString());
                        Log.d("ERROR Ejercicios Asig: ", error.toString());
                    }
                }
        );
        final int MY_DEFAULT_TIMEOUT = 15000;
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonObjectRequest);

    }
    //***********************************

    //***********************************
    public void listar_Estudiantes_de_GrupoEst() {
        String url_lh = Globals.url;
        String url = "http://" + url_lh + "/proyecto_dconfo_v1/15wsJSONConsultar_Lista_idEst_Grupo.php?idgrupoest="+grupoestudiante;

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

                        listaGrupoHEstudiantes = new ArrayList<>();

                        GrupoHEstudiante grupoHEstudiante = null;


                        try {
                            JSONArray json = response.optJSONArray("grupo_estudiante_has_estudiante");
                            for (int i = 0; i < json.length(); i++) {
                                grupoHEstudiante = new GrupoHEstudiante();
                                JSONObject jsonObject = null;
                                jsonObject = json.getJSONObject(i);
                                grupoHEstudiante.setIdgrupoestudiante(jsonObject.optInt("grupo_estudiante_idgrupo_estudiante"));
                                grupoHEstudiante.setIdestudiante(jsonObject.optInt("estudiante_idestudiante"));

                                listaGrupoHEstudiantes.add(grupoHEstudiante);
                            }


                            // listaString_id_Ejercicios_Asi.add("Seleccione Id Ejercicio");
                            for (int i = 0; i < listaGrupoHEstudiantes.size(); i++) {
                                listaString_id_Estudiante_GrupoHE.add(listaGrupoHEstudiantes.get(i).getIdestudiante().toString());
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        System.out.println("lista estudiantes: " + listaString_id_Estudiante_GrupoHE.toString());
                        flag = 0;
                        asignar_deber_Estudiante_Grupo();

                       /* flag = 0;
                        flagAsignarEjercicio = true;
                        asignar_deber_Ejercicios_de_asignacion();*/

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        System.out.println("ERROR Ejercicios Asig: " + error.toString());
                        Log.d("ERROR Ejercicios Asig: ", error.toString());
                    }
                }
        );
        final int MY_DEFAULT_TIMEOUT = 15000;
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonObjectRequest);

    }


    //***********************************


    public void listarEjercicios_Docente() {
        // final String usuario = etUsuario.getText().toString();
        // final String password = etPass.getText().toString();

        // INICIAR LA CONEXION CON VOLLEY

        String url_lh = Globals.url;

        String url = "http://" + url_lh + "/proyecto_dconfo_v1/3wsJSONConsultarListaEjerciciosDocente.php?iddocente="+iddocente+"&idactividad="+id_Actividad;
        //String url = "http://" + url_lh + "/proyecto_dconfo/wsJSON1ConsultarListaEjercicios.php";


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
                                        edt_idActividades.setText("");
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
    private void cargarEjercicios() {

        String url_lh = Globals.url;

        //String url = "http://" + url_lh + "/proyecto_dconfo/wsJSONConsultarListaEjerciciosDocente.php?docente_iddocente=" + iddocente;
        //String url = "http://" + url_lh + "/proyecto_dconfo/wsJSON_Con_Ejerc_Doc.php?docente_iddocente=" + iddocente;
        String url = "http://" + url_lh + "/proyecto_dconfo_v1/wsJSON1ConsultarListaEjercicios.php";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //Toast.makeText(getContext(), "Mensaje: " + response.toString(), Toast.LENGTH_SHORT).show();
                EjercicioG1 ejercicioG1 = new EjercicioG1();
                JSONArray json = response.optJSONArray("ejerciciog1");
                JSONObject jsonObject = null;
                try {
                    //objeto como tal en posicion 0
                    jsonObject = json.getJSONObject(0);
                    ejercicioG1.setIdEjercicio(jsonObject.optInt("idEjercicioG1"));
                    System.out.println("id ejercicio " + ejercicioG1.getIdEjercicio());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                edt_idEjercicio.setText(ejercicioG1.getIdEjercicio().toString());


                /*
                if (miUsuario.getImagen()!=null) {
                    campoImagen.setImageBitmap(miUsuario.getImagen());
                }else {
                    campoImagen.setImageResource(R.drawable.imagen_no_disponible);
                }*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getContext(), "No se puede conectar" + error.toString(), Toast.LENGTH_LONG).show();
                System.out.println();
                progreso.hide();
                Log.d("ERROR CE", error.toString());
            }
        });
        //request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);//p21
    }
    //***********************************

    //**********************************************************************************************

    private void crearGrupoEstudiantesHasDeber() {

        String url_lh = Globals.url;
        String url = "http://" + url_lh + "/proyecto_dconfo_v1/16wsJSONRegistro_GrupoEst_H_Deber.php";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//recibe respuesta del webservice,cuando esta correcto
                // progreso.hide();
                if (response.trim().equalsIgnoreCase("registra")) {
                    //listar_Estudiantes_de_GrupoEst();
                    listar_Id_GrupoHasDeber();

                    Toast.makeText(getApplicationContext(), "Se ha cargado con éxito GEHD", Toast.LENGTH_LONG).show();
                } else {
                    // Toast.makeText(getContext(), "No se ha cargado con éxito", Toast.LENGTH_LONG).show();
                    Log.i("ERROR gehd: ", "RESPONSE" + response.toString());
                    System.out.println("idgrupoestudiante : " + edt_idGrupoEstudiantes.toString());
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
                if (flagXGrupo && flagEje) {
                    idEjercicio = edt_idEjercicio.getText().toString();
                    System.out.println("flagXGrupo && flagEje : " + idEjercicio);
                    idAsignacion = String.valueOf(0);
                    System.out.println("flagXGrupo && flagEje idAsignacion: " + idAsignacion);

                } else if (flagXGrupo && flagAct) {
                    idEjercicio = String.valueOf(0);
                    System.out.println("flagXGrupo && flagEje=0 : " + idEjercicio);
                    idAsignacion = edt_idActividades.getText().toString();
                    System.out.println("flagXGrupo && flagEje=0 idAsignacion: " + idAsignacion);
                }

                String idgrupoestudiante = edt_idGrupoEstudiantes.getText().toString();
                System.out.println("idgrupoestudiante : " + idgrupoestudiante);
                String fecha_gehd = simpleDateFormat.format(calendar.getTime());
                String tipo_gehd = edt_TipoDeber.getText().toString();
                String calificacion = "";

                Map<String, String> parametros = new HashMap<>();
                parametros.put("idgrupoestudiante", idgrupoestudiante);
                parametros.put("idEjercicio", idEjercicio);
                parametros.put("idAsignacion", idAsignacion);
                parametros.put("fecha_gehd", fecha_gehd);
                parametros.put("tipo_gehd", tipo_gehd);
                //parametros.put("calificacionestudiante_has_Deber", calificacion);
                return parametros;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);//p21
    }//crearGrupoEstudiantesHasDeber

    //**********************************************************************************************
    public void listar_Id_GrupoHasDeber() {
        // final String usuario = etUsuario.getText().toString();
        // final String password = etPass.getText().toString();

        // INICIAR LA CONEXION CON VOLLEY

        String url_lh = Globals.url;

        String url = "http://" + url_lh + "/proyecto_dconfo_v1/17wsJSONConsultar_Lista_Grupo_H_Deber.php?iddocente="+iddocente;
        //String url = "http://" + url_lh + "/proyecto_dconfo/wsJSON1ConsultarListaEjercicios.php";


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

                        listaGrupoEstHasDeber = new ArrayList<>();

                        GrupoEstudiantesHasDeber grupoEstudiantesHasDeber = null;


                        try {
                            JSONArray json = response.optJSONArray("id_grupoHest");
                            for (int i = 0; i < json.length(); i++) {
                                grupoEstudiantesHasDeber = new GrupoEstudiantesHasDeber();
                                JSONObject jsonObject = null;
                                jsonObject = json.getJSONObject(i);
                                grupoEstudiantesHasDeber.setId_GE_H_D(jsonObject.optInt("id_GE_H_D"));
                                grupoEstudiantesHasDeber.setGrupo_estudiante_idgrupo_estudiante(jsonObject.optInt("grupo_estudiante_idgrupo_estudiante"));
                                grupoEstudiantesHasDeber.setEjercicioG2_idEjercicioG2(jsonObject.optInt("EjercicioG2_idEjercicioG2"));
                                grupoEstudiantesHasDeber.setAsignacion_idGrupoAsignacion(jsonObject.optInt("Asignacion_idGrupoAsignacion"));
                                grupoEstudiantesHasDeber.setFecha_gehd(jsonObject.optString("fecha_gehd"));
                                grupoEstudiantesHasDeber.setTipo_gehd(jsonObject.optString("tipo_gehd"));

                                listaGrupoEstHasDeber.add(grupoEstudiantesHasDeber);
                            }

                            /* Toast.makeText(getApplicationContext(), "lista estudiantes" + listaStringEstudiantes, Toast.LENGTH_LONG).show();
                            System.out.println("estudiantes size: " + listaEstudiantes.size());
                            System.out.println("estudiantes: " + listaEstudiantes.get(0).getIdestudiante());*/

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        lista_String_id_GrupoEstHasDeber = new ArrayList<>();
                        //listaStringEjercicios.add("Seleccione Id Ejercicio");
                        for (int i = 0; i < listaGrupoEstHasDeber.size(); i++) {
                            lista_String_id_GrupoEstHasDeber.add(listaGrupoEstHasDeber.get(i).getId_GE_H_D());
                        }


                        if (lista_String_id_GrupoEstHasDeber.size() == listaGrupoEstHasDeber.size()) {
                            listar_Estudiantes_de_GrupoEst();
                            System.out.println("lista id GEHD: " + lista_String_id_GrupoEstHasDeber.toString());
                            System.out.println("lista  GEHD: " + listaGrupoEstHasDeber.toString());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        System.out.println();
                        Log.d("ERROR lista id GEHD: ", error.toString());
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


    private void cargarWebService() {

        if ((flagXGrupo && flagAct)!=true) {
            flag++;
        }
        flag1++;

        System.out.println("flag ws : " + flag);
        System.out.println("flag1 ws : " + flag1);

        String url_lh = Globals.url;
        String url =
                //"http://192.168.0.13/proyecto_dconfo/wsJSONCrearCurso.php?";
                "http://" + url_lh + "/proyecto_dconfo_v1/5wsJSONAsignarDeberEstudiante.php";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//recibe respuesta del webservice,cuando esta correcto
                // progreso.hide();
                if (response.trim().equalsIgnoreCase("registra")) {
                  /*  edt_idEstudiante.setText("");
                    edt_idEjercicio.setText("");
                    edt_TipoDeber.setText("");*/

                    if (flagEst && flagEje) {
                        Toast.makeText(getApplicationContext(), "Se ha cargado con éxito G1", Toast.LENGTH_LONG).show();
                    } else if (flagEst && flagAct) {

                        asignar_deber_Ejercicios_de_asignacion();
                        Toast.makeText(getApplicationContext(), "Se ha cargado con éxito G2", Toast.LENGTH_LONG).show();

                    } else if (flagXGrupo && flagEje) {
                        asignar_deber_Estudiante_Grupo();
                        Toast.makeText(getApplicationContext(), "Se ha cargado con éxito G3", Toast.LENGTH_LONG).show();

                    } else if (flagXGrupo && flagAct) {

                        if (flag1 % (listaIdEjerG2_Asi_H_E.size()) == 0) {
                            flag++;
                            flag1=0;
                            System.out.println("mod: "+true);
                        }
                        //flag1++;
                        asignar_deber_Estudiante_Grupo();
                        Toast.makeText(getApplicationContext(), "Se ha cargado con éxito G3", Toast.LENGTH_LONG).show();
                    }
                    //asignar_deber_Ejercicios_de_asignacion();
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

                if (flagEst && flagEje) {

                    idestudiante = edt_idEstudiante.getText().toString();
                    idejercicio = edt_idEjercicio.getText().toString();
                    grupo_estudiante_has_deber = String.valueOf(0);
                    estudiante_has_Asignacion = String.valueOf(0);
                    // Toast.makeText(getApplicationContext(), "1" , Toast.LENGTH_LONG).show();

                } else if (flagEst && flagAct) {

                    idestudiante = edt_idEstudiante.getText().toString();
                    idejercicio = String.valueOf(IdEjercicioG2);
                    grupo_estudiante_has_deber = String.valueOf(0);
                    estudiante_has_Asignacion = String.valueOf(idactividad);

                } else if (flagXGrupo && flagEje) {
                    idestudiante = String.valueOf(Id_Estudiante);
                    grupo_estudiante_has_deber = lista_String_id_GrupoEstHasDeber.get((lista_String_id_GrupoEstHasDeber.size() - 1)).toString();
                    idejercicio = edt_idEjercicio.getText().toString();
                    estudiante_has_Asignacion = String.valueOf(0);
                    //grupo_estudiante_has_deber = String.valueOf(0);
                    //estudiante_has_Asignacion = String.valueOf(0);
                } else if (flagXGrupo && flagAct) {
                    idestudiante = String.valueOf(Id_Estudiante);
                    idejercicio = String.valueOf(IdEjercicioG2);
                    grupo_estudiante_has_deber = lista_String_id_GrupoEstHasDeber.get((lista_String_id_GrupoEstHasDeber.size() - 1)).toString();
                    System.out.println("grupo_estudiante_has_deber CWS : " + grupo_estudiante_has_deber);
                    estudiante_has_Asignacion = String.valueOf(idactividad);
                    //cargarEstudiante_X_Actividad(idactividad);
                }

                String iddocente1 = String.valueOf(iddocente);
                id_grupo = String.valueOf(idgrupo);
                String fecha = simpleDateFormat.format(calendar.getTime());
                String tipoDeber = edt_TipoDeber.getText().toString();
                String calificacion = "null";


                Map<String, String> parametros = new HashMap<>();
                parametros.put("estudiante_idestudiante", idestudiante);
                parametros.put("docente_iddocente", iddocente1);
                parametros.put("EjercicioG2_idEjercicioG2", idejercicio);
                parametros.put("grupo_estudiante_has_deber", grupo_estudiante_has_deber);
                parametros.put("estudiante_has_Asignacion", estudiante_has_Asignacion);
                parametros.put("idgrupo", id_grupo);
                parametros.put("fechaestudiante_has_Deber", fecha);
                parametros.put("tipoDeber", tipoDeber);
                parametros.put("calificacionestudiante_has_Deber", calificacion);

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


}
