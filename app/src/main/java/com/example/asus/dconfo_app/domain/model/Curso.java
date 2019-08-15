package com.example.asus.dconfo_app.domain.model;

public class Curso {
    private Integer idCurso;
    private Integer idInstitutoCurso;
    private String nameCurso;
    private String periodoCurso;

    public Integer getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Integer idCurso) {
        this.idCurso = idCurso;
    }

    public Integer getIdInstitutoCurso() {
        return idInstitutoCurso;
    }

    public void setIdInstitutoCurso(Integer idInstitutoCurso) {
        this.idInstitutoCurso = idInstitutoCurso;
    }

    public String getNameCurso() {
        return nameCurso;
    }

    public void setNameCurso(String nameCurso) {
        this.nameCurso = nameCurso;
    }

    public String getPeriodoCurso() {
        return periodoCurso;
    }

    public void setPeriodoCurso(String periodoCurso) {
        this.periodoCurso = periodoCurso;
    }
}
