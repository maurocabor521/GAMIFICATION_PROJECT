package com.example.asus.dconfo_app.repository;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.domain.model.EjercicioG1;
import com.example.asus.dconfo_app.domain.model.VolleySingleton;
import com.example.asus.dconfo_app.helpers.Globals;
import com.example.asus.dconfo_app.presentation.view.activity.docente.AsignarEstudianteDeberActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ImpListEjercicios implements Response.Listener<JSONObject>,
        Response.ErrorListener {


    JsonObjectRequest jsonObjectRequest;

    ArrayList<EjercicioG1> listaEjerciciosg1;
    Context context;
    View view;
    Integer idDocente;
    List<String> listaNombreEjerciciog1;
    List<Integer> listaidEjerciciog1;
    JSONArray json;

    StringRequest stringRequest;

    public ImpListEjercicios(Context context, View view, Integer idDocente) {
        this.context = context;
        this.view = view;
        this.idDocente = idDocente;
        cargarWebService(context);
    }

    public void cargarWebService(Context context) {

        String url_lh = Globals.url;
        // String ip = getString(R.string.ip);

        //String url = "http://192.168.0.13/proyecto_dconfo/wsJSONConsultarListaCursos.php";
        String url = "http://" + url_lh + "/proyecto_dconfo_v1/wsJSONConsultarListaEjerciciosDocente.php?iddocente=" + idDocente;
        //String url = ip+"ejemploBDRemota/wsJSONConsultarLista.php";
        //reemplazar espacios en blanco del nombre por %20
        url = url.replace(" ", "%20");
        //hace el llamado a la url
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(context).addToRequestQueue(jsonObjectRequest);//p21
        //Toast.makeText(getContext(), "web service", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        // Toast.makeText(getContext(), "No se puede conectar exitosamente" + error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("ERROR", error.toString());

    }

    // si esta bien el llamado a la url entonces entra a este metodo
    @Override
    public void onResponse(JSONObject response) {
        // public ArrayList<Curso> onResponse(JSONObject response) {
        //lectura del Json

        //Toast.makeText(getContext(), "onResponse: " + response.toString(), Toast.LENGTH_SHORT).show();
        EjercicioG1 ejercicioG1 = null;
        json = response.optJSONArray("ejerciciog1");

        ArrayList<EjercicioG1> listaDEjerciciosg1 = new ArrayList<>();
        listaDEjerciciosg1 = new ArrayList<>();

        try {
            for (int i = 0; i < json.length(); i++) {
                ejercicioG1 = new EjercicioG1();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);
                ejercicioG1.setNameEjercicio(jsonObject.optString("nameEjercicioG1"));
                ejercicioG1.setIdEjercicio(jsonObject.optInt("idEjercicioG1"));
                ejercicioG1.setIdTipo(jsonObject.optInt("Tipo_idTipo"));

                listaDEjerciciosg1.add(ejercicioG1);

            }
            //Spinner spinner = (Spinner) this.view.findViewById(R.id.sp_Ejercicios_asignar);




        } catch (JSONException e) {
            e.printStackTrace();
            //Toast.makeText(getContext(), "No se ha podido establecer conexión: " + response.toString(), Toast.LENGTH_LONG).show();

        }
    }

    public List<String> getListaEjercicios() {
        List<EjercicioG1> ListEjer = new ArrayList<>();
        EjercicioG1 ejercicioG1 = new EjercicioG1();
        //JSONArray json1 = json;
        if (json == null) {
            cargarWebService(context);
        } else {
            try {
                ArrayList<EjercicioG1> listaDEjerciciosg1 = new ArrayList<>();
                for (int i = 0; i < json.length(); i++) {
                    ejercicioG1 = new EjercicioG1();
                    JSONObject jsonObject = null;
                    jsonObject = json.getJSONObject(i);
                    ejercicioG1.setNameEjercicio(jsonObject.optString("nameEjercicioG1"));
                    ejercicioG1.setIdEjercicio(jsonObject.optInt("idEjercicioG1"));

                    listaDEjerciciosg1.add(ejercicioG1);

                }
                System.out.println("Ejercicio: - "+ listaDEjerciciosg1.get(0).getIdEjercicio());

                listaNombreEjerciciog1 = new ArrayList<>();
                listaidEjerciciog1 = new ArrayList<>();
                //listaNombreEjerciciog1.add(" ");

                for (int i = 0; i < listaDEjerciciosg1.size(); i++) {

                   // listaNombreEjerciciog1.add(String.valueOf(listaDEjerciciosg1.get(i).getNameEjercicio()));
                    listaNombreEjerciciog1.add(String.valueOf(listaDEjerciciosg1.get(i).getNameEjercicio()));
                    listaidEjerciciog1.add(listaDEjerciciosg1.get(i).getIdEjercicio());

                    System.out.println("Ejercicio:" + i +" - "+ listaDEjerciciosg1.get(i).getIdEjercicio());
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return listaNombreEjerciciog1;
    }


}