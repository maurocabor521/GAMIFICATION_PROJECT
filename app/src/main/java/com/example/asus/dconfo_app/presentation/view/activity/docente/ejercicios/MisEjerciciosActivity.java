package com.example.asus.dconfo_app.presentation.view.activity.docente.ejercicios;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.domain.model.EjercicioG2;
import com.example.asus.dconfo_app.domain.model.EjercicioG2HasImagen;
import com.example.asus.dconfo_app.domain.model.EjercicioG2HasLetrag2;
import com.example.asus.dconfo_app.domain.model.VolleySingleton;
import com.example.asus.dconfo_app.helpers.Globals;
import com.example.asus.dconfo_app.presentation.view.adapter.TipoEjerciciosActividadDocenteAdapter;
import com.example.asus.dconfo_app.presentation.view.fragment.Estudiante.Tipo1EstudianteFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.Estudiante.Tipo2EstudianteFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.Estudiante.fonico.Tipo1FonicoFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.Estudiante.fonico.Tipo2FonicoFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.Estudiante.silabico.Tipo1silabicoEstudianteFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.Estudiante.silabico.Tipo2silabicoEstudianteFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.docente.modificarEjercicio.fonicos.Tipo1FonicoUpdateFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.docente.modificarEjercicio.fonicos.Tipo2FonicoUpdateFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.docente.modificarEjercicio.lexicos.Tipo1LexicoUpdateFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.docente.modificarEjercicio.lexicos.Tipo2LexicoUpdateFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.docente.modificarEjercicio.silabicos.Tipo1SilabicoUpdateFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.docente.modificarEjercicio.silabicos.Tipo2SilabicoUpdateFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.docente.tipoFragments.Tipo2Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MisEjerciciosActivity extends AppCompatActivity implements Response.Listener<JSONObject>,
        Response.ErrorListener,
        Tipo1LexicoUpdateFragment.OnFragmentInteractionListener,
        Tipo2LexicoUpdateFragment.OnFragmentInteractionListener,
        Tipo1EstudianteFragment.OnFragmentInteractionListener,
        Tipo2EstudianteFragment.OnFragmentInteractionListener,
        Tipo1FonicoFragment.OnFragmentInteractionListener,
        Tipo1FonicoUpdateFragment.OnFragmentInteractionListener,
        Tipo2FonicoFragment.OnFragmentInteractionListener,
        Tipo2FonicoUpdateFragment.OnFragmentInteractionListener,
        Tipo2silabicoEstudianteFragment.OnFragmentInteractionListener,
        Tipo1silabicoEstudianteFragment.OnFragmentInteractionListener,
        Tipo1SilabicoUpdateFragment.OnFragmentInteractionListener,
        Tipo2SilabicoUpdateFragment.OnFragmentInteractionListener {

    private int idgrupo;
    private int iddocente;
    private int idactividad;
    private int idtipo;
    private int idejercicio;
    private String cantLexemas;
    private String oracion;
    private String letrainicial;

    private String buscar;

    Bundle parametros_1 = new Bundle();

    private EjercicioG2HasImagen ejercicioG2HasImagen;
    private EjercicioG2HasLetrag2 ejercicioG2HasLetrag2;

    private LinearLayout ll_rv_ejercicios;
    private Button btn_fon;
    private Button btn_lex;
    private Button btn_sil;
    private FloatingActionButton fa_btn_btn_modificar;
    private Button btn_crear_actividad;
    private EditText edt_name_actividad;
    private RecyclerView rv_ejercicios_act;

    private LinearLayout ll_ejercicios;

    private FrameLayout fl_mod_ejercicios;

    private EjercicioG2 ejercicioG2 = null;

    private ArrayList<EjercicioG2> listaEjercicios;

    private ProgressDialog progreso;
    private FloatingActionButton fb_nuevo_GrupoEstudiantes;
    //RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    //**********************************************************************************************
    Tipo1FonicoFragment tipo1FonicoFragment;
    Tipo2FonicoFragment tipo2FonicoFragment;

    //Tipo2FonicoFragment tipo2FonicoFragment

    Tipo1EstudianteFragment tipo1EstudianteFragment;
    Tipo2EstudianteFragment tipo2EstudianteFragment;

    Tipo1silabicoEstudianteFragment tipo1silabicoEstudianteFragment;
    Tipo2silabicoEstudianteFragment tipo2silabicoEstudianteFragment;

    Tipo1LexicoUpdateFragment tipo1LexicoUpdateFragment;
    Tipo2LexicoUpdateFragment tipo2LexicoUpdateFragment;

    Tipo1FonicoUpdateFragment tipo1FonicoUpdateFragment;
    Tipo2FonicoUpdateFragment tipo2FonicoUpdateFragment;

    Tipo1SilabicoUpdateFragment tipo1SilabicoUpdateFragment;
    Tipo2SilabicoUpdateFragment tipo2SilabicoUpdateFragment;

    Tipo2Fragment tipo2Fragment;
    //**********************************************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_ejercicios);

        progreso = new ProgressDialog(this);

        ll_rv_ejercicios = (LinearLayout) findViewById(R.id.ll_docente_ejer_rv);
        ll_ejercicios = (LinearLayout) findViewById(R.id.ll_docente_edit_ejer);

        fl_mod_ejercicios = (FrameLayout) findViewById(R.id.container_docente_edit_ejer);


        Intent intent = this.getIntent();
        Bundle datos = intent.getExtras();
        iddocente = datos.getInt("iddocente");
        idgrupo = datos.getInt("idgrupo");
        showToolbar("Mis Ejercicios" + " - " + iddocente, true);

        rv_ejercicios_act = (RecyclerView) findViewById(R.id.rv_docente_mis_ejercicios);
        rv_ejercicios_act.setLayoutManager(new GridLayoutManager(this, 2));
        rv_ejercicios_act.setHasFixedSize(true);

        listaEjercicios = new ArrayList<>();

        fa_btn_btn_modificar = (FloatingActionButton) findViewById(R.id.fabtn_docente_modificar);
        fa_btn_btn_modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (idtipo == 1) {
                    tipo1FonicoUpdateFragment = new Tipo1FonicoUpdateFragment();
                    Bundle parametros_1 = new Bundle();
                    parametros_1.putInt("iddocente", iddocente);
                    parametros_1.putInt("idgrupo", idgrupo);
                    parametros_1.putInt("idejercicio", idejercicio);
                    tipo1FonicoUpdateFragment.setArguments(parametros_1);

                    getSupportFragmentManager().beginTransaction().replace(R.id.container_docente_edit_ejer, tipo1FonicoUpdateFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();
                    //Toast.makeText(getApplicationContext(), "floating action button", Toast.LENGTH_SHORT).show();
                }
                if (idtipo == 2) {
                    tipo2FonicoUpdateFragment = new Tipo2FonicoUpdateFragment();
                    Bundle parametros_1 = new Bundle();
                    parametros_1.putInt("iddocente", iddocente);
                    parametros_1.putInt("idgrupo", idgrupo);
                    parametros_1.putInt("idejercicio", idejercicio);
                    tipo2FonicoUpdateFragment.setArguments(parametros_1);

                    getSupportFragmentManager().beginTransaction().replace(R.id.container_docente_edit_ejer, tipo2FonicoUpdateFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();
                    //Toast.makeText(getApplicationContext(), "floating action button", Toast.LENGTH_SHORT).show();
                }
                if (idtipo == 3) {
                    tipo1LexicoUpdateFragment = new Tipo1LexicoUpdateFragment();
                    Bundle parametros_1 = new Bundle();
                    parametros_1.putInt("iddocente", iddocente);
                    parametros_1.putInt("idgrupo", idgrupo);
                    parametros_1.putInt("idejercicio", idejercicio);
                    tipo1LexicoUpdateFragment.setArguments(parametros_1);

                    getSupportFragmentManager().beginTransaction().replace(R.id.container_docente_edit_ejer, tipo1LexicoUpdateFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();
                    //Toast.makeText(getApplicationContext(), "floating action button", Toast.LENGTH_SHORT).show();
                } else if (idtipo == 4) {
                    tipo2LexicoUpdateFragment = new Tipo2LexicoUpdateFragment();
                    Bundle parametros_1 = new Bundle();
                    parametros_1.putInt("iddocente", iddocente);
                    parametros_1.putInt("idgrupo", idgrupo);
                    parametros_1.putInt("idejercicio", idejercicio);
                    tipo2LexicoUpdateFragment.setArguments(parametros_1);

                    getSupportFragmentManager().beginTransaction().replace(R.id.container_docente_edit_ejer, tipo2LexicoUpdateFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();
                    //Toast.makeText(getApplicationContext(), "floating action button", Toast.LENGTH_SHORT).show();
                } else if (idtipo == 5) {
                    tipo1SilabicoUpdateFragment = new Tipo1SilabicoUpdateFragment();
                    Bundle parametros_1 = new Bundle();
                    parametros_1.putInt("iddocente", iddocente);
                    parametros_1.putInt("idgrupo", idgrupo);
                    parametros_1.putInt("idejercicio", idejercicio);
                    tipo1SilabicoUpdateFragment.setArguments(parametros_1);

                    getSupportFragmentManager().beginTransaction().replace(R.id.container_docente_edit_ejer, tipo1SilabicoUpdateFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();
                    //Toast.makeText(getApplicationContext(), "floating action button", Toast.LENGTH_SHORT).show();
                } else if (idtipo == 6) {
                    tipo2SilabicoUpdateFragment = new Tipo2SilabicoUpdateFragment();
                    Bundle parametros_1 = new Bundle();
                    parametros_1.putInt("iddocente", iddocente);
                    parametros_1.putInt("idgrupo", idgrupo);
                    parametros_1.putInt("idejercicio", idejercicio);
                    tipo2SilabicoUpdateFragment.setArguments(parametros_1);

                    getSupportFragmentManager().beginTransaction().replace(R.id.container_docente_edit_ejer, tipo2SilabicoUpdateFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();
                    //Toast.makeText(getApplicationContext(), "floating action button", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_fon = (Button) findViewById(R.id.btn_docentente_ejer_fon);
        btn_fon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaEjercicios = new ArrayList<>();
                idactividad = 1;
                buscar = "listaEje";
                cargarEjerciciosPorActividad();
                ll_rv_ejercicios.setVisibility(View.VISIBLE);
            }
        });
        btn_lex = (Button) findViewById(R.id.btn_docentente_ejer_lex);
        btn_lex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaEjercicios = new ArrayList<>();
                idactividad = 2;
                buscar = "listaEje";
                cargarEjerciciosPorActividad();
                ll_rv_ejercicios.setVisibility(View.VISIBLE);
            }
        });
        btn_sil = (Button) findViewById(R.id.btn_docentente_ejer_sil);
        btn_sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaEjercicios = new ArrayList<>();
                idactividad = 3;
                buscar = "listaEje";
                cargarEjerciciosPorActividad();
                ll_rv_ejercicios.setVisibility(View.VISIBLE);
            }
        });

    }
    //*********************************************************************************************

    private void cargarEjerciciosPorActividad() {

        progreso.setMessage("Cargando...");
        progreso.show();

        if (buscar.equals("listaEje")) {

            String iddoc = "20181";
            String url_lh = Globals.url;

            String url = "http://" + url_lh + "/proyecto_dconfo_v1/20wsJSONConsultarListaEjerciciosXactividad.php?iddocente="+iddocente+"&idactividad="+idactividad;
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);

            final int MY_DEFAULT_TIMEOUT = 15000;
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    MY_DEFAULT_TIMEOUT,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            // request.add(jsonObjectRequest);
            VolleySingleton.getIntanciaVolley(this).addToRequestQueue(jsonObjectRequest);
        } else if (buscar.equals("1")) {//**************AGREGADO
            String url_lh = Globals.url;

            String url = "http://" + url_lh + "/proyecto_dconfo_v1/10wsJSONConsultarListaEjerG2_H_Img.php?idejercicioG2=" + idejercicio;
            url = url.replace(" ", "%20");

            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            VolleySingleton.getIntanciaVolley(this).addToRequestQueue(jsonObjectRequest);//p21
        } else if (buscar.equals("2")) {
            String url_lh = Globals.url;

            String url = "http://" + url_lh + "/proyecto_dconfo_v1/11wsJSON__ConsultarListaEjerG2_Has_Letra.php?idejercicioG2=" + idejercicio;
            url = url.replace(" ", "%20");

            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            VolleySingleton.getIntanciaVolley(this).addToRequestQueue(jsonObjectRequest);//p21
        }

        // Toast.makeText(getContext(), "LISTA EJERCICIOS DOC.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        // Toast.makeText(getContext(), "No se puede cone , grupo doc" + error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("ERROR CExA: ", error.toString());
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

                        idejercicio = listaEjercicios.get(rv_ejercicios_act.
                                getChildAdapterPosition(v)).getIdEjercicioG2();

                        Toast.makeText(getApplicationContext(), "idejercicio: " + idejercicio, Toast.LENGTH_LONG).show();

                        idactividad = listaEjercicios.get(rv_ejercicios_act.
                                getChildAdapterPosition(v)).getIdActividad();

                        idtipo = listaEjercicios.get(rv_ejercicios_act.
                                getChildAdapterPosition(v)).getIdTipo();

                        oracion = listaEjercicios.get(rv_ejercicios_act.
                                getChildAdapterPosition(v)).getOracion();

                        cantLexemas = listaEjercicios.get(rv_ejercicios_act.
                                getChildAdapterPosition(v)).getCantidadLexemas();

                        letrainicial = listaEjercicios.get(rv_ejercicios_act.
                                getChildAdapterPosition(v)).getLetra_inicial_EjercicioG2();


                        ll_rv_ejercicios.setVisibility(View.GONE);
                        fl_mod_ejercicios.setVisibility(View.VISIBLE);

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

                        Toast.makeText(getApplicationContext(), "oracion: " + oracion, Toast.LENGTH_LONG).show();

                        //tipo1LexicoUpdateFragment = new Tipo1LexicoUpdateFragment();
                        //tipo1LexicoUpdateFragment.setArguments(parametros_1);

                        //****************************************************************fon
                        tipo1FonicoFragment = new Tipo1FonicoFragment();
                        tipo1FonicoFragment.setArguments(parametros_1);

                        tipo2FonicoFragment = new Tipo2FonicoFragment();
                        tipo2FonicoFragment.setArguments(parametros_1);
                        //****************************************************************lex
                        tipo1EstudianteFragment = new Tipo1EstudianteFragment();
                        tipo1EstudianteFragment.setArguments(parametros_1);

                        tipo2EstudianteFragment = new Tipo2EstudianteFragment();
                        tipo2EstudianteFragment.setArguments(parametros_1);

                        tipo1silabicoEstudianteFragment = new Tipo1silabicoEstudianteFragment();
                        tipo1silabicoEstudianteFragment.setArguments(parametros_1);
                        //****************************************************************sil
                        //****************************************************************


                        //Toast.makeText(getApplicationContext(), "idtipo: " + idtipo, Toast.LENGTH_LONG).show();

                        if (idtipo == 1) {
                            buscar = "1";
                            cargarEjerciciosPorActividad();
                        }
                        if (idtipo == 2) {
                            buscar = "1";
                            cargarEjerciciosPorActividad();
                        }
                        if (idtipo == 3) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.container_docente_edit_ejer, tipo1EstudianteFragment)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                    .addToBackStack(null).commit();
                        }
                        if (idtipo == 4) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.container_docente_edit_ejer, tipo2EstudianteFragment)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                    .addToBackStack(null).commit();
                        }
                        if (idtipo == 5) {//************listo

                            //tipo1EstudianteFragment.setArguments(bundle);

                            //getFragmentManager().beginTransaction().replace(R.id.container_HomeEstudiante, tipo1EstudianteFragment)
                            getSupportFragmentManager().beginTransaction().replace(R.id.container_docente_edit_ejer, tipo1silabicoEstudianteFragment)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                    .addToBackStack(null).commit();

                        }
                        if (idtipo == 6) {
                            buscar = "1";
                            cargarEjerciciosPorActividad();
                        }

                        //getSupportFragmentManager().beginTransaction().replace(R.id.container_docente_edit_ejer, tipo1LexicoUpdateFragment)
                    }
                });
                System.out.println("lista ejercicios: " + listaEjercicios.size() + " idactividad: " + idactividad);
                rv_ejercicios_act.setAdapter(tipoEjerciciosActividadDocenteAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("error", response.toString());

                //Toast.makeText(getApplicationContext(), "No se ha podido establecer conexión: " + response.toString(), Toast.LENGTH_LONG).show();

                progreso.hide();
            }

        }//listaEje

        else if (buscar.equals("1")) {//------------------------------------------------------------------------------------1

            Toast.makeText(getApplicationContext(), "grupo g3 activo: ", Toast.LENGTH_LONG).show();

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

                tipo2silabicoEstudianteFragment = new Tipo2silabicoEstudianteFragment();
                tipo2silabicoEstudianteFragment.setArguments(parametros_1);

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

                if (idtipo == 1) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_docente_edit_ejer, tipo1FonicoFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();
                }
                if (idtipo == 2) {
                    buscar = "2";
                    cargarEjerciciosPorActividad();
                }
                if (idtipo == 6) {
                    Toast.makeText(getApplicationContext(), "idtipo*******: " + idtipo, Toast.LENGTH_LONG).show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_docente_edit_ejer, tipo2silabicoEstudianteFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();
                }


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
                tipo2FonicoFragment = new Tipo2FonicoFragment();

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


                if (idtipo == 2) {
                    //tipo2FonicoFragment.setArguments(bundle_t2);
                    tipo2FonicoFragment.setArguments(parametros_1);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_docente_edit_ejer, tipo2FonicoFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();
                }


            } catch (JSONException e) {
                e.printStackTrace();
                //Toast.makeText(getContext(), "No se ha podido establecer conexión: " + response.toString(), Toast.LENGTH_LONG).show();

            }


        }// 2


    }//cargarEjerciciosPorActividad()


    //*********************************************************************************************

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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
