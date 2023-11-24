package com.mycompany.projet_picross;

public class Bloc {
    
    // Attributs
    
    private int taille;
    private int debut;
    private int nature;
    
    // Constructeurs
    
    public Bloc(int taille){
        this.taille = taille;
        this.debut = -1;
        this.nature = -1;
    }
    
    public Bloc(int taille, int debut){
        this.taille = taille;
        this.debut = debut;
        this.nature = -1;
    }
    
        public Bloc(int taille, int debut, int nature){
        this.taille = taille;
        this.debut = debut;
        this.nature = nature;
    }
    
    // Getter et Setter

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public int getDebut() {
        return debut;
    }

    public void setDebut(int debut) {
        this.debut = debut;
    }

    public int getNature() {
        return nature;
    }

    public void setNature(int nature) {
        this.nature = nature;
    }
    
    
    
    
    
}
