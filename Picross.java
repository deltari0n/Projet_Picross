package com.mycompany.projet_picross;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Picross {
    
    // Attributs
    
    private int n;
    private int m;
    private SerieBloc[] colonnes;
    private SerieBloc[] lignes;
    private String[] listeColonnes;
    private String[] listeLignes;
    private String[][] image;
    
    // Constructeurs
    
    public Picross(int longueur, int largeur, SerieBloc[] colonnes, SerieBloc[] lignes){
        this.n = longueur;
        this.m = largeur;
        this.colonnes = colonnes;
        this.lignes = lignes;
        this.image = new String[longueur][largeur];
    }
    
    public Picross(int longueur, int largeur){
        this.n = longueur;
        this.m = largeur;
        this.image = new String[longueur][largeur];

    }
    
    public Picross(){
        this.n=0;
        this.m=0;
            
    }
    
    // Getter et Setter
    public int getNbColonne(){
        return this.m;
    }
    public int getNbLignes(){
        return this.n;
    }
    public SerieBloc[] colonnes(){
        return this.colonnes;
    }
    public SerieBloc[] lignes(){
        return this.lignes;
    }
        
        
    // récupération des données
    public void lectureFichier(String nomDuFichier){
        try{
            BufferedReader fichier = new BufferedReader(new FileReader(nomDuFichier));
            String ligne;
            int l,c;
            l = Integer.parseInt(fichier.readLine());
            c = Integer.parseInt(fichier.readLine());
            this.listeLignes = new String[l];
            this.listeColonnes = new String[c];
            fichier.readLine();
            for (int i=0; i<this.listeLignes.length;i++){
                this.listeLignes[i] = fichier.readLine();
            }
            fichier.readLine();
            for (int j=0; j<this.listeColonnes.length;j++){
                this.listeColonnes[j] = fichier.readLine();
            }
            fichier.close() ;
            } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
        
    // Construction du Damier
    
    public void Damier1(){
        // Tableau
        // Bord haut
        System.out.print("  ");
        for (int i = 0 ; i < this.n ; i++){
            System.out.print("---");
        }
        // Entre les bord haut et bas
        for (int j = 0; j < this.n ; j++){
            System.out.println();
            System.out.print(j+"|");
            for (int k = 0; k < this.n ; k++){
                System.out.print("   ");
            }
            System.out.print("|");
        }
        // Bord bas
        System.out.print("  ");
        System.out.println();
        for (int i = 0 ; i < this.n ; i++){
            System.out.print("---");
        }
    }
       
    
        
}
