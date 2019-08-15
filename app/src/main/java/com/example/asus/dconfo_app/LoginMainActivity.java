package com.example.asus.dconfo_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.dconfo_app.domain.model.Docente;
import com.example.asus.dconfo_app.domain.model.Estudiante;
import com.example.asus.dconfo_app.domain.model.Grupo;
import com.example.asus.dconfo_app.domain.model.VolleySingleton;
import com.example.asus.dconfo_app.helpers.Globals;
import com.example.asus.dconfo_app.presentation.view.activity.docente.HomeDocenteActivity;
import com.example.asus.dconfo_app.presentation.view.activity.docente.ManageCursosDocenteActivity;
import com.example.asus.dconfo_app.presentation.view.adapter.GruposDocenteAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginMainActivity extends AppCompatActivity implements Response.Listener<JSONObject>,
        Response.ErrorListener {

    private EditText edt_email;
    private EditText edt_pass;
    private Button btn_ingresar;
    private int iddconte_bundle = 0;
    private String usuario;
    ProgressDialog progreso;

    //******** CONEXIÓN CON WEBSERVICE
    //RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
        edt_email = (EditText) findViewById(R.id.edt_loginMain_email);
        edt_pass = (EditText) findViewById(R.id.edt_loginMain_password);
        btn_ingresar = (Button) findViewById(R.id.btn_LoginMain);
        progreso = new ProgressDialog(this);

        Bundle datos = this.getIntent().getExtras();
        usuario = datos.getString("usuario");
        btn_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService();
            }
        });
    }

    private void cargarWebService() {

        progreso.setMessage("Cargando...");
        progreso.show();
        // String ip = getString(R.string.ip);
        //int iddoc=20181;
        String iddoc = "20181";
        String url_lh = Globals.url;
        int p=20181;
        int e=20181;
       // int password=Integer.parseInt(edt_pass.getText().toString());
       // int email=Integer.parseInt(edt_email.getText().toString());
        String url = "http://" + url_lh + "/proyecto_dconfo_v1/wsJSONLogin1.php?" +
                "password="+edt_pass.getText().toString()+"&email="+edt_email.getText().toString();
                //"password="+p+"&email="+e;
               // "password="+password+"&email="+email;
        // url = url.replace(" ", "%20");
        //hace el llamado a la url
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, this, this);

   /*     final int MY_DEFAULT_TIMEOUT = 15000;
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*/

        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);//p21
        Toast.makeText(getApplicationContext(), "web service 1111", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getApplicationContext(), "No se puede cone , grupo doc" + error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("ERROR", error.toString());
        progreso.hide();

    }

    // si esta bien el llamado a la url entonces entra a este metodo
    @Override
    public void onResponse(JSONObject response) {
        progreso.hide();
        //Toast.makeText(getApplicationContext(), "Mensaje: " + response.toString(), Toast.LENGTH_SHORT).show();


        JSONArray json = response.optJSONArray("docente");
        JSONObject jsonObject = null;

        try {
            jsonObject = json.getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // jsonObject = new JSONObject(response);

        if (usuario == "administrador") {

        } else if (usuario == "docente") {
            Docente docente = new Docente();
            docente.setIddocente(jsonObject.optInt("iddocente"));

            Intent intent = new Intent(LoginMainActivity.this, ManageCursosDocenteActivity.class);
            int iddocente = docente.getIddocente();
            intent.putExtra("iddocente", iddocente);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "DOCENTE: " + docente.getIddocente().toString(), Toast.LENGTH_SHORT).show();

        } else if (usuario == "estudiante") {
            Estudiante estudiante = new Estudiante();
            estudiante.setDniestudiante(jsonObject.optInt("dniestudiante"));

        }

    }


}
