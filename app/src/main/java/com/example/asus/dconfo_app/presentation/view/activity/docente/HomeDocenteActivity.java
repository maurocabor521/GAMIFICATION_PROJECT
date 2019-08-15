package com.example.asus.dconfo_app.presentation.view.activity.docente;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.asus.dconfo_app.MainActivity;
import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.domain.model.ConcienciaTipo;
import com.example.asus.dconfo_app.presentation.view.activity.docente.actividades.ActividadDocenteActivity;
import com.example.asus.dconfo_app.presentation.view.activity.docente.ejercicios.MisEjerciciosActivity;
import com.example.asus.dconfo_app.presentation.view.activity.docente.fonico.AsignarEjercicioFonicoActivity;
import com.example.asus.dconfo_app.presentation.view.activity.docente.fonico.NewEjercicioFonicoActivity;
import com.example.asus.dconfo_app.presentation.view.activity.docente.fonico.NewEjercicioFonicoDocenteActivity;
import com.example.asus.dconfo_app.presentation.view.activity.docente.grupos.GrupoDocenteActivity;
import com.example.asus.dconfo_app.presentation.view.activity.docente.notas.NotasActivity;
import com.example.asus.dconfo_app.presentation.view.activity.docente.notas.PorcentNotasActivity;
import com.example.asus.dconfo_app.presentation.view.activity.docente.reportes.ShowReportsDocActivity;
import com.example.asus.dconfo_app.presentation.view.activity.docente.silabico.AsignarEjercicioSilabicoActivity;
import com.example.asus.dconfo_app.presentation.view.activity.docente.silabico.NewEjercicioSilabicoDocenteActivity;
import com.example.asus.dconfo_app.presentation.view.adapter.ConcienciaTipoAdapterRecyclerView;

import java.util.ArrayList;

public class HomeDocenteActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Intent intentCED;
    Intent intentCreateEjercicioFonico;
    Intent intentAsignarDeber;
    Intent intentAsignarDeberFonico;
    Intent intentActividades;
    Intent intentGrupos;
    Intent intentEjercicios;
    Intent intentConsultarNotas;
    Intent intentConsultarPorcentajes;
    Intent intentShowReportes;
    String namegrupo;
    String namedocente;
    int iddocente;
    int idgrupo;
    Button btnInicio;
    RecyclerView rv_docente_conciencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_docente);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view1);
        navigationView.setNavigationItemSelectedListener(this);

        ManageCursosDocenteActivity intent = new ManageCursosDocenteActivity();
