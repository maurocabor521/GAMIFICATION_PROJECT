package com.example.asus.dconfo_app.domain.model.caseuse.impl;

import com.example.asus.dconfo_app.domain.model.EjercicioG1;
import com.example.asus.dconfo_app.helpers.Callback;

import java.util.List;

public interface EjerciciosCaseUse {

    void getlstEjercicios(Callback<List<EjercicioG1>> callback);

    List<EjercicioG1> getlstEjercicios() throws Exception;
}
