package com.example.asus.dconfo_app.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.asus.dconfo_app.domain.model.EjercicioG1;
import com.example.asus.dconfo_app.domain.model.Estudiante;
import com.example.asus.dconfo_app.helpers.Globals;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListaEjercicios extends AsyncTask<String, Void, String> {
    JsonObjectRequest jsonObjectRequest;

    StringRequest stringRequest;
    ArrayList<Estudiante> listaEstudiantes;
    ArrayList<EjercicioG1> listaEjercicios;
    final List<String> listaStringEjercicios = new ArrayList<>();
    Context context;

    public ListaEjercicios(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {

        listarEjerciciosDocente();
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        //super.onPostExecute(s);
        getListaStringEjercicios();
        //System.out.println("get lista ejercicios" + listaEjercicios.toString());
    }

    public void listarEjerciciosDocente() {
        // final String usuario = etUsuario.getText().toString();
        // final String password = etPass.getText().toString();

        // INICIAR LA CONEXION CON VOLLEY

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

                        listaEjercicios = new ArrayList<>();

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

                            //listaStringEjercicios.add("Seleccione Id Ejercicio");
                            for (int i = 0; i < listaEjercicios.size(); i++) {
                                //listaStringEjercicios.add(listaEjercicios.get(i).getIdEjercicio().toString());
                                listaStringEjercicios.add(listaEjercicios.get(i).getNameEjercicio());
                            }
                            getListaStringEjercicios();

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

    public List<String> getListaStringEjercicios() {
        System.out.println("get lista ejercicios string" + listaStringEjercicios);
        return this.listaStringEjercicios;
    }


}
