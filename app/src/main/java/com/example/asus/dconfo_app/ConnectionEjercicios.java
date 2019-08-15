package com.example.asus.dconfo_app;

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
import com.example.asus.dconfo_app.domain.model.Curso;
import com.example.asus.dconfo_app.domain.model.EjercicioG1;
import com.example.asus.dconfo_app.domain.model.Estudiante;
import com.example.asus.dconfo_app.domain.model.VolleySingleton;
import com.example.asus.dconfo_app.helpers.Globals;
import com.example.asus.dconfo_app.presentation.view.activity.docente.AsignarEstudianteDeberActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConnectionEjercicios implements Response.Listener<JSONObject>,
        Response.ErrorListener {

    JsonObjectRequest jsonObjectRequest;

    ArrayList<EjercicioG1> listaEjerciciosg1;
    Context context;
    View view;
    Integer iddocente;
    List<String> listaNombreEjerciciog1;
    List<Integer> listaidEjerciciog1;

    StringRequest stringRequest;

    public ConnectionEjercicios(Context context, View view, Integer iddocente) {
        this.context = context;
        this.view = view;
        this.iddocente = iddocente;
        cargarWebService(context);
    }

    public void cargarWebService(Context context) {

        String url_lh = Globals.url;
        // String ip = getString(R.string.ip);

        //String url = "http://192.168.0.13/proyecto_dconfo/wsJSONConsultarListaCursos.php";
        String url = "http://" + url_lh + "/proyecto_dconfo_v1/wsJSONConsultarListaEjerciciosDocente.php?iddocente=" + iddocente;
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
        JSONArray json = response.optJSONArray("ejerciciog1");

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
            Spinner spinner = (Spinner) this.view.findViewById(R.id.sp_Ejercicios_asignar);
            listaNombreEjerciciog1 = new ArrayList<>();
            listaidEjerciciog1 = new ArrayList<>();
            //listaNombreEjerciciog1.add(" ");

            for (int i = 0; i < listaDEjerciciosg1.size(); i++) {

                listaNombreEjerciciog1.add(String.valueOf(listaDEjerciciosg1.get(i).getNameEjercicio()));
                listaidEjerciciog1.add(listaDEjerciciosg1.get(i).getIdEjercicio());

                System.out.println("Ejercicio:" + i + listaDEjerciciosg1.get(i).getIdEjercicio());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, listaNombreEjerciciog1);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    System.out.println("ejericio seleccionado: "+listaNombreEjerciciog1.get(position));
                    AsignarEstudianteDeberActivity asignarEstudianteDeberActivity=new AsignarEstudianteDeberActivity();
                    //asignarEstudianteDeberActivity.
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            //spinner.s
            //setListaCursos(listaCursos1);
            // listaDCursos = listaCursos1;
            // setListadeCursos(listaDCursos);

            System.out.println("la lista Ejercicios:" + listaNombreEjerciciog1.size());
//            System.out.println("la lista1:"+listaCursos1.get(0).getNameCurso());
            // return listaCursos;
            // CursosAdapter cursosAdapter = new CursosAdapter(listaCursos);
            // rvListaCursos.setAdapter(cursosAdapter);
            // getListaCursos();
        } catch (JSONException e) {
            e.printStackTrace();
            //Toast.makeText(getContext(), "No se ha podido establecer conexión: " + response.toString(), Toast.LENGTH_LONG).show();

        }
    }

  /*  public void setListadeCursos(ArrayList<Curso> listaCursos1) {
        this.listaCursos1 = listaCursos1;
    }

  /*  public ArrayList<Curso> getListaCursos1() {
       /* Curso curso=new Curso();
        curso.setIdCurso(03);
        listaCursos1.add(curso);*/
    // return listaCursos1;
    //}*/


}
