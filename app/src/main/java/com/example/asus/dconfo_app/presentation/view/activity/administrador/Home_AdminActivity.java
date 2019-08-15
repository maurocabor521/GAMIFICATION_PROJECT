package com.example.asus.dconfo_app.presentation.view.activity.administrador;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.presentation.view.fragment.administrador.AllCursosFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.administrador.AllotDocenteToGrupoFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.administrador.AllotEstudianteToGrupoFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.administrador.AllotGrupoToCursoFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.administrador.ConsultEstudianteFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.administrador.ListNewDocenteFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.administrador.NewCursoFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.administrador.NewDocenteFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.administrador.NewEstudianteFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.administrador.NewListEstudianteFragment;

public class Home_AdminActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        NewDocenteFragment.OnFragmentInteractionListener,
        ListNewDocenteFragment.OnFragmentInteractionListener,
        NewEstudianteFragment.OnFragmentInteractionListener,
        NewListEstudianteFragment.OnFragmentInteractionListener,
        NewCursoFragment.OnFragmentInteractionListener,
        AllotDocenteToGrupoFragment.OnFragmentInteractionListener,
        AllotEstudianteToGrupoFragment.OnFragmentInteractionListener,
        AllotGrupoToCursoFragment.OnFragmentInteractionListener,
        AllCursosFragment.OnFragmentInteractionListener,
        ConsultEstudianteFragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__admin);
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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

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
        getMenuInflater().inflate(R.menu.home__admin, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_newDocente) {
            Toast.makeText(getApplicationContext(), "docente", Toast.LENGTH_SHORT).show();
            replaceFragment(NewDocenteFragment.getInstance(), true);

          /*  getSupportFragmentManager().beginTransaction().replace(R.id.content_main, newDocenteFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null).commit();*/
        } else if (id == R.id.nav_newListDocente) {
            Toast.makeText(getApplicationContext(), "lista docente", Toast.LENGTH_SHORT).show();
            replaceFragment(ListNewDocenteFragment.getInstance(), true);
        } else if (id == R.id.nav_new_Estudiante) {
            replaceFragment(NewEstudianteFragment.getInstance(), true);
        } else if (id == R.id.nav_new_ListEstudiante) {
            replaceFragment(NewListEstudianteFragment.getInstance(), true);
        } else if (id == R.id.nav_new_Curso) {
            replaceFragment(NewCursoFragment.getInstance(), true);

        } else if (id == R.id.nav_allocate_grupo_to_curso) {
            replaceFragment(AllotGrupoToCursoFragment.getInstance(), true);

        } else if (id == R.id.nav_allocate_docente_to_grupo) {
            replaceFragment(AllotDocenteToGrupoFragment.getInstance(), true);

        } else if (id == R.id.nav_allocate_estudiante_to_grupo) {
            replaceFragment(AllotEstudianteToGrupoFragment.getInstance(), true);

        } else if (id == R.id.nav_new_Act_Curso) {
            Intent intent = new Intent(Home_AdminActivity.this, Home_NewCursoActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_All_Cursos) {
            replaceFragment(AllotGrupoToCursoFragment.getInstance(), true);
        } else if (id == R.id.nav_consult_estudiante) {
            replaceFragment(ConsultEstudianteFragment.getInstance(), true);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void replaceFragment(Fragment fragment, boolean addToBackstack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_main, fragment);
        if (addToBackstack) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
