package com.tw.userapp.service.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class CVUserDetails implements Serializable {


    @JsonProperty("info_perso")
    private InfoPerso infoPerso;
    private String education;



    public InfoPerso getInfoPerso() {
        return infoPerso;
    }

    public void setInfoPerso(InfoPerso infoPerso) {
        this.infoPerso = infoPerso;
    }

    @Override
    public String toString() {
        return "CVUserDetails{" +
            "personnel Info= " + infoPerso +
            '}';
    }
}
