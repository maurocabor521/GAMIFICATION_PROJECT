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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.example.asus.dconfo_app.domain.model.GrupoEstudiantesHasDeber;
import com.example.asus.dconfo_app.domain.model.Grupo_Estudiantes;
import com.example.asus.dconfo_app.domain.model.VolleySingleton;
import com.example.asus.dconfo_app.helpers.Globals;
import com.example.asus.dconfo_app.presentation.view.activity.docente.notas.PorcentNotasActivity;
import com.example.asus.dconfo_app.presentation.view.adapter.Grupos_Estudiante_DocenteAdapter;
import com.example.asus.dconfo_app.presentation.view.adapter.NotasDeberesEstudianteAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FindNotasXTipoAsigFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FindNotasXTipoAsigFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FindNotasXTipoAsigFragment extends Fragment implements Response.Listener<JSONObject>,
        Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    int idgrupo;
    int idgrupoEst;
    int iddocente;
    int id_Ejercicio;
    int id_GrupoEstudiante = 0;
    String tipo;
    ProgressDialog progreso;
    String nameEst;
    String actividad;
    private int idestudiante = 0;
    private String flag;
    private int flag_est = 0;
    private int flag_grupoest = 0;
    int sum = 0;
    float calDefinitiva = 0;
    int listaFull = 0;
    int listaHechos = 0;

    private Button btn_prueba;
    private Button btn_ejercicio;
    private Button btn_evaluacion;
    private Button btn_definitiva_porcentaje;

    private RadioButton rb_estudiantes;
    private RadioButton rb_grupoestudiantes;

    private RecyclerView rv_notasXtipo;
    private RecyclerView rv_notasX_GEHD;

    private EditText edt_estudiantes;
    private EditText edt_grupo_estudiantes;

    private TextView txt_definitiva;
    private TextView txt_pendientes;

    private Spinner sp_estudiantes;
    private Spinner sp_grupoestudiantes;
    private Spinner sp_GEHD;

    private LinearLayout ll_estudiantes;
    private LinearLayout ll_grupoestudiantes;
    private LinearLayout ll_definitiva;

    ArrayList<Estudiante> listaEstudiantes;
    ArrayList<Integer> listaIdEstudiantes;
    List<String> listaStringEstudiantes = new ArrayList<>();
    ArrayList<DeberEstudiante> listaDeberes_full;
    ArrayList<DeberEstudiante> listaDeberes_full_1;
    ArrayList<DeberEstudiante> listaDeberes_full_temp;
    ArrayList<Integer> lista_idEstudiante;

    //ArrayList<EjercicioG1> listaEjercicios;
    ArrayList<EjercicioG2> listaEjercicios;

    ArrayList<Integer> lista_idEjercicios;
    ArrayList<Integer> lista_calificaciones;

    ArrayList<Grupo_Estudiantes> listaGrupoEstudiantes;
    ArrayList<GrupoEstudiantesHasDeber> listaGrupoEstudiantesHD;
    List<String> listaStringGrupo_Estudiantes_HD;

    int bandera = 0;
    int NDF = 0;
    int NDL = 0;
    int NDS = 0;
    int SUMNDF = 0;
    int SUMNDL = 0;
    int SUMNDS = 0;

    ArrayList<String> lista_String_Actividades;
    ArrayList<Integer> listanotaFonico;
    ArrayList<Integer> listanotaLexico;
    ArrayList<Integer> listanotaSilabico;
    ArrayList<EjercicioG2> listaEjerciciosG2;
    ArrayList<Integer> listaActividades;
    ArrayList<Integer> IDejercicios;
    ArrayList<Integer> listaCalificaciones;


    //RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    StringRequest stringRequest;

    private OnFragmentInteractionListener mListener;

    public FindNotasXTipoAsigFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FindNotasXTipoAsigFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FindNotasXTipoAsigFragment newInstance(String param1, String param2) {
        FindNotasXTipoAsigFragment fragment = new FindNotasXTipoAsigFragment();
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
        View view = inflater.inflate(R.layout.fragment_find_notas_xtipo_asig, container, false);

        progreso = new ProgressDialog(getActivity());
        iddocente = getArguments().getInt("iddocente");
        idgrupo = getArguments().getInt("idgrupo");

        listaIdEstudiantes = new ArrayList<>();
        listaGrupoEstudiantes = new ArrayList<>();
        lista_idEjercicios = new ArrayList<>();
        lista_calificaciones = new ArrayList<>();
        listaDeberes_full = new ArrayList<>();
        listaDeberes_full_1 = new ArrayList<>();
        listaDeberes_full_temp = new ArrayList<>();
        lista_idEstudiante = new ArrayList<>();
        lista_idEjercicios = new ArrayList<>();
        lista_calificaciones = new ArrayList<>();
        lista_String_Actividades = new ArrayList<>();
        listanotaFonico = new ArrayList<>();
        listanotaLexico = new ArrayList<>();
        listanotaSilabico = new ArrayList<>();
        listaEjerciciosG2 = new ArrayList<>();
        listaActividades = new ArrayList<>();

        IDejercicios = new ArrayList<>();
        listaCalificaciones = new ArrayList<>();

        btn_ejercicio = view.findViewById(R.id.btn_notas_Xejercicios);
        btn_ejercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //clearLists();
                lista_idEjercicios.clear();
                lista_calificaciones.clear();
                bandera = 0;
                NDF = 0;
                NDL = 0;
                NDS = 0;
                SUMNDF = 0;
                SUMNDL = 0;
                SUMNDS = 0;
                listanotaFonico = new ArrayList<>();
                listanotaLexico = new ArrayList<>();
                listanotaSilabico = new ArrayList<>();
                lista_String_Actividades = new ArrayList<>();
                listaDeberes_full_temp.clear();
                listaFull = 0;
                listaHechos = 0;
                sum = 0;
                calDefinitiva = 0;
                txt_definitiva.setText("");
                ll_definitiva.setVisibility(View.GONE);
                flag = "1";
                tipo = "EJERCICIO";
                System.out.println("id_GrupoEstudiante: " + id_GrupoEstudiante + ", id estudiante: " + idestudiante + "id docente: " + iddocente + ", id tipo: " + tipo);
                cargarWebService();
                ll_definitiva.setVisibility(View.VISIBLE);
            }
        });
        btn_evaluacion = view.findViewById(R.id.btn_notas_Xevaluacion);
        btn_evaluacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // clearLists();
                lista_idEjercicios.clear();
                lista_calificaciones.clear();
                bandera = 0;
                NDF = 0;
                NDL = 0;
                NDS = 0;
                SUMNDF = 0;
                SUMNDL = 0;
                SUMNDS = 0;
                listanotaFonico = new ArrayList<>();
                listanotaLexico = new ArrayList<>();
                listanotaSilabico = new ArrayList<>();
                lista_String_Actividades = new ArrayList<>();
                listaDeberes_full_temp.clear();
                listaFull = 0;
                listaHechos = 0;
                ll_definitiva.setVisibility(View.GONE);
                sum = 0;
                calDefinitiva = 0;
                txt_definitiva.setText("");
                flag = "1";
                tipo = "EVALUACION";
                System.out.println("id_GrupoEstudiante: " + id_GrupoEstudiante + ", id estudiante: " + idestudiante + "id docente: " + iddocente + ", id tipo: " + tipo);
                cargarWebService();
                ll_definitiva.setVisibility(View.VISIBLE);
            }
        });
        btn_prueba = view.findViewById(R.id.btn_notas_Xprueba);
        btn_prueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lista_idEjercicios.clear();
                lista_calificaciones.clear();
                bandera = 0;
                NDF = 0;
                NDL = 0;
                NDS = 0;
                SUMNDF = 0;
                SUMNDL = 0;
                SUMNDS = 0;
                listanotaFonico = new ArrayList<>();
                listanotaLexico = new ArrayList<>();
                listanotaSilabico = new ArrayList<>();
                lista_String_Actividades = new ArrayList<>();
                listaDeberes_full_temp.clear();
                ll_definitiva.setVisibility(View.GONE);
                listaFull = 0;
                listaHechos = 0;
                sum = 0;
                calDefinitiva = 0;
                txt_definitiva.setText("");
                // clearLists();
                flag = "1";
                tipo = "PRUEBA";
                System.out.println("id_GrupoEstudiante: " + id_GrupoEstudiante + ", id estudiante: " + idestudiante + "id docente: " + iddocente + ", id tipo: " + tipo);
                cargarWebService();
                ll_definitiva.setVisibility(View.VISIBLE);
            }
        });

        btn_definitiva_porcentaje = view.findViewById(R.id.btn_definitiva_Porcentaje);
        btn_definitiva_porcentaje.setVisibility(View.GONE);
        btn_definitiva_porcentaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("fonico", NDF);
                bundle.putInt("lexico", NDL);
                bundle.putInt("silabico", NDS);
                bundle.putString("nameEst", nameEst);
                System.out.println("nameEst_: " + nameEst);
                Intent intent = new Intent(getActivity(), PorcentNotasActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                btn_definitiva_porcentaje.setVisibility(View.GONE);
            }
        });

        rb_estudiantes = view.findViewById(R.id.rb_notasXEstudiante);
        rb_estudiantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificaRB();
            }
        });
        rb_grupoestudiantes = view.findViewById(R.id.rb_notasXGrupoEstudiante);
        rb_grupoestudiantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificaRB();
            }
        });

        edt_estudiantes = view.findViewById(R.id.edt_doc_estudiante_nota_X_Estudiante);
        edt_grupo_estudiantes = view.findViewById(R.id.edt_doc_estudiante_nota_Xtipo);

        txt_definitiva = view.findViewById(R.id.txt_definitiva);
        txt_pendientes = view.findViewById(R.id.txt_pendientes);

        sp_estudiantes = view.findViewById(R.id.sp_notasXest);
        sp_grupoestudiantes = view.findViewById(R.id.sp_notasX_Grupo);
        sp_GEHD = view.findViewById(R.id.sp_notasX_GEHD_id);
        sp_GEHD.setVisibility(View.GONE);

        ll_estudiantes = view.findViewById(R.id.ll_tipo_nota_est);
        ll_grupoestudiantes = view.findViewById(R.id.ll_tipo_nota_grupo);
        ll_definitiva = view.findViewById(R.id.ll_definitiva);
        ll_definitiva.setVisibility(View.GONE);


        rv_notasXtipo = (RecyclerView) view.findViewById(R.id.rv_docente_Xtipo);
        rv_notasXtipo.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_notasXtipo.setHasFixedSize(true);

       /* rv_notasX_GEHD = (RecyclerView) view.findViewById(R.id.rv_docente_X_id_GEHD);
        rv_notasX_GEHD.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_notasX_GEHD.setHasFixedSize(true);*/
        listarSpinner();

        verificaRB();

        return view;
    }

    private void definitiva() {

        for (int i = 0; i < lista_calificaciones.size(); i++) {
            sum += lista_calificaciones.get(i);
        }
        calDefinitiva = sum / lista_calificaciones.size();
        System.out.println("sum: " + sum);
        System.out.println("lista_calificaciones.size(): " + lista_calificaciones.size());
        txt_definitiva.setText(String.valueOf(calDefinitiva));
        System.out.println("calDefinitiva: " + calDefinitiva);
        flag = "2";
        cargarWebService();
    }

    private void listarSpinner() {
        if (flag_est == 0) {
            listarEstudiantes();
        } else {

        }
        if (flag_grupoest == 0) {
            listarGrupo_Estudiantes_Docente();
        } else {

        }

    }

    private void clearLists() {

        listaIdEstudiantes = new ArrayList<>();
        listaGrupoEstudiantes = new ArrayList<>();
        lista_idEjercicios = new ArrayList<>();
        lista_calificaciones = new ArrayList<>();
        listaDeberes_full = new ArrayList<>();
        lista_idEstudiante = new ArrayList<>();
        lista_idEjercicios = new ArrayList<>();
        lista_calificaciones = new ArrayList<>();
    }

    //***********************************

    private void verificaRB() {
        if (rb_estudiantes.isChecked()) {
            ll_estudiantes.setVisibility(View.VISIBLE);
            ll_grupoestudiantes.setVisibility(View.GONE);
            id_GrupoEstudiante = 0;
            flag_grupoest = 1;
            edt_estudiantes.setText("");
            listarSpinner();
            ll_definitiva.setVisibility(View.GONE);

            //edt_grupo_estudiantes.setText("");
        }
        if (rb_grupoestudiantes.isChecked()) {
            ll_estudiantes.setVisibility(View.GONE);
            ll_grupoestudiantes.setVisibility(View.VISIBLE);
            idestudiante = 0;
            flag_est = 1;
            edt_grupo_estudiantes.setText("");
            listarSpinner();
            ll_definitiva.setVisibility(View.GONE);
            //edt_estudiantes.setText("");
        }
    }

    //***********************************

    private void cargarWebService() {

        progreso.setMessage("Cargando...");
        progreso.show();
        // String ip = getString(R.string.ip);
        //int iddoc=20181;
        String iddoc = "20181";
        String url_lh = Globals.url;

        if (flag.equals("1")) {

            String url = "http://" + url_lh + "/proyecto_dconfo_v1/8_5_4wsJSONConsultarListaDeberes_Tipo.php?estudiante_idestudiante="
                    + idestudiante + "&grupo_estudiantes=" + id_GrupoEstudiante + "&iddocente=" + iddocente + "&tipo=" + tipo;
            /*String url = "http://" + url_lh + "/proyecto_dconfo_v1/8_5_4_1wsJSONConsultarListaDeberes_Tipo_full.php?estudiante_idestudiante=" + idestudiante + "&grupo_estudiantes=" + id_GrupoEstudiante +
                    "&iddocente=" + iddocente + "&tipo=" + tipo;*/

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

            //String url = "http://" + url_lh + "/proyecto_dconfo_v1/9wsJSONConsultarEjercicioEstudiante.php?idEjercicioG2=" + id_Ejercicio;
            String url = "http://" + url_lh + "/proyecto_dconfo_v1/8_5_4_1wsJSONConsultarListaDeberes_Tipo_full.php?estudiante_idestudiante=" + idestudiante + "&grupo_estudiantes=" + id_GrupoEstudiante +
                    "&iddocente=" + iddocente + "&tipo=" + tipo;

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
        if (flag.equals("1") || flag.equals("2")) {
            //Toast.makeText(getApplicationContext(), "Mensaje: " + response.toString(), Toast.LENGTH_SHORT).show();
            DeberEstudiante deberEstudiante = null;

            listaDeberes_full.clear();
            listaDeberes_full_1.clear();
            lista_idEstudiante.clear();
            // lista_idEjercicios.clear();
            // lista_calificaciones.clear();


            //if (idestudiante != 0) {
            JSONArray json = response.optJSONArray("deber_nota");
            System.out.println("json 1: " + json.toString());


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
                    if (flag.equals("1")) {
                        listaDeberes_full.add(deberEstudiante);
                        listaDeberes_full_temp.add(deberEstudiante);
                        lista_idEstudiante.add(deberEstudiante.getIdEstudiante());
                        lista_idEjercicios.add(deberEstudiante.getIdEjercicio2());
                        lista_calificaciones.add(deberEstudiante.getIdCalificacion());
                        IDejercicios.add(deberEstudiante.getIdEjercicio2());
                        listaCalificaciones.add(deberEstudiante.getIdCalificacion());
                    }
                    if (flag.equals("2")) {
                        listaDeberes_full_1.add(deberEstudiante);
                        //lista_idEjercicios.add(deberEstudiante.getIdEjercicio2());
                        //lista_calificaciones.add(deberEstudiante.getIdCalificacion());
                    }

                }

                if (flag.equals("1")) {

                    NotasDeberesEstudianteAdapter notasDeberesEstudianteAdapter = new NotasDeberesEstudianteAdapter(listaDeberes_full);

                    notasDeberesEstudianteAdapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                        }
                    });
                    rv_notasXtipo.setAdapter(notasDeberesEstudianteAdapter);
                }
                if (flag.equals("2")) {

                    NotasDeberesEstudianteAdapter notasDeberesEstudianteAdapter = new NotasDeberesEstudianteAdapter(listaDeberes_full_temp);

                    notasDeberesEstudianteAdapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                        }
                    });
                    rv_notasXtipo.setAdapter(notasDeberesEstudianteAdapter);
                }
                //Toast.makeText(getApplicationContext(), "listagrupos: " + listaGrupos.size(), Toast.LENGTH_LONG).show();
                // Log.i("size", "lista: " + listaGrupos.size());

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("error", response.toString());

                Toast.makeText(getContext(), "No se ha podido establecer conexión: " + response.toString(), Toast.LENGTH_LONG).show();

                progreso.hide();
            }

            if (flag.equals("1")) {
                listaHechos = listaDeberes_full.size();
                definitiva();
            }


            if (listaDeberes_full_1.size() != 0) {
                listaFull = listaDeberes_full_1.size();
                System.out.println("==========listaDeberes_full_1: " + listaDeberes_full_1.size());
                pendientes();
            }


            for (int i = 0; i < lista_idEjercicios.size(); i++) {
                System.out.println("Lista id ejercicios: " + lista_idEjercicios.get(i));
            }
            for (int i = 0; i < lista_calificaciones.size(); i++) {
                System.out.println("lista_calificaciones: " + lista_calificaciones.get(i));
            }

            //getEjercicioAct();

            //System.out.println("Lista id estudiante: " + lista_idEstudiante.toString());
        }//if flag==1 y 2

        else
            //flag="1"
            if (flag.equals("3")) {
                //Toast.makeText(getApplicationContext(), "Mensaje: " + response.toString(), Toast.LENGTH_SHORT).show();
                DeberEstudiante deberEstudiante = null;

                bandera++;
                EjercicioG2 ejercicioG2 = null;

                JSONArray json = response.optJSONArray("ejerciciog2");
                System.out.println("response flag= 3 : " + response.toString());
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
            }//flag="3"


    }//********************************************

    private void pendientes() {
        int pen = (listaFull - listaHechos);
        System.out.println("listaFull: " + listaFull);
        System.out.println("listaHechos: " + listaHechos);
        System.out.println("pen: " + pen);
        txt_pendientes.setText(String.valueOf(pen));
        getEjercicioAct();
    }


    //***********************************

    //********************************************

    private void getEjercicioAct() {
        if (bandera < listaDeberes_full_temp.size()) {

            id_Ejercicio = lista_idEjercicios.get(bandera);
            System.out.println("id_Ejercicio: " + id_Ejercicio);
            flag = "3";
            cargarWebService();
        } else {
            calculaNotaAct();
        }

    }

    private void calculaNotaAct() {


        for (int i = 0; i < lista_calificaciones.size(); i++) {
            if (lista_String_Actividades.size() != 0) {
                //System.out.println("lista_String_Actividades.size(): " + lista_String_Actividades.size());
                if (lista_String_Actividades.get(i) == "f") {
                    SUMNDF += lista_calificaciones.get(i);
                } else if (lista_String_Actividades.get(i) == "l") {
                    SUMNDL += lista_calificaciones.get(i);
                } else if (lista_String_Actividades.get(i) == "s") {
                    SUMNDS += lista_calificaciones.get(i);
                }
            } else {
                System.out.println("lista_String_Actividades.size(): " + lista_String_Actividades.size());
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


        btn_definitiva_porcentaje.setVisibility(View.VISIBLE);
        System.out.println("NDF: " + NDF);
        System.out.println("NDL: " + NDL);
        System.out.println("NDS: " + NDS);
    }


    //***********************************

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
                            sp_estudiantes.setAdapter(adapter);
                            sp_estudiantes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position != -1) {
                                        //listaIdEstudiantes.add(listaEstudiantes.get(position - 1).getIdestudiante());
                                        listaIdEstudiantes.add(listaEstudiantes.get(position).getIdestudiante());
                                        edt_estudiantes.setText(listaEstudiantes.get(position).getIdestudiante().toString());
                                        nameEst = listaEstudiantes.get(position).getNameestudiante();
                                        idestudiante = Integer.parseInt(edt_estudiantes.getText().toString());
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


    //***********************************
    public void listarGrupo_Estudiantes_Docente() {


        String url_lh = Globals.url;

        String url = "http://" + url_lh +
                "/proyecto_dconfo_v1/12wsJSONConsultar_Lista_Grupo_Est.php?idgrupo=" + idgrupo + "&iddocente=" + iddocente;

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

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
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listaStringGrupo_Estudiantes);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sp_grupoestudiantes.setAdapter(adapter);
                            sp_grupoestudiantes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position != 0) {
                                        System.out.println("****************listaGrupoEstudiantes: " + listaGrupoEstudiantes.size());
                                        edt_grupo_estudiantes.setText(listaGrupoEstudiantes.get(position - 1).getIdGrupoEstudiantes().toString());
                                        id_GrupoEstudiante = Integer.parseInt(edt_grupo_estudiantes.getText().toString());
                                        idgrupoEst = listaGrupoEstudiantes.get(position - 1).getIdGrupoEstudiantes();
                                        System.out.println("idgrupoEst : " + idgrupoEst);
                                        listarGrupoHD_x_id_grupoest();
                                        sp_GEHD.setVisibility(View.VISIBLE);
                                        //edt_estudiantes.setText("");
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
    public void listarGrupoHD_x_id_grupoest() {

        Toast.makeText(getContext(), "//////////////listarGrupoHD_x_id_grupoest: ", Toast.LENGTH_LONG).show();
        String url_lh = Globals.url;

        String url = "http://" + url_lh +
                "/proyecto_dconfo_v1/17_1wsJSONConsultar_Lista_Grupo_H_Deber_XidGE.php?iddocente=" + iddocente + "&idgrupoEst=" + idgrupoEst;

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        GrupoEstudiantesHasDeber grupoEstudiantesHasDeber = null;


                        ArrayList<Curso> listaDCursos = new ArrayList<>();
                        //listaCursos1 = new ArrayList<>();

                        listaGrupoEstudiantesHD = new ArrayList<>();
                        listaStringGrupo_Estudiantes_HD = new ArrayList<>();

                        // Process the JSON
                        try {

                            JSONArray array = response.optJSONArray("id_grupoHest");

                            // Loop through the array elements
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject student = array.getJSONObject(i);
                                grupoEstudiantesHasDeber = new GrupoEstudiantesHasDeber();
                                JSONObject jsonObject = null;
                                jsonObject = array.getJSONObject(i);
                                grupoEstudiantesHasDeber.setId_GE_H_D(jsonObject.optInt("id_GE_H_D"));
                                grupoEstudiantesHasDeber.setFecha_gehd(jsonObject.optString("fecha_gehd"));


                                listaGrupoEstudiantesHD.add(grupoEstudiantesHasDeber);
                            }


                            listaStringGrupo_Estudiantes_HD.add("Seleccione fecha de asignación");
                            for (int i = 0; i < listaGrupoEstudiantesHD.size(); i++) {
                                listaStringGrupo_Estudiantes_HD.add(listaGrupoEstudiantesHD.get(i).getId_GE_H_D().toString() + " - " + listaGrupoEstudiantesHD.get(i).getFecha_gehd());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listaStringGrupo_Estudiantes_HD);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sp_GEHD.setAdapter(adapter);
                            sp_GEHD.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position != 0) {
                                        System.out.println("listaGrupoEstudiantesHD: " + listaGrupoEstudiantesHD.size());
                                        edt_grupo_estudiantes.setText(listaGrupoEstudiantesHD.get(position - 1).getId_GE_H_D().toString() + " - " + listaGrupoEstudiantesHD.get(position - 1).getFecha_gehd());
                                        id_GrupoEstudiante = listaGrupoEstudiantesHD.get(position - 1).getId_GE_H_D();
                                        idestudiante = 0;
                                        Toast.makeText(getContext(), "id_GrupoEstudiante: " + id_GrupoEstudiante, Toast.LENGTH_LONG).show();
                                        //edt_estudiantes.setText("");
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
