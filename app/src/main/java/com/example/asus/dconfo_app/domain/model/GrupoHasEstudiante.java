package com.example.asus.dconfo_app.domain.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GrupoHasEstudiante {
    private Integer idgrupo;
    private Integer idestudiante;
    private ArrayList<Integer> listIdestudiante;

    public Integer getIdgrupo() {
        return idgrupo;
    }

    public void setIdgrupo(Integer idgrupo) {
        this.idgrupo = idgrupo;
    }

    public Integer getIdestudiante() {
        return idestudiante;
    }

    public void setIdestudiante(Integer idestudiante) {
        this.idestudiante = idestudiante;
    }

    public ArrayList<Integer> getListIdestudiante() {
        return listIdestudiante;
    }

    public void setListIdestudiante(ArrayList<Integer> listIdestudiante) {
        this.listIdestudiante = listIdestudiante;
    }
}
