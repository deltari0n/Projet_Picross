/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projet_pi;

/**
 *
 * @author vruche
 */
import java.util.ArrayList;

public class SerieBloc {
    
    // Attribut
    
    private ArrayList<Bloc> ListeBloc;
    
    // Constructeur
    
    public SerieBloc(){
        this.ListeBloc = new ArrayList<Bloc>();
    }
    
    public SerieBloc(ArrayList<Integer> listeTaille){
        this.ListeBloc = new ArrayList<Bloc>();
        for(int i = 0 ; i < listeTaille.size() ; i++){
            this.ListeBloc.add(new Bloc(listeTaille.get(i)));
        }
    }

    //getteur et méthode classique
    
    public int getNbBlocs(){
        return ListeBloc.size();
    }
    
    public Bloc get(int i){
        return ListeBloc.get(i);
    }
    
    public ArrayList<Bloc> getListeBloc() {
        return ListeBloc;
    }
        
    public String toString(){
        String s = "";
        for(int i = 0; i< this.ListeBloc.size()-1; i++){
            s = s + this.ListeBloc.get(i).toString() + "/";
        }
        s = s + this.ListeBloc.get(this.ListeBloc.size()-1).toString();
        return s;
    }
    
//-----Méthode d'ajout de bloc 1*1
    
    public void fusionDeBlocPlein(){
        if (this.ListeBloc.size() >= 2){        
            for (int i = 0 ; i < this.ListeBloc.size()-1 ; i++){       
                Bloc bloc_i = this.ListeBloc.get(i).cloneBloc();
                Bloc bloc_iPlus1 = this.ListeBloc.get(i+1).cloneBloc();
                if(bloc_i.getFin()+1 == bloc_iPlus1.getDebut() && bloc_i.getNature() == 1 &&  bloc_iPlus1.getNature() == 1){
                    this.ListeBloc.set(i, new Bloc(bloc_i.getTaille()+bloc_iPlus1.getTaille(), bloc_i.getDebut(),1));
                    this.ListeBloc.remove(i+1);
                    i--;
                }
            }
        }
    }
    
    public boolean bloc1x1DansListe(Bloc bloc1x1){
        boolean indiceDepasse = false;         //booléen pour ne pas avoir à verifier tous les blocs de la liste avec le while
        boolean blocDansListe = false;
        int i = 0 ;
        if(this.ListeBloc.isEmpty() == true){
            return blocDansListe;
        }
        else{
            while(i < this.ListeBloc.size() && blocDansListe == false && indiceDepasse == false){
                Bloc bloc_i = this.ListeBloc.get(i);
                if(bloc1x1.getDebut() > bloc_i.getFin()){             //if pour ne pas avoir a regarder les blocs de qui sont placés après bloc1x1
                    indiceDepasse = true;
                }
                if(bloc1x1.getDebut() >= bloc_i.getDebut() && bloc1x1.getDebut() <= bloc_i.getFin()){     //on verifie si le debut de bloc1x1 est compris dans le bloc_i
                    if(bloc1x1.getNature() == bloc_i.getNature()){                                        //puis on verifie si les natures des blocs sont les mêmes
                        blocDansListe = true;
                    }
                }
                i++;
            }
            return blocDansListe;
        }
        
    }
    
    public void separationDeBloc(Bloc bloc1x1){        //Pour separer deux blocs noir via un bloc blanc (qui est aussi placé) ou indeterminé  / Condition : bloc1x1.getTaille() == 1
        if(bloc1x1.getNature() == 0 || bloc1x1.getNature() == -1){
            int i = 0;
            boolean terminer = false;               //booleen pour faire l'equivalent du elif de python
            boolean blocSepare = false;             //Booleen pour arreter le while
            while(i < this.ListeBloc.size() && blocSepare == false){
                Bloc bloc_i = this.ListeBloc.get(i).cloneBloc();
                if(bloc1x1.getDebut() == bloc_i.getDebut() && terminer == false){         //Cas où l'on enlève que le début d'un bloc
                    this.ListeBloc.set(i, new Bloc(bloc_i.getTaille()-1, bloc_i.getDebut()+1, 1));
                    if(this.ListeBloc.get(i).getTaille()==0){
                        this.ListeBloc.remove(i);
                    }
                    if( bloc1x1.getNature() == 0){
                        this.ListeBloc.add(i,bloc1x1);
                    }
                    terminer = true;
                    blocSepare = true;
                }
                if(bloc1x1.getDebut() == bloc_i.getFin() && terminer == false){          // Cas où l'on enlève que la fin d'un bloc
                    this.ListeBloc.set(i, new Bloc(bloc_i.getTaille()-1, bloc_i.getDebut(), 1));
                    if(this.ListeBloc.get(i).getTaille()==0){
                        this.ListeBloc.remove(i);
                    }
                    if( bloc1x1.getNature() == 0){
                        this.ListeBloc.add(i+1,bloc1x1);
                    }
                    terminer = true;
                    blocSepare = true;
                }
                if(bloc1x1.getDebut() > bloc_i.getDebut() && bloc1x1.getDebut() < bloc_i.getFin() && terminer == false){          // Cas où l'on envlève le milieu d'un bloc (qui est donc coupé en deux)
                    this.ListeBloc.set(i, new Bloc(bloc1x1.getDebut()-bloc_i.getDebut(), bloc_i.getDebut(), 1));
                    if(bloc1x1.getNature() == 0){
                        this.ListeBloc.add(i+1,bloc1x1);
                        this.ListeBloc.add(i+2, new Bloc(bloc_i.getFin()-bloc1x1.getDebut(), bloc1x1.getDebut()+1, 1));

                    }
                    else{
                        this.ListeBloc.add(i+1, new Bloc(bloc_i.getFin()-bloc1x1.getDebut(), bloc1x1.getDebut()+1, 1));
                    }
                    blocSepare = true;
                    terminer = true;
                    
                }
                i++;
            }
        }
    }
    
