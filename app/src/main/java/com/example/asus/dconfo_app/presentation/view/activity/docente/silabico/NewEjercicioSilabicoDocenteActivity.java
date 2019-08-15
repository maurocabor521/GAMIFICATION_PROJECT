package com.example.asus.dconfo_app.presentation.view.activity.docente.silabico;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.presentation.view.fragment.Estudiante.silabico.Tipo1silabicoEstudianteFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.Estudiante.silabico.Tipo2silabicoEstudianteFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.docente.fonico.HomeFonicoFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.docente.fonico.Tipo1FonicoFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.docente.fonico.Tipo2FonicoFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.docente.silabico.HomeSilabicoFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.docente.silabico.Tipo1SilabicoFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.docente.silabico.Tipo2SilabicoFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class NewEjercicioSilabicoDocenteActivity extends AppCompatActivity
        implements
        Tipo1SilabicoFragment.OnFragmentInteractionListener,
        Tipo2SilabicoFragment.OnFragmentInteractionListener,
        HomeSilabicoFragment.OnFragmentInteractionListener,
        Tipo1silabicoEstudianteFragment.OnFragmentInteractionListener ,
        Tipo2silabicoEstudianteFragment.OnFragmentInteractionListener {

    private BottomBar bottomBar;
    private String nameDocente = "";
    private int idDocente = 0;
    private int idgrupo ;

    private Tipo1SilabicoFragment tipo1SilabicoFragment;
    private Tipo2SilabicoFragment tipo2SilabicoFragment;
    private HomeSilabicoFragment homeSilabicoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ejercicio_silabico_docente);
        Intent intent = this.getIntent();
        Bundle extra = intent.getExtras();

        nameDocente = extra.getString("namedocente");
        idDocente = extra.getInt("iddocente");
        idgrupo = extra.getInt("idgrupo");

        showToolbar("Nuevo Ejercicio Silábico, Docente: " + nameDocente, true);
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

                        homeSilabicoFragment = new HomeSilabicoFragment();
                        homeSilabicoFragment.setArguments(args1);

                        getSupportFragmentManager().beginTransaction().replace(R.id.container_Tipos_C_SIL, homeSilabicoFragment)
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

                        tipo1SilabicoFragment = new Tipo1SilabicoFragment();
                        tipo1SilabicoFragment.setArguments(args);

                        getSupportFragmentManager().beginTransaction().replace(R.id.container_Tipos_C_SIL, tipo1SilabicoFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        //Toast.makeText(getApplicationContext(), "Ejercicio tipo 1", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.bott_tipo2_CED:

                        Bundle args2 = new Bundle();
                        args2.putString("namedocente", nameDocente);
                        args2.putInt("iddocente", idDocente);

                        tipo2SilabicoFragment = new Tipo2SilabicoFragment();
                        tipo2SilabicoFragment.setArguments(args2);

                        getSupportFragmentManager().beginTransaction().replace(R.id.container_Tipos_C_SIL, tipo2SilabicoFragment)
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
