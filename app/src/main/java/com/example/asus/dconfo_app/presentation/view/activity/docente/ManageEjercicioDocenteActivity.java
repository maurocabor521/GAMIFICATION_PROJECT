package com.example.asus.dconfo_app.presentation.view.activity.docente;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.presentation.view.fragment.docente.Find1EjercicioFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.docente.FindEjercicioFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.docente.HomeEjerciciosDocenteFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.docente.NewEjercicioDocenteFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class ManageEjercicioDocenteActivity extends AppCompatActivity implements
        HomeEjerciciosDocenteFragment.OnFragmentInteractionListener,
        NewEjercicioDocenteFragment.OnFragmentInteractionListener,
        FindEjercicioFragment.OnFragmentInteractionListener,
        Find1EjercicioFragment.OnFragmentInteractionListener {

    private BottomBar bottomBar;
    private HomeEjerciciosDocenteFragment homeEjerciciosDocenteFragment;
    private NewEjercicioDocenteFragment newEjercicioDocenteFragment;
    private FindEjercicioFragment findEjercicioFragment;
    private Find1EjercicioFragment find1EjercicioFragment;
    String nameDocente = "";
    int idDocente = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_ejercicio_docente);


        Intent intent = this.getIntent();
        Bundle extra = intent.getExtras();

        nameDocente = extra.getString("namedocente");
        idDocente = extra.getInt("iddocente");
        showToolbar("Gestionar Ejercicios Léxicos, Docente: " + nameDocente, true);
        bottomBar = findViewById(R.id.bottombar_CED);
        cargarBottombar();
    }

    //método que permite volver al padre conservando las variables
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
     /*   Bundle bundle=new Bundle();
        bundle.putInt("iddocente",idDocente);
        bundle.putString("namedocente",nameDocente);*/
    }

    private void cargarBottombar() {

        bottomBar.setDefaultTab(R.id.bott_home_CED);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId) {
                    case R.id.bott_home_CED:
                        homeEjerciciosDocenteFragment = new HomeEjerciciosDocenteFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_DocenteEjercicios, homeEjerciciosDocenteFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        //Toast.makeText(getApplicationContext(), "Ejercicios Home", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.bott_buscar_CED:
                        Bundle parametros_1 = new Bundle();
                        parametros_1.putInt("iddocente", idDocente);

                        // findEjercicioFragment = new FindEjercicioFragment();
                        // findEjercicioFragment.setArguments(parametros_1);

                        find1EjercicioFragment = new Find1EjercicioFragment();
                        find1EjercicioFragment.setArguments(parametros_1);

                        getSupportFragmentManager().beginTransaction().replace(R.id.container_DocenteEjercicios, find1EjercicioFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        //Toast.makeText(getApplicationContext(), "Ejercicios Home", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.bott_new_CED:
                        Bundle parametros = new Bundle();
                        parametros.putInt("iddocente", idDocente);
                        parametros.putString("namedocente", nameDocente);

                        Intent intentNewEjercicio = new Intent(ManageEjercicioDocenteActivity.this, NewEjercicioDocenteActivity.class);

                        intentNewEjercicio.putExtras(parametros);
                        startActivity(intentNewEjercicio);
                     /*   newEjercicioDocenteFragment = new NewEjercicioDocenteFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_DocenteEjercicios, newEjercicioDocenteFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();*/
                        //Toast.makeText(getApplicationContext(), "Ejercicio Nuevo", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

    public void showToolbar(String tittle, boolean upButton) {
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_ejercicio);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        //getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
