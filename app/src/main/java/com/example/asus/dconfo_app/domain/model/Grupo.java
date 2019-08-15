package com.example.asus.dconfo_app.domain.model;

public class Grupo {
    private Integer idGrupo;
    private String nameGrupo;
    private Integer curso_idCurso;
    private Integer idInstituto;
    private Integer idDocente;

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getNameGrupo() {
        return nameGrupo;
    }

    public void setNameGrupo(String nameGrupo) {
        this.nameGrupo = nameGrupo;
    }

    public Integer getCurso_idCurso() {
        return curso_idCurso;
    }

    public void setCurso_idCurso(Integer curso_idCurso) {
        this.curso_idCurso = curso_idCurso;
    }

    public Integer getIdInstituto() {
        return idInstituto;
    }

    public void setIdInstituto(Integer idInstituto) {
        this.idInstituto = idInstituto;
    }

    public Integer getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(Integer idDocente) {
        this.idDocente = idDocente;
    }
}
