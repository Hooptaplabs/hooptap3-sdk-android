package com.hooptap.sdkgameroom.Customs;

import java.io.Serializable;

/**
 * Clase custom para encapsular los datos de un juego y facilitar su uso
 *
 * @author Hooptap Team
 */
public class HooptapGame implements Serializable{
    private String image;
    private String title;
    private String url;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
