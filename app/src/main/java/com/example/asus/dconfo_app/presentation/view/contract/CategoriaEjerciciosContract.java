package com.example.asus.dconfo_app.presentation.view.contract;

import android.content.Context;

import com.example.asus.dconfo_app.domain.model.EjercicioG1;

import java.util.List;

public interface CategoriaEjerciciosContract {
    interface Presenter{
        List<EjercicioG1> getListaEjercicios();
        //devuelve el resultado lista, no va a lc caso de uso
        List<String> getLstNombreEjercicios();
        //llama el caso de uso
        void loadListaEjercicios();

        void crearReceta(Context context, String nombre, List<String>ejercicios);
    }
    interface View{
        //void disableButtons();
        void showListaEjercicios();
        void findEjercicios();
        //void getAdapter();
    }
}
