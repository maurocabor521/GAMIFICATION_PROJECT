package com.example.asus.dconfo_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.asus.dconfo_app.presentation.view.activity.administrador.Home_AdminActivity;
import com.example.asus.dconfo_app.presentation.view.activity.docente.LoginDocenteActivity;
import com.example.asus.dconfo_app.presentation.view.activity.estudiante.HomeEstudianteActivity;
import com.example.asus.dconfo_app.presentation.view.activity.estudiante.LoginEstudianteActivity;
import com.roughike.bottombar.BottomBar;

public class MainActivity extends AppCompatActivity {

    BottomBar bottomBar;
    private Intent intentAdministrador;
    private Intent intentDocente;
    private Intent intentEstudiante;
    private Intent loginDocente;
    private Button btn_admin;
    private Button btn_estudiante;
    private Button btn_docente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  bottomBar = findViewById(R.id.bar_home_access1);
      //  bottomBar.setDefaultTab(R.id.home_null);
        btn_admin=findViewById(R.id.btn_home_admin);
        btn_docente=findViewById(R.id.btn_home_docente);
        btn_estudiante=findViewById(R.id.btn_home_estudiante);

        btn_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentAdministrador=new Intent(MainActivity.this,Home_AdminActivity.class);
                Bundle parametros = new Bundle();
                String usuarioa="administrador";
                intentAdministrador.putExtra("usuario", usuarioa);
                startActivity(intentAdministrador);
            }
        });
        btn_docente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // intentDocente=new Intent(MainActivity.this,HomeDocenteActivity.class);
                //startActivity(intentDocente);
                //loginDocente=new Intent(MainActivity.this,LoginDocenteActivity.class);
                loginDocente=new Intent(MainActivity.this,LoginDocenteActivity.class);
                Bundle parametros = new Bundle();
                String usuariod="docente";
                loginDocente.putExtra("usuario", usuariod);
                startActivity(loginDocente);
            }
        });
        btn_estudiante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentEstudiante=new Intent(MainActivity.this, LoginEstudianteActivity.class);
                Bundle parametros = new Bundle();
                String usuarioe="estudiante";
               // intentEstudiante.putExtra("usuario", usuarioe);
                startActivity(intentEstudiante);
            }
        });
        //cargarMiddleBar();
    }
/*
    private void cargarMiddleBar() {
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId) {
                    case R.id.home_administrador:
                        intentAdministrador=new Intent(MainActivity.this,Home_AdminActivity.class);
                        startActivity(intentAdministrador);
                        break;
                        case R.id.home_docente:
                        intentDocente=new Intent(MainActivity.this,HomeDocenteActivity.class);
                        startActivity(intentDocente);
                        break;
                        case R.id.home_estudiante:
                        intentEstudiante=new Intent(MainActivity.this,HomeEstudianteActivity.class);
                        startActivity(intentEstudiante);
                        break;
                }
            }
        });
    }*/


}
