/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projet_pi;

/**
 *
 * @author vruche
 */
public class Bloc {
    
    // Attributs
    
    private int taille;
    private int debut;
    private int fin;
    private int nature;
    
    // Constructeurs
    
    public Bloc(int taille){
        this.taille = taille;
        this.debut = -1;
        this.fin = -1;
        this.nature = -1;
    }
    
    public Bloc(int taille, int debut){
        this.taille = taille;
        this.debut = debut;
        this.fin = debut + taille-1 ;
        this.nature = -1;
    }
    
    public Bloc(int taille, int debut, int nature){
        this.taille = taille;
        this.debut = debut;
        this.fin = debut + taille-1;
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

    public int getFin() {
        return fin;
    }

    public void setFin(int fin) {
        this.fin = fin;
    }

    public int getNature() {
        return nature;
    }

    public void setNature(int nature) {
        this.nature = nature;
    }
    
    // equals

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Bloc other = (Bloc) obj;
        return this.taille == other.taille;
    }
    
    
    // toString
    
    public String toString(){
        return this.debut + ";" + this.fin + ";" + this.taille + ";" + this.nature;
    }
    
    // clone
    
    public Bloc cloneBloc(){
        return new Bloc(this.taille,this.debut,this.nature);
    }
    
}