package com.example.asus.dconfo_app.domain.model;

public class ConcienciaTipo {
    private String titleConciencia;
    private String Description;
    private String urlImage;

    public ConcienciaTipo(String titleConciencia, String description, String urlImage) {
        this.titleConciencia = titleConciencia;
        Description = description;
        this.urlImage = urlImage;
    }

    public String getTitleConciencia() {
        return titleConciencia;
    }

    public void setTitleConciencia(String titleConciencia) {
        this.titleConciencia = titleConciencia;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