//        Bundle datos = intent.getIntent().getBundleExtra("idgrupo");

        Bundle datos = this.getIntent().getExtras();
        idgrupo = datos.getInt("idgrupo");
        int idcurso = datos.getInt("idcurso");
        namegrupo = datos.getString("namegrupo");

        namedocente = datos.getString("nameDoc");
        iddocente = datos.getInt("idDoc");
        //toolbar.setLabelFor();
        this.setTitle("Docente: " + namedocente + " - Id Curso: " + idcurso + " - " + namegrupo);
       /* btnInicio = (Button) findViewById(R.id.btn_docente_content);
        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "idgrupo_btn: " + idgrupo, Toast.LENGTH_LONG).show();
            }
        });*/

        rv_docente_conciencias = (RecyclerView) findViewById(R.id.rv_docente_conciencias);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_docente_conciencias.setLayoutManager(linearLayoutManager);

        ConcienciaTipoAdapterRecyclerView concienciaTipoAdapterRecyclerView = new ConcienciaTipoAdapterRecyclerView(buidConcienciaTipo());
        System.out.println("lista conciencias: " + buidConcienciaTipo().toString());
        rv_docente_conciencias.setAdapter(concienciaTipoAdapterRecyclerView);

        //Toast.makeText(getApplicationContext(), "idgrupo: " + idgrupo, Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplicationContext(), "idcurso: " + idcurso, Toast.LENGTH_LONG).show();
    }

    public ArrayList<ConcienciaTipo> buidConcienciaTipo() {
        ArrayList<ConcienciaTipo> conciencia_tipo = new ArrayList<>();
        conciencia_tipo.add(new ConcienciaTipo("Conciencia Fónica",
                "La conciencia Fónica se refiere a la habilidad de escuchar, " +
                        "identificar y manipular los fonemas— el objetivo de esta actividad es" +
                        "que el estudiante se percate de los fonemas que contiene la palabra. ", ""));

        conciencia_tipo.add(new ConcienciaTipo("Conciencia Sílabica",
                "El objetivo de estas actividades es que el estudiante marque un" +
                        "cuadro por cada silaba que la palabra contenga ", ""));
        conciencia_tipo.add(new ConcienciaTipo("Conciencia Léxica",
                "conciencia léxica se define como la capacidad para percibir que una oración" +
                        " o enunciado puede ser segmentado en palabras." +
                        "como objetivo marcar con cuadros el número de palabras que contenga la oración. ", ""));

        return conciencia_tipo;
    }

   /* public ArrayList<Picture> buidPicture() {
        ArrayList<Picture> pictures = new ArrayList<>();
        pictures.add(
                new Picture("https://www.hcn.org/issues/47.2/this-land-is-their-land/publicland11-jpg/image",
                        "Uriel Ramirez", "4 días", "7 Me gusta")
        );
        pictures.add(
                new Picture("http://hardboilednews.com/wp-content/uploads/2016/11/Crazy-Mountains2.jpeg",
                        "Camilo Medina", "2 días", "1 Me gusta")
        );
        pictures.add(
                new Picture("https://www.hcn.org/issues/47.2/this-land-is-their-land/publicland11-jpg/image",
                        "Luisa Jimenez", "8 días", "6 Me gusta")
        );
        return pictures;
    }*/

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_docente, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            finish();
            return true;
        } else if (id == R.id.action_off) {
            finish();
            Intent intent = new Intent(HomeDocenteActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        NavigationView nv = (NavigationView) findViewById(R.id.nav_view1);
        Menu m = nv.getMenu();

        if (id == R.id.nav_misgrupos) {
          /*  Intent intent = new Intent(HomeDocenteActivity.this, ManageCursosDocenteActivity.class);
            startActivity(intent);*/
            Bundle args = new Bundle();
            args.putInt("iddocente", iddocente);
            args.putInt("idgrupo", idgrupo);
            intentGrupos = new Intent(HomeDocenteActivity.this, GrupoDocenteActivity.class);
            intentGrupos.putExtras(args);
            startActivity(intentGrupos);

        } else if (id == R.id.nav_actividades) {
            Bundle args = new Bundle();
            args.putInt("iddocente", iddocente);
            args.putInt("idgrupo", idgrupo);
            intentActividades = new Intent(HomeDocenteActivity.this, ActividadDocenteActivity.class);
            intentActividades.putExtras(args);
            startActivity(intentActividades);

        } else if (id == R.id.nav_misejercicios) {
            Bundle args = new Bundle();
            args.putInt("iddocente", iddocente);
            args.putInt("idgrupo", idgrupo);
            intentEjercicios = new Intent(HomeDocenteActivity.this, MisEjerciciosActivity.class);
            intentEjercicios.putExtras(args);
            startActivity(intentEjercicios);

        } else if (id == R.id.nav_asignardeber) {
            //intentAsignarDeber = new Intent(HomeDocenteActivity.this, AsignarDeberDocenteActivity.class);
            Bundle args = new Bundle();
            args.putInt("iddocente", iddocente);
            args.putInt("idgrupo", idgrupo);
            intentAsignarDeber = new Intent(HomeDocenteActivity.this, AsignarEstudianteDeberActivity.class);
            intentAsignarDeber.putExtras(args);
            startActivity(intentAsignarDeber);

        } else if (id == R.id.nav_consultar_notas) {
            Bundle args = new Bundle();
            args.putInt("iddocente", iddocente);
            args.putInt("idgrupo", idgrupo);
            intentConsultarNotas = new Intent(HomeDocenteActivity.this, NotasActivity.class);
            intentConsultarNotas.putExtras(args);
            startActivity(intentConsultarNotas);

        } else if (id == R.id.nav_con_lexica) {
            // Handle the camera action
            boolean b = !m.findItem(R.id.nav_con_lexica_actividades).isVisible();
            //setting submenus visible state
            m.findItem(R.id.nav_con_lexica_actividades).setVisible(b);
            m.findItem(R.id.nav_con_lexica_ejercicios).setVisible(b);
            m.findItem(R.id.nav_con_lexica_asignar).setVisible(b);
            m.findItem(R.id.nav_con_lexica_estudiantes).setVisible(b);
            return true;

        } else if (id == R.id.nav_con_fonica) {
            // Handle the camera action
            boolean b = !m.findItem(R.id.nav_con_fonica_actividades).isVisible();
            //boolean b = !m.findItem(R.id.nav_con_fonica_ejercicios).isVisible();
            //setting submenus visible state
            m.findItem(R.id.nav_con_fonica_actividades).setVisible(false);
            m.findItem(R.id.nav_con_fonica_ejercicios).setVisible(false);
            m.findItem(R.id.nav_con_fonica_asignar).setVisible(b);
            //m.findItem(R.id.nav_con_fonica_estudiantes).setVisible(b);
            return true;
        } else if (id == R.id.nav_con_lexica_ejercicios) {
            Bundle parametros = new Bundle();
            parametros.putInt("iddocente", iddocente);
            parametros.putString("namedocente", namedocente);
            parametros.putInt("idgrupo", idgrupo);//nuevo 040719

            //intentCED = new Intent(HomeDocenteActivity.this, ManageEjercicioDocenteActivity.class);
            intentCED = new Intent(HomeDocenteActivity.this, NewEjercicioDocenteActivity.class);
            intentCED.putExtras(parametros);
            startActivity(intentCED);


        } else if (id == R.id.nav_con_fonica_ejercicios) {
            Bundle parametros1 = new Bundle();
            parametros1.putInt("iddocente", iddocente);
            parametros1.putString("namedocente", namedocente);
            parametros1.putInt("idgrupo", idgrupo);//nuevo 040719

            intentCreateEjercicioFonico = new Intent(HomeDocenteActivity.this, NewEjercicioFonicoDocenteActivity.class);
            intentCreateEjercicioFonico.putExtras(parametros1);
            startActivity(intentCreateEjercicioFonico);
            String TAG = "TAG";
            Log.i(TAG, "iddocente: " + iddocente);
            Log.i(TAG, "namedocente: " + namedocente);

        } else if (id == R.id.nav_con_fonica_asignar) {
            Bundle parametros1 = new Bundle();
            parametros1.putInt("iddocente", iddocente);
            parametros1.putString("namedocente", namedocente);
            parametros1.putInt("idgrupo", idgrupo);

            intentAsignarDeberFonico = new Intent(HomeDocenteActivity.this, AsignarEjercicioFonicoActivity.class);
            intentAsignarDeberFonico.putExtras(parametros1);
            startActivity(intentAsignarDeberFonico);
            String TAG = "TAG";
            Log.i(TAG, "iddocente: " + iddocente);
            Log.i(TAG, "namedocente: " + namedocente);

        } else if (id == R.id.nav_con_silabica) {
            // Handle the camera action
            boolean b = !m.findItem(R.id.nav_con_silabica_actividades).isVisible();
            //setting submenus visible state
            m.findItem(R.id.nav_con_silabica_actividades).setVisible(b);
            m.findItem(R.id.nav_con_silabica_ejercicios).setVisible(b);
            m.findItem(R.id.nav_con_silabica_asignar).setVisible(b);
            m.findItem(R.id.nav_con_silabica_estudiantes).setVisible(b);
            return true;
        } else if (id == R.id.nav_con_silabica_ejercicios) {
            Bundle parametros1 = new Bundle();
            parametros1.putInt("iddocente", iddocente);
            parametros1.putString("namedocente", namedocente);
            parametros1.putInt("idgrupo", idgrupo);

            intentCreateEjercicioFonico = new Intent(HomeDocenteActivity.this, NewEjercicioSilabicoDocenteActivity.class);
            intentCreateEjercicioFonico.putExtras(parametros1);
            startActivity(intentCreateEjercicioFonico);
            String TAG = "TAG";
            Log.i(TAG, "iddocente: " + iddocente);
            Log.i(TAG, "namedocente: " + namedocente);

        } else if (id == R.id.nav_con_silabica_asignar) {
            Bundle parametros1 = new Bundle();
            parametros1.putInt("iddocente", iddocente);
            parametros1.putString("namedocente", namedocente);
            parametros1.putInt("idgrupo", idgrupo);

            intentAsignarDeberFonico = new Intent(HomeDocenteActivity.this, AsignarEjercicioSilabicoActivity.class);
            intentAsignarDeberFonico.putExtras(parametros1);
            startActivity(intentAsignarDeberFonico);
            String TAG = "TAG";
            Log.i(TAG, "iddocente: " + iddocente);
            Log.i(TAG, "namedocente: " + namedocente);

        }
        /*else if (id == R.id.nav_reportes) {
            Bundle parametros1 = new Bundle();
            parametros1.putInt("iddocente", iddocente);
            parametros1.putString("namedocente", namedocente);
            parametros1.putInt("idgrupo", idgrupo);

           // intentConsultarPorcentajes = new Intent(HomeDocenteActivity.this, PorcentNotasActivity.class);
            intentShowReportes = new Intent(HomeDocenteActivity.this, NotasActivity.class);
            intentShowReportes.putExtras(parametros1);
            startActivity(intentShowReportes);
            String TAG = "TAG";
            Log.i(TAG, "iddocente: " + iddocente);
            Log.i(TAG, "namedocente: " + namedocente);

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }//menuitem


}
