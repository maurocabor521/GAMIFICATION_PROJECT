package com.example.asus.dconfo_app.presentation.view.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;



import java.util.List;

/**
 * Created by Andrés Cabal on 6/05/2018.
 */

public class SpinnerEjerciciosAdapter {
    private Spinner spinner;
    private List<String> lstNombresEjercicios;
    private View view;
    Context context;
   // private CategoriaAlimentosContract.View view;

  /*  public SpinnerAdapter(Context context, List<String> lstNombresAlimentos, CategoriaAlimentosContract.View view) {
        this.lstNombresAlimentos = lstNombresAlimentos;
        this.context = context;
        this.view = view;
        //construirAdapter();
    }*/

    public SpinnerEjerciciosAdapter(Context context, List<String> lstNombresEjercicios, View view, Spinner spinner) {
        this.spinner = spinner;
        this.lstNombresEjercicios = lstNombresEjercicios;
        this.context = context;
        this.view = view;
        construirAdapter();

    }

    /*  public SpinnerAdapter(Context context, List<String> lstNombresAlimentos ) {

        this.lstNombresAlimentos = lstNombresAlimentos;
        this.context = context;
        //spinner=context.
        construirAdapter();
    }*/

    public SpinnerEjerciciosAdapter(View view, List<String> lstNombresAlimentos) {
    }

    public void construirAdapter() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, lstNombresEjercicios);
        // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //return adapter;
        spinner.setAdapter(adapter);
       // view.getAdapter();
    }

    public Spinner getSpinner() {
        return spinner;
    }
}
