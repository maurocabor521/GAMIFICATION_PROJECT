package com.example.asus.dconfo_app.domain.model;

public class Imagen {
    private Integer idImagen;
    private String nameImagen;
    private String rutaImagen;
    private String letraInicialImagen;
    private String letraFinalImagen;
    private Integer cantSilabasImagen;

    public Integer getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(Integer idImagen) {
        this.idImagen = idImagen;
    }

    public String getNameImagen() {
        return nameImagen;
    }

    public void setNameImagen(String nameImagen) {
        this.nameImagen = nameImagen;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getLetraInicialImagen() {
        return letraInicialImagen;
    }

    public void setLetraInicialImagen(String letraInicialImagen) {
        this.letraInicialImagen = letraInicialImagen;
    }

    public String getLetraFinalImagen() {
        return letraFinalImagen;
    }

    public void setLetraFinalImagen(String letraFinalImagen) {
        this.letraFinalImagen = letraFinalImagen;
    }

    public Integer getCantSilabasImagen() {
        return cantSilabasImagen;
    }

    public void setCantSilabasImagen(Integer cantSilabasImagen) {
        this.cantSilabasImagen = cantSilabasImagen;
    }
}
