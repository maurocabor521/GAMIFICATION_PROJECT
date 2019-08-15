package com.example.asus.dconfo_app.presentation.view.fragment.docente.tipoFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.domain.model.Curso;
import com.example.asus.dconfo_app.domain.model.EjercicioG1;
import com.example.asus.dconfo_app.domain.model.EjercicioG2;
import com.example.asus.dconfo_app.domain.model.EjercicioG2HasImagen;
import com.example.asus.dconfo_app.domain.model.EjercicioG2HasLetrag2;
import com.example.asus.dconfo_app.domain.model.Grupo;
import com.example.asus.dconfo_app.domain.model.Imagen;
import com.example.asus.dconfo_app.domain.model.VolleySingleton;
import com.example.asus.dconfo_app.helpers.Globals;
import com.example.asus.dconfo_app.presentation.view.activity.docente.HomeDocenteActivity;
import com.example.asus.dconfo_app.presentation.view.activity.docente.ManageCursosDocenteActivity;
import com.example.asus.dconfo_app.presentation.view.adapter.GruposDocenteAdapter;
import com.example.asus.dconfo_app.presentation.view.adapter.TipoEjerciciosActividadDocenteAdapter;
import com.example.asus.dconfo_app.presentation.view.adapter.TipoEjerciciosDocenteAdapter;
import com.example.asus.dconfo_app.presentation.view.fragment.Estudiante.Tipo1EstudianteFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.Estudiante.Tipo2EstudianteFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.Estudiante.fonico.Tipo1FonicoFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.Estudiante.fonico.Tipo2FonicoFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.Estudiante.silabico.Tipo1silabicoEstudianteFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.Estudiante.silabico.Tipo2silabicoEstudianteFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.administrador.NewCursoFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeTiposFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeTiposFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeTiposFragment extends Fragment
        implements Response.Listener<JSONObject>,
        Response.ErrorListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView rv_tipos;
    private TextToSpeech mTTS;
    String nameDocente = "";
    int idDocente = 0;
    ArrayList<EjercicioG2> listaEjercicios;
    ProgressDialog progreso;
    //******** CONEXIÓN CON WEBSERVICE
    //RequestQueue request;
    JsonObjectRequest jsonObjectRequest;


    Bundle parametros_1 = new Bundle();
    EjercicioG2 ejercicioG2 = null;
    private String buscar;
    private int idactividad;
    private int idtipo;
    private int idejercicio;
    private String cantLexemas;
    private String oracion;
    private String letrainicial;
    private EjercicioG2HasImagen ejercicioG2HasImagen;
    private EjercicioG2HasLetrag2 ejercicioG2HasLetrag2;

    int iddocente;
    int idgrupo;
    String namedocente;
    private RecyclerView rv_tipoLexico;
    ArrayList<Imagen> listaImagenes;
    Tipo1EstudianteFragment tipo1EstudianteFragment=new Tipo1EstudianteFragment();
    Tipo2EstudianteFragment tipo2EstudianteFragment=new Tipo2EstudianteFragment();

    private OnFragmentInteractionListener mListener;

    public static HomeTiposFragment getInstance() {
        return new HomeTiposFragment();
    }

    public HomeTiposFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeTiposFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeTiposFragment newInstance(String param1, String param2) {
        HomeTiposFragment fragment = new HomeTiposFragment();
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

        View view = inflater.inflate(R.layout.fragment_home_tipos, container, false);
        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.home_tipo);
        namedocente = getArguments().getString("namedocente");

        iddocente = getArguments().getInt("iddocente");
        idgrupo = getArguments().getInt("idgrupo");

        progreso = new ProgressDialog(getActivity());

        listaImagenes = new ArrayList<>();
        rv_tipoLexico = (RecyclerView) view.findViewById(R.id.rv_tipos_docente);
        rv_tipoLexico.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rv_tipoLexico.setHasFixedSize(true);
        //rv_tipo1Fonico.setLayoutManager(new LinearLayoutManager(getContext()));
        //rv_tipo1Fonico.setHasFixedSize(true);

        //listarEjercicios_Docente();
        buscar = "listaEje";
        cargarEjerciciosPorActividad();

        listaEjercicios = new ArrayList<>();
        return view;
    }


    private void cargarEjerciciosPorActividad() {

        progreso.setMessage("Cargando...");
        progreso.show();

        if (buscar.equals("listaEje")) {

            String iddoc = "20181";
            String url_lh = Globals.url;

            String url = "http://" + url_lh + "/proyecto_dconfo_v1/20wsJSONConsultarListaEjerciciosXactividad.php?iddocente="+iddocente+"&idactividad="+2;
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);

            final int MY_DEFAULT_TIMEOUT = 15000;
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    MY_DEFAULT_TIMEOUT,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            // request.add(jsonObjectRequest);
            VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
        } else if (buscar.equals("1")) {//**************AGREGADO
            String url_lh = Globals.url;

            String url = "http://" + url_lh + "/proyecto_dconfo_v1/10wsJSONConsultarListaEjerG2_H_Img.php?idejercicioG2=" + idejercicio;
            url = url.replace(" ", "%20");

            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);//p21
        } else if (buscar.equals("2")) {
            String url_lh = Globals.url;

            String url = "http://" + url_lh + "/proyecto_dconfo_v1/11wsJSON__ConsultarListaEjerG2_Has_Letra.php?idejercicioG2=" + idejercicio;
            url = url.replace(" ", "%20");

            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);//p21
        }

        // Toast.makeText(getContext(), "LISTA EJERCICIOS DOC.", Toast.LENGTH_LONG).show();
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

        if (buscar.equals("listaEje")) {//listaEje

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
                    ejercicioG2.setOracion(jsonObject.optString("oracionEjercicio"));
                    ejercicioG2.setCantidadLexemas(jsonObject.optString("cantidadValidadEjercicio"));
                    ejercicioG2.setLetra_inicial_EjercicioG2(jsonObject.optString("letra_inicial_EjercicioG2"));

                    listaEjercicios.add(ejercicioG2);

//idgrupo,namegrupo,curso_idcurso,curso_Instituto_idInstituto
                }
                TipoEjerciciosActividadDocenteAdapter tipoEjerciciosActividadDocenteAdapter = new TipoEjerciciosActividadDocenteAdapter(listaEjercicios, 2);
                tipoEjerciciosActividadDocenteAdapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        idejercicio = listaEjercicios.get(rv_tipoLexico.
                                getChildAdapterPosition(v)).getIdEjercicioG2();

                        Toast.makeText(getContext(), "idejercicio: " + idejercicio, Toast.LENGTH_LONG).show();

                        idactividad = listaEjercicios.get(rv_tipoLexico.
                                getChildAdapterPosition(v)).getIdActividad();

                        idtipo = listaEjercicios.get(rv_tipoLexico.
                                getChildAdapterPosition(v)).getIdTipo();

                        oracion = listaEjercicios.get(rv_tipoLexico.
                                getChildAdapterPosition(v)).getOracion();

                        cantLexemas = listaEjercicios.get(rv_tipoLexico.
                                getChildAdapterPosition(v)).getCantidadLexemas();

                        letrainicial = listaEjercicios.get(rv_tipoLexico.
                                getChildAdapterPosition(v)).getLetra_inicial_EjercicioG2();




                        // Bundle parametros_1 = new Bundle();

                        String usuario = "docente";

                        parametros_1.putInt("iddocente", iddocente);
                        parametros_1.putInt("idgrupo", idgrupo);
                        parametros_1.putInt("idejercicio", idejercicio);
                        parametros_1.putInt("idactividad", idactividad);
                        parametros_1.putInt("idtipo", idtipo);
                        parametros_1.putString("usuario", usuario);
                        parametros_1.putString("oracion", oracion);
                        parametros_1.putString("cantLexemas", cantLexemas);
                        parametros_1.putString("letrainicial", letrainicial);

                        Toast.makeText(getContext(), "oracion: " + oracion, Toast.LENGTH_LONG).show();

                        if (idtipo == 3) {
                            tipo1EstudianteFragment.setArguments(parametros_1);
                            getFragmentManager().beginTransaction().replace(R.id.container_lexico_ejercicios, tipo1EstudianteFragment)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                    .addToBackStack(null).commit();
                        }
                        if (idtipo == 4) {
                            tipo2EstudianteFragment.setArguments(parametros_1);
                            getFragmentManager().beginTransaction().replace(R.id.container_lexico_ejercicios, tipo2EstudianteFragment)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                    .addToBackStack(null).commit();
                        }


                        //getSupportFragmentManager().beginTransaction().replace(R.id.container_docente_edit_ejer, tipo1LexicoUpdateFragment)
                    }
                });
                System.out.println("lista ejercicios: " + listaEjercicios.size() + " idactividad: " + idactividad);
                rv_tipoLexico.setAdapter(tipoEjerciciosActividadDocenteAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("error", response.toString());

                //Toast.makeText(getApplicationContext(), "No se ha podido establecer conexión: " + response.toString(), Toast.LENGTH_LONG).show();

                progreso.hide();
            }

        }//listaEje

        else if (buscar.equals("1")) {//------------------------------------------------------------------------------------1

            Toast.makeText(getContext(), "grupo g3 activo: ", Toast.LENGTH_LONG).show();

            ejercicioG2HasImagen = null;
            JSONArray json = response.optJSONArray("ejerg2hasimagen");

            ArrayList<EjercicioG2HasImagen> listaDEjerciciosg2HI = new ArrayList<>();
            listaDEjerciciosg2HI = new ArrayList<>();

            try {
                for (int i = 0; i < json.length(); i++) {
                    ejercicioG2HasImagen = new EjercicioG2HasImagen();
                    JSONObject jsonObject = null;
                    jsonObject = json.getJSONObject(i);

                    ejercicioG2HasImagen.setIdEjercicioG2(jsonObject.optInt("EjercicioG2_idEjercicioG2"));
                    ejercicioG2HasImagen.setIdImagen(jsonObject.optInt("Imagen_idImagen_Ejercicio"));
                    ejercicioG2HasImagen.setColumnaImagen(jsonObject.optInt("columna_E_H_I"));
                    ejercicioG2HasImagen.setFilaImagen(jsonObject.optInt("fila_E_h_I"));

                    listaDEjerciciosg2HI.add(ejercicioG2HasImagen);

                }
                int tipoEjercicioG2 = ejercicioG2.getIdTipo();
                //Tipo1FonicoFragment tipo1FonicoFragment = new Tipo1FonicoFragment();
                //Tipo2FonicoFragment tipo2FonicoFragment = new Tipo2FonicoFragment();



                // bundle_t2 = new Bundle();

                int[] listaidImagenes = new int[listaDEjerciciosg2HI.size()];
                for (int i = 0; i < listaDEjerciciosg2HI.size(); i++) {
                    // listaIDimagenes.add(listaDEjerciciosg2HI.get(i).getIdImagen());
                    listaidImagenes[i] = listaDEjerciciosg2HI.get(i).getIdImagen();
                    parametros_1.putInt("idejercicio" + (i + 1) + "", listaDEjerciciosg2HI.get(i).getIdImagen());
                    parametros_1.putInt("colejercicio" + (i + 1) + "", listaDEjerciciosg2HI.get(i).getColumnaImagen());
                    System.out.println("columna imagen g2: " + listaDEjerciciosg2HI.get(i).getColumnaImagen());
                    parametros_1.putInt("filejercicio" + (i + 1) + "", listaDEjerciciosg2HI.get(i).getFilaImagen());
                }

                String usuario = "estudiante";

                parametros_1.putString("usuario", usuario);//**************Nuevo 250619///////////////////////
                //bundle.putIntArray("cadenaidimagenes", listaidImagenes);





            } catch (JSONException e) {
                e.printStackTrace();
                //Toast.makeText(getContext(), "No se ha podido establecer conexión: " + response.toString(), Toast.LENGTH_LONG).show();

            }


        }// 1
        else if (buscar.equals("2")) {//------------------------------------------------------------------------------------g2

            //Toast.makeText(getApplicationContext(), "grupo g4 activo: ", Toast.LENGTH_LONG).show();

            ejercicioG2HasLetrag2 = null;
            JSONArray json = response.optJSONArray("ejerciciog2_has_letrag2");

            ArrayList<EjercicioG2HasLetrag2> listaDEjerciciosg2HL = new ArrayList<>();
            listaDEjerciciosg2HL = new ArrayList<>();

            try {
                for (int i = 0; i < json.length(); i++) {
                    ejercicioG2HasLetrag2 = new EjercicioG2HasLetrag2();
                    JSONObject jsonObject = null;
                    jsonObject = json.getJSONObject(i);

                    ejercicioG2HasLetrag2.setId_EjercicioG2(jsonObject.optInt("EjercicioG2_idEjercicioG2"));
                    ejercicioG2HasLetrag2.setLetra(jsonObject.optString("Letra"));
                    ejercicioG2HasLetrag2.setFila_Eg2H_Lg2(jsonObject.optInt("fila_Eg2H_Lg2"));
                    ejercicioG2HasLetrag2.setCol_Eg2H_Lge(jsonObject.optInt("col_Eg2H_Lge"));


                    listaDEjerciciosg2HL.add(ejercicioG2HasLetrag2);

                }

                int tipoEjercicioG2 = ejercicioG2.getIdTipo();
                System.out.println("lista ejercicio has letra: " + listaDEjerciciosg2HL.size());

                //Tipo1FonicoFragment tipo1FonicoFragment = new Tipo1FonicoFragment();


                // ArrayList<Integer> listaIDimagenes = new ArrayList<>();
                //Bundle bundle = new Bundle();
                Bundle bundle2_t2 = new Bundle();

                int[] listaidImagenes = new int[listaDEjerciciosg2HL.size()];
                for (int i = 0; i < listaDEjerciciosg2HL.size(); i++) {
                    // listaIDimagenes.add(listaDEjerciciosg2HI.get(i).getIdImagen());

                    parametros_1.putInt("idejercicioHletra" + (i + 1) + "", listaDEjerciciosg2HL.get(i).getId_EjercicioG2());
                    parametros_1.putString("letra" + (i + 1) + "", listaDEjerciciosg2HL.get(i).getLetra());
                    parametros_1.putInt("filaletra" + (i + 1) + "", listaDEjerciciosg2HL.get(i).getFila_Eg2H_Lg2());
                    parametros_1.putInt("colletra" + (i + 1) + "", listaDEjerciciosg2HL.get(i).getCol_Eg2H_Lge());

                    System.out.println("columna letra: " + listaDEjerciciosg2HL.get(i).getCol_Eg2H_Lge());
                }





            } catch (JSONException e) {
                e.printStackTrace();
                //Toast.makeText(getContext(), "No se ha podido establecer conexión: " + response.toString(), Toast.LENGTH_LONG).show();

            }


        }// 2


    }//cargarEjerciciosPorActividad()


    //*********************************************************************************************


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
