package com.example.asus.dconfo_app.repository;

import com.example.asus.dconfo_app.domain.model.EjercicioG1;

import java.util.List;

public interface interfaceEjercicio {
    void getListaAlimentos();
    void setEjercicioG1(EjercicioG1 ejercicioG1);
    void getAlimento(String name);

    void getlstEjercicios(Callback<List<EjercicioG1>> callback);
    List<EjercicioG1> getlstEjercicios() throws Exception;
}
