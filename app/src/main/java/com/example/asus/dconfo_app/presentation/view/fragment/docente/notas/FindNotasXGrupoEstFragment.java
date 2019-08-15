package com.example.asus.dconfo_app.presentation.view.fragment.docente.notas;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.EditText;
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
import com.example.asus.dconfo_app.domain.model.Estudiante;
import com.example.asus.dconfo_app.domain.model.GrupoEstudiantesHasDeber;
import com.example.asus.dconfo_app.domain.model.Grupo_Estudiantes;
import com.example.asus.dconfo_app.domain.model.VolleySingleton;
import com.example.asus.dconfo_app.helpers.Globals;
import com.example.asus.dconfo_app.presentation.view.adapter.Grupos_Estudiante_Has_DeberAdapter;
import com.example.asus.dconfo_app.presentation.view.adapter.NotasDeberesEstudianteAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FindNotasXGrupoEstFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FindNotasXGrupoEstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FindNotasXGrupoEstFragment extends Fragment implements Response.Listener<JSONObject>,
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
    int id_GrupoEst_has_deber;
    int iddocente;
    ProgressDialog progreso;

    Spinner sp_Grupo_Estudiantes;
    EditText edt_idGrupoEstudiante;
    Button btn_BuscarGrupoEstudiante;
    RecyclerView rv_datosGrupoEstudiante;
    private int idestudiante;

    private String flag;

    ArrayList<String> listaStringIdGrupoEstudiantes;
    ArrayList<Estudiante> listaEstudiantes;
    ArrayList<Integer> listaIdEstudiantes;
    List<String> listaStringGrupoEstudiantes = new ArrayList<>();
    List<String> listaStringGrupoEstHasDeber;
    ArrayList<Grupo_Estudiantes> listaGrupoEstudiantes;
    ArrayList<Integer> lista_idEstudiante;
    ArrayList<DeberEstudiante> listaDeberes_full;
    ArrayList<GrupoEstudiantesHasDeber> listaGrupoEstHasDeber;

    ShowNotasGrupoEstudianteFragment showNotasGrupoEstudianteFragment;

    //RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    StringRequest stringRequest;

    private OnFragmentInteractionListener mListener;

    public FindNotasXGrupoEstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FindNotasXGrupoEstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FindNotasXGrupoEstFragment newInstance(String param1, String param2) {
        FindNotasXGrupoEstFragment fragment = new FindNotasXGrupoEstFragment();
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
        View view = inflater.inflate(R.layout.fragment_find_notas_xgrupo_est, container, false);
        ;
        progreso = new ProgressDialog(getActivity());
        iddocente = getArguments().getInt("iddocente");
        idgrupo = getArguments().getInt("idgrupo");

        sp_Grupo_Estudiantes = (Spinner) view.findViewById(R.id.sp_docente_X_grupo_nota);
        edt_idGrupoEstudiante = (EditText) view.findViewById(R.id.edt_doc_X_grupo_nota);

        rv_datosGrupoEstudiante = (RecyclerView) view.findViewById(R.id.rv_docente_X_Grupo_Est_notas);
        rv_datosGrupoEstudiante.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_datosGrupoEstudiante.setHasFixedSize(true);
        rv_datosGrupoEstudiante.setVisibility(View.GONE);

        listaDeberes_full = new ArrayList<>();
        listaGrupoEstHasDeber = new ArrayList<>();

        btn_BuscarGrupoEstudiante = (Button) view.findViewById(R.id.btn_docente_buscar_X_grupo_nota);
        btn_BuscarGrupoEstudiante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = "1";
                listaGrupoEstHasDeber = new ArrayList<>();
                idgrupo_GrupoEst = Integer.parseInt(edt_idGrupoEstudiante.getText().toString());
                cargarWebService();
            }
        });

        listarGrupoEstudiantes();

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

            String url = "http://" + url_lh + "/proyecto_dconfo_v1/8_5_1wsJSONConsultarListaDeberesGrupoEst_nota.php?id_grupo_estudiante=" + idgrupo_GrupoEst;

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
        }


    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getContext(), "No hay actividades para el grupo de estudiantes: " + idgrupo_GrupoEst, Toast.LENGTH_LONG).show();
        System.out.println("el error: " + error.toString());
        // Log.d("ERROR", error.toString());
        progreso.hide();
    }

    // si esta bien el llamado a la url entonces entra a este metodo
    @Override
    public void onResponse(JSONObject response) {
        progreso.hide();
        if (flag.equals("1")) {
            //Toast.makeText(getApplicationContext(), "Mensaje: " + response.toString(), Toast.LENGTH_SHORT).show();
            //DeberEstudiante deberEstudiante = null;

            GrupoEstudiantesHasDeber grupoEstudiantesHasDeber = null;
            JSONArray json = response.optJSONArray("deber_nota");


            try {
                for (int i = 0; i < json.length(); i++) {
                    grupoEstudiantesHasDeber = new GrupoEstudiantesHasDeber();
                    JSONObject jsonObject = null;
                    jsonObject = json.getJSONObject(i);
                    // jsonObject = new JSONObject(response);
                    grupoEstudiantesHasDeber.setGrupo_estudiante_idgrupo_estudiante(jsonObject.optInt("grupo_estudiante_idgrupo_estudiante"));
                    grupoEstudiantesHasDeber.setId_GE_H_D(jsonObject.optInt("id_GE_H_D"));
                    grupoEstudiantesHasDeber.setFecha_gehd(jsonObject.optString("fecha_gehd"));

                    listaGrupoEstHasDeber.add(grupoEstudiantesHasDeber);
                    // lista_idEstudiante.add(deberEstudiante.getIdEstudiante());

                }
             /*   listaStringGrupoEstHasDeber = new ArrayList<>();
                // listaStringEstudiantes.add("Seleccione Id Estudiante");
                for (int i = 0; i < listaGrupoEstudiantes.size(); i++) {
                    listaStringGrupoEstHasDeber.add(listaGrupoEstudiantes.get(i).getIdGrupoEstudiantes().toString() + " - " + listaGrupoEstudiantes.get(i).getNameGrupoEstudiantes());
                }*/
                Grupos_Estudiante_Has_DeberAdapter grupos_estudiante_has_deberAdapter = new Grupos_Estudiante_Has_DeberAdapter(listaGrupoEstHasDeber);

                grupos_estudiante_has_deberAdapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        id_GrupoEst_rv = listaGrupoEstHasDeber.get(rv_datosGrupoEstudiante.
                                getChildAdapterPosition(v)).getGrupo_estudiante_idgrupo_estudiante();


                        id_GrupoEst_has_deber = listaGrupoEstHasDeber.get(rv_datosGrupoEstudiante.
                                getChildAdapterPosition(v)).getId_GE_H_D();

                        Bundle args1 = new Bundle();
                        args1.putInt("iddocente", iddocente);
                        args1.putInt("idgrupo", idgrupo);
                        args1.putInt("idgrupoesthasdeber", id_GrupoEst_has_deber);

                        showNotasGrupoEstudianteFragment = new ShowNotasGrupoEstudianteFragment();
                        showNotasGrupoEstudianteFragment.setArguments(args1);

                        getFragmentManager().beginTransaction().replace(R.id.fl_contenedor_notas, showNotasGrupoEstudianteFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        Toast.makeText(getContext(), "id grupo estudiante: " + id_GrupoEst_rv, Toast.LENGTH_LONG).show();

                    }
                });
                rv_datosGrupoEstudiante.setVisibility(View.VISIBLE);
                rv_datosGrupoEstudiante.setAdapter(grupos_estudiante_has_deberAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("error", response.toString());

                Toast.makeText(getContext(), "No se ha podido establecer conexión: " + response.toString(), Toast.LENGTH_LONG).show();

                progreso.hide();
            }
            //System.out.println("Lista listaDeberes_full: " + listaDeberes_full.toString());

        }//flag="1"


    }//********************************************


    //***********************************
    public void listarGrupoEstudiantes() {

        String url_lh = Globals.url;

        String url = "http://" + url_lh + "/proyecto_dconfo_v1/12wsJSONConsultar_Lista_Grupo_Est.php?idgrupo=" + idgrupo + "&iddocente=" + iddocente;

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

                        listaGrupoEstudiantes = new ArrayList<>();
                        Grupo_Estudiantes grupo_estudiantes;

                        // Process the JSON
                        try {
                            // Get the JSON array
                            //JSONArray array = response.getJSONArray("students");
                            JSONArray array = response.optJSONArray("grupo_estudiante");

                            // Loop through the array elements
                            for (int i = 0; i < array.length(); i++) {
                                // curso = new Curso();
                                // JSONObject jsonObject = null;
                                // jsonObject = json.getJSONObject(i);

                                // Get current json object
                                JSONObject student = array.getJSONObject(i);
                                grupo_estudiantes = new Grupo_Estudiantes();
                                JSONObject jsonObject = null;
                                jsonObject = array.getJSONObject(i);
                                grupo_estudiantes.setIdGrupoEstudiantes(jsonObject.optInt("idgrupo_estudiante"));
                                grupo_estudiantes.setNameGrupoEstudiantes(jsonObject.optString("name_grupo_estudiante"));

                                listaGrupoEstudiantes.add(grupo_estudiantes);
                            }

                            listaStringGrupoEstudiantes = new ArrayList<>();
                            // listaStringEstudiantes.add("Seleccione Id Estudiante");
                            for (int i = 0; i < listaGrupoEstudiantes.size(); i++) {
                                listaStringGrupoEstudiantes.add(listaGrupoEstudiantes.get(i).getIdGrupoEstudiantes().toString() + " - " + listaGrupoEstudiantes.get(i).getNameGrupoEstudiantes());
                            }

                            // listaIdEstudiantes.add(0000);

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listaStringGrupoEstudiantes);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sp_Grupo_Estudiantes.setAdapter(adapter);
                            sp_Grupo_Estudiantes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position != -1) {
                                        //listaIdEstudiantes.add(listaEstudiantes.get(position - 1).getIdestudiante());
                                        // listaIdEstudiantes.add(listaEstudiantes.get(position).getIdestudiante());
                                        edt_idGrupoEstudiante.setText(listaGrupoEstudiantes.get(position).getIdGrupoEstudiantes().toString());

                                        // idestudiante = Integer.parseInt(edt_idGrupoEstudiante.getText().toString());
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
