package com.example.asus.dconfo_app.presentation.view.activity.docente.fonico;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.presentation.view.fragment.docente.fonico.HomeFonicoFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.docente.fonico.Tipo1FonicoFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.docente.fonico.Tipo2FonicoFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.docente.modificarEjercicio.fonicos.Tipo1FonicoUpdateFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.docente.modificarEjercicio.fonicos.Tipo2FonicoUpdateFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.docente.tipoFragments.HomeTiposFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.docente.tipoFragments.Tipo1Fragment;
import com.example.asus.dconfo_app.presentation.view.fragment.docente.tipoFragments.Tipo2Fragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class NewEjercicioFonicoDocenteActivity extends AppCompatActivity implements
        HomeFonicoFragment.OnFragmentInteractionListener,
        Tipo1FonicoFragment.OnFragmentInteractionListener,
        Tipo2FonicoFragment.OnFragmentInteractionListener,
        com.example.asus.dconfo_app.presentation.view.fragment.Estudiante.fonico.Tipo1FonicoFragment.OnFragmentInteractionListener,
        com.example.asus.dconfo_app.presentation.view.fragment.Estudiante.fonico.Tipo2FonicoFragment.OnFragmentInteractionListener {

    private BottomBar bottomBar;
    private HomeFonicoFragment homeFonicoFragment;
    private Tipo1FonicoFragment tipo1FonicoFragment;
    private Tipo2FonicoFragment tipo2FonicoFragment;

    String nameDocente = "";
    int idDocente = 0;
    int idgrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ejercicio_fonico_docente);
        Intent intent = this.getIntent();
        Bundle extra = intent.getExtras();

        nameDocente = extra.getString("namedocente");
        idDocente = extra.getInt("iddocente");
        idgrupo = extra.getInt("idgrupo");

        showToolbar("Nuevo Ejercicio Fónico, Docente: " + nameDocente, true);
        bottomBar = findViewById(R.id.bottombar_CED_activity);
        cargarBottombar();
    }

    //método que permite volver al padre conservando las variables
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void cargarBottombar() {

        bottomBar.setDefaultTab(R.id.bott_home_tipo_CED);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId) {
                    case R.id.bott_home_tipo_CED:
                        Bundle args1 = new Bundle();

                        //  String
                        args1.putString("namedocente", nameDocente);
                        args1.putInt("iddocente", idDocente);
                        args1.putInt("idgrupo", idgrupo);

                        homeFonicoFragment = new HomeFonicoFragment();
                        homeFonicoFragment.setArguments(args1);

                        getSupportFragmentManager().beginTransaction().replace(R.id.container_Tipos_CEFD, homeFonicoFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        //Toast.makeText(getApplicationContext(), "Ejercicios Home", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.bott_tipo1_CED:
                        //enviar datos activity a fragment
                        Bundle args = new Bundle();

                        //  String
                        args.putString("namedocente", nameDocente);
                        args.putInt("iddocente", idDocente);

                        tipo1FonicoFragment = new Tipo1FonicoFragment();
                        tipo1FonicoFragment.setArguments(args);

                        getSupportFragmentManager().beginTransaction().replace(R.id.container_Tipos_CEFD, tipo1FonicoFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        //Toast.makeText(getApplicationContext(), "Ejercicio tipo 1", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.bott_tipo2_CED:

                        Bundle args2 = new Bundle();
                        args2.putString("namedocente", nameDocente);
                        args2.putInt("iddocente", idDocente);

                        tipo2FonicoFragment = new Tipo2FonicoFragment();
                        tipo2FonicoFragment.setArguments(args2);

                        getSupportFragmentManager().beginTransaction().replace(R.id.container_Tipos_CEFD, tipo2FonicoFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        //Toast.makeText(getApplicationContext(), "Ejercicio tipo 2", Toast.LENGTH_LONG).show();
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
