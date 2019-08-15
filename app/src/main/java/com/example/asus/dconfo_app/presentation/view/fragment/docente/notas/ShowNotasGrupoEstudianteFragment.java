package com.example.asus.dconfo_app.presentation.view.fragment.docente.notas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.domain.model.DeberEstudiante;
import com.example.asus.dconfo_app.domain.model.EjercicioG2;
import com.example.asus.dconfo_app.domain.model.Estudiante;
import com.example.asus.dconfo_app.domain.model.VolleySingleton;
import com.example.asus.dconfo_app.helpers.Globals;
import com.example.asus.dconfo_app.presentation.view.activity.docente.notas.NotasActivity;
import com.example.asus.dconfo_app.presentation.view.activity.docente.notas.PorcentNotasActivity;
import com.example.asus.dconfo_app.presentation.view.adapter.NotasDeberesEstudianteAdapter;
import com.example.asus.dconfo_app.presentation.view.adapter.TipoEjerciciosDocente_EjerG2Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowNotasGrupoEstudianteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShowNotasGrupoEstudianteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowNotasGrupoEstudianteFragment extends Fragment implements Response.Listener<JSONObject>,
        Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    int idgrupo;
    int idgrupo_GrupoEst;
    int id_GrupoEst_rv;
    int iddocente;
    ProgressDialog progreso;
    //RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    StringRequest stringRequest;
    ArrayList<String> listaStringIdGrupoEstudiantes;
    ArrayList<Estudiante> listaEstudiantes;
    ArrayList<Integer> listaIdEstudiantes;
    List<String> listaStringEstudiantes = new ArrayList<>();
    ArrayList<DeberEstudiante> listaDeberes_full;

    private String flag;
    RecyclerView rv_datosEstudiante;
    RecyclerView rv_ejercicios;
    FloatingActionButton fl_back;
    FindNotasXGrupoEstFragment findNotasXGrupoEstFragment;

    ArrayList<Integer> lista_notas;
    ArrayList<Integer> lista_idEjercicios;
    ArrayList<Integer> lista_Calificaciones;
    ArrayList<Integer> lista_idEstudiante;
    ArrayList<Integer> lista_idEjercicioNRepetido;
    ArrayList<Integer> lista_idEstudiantesNRepetido;
    ArrayList<String> lista_String_idEstNRepetido;
    ArrayList<Integer> lista_int_Ejercicio_Calificacion;
    ArrayList<EjercicioG2> listaEjercicios;
    ArrayList<Integer> lisnotaprov;

    int porcentajeHechos;
    int califPromedio;
    int idEjercicio;
    int ID_Ejercicio;
    int bandera = 0;
    TextView txt_porcentajeHechos;
    TextView txt_califPromedio;

    Button btn_Todas;
    Button btn_Ejercicios;
    Button btn_verPorcentajeEstudiante;

    int NDF = 0;
    int NDL = 0;
    int NDS = 0;
    int SUMNDF = 0;
    int SUMNDL = 0;
    int SUMNDS = 0;
    String nameEst;

    private OnFragmentInteractionListener mListener;

    public ShowNotasGrupoEstudianteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowNotasGrupoEstudianteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowNotasGrupoEstudianteFragment newInstance(String param1, String param2) {
        ShowNotasGrupoEstudianteFragment fragment = new ShowNotasGrupoEstudianteFragment();
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
        View view = inflater.inflate(R.layout.fragment_show_notas_grupo_estudiante, container, false);

        lista_notas = new ArrayList<>();
        lista_idEjercicios = new ArrayList<>();
        lista_Calificaciones = new ArrayList<>();
        lista_idEstudiante = new ArrayList<>();
        lista_idEjercicioNRepetido = new ArrayList<>();
        lista_idEstudiantesNRepetido = new ArrayList<>();
        lista_String_idEstNRepetido = new ArrayList<>();
        lista_int_Ejercicio_Calificacion = new ArrayList<>();
        listaEjercicios = new ArrayList<>();
        lisnotaprov = new ArrayList<>();

        txt_porcentajeHechos = view.findViewById(R.id.txt_show_porc_eje_hechos);
        txt_califPromedio = view.findViewById(R.id.txt_show_porc_calif_prom);

        rv_datosEstudiante = (RecyclerView) view.findViewById(R.id.rv_notasGrupoEstHasDeber);
        rv_datosEstudiante.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_datosEstudiante.setHasFixedSize(true);

        rv_datosEstudiante.setVisibility(View.GONE);

        rv_ejercicios = (RecyclerView) view.findViewById(R.id.rv_notasGrupoEjercicios);
        rv_ejercicios.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_ejercicios.setHasFixedSize(true);


        btn_Todas = view.findViewById(R.id.btn_showAllGrades);
        btn_Todas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rv_datosEstudiante.setVisibility(View.VISIBLE);
                rv_ejercicios.setVisibility(View.INVISIBLE);
            }
        });
        btn_Ejercicios = view.findViewById(R.id.btn_showPerEjercicio);
        btn_Ejercicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rv_datosEstudiante.setVisibility(View.INVISIBLE);
                rv_ejercicios.setVisibility(View.VISIBLE);

            }
        });
        btn_verPorcentajeEstudiante = view.findViewById(R.id.btn_showPorcentajeNotas);
        btn_verPorcentajeEstudiante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* lista_int_Ejercicio_Calificacion.add(3);
                lista_int_Ejercicio_Calificacion.add(5);*/
                llamarPorcentaje();

            }
        });

        progreso = new ProgressDialog(getActivity());
        iddocente = getArguments().getInt("iddocente");
        idgrupo = getArguments().getInt("idgrupo");
        id_GrupoEst_rv = getArguments().getInt("idgrupoesthasdeber");
        System.out.println("id grupoesthasdeber: " + id_GrupoEst_rv);

        listaDeberes_full = new ArrayList<>();

        fl_back = (FloatingActionButton) view.findViewById(R.id.fabtn_show_back);
        fl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle args1 = new Bundle();
                args1.putInt("iddocente", iddocente);
                args1.putInt("idgrupo", idgrupo);


                findNotasXGrupoEstFragment = new FindNotasXGrupoEstFragment();
                findNotasXGrupoEstFragment.setArguments(args1);
                getFragmentManager().beginTransaction().replace(R.id.fl_contenedor_notas, findNotasXGrupoEstFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();
            }
        });


        flag = "1";
        cargarWebService();
        return view;
    }

    private void llamarPorcentaje() {
        Bundle bundle = new Bundle();
        bundle.putInt("fonico", NDF);
        bundle.putInt("lexico", NDL);
        bundle.putInt("silabico", NDS);
        bundle.putString("nameEst", nameEst);
        bundle.putStringArrayList("list", lista_String_idEstNRepetido);
        // bundle.putIntegerArrayList("listCalificacion", lista_int_Ejercicio_Calificacion);
        bundle.putIntegerArrayList("listCalificacion", lisnotaprov);

        for (int i = 0; i < lista_String_idEstNRepetido.size(); i++) {
            System.out.println("lista_String_idEstNRepetido **********: " + lista_String_idEstNRepetido.get(i));
        }

        for (int i = 0; i < lisnotaprov.size(); i++) {
            System.out.println("listCalificacion **********: " + lisnotaprov.get(i));
        }

        System.out.println("nameEst_**********: " + nameEst);
        System.out.println("lisnotaprov**********: " + lisnotaprov.size());
        Intent intent = new Intent(getActivity(), PorcentNotasActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void cargarWebService() {

        progreso.setMessage("Cargando...");
        progreso.show();
        // String ip = getString(R.string.ip);
        //int iddoc=20181;
        String iddoc = "20181";
        String url_lh = Globals.url;

        if (flag.equals("1")) {

            String url = "http://" + url_lh + "/proyecto_dconfo_v1/8_5_2wsJSONConsultarListaDebEstGEST.php?idgrupoesthasdeber=" + id_GrupoEst_rv;

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

            String url = "http://" + url_lh + "/proyecto_dconfo_v1/2_1wsJSONConsultarEjercicio_X_Id.php?iddocente=" + iddocente + "&idejercicio=" + idEjercicio;

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
        if (flag.equals("1")) {
            //Toast.makeText(getApplicationContext(), "Mensaje: " + response.toString(), Toast.LENGTH_SHORT).show();
            DeberEstudiante deberEstudiante = null;
            JSONArray json = response.optJSONArray("notasdeberxgehd");


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
                    lista_notas.add(deberEstudiante.getIdCalificacion());
                    lista_idEjercicios.add(deberEstudiante.getIdEjercicio2());
                    lista_idEstudiante.add(deberEstudiante.getIdEstudiante());

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
            verificaEjeHechos();
            creaListaNoRepetidos();
            creaListaNoRepetidosEstudiantes();
            buscarEjercicio();

            for (int i = 0; i < listaDeberes_full.size(); i++) {
                // System.out.println("Lista listaDeberes_full show: i=" + (i + 1) + " - Find - " + listaDeberes_full.get(i).getIdCalificacion());
            }
            for (int i = 0; i < lista_notas.size(); i++) {
                System.out.println("lista_notas : " + lista_notas.get(i));
            }
            for (int i = 0; i < lista_idEjercicios.size(); i++) {
                System.out.println("lista_idEjercicios: " + lista_idEjercicios.get(i));
            }

            //System.out.println("lista_idEjercicios size: " + lista_idEjercicios.size());

        }//flag="1"
        else if (flag.equals("2")) {
            //Toast.makeText(getApplicationContext(), "Mensaje: " + response.toString(), Toast.LENGTH_SHORT).show();
            // DeberEstudiante deberEstudiante = null;
            System.out.println("***********flag2: ");
            JSONArray json = response.optJSONArray("ejerciciog2");
            bandera++;
            //
            EjercicioG2 ejercicioG2 = null;


            try {
                // JSONArray json = response.optJSONArray("ejerciciog2");
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

                TipoEjerciciosDocente_EjerG2Adapter tipoEjerciciosDocente_ejerG2Adapter = new TipoEjerciciosDocente_EjerG2Adapter(listaEjercicios);

                tipoEjerciciosDocente_ejerG2Adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ID_Ejercicio = listaEjercicios.get(rv_ejercicios.getChildAdapterPosition(view)).getIdEjercicioG2();
                        Toast.makeText(getContext(), "ID_Ejercicio: " + ID_Ejercicio, Toast.LENGTH_LONG).show();
                        calculaNotaAct();
                        llamarPorcentaje();
                    }
                });
                rv_ejercicios.setAdapter(tipoEjerciciosDocente_ejerG2Adapter);


              /*  listaStringEjercicios.add("Seleccione Id Ejercicio");
                for (int i = 0; i < listaEjercicios.size(); i++) {
                    listaStringEjercicios.add(listaEjercicios.get(i).getIdEjercicioG2().toString());
                }*/

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("error f2", response.toString());

                Toast.makeText(getContext(), "No se ha podido establecer conexión: " + response.toString(), Toast.LENGTH_LONG).show();

                progreso.hide();
            }

            buscarEjercicio();

            for (int i = 0; i < listaEjercicios.size(); i++) {
                System.out.println("***********listaEjercicios: " + listaEjercicios.get(i).getNameEjercicioG2());
            }


        }//flag 2


    }//********************************************

    private void buscarEjercicio() {
        if (bandera < lista_idEjercicioNRepetido.size()) {
            idEjercicio = lista_idEjercicioNRepetido.get(bandera);
            System.out.println("idEjercicio////////// : " + idEjercicio);
            flag = "2";
            cargarWebService();
        } else {
            //calculaNotaAct();
        }
    }

    private void calculaNotaAct() {
        //Toast.makeText(getContext(), "calculaNotaAct: " + ID_Ejercicio, Toast.LENGTH_LONG).show();
        lisnotaprov.clear();

        int sum_nota = 0;
        for (int i = 0; i < listaDeberes_full.size(); i++) {
            //  for (int k = 0; k < lista_idEstudiantesNRepetido.size(); k++) {

            if (listaDeberes_full.get(i).getIdEjercicio2() == ID_Ejercicio) {

               // for (int k = 0; k < lista_idEstudiantesNRepetido.size(); k++) {

                  //  if (lista_idEstudiantesNRepetido.get(k) == listaDeberes_full.get(i).getIdEstudiante()) {

                        lisnotaprov.add(listaDeberes_full.get(i).getIdCalificacion());
                        System.out.println("================lisnotaprov : ");
                   // }
               // }
                sum_nota++;
            }
            // }

        }
        System.out.println("================SI : " + sum_nota);
        System.out.println("================SALE : " + ID_Ejercicio);
        for (int i = 0; i < lisnotaprov.size(); i++) {
            System.out.println("================lisnotaprov : " + lisnotaprov.get(i));
        }

    }
   /* private void calculaNotaAct() {
        int sum_nota = 0;
        for (int i = 0; i < listaDeberes_full.size(); i++) {
            for (int k = 0; k < lista_idEjercicioNRepetido.size(); k++) {
                if (lista_idEjercicioNRepetido.get(k) == listaDeberes_full.get(i).getIdEjercicio2()) {
                    lisnotaprov.add(listaDeberes_full.get(i).getIdCalificacion());
                    System.out.println("lisnotaprov : "+lista_idEjercicioNRepetido.get(k)+" - " + lisnotaprov.get(i));
                }
            }

        }
    }*/


    private void creaListaNoRepetidos() {

        int rep = 0;
        for (int i = 0; i < lista_idEjercicios.size(); i++) {
            lista_idEjercicioNRepetido.add(lista_idEjercicios.get(i));
        }
        Set<Integer> hs = new HashSet<>();
        hs.addAll(lista_idEjercicioNRepetido);
        lista_idEjercicioNRepetido.clear();
        lista_idEjercicioNRepetido.addAll(hs);

        for (int i = 0; i < lista_idEjercicioNRepetido.size(); i++) {
            System.out.println("lista_idEjercicioNRepetido : " + lista_idEjercicioNRepetido.get(i));
        }


    }

    private void creaListaNoRepetidosEstudiantes() {

        int rep = 0;
        for (int i = 0; i < lista_idEstudiante.size(); i++) {
            lista_idEstudiantesNRepetido.add(lista_idEstudiante.get(i));
        }
        Set<Integer> hs = new HashSet<>();
        hs.addAll(lista_idEstudiantesNRepetido);
        lista_idEstudiantesNRepetido.clear();
        lista_idEstudiantesNRepetido.addAll(hs);

        btn_verPorcentajeEstudiante.setVisibility(View.VISIBLE);

        for (int i = 0; i < lista_idEstudiantesNRepetido.size(); i++) {
            // System.out.println("lista_idEstudiantesNRepetido : " + lista_idEstudiantesNRepetido.get(i));
        }

        for (int i = 0; i < lista_idEstudiantesNRepetido.size(); i++) {
            lista_String_idEstNRepetido.add(String.valueOf(lista_idEstudiantesNRepetido.get(i)));
            System.out.println("lista_String_idEstNRepetido : " + lista_String_idEstNRepetido.get(i));
        }

    }

    private void verificaEjeHechos() {
        int sum = 0;
        int sumNotas = 0;
        for (int i = 0; i < lista_notas.size(); i++) {
            if (lista_notas.get(i) != 0) {
                sum++;
                sumNotas += lista_notas.get(i);
                System.out.println("lista_notas: " + lista_notas.get(i));

            }

        }
        // if (i == lista_notas.size()) {
        porcentajeHechos = (sum * 100) / lista_notas.size();
        califPromedio = sumNotas / sum;

        txt_porcentajeHechos.setText(String.valueOf(porcentajeHechos));
        txt_califPromedio.setText(String.valueOf(califPromedio));

        System.out.println("porcentajeHechos: " + porcentajeHechos);
        //}
    }


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
