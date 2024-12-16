 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projet_pi;

/**
 *
 * @author vruche
 */
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Picross {
    
    // Attributs
    private String nomFichier;
    private SerieBloc[] colonnes, lignes;
    private ArrayList<ArrayList<Integer>> lesColonnes, lesLignes;
    private String[] listeColonnes, listeLignes;
    private int n,m,decalX,decalY,nbBlocsIndetermine;
    private int[][] tableau;
        // attributs pour le jeu
    boolean retry = true;
    boolean jeuEnCours;
    boolean charger = false;
    boolean resolution = false;
        //attributs pour la résolution automatique
    boolean resolutionImpossible =false;
    int imodif, jmodif;
    int imodifInit= -1;
    int jmodifInit =-1;
    // Constructeurs
    
    //un constructeur vide suffit car on initialise toutes les données lors de la lecure du fichier
    
    public Picross(){   
    }
    
    // Getter et Setter
    public int getNbColonnes(){
        return this.m;
    }
    public int getNbLignes(){
        return this.n;
    }
    public String[] getlistecolonnes(){
        return this.listeColonnes;
    }
    public String[] getlistelignes(){
        return this.listeLignes;
    }
    public SerieBloc[] getcolonnes(){
        return this.colonnes;
    }
    public SerieBloc[] getlignes(){
        return this.lignes;
    }
    public ArrayList<ArrayList<Integer>> getlesColonnes(){
        return this.lesColonnes;
    }
    public ArrayList<ArrayList<Integer>> getlesLignes(){
        return this.lesLignes;
    }
    public int getDecalX(){
        return this.decalX;
    }
    public int getDecalY(){
        return this.decalY;
    }
    
    public String toString1(){
        String s = "" ; 
        for (int i = 0; i<this.lignes.length; i++){
            if(this.lignes[i].getListeBloc().isEmpty() == false){
                s = s + "Ligne " + (i+1) + " : " + this.lignes[i].toString() +"\n"; 
            }
        }
        for (int j = 0; j<this.colonnes.length; j++){
            if(this.colonnes[j].getListeBloc().isEmpty() == false ){
                s = s + "Colonne " + (j+1) + " : " + this.colonnes[j].toString() +"\n"; 
            }
        }         
        return s;
    }
    
    // récupération et initialisation des données
    public void lectureFichier(String nomDuFichier){
        try{
            nomFichier=nomDuFichier;
            BufferedReader fichier = new BufferedReader(new FileReader(nomFichier));
            this.n = Integer.parseInt(fichier.readLine()); //nombre de lignes
            this.m = Integer.parseInt(fichier.readLine()); //nombre de colonnes
            this.nbBlocsIndetermine = n*m; // nombre de bloc indéterminé (gris) donc au début c'est tout les blocs
            this.listeLignes = new String[this.n];
            this.listeColonnes = new String[this.m];
            fichier.readLine(); //on passe la ligne de séparation avec écrit #lignes
            for (int i=0; i<this.n;i++){
                this.listeLignes[i] = fichier.readLine();
            }
            fichier.readLine(); //on passe la ligne de séparation avec écrit #colonnes
            for (int j=0; j<this.m;j++){
                this.listeColonnes[j] = fichier.readLine();
            }
            fichier.close() ;
            } catch (IOException e) {
            }
    }
    
    public void convertListeStringToListeInt(){ //va convertir listeColonnes et listeLignes en des listes d'entier
        lesLignes = new ArrayList<>();
        lesColonnes = new ArrayList<>();
        for (int i=0; i<n;i++){ //sur les lignes
            String blocsL[] = this.listeLignes[i].split("\\s|;"); // sépare chaque ligne en une liste pour chaque élément à chaque espace ou à chaque ;
            // on convertit chacun de ces éléments en entier
            int blocsInt;
            ArrayList<Integer> suiteDeBloc1Ligne = new ArrayList<>();
            for (String bloc : blocsL) {
                if ("".equals(bloc) || " ".equals(bloc) || "0".equals(bloc)) {
                    //vérification si une ligne ne contient aucun bloc, si c'est le cas on met comme valeur -1
                    blocsInt = -1;
                } else {
                    blocsInt = Integer.parseInt(bloc);
                }
                suiteDeBloc1Ligne.add(blocsInt);
            }
            lesLignes.add(suiteDeBloc1Ligne);
        }
        for (int i=0; i<m;i++){ //sur les colonnes
            String blocsC[] = this.listeColonnes[i].split(" "); // sépare chaque colonne en une liste pour chaque élément
            // on convertit chacun de ces éléments en entier de bloc en entier puis en bloc 
            int blocsInt;
            ArrayList<Integer> suiteDeBloc1Colonne = new ArrayList<>();
            for (String bloc : blocsC) {
                if ("".equals(bloc) || " ".equals(bloc) || "0".equals(bloc)) {
                    //vérification si une colonne ne contient aucun bloc, si c'est le cas ou met comme valeur -1
                    blocsInt = -1;
                } else {
                    blocsInt = Integer.parseInt(bloc);
                }
                suiteDeBloc1Colonne.add(blocsInt); 
            }
            lesColonnes.add(suiteDeBloc1Colonne);
        }
    }
    
    //-----Initialisation de this.Lignes et this.Colonnes

    public void initialisation(){
        this.lignes = new SerieBloc[this.n];
        this.colonnes = new SerieBloc[this.m];
        for(int i = 0; i < this.n; i++){
            this.lignes[i] = new SerieBloc();
        }
        for(int j = 0; j < this.m; j++){
            this.colonnes[j] = new SerieBloc();
        }
        this.tableau = new int[n][m];
        for (int i=0; i<n; i++){
            for (int j=0;j<m;j++){
                this.tableau[i][j] = -1;
            }
        }
    }
    
    //méthode pour la sauvegarde
    public void ecritureSauvegarde(){
        try {
            String nomSauvegarde="sauvegarde"+nomFichier;
            FileWriter sauvegarde = new FileWriter(nomSauvegarde);
            sauvegarde.write(this.n + System.getProperty("line.separator"));
            sauvegarde.write(this.m + System.getProperty("line.separator"));
            sauvegarde.write("#lignes sauvegardée" + System.getProperty("line.separator"));
            // on ne parcours que les lignes car on a aussi les infos sur les colonnes
            for (int i=0; i<this.n; i++){
                if (this.lignes[i].getListeBloc().isEmpty()){
                    sauvegarde.write(""+ System.getProperty("line.separator"));
                } else{
                    sauvegarde.write(this.lignes[i] + System.getProperty("line.separator"));
                }
            }
            sauvegarde.close();
        } catch (IOException e) {
        }
    }
    
    public void lectureSauvegarde(Interface graph){
        try{
            String nomSauvegarde="sauvegarde"+nomFichier;
            BufferedReader fichier = new BufferedReader(new FileReader(nomSauvegarde));
            int n1,m1;
            String premiereLigne;
            premiereLigne = fichier.readLine();
            if ("".equals(premiereLigne)){
                System.out.println("erreur de sauvegarde, la sauvegarde que vous essayez de charger est vide");
                fichier.close();
                System.exit(0);
            }
            n1 = Integer.parseInt(premiereLigne); //nombre de lignes
            m1 = Integer.parseInt(fichier.readLine()); //nombre de colonnes
            if (n1!=this.n || m1!=this.m){
                System.out.println("erreur de sauvegarde, la sauvegarde que vous essayez de charger est differente du jeu actuelle");
                fichier.close();
                System.exit(0);
            }
            fichier.readLine();
            for (int i=0; i<this.n; i++){ //on parcours toutes les lignes
                String ligne;
                String[] blocs;
                ligne = fichier.readLine();
                if (!"".equals(ligne)){
                    blocs = ligne.split("/");
                    for (String bloc : blocs) {
                        //sur chaque ligne on parcours toutes les séries de blocs
                        String[] info;
                        info = bloc.split(";");
                        for (int j=Integer.parseInt(info[0]); j<Integer.parseInt(info[1])+1;j++){ //on parcours chaque blocs de série de bloc
                            int[] pos = new int[2];
                            int val=Integer.parseInt(info[3]);
                            pos[1]=i;
                            pos[0]=j;
                            if (Integer.parseInt(info[3])==0){
                                val = 3;
                            }
                            this.ChangerCouleurCase(graph,pos,val);
                            graph.repaint();
                            this.AjoutDeBloc1x1(Integer.parseInt(info[3]), i, j);
                        }
                    }
                }
            }
            fichier.close();
        } catch (IOException e) {
        }
    }
    
    //_________________________________________________________________________________________________________________________________________
    //Construction du Damier et des différentes interfaces
    /** * Avant la construction du damier on va créer une méthode et des attributs pour savoir la place que va prendre l'assignation des bloc à chaque
     *ligne/colonne.En effet plus il y'a de bloc sur une ligne ou une colonne plus on va devoir décaller le damier
     * @param liste
     * @return 
    **/
    public int Decallage(String[] liste){// va déterminer la plus grande chaine de caractère parmis les liste de string
        int max = 0;
        for (String ele : liste) {
            if (max < ele.length()) {
                max = ele.length();
            }
        }
        return max;
    }
    
    //construction du Damier
    public void Damier(Interface graph){
        graph.setBackground(Color.WHITE);
        // on détermine le décallage sur les lignes et les colonnes
        this.decalX = Decallage(listeLignes)*17+10;
        this.decalY = Decallage(listeColonnes)*18+10;
        graph.getGraphics2D().setColor(Color.BLACK);
        for (int i=0;i<this.n+1;i++){ //dessiner les lignes
            graph.getGraphics2D().drawLine(0, this.decalY+i*30, this.decalX+this.m*30, this.decalY+i*30);
        }
        for (int i=0;i<this.m+1;i++){ //dessiner les colonnes
            graph.getGraphics2D().drawLine(this.decalX+i*30, 0, this.decalX+i*30, this.decalY+this.n*30);
        }
        //dessiner les cases
        graph.getGraphics2D().setColor(Color.LIGHT_GRAY);
        for (int i=0;i<this.n;i++){
            for (int k=0;k<this.m;k++){
                graph.getGraphics2D().fillRect(this.decalX+1+k*30, this.decalY+1+i*30, 29, 29);
            }
        }
        //ajout des nombres de bloc par ligne/colonne
        Font fonte = new Font(" Dialog ",Font.PLAIN,20);
        graph.getGraphics2D().setColor(Color.BLACK);
        graph.getGraphics2D().setFont(fonte);
        //sur les lignes
        for (int i=0;i<this.n;i++){
            ArrayList<Integer> laLigne;
            laLigne = this.lesLignes.get(i);
            for (int k=laLigne.size();k>0;k=k-1){
                if (laLigne.get(k-1)!=-1){ //on affiche seulement si la ligne contient au moins un bloc
                    graph.getGraphics2D().drawString(" "+laLigne.get(k-1), this.decalX-30*(laLigne.size()-k+1), this.decalY+22+i*30);
                    graph.repaint();
                }
            }
        }
        //sur les colonnes
        for (int i=0;i<this.m;i++){
            ArrayList<Integer> laColonne;
            laColonne = this.lesColonnes.get(i);
            for (int k=laColonne.size();k>0;k=k-1){
                if (laColonne.get(k-1)!=-1){ //on affiche seulement si la colonne contient au moins un bloc
                    graph.getGraphics2D().drawString(" "+laColonne.get(k-1), this.decalX+i*30, this.decalY-10-30*(laColonne.size()-k));
                    graph.repaint();
                }
            }
        }
        // icone de menu
        graph.getGraphics2D().drawRect(decalX+30*(m+1), 30, 30, 30);
        fonte = new Font(" Dialog ",Font.PLAIN,35);
        graph.getGraphics2D().setColor(Color.BLACK);
        graph.getGraphics2D().setFont(fonte);
        graph.getGraphics2D().drawString("Menu", decalX+30*(m+2)+5,58);
    }
    
    //interface de fin de jeu
    public void FinDuJeu(boolean victoire){
        Souris furet = new Souris();
        Interface fin = new Interface(furet,230,180);
        fin.setBackground(Color.WHITE);
        Font fonte = new Font(" Dialog ",Font.PLAIN,50);
        fin.getGraphics2D().setColor(Color.RED);
        fin.getGraphics2D().setFont(fonte);
        if (victoire){
            fin.getGraphics2D().drawString("victoire !", 15,40);
            fin.repaint();
        } else{
            fin.getGraphics2D().drawString("perdu :'(", 15,40);
            fin.repaint();
        }
        fonte = new Font(" Dialog ",Font.PLAIN,30);
        fin.getGraphics2D().setColor(Color.BLACK);
        fin.getGraphics2D().setFont(fonte);
        fin.getGraphics2D().drawString("rejouer", 50, 100);
        fin.repaint();
        fin.getGraphics2D().drawString("quitter", 50, 140);
        fin.repaint();
        boolean finOuvert = true;
        while (finOuvert){
            if (furet.getClick()){
                if (100 < furet.getY() && furet.getY() < 140){
                    finOuvert = false;
                    fin.dispose();
                } else if(140 <furet.getY() && furet.getY()< 180){
                    System.exit(0);
                }
                furet.setClick(false);
            }
            fin.attendre(100);
        }
    }
    
    //interface de menu
    public void Menu(Interface graph, Souris mule){
        graph.setBackground(Color.WHITE);
        Font fonte = new Font(" Dialog ",Font.PLAIN,30);
        graph.getGraphics2D().setColor(Color.BLACK);
        graph.getGraphics2D().setFont(fonte);
        graph.getGraphics2D().drawString("Recommencer", 10,30);
        graph.getGraphics2D().drawString("Sauvegarder", 10,70);
        graph.getGraphics2D().drawString("Charger", 10,110);
        graph.getGraphics2D().drawString("Revenir au jeu", 10,150);
        graph.getGraphics2D().drawString("Quitter", 10,190);
        graph.getGraphics2D().drawString("Résolution", 10,230);
        graph.repaint();
        boolean menuOuvert = true;
        while (menuOuvert){
            if (mule.getClick()){
                if(mule.getY()<70){
                    System.out.println("recommencer");
                    jeuEnCours = false;
                    menuOuvert = false;
                    graph.dispose();
                } else if (mule.getY()<110){
                    System.out.println("jeu sauvergarde !");
                    this.ecritureSauvegarde();
                } else if (mule.getY()<150){
                    System.out.println("charger");
                    jeuEnCours = false;
                    menuOuvert = false;
                    charger = true;
                    graph.dispose();
                } else if (mule.getY()<190){
                    System.out.println("retour");
                    menuOuvert = false;
                    graph.dispose();
                } else if (mule.getY()<230){
                    System.exit(0);
                    System.out.println("quitter");
                } else if (mule.getY()<270){
                    System.out.println("resolution automatique");
                    jeuEnCours = false;
                    menuOuvert = false;
                    resolution = true;
                    graph.dispose();
                }
                mule.setClick(false);
            }
            graph.attendre(100);
        }
    }
    
    
    //__________________________________________________________________________
    // méthode de modification de cases
    
    public void ChangerCouleurCase(Interface graph, int[] position, int val){
        // on détermine le décallage sur les lignes et les colonnes
        this.decalX = Decallage(listeLignes)*17+10;
        this.decalY = Decallage(listeColonnes)*18+10;
        //position contient l'indice de la ligne et de la colonne de la case
        if (val==1){
            graph.getGraphics2D().setColor(Color.BLACK);
        }
        if (val==3){
            graph.getGraphics2D().setColor(Color.WHITE);
            val = 0; //dans noter code, une case blanche à comme valeur 0
        }
        if (val==2){
            graph.getGraphics2D().setColor(Color.LIGHT_GRAY);
            val = -1;
        }
        if (position[0]>=0 && position[1]>=0){ //si la position est positive (elle peut etre négative si on a cliqué sur le menu ou à coté ,cf méthode coordToCase mais ne peut pas valoir + que n ou m, ça évite de dessiner pour rien
            graph.getGraphics2D().fillRect(this.decalX+1+position[0]*30, this.decalY+1+position[1]*30, 29, 29);
            graph.repaint();
            this.AjoutDeBloc1x1(val, position[1], position[0]);
        }
    }
    
    public int[] coordToCase(int x, int y){ //va permettre d'associer les coordonnées du clique de la souris a la case cliqué
        int ncase[] = new int[2];
        ncase[0]=-2;// on initialise la position de la case a une valeur négative car (0,0) est une case du jeu
        ncase[1]=-2;
        if (decalX+10+30*(m+1)<=x && x<=decalX+10+30*(m+2) && 60<=y && y<=90){ //si on clique sur le menu, tout les y sont décalée de 30 pixel, donc l'image en haut commence au pixel 30 jsp pk
            Souris mulot = new Souris();
            Interface menu = new Interface(mulot, 250, 240);
            this.Menu(menu, mulot);
            
        }
        if (this.decalX+10<=x && x<=this.decalX+9+30*(m) && this.decalY+30<=y && y<=this.decalY+30*(n+1)){ //si on a cliqué sur une case du jeu et pas a côté
            ncase[0] = Math.round((x-this.decalX-10)/30);
            ncase[1] = Math.round((y-this.decalY-30)/30);
        }
        return ncase;
    }
    
    //méthode pour ajouter le bloc dans les listes de bloc et colonne
    public void AjoutDeBloc1x1(int nature, int i, int j){
        if (nature == 3){ //le clique gauche de la souris correspondant à une case blanche a comme valeur 3 et dans le code une case blanche à comme valeur 0 donc on modif
            nature = 0;
        }
        if (nature == 2){
            nature = -1;
        }
        if(i >= 0 && j >= 0 && i <= this.n && j <= this.m){
            this.lignes[i].AjouteBloc1x1(new Bloc(1,j,nature));
            this.colonnes[j].AjouteBloc1x1(new Bloc(1,i,nature));
        }
        if((nature == 1 || nature == 0) && this.tableau[i][j] == -1){
            this.nbBlocsIndetermine--;
        }
        if (nature == -1 && this.tableau[i][j] != -1){
            this.nbBlocsIndetermine++;
        }
        this.tableau[i][j] = nature;
        if (this.nbBlocsIndetermine==0){
            this.jeuEnCours = false;
        }
    }
    
    
    //__________________________________________________________________________
    // Méthodes pour vérifier la victoire
    
    public boolean verification(){
        boolean jeuFini = true;
        boolean terminer = false;
        int n1 = 0;
        int m1 = 0;
        SerieBloc[] Lignes_A_Verifier = new SerieBloc[this.lesLignes.size()];
        SerieBloc[] Colonnes_A_Verifier = new SerieBloc[this.lesColonnes.size()];
        for(int i = 0; i < this.lesLignes.size(); i++){
            if (this.lesLignes.get(i).get(0) != -1){
                Lignes_A_Verifier[i] = new SerieBloc(this.lesLignes.get(i));
            }
            else{
                Lignes_A_Verifier[i] = new SerieBloc();
            }
            
        }
        for(int j = 0; j < this.lesColonnes.size(); j++){
            if (this.lesColonnes.get(j).get(0) != -1){
                Colonnes_A_Verifier[j] = new SerieBloc(this.lesColonnes.get(j));
            }
            else{
                 Colonnes_A_Verifier[j] = new SerieBloc();
            }
        }
        while(n1 < this.lesLignes.size() && terminer == false){
            if(this.lignes[n1].verifierBlocs(Lignes_A_Verifier[n1]) == false){
                terminer = true;
                jeuFini = false;
            }
            n1++;
        }
        while(m1 < this.lesColonnes.size() && terminer == false){
            if(this.colonnes[m1].verifierBlocs(Colonnes_A_Verifier[m1]) == false){
                terminer = true;
                jeuFini = false;
            }
            m1++;
        }
        return jeuFini;
    }
    

    //__________________________________________________________________________
    //méthode pour jouer
    public void jeu(String fichier){
        while (retry){
            this.lectureFichier(fichier);
            this.convertListeStringToListeInt();
            this.initialisation();
            //on initalise la souris et la fenetre graphique
            Souris rat = new Souris();
            Interface fenetre = new Interface(this.getNbLignes(), this.getNbColonnes(),this.Decallage(this.getlistelignes()),this.Decallage(this.getlistecolonnes()), rat);
        
            //on initialise le jeu
            this.Damier(fenetre);
            jeuEnCours = true;
            if (charger){
                this.lectureSauvegarde(fenetre);
                charger = false;
            }
            if(resolution){
                this.resoudre(fenetre);
                if(!this.verification() && !resolutionImpossible){
                    jeuEnCours=false;
                    nbBlocsIndetermine++;//pour ne pas faire la vérification
                }  
            }
            //lancement du jeu
            while (jeuEnCours){
                if (rat.getClick()){
                    //détermine la case où on a cliqué
                    int clique = rat.getBoutonSouris();
                    int lacase[] =  this.coordToCase(rat.getX(),rat.getY());
                    this.ChangerCouleurCase(fenetre, lacase, clique);
                    fenetre.actualiser();
                    rat.setClick(false);
                }
                fenetre.attendre(100);
            }
            if (nbBlocsIndetermine==0 || resolutionImpossible){
                this.FinDuJeu(this.verification());
            }
            
            fenetre.dispose();
        }
    }
    
    
    //__________________________________________________________________________
    //résolution
    
    public void resoudre(Interface graph){
        ArrayList<Integer> lignesNonRempli = new ArrayList<>();
        ArrayList<Integer> colonnesNonRempli = new ArrayList<>();
        int[][] tabDebutBoucle,tabFinBoucle;
        for(int i = 0; i < n; i++){
            lignesNonRempli.add(i);
        }
        for(int j = 0; j < m; j++){
            colonnesNonRempli.add(j);
        }
        while(lignesNonRempli.isEmpty()==false && colonnesNonRempli.isEmpty() == false){
            //on enregistre le tableau au début de la boucle, on le recopie indice par indice car sinon le nouveau tableau est modifié en meme temps que le tableau
            tabDebutBoucle=new int[n][m];
            for(int i = 0; i < n; i++){
                for(int j = 0; j < m; j++){
                    tabDebutBoucle[i][j]=tableau[i][j];
                }
            }
            for (int indice : lignesNonRempli){
                //graph.attendre(10);
                this.finirRemplirLigne(graph,indice);
                //graph.attendre(10);
                this.dessinerCombinaisonLigne(graph,indice);
                //graph.attendre(10);
            }
            for (int indice : colonnesNonRempli){
                //graph.attendre(10);
                this.finirRemplirColonne(graph,indice);
                //graph.attendre(10);
                this.dessinerCombinaisonColonne(graph,indice);
                //graph.attendre(10);
            }
            for(int i = 0; i < n; i++){
                if(this.estRempliLigne(i) == true){
                    int k = 0;
                    boolean terminer = false;
                    while(k < lignesNonRempli.size() && terminer == false){
                        if(lignesNonRempli.get(k) == i){
                            lignesNonRempli.remove(k);
                            terminer = true;
                        }
                        k++;
                    }
                }
            }
            for(int j = 0; j < m; j++){
                if(this.estRempliColonne(j) == true){
                    int k = 0;
                    boolean terminer = false;
                    while(k < colonnesNonRempli.size() && terminer == false){
                        if(colonnesNonRempli.get(k) == j){
                            colonnesNonRempli.remove(k);
                            terminer =true;
                        }
                        k++;
                    }
                }
            }
            
            tabFinBoucle=new int[n][m];
            for(int i = 0; i < n; i++){
                for(int j = 0; j < m; j++){
                    tabFinBoucle[i][j]=tableau[i][j];
                }
            }
            //on vérifie que les tableaux de début et de fin de boucle soit bien différent et qu'on ne tourne pas en rond
            //on est obligé de parcourir toute la liste car le == et le .equals() ne marchent pas
            boolean estPareil=true;
            int i=0;
            while(estPareil && i<n){
                int j=0;
                while(estPareil && j<m){
                    if(tabFinBoucle[i][j]!=tabDebutBoucle[i][j]){
                        estPareil=false;
                    }
                    j++;
                }
                i++;
            }
            
            if(estPareil){
                System.out.println("pas de resolution evidente possible, recherche d'une des solutions");
                boolean unBlocPlace=false;
                i=0;
                while(!unBlocPlace && i<n){
                    int j=0;
                    while(!unBlocPlace && j<m){
                        if(tableau[i][j]==-1){
                            if(imodif!=i && jmodif!=j){
                                int[] pos= new int[2];
                                pos[0]=j;
                                pos[1]=i;
                                imodif = i;
                                jmodif = j;
                                if(imodifInit!=imodif && jmodifInit!=jmodif){
                                    this.ChangerCouleurCase(graph, pos, 1);
                                    unBlocPlace=true;
                                } else {
                                    resolutionImpossible=true;
                                    System.out.println("aucune solution trouve");
                                }
                                if (imodifInit==-1 && jmodifInit==-1){
                                    imodifInit=i;
                                    jmodifInit=j;
                                }
                            }
                        }
                        j++;
                    }
                    i++;
                }
            }
        }
        jeuEnCours = false;
    }
    
    
    //vérifie si une ligne ou une colonne est déjà entièrement rempli
    public boolean estRempliLigne(int indice){
        for (int k=0; k<m;k++){
            if (tableau[indice][k]==-1){
                return false;
            }
        }
        return true;
    }
    public boolean estRempliColonne(int indice){
        for (int k=0; k<n;k++){
            if (tableau[k][indice]==-1){
                return false;
            }
        }
        return true;
    }
    
    
    //méthodes pour faire des combinaisons de k parmi n
    public ArrayList<ArrayList<Integer>> genererCombinaisons(int n, int k){
        ArrayList<ArrayList<Integer>> combinaisons = new ArrayList<>();
        genererCombinaisonsRecursivite(n, k, 0,new ArrayList<>(), combinaisons);
        return combinaisons;
    }

    private static void genererCombinaisonsRecursivite(int n, int k, int debut,ArrayList<Integer> combActuelle, ArrayList<ArrayList<Integer>> combinaisons){
        if (k == 0){
            combinaisons.add(new ArrayList<>(combActuelle));
            //System.out.println(combinaisons);
            return;
        }
        for (int i=debut; i<=n-k; i++){
            combActuelle.add(i);
            genererCombinaisonsRecursivite(n, k-1, i+1,combActuelle, combinaisons);
            combActuelle.remove(combActuelle.size()-1);
        }
    }
    
    //méthodes pour transformer ces combinaisons en des listes de blocs noires/blancs correspondant à chauqe combinaisons
    public ArrayList<ArrayList<Integer>> genererCombinaisonBlocs(ArrayList<Integer> listeBloc, int taille){
        int nbCaseNoire=0;
        for (int nombre : listeBloc){
            nbCaseNoire+=nombre;
        }
        int k=listeBloc.size();
        int blancEnPlus = taille-nbCaseNoire-k+1;
        int p=k+blancEnPlus;
        ArrayList<ArrayList<Integer>> listeComb = genererCombinaisons(p,k);
        ArrayList<ArrayList<Integer>> tableauComb;
        tableauComb = new ArrayList<>();
        for (ArrayList<Integer> comb : listeComb) {
            //on créé un premier tableau simple (se référer au sites) ou on considère que chaque blocs noir à une taille 1
            ArrayList<Integer> uneCombSimple = new ArrayList<>(Collections.nCopies(p+k-1, 0));
            for(int i=0;i<comb.size();i++){
                //on va remplacer la case à la position i+comb.get(i) car il faut prendre en compte un décallage de 1 case
                //supplémentaire après chaque case noire
                uneCombSimple.set(i+comb.get(i), 1);
            }
            //on va maintenant remplir un nouveau tableau de la taille de la ligne/colonne avec les bonne taille de bloc noire
            ArrayList<Integer> uneCombComplete = new ArrayList<>(Collections.nCopies(taille, 0));
            int j=0;//c'est l'indice du nouveau tableau car il n'a pas la même taille que l'autre
            int indEleComb=0;
            for (int i=0;i<uneCombSimple.size();i++){
                if (uneCombSimple.get(i)==1){
                    //si on trouve un bloc noire, on complète le nouveau tableau par sa vraie taille
                    for (int s=0;s<listeBloc.get(indEleComb);s++){
                        uneCombComplete.set(j, 1);
                        j++;
                    }
                    indEleComb++;//on passe au bloc suivant de listeBloc
                    j--;//on décrémente de 1 sinon à la sortie de la boucle on va incrémenter j de 2
                }
                j++;
            }
            tableauComb.add(uneCombComplete);
        }
        return tableauComb;
    }
    
    //on détermine les blocs dont on est sur qu'ils seront présent
    public ArrayList<Integer> determinerCasePossible(ArrayList<ArrayList<Integer>> listeComb, int taille){
        ArrayList<Integer> tabFinal = new ArrayList<>(Collections.nCopies(taille, -1));
        for (int i=0;i<tabFinal.size();i++){
            boolean toujoursMemeBloc = true;
            int val=listeComb.get(0).get(i);
            int j=0;
            //on va parcourir toute les valeurs de toute les combinaisons possible à un indice donné pour vérifier si la valeur 
            //à cette indice est toujours la même
            while (toujoursMemeBloc && j<listeComb.size()){
                if (listeComb.get(j).get(i)!=val){
                    toujoursMemeBloc=false;
                }
                j++;
            }
            if (toujoursMemeBloc){
                tabFinal.set(i, val);
            }
        }
        return tabFinal;
    }
    
    //méthode similaire à la présédente pour déterminer quels blocs on est sur d'avoir sachant qu'il y'a déjà des blocs sur la ligne/colonne
    
    public void dessinerCombinaisonLigne(Interface graph, int indiceLigne){
        ArrayList<Integer> ligne = lesLignes.get(indiceLigne);
        //on récupère toutes les combinaisons possible
        ArrayList<ArrayList<Integer>> tableauComb = genererCombinaisonBlocs(ligne, this.m);
        //on détermine les combinaisons possible en acord avec les blocs déjà posé
        for(int k=0;k<tableauComb.size();k++){
            boolean combConvient = true;
            int i=0;
            while(combConvient && i<tableauComb.get(k).size()){
                if(tableau[indiceLigne][i]!=-1 && tableau[indiceLigne][i]!=tableauComb.get(k).get(i)){
                    tableauComb.remove(k);
                    combConvient=false;
                    k--;
                }
                i++;
            }
        }
        if(!tableauComb.isEmpty()){
            ArrayList<Integer> ligneFinal = this.determinerCasePossible(tableauComb,this.m);
            int[] pos = new int[2];
            pos[1]=indiceLigne;
            for (int i=0;i<ligneFinal.size();i++){
                pos[0]=i;
                if(tableau[indiceLigne][i]==-1){
                    if(ligneFinal.get(i)==1){
                        this.ChangerCouleurCase(graph, pos, 1);
                    }
                    if(ligneFinal.get(i)==0){
                        this.ChangerCouleurCase(graph, pos, 3);
                    }
                }
            }
        }
    }
    
    public void dessinerCombinaisonColonne(Interface graph, int indiceColonne){
        ArrayList<Integer> colonne = lesColonnes.get(indiceColonne);
        //on récupère toutes les combinaisons possible
        ArrayList<ArrayList<Integer>> tableauComb = genererCombinaisonBlocs(colonne, this.n);
        //on détermine les combinaisons possible en acord avec les blocs déjà posé
        for(int k=0;k<tableauComb.size();k++){
            boolean combConvient = true;
            int i=0;
            while(combConvient && i<tableauComb.get(k).size()){
                if(tableau[i][indiceColonne]!=-1 && tableau[i][indiceColonne]!=tableauComb.get(k).get(i)){
                    tableauComb.remove(k);
                    combConvient=false;
                    k--;
                }
                i++;
            }
        }
        if(!tableauComb.isEmpty()){
            ArrayList<Integer> colonneFinal = this.determinerCasePossible(tableauComb,this.n);
            int[] pos = new int[2];
            pos[0]=indiceColonne;
            for (int i=0;i<colonneFinal.size();i++){
                pos[1]=i;
                if(tableau[i][indiceColonne]==-1){
                    if(colonneFinal.get(i)==1){
                        this.ChangerCouleurCase(graph, pos, 1);
                    }
                    if(colonneFinal.get(i)==0){
                        this.ChangerCouleurCase(graph, pos, 3);
                    }
                }
            }
        }
    }

    //méthode pour remplir une ligne/colonne quand il reste que des cases noires ou blanches à mettre
        
    public void finirRemplirLigne(Interface graph, int indiceLigne){
        int nbCaseRestant=m;
        int nbCaseNoir=0;
        int nbCaseNoirTotal=0;
        for(int i=0;i<m;i++){
            if (tableau[indiceLigne][i]==1){
                nbCaseRestant--;
                nbCaseNoir++;
            }
            if (tableau[indiceLigne][i]==0){
                nbCaseRestant--;
            }
        }
        for(int bloc:lesLignes.get(indiceLigne)){
            nbCaseNoirTotal+=bloc;
        }
        for(int i=0;i<m;i++){
            if(tableau[indiceLigne][i]==-1){
                int[] pos = new int[2];
                pos[0]=i;
                pos[1]=indiceLigne;
                //si il reste aucune case noir, on remplit de blanc
                if(nbCaseNoirTotal-nbCaseNoir==0){
                    this.ChangerCouleurCase(graph,pos,3);
                }
                //si il reste autant de case que de bloc noir, on remplit de bloc noir
                if(nbCaseRestant==nbCaseNoirTotal-nbCaseNoir){
                    this.ChangerCouleurCase(graph,pos,1);
                }
            }
        }
    }
    public void finirRemplirColonne(Interface graph, int indiceColonne){
        int nbCaseRestant=n;
        int nbCaseNoir=0;
        int nbCaseNoirTotal=0;
        for(int i=0;i<n;i++){
            if (tableau[i][indiceColonne]==1){
                nbCaseRestant--;
                nbCaseNoir++;
            }
            if (tableau[i][indiceColonne]==0){
                nbCaseRestant--;
            }
        }
        for(int bloc:lesColonnes.get(indiceColonne)){
            nbCaseNoirTotal+=bloc;
        }
        for(int i=0;i<n;i++){
            if(tableau[i][indiceColonne]==-1){
                int[] pos = new int[2];
                pos[1]=i;
                pos[0]=indiceColonne;
                //si il reste aucune case noir, on remplit de blanc
                if(nbCaseNoirTotal-nbCaseNoir==0){
                    this.ChangerCouleurCase(graph,pos,3);
                }
                //si il reste autant de case que de bloc noir, on remplit de bloc noir
                if(nbCaseRestant==nbCaseNoirTotal-nbCaseNoir){
                    this.ChangerCouleurCase(graph,pos,1);
                }
            }
        }
    }
}