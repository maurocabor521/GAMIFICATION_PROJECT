package com.example.asus.dconfo_app.domain.model.caseuse.impl.impl;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.asus.dconfo_app.domain.model.EjercicioG1;
import com.example.asus.dconfo_app.domain.model.caseuse.impl.EjerciciosCaseUse;
import com.example.asus.dconfo_app.helpers.Callback;
import com.example.asus.dconfo_app.helpers.Globals;
import com.example.asus.dconfo_app.helpers.ThreadExecutor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EjerciciosCaseUseImpl implements EjerciciosCaseUse {

    ArrayList<EjercicioG1> listaEjercicios = new ArrayList<>();//modificado con new ArrayList<>();
    ArrayList<String> listaStringEjercicios = new ArrayList<>();
    int flag = 0;
    Context context;

    public EjerciciosCaseUseImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getlstEjercicios(final Callback<List<EjercicioG1>> callback) {
        new ThreadExecutor<List<EjercicioG1>>()
                .execute(new ThreadExecutor.Task<List<EjercicioG1>>() {
                    @Override
                    public List<EjercicioG1> execute() throws Exception {
                        //Llamo al metodo sincrono del UseCase
                     /*   try {
                            Thread.sleep(100);
                            System.out.println("Dormir...");
                        } catch (InterruptedException e) {

                        }*/
                      /*  if (flag != 1) {
                            getlstEjercicios();
                            //return getlstEjercicios1();
                        } else {

                            return getlstEjercicios();
                        }*/
                        // getlstEjercicios();
                        return getlstEjercicios();
                        //return getlstEjercicios();

                    }

                    @Override
                    public void finish(Exception error, List<EjercicioG1> result) {
                        if (error == null) {
                            callback.success(result);
                        } else {
                            callback.error(error);
                        }
                    }
                });
    }

    private List<EjercicioG1> getlstEjercicios2() throws Exception {
        System.out.println("*****flag :" + flag);
        List<EjercicioG1> lstEjer = new ArrayList<>();
        if (flag == 1) {
            lstEjer = listaEjercicios;
            System.out.println("-----flag :" + flag);
            //flag = 0;
            return lstEjer;

        } else {
            //getlstEjercicios();
            getlstEjercicios2();
            System.out.println("*****getlstEjercicios2" + listaEjercicios.toString());
        }

        System.out.println("*****lstEjer" + lstEjer.toString());
        return lstEjer;
    }

    public List<EjercicioG1> getlstEjercicios1() throws Exception {
        List<EjercicioG1> listaEjercicios = new ArrayList<>();
        EjercicioG1 ejercicioG1 = new EjercicioG1();
        ejercicioG1.setNameEjercicio("eje 1");
        EjercicioG1 ejercicioG12 = new EjercicioG1();
        ejercicioG12.setNameEjercicio("eje 2");
        listaEjercicios.add(ejercicioG1);
        listaEjercicios.add(ejercicioG12);
        return listaEjercicios;
    }


    @Override
    public List<EjercicioG1> getlstEjercicios() throws Exception {

        // List<EjercicioG1> listaEjercicios;//2 agregado aquí

        String url_lh = Globals.url;

        //String url = "http://" + url_lh + "/proyecto_dconfo/wsJSONConsultarListaEjerciciosDocente.php?docente_iddocente=" + iddocente;
        String url = "http://" + url_lh + "/proyecto_dconfo_v1/wsJSON1ConsultarListaEjercicios.php";


        RequestQueue requestQueue = Volley.newRequestQueue(context);

        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Do something with response

                        //listaEjercicios = new ArrayList<>();//comentado

                        EjercicioG1 ejercicioG1 = null;


                        try {
                            JSONArray json = response.optJSONArray("ejerciciog1");
                            for (int i = 0; i < json.length(); i++) {
                                ejercicioG1 = new EjercicioG1();
                                JSONObject jsonObject = null;
                                jsonObject = json.getJSONObject(i);
                                ejercicioG1.setIdEjercicio(jsonObject.optInt("idEjercicioG1"));
                                ejercicioG1.setNameEjercicio(jsonObject.optString("nameEjercicioG1"));
                                ejercicioG1.setIdDocente(jsonObject.optInt("docente_iddocente"));
                                ejercicioG1.setIdTipo(jsonObject.optInt("Tipo_idTipo"));
                                ejercicioG1.setIdActividad(jsonObject.optInt("Tipo_Actividad_idActividad"));

                                listaEjercicios.add(ejercicioG1);
                            }
                            System.out.println("get lista ejercicios" + listaEjercicios.toString());
                            flag = 1;

                            //listaStringEjercicios.add("Seleccione Id Ejercicio");
                            for (int i = 0; i < listaEjercicios.size(); i++) {
                                //listaStringEjercicios.add(listaEjercicios.get(i).getIdEjercicio().toString());
                                listaStringEjercicios.add(listaEjercicios.get(i).getNameEjercicio());
                            }


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

        try {
            Thread.sleep(100);
            System.out.println("Dormir...");
        } catch (InterruptedException e) {

        }

        return listaEjercicios;
    }
}
