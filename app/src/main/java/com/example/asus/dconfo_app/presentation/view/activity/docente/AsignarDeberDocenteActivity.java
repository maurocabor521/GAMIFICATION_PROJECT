package com.example.asus.dconfo_app.presentation.view.activity.docente;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.dconfo_app.Connection;
import com.example.asus.dconfo_app.ConnectionEjercicios;
import com.example.asus.dconfo_app.ConnectionEstudiantes;
import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.domain.model.VolleySingleton;
import com.example.asus.dconfo_app.helpers.Globals;
import com.roughike.bottombar.BottomBar;

import java.util.HashMap;
import java.util.Map;

public class AsignarDeberDocenteActivity extends AppCompatActivity {

    Spinner spinnerEjercicios;
    Spinner spinnerEstudiantes;
    Button btnAsignar;
    private BottomBar bottomBar;
    ProgressDialog progreso;
    JsonObjectRequest jsonObjectRequest;
    View view;
    Integer idEstudianteDeber=null;
    Integer idEjercicioDeber=null;

    StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignar_deber_docente);
        spinnerEjercicios = findViewById(R.id.sp_Ejercicios_asignar);
        spinnerEstudiantes = findViewById(R.id.sp_Estudiantes_asignar);
        btnAsignar = findViewById(R.id.btn_Asignar_asignar);
        progreso = new ProgressDialog(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        view = findViewById(android.R.id.content);
        listaEstudiantes();
        listaEjercicios();
        if(!(idEstudianteDeber.equals(null)&&(idEjercicioDeber.equals(null)))){
            Toast.makeText(getApplicationContext(),"llamar webservice",Toast.LENGTH_LONG).show();
        }
    }

    //método que permite volver al padre conservando las variables
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void listaEjercicios() {
        ConnectionEjercicios connectionEje=new ConnectionEjercicios(getApplicationContext(),view,220);
    }

    public void listaEstudiantes(){
        ConnectionEstudiantes connectionE=new ConnectionEstudiantes(getApplicationContext(),view,1);
    }

    public void setIdEstudiante(Integer idEstudiante){
        idEstudianteDeber=idEstudiante;
    }
    public void setIdEjercicio(Integer idEjercicio){
        idEjercicioDeber=idEjercicio;
    }
}
