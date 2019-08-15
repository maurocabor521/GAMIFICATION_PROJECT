package com.example.asus.dconfo_app.presentation.view.activity.docente.fonico;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.asus.dconfo_app.R;
import com.roughike.bottombar.BottomBar;

public class NewEjercicioFonicoActivity extends AppCompatActivity {
    String nameDocente = "";
    int idDocente = 0;
    private BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ejercicio_fonico);
        Intent intent = this.getIntent();
        Bundle extra = intent.getExtras();

        nameDocente = extra.getString("namedocente");
        idDocente = extra.getInt("iddocente");
        String TAG1 = "TAG1";
        Log.i(TAG1, "idDocente: " + idDocente);
        Log.i(TAG1, "nameDocente: " + nameDocente);

        showToolbar("hola",true);
        //bottomBar = findViewById(R.id.bottombar_CED_activity);
        //cargarBottombar();
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