    public void remplace_bloc1x1(Bloc bloc1x1){  //Methode pour emplacer un bloc de taille 1 déjà placé
        int i = 0;
        boolean terminer = false;
        while(i < this.getListeBloc().size() && terminer == false){
            if(this.ListeBloc.get(i).getDebut() == bloc1x1.getDebut() && this.ListeBloc.get(i).getFin() == bloc1x1.getFin()){
                this.ListeBloc.set(i,bloc1x1);
                terminer = true;
            }
            i++;
        }
    }
    
    public void AjouteBloc1x1(Bloc bloc1x1){                          
        if(this.ListeBloc.isEmpty() == true){     //Cas où la SerieBloc est vide
            this.ListeBloc.add(bloc1x1);
        }
        if(bloc1x1.getNature() == -1){            //Cas où on veut mettre un bloc gris
            this.separationDeBloc(bloc1x1);
        }
        else{
            if(this.bloc1x1DansListe(bloc1x1) == false){      //On verifie si le bloc n'est pas déja placé
                boolean terminer = false;
                if(bloc1x1.getDebut() > this.ListeBloc.get(this.ListeBloc.size()-1).getFin() && terminer == false){  //Cas où le bloc est juste à placé au début
                    this.ListeBloc.add(bloc1x1);
                    if(bloc1x1.getNature()==1){
                        this.fusionDeBlocPlein();
                    }
                    terminer = true;
                }
                if(bloc1x1.getFin() < this.ListeBloc.get(0).getDebut() && terminer == false){                  //Cas où le bloc est juste à placé a la fin
                    this.ListeBloc.add(0,bloc1x1);
                    if(bloc1x1.getNature()==1){
                        this.fusionDeBlocPlein();
                    }
                    terminer = true;
                }
                if(terminer == false){
                    int i = 0;
                    boolean blocPlace = false;
                    while(i < this.ListeBloc.size()-1 && blocPlace == false){           //Cas où le bloc est à placé entre deux autres blocs
                        Bloc bloc_i = this.ListeBloc.get(i);
                        Bloc bloc_iPlus1 = this.ListeBloc.get(i+1);
                        if(bloc1x1.getDebut() > bloc_i.getFin() && bloc1x1.getDebut() < bloc_iPlus1.getDebut()){
                            this.ListeBloc.add(i+1,bloc1x1);
                            blocPlace = true;
                        }
                        i++;
                    }
                    if(blocPlace == false){              
                        if(bloc1x1.getNature() == 0){        //Cas où l'on veut poser une bloc blanc dans un bloc noir
                            this.separationDeBloc(bloc1x1);   
                        }
                        else{                                //Cas où l'on veut remplacer un bloc blanc par un bloc noir
                            this.remplace_bloc1x1(bloc1x1);
                            this.fusionDeBlocPlein();
                        }
                    }
                    else{
                        this.fusionDeBlocPlein();
                    }
                }
            }
        }
    }
    
    
    
//-----Methode de vérification d'une SerieBloc par rapport à une autre (en comparant les blocs noir)
    
    public boolean verifierBlocs(SerieBloc BlocsAVerifier){   //Condition : BlocsAVerifier contient que des blocs de nature 1
        boolean listeBonne = false;
        boolean terminer_if;                     //booleen pour faire l'equivalent du elif de python
        boolean terminer_while = false;
        int i = 0;
        int j = 0;
        if(BlocsAVerifier.ListeBloc.isEmpty() == false){
            while(i < BlocsAVerifier.ListeBloc.size() && j < this.ListeBloc.size() && listeBonne == false && terminer_while == false){
                Bloc Bloc_i_a_verifier = BlocsAVerifier.ListeBloc.get(i);
                Bloc Bloc_j_de_la_liste = this.ListeBloc.get(j);
                if(Bloc_j_de_la_liste.getNature() == 1){
                    terminer_if = false;
                    if(Bloc_j_de_la_liste.equals(Bloc_i_a_verifier) == false && terminer_if == false){
                        terminer_while = true;
                        terminer_if = true;
                    }
                    if(Bloc_j_de_la_liste.equals(Bloc_i_a_verifier) == true && terminer_if == false){
                        i++;
                    }
                    if(i == BlocsAVerifier.ListeBloc.size() && terminer_if ==false){
                        listeBonne = true;
                   }
                }
                j++;
            }
            if(listeBonne == true){
                for(int k = j; k < this.ListeBloc.size(); k++){
                    Bloc Bloc_k_de_la_liste = this.ListeBloc.get(k);
                    if(Bloc_k_de_la_liste.getNature()==1){
                        listeBonne = false;
                    }
                }
            }
        }
        else{
            while(j < this.ListeBloc.size() && listeBonne == false && terminer_while == false){
                Bloc Bloc_j_de_la_liste = this.ListeBloc.get(j);
                if(Bloc_j_de_la_liste.getNature() == 1){
                    terminer_while = true;
                }
                j++;
            }
            if(j == this.ListeBloc.size()){
                listeBonne = true;
            }
        }

        return listeBonne;
    }
        
}
