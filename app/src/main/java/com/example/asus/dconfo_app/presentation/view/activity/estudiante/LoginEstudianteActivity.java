package com.example.asus.dconfo_app.presentation.view.activity.estudiante;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.dconfo_app.LoginMainEstudianteActivity;
import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.domain.model.Docente;
import com.example.asus.dconfo_app.domain.model.Estudiante;
import com.example.asus.dconfo_app.domain.model.Login;
import com.example.asus.dconfo_app.domain.model.VolleySingleton;
import com.example.asus.dconfo_app.helpers.Globals;
import com.example.asus.dconfo_app.presentation.view.activity.docente.ManageCursosDocenteActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginEstudianteActivity extends AppCompatActivity implements Response.Listener<JSONObject>,
        Response.ErrorListener {

    private EditText edt_email;
    private EditText edt_pass;
    private Button btn_ingresar;
    private int iddconte_bundle=0;
    private String namedocente_bundle;
    ProgressDialog progreso;

    //******** CONEXIÓN CON WEBSERVICE
    //RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    StringRequest stringRequest;
    JsonObjectRequest jsonObjectRequest1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_email = (EditText) findViewById(R.id.edt_login_doc_email);
        edt_pass = (EditText) findViewById(R.id.edt_login_doc_password);
        btn_ingresar = (Button) findViewById(R.id.btn_Login_Docente);
        progreso = new ProgressDialog(this);
        progreso = new ProgressDialog(getApplicationContext());
        btn_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cargarWebService();
                cargarWebService1();
            }
        });


    }

    private void cargarWebService1() {

      /*  progreso.setMessage("Consultando...");
        progreso.show();*/

        //int cod=Integer.parseInt(edt_codigo.getText().toString());
        String email = edt_email.getText().toString();
        String password = edt_pass.getText().toString();

        String url_lh=Globals.url;
        // String ip = getString(R.string.ip);

        //String url = "http://192.168.0.13/" +
        String url = "http://"+url_lh+"/" +
                //"ejemploBDRemota/wsJSONConsultarUsuario.php?documento=" + campoDocumento.getText().toString();
                "proyecto_dconfo_v1/wsJSONConsultarEstudiante.php?passwork_estudiante="+password+"&emailAcudienteestudiante="+email;
       // Toast.makeText(getApplicationContext(), "Mensaje: " + cod, Toast.LENGTH_SHORT).show();
        // String url = ip+"ejemploBDRemota/wsJSONConsultarUsuarioImagen.php?documento=" + campoDocumento.getText().toString();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);//p21
    }

    private void cargarWebService() {

      //  progreso.setMessage("Cargando...");
//        progreso.show();
        String url_lh=Globals.url;
        String url =
                "http://"+url_lh+"/proyecto_dconfo_v1/wsJSONLogin1.php?";
               // "http://192.168.0.13/proyecto_dconfo/wsJSONLogin.php?";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//recibe respuesta del webservice,cuando esta correcto
                progreso.hide();
                if (response.trim().equalsIgnoreCase("registra")) {

                //if (response.contentEquals("registra")) {
                    edt_email.setText("");
                    edt_pass.setText("");
                    //Login miLogin = new Login();
                    Bundle parmetros = new Bundle();
                    parmetros.putInt("iddocente", iddconte_bundle);
                    parmetros.putString("namedocente", namedocente_bundle);

                    Intent intent = new Intent(LoginEstudianteActivity.this, ManageCursosDocenteActivity.class);
                   // intent.putExtras(parmetros);
                    startActivity(intent);


                    Toast.makeText(getApplicationContext(), "Se ha cargado con éxito", Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "iddocente: "+iddconte_bundle, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No se ha cargado ", Toast.LENGTH_LONG).show();
                    Log.i("ERROR", "RESPONSE" + response.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No se ha podido conectar", Toast.LENGTH_LONG).show();
                // progreso.hide();
            }
        }) {//enviar para metros a webservice, mediante post
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String email = edt_email.getText().toString();
                String password = edt_pass.getText().toString();

                Map<String, String> parametros = new HashMap<>();
                parametros.put("email", email);
                parametros.put("password", password);

                return parametros;
            }
        };
        //request.add(stringRequest);
        //p25 duplicar tiempo x defecto
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);//p21
    }

    public void enviarIdDocente(View view){

      /* tv1.setText("1");

        String datos = tv1.getText().toString();

        Bundle parmetros = new Bundle();
        parmetros.putString("datos", datos);

        Intent i = new Intent(this, MainActivity.class);
        i.putExtras(parmetros);
        startActivity(i);*/
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getApplicationContext(), "No se ha realizado la consulta de usuario" + error.toString(), Toast.LENGTH_LONG).show();
        Log.i("ERROR", error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        progreso.hide();
        //Toast.makeText(getApplicationContext(), "Mensaje: " + response.toString(), Toast.LENGTH_SHORT).show();


        JSONArray json = response.optJSONArray("estudiante");
        JSONObject jsonObject = null;

        try {
            jsonObject = json.getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // jsonObject = new JSONObject(response);

    /*    if (usuario == "administrador") {

        } else if (usuario == "docente") {*/
        Estudiante estudiante = new Estudiante();
        estudiante.setIdestudiante(jsonObject.optInt("idestudiante"));
        estudiante.setNameestudiante(jsonObject.optString("nameestudiante"));

        Intent intent = new Intent(LoginEstudianteActivity.this, HomeEstudianteActivity.class);
        int idestudiante = estudiante.getIdestudiante();
        String nameestudiante=estudiante.getNameestudiante();
        intent.putExtra("idestudiante", idestudiante);
        intent.putExtra("nameestudiante", nameestudiante);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "ESTUDIANTE: " + estudiante.getIdestudiante().toString(), Toast.LENGTH_SHORT).show();

      /*  } else if (usuario == "estudiante") {
            Estudiante estudiante = new Estudiante();
            estudiante.setDniestudiante(jsonObject.optInt("dniestudiante"));

        }*/

    }
}
