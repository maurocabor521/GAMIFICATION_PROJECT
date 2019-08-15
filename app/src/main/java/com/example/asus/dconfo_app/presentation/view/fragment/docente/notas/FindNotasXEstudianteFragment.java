package com.example.asus.dconfo_app.presentation.view.fragment.docente.notas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.example.asus.dconfo_app.domain.model.DeberEstudiante;
import com.example.asus.dconfo_app.domain.model.EjercicioG2;
import com.example.asus.dconfo_app.domain.model.Estudiante;
import com.example.asus.dconfo_app.domain.model.VolleySingleton;
import com.example.asus.dconfo_app.helpers.Globals;
import com.example.asus.dconfo_app.presentation.view.activity.docente.notas.PorcentNotasActivity;
import com.example.asus.dconfo_app.presentation.view.adapter.NotasDeberesEstudianteAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FindNotasXEstudianteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FindNotasXEstudianteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FindNotasXEstudianteFragment extends Fragment implements Response.Listener<JSONObject>,
        Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    int idgrupo;
    int iddocente;
    ProgressDialog progreso;

    Spinner sp_Estudiantes;
    EditText edt_Estudiante;
    Button btn_BuscarEstudiante;
    Button btn_BuscarAct;
    Button btn_BuscarTodas;
    Button btn_BuscarGrupo;
    RecyclerView rv_datosEstudiante;
    private int idestudiante;
    private String flag;

    ArrayList<String> listaStringIdGrupoEstudiantes;
    ArrayList<Estudiante> listaEstudiantes;
    ArrayList<Integer> listaIdEstudiantes;
    List<String> listaStringEstudiantes = new ArrayList<>();
    ArrayList<DeberEstudiante> listaDeberes_full;
    ArrayList<Integer> lista_idEstudiante;

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
    String tipoEst;
    String actividad;


    //RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    StringRequest stringRequest;


    private OnFragmentInteractionListener mListener;

    public FindNotasXEstudianteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FindNotasXEstudianteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FindNotasXEstudianteFragment newInstance(String param1, String param2) {
        FindNotasXEstudianteFragment fragment = new FindNotasXEstudianteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find_notas_xestudiante, container, false);

        progreso = new ProgressDialog(getActivity());
        iddocente = getArguments().getInt("iddocente");
        idgrupo = getArguments().getInt("idgrupo");

        sp_Estudiantes = (Spinner) view.findViewById(R.id.sp_docente_estudiantes_nota);
        edt_Estudiante = (EditText) view.findViewById(R.id.edt_doc_estudiante_nota);

        rv_datosEstudiante = (RecyclerView) view.findViewById(R.id.rv_docente_X_Est_notas);
        rv_datosEstudiante.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_datosEstudiante.setHasFixedSize(true);

        listaDeberes_full = new ArrayList<>();
        lista_idEstudiante = new ArrayList<>();
        listaIdEstudiantes = new ArrayList<>();
        listaEjerciciosG2 = new ArrayList<>();

        lista_idEjercicios = new ArrayList<>();
        lista_calificaciones = new ArrayList<>();
        listaActividades = new ArrayList<>();

        listanotaFonico = new ArrayList<>();
        listanotaLexico = new ArrayList<>();
        listanotaSilabico = new ArrayList<>();
        lista_String_Actividades = new ArrayList<>();

        listarEstudiantes();

        btn_BuscarEstudiante = (Button) view.findViewById(R.id.btn_docente_buscar_nota);
        btn_BuscarEstudiante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("fonico", NDF);
                bundle.putInt("lexico", NDL);
                bundle.putInt("silabico", NDS);
                bundle.putInt("idgrupo", idgrupo);
                bundle.putString("nameEst", nameEst);
                bundle.putString("tipoEst", tipoEst);
                System.out.println("nameEst_: " + nameEst);
                Intent intent = new Intent(getActivity(), PorcentNotasActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                btn_BuscarEstudiante.setVisibility(View.GONE);
            }
        });

        btn_BuscarTodas = (Button) view.findViewById(R.id.btn_docente_buscar_todas);
        btn_BuscarTodas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoEst = "est";
                listaDeberes_full = new ArrayList<>();
                lista_idEstudiante = new ArrayList<>();
                listaIdEstudiantes = new ArrayList<>();
                listaDeberes_full = new ArrayList<>();
                lista_idEstudiante = new ArrayList<>();
                listaIdEstudiantes = new ArrayList<>();
                listaEjerciciosG2 = new ArrayList<>();

                lista_idEjercicios = new ArrayList<>();
                lista_calificaciones = new ArrayList<>();
                listaActividades = new ArrayList<>();

                listanotaFonico = new ArrayList<>();
                listanotaLexico = new ArrayList<>();
                listanotaSilabico = new ArrayList<>();
                lista_String_Actividades = new ArrayList<>();

                bandera = 0;
                NDF = 0;
                NDL = 0;
                NDS = 0;
                SUMNDF = 0;
                SUMNDL = 0;
                SUMNDS = 0;

                flag = "1";
                cargarWebService();
            }
        });
        btn_BuscarAct = (Button) view.findViewById(R.id.btn_docente_buscar_act);
        btn_BuscarAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoEst = "";
                listaDeberes_full = new ArrayList<>();
                lista_idEstudiante = new ArrayList<>();
                listaIdEstudiantes = new ArrayList<>();
                listaDeberes_full = new ArrayList<>();
                lista_idEstudiante = new ArrayList<>();
                listaIdEstudiantes = new ArrayList<>();
                listaEjerciciosG2 = new ArrayList<>();

                lista_idEjercicios = new ArrayList<>();
                lista_calificaciones = new ArrayList<>();
                listaActividades = new ArrayList<>();

                listanotaFonico = new ArrayList<>();
                listanotaLexico = new ArrayList<>();
                listanotaSilabico = new ArrayList<>();
                lista_String_Actividades = new ArrayList<>();

                bandera = 0;
                NDF = 0;
                NDL = 0;
                NDS = 0;
                SUMNDF = 0;
                SUMNDL = 0;
                SUMNDS = 0;

                flag = "3";
                actividad = "a";
                cargarWebService();
            }
        });

        btn_BuscarGrupo = (Button) view.findViewById(R.id.btn_docente_buscar_grupos);
        btn_BuscarGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoEst = "";
                listaDeberes_full = new ArrayList<>();
                lista_idEstudiante = new ArrayList<>();
                listaIdEstudiantes = new ArrayList<>();
                listaDeberes_full = new ArrayList<>();
                lista_idEstudiante = new ArrayList<>();
                listaIdEstudiantes = new ArrayList<>();
                listaEjerciciosG2 = new ArrayList<>();

                lista_idEjercicios = new ArrayList<>();
                lista_calificaciones = new ArrayList<>();
                listaActividades = new ArrayList<>();

                listanotaFonico = new ArrayList<>();
                listanotaLexico = new ArrayList<>();
                listanotaSilabico = new ArrayList<>();
                lista_String_Actividades = new ArrayList<>();

                bandera = 0;
                NDF = 0;
                NDL = 0;
                NDS = 0;
                SUMNDF = 0;
                SUMNDL = 0;
                SUMNDS = 0;

                flag = "3";
                actividad = "g";
                cargarWebService();
            }
        });

        return view;
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
            VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);//p21
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
            VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);//p21
            //Toast.makeText(getApplicationContext(), "web service 1111", Toast.LENGTH_LONG).show();}
        }//flag="2"
        else if (flag.equals("3")) {

            String url = "http://" + url_lh + "/proyecto_dconfo_v1/8_5_3wsJSONConsultarListaDeberesEst_nota_Act.php?estudiante_idestudiante=" + idestudiante + "&actividad=" + actividad;

            url = url.replace(" ", "%20");
            //hace el llamado a la url
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);

            final int MY_DEFAULT_TIMEOUT = 15000;
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    MY_DEFAULT_TIMEOUT,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            // request.add(jsonObjectRequest);
            VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);//p21
            //Toast.makeText(getApplicationContext(), "web service 1111", Toast.LENGTH_LONG).show();}
        }//flag="3"


    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getContext(), "No se puede cone , grupo doc" + error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("ERROR", error.toString());
        progreso.hide();
    }

    // si esta bien el llamado a la url entonces entra a este metodo
    @Override
    public void onResponse(JSONObject response) {
        progreso.hide();
        if (flag.equals("1") || flag.equals("3")) {
            //Toast.makeText(getApplicationContext(), "Mensaje: " + response.toString(), Toast.LENGTH_SHORT).show();
            DeberEstudiante deberEstudiante = null;
            JSONArray json = response.optJSONArray("deber_nota1");


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
                rv_datosEstudiante.setAdapter(notasDeberesEstudianteAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("error", response.toString());

                Toast.makeText(getContext(), "No se ha podido establecer conexión: " + response.toString(), Toast.LENGTH_LONG).show();

                progreso.hide();
            }

            getEjercicioAct();

            for (int i = 0; i < lista_idEjercicios.size(); i++) {
                System.out.println("Lista id ejercicios: " + lista_idEjercicios.get(i));
            }
            for (int i = 0; i < lista_calificaciones.size(); i++) {
                System.out.println("lista_calificaciones: " + lista_calificaciones.get(i));
            }
            //System.out.println("Lista id estudiante: " + lista_idEstudiante.toString());
        } else
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

                    Toast.makeText(getContext(), "No se ha podido establecer conexión: " + response.toString(), Toast.LENGTH_LONG).show();

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

    //********************************************

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


        btn_BuscarEstudiante.setVisibility(View.VISIBLE);
        System.out.println("NDF: " + NDF);
        System.out.println("NDL: " + NDL);
        System.out.println("NDS: " + NDS);
    }


    //***********************************
    public void listarEstudiantes() {

        String url_lh = Globals.url;

        String url = "http://" + url_lh + "/proyecto_dconfo_v1/4wsJSONConsultarListaEstudiantesGrupoDocente.php?idgrupo=" + idgrupo;

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

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
                            // listaStringEstudiantes.add("Seleccione Id Estudiante");
                            for (int i = 0; i < listaEstudiantes.size(); i++) {
                                listaStringEstudiantes.add(listaEstudiantes.get(i).getIdestudiante().toString() + " - " + listaEstudiantes.get(i).getNameestudiante());
                            }

                            listaIdEstudiantes.add(0000);

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listaStringEstudiantes);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sp_Estudiantes.setAdapter(adapter);
                            sp_Estudiantes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position != -1) {
                                        //listaIdEstudiantes.add(listaEstudiantes.get(position - 1).getIdestudiante());
                                        listaIdEstudiantes.add(listaEstudiantes.get(position).getIdestudiante());
                                        edt_Estudiante.setText(listaEstudiantes.get(position).getIdestudiante().toString());
                                        nameEst = listaEstudiantes.get(position).getNameestudiante();
                                        idestudiante = Integer.parseInt(edt_Estudiante.getText().toString());
                                        //System.out.println("lista id est: " + listaIdEstudiantes.toString());
//                                        Toast.makeText(getApplicationContext(), "id est: " + listaIdEstudiantes.get(position), Toast.LENGTH_LONG).show();
                                        //showListView();
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


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
