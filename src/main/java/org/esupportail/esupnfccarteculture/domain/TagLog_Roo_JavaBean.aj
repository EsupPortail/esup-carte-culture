// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.esupportail.esupnfccarteculture.domain;

import java.util.Date;
import org.esupportail.esupnfccarteculture.domain.Etudiant;
import org.esupportail.esupnfccarteculture.domain.Salle;
import org.esupportail.esupnfccarteculture.domain.TagLog;

privileged aspect TagLog_Roo_JavaBean {
    
    public Etudiant TagLog.getEtudiant() {
        return this.etudiant;
    }
    
    public void TagLog.setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }
    
    public Salle TagLog.getSalle() {
        return this.salle;
    }
    
    public void TagLog.setSalle(Salle salle) {
        this.salle = salle;
    }
    
    public Date TagLog.getDate() {
        return this.date;
    }
    
    public void TagLog.setDate(Date date) {
        this.date = date;
    }
    
    public int TagLog.getTarif() {
        return this.tarif;
    }
    
    public void TagLog.setTarif(int tarif) {
        this.tarif = tarif;
    }
    
    public String TagLog.getEppnInit() {
        return this.eppnInit;
    }
    
    public void TagLog.setEppnInit(String eppnInit) {
        this.eppnInit = eppnInit;
    }
    
}
