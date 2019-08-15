package com.example.asus.dconfo_app.domain.model;

public class EjercicioG1 {
    private Integer idEjercicio;
    private String nameEjercicio;
    private Integer idDocente;
    private Integer idActividad;
    private Integer idTipo;
    private String rutaImagen;
    private Integer cantidadValida;
    private String oracion;

    public Integer getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEjercicio(Integer idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public String getNameEjercicio() {
        return nameEjercicio;
    }

    public void setNameEjercicio(String nameEjercicio) {
        this.nameEjercicio = nameEjercicio;
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

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public Integer getCantidadValida() {
        return cantidadValida;
    }

    public void setCantidadValida(Integer cantidadValida) {
        this.cantidadValida = cantidadValida;
    }

    public String getOracion() {
        return oracion;
    }

    public void setOracion(String oracion) {
        this.oracion = oracion;
    }
}
