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
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author val10
 */
public class Interface extends JFrame {
    
    private BufferedImage image;
    private Graphics2D graphics;
    private int largeur, hauteur, x, y, bouton;
    private long tempsDerniereActualisation;
    private boolean click;
    
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public int getBoutonSouris(){
        return this.bouton;
    }
    public boolean getClick(){
        return click;
    }
    public void setClick(boolean val){
        this.click = val;
    }
    
    public Interface(Souris sour, int longueur, int largeur){ // pour le menu
        super();
        setSize(longueur,largeur);
        setTitle("Menu ");
        image = new BufferedImage(longueur,largeur, BufferedImage.TYPE_INT_ARGB);
        graphics = image.createGraphics();
                RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, 
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.addRenderingHints(rh);
        effacer(Color.WHITE);
        graphics.setColor(Color.BLACK);
        setResizable(false);
                ImageIcon icon = new ImageIcon(image);
        JLabel draw = new JLabel(icon);
        setContentPane(draw);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        this.addMouseListener(sour);
    }
    
    public Interface(int nbDeLignes,int nbDeColonnes,int decalX,int decalY, Souris rat){
        super();

        if (nbDeLignes <= 0 || nbDeColonnes <= 0) {
            throw new IllegalArgumentException("La largeur et la hauteur de la fenetre doivent être positives");
        }

        setSize(decalX*20+(nbDeColonnes+2)*30+100,decalY*20+(nbDeLignes+2)*30);
        setTitle("Picross de taille " + nbDeLignes + " " + nbDeColonnes);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        image = new BufferedImage(decalX*20+(nbDeColonnes+2)*30+100,decalY*20+(nbDeLignes+2)*30, BufferedImage.TYPE_INT_ARGB);
        graphics = image.createGraphics();
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, 
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.addRenderingHints(rh);

        ImageIcon icon = new ImageIcon(image);
        JLabel draw = new JLabel(icon);
        setContentPane(draw);
        pack();
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        this.addMouseListener(rat);
    }

    public Graphics2D getGraphics2D() {
        return graphics;
    }

    public void actualiser() {
        this.paint(this.getGraphics());
    }

    public void actualiser(long millisecondes) {
        while (System.currentTimeMillis() - tempsDerniereActualisation < millisecondes) {
            attendre(1);
        }
        tempsDerniereActualisation = System.currentTimeMillis();
        repaint();
    }

    public void attendre(long millisecondes) {
        try {
            Thread.sleep(millisecondes);
        } catch (InterruptedException ex) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fermer() {
        dispose();
    }

    public void effacer() {
        effacer(Color.WHITE);
    }

    public void effacer(Color couleur) {
        Color couleurCourante = graphics.getColor();
        graphics.setColor(couleur);
        graphics.fillRect(0, 0, largeur, hauteur);
        graphics.setColor(couleurCourante);
    }
}