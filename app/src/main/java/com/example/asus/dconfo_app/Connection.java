package com.example.asus.dconfo_app;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.dconfo_app.domain.model.Curso;
import com.example.asus.dconfo_app.domain.model.VolleySingleton;
import com.example.asus.dconfo_app.helpers.Globals;
import com.example.asus.dconfo_app.presentation.view.adapter.CursosAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Connection implements Response.Listener<JSONObject>,
        Response.ErrorListener {

    JsonObjectRequest jsonObjectRequest;
   // ArrayList<Curso> listaCursos;
    ArrayList<Curso> listaCursos1;
    Context context;
    View view;

    StringRequest stringRequest;

    public Connection(Context context, View view) {
        this.context = context;
        this.view = view;
        cargarWebService(context);
    }

    public void cargarWebService(Context context) {

        String url_lh = Globals.url;
        // String ip = getString(R.string.ip);

        //String url = "http://192.168.0.13/proyecto_dconfo/wsJSONConsultarListaCursos.php";
        String url = "http://" + url_lh + "/proyecto_dconfo_v1/wsJSONConsultarListaCursos.php";
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
        Curso curso = null;
        JSONArray json = response.optJSONArray("curso");

        ArrayList<Curso>listaDCursos = new ArrayList<>();
        listaCursos1 = new ArrayList<>();

        try {
            for (int i = 0; i < json.length(); i++) {
                curso = new Curso();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);
                curso.setIdCurso(jsonObject.optInt("idcurso"));
                curso.setIdInstitutoCurso(jsonObject.optInt("Instituto_idInstituto"));
                curso.setNameCurso(jsonObject.optString("namecurso"));
                curso.setPeriodoCurso(jsonObject.optString("periodocurso"));
                listaCursos1.add(curso);

            }
            Spinner spinner=(Spinner)this.view.findViewById(R.id.sp_idCurso);
            List<String>listanombreCurso=new ArrayList<>();
            for (int i=0;i<listaCursos1.size();i++){
                listanombreCurso.add(listaCursos1.get(i).getNameCurso());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, listanombreCurso);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            //spinner.s
            //setListaCursos(listaCursos1);
           listaDCursos = listaCursos1;
            setListadeCursos(listaDCursos);

            System.out.println("la lista:" + listaCursos1.size());
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

    public void setListadeCursos(ArrayList<Curso> listaCursos1) {
        this.listaCursos1 = listaCursos1;
    }

    public ArrayList<Curso> getListaCursos1() {
       /* Curso curso=new Curso();
        curso.setIdCurso(03);
        listaCursos1.add(curso);*/
        return listaCursos1;
    }




}
