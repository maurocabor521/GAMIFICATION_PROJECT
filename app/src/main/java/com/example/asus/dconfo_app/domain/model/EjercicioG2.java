package com.example.asus.dconfo_app.domain.model;

public class EjercicioG2 {
    private Integer idEjercicioG2;
    private String nameEjercicioG2;
    private Integer idDocente;
    private Integer idActividad;
    private Integer idTipo;
    private String letra_inicial_EjercicioG2;
    private String letra_final_EjercicioG2;
    private String cantidadLexemas;
    private String oracion;
    private String rutaImagen;

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getOracion() {
        return oracion;
    }

    public void setOracion(String oracion) {
        this.oracion = oracion;
    }

    public String getCantidadLexemas() {
        return cantidadLexemas;
    }

    public void setCantidadLexemas(String cantidadLexemas) {
        this.cantidadLexemas = cantidadLexemas;
    }

    public Integer getIdEjercicioG2() {
        return idEjercicioG2;
    }

    public void setIdEjercicioG2(Integer idEjercicioG2) {
        this.idEjercicioG2 = idEjercicioG2;
    }

    public String getNameEjercicioG2() {
        return nameEjercicioG2;
    }

    public void setNameEjercicioG2(String nameEjercicioG2) {
        this.nameEjercicioG2 = nameEjercicioG2;
    }

    public Integer getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(Integer idDocente) {
        this.idDocente = idDocente;
    }

    public Integer getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Integer idActividad) {
        this.idActividad = idActividad;
    }

    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }

    public String getLetra_inicial_EjercicioG2() {
        return letra_inicial_EjercicioG2;
    }

    public void setLetra_inicial_EjercicioG2(String letra_inicial_EjercicioG2) {
        this.letra_inicial_EjercicioG2 = letra_inicial_EjercicioG2;
    }

    public String getLetra_final_EjercicioG2() {
        return letra_final_EjercicioG2;
    }

    public void setLetra_final_EjercicioG2(String letra_final_EjercicioG2) {
        this.letra_final_EjercicioG2 = letra_final_EjercicioG2;
    }
}
