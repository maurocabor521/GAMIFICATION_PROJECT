package com.example.asus.dconfo_app.repository;

import com.example.asus.dconfo_app.domain.model.EjercicioG1;

import java.util.List;

public class ImpUseCaseLstEjercicios {

    private List<EjercicioG1> lstEjericiosG1;
    public void getlstEjercicios(final Callback<List<EjercicioG1>> callback) {
        new ThreadExecutor<List<EjercicioG1>>()
                .execute(new ThreadExecutor.Task<List<EjercicioG1>>() {
                    @Override
                    public List<EjercicioG1> execute() throws Exception {
                        //Llamo al metodo sincrono del UseCase
                        return getLstEjericiosG1();
                    }

                    @Override
                    public void finish(Exception error, List<EjercicioG1> result) {
                        if (error == null) {
                            callback.success(result);
                        } else {
                            callback.error(error);
                        }
                    }
                });
    }


    public List<EjercicioG1> getLstEjericiosG1() throws Exception {

        //return alimentosRepository.getAllAlimentos();
        return null ;
    }
}
