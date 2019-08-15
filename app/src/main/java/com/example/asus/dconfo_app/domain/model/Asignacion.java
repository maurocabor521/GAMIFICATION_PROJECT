package com.example.asus.dconfo_app.domain.model;

public class Asignacion {
    private Integer idGrupoAsignacion;
    private String nameGrupoAsignacion;
    private Integer docente_iddocente;
    private Integer grupo_idgrupo;
    private Integer Actividad_idActividad;

    public Integer getIdGrupoAsignacion() {
        return idGrupoAsignacion;
    }

    public void setIdGrupoAsignacion(Integer idGrupoAsignacion) {
        this.idGrupoAsignacion = idGrupoAsignacion;
    }

    public String getNameGrupoAsignacion() {
        return nameGrupoAsignacion;
    }

    public void setNameGrupoAsignacion(String nameGrupoAsignacion) {
        this.nameGrupoAsignacion = nameGrupoAsignacion;
    }

    public Integer getDocente_iddocente() {
        return docente_iddocente;
    }

    public void setDocente_iddocente(Integer docente_iddocente) {
        this.docente_iddocente = docente_iddocente;
    }

    public Integer getGrupo_idgrupo() {
        return grupo_idgrupo;
    }

    public void setGrupo_idgrupo(Integer grupo_idgrupo) {
        this.grupo_idgrupo = grupo_idgrupo;
    }

    public Integer getActividad_idActividad() {
        return Actividad_idActividad;
    }

    public void setActividad_idActividad(Integer actividad_idActividad) {
        Actividad_idActividad = actividad_idActividad;
    }
}
