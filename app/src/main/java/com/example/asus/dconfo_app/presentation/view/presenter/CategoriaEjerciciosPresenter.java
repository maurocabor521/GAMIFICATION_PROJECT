package com.example.asus.dconfo_app.presentation.view.presenter;

import android.content.Context;

import com.example.asus.dconfo_app.domain.model.EjercicioG1;
import com.example.asus.dconfo_app.domain.model.caseuse.impl.EjerciciosCaseUse;
import com.example.asus.dconfo_app.domain.model.caseuse.impl.impl.EjerciciosCaseUseImpl;
import com.example.asus.dconfo_app.helpers.Callback;
import com.example.asus.dconfo_app.presentation.view.contract.CategoriaEjerciciosContract;

import java.util.ArrayList;
import java.util.List;

public class CategoriaEjerciciosPresenter implements CategoriaEjerciciosContract.Presenter {

    private CategoriaEjerciciosContract.View view;
    private EjerciciosCaseUse ejerciciosCaseUse;
    private List<EjercicioG1> lstEjercicios;
    private List<String> lstNombresEjercicios;
    private Context context;


    public CategoriaEjerciciosPresenter(CategoriaEjerciciosContract.View view, Context context) {
        this.view = view;
        this.context = context;
        this.lstEjercicios = new ArrayList<>(0);
        this.lstNombresEjercicios = new ArrayList<>(0);
        ejerciciosCaseUse = new EjerciciosCaseUseImpl(context);
    }

    @Override
    public List<EjercicioG1> getListaEjercicios() {
        return lstEjercicios;
    }

    @Override
    public List<String> getLstNombreEjercicios() {
        return lstNombresEjercicios;
    }

    @Override
    public void loadListaEjercicios() {
        ejerciciosCaseUse.getlstEjercicios(new Callback<List<EjercicioG1>>() {
            @Override
            public void success(List<EjercicioG1> result) {
                System.out.println("result: "+result);
                lstEjercicios.clear();
                lstEjercicios.addAll(result);
                lstNombresEjercicios.clear();
                lstNombresEjercicios.add("Seleccione Ejercicio");

                for (EjercicioG1 ejercicioG1 : lstEjercicios) {
                    lstNombresEjercicios.add(ejercicioG1.getNameEjercicio());
                }
                view.showListaEjercicios();

            }

            @Override
            public void error(Exception error) {
                error.printStackTrace();
            }
        });

    }

    @Override
    public void crearReceta(Context context, String nombre, List<String> ejercicios) {

    }
}
