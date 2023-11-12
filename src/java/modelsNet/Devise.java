/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelsNet;

import java.sql.Date;
import java.time.LocalDateTime;

/**
 *
 * @author Tafitasoa-P15B-140
 */
public class Devise {
    Vola vola;
    float coursAr;
    float tauxvente;
    Date dateheure;

    public Vola getVola() {
        return vola;
    }

    public void setVola(Vola vola) {
        this.vola = vola;
    }

    public float getCoursAr() {
        return coursAr;
    }

    public void setCoursAr(float coursAr) {
        this.coursAr = coursAr;
    }

    public Date getDateheure() {
        return dateheure;
    }

    public void setDateheure(Date dateheure) {
        this.dateheure = dateheure;
    }

    public float getTauxvente() {
        return tauxvente;
    }

    public void setTauxvente(float tauxvente) {
        this.tauxvente = tauxvente;
    }
    
    public Devise() {
    }
    
}
