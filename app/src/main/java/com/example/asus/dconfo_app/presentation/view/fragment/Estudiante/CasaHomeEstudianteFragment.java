package com.example.asus.dconfo_app.presentation.view.fragment.Estudiante;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.domain.model.DeberEstudiante;
import com.example.asus.dconfo_app.domain.model.EjercicioG1;
import com.example.asus.dconfo_app.domain.model.EjercicioG2;
import com.example.asus.dconfo_app.domain.model.EjercicioG2HasImagen;
import com.example.asus.dconfo_app.domain.model.EjercicioG2HasLetrag2;
import com.example.asus.dconfo_app.domain.model.Estudiante;
import com.example.asus.dconfo_app.domain.model.VolleySingleton;
import com.example.asus.dconfo_app.helpers.Globals;
import com.example.asus.dconfo_app.presentation.view.adapter.DeberesEstudianteAdapter;
import com.example.asus.dconfo_app.presentation.view.fragment.Estudiante.fonico.Tipo1FonicoFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.Estudiante.fonico.Tipo2FonicoFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.Estudiante.silabico.Tipo1silabicoEstudianteFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.Estudiante.silabico.Tipo2silabicoEstudianteFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CasaHomeEstudianteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CasaHomeEstudianteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CasaHomeEstudianteFragment extends Fragment implements Response.Listener<JSONObject>,
        Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String letraInicial;
    private String letraFinal;

    private String letrainicial;
    private String letrafinal;

    private RecyclerView rv_misDeberes;

    private EjercicioG2HasImagen ejercicioG2HasImagen;
    private EjercicioG2HasLetrag2 ejercicioG2HasLetrag2;
    private EjercicioG2 ejercicioG2;

    String nameestudiante = "";
    int idestudiante = 0;
    int IdEjercicioG1;
    int IdEjercicioG2;

    int ejerpos2;
    int tipo;

    JsonObjectRequest jsonObjectRequest;
    ArrayList<DeberEstudiante> listaDeberes;
    ArrayList<DeberEstudiante> listaDeberes_full;
    ArrayList<EjercicioG1> listaEjerciciosG1;
    ArrayList<EjercicioG2> listaEjerciciosG2;//*****************
    ArrayList<Integer> listaEjercicios_g1;
    ArrayList<Integer> listaEjercicios_g2;
    ArrayList<Integer> listaEjercicios_total;
    ArrayList<Integer> listaIdActividadEjercicios;

    ArrayList<Integer> listaIdEjercicioG2;

    ArrayList<String> listaTipoDeber;
    ArrayList<String> listaTipoDeber2;

    private int idEjercicio;
    private Bundle bundle_t2 = new Bundle();
    ;

    private String buscar = "deber";
    int flag = 0;
    int flag2 = 0;
    boolean puedeg1 = false;
    boolean puedeg2 = false;
    boolean flag_cws = false;

    int idDeber;
    TextView txt_name;
    TextView txt_listPendientes;
    TextView txt_listHechas;

    JSONArray jsonArray1;

    private ProgressDialog progreso;

    private OnFragmentInteractionListener mListener;

    public CasaHomeEstudianteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CasaHomeEstudianteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CasaHomeEstudianteFragment newInstance(String param1, String param2) {
        CasaHomeEstudianteFragment fragment = new CasaHomeEstudianteFragment();
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
        View view = inflater.inflate(R.layout.fragment_casa_home_estudiante, container, false);

        progreso = new ProgressDialog(getActivity());

        listaDeberes = new ArrayList<>();
        listaDeberes_full = new ArrayList<>();
        listaIdActividadEjercicios = new ArrayList<>();
        listaEjerciciosG1 = new ArrayList<>();
        listaEjerciciosG2 = new ArrayList<>();
        listaEjercicios_g1 = new ArrayList<>();
        listaEjercicios_g2 = new ArrayList<>();
        listaEjercicios_total = new ArrayList<>();
        listaTipoDeber = new ArrayList<>();
        listaTipoDeber2 = new ArrayList<>();
        listaIdEjercicioG2 = new ArrayList<>();

        txt_listPendientes = (TextView) view.findViewById(R.id.txt_estudiante_home_lista_tareas);
        txt_name = (TextView) view.findViewById(R.id.txt_estudiante_home_est_name);
        txt_listHechas = (TextView) view.findViewById(R.id.txt_estudiante_home_lista_tareas_hechas);


        rv_misDeberes = (RecyclerView) view.findViewById(R.id.rcv_EstudianteListaDeberes_CHE);
        rv_misDeberes.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_misDeberes.setHasFixedSize(true);

        nameestudiante = getArguments().getString("nameEstudiante");
        txt_name.setText(nameestudiante);
        idestudiante = getArguments().getInt("idEstudiante");
        //idestudiante = 0;

        bundle_t2.putString("nameEstudiante", nameestudiante);
        bundle_t2.putInt("idEstudiante", idestudiante);
        System.out.println("id del estudiante****************" + idestudiante);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Nombre: "+ nameestudiante +" - id: " + idestudiante);

        if (flag_cws == false) {

            cargarWebService();
            //crearRecyclerview();
        }
        //cargarWebService1();


        return view;
    }

    private void cargarWebService() {
        progreso.setMessage("Cargando...");
        progreso.show();
        flag_cws = true;

        if (buscar.equals("deber")) {
            String url_lh = Globals.url;
            //String url = "http://" + url_lh + "/proyecto_dconfo_v1/wsJSONConsultarListaDeberesEst.php?estudiante_idestudiante=" + idestudiante;
            String url = "http://" + url_lh + "/proyecto_dconfo_v1/8wsJSONConsultarListaDeberesEst.php?estudiante_idestudiante="+idestudiante;
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);

            VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);//p21
        } else if (buscar.equals("eje1")) {//NO SE USA************************************************************
           /* String url_lh = Globals.url;
            String url = "http://" + url_lh + "/proyecto_dconfo_v1/wsJSONConsultarEjercicio.php?idEjercicioG1="
                    + IdEjercicioG1;
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);//p21*/
        } else if (buscar.equals("eje2")) {
            String url_lh = Globals.url;
            //String url = "http://" + url_lh + "/proyecto_dconfo_v1/wsJSONConsultarEjercicioFonico1.php?idEjercicioG2=" + IdEjercicioG2;
            String url = "http://" + url_lh + "/proyecto_dconfo_v1/9wsJSONConsultarEjercicioEstudiante.php?idEjercicioG2="+IdEjercicioG2;
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);//p21
        } else if (buscar.equals("eje3")) {//**************AGREGADO
            String url_lh = Globals.url;
            // String url = "http://" + url_lh + "/proyecto_dconfo_v1/wsJSONConsultarListaEjerG2_DET.php?idejercicioG2=" + idEjercicio;
            String url = "http://" + url_lh + "/proyecto_dconfo_v1/10wsJSONConsultarListaEjerG2_H_Img.php?idejercicioG2="+ejerpos2;
            url = url.replace(" ", "%20");
            //Toast.makeText(getContext(), "consultar lista det imagenes: ", Toast.LENGTH_LONG).show();
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);//p21
        } else if (buscar.equals("eje4")) {
            String url_lh = Globals.url;
            //String url = "http://" + url_lh + "/proyecto_dconfo_v1/wsJSON__ConsultarListaEjerG2_Has_Letra.php?idejercicioG2=" + idEjercicio;
            String url = "http://" + url_lh + "/proyecto_dconfo_v1/11wsJSON__ConsultarListaEjerG2_Has_Letra.php?idejercicioG2="+ejerpos2;
            url = url.replace(" ", "%20");
            //Toast.makeText(getContext(), "consultar lista det letras: ", Toast.LENGTH_LONG).show();
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);//p21
        }
        //Toast.makeText(getContext(), "LISTA EJERCICIOS DOC.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getContext(), "ERROR-CASAHOME" + error.toString(), Toast.LENGTH_LONG).show();
        System.out.println("////////////////eeeerrrrrr////////////");
        if (buscar.equals("deber")) {
            Log.d("-ERROR-CASAHOME-deber: ", error.toString());
            notificar();
        } else if (buscar.equals("eje1")) {
            Log.d("-ERROR-CASAHOME-eje1: ", error.toString());
        } else if (buscar.equals("eje2")) {
            Log.d("-ERROR-CASAHOME-eje2: ", error.toString());
        } else if (buscar.equals("eje3")) {
            Log.d("-ERROR-CASAHOME-eje3: ", error.toString());
        } else if (buscar.equals("eje4")) {
            Log.d("-ERROR-CASAHOME-eje4: ", error.toString());
        }

        //progreso.hide();
    }

    @Override
    public void onResponse(JSONObject response) {

        //Toast.makeText(getContext(), "Mensaje: " + response.toString(), Toast.LENGTH_SHORT).show();
        progreso.hide();
        if (buscar.equals("deber")) {//deber

            DeberEstudiante deberEstudiante = null;
            DeberEstudiante deberEstudiante1 = null;
            EjercicioG1 ejercicioG1 = null;
            EjercicioG2 ejercicioG2 = null;

            JSONArray json = response.optJSONArray("deber");

            JSONArray json1 = response.optJSONArray("deber1");

            //jsonArray1 = response.optJSONArray("ejerciciog1");


            try {
                for (int i = 0; i < json.length(); i++) {
                    deberEstudiante = new DeberEstudiante();
                    JSONObject jsonObject = null;
                    jsonObject = json.getJSONObject(i);
                    // jsonObject = new JSONObject(response);
                    deberEstudiante.setIdEjercicio2(jsonObject.optInt("EjercicioG2_idEjercicioG2"));
                    deberEstudiante.setFechaDeber(jsonObject.optString("fechaestudiante_has_Deber"));
                    deberEstudiante.setTipoDeber(jsonObject.optString("tipoDeber"));
                    deberEstudiante.setIdDocente(jsonObject.optInt("docente_iddocente"));
                    deberEstudiante.setIdCalificacion(jsonObject.optInt("calificacionestudiante_has_Deber"));
                    deberEstudiante.setIdEstHasDeber(jsonObject.optInt("id_estudiante_has_Debercol"));
                    listaDeberes.add(deberEstudiante);
                    listaEjercicios_g2.add(deberEstudiante.getIdEjercicio2());

                }
                //buscar="eje2";
                puedeg2 = true;
                crearListaIdActividadEjercicio();
                txt_listPendientes.setText(String.valueOf(listaDeberes.size()));
                // System.out.println("************listaDeberes: " + listaDeberes.size());
                //System.out.println("************listaIdEjercicio: " + listaEjercicios_g2.toString());


            } catch (JSONException e) {
                e.printStackTrace();
            }//catch

            try {
                for (int i = 0; i < json1.length(); i++) {


                    deberEstudiante1 = new DeberEstudiante();
                    JSONObject jsonObject1 = null;
                    jsonObject1 = json1.getJSONObject(i);
                    // jsonObject = new JSONObject(response);
                    deberEstudiante1.setIdEjercicio2(jsonObject1.optInt("EjercicioG2_idEjercicioG2"));
                    deberEstudiante1.setFechaDeber(jsonObject1.optString("fechaestudiante_has_Deber"));
                    deberEstudiante1.setTipoDeber(jsonObject1.optString("tipoDeber"));
                    deberEstudiante1.setIdDocente(jsonObject1.optInt("docente_iddocente"));
                    deberEstudiante1.setIdCalificacion(jsonObject1.optInt("calificacionestudiante_has_Deber"));
                    deberEstudiante1.setIdEstHasDeber(jsonObject1.optInt("id_estudiante_has_Debercol"));
                    listaDeberes_full.add(deberEstudiante1);


                }

              //  if (listaDeberes_full.size()==0){

               // }

                txt_listHechas.setText(String.valueOf(listaDeberes_full.size() - listaDeberes.size()));
                // System.out.println("************listaDeberes: " + listaDeberes.size());
                //System.out.println("************listaIdEjercicio: " + listaEjercicios_g2.toString());


            } catch (JSONException e) {
                e.printStackTrace();
            }//catch

        }//deber
        else if (buscar.equals("eje1")) {//eje1

            EjercicioG1 ejercicioG1 = null;
            JSONArray json = response.optJSONArray("ejerciciog1");
            System.out.println("json G1: " + response.toString());
            flag++;
            System.out.println("flag: " + flag);

            try {
                for (int i = 0; i < json.length(); i++) {
                    ejercicioG1 = new EjercicioG1();
                    JSONObject jsonObject = null;
                    jsonObject = json.getJSONObject(i);
                    // jsonObject = new JSONObject(response);
                    ejercicioG1.setIdEjercicio(jsonObject.optInt("idEjercicioG1"));
                    ejercicioG1.setIdActividad(jsonObject.optInt("Tipo_Actividad_idActividad"));
                    //System.out.println("id del ejercicio: " + ejercicioG1.getIdEjercicio());
                    listaIdActividadEjercicios.add(ejercicioG1.getIdActividad());

                    listaEjerciciosG1.add(ejercicioG1);
                }

                //buscar="eje2";
                //cargarWebService();


            } catch (JSONException e) {
                e.printStackTrace();
            }//catch

            crearListaIdActividadEjercicio();

            // System.out.println("listaIdActividadEjercicio: " + listaIdActividadEjercicios.toString());
            puedeg1 = true;
            response = null;
            if (flag == listaEjercicios_g1.size()) {
                puedeg2 = true;
                crearListaIdActividadEjercicioG2();
            }


            //System.out.println("lista Ejercicio --: " + listaEjerciciosG1.get(0).getIdEjercicio());

        }//eje1
        else if (buscar.equals("eje2")) {//eje2

            ejercicioG2 = null;
            JSONArray json;
            flag2++;
            // System.out.println("flag2: " + flag2);

            json = response.optJSONArray("ejerciciog2");
            // System.out.println("json G2: " + response.toString());
            //listaEjerciciosG2 = new ArrayList<>();

            try {
                for (int i = 0; i < json.length(); i++) {
                    ejercicioG2 = new EjercicioG2();
                    JSONObject jsonObject = null;
                    jsonObject = json.getJSONObject(i);
                    // jsonObject = new JSONObject(response);
                    ejercicioG2.setIdEjercicioG2(jsonObject.optInt("idEjercicioG2"));
                    ejercicioG2.setIdActividad(jsonObject.optInt("Tipo_Actividad_idActividad"));
                    ejercicioG2.setIdTipo(jsonObject.optInt("Tipo_idTipo"));
                    ejercicioG2.setNameEjercicioG2(jsonObject.optString("nameEjercicioG2"));
                    ejercicioG2.setCantidadLexemas(jsonObject.optString("cantidadValidadEjercicio"));
                    ejercicioG2.setOracion(jsonObject.optString("oracionEjercicio"));
                    ejercicioG2.setLetra_inicial_EjercicioG2(jsonObject.optString("letra_inicial_EjercicioG2"));
                    ejercicioG2.setLetra_final_EjercicioG2(jsonObject.optString("letra_final_EjercicioG2"));
                    //System.out.println("id del ejercicio: " + ejercicioG1.getIdEjercicio());
                    listaIdActividadEjercicios.add(ejercicioG2.getIdActividad());
                    listaIdEjercicioG2.add(ejercicioG2.getIdEjercicioG2());
                    listaEjerciciosG2.add(ejercicioG2);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }//catch

            if (flag2 != listaEjercicios_g2.size()) {
                puedeg2 = true;
                crearListaIdActividadEjercicio();
            }

            // System.out.println("********listaIdActividadEjercicio G2*********: " + listaIdActividadEjercicios.get(0));
            // System.out.println("********listaIdEjercicios G2*********: " + listaIdEjercicioG2.size());
            puedeg2 = true;

            if (listaDeberes.size() == listaIdActividadEjercicios.size()) {
                crearRecyclerview();
            }

            // crearListaIdActividadEjercicioG2();


            //DeberesEstudianteAdapter deberesEstudianteAdapter = new DeberesEstudianteAdapter(listaDeberes,listaIdActividadEjercicios);

            //crearListaIdActividadEjercicio();
            //cargarWebService();

            //System.out.println("lista Ejercicio --: " + listaEjerciciosG1.get(0).getIdEjercicio());

        }//eje2
        else if (buscar.equals("eje3")) {//------------------------------------------------------------------------------------g3

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
                Tipo1FonicoFragment tipo1FonicoFragment = new Tipo1FonicoFragment();
                Tipo2FonicoFragment tipo2FonicoFragment = new Tipo2FonicoFragment();

                Tipo2silabicoEstudianteFragment tipo2silabicoEstudianteFragment = new Tipo2silabicoEstudianteFragment();

                // ArrayList<Integer> listaIDimagenes = new ArrayList<>();
                //Bundle bundle = new Bundle();
                // bundle_t2 = new Bundle();


                int[] listaidImagenes = new int[listaDEjerciciosg2HI.size()];
                for (int i = 0; i < listaDEjerciciosg2HI.size(); i++) {
                    // listaIDimagenes.add(listaDEjerciciosg2HI.get(i).getIdImagen());
                    listaidImagenes[i] = listaDEjerciciosg2HI.get(i).getIdImagen();
                    bundle_t2.putInt("idejercicio" + (i + 1) + "", listaDEjerciciosg2HI.get(i).getIdImagen());
                    bundle_t2.putInt("colejercicio" + (i + 1) + "", listaDEjerciciosg2HI.get(i).getColumnaImagen());
                    System.out.println("columna imagen g2: " + listaDEjerciciosg2HI.get(i).getColumnaImagen());
                    bundle_t2.putInt("filejercicio" + (i + 1) + "", listaDEjerciciosg2HI.get(i).getFilaImagen());
                }

               /* System.out.println("cadena imagenes: " + listaidImagenes.toString());
                System.out.println("bundle1: " + bundle.get("idejercicio1"));
                System.out.println("bundle2: " + bundle.get("idejercicio2"));
                System.out.println("bundle3: " + bundle.get("idejercicio3"));
                System.out.println("bundle4: " + bundle.get("idejercicio4"));*/

                String usuario = "estudiante";

                bundle_t2.putInt("idejercicio", idEjercicio);
                bundle_t2.putString("letrainicial", letraInicial);

                bundle_t2.putString("usuario", usuario);//**************Nuevo 250619///////////////////////
                //bundle.putIntArray("cadenaidimagenes", listaidImagenes);

                if (tipo == 1) {
                    tipo1FonicoFragment.setArguments(bundle_t2);
                    // inicioEjercicioFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.container_HomeEstudiante, tipo1FonicoFragment)
                            //getFragmentManager().beginTransaction().replace(R.id.container_HomeEstudiante, inicioEjercicioFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();
                }
                if (tipo == 2) {
                    buscar = "eje4";
                    cargarWebService();
                }
                if (tipo == 6) {
                    System.out.println("BUNDLE_T2: " + bundle_t2.toString());
                    Toast.makeText(getContext(), "idejercicio: " + bundle_t2.toString(), Toast.LENGTH_SHORT).show();
                    tipo2silabicoEstudianteFragment.setArguments(bundle_t2);
                    // inicioEjercicioFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.container_HomeEstudiante, tipo2silabicoEstudianteFragment)
                            //getFragmentManager().beginTransaction().replace(R.id.container_HomeEstudiante, inicioEjercicioFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();
                }

              /*  if (tipoEjercicioG2 == 2) {
                    tipo2FonicoFragment.setArguments(bundle_t2);
                    // inicioEjercicioFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.container_HomeEstudiante, tipo2FonicoFragment)
                            //getFragmentManager().beginTransaction().replace(R.id.container_HomeEstudiante, inicioEjercicioFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();
                }*/


            } catch (JSONException e) {
                e.printStackTrace();
                //Toast.makeText(getContext(), "No se ha podido establecer conexión: " + response.toString(), Toast.LENGTH_LONG).show();

            }


        }//eje3
        else if (buscar.equals("eje4")) {//------------------------------------------------------------------------------------g3

            Toast.makeText(getContext(), "grupo g4 activo: ", Toast.LENGTH_LONG).show();

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
                Tipo2FonicoFragment tipo2FonicoFragment = new Tipo2FonicoFragment();

                // ArrayList<Integer> listaIDimagenes = new ArrayList<>();
                //Bundle bundle = new Bundle();
                Bundle bundle2_t2 = new Bundle();

                int[] listaidImagenes = new int[listaDEjerciciosg2HL.size()];
                for (int i = 0; i < listaDEjerciciosg2HL.size(); i++) {
                    // listaIDimagenes.add(listaDEjerciciosg2HI.get(i).getIdImagen());

                    bundle_t2.putInt("idejercicioHletra" + (i + 1) + "", listaDEjerciciosg2HL.get(i).getId_EjercicioG2());
                    bundle_t2.putString("letra" + (i + 1) + "", listaDEjerciciosg2HL.get(i).getLetra());
                    bundle_t2.putInt("filaletra" + (i + 1) + "", listaDEjerciciosg2HL.get(i).getFila_Eg2H_Lg2());
                    bundle_t2.putInt("colletra" + (i + 1) + "", listaDEjerciciosg2HL.get(i).getCol_Eg2H_Lge());

                    System.out.println("columna letra: " + listaDEjerciciosg2HL.get(i).getCol_Eg2H_Lge());
                }


                if (tipo == 2) {
                    //tipo2FonicoFragment.setArguments(bundle_t2);
                    tipo2FonicoFragment.setArguments(bundle_t2);
                    // inicioEjercicioFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.container_HomeEstudiante, tipo2FonicoFragment)
                            //getFragmentManager().beginTransaction().replace(R.id.container_HomeEstudiante, inicioEjercicioFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();
                }


            } catch (JSONException e) {
                e.printStackTrace();
                //Toast.makeText(getContext(), "No se ha podido establecer conexión: " + response.toString(), Toast.LENGTH_LONG).show();

            }


        }//eje4


    }//onResponse

    private void notificar() {

            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle("No tienes Deberes!!!");


           /* Drawable drawable = ll_intento.getResources().getDrawable(R.drawable.rana_gif);

            alertDialog.setIcon(drawable);*/
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
            alertDialog.show();

    }

    private void crearRecyclerview() {
        DeberesEstudianteAdapter deberesEstudianteAdapter =
                new DeberesEstudianteAdapter(listaDeberes, listaIdActividadEjercicios, listaIdEjercicioG2, listaEjerciciosG2);
        deberesEstudianteAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ejerpos2 = listaDeberes.get(rv_misDeberes.getChildAdapterPosition(v)).getIdEjercicio2();
                tipo = listaEjerciciosG2.get(rv_misDeberes.getChildAdapterPosition(v)).getIdTipo();
                int actividad = listaEjerciciosG2.get(rv_misDeberes.getChildAdapterPosition(v)).getIdActividad();

                letraInicial = listaEjerciciosG2.get(rv_misDeberes.getChildAdapterPosition(v)).getLetra_inicial_EjercicioG2();
                letraFinal = listaEjerciciosG2.get(rv_misDeberes.getChildAdapterPosition(v)).getLetra_final_EjercicioG2();

                letrainicial = listaEjerciciosG2.get(rv_misDeberes.getChildAdapterPosition(v)).getLetra_inicial_EjercicioG2();
                letrafinal = listaEjerciciosG2.get(rv_misDeberes.getChildAdapterPosition(v)).getLetra_final_EjercicioG2();

                idDeber = listaDeberes.get(rv_misDeberes.getChildAdapterPosition(v)).getIdEstHasDeber();//***********


                String cantLex = listaEjerciciosG2.get(rv_misDeberes.getChildAdapterPosition(v)).getCantidadLexemas();
                String oracion = listaEjerciciosG2.get(rv_misDeberes.getChildAdapterPosition(v)).getOracion();
                Toast.makeText(getContext(), "Cant lexe: " + cantLex, Toast.LENGTH_LONG).show();
                Toast.makeText(getContext(), "oracion: " + oracion, Toast.LENGTH_LONG).show();

                String tipo2 = String.valueOf(ejerpos2);

                String g1 = "g1";
                String g2 = "g2";


                // int ejertipo = listaDeberes.get(rv_misDeberes.getChildAdapterPosition(v)).getIdEjercicio();
                Tipo1EstudianteFragment tipo1EstudianteFragment = new Tipo1EstudianteFragment();
                Tipo2EstudianteFragment tipo2EstudianteFragment = new Tipo2EstudianteFragment();

                Tipo1FonicoFragment tipo1FonicoFragment = new Tipo1FonicoFragment();
                Tipo2FonicoFragment tipo2FonicoFragment = new Tipo2FonicoFragment();

                Tipo1silabicoEstudianteFragment tipo1silabicoEstudianteFragment = new Tipo1silabicoEstudianteFragment();
                Tipo2silabicoEstudianteFragment tipo2silabicoEstudianteFragment = new Tipo2silabicoEstudianteFragment();

                InicioEjercicioFragment inicioEjercicioFragment = new InicioEjercicioFragment();


                Bundle bundle1 = new Bundle();
                bundle1.putInt("idejercicio", ejerpos2);

                bundle1.putString("letrainicial", letraInicial);
                bundle1.putString("letrafinal", letraFinal);
                if (!letrainicial.equals(null)) {
                    bundle_t2.putString("letrainicial", letrainicial);
                    System.out.println("letraInicial *******: " + letrainicial);
                }
                if (!letrafinal.equals(null)) {
                    bundle_t2.putString("letrafinal", letrafinal);
                    System.out.println("letraFinal *******: " + letrafinal);
                }

                bundle_t2.putInt("idesthasdeber", idDeber);


                Bundle bundle2 = new Bundle();
                Bundle bundle3 = new Bundle();
                Bundle bundle4 = new Bundle();

                String usuario = "estudiante";

                Bundle bundle = new Bundle();
                bundle.putInt("idejercicio", ejerpos2);
                bundle.putInt("idEstudiante", idestudiante);
                bundle.putString("cantLexemas", cantLex);
                bundle.putString("oracion", oracion);

                bundle.putString("usuario", usuario);//**************Nuevo 250619///////////////////////
                bundle.putInt("idesthasdeber", idDeber);

                if (tipo == 1) {
                   /* tipo1FonicoFragment.setArguments(bundle);
                    // inicioEjercicioFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.container_HomeEstudiante, tipo1FonicoFragment)
                            //getFragmentManager().beginTransaction().replace(R.id.container_HomeEstudiante, inicioEjercicioFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();*/
                    buscar = "eje3";
                    cargarWebService();
                } else if (tipo == 2) {
                   /* tipo2FonicoFragment.setArguments(bundle);
                    // inicioEjercicioFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.container_HomeEstudiante, tipo2FonicoFragment)
                            //getFragmentManager().beginTransaction().replace(R.id.container_HomeEstudiante, inicioEjercicioFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();*/
                    buscar = "eje3";
                    cargarWebService();
                } else if (tipo == 3) {//************listo
                    System.out.println("BUNDLE_T2: " + bundle.toString());
                    tipo1EstudianteFragment.setArguments(bundle);
                    // tipo1EstudianteFragment.setArguments(bundle_t2);
                    // inicioEjercicioFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.container_HomeEstudiante, tipo1EstudianteFragment)
                            //getFragmentManager().beginTransaction().replace(R.id.container_HomeEstudiante, inicioEjercicioFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();
                } else if (tipo == 4) {//************listo
                    //tipo1EstudianteFragment.setArguments(bundle);
                    tipo2EstudianteFragment.setArguments(bundle);
                    //tipo2EstudianteFragment.setArguments(bundle_t2);
                    //getFragmentManager().beginTransaction().replace(R.id.container_HomeEstudiante, tipo1EstudianteFragment)
                    getFragmentManager().beginTransaction().replace(R.id.container_HomeEstudiante, tipo2EstudianteFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();
                } else if (tipo == 5) {//************listo
                    //tipo1EstudianteFragment.setArguments(bundle);
                    tipo1silabicoEstudianteFragment.setArguments(bundle);
                    //tipo1silabicoEstudianteFragment.setArguments(bundle_t2);
                    //getFragmentManager().beginTransaction().replace(R.id.container_HomeEstudiante, tipo1EstudianteFragment)
                    getFragmentManager().beginTransaction().replace(R.id.container_HomeEstudiante, tipo1silabicoEstudianteFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();
                } else if (tipo == 6) {//************listo
                    buscar = "eje3";
                    cargarWebService();
                 /*   //tipo1EstudianteFragment.setArguments(bundle);
                    tipo2silabicoEstudianteFragment.setArguments(bundle);
                    //getFragmentManager().beginTransaction().replace(R.id.container_HomeEstudiante, tipo1EstudianteFragment)
                    getFragmentManager().beginTransaction().replace(R.id.container_HomeEstudiante, tipo2silabicoEstudianteFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();*/
                }


            }
        });

        rv_misDeberes.setAdapter(deberesEstudianteAdapter);
    }

    private void crearListaIdActividadEjercicio() {


        if (puedeg2 == true && flag2 < listaEjercicios_g2.size()) {
            //System.out.println("flag2 met: " + flag2);
            IdEjercicioG2 = listaEjercicios_g2.get(flag2);
            buscar = "eje2";

            cargarWebService();
        }


    }

    private void crearListaIdActividadEjercicioG2() {

        if (puedeg2 == true && flag2 < listaEjercicios_g2.size()) {
            System.out.println("flag2 met: " + flag2);
            IdEjercicioG2 = listaEjercicios_g2.get(flag2);
            buscar = "eje2";

            cargarWebService();
        }


       /* if (puedeg2 == true) {
            for (int i = 0; i < listaEjercicios_g2.size(); i++) {

                IdEjercicioG2 = listaEjercicios_g2.get(i);
                System.out.println("IdEjercicio g2*******: " + IdEjercicioG2);

                buscar = "eje2";

                cargarWebService();
                puedeg2 = false;

            }

        }*/
    }


    private void cargarWebService1() {

        String url_lh = Globals.url;
        idEjercicio = 27;
        String url = "http://" + url_lh + "/proyecto_dconfo_v1/wsJSONConsultarEjercicio.php?idEjercicioG1="
                + idEjercicio;
        // + idestudiante + "&docente_iddocente=" + 220;

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);

        final int MY_DEFAULT_TIMEOUT = 15000;
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);//p21
        Toast.makeText(getContext(), "ejercicio a buscar", Toast.LENGTH_LONG).show();
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
