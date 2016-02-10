package com.hooptap.sdkbrandclub.Models;

/**
 * Created by carloscarrasco on 10/2/16.
 */
public class HooptapGame extends HooptapItem {

    private String url_game;
    private boolean has_attempts;

    public String getUrl_game() {
        return url_game;
    }

    public void setUrl_game(String url_game) {
        this.url_game = url_game;
    }

    public boolean isHas_attempts() {
        return has_attempts;
    }

    public void setHas_attempts(boolean has_attempts) {
        this.has_attempts = has_attempts;
    }
}
