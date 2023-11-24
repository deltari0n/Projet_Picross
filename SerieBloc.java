package com.mycompany.projet_picross;

import java.util.ArrayList;

public class SerieBloc {
    
    // Attribut
    
    private ArrayList<Bloc> ListeBloc;
    
    // Constructeur
    
    public SerieBloc(){
        this.ListeBloc = new ArrayList<Bloc>();
    }
    
    public SerieBloc(Bloc[] Liste){
        this.ListeBloc = new ArrayList<Bloc>();
        for (int i = 0; i< Liste.length ; i++){
            this.ListeBloc.add(Liste[i]);
            
        }
    }
    
    // Méthode
        
    public void Ajout(int[] listeInt){
        for(int i = 0; i< listeInt.length ; i++){
            this.ListeBloc.add(new Bloc(listeInt[i], -1, 1));
        }
    }
    
    
}
