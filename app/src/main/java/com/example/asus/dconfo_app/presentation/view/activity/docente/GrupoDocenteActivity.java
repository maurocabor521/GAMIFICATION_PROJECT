package com.example.asus.dconfo_app.presentation.view.activity.docente;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.asus.dconfo_app.R;

public class GrupoDocenteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo_docente);
      showToolbar("Mi Grupo",true);

        Bundle datos = this.getIntent().getExtras();
        int idgrupo = datos.getInt("idgrupo");
        int idcurso = datos.getInt("idcurso");

        Toast.makeText(getApplicationContext(),"idgrupo: "+idgrupo,Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(),"idcurso: "+idcurso,Toast.LENGTH_LONG).show();
    }

    public void showToolbar(String tittle, boolean upButton) {
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_ejercicio);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        //getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
