package com.example.asus.dconfo_app.domain.model;

public class GrupoEstudiantesHasDeber {
    private Integer id_GE_H_D;
    private Integer grupo_estudiante_idgrupo_estudiante;
    private Integer EjercicioG2_idEjercicioG2;
    private Integer Asignacion_idGrupoAsignacion;
    private String fecha_gehd;
    private String tipo_gehd;

    public Integer getId_GE_H_D() {
        return id_GE_H_D;
    }

    public void setId_GE_H_D(Integer id_GE_H_D) {
        this.id_GE_H_D = id_GE_H_D;
    }

    public Integer getGrupo_estudiante_idgrupo_estudiante() {
        return grupo_estudiante_idgrupo_estudiante;
    }

    public void setGrupo_estudiante_idgrupo_estudiante(Integer grupo_estudiante_idgrupo_estudiante) {
        this.grupo_estudiante_idgrupo_estudiante = grupo_estudiante_idgrupo_estudiante;
    }

    public Integer getEjercicioG2_idEjercicioG2() {
        return EjercicioG2_idEjercicioG2;
    }

    public void setEjercicioG2_idEjercicioG2(Integer ejercicioG2_idEjercicioG2) {
        EjercicioG2_idEjercicioG2 = ejercicioG2_idEjercicioG2;
    }

    public Integer getAsignacion_idGrupoAsignacion() {
        return Asignacion_idGrupoAsignacion;
    }

    public void setAsignacion_idGrupoAsignacion(Integer asignacion_idGrupoAsignacion) {
        Asignacion_idGrupoAsignacion = asignacion_idGrupoAsignacion;
    }

    public String getFecha_gehd() {
        return fecha_gehd;
    }

    public void setFecha_gehd(String fecha_gehd) {
        this.fecha_gehd = fecha_gehd;
    }

    public String getTipo_gehd() {
        return tipo_gehd;
    }

    public void setTipo_gehd(String tipo_gehd) {
        this.tipo_gehd = tipo_gehd;
    }
}
